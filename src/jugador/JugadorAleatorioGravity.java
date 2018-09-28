package jugador;

import reglas.ReglasGravity;
import logica.Ficha;
import logica.Tablero;

public class JugadorAleatorioGravity extends Jugador{
	@Override
	public void obtenFilaColumna(Tablero tablero, Ficha color){
		super.color = color;
		boolean fin = false;
		int columna = 0;
		int fila = 0;
		while(!fin){
			columna = (int)(ReglasGravity.getColumnas() * Math.random());
			fila = (int)(ReglasGravity.getFilas() * Math.random());
			if (tablero.getFicha(fila, columna) == Ficha.VACIA)
				fin = true;
		}
		this.fila = fila;
		this.columna = columna;
	}
}