package movimiento;

import excepciones.MovimientoInvalido;
import reglas.ReglasComplica;
import logica.Ficha;
import logica.Tablero;

/** Clase Movimiento Complica*/
public class MovimientoComplica extends Movimiento {
	private Ficha fichaPerdida;
	private boolean perdidaOk;
	
	/** Constructora de MovimientoComplica
	 * @param columna
	 * @param turno
	 */
	public MovimientoComplica(int columna, Ficha turno){
		super(columna, turno);
		fila = 0;
		fichaPerdida = Ficha.VACIA;
		perdidaOk = false;
	}

	/** Ejecuta un movimiento
	 * @param tablero
	 * @return encontrado, si ha encontrado un grupo
	 */
	@Override
	public void ejecutaMovimiento(Tablero tablero)throws MovimientoInvalido{
		boolean valido = movimientoValido(tablero);
		if(valido){
			if(fila >= 0)   //Si hay hueco en la columna se añade la ficha y se comprueban grupos para esa posicion
				tablero.ponFicha(fila, columna, turno);   //Ponemos la ficha
			else{	   //Si no hay hueco se añade la ficha y se comprueban grupos para toda la columna
				perdidaOk = true;
				fila = 0;    //Colocamos la ficha en la cima
				tablero.ponFicha(fila, columna, turno);   //Ponemos la ficha
			}
		}
	}
	
	/** Deshace el ultimo movimiento
	 * @param tablero
	 */	
	@Override
	public void undo(Tablero tablero){
		if(!perdidaOk)    //Si la ficha se puso sobre vacía se recupera el hueco
			tablero.ponFicha(fila, columna, Ficha.VACIA);
		else{     //Si la ficha se puso sobreescribiendo otra se mueve toda la columna hacia arriba
			for(int i=0; i<(ReglasComplica.FILAS-1); i++)
				tablero.ponFicha(i, columna, tablero.getFicha(i+1, columna));
			
			tablero.ponFicha(ReglasComplica.FILAS-1, columna, fichaPerdida);   //Y recuperamos la ficha perdida
		}	
	}

	/** Comprueba la validez del movimiento y si hay una columna llena, la desplaza hacia abajo una posición
	 * @param tablero
	 * @return valido
	 */
	@Override
	protected boolean movimientoValido(Tablero tablero){
		boolean valido = false;
		if(columna>=0 && columna<ReglasComplica.COLUMNAS){  //Comprueba que la columna sea correcta
			valido = true;
			fila = buscarFila(columna, tablero);  //Dada una columna, busca y comprueba la fila conveniente
			if(fila < 0){
				//Guardamos la ficha que se va a perder en fichaPerdida
				fichaPerdida = tablero.getFicha(ReglasComplica.FILAS-1, columna);
				
				//Bajamos todas las fichas de la columna una posicion
				for(int i=(ReglasComplica.FILAS-1); i>0; i--)
					tablero.ponFicha(i, columna, tablero.getFicha(i-1, columna));
				
				//Y se vacía la cima
				tablero.ponFicha(0, columna, Ficha.VACIA);
			}
		}
		
		return valido;
	}
	
	/** Busca la fila en la que se ha de colocar una ficha, para una determinada columna
	 * @param columna, en la que vamos a buscar posicion para la ficha
	 * @return fila, dada la columna, que corresponde a una ficha determinada
	 */
	@Override
	protected int buscarFila(int columna, Tablero tablero){
		int i = ReglasComplica.FILAS;
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