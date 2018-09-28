package movimiento;

import excepciones.MovimientoInvalido;
import reglas.ReglasGravity;
import logica.Coordenadas;
import logica.Ficha;
import logica.Tablero;

/** Clase Movimiento Gravity*/
public class MovimientoGravity extends Movimiento {
	Coordenadas [] coord = {Coordenadas.NORTE, Coordenadas.SUR, Coordenadas.ESTE, Coordenadas.OESTE};
	
	/** Constructora de MovimientoConecta4
	 * @param columna
	 * @param turno
	 */
	public MovimientoGravity(int fila, int columna, Ficha turno){
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
		int cont = 0;
		int [] distancia = {fila, ReglasGravity.FILAS-1 - fila,ReglasGravity.COLUMNAS-1 - columna, columna};
		
		if(columna>=0 && columna<ReglasGravity.COLUMNAS && fila>=0 && fila<ReglasGravity.FILAS){  //Comprueba que la fila y la columna sea correcta
			distancia = ordenarInsercion(distancia);
			for(int i=0; i<4; i++){
				if(distancia[i] == distancia[0]){
					cont++;
				}
			}
			
			if(cont == 1){
				if(coord[0] == Coordenadas.NORTE){
					fila = desplazaNorte(tablero);
				}
				else if(coord[0] == Coordenadas.SUR){
					fila = desplazaSur(tablero);
				}
				else if(coord[0] == Coordenadas.ESTE){
					columna = desplazaEste(tablero);
				}
				else if(coord[0] == Coordenadas.OESTE){
					columna = desplazaOeste(tablero);
				}
				//Si solo hay un margen cercano se aproxima hacia él
			}
			else if(cont == 2){
				if(coord[0] == Coordenadas.NORTE && coord[1] == Coordenadas.ESTE){
					desplazarNorEste(tablero);
				}
				else if(coord[0] == Coordenadas.NORTE && coord[1] == Coordenadas.OESTE){
					desplazarNorOeste(tablero);
				}
				else if(coord[0] == Coordenadas.SUR && coord[1] == Coordenadas.ESTE){
					desplazarSurEste(tablero);				
				}
				else if(coord[0] == Coordenadas.SUR && coord[1] == Coordenadas.OESTE){
					desplazarSurOeste(tablero);
				}
				//El resto de casos no se contabilizan, ya que si NORTE == SUR ó ESTE == OESTE quiere decir que son
				//equidistantes y por tanto una fuerza anula a la otra
			}
			else if(cont == 3){
				if(coord[0] == Coordenadas.NORTE && coord[1] == Coordenadas.ESTE && coord[2] == Coordenadas.OESTE){
					fila = desplazaNorte(tablero);
				}
				else if(coord[0] == Coordenadas.NORTE && coord[1] == Coordenadas.SUR && coord[2] == Coordenadas.ESTE){
					columna = desplazaEste(tablero);				
				}
				else if(coord[0] == Coordenadas.SUR && coord[1] == Coordenadas.ESTE && coord[2] == Coordenadas.OESTE){
					fila = desplazaSur(tablero);
				}
				else if(coord[0] == Coordenadas.NORTE && coord[1] == Coordenadas.SUR && coord[2] == Coordenadas.OESTE){
					columna = desplazaOeste(tablero);
				}
				//Cuando tres fuerzas son iguales, las dos que se encuentran enfrentadas, se anulan
				//Por ejemplo, en el primer casos ESTE anula a OESTE, y por tanto la ficha se mueve hacia el NORTE
				//El orden de las fichas en el array influye, ya que al odenar lo hacemos hasta el (<) y no (<=), de tal
				//manera que para dos o más valores iguales siempre conservarán con el siguiente orden: N, S, E, O
			}
			
			// Si el contador es cuatro quiere decir que es equidistante a todos los margenes, 
			// por lo que está en el centro y no se desplazará
			
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

    public int [] ordenarInsercion(int [] distancia){
    	int j, auxDist;
    	Coordenadas auxCoord;
        for (int i = 1; i < distancia.length; i++) {
        	auxDist = distancia[i];
        	auxCoord = coord[i];
        	j = i;
            while (j > 0 && distancia[j - 1] > auxDist) {
            	distancia[j] = distancia[j - 1];
            	coord[j] = coord[j-1];
            	j--;
            }
            distancia[j] = auxDist;
            coord[j] = auxCoord;
        }
        return distancia;
    }	
	
	private int desplazaNorte(Tablero tablero){
		int i = fila;
		while(i>0 && tablero.getFicha(i-1, columna).equals(Ficha.VACIA)){
			i--;
		}
		return i;
	}
	private int desplazaSur(Tablero tablero){
		int i = fila;
		while(i<(ReglasGravity.FILAS-1) && tablero.getFicha(i+1, columna).equals(Ficha.VACIA)){
			i++;
		}
		return i;
	}
	private int desplazaEste(Tablero tablero){
		int j = columna;
		while(j<(ReglasGravity.COLUMNAS-1) && tablero.getFicha(fila, j+1).equals(Ficha.VACIA)){
			j++;
		}
		return j;
	}
	private int desplazaOeste(Tablero tablero){
		int j = columna;
		while(j>0 && tablero.getFicha(fila, j-1).equals(Ficha.VACIA)){
			j--;
		}
		return j;
	}
	private void desplazarNorEste(Tablero tablero){
		int i = fila, j = columna;
		while(i>0 && j<(ReglasGravity.COLUMNAS-1) && tablero.getFicha(i-1, j+1).equals(Ficha.VACIA)){
			i--;
			j++;
		}
		fila = i;
		columna = j;
	}
	private void desplazarNorOeste(Tablero tablero){
		int i = fila, j = columna;
		while(i>0 && j>0 && tablero.getFicha(i-1, j-1).equals(Ficha.VACIA)){
			i--;
			j--;
		}
		fila = i;
		columna = j;		
	}
	private void desplazarSurEste(Tablero tablero){
		int i = fila, j = columna;
		while(i<(ReglasGravity.FILAS-1) && j<(ReglasGravity.COLUMNAS-1) && tablero.getFicha(i+1, j+1).equals(Ficha.VACIA)){
			i++;
			j++;
		}
		fila = i;
		columna = j;
	}
	private void desplazarSurOeste(Tablero tablero){
		int i = fila, j = columna;
		while(i<(ReglasGravity.FILAS-1) && j>0 && tablero.getFicha(i+1, j-1).equals(Ficha.VACIA)){
			i++;
			j--;
		}
		fila = i;
		columna = j;
	}
}