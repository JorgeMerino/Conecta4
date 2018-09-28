package jugador;

import java.util.Scanner;

import logica.Tablero;
import logica.Ficha;

public class JugadorHumanoConecta4 extends Jugador{
	private Scanner sc;
	public JugadorHumanoConecta4(Scanner sc){
		this.sc = sc;
	}
	@Override
	public void obtenFilaColumna(Tablero tablero, Ficha color){
		super.color = color;
		System.out.print("Introduce la columna: ");
		int columna = sc.nextInt();
		sc.nextLine(); // Quitamos el INTRO
		this.columna = columna - 1;
	}
}