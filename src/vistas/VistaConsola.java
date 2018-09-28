package vistas;

import excepciones.MovimientoInvalido;
import observadores.Observador;
import logica.Ficha;
import logica.TableroSoloLectura;

public class VistaConsola implements Observador{
	//Gestión Partida
	@Override
	public void onReset(TableroSoloLectura tablero, Ficha turno){
		System.out.println(tablero.toString());  //El metodo toString() imprime la matriz por pantalla
		System.out.println("Partida Reiniciada");
		switch(turno){
			case BLANCA: 
				System.out.println("Juegan Blancas");
			break;
			default: 
				System.out.println("Juegan Negras");
			break;
		}
		System.out.print("Que quiere hacer?: ");  //Introducimos la accion
	}
	
	@Override
	public void onPartidaTerminada(TableroSoloLectura tableroFinal, Ficha ganador){
		System.out.println("\n" + tableroFinal.toString());  //El metodo toString() imprime la matriz por pantalla
		if(ganador != Ficha.VACIA){
			switch(ganador){
				case BLANCA: 
					System.out.println("Ganan las Blancas");
				break;
				default: 
					System.out.println("Ganan las Negras");
				break;
			}
		}
		else 
			System.out.println("El juego termina en Tablas");
	}
	
	@Override
	public void onCambioJuego(TableroSoloLectura tablero, Ficha turno){
		System.out.println(tablero.toString());  //El metodo toString() imprime la matriz por pantalla
		System.out.println("Juego Nuevo");
		switch(turno){
			case BLANCA: 
				System.out.println("Juegan Blancas");
			break;
			default: 
				System.out.println("Juegan Negras");
			break;
		}
		System.out.print("Que quiere hacer?: ");  //Introducimos la accion
	}
	
	@Override
	public void onExit(){}
	
	//Gestión Movimientos
	@Override
	public void onUndoNotPossible(){
		System.out.println("Imposible Deshacer");
		System.out.print("Que quiere hacer?: ");  //Introducimos la accion
	}
	
	@Override
	public void onUndo(TableroSoloLectura tablero, Ficha turno, boolean hayMas){
		System.out.println("Movimiento Deshecho");
		System.out.println(tablero.toString());  //El metodo toString() imprime la matriz por pantalla
		switch(turno){
			case BLANCA: 
				System.out.println("Juegan Blancas");
			break;
			default: 
				System.out.println("Juegan Negras");
			break;
		}
		System.out.print("Que quiere hacer?: ");  //Introducimos la accion		
	}
	
	@Override
	public void onMovimientoStart(Ficha turno){}
	
	@Override
	public void onMovimientoEnd(TableroSoloLectura tablero, Ficha turno){
		System.out.println(tablero.toString());  //El metodo toString() imprime la matriz por pantalla
		switch(turno){
			case BLANCA: 
				System.out.println("Juegan Blancas");
			break;
			default: 
				System.out.println("Juegan Negras");
			break;
		}
		System.out.print("Que quiere hacer?: ");  //Introducimos la accion
	}
	
	@Override
	public void onMovimientoIncorrecto(MovimientoInvalido movimientoException){
		System.out.println(movimientoException.getMessage());
	}
}