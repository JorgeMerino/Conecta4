package reglas;

import logica.Ficha;
import logica.Tablero;

/** Clase Reglas Juego, establece las normas de cada tipo de juego*/
public abstract class ReglasJuego {	
	/** Inicia el tablero a 0
	 * @return tablero inicado
	 */
	public abstract Tablero iniciaTablero();
	
	/** Establece el Jugador Inicial
	 * @return Ficha.BLANCA, por defecto
	 */
	public abstract Ficha jugadorInicial();
	
	/** Determina si hay un ganador 
	 * @param fila
	 * @param columna
	 * @param ultimo, ultimo tuno que se ha efectuado
	 * @param tablero
	 * @return ganador
	 */
	public abstract Ficha hayGanador(int fila, int columna, Ficha ultimo, Tablero tablero);
	
	/** Determina si el juego termina en tablas (solo en modo Conecta 4)
	 * @param t
	 * @return tablas
	 */
	public abstract boolean tablas(Tablero tablero);
	
	/** Cambia de turno
	 * @param ultimo
	 * @return siguiente turno
	 */
	public Ficha siguienteTurno(Ficha ultimo){
		Ficha siguiente;
		switch(ultimo){
			case BLANCA:
				siguiente = Ficha.NEGRA;
			break;
			default:
				siguiente = Ficha.BLANCA;
			break;
		}
		return siguiente;
	}
}