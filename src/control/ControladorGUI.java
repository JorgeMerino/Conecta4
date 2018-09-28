package control;

import java.util.ArrayList;

import jugador.Jugador;
import reglas.ReglasJuego;
import logica.Ficha;
import logica.Partida;
import logica.TipoJuego;
import logica.TipoJugador;
import modos.ModoJugador;
import modos.ModoJugadorAutomatico;
import modos.ModoJugadorHumano;
import movimiento.Movimiento;
import movimiento.MovimientoReversi;
import excepciones.DatosIncorrectos;
import excepciones.MovimientoInvalido;
import factorias.FactoriaJuego;
import factorias.FactoriaJuego3Raya;
import factorias.FactoriaJuegoComplica;
import factorias.FactoriaJuegoConecta4;
import factorias.FactoriaJuegoGravity;
import factorias.FactoriaJuegoReversi;

public class ControladorGUI {
	private Partida partida;
	private FactoriaJuego factoria;
	private TipoJuego tipoJuego;
	private ModoJugador modoBlancas;
	private ModoJugador modoNegras;
	private ModoJugador modoJugadorActual;
	private ModoJugador modoJugadorContrario;
		
	public ControladorGUI(Partida partida, FactoriaJuego factoria, TipoJuego tipoJuego){
		this.partida = partida;
		this.factoria = factoria;
		this.tipoJuego = tipoJuego;
		modoBlancas = new ModoJugadorHumano();
		modoNegras = new ModoJugadorHumano();
		
		if(Ficha.BLANCA == partida.getJugador()){
			modoJugadorActual = modoBlancas;
			modoJugadorContrario = modoNegras;
		}
		else{
			modoJugadorActual = modoNegras;
			modoJugadorContrario = modoBlancas;
		}	
	}
	
	public void deshacer(){
		partida.detenerPartida(modoJugadorActual);
		partida.undo();
		partida.continuarPartida(modoJugadorContrario);
	}
	
	public void jugar(TipoJuego tipoJuego){
		ReglasJuego reglas;
		this.tipoJuego = tipoJuego;
		switch(tipoJuego){
			case C4:
				factoria = new FactoriaJuegoConecta4();
			break;
			case CO:
				factoria = new FactoriaJuegoComplica();
			break;
			case GR:
				factoria = new FactoriaJuegoGravity();
			break;
			case RV:
				factoria = new FactoriaJuegoReversi();
			break;
			default:
				factoria = new FactoriaJuego3Raya();
			break;
		}
		reglas = factoria.creaReglas();    //Llamamos a la constructora de partida
		partida.reset(reglas);
	}
	
	public void jugar(TipoJuego tipoJuego, int fila, int columna){
		ReglasJuego reglas;
		this.tipoJuego = tipoJuego;
		if(tipoJuego == TipoJuego.GR)
			factoria = new FactoriaJuegoGravity();
		else if(tipoJuego == TipoJuego.TR)
			factoria = new FactoriaJuego3Raya();
		
		reglas = factoria.creaReglas(fila, columna);    //Llamamos a la constructora de partida
		partida.reset(reglas);
	}
	
	public void poner(int fila, int columna){
		Movimiento mov; 
		try{
			partida.detenerPartida(modoJugadorActual);
			
			if(tipoJuego != TipoJuego.RV){
				mov = factoria.creaMovimiento(fila, columna, partida.getJugador());
				partida.ejecutaMovimiento(mov);   //Ejecutamos el movimiento y comprobamos que no haya terminado la partida
			}			
			else{
				mov = factoria.creaMovimiento(fila, columna, partida.getJugador());
				((MovimientoReversi) mov).comprobarPosibles(partida.getTablero());   //((MovimientoReversi) mov) hace un casting de MovimientoReversi sobre mov: Movimiento, para asegurarse de que mov es de tipo MovimientoReversi
				ArrayList <Movimiento> fichasPosibles = ((MovimientoReversi) mov).getFichasPosibles();
				
				//Comprueba que haya movimientos posibles, sino cambia de turno y si ninguno del los dos puede, termina la partida
				if(fichasPosibles.size() != 0){
					for(int i=0; i<fichasPosibles.size(); i++){
						if(fila == fichasPosibles.get(i).getFila() && columna == fichasPosibles.get(i).getColumna() && partida.getJugador() == fichasPosibles.get(i).getJugador()){
							partida.ejecutaMovimiento(mov);   //Ejecutamos el movimiento y comprobamos que no haya terminado la partida
						}
					}
				}
				else{
					partida.setJugador(((MovimientoReversi) mov).turnoContrario(partida.getJugador()));
					mov = factoria.creaMovimiento(fila, columna, partida.getJugador());
					((MovimientoReversi) mov).comprobarPosibles(partida.getTablero());
					fichasPosibles = ((MovimientoReversi) mov).getFichasPosibles();
					
					if(fichasPosibles.size() != 0){
						for(int i=0; i<fichasPosibles.size(); i++){
							if(fila == mov.getFila() && columna == mov.getColumna() && partida.getJugador() == mov.getJugador()){
								partida.ejecutaMovimiento(mov);   //Ejecutamos el movimiento y comprobamos que no haya terminado la partida
							}
						}
					}
					else
						partida.setTerminada(true);
				}
			}			
			
			if(Ficha.BLANCA == partida.getJugador()){
				modoJugadorActual = modoBlancas;
				modoJugadorContrario = modoNegras;
			}
			else{
				modoJugadorActual = modoNegras;
				modoJugadorContrario = modoBlancas;
			}
			
			partida.continuarPartida(modoJugadorActual);
			
			partida.partidaTerminada();
		}
		catch(MovimientoInvalido e){
			System.out.println(e.getMessage());
		}
	}
	
	public void ponAleatorio(){
		Jugador jugador = factoria.creaJugadorAleatorio();
		Movimiento mov; 
		
		try{
			mov = jugador.getMovimiento(factoria, partida.getTablero(), partida.getJugador());
			partida.setJugador(mov.getJugador());
			
			if(Ficha.BLANCA == partida.getJugador()){
				modoJugadorActual = modoBlancas;
				modoJugadorContrario = modoNegras;
			}
			else{
				modoJugadorActual = modoNegras;
				modoJugadorContrario = modoBlancas;
			}
			poner(mov.getFila(), mov.getColumna());
		}
		catch(DatosIncorrectos e){
			e.printStackTrace();
		}
	}
	
	public void cambioJugador(Ficha turno, TipoJugador tipoJugador){
		partida.detenerPartida(modoJugadorActual);
		if(turno == Ficha.BLANCA){
			if(tipoJugador == TipoJugador.HUMANO)
				modoBlancas = new ModoJugadorHumano();
			else
				modoBlancas = new ModoJugadorAutomatico(this);
		}
		else{
			if(tipoJugador == TipoJugador.HUMANO)
				modoNegras = new ModoJugadorHumano();
			else
				modoNegras = new ModoJugadorAutomatico(this);
		}
		
		if(Ficha.BLANCA == partida.getJugador()){
			modoJugadorActual = modoBlancas;
			modoJugadorContrario = modoNegras;
		}
		else{
			modoJugadorActual = modoNegras;
			modoJugadorContrario = modoBlancas;
		}
		
		partida.continuarPartida(modoJugadorActual);
	}
	
	public void reiniciar(){
		partida.detenerPartida(modoJugadorActual);
		partida.reset(partida.getReglas());
		
		if(Ficha.BLANCA == partida.getJugador()){
			modoJugadorActual = modoBlancas;
			modoJugadorContrario = modoNegras;
		}
		else{
			modoJugadorActual = modoNegras;
			modoJugadorContrario = modoBlancas;
		}
		
		partida.continuarPartida(modoJugadorActual);
	}
	
	public void salir(){
		partida.salir();
	}
	
	public Partida getPartida(){
		return partida;
	}

	public TipoJuego getTipoJuego() {
		return tipoJuego;
	}
}