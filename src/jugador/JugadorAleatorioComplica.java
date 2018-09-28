package jugador;

import reglas.ReglasComplica;
import logica.Ficha;
import logica.Tablero;

public class JugadorAleatorioComplica extends Jugador{
	@Override
	public void obtenFilaColumna(Tablero tablero, Ficha color){
		super.color = color;
		int columna = (int)(ReglasComplica.getColumnas() * Math.random());
		this.columna = columna;
	}
}