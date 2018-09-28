package factorias;

import java.util.Scanner;

import reglas.Reglas3Raya;
import reglas.ReglasJuego;
import movimiento.Movimiento;
import movimiento.Movimiento3Raya;
import jugador.Jugador;
import jugador.JugadorAleatorio3Raya;
import jugador.JugadorHumano3Raya;
import logica.Ficha;

public class FactoriaJuego3Raya implements FactoriaJuego {
	@Override
	public ReglasJuego creaReglas() {
		return new Reglas3Raya();
	}
	@Override
	public ReglasJuego creaReglas(int filas, int columnas) {
		return new Reglas3Raya(filas, columnas);
	}
	@Override
	public Movimiento creaMovimiento(int fila, int columna, Ficha color) {
		return new Movimiento3Raya(fila, columna, color);
	}
	@Override
	public Jugador creaJugadorAleatorio() {
		return new JugadorAleatorio3Raya();
	}
	@Override
	public Jugador creaJugadorHumano(Scanner sc) {
		return new JugadorHumano3Raya(sc);
	}	
}