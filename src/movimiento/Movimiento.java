package movimiento;

import logica.Ficha;
import logica.Tablero;
import excepciones.MovimientoInvalido;

/** Clase Movimiento, gestiona los movimientos que le pasa partida*/
public abstract class Movimiento {
	protected int columna;
	protected int fila;
	protected Ficha turno;
	
	/** Constructora de Movimiento
	 * @param columna
	 * @param turno
	 */
	public Movimiento(int columna, Ficha turno){
		this.columna = columna;
		this.turno = turno;
	}
	
	public Movimiento(Ficha turno){
		this.turno = turno;
	}
	
	/** Coge el jugador de ese momento
	 * @return turno, con el jugador pertinente
	 */
	public Ficha getJugador(){		
		return turno;
	}
	
	/** Coge la columna de ese momento
	 * @return columna, con la columna pertinente
	 */
	public int getColumna(){
		return columna;
	}

	/** Coge la fila de ese momento
	 * @return fila, con la fila pertinente
	 */
	public int getFila(){
		return fila;
	}
	
	/** Ejecuta un movimiento
	 * @param tablero
	 * @return encontrado, si ha encontrado un grupo
	 */
	public abstract void ejecutaMovimiento(Tablero tablero) throws MovimientoInvalido;
	
	/** Deshace el ultimo movimiento
	 * @param tablero
	 */
	public abstract void undo(Tablero tablero);
	
	/** Comprueba la validez del movimiento
	 * @param tablero
	 * @return valido
	 */
	protected abstract boolean movimientoValido(Tablero tablero);
	
	/** Busca la fila en la que se ha de colocar una ficha, para una determinada columna
	 * @param columna, en la que vamos a buscar posicion para la ficha
	 * @return fila, dada la columna, que corresponde a una ficha determinada
	 */
	protected abstract int buscarFila(int columna, Tablero tablero);
}