package factorias;

import java.util.Scanner;
import reglas.ReglasJuego;
import reglas.ReglasGravity;
import movimiento.Movimiento;
import movimiento.MovimientoGravity;
import jugador.Jugador;
import jugador.JugadorAleatorioGravity;
import jugador.JugadorHumanoGravity;
import logica.Ficha;

public class FactoriaJuegoGravity implements FactoriaJuego {
	@Override
	public ReglasJuego creaReglas() {
		return new ReglasGravity();
	}
	@Override
	public ReglasJuego creaReglas(int filas, int columnas) {
		return new ReglasGravity(filas, columnas);
	}
	@Override
	public Movimiento creaMovimiento(int fila, int columna, Ficha color) {
		return new MovimientoGravity(fila, columna, color);
	}
	@Override
	public Jugador creaJugadorAleatorio() {
		return new JugadorAleatorioGravity();
	}
	@Override
	public Jugador creaJugadorHumano(Scanner sc) {
		return new JugadorHumanoGravity(sc);
	}	
}