package control;

import java.util.Scanner;

import reglas.ReglasJuego;
import logica.Ficha;
import logica.Partida;
import logica.Tablero;
import logica.TipoJugador;
import movimiento.Movimiento;
import excepciones.DatosIncorrectos;
import excepciones.ErrorDeEjecucion;
import excepciones.MovimientoInvalido;
import factorias.FactoriaJuego;
import jugador.Jugador;
import comandos.Comando;
import comandos.Jugar;
import comandos.PonJugador;
import comandos.ParserAyudaComandos;

/** Clase Controlador, se encarga del funcionamiento principal del programa*/
public class ControladorConsola {
	private Partida partida;
	private Scanner in;
	private FactoriaJuego factoria;
	private Jugador jugador;
	private Jugador jugador1;
	private Jugador jugador2;
	private boolean terminadaPoner;
	private boolean terminadaSalir;
	
	/** Constructora de Controlador
	 * @param partida, de tipo Partida es inicializado
	 * @param in, de tipo Scanner es inicializado
	 */
	public ControladorConsola(FactoriaJuego factoria, Partida partida, Scanner in){
		this.partida = partida;
		this.factoria = factoria;
		this.in = in;
		jugador1 = this.factoria.creaJugadorHumano(in);
		jugador = jugador1;
		jugador2 = this.factoria.creaJugadorHumano(in);
		terminadaPoner = false;
		terminadaSalir = false;
	}
	
	/** Método run, se encarga de recibir los distintos comandos y llevar a cabo las acciones pertinentes
	 * @throws MovimientoInvalido 
	 * @throws DatosIncorrectos 
	 * @throws ErrorDeEjecucion */
	public void run() throws MovimientoInvalido, DatosIncorrectos, ErrorDeEjecucion{
		String[] cadenas;
		String cadena;
		Comando comando;
		
		do{	
			try{
				do{
					cadena = in.nextLine();
					cadenas = cadena.split(" ");
					comando = ParserAyudaComandos.parsea(cadenas);
					if(comando == null){
						System.out.println("Comando Incorrecto");
						System.out.print("Inténtelo de nuevo: ");  //Introducimos la accion
					}
				}while(comando == null);
				comando.execute(this);
			}
			catch(NullPointerException e){
				throw new ErrorDeEjecucion("Comando Incorrecto.");
			}
		}while(!terminadaPoner && !terminadaSalir);
	}
	
	public void ayuda(){
		System.out.println(ParserAyudaComandos.AyudaComandos());
	}
	
	public void deshacer(){
		boolean movDeshecho = partida.undo();
		if(movDeshecho){
			if(jugador.equals(jugador1))
				jugador = jugador2;
			else
				jugador = jugador1;
		}
	}
	
	public void jugar(){
		int filas, columnas;
		ReglasJuego reglas;
		factoria = Jugar.getFactoria();
		filas = Jugar.getFilas();
		columnas = Jugar.getColumnas();
		if(filas == 0 || columnas == 0)
			reglas = factoria.creaReglas();    //Llamamos a la constructora de partida
		else
			reglas = factoria.creaReglas(filas, columnas);
			
		partida.reset(reglas);
		jugador1 = factoria.creaJugadorHumano(in);
		jugador2 = factoria.creaJugadorHumano(in);
		jugador = jugador1;
	}
	
	public void poner(){
		Movimiento mov;
		Tablero tablero = partida.getTablero();		
		Ficha color = partida.getJugador();	
		
		try{
			mov = jugador.getMovimiento(factoria, tablero, color);
			partida.ejecutaMovimiento(mov);   //Ejecutamos el movimiento y comprobamos que no haya terminado la partida
			if(jugador.equals(jugador1))
				jugador = jugador2;
			else
				jugador = jugador1;
		}
		catch(DatosIncorrectos e){
			System.out.println(e.getMessage());
			in.nextLine();   //Para que cargue todo el buffer restante
		}
		catch(MovimientoInvalido e){
			System.out.println(e.getMessage());
		}
		
		terminadaPoner = partida.partidaTerminada();	
	}
	
	public void ponjugador(){
		Ficha color = PonJugador.getColor();
		TipoJugador tipoJugador = PonJugador.getTipoJugador();
		if(color == Ficha.BLANCA){
			if(tipoJugador == TipoJugador.HUMANO){
				jugador1 = factoria.creaJugadorHumano(in);
				
			}
			else if(tipoJugador == TipoJugador.AUTO){
				jugador1 = factoria.creaJugadorAleatorio();
			}
			jugador = jugador1;
			partida.reset(partida.getReglas());
		}
		else if(color == Ficha.NEGRA){
			if(tipoJugador == TipoJugador.HUMANO){
				jugador2 = factoria.creaJugadorHumano(in);
			}
			else if(tipoJugador == TipoJugador.AUTO){
				jugador2 = factoria.creaJugadorAleatorio();
			}
			jugador = jugador1;
			partida.reset(partida.getReglas());
		}
	}
	
	public void reiniciar(){
		partida.reset(partida.getReglas());
		jugador = jugador1;
	}
	
	public void salir(){
		terminadaSalir = true;
	}
}