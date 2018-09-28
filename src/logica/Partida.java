package logica;

import java.util.ArrayList;
import java.util.Stack;

import observadores.Observable;
import observadores.Observador;
import reglas.ReglasJuego;
import modos.ModoJugador;
import movimiento.Movimiento;
import factorias.FactoriaJuego;
import jugador.Jugador;
import excepciones.DatosIncorrectos;
import excepciones.MovimientoInvalido;

/** Clase Partida, lleva las funciones relativas a la gestión de la partida*/
public class Partida implements Observable{
	private ArrayList <Observador> obs;
	private Stack <Movimiento> undoStack;
	private Tablero tablero;
	private Ficha turno;
	private Ficha ganador;
	private boolean terminada;
	private ReglasJuego reglas;	
	
	/** Constructora de Partida, inicia los atributos por defecto*/
	public Partida(ReglasJuego juego){
		obs = new ArrayList <Observador> ();
		undoStack = new Stack <Movimiento> (); //Creamos la pila de undo's
		undoStack.setSize(10);
		reglas = juego;
		reset(juego);
	}
	
	@Override
	public void addObservador(Observador o){
		if(!obs.contains(o))
			obs.add(o);
	}
	
	@Override
	public void removeObservador(Observador o){
		obs.remove(o);
	}

	/** Determina si la partida ha finalizado
	 * @return terminada, ya sea porque quede en tablas o bien porque haya un ganador
	 */
	public boolean partidaTerminada(){
		if(!terminada)  //En caso de que no se hayan encontrado grupos comprueba el estado del tablero
			terminada = reglas.tablas(tablero);
		if(terminada){
			for (Observador o : obs) 
				o.onPartidaTerminada(tablero, ganador);
		}
			
		return terminada;
	}
	
	/** Lleva a cabo el movimiento de una ficha en la partida
	 * @param mov, movimiento que se efectuará
	 * @return valido, si la columna es correcta y no se ha completado todavía
	 * @throws MovimientoInvalido 
	 */
	public void ejecutaMovimiento(Movimiento mov) throws MovimientoInvalido{
		int columna, fila;	
		
		try{
			if(!terminada){
				mov.ejecutaMovimiento(tablero);
				columna = mov.getColumna();
				fila = mov.getFila();
				ganador = reglas.hayGanador(fila, columna, turno, tablero);
				
				undoStack.push(mov);  //Añadimos el movimiento a la pila
				
				if(ganador != Ficha.VACIA)
					terminada = true;
				else{
					turno = reglas.siguienteTurno(turno);    //Cambiamos de turno
					for (Observador o : obs) 
						o.onMovimientoEnd(tablero, turno);
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e){
			for (Observador o : obs) 
				o.onMovimientoIncorrecto(new MovimientoInvalido("El movimiento incorrecto\nInténtelo de nuevo: "));
		}
	}
	
	/** Resetea la partida
	 * @param reglas, necesaria para iniciar a cero el tablero, de acuerdo con el tipo de juego
	 * */
	public void reset(ReglasJuego reglas){
		tablero = reglas.iniciaTablero();
		turno = reglas.jugadorInicial();
		undoStack.clear();   //Vaciamos la pila de undo's
		
		if(this.reglas.equals(reglas)){
			for (Observador o : obs) 
				o.onReset(tablero, turno);
		}
		else{
			for (Observador o : obs) 
				o.onCambioJuego(tablero, turno);
		}
		
		terminada = false;
		
		this.reglas = reglas;
	}
	
	/** Deshace un movimiento
	 * @return movDeshecho, indicando si se ha podido deshacer el movimiento
	 */
	public boolean undo(){
		boolean deshecho;
		if(undoStack.empty()){
			deshecho = false;
			for (Observador o : obs) 
				o.onUndoNotPossible();
		}
		else{
			Movimiento mov = undoStack.pop();
			mov.undo(this.tablero);
			if(undoStack.size() != 0)
				turno = mov.getJugador();    //Extraemos el turno anterior
			else
				turno = reglas.jugadorInicial();
			
			deshecho = true;
			for (Observador o : obs) 
				o.onUndo(tablero, turno, !undoStack.empty());
		}
		return deshecho;
	}
	
	public Movimiento getMovimiento(FactoriaJuego factoria, Jugador jugador) throws DatosIncorrectos{
		try{
			return jugador.getMovimiento(factoria, this.tablero, this.turno);
		}
		catch (DatosIncorrectos e){
			throw e;
		}
	}
	
	public void salir(){
		for (Observador o : obs) 
			o.onExit();
	}
	
	public void continuarPartida(ModoJugador modoJugador){
		for (Observador o : obs) 
			o.onMovimientoStart(turno);	
		
		modoJugador.comenzar();	
	}
	
	public void detenerPartida(ModoJugador modoJugador){
		modoJugador.terminar();
	}
	
	/** Coge el jugador de ese momento
	 * @return turno, con el jugador pertinente
	 */
	public Ficha getJugador(){
		return turno;
	}
	
	public boolean getTerminada(){
		return terminada;
	}
	
	public ReglasJuego getReglas(){
		return reglas;
	}
	
	public Tablero getTablero(){
		return tablero;
	}

	public void setJugador(Ficha siguienteTurno) {
		this.turno = siguienteTurno;
	}

	public void setTerminada(boolean terminada) {
		this.terminada = terminada;
	}
}