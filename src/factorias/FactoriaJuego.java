package factorias;

import java.util.Scanner;
import reglas.ReglasJuego;
import movimiento.Movimiento;
import jugador.Jugador;
import logica.Ficha;

public interface FactoriaJuego {
	public ReglasJuego creaReglas();
	public ReglasJuego creaReglas(int fila, int columna);
	public Movimiento creaMovimiento(int fila, int columna, Ficha color);
	public Jugador creaJugadorAleatorio();
	public Jugador creaJugadorHumano(Scanner sc);	
}