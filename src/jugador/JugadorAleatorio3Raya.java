package jugador;

import reglas.Reglas3Raya;
import logica.Ficha;
import logica.Tablero;

public class JugadorAleatorio3Raya extends Jugador{
	@Override
	public void obtenFilaColumna(Tablero tablero, Ficha color){
		super.color = color;
		boolean fin = false;
		int columna = 0;
		int fila = 0;
		while(!fin){
			columna = (int)(Reglas3Raya.getColumnas() * Math.random());
			fila = (int)(Reglas3Raya.getFilas() * Math.random());
			if (tablero.getFicha(fila, columna) == Ficha.VACIA)
				fin = true;
		}
		this.fila = fila;
		this.columna = columna;
	}
}