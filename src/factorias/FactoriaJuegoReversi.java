package factorias;

import java.util.Scanner;

import jugador.Jugador;
import jugador.JugadorAleatorioReversi;
import jugador.JugadorHumanoReversi;
import logica.Ficha;
import movimiento.Movimiento;
import movimiento.MovimientoReversi;
import reglas.ReglasReversi;
import reglas.ReglasJuego;

public class FactoriaJuegoReversi implements FactoriaJuego {
	@Override
	public ReglasJuego creaReglas() {
		return new ReglasReversi();
	}
	@Override
	public ReglasJuego creaReglas(int filas, int columnas) {
		return null;
	}
	@Override
	public Movimiento creaMovimiento(int fila, int columna, Ficha color){
		return new MovimientoReversi(fila, columna, color);
	}
	@Override
	public Jugador creaJugadorAleatorio() {
		return new JugadorAleatorioReversi();
	}
	@Override
	public Jugador creaJugadorHumano(Scanner sc) {
		return new JugadorHumanoReversi(sc);
	}	
}
