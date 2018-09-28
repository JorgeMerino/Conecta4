package movimiento;

import excepciones.MovimientoInvalido;
import reglas.ReglasConecta4;
import logica.Ficha;
import logica.Tablero;

/** Clase Movimiento Conecta 4*/
public class MovimientoConecta4 extends Movimiento {
	/** Constructora de MovimientoConecta4
	 * @param columna
	 * @param turno
	 */
	public MovimientoConecta4(int columna, Ficha turno){
		super(columna, turno);
		fila = 0;
	}

	/** Ejecuta un movimiento
	 * @param tablero
	 * @return encontrado, si ha encontrado un grupo
	 */
	@Override
	public void ejecutaMovimiento(Tablero tablero)throws MovimientoInvalido{
		boolean valido = movimientoValido(tablero);
		if(valido)
			tablero.ponFicha(fila, columna, turno);   //Ponemos la ficha
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
		if(columna>=0 && columna<ReglasConecta4.COLUMNAS){  //Comprueba que la columna sea correcta
			fila = buscarFila(columna, tablero);  //Dada una columna, busca y comprueba la fila conveniente
			if(fila >= 0)
				valido = true;
			else
				valido = false;
		}
		
		return valido;
	}
	
	/** Busca la fila en la que se ha de colocar una ficha, para una determinada columna
	 * @param columna, en la que vamos a buscar posicion para la ficha
	 * @return fila, dada la columna, que corresponde a una ficha determinada
	 */
	@Override
	protected int buscarFila(int columna, Tablero tablero){
		int i = ReglasConecta4.FILAS;
		Ficha miFicha;
		//Hacemos un bucle inverso desde MAX-1 hasta 0, en cuanto encuenta una vacia i se para
		do{			
			i--;
			if(i>=0){
				miFicha = tablero.getFicha(i, columna);
			}
			else
				miFicha = Ficha.VACIA;
		}while(!miFicha.equals(Ficha.VACIA));
		
		return i;
	}
}