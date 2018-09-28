package factorias;

import java.util.Scanner;
import reglas.ReglasJuego;
import reglas.ReglasConecta4;
import movimiento.Movimiento;
import movimiento.MovimientoConecta4;
import jugador.Jugador;
import jugador.JugadorAleatorioConecta4;
import jugador.JugadorHumanoConecta4;
import logica.Ficha;

public class FactoriaJuegoConecta4 implements FactoriaJuego {
	@Override
	public ReglasJuego creaReglas() {
		return new ReglasConecta4();
	}
	@Override
	public ReglasJuego creaReglas(int filas, int columnas) {
		return null;
	}
	@Override
	public Movimiento creaMovimiento(int fila, int columna, Ficha color){
		return new MovimientoConecta4(columna, color);
	}
	@Override
	public Jugador creaJugadorAleatorio() {
		return new JugadorAleatorioConecta4();
	}
	@Override
	public Jugador creaJugadorHumano(Scanner sc) {
		return new JugadorHumanoConecta4(sc);
	}	
}