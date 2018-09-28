package jugador;

import java.util.InputMismatchException;
import factorias.FactoriaJuego;
import movimiento.Movimiento;
import logica.Tablero;
import logica.Ficha;
import excepciones.DatosIncorrectos;

public abstract class Jugador {
	protected int fila;
	protected int columna;
	protected Ficha color;
	protected abstract void obtenFilaColumna(Tablero tablero, Ficha color);
	public Movimiento getMovimiento(FactoriaJuego factoria,	Tablero tab, Ficha color) throws DatosIncorrectos {
		try{
			this.obtenFilaColumna(tab,color);
			return factoria.creaMovimiento(this.fila, this.columna, this.color);
		}
		catch (InputMismatchException e){
			throw new DatosIncorrectos("Los datos introducidos no son numericos.");
		}
	}
}