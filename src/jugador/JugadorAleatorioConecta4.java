package jugador;

import reglas.ReglasConecta4;
import logica.Tablero;
import logica.Ficha;

public class JugadorAleatorioConecta4 extends Jugador {
	@Override
	public void obtenFilaColumna(Tablero tablero, Ficha color){
		super.color = color;
		boolean fin = false;
		int columna = 0;
		while(!fin){
			columna = (int)(ReglasConecta4.getColumnas() * Math.random());
			if (tablero.getFicha(0, columna) == Ficha.VACIA)
				fin = true;
		}
		this.columna = columna;
	}
}