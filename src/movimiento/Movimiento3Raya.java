package movimiento;

import excepciones.MovimientoInvalido;
import reglas.Reglas3Raya;
import logica.Ficha;
import logica.Tablero;

/** Clase Movimiento 3Raya*/
public class Movimiento3Raya extends Movimiento {
	/** Constructora de MovimientoConecta4
	 * @param columna
	 * @param turno
	 */
	public Movimiento3Raya(int fila, int columna, Ficha turno){
		super(columna, turno);
		this.fila = fila;
	}

	/** Ejecuta un movimiento
	 * @param tablero
	 * @return encontrado, si ha encontrado un grupo
	 */
	@Override
	public void ejecutaMovimiento(Tablero tablero)throws MovimientoInvalido{
		boolean valido = movimientoValido(tablero);
		if(valido){
			tablero.ponFicha(fila, columna, turno);   //Ponemos la ficha
		}
		else
			tablero.ponFicha(-1, -1, turno);  // Si no es valido sacamos la ficha del tablero para que salte la excepción
	}

	/** Deshace el ultimo movimiento
	 * @param tablero
	 */
	@Override
	public void undo(Tablero tablero){
		tablero.ponFicha(fila, columna, Ficha.VACIA);
	}
	
	/** Comprueba la validez del movimiento
	 * @param tablero
	 * @return valido
	 */
	@Override
	protected boolean movimientoValido(Tablero tablero){
		boolean valido = false;
		
		if(columna>=0 && columna<Reglas3Raya.COLUMNAS && fila>=0 && fila<Reglas3Raya.FILAS){  //Comprueba que la fila y la columna sea correcta
			if(tablero.getFicha(fila, columna) == Ficha.VACIA)  //Comprueba que la posición esté vacía
				valido = true;
		}

		return valido;
	}	
	
	/** Busca la fila en la que se ha de colocar una ficha, para una determinada columna
	 * @param columna, en la que vamos a buscar posicion para la ficha
	 * @return fila, dada la columna, que corresponde a una ficha determinada
	 */
	@Override
	protected int buscarFila(int columna, Tablero tablero){
		return this.fila;
	}
}