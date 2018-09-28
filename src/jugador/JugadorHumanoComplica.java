package jugador;

import java.util.Scanner;

import logica.Tablero;
import logica.Ficha;

public class JugadorHumanoComplica extends Jugador{
	private Scanner sc;
	public JugadorHumanoComplica(Scanner sc){
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