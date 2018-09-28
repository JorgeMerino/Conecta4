package factorias;

import java.util.Scanner;
import reglas.ReglasJuego;
import reglas.ReglasComplica;
import movimiento.Movimiento;
import movimiento.MovimientoComplica;
import jugador.Jugador;
import jugador.JugadorAleatorioComplica;
import jugador.JugadorHumanoComplica;
import logica.Ficha;

public class FactoriaJuegoComplica implements FactoriaJuego {
	@Override
	public ReglasJuego creaReglas() {
		return new ReglasComplica();
	}
	@Override
	public ReglasJuego creaReglas(int filas, int columnas) {
		return null;
	}
	@Override
	public Movimiento creaMovimiento(int fila, int columna, Ficha color){
		return new MovimientoComplica(columna, color);
	}
	@Override
	public Jugador creaJugadorAleatorio() {
		return new JugadorAleatorioComplica();
	}
	@Override
	public Jugador creaJugadorHumano(Scanner sc) {
		return new JugadorHumanoComplica(sc);
	}
}