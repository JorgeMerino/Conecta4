package jugador;

import java.util.Scanner;

import logica.Ficha;
import logica.Tablero;

public class JugadorHumanoReversi extends Jugador{
	private Scanner sc;
	public JugadorHumanoReversi(Scanner sc){
		this.sc = sc;
	}
	@Override
	public void obtenFilaColumna(Tablero tablero, Ficha color){
		super.color = color;
		System.out.print("Introduce la fila: ");
		int fila = sc.nextInt();
		System.out.print("Introduce la columna: ");
		int columna = sc.nextInt();
		sc.nextLine(); // Quitamos el INTRO
		this.fila = fila - 1;
		this.columna = columna - 1;
	}
}
