package jugador;

import java.util.ArrayList;

import logica.Ficha;
import logica.Tablero;
import movimiento.Movimiento;
import movimiento.MovimientoReversi;

public class JugadorAleatorioReversi extends Jugador{
	@Override
	public void obtenFilaColumna(Tablero tablero, Ficha color){
		super.color = color;
		MovimientoReversi mov = new MovimientoReversi(super.color);
		mov.comprobarPosibles(tablero);
		ArrayList <Movimiento> fichasPosibles = mov.getFichasPosibles();
		
		if(fichasPosibles.size() != 0){
			int random = (int)(fichasPosibles.size() * Math.random());
			fila = fichasPosibles.get(random).getFila();
			columna = fichasPosibles.get(random).getColumna();
		}
		else{
			super.color = mov.turnoContrario(color);
			mov = new MovimientoReversi(super.color);
			mov.comprobarPosibles(tablero);
			fichasPosibles = mov.getFichasPosibles();
			
			if(fichasPosibles.size() != 0){
				int random = (int)(fichasPosibles.size() * Math.random());
				fila = fichasPosibles.get(random).getFila();
				columna = fichasPosibles.get(random).getColumna();
			}
		}
	}
}