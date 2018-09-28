package reglas;

import logica.Ficha;
import logica.Tablero;

/** Clase Reglas Juego Cuatro en Linea, la cual busca grupos de cuatro*/
public class ReglasJuegoTresEnLinea {
	public static final int MAX_GRUPO = 3;
	
	/** Compueba grupos de cuatro, en horizontal, vertical y diagonal
	 * @param fila, Parametro fila
	 * @param columna, Parametro columna
	 * @return encontrado, en caso se haber encontrado un grupo de cuatro
	 */
	static public boolean tresEnLinea(int fila, int columna, Ficha turno, Tablero tablero){
		boolean encontrado = false;
		int cont;

		//Buscamos Grupos por filas
		cont = buscarFilas(fila, columna, tablero);
		if(cont == MAX_GRUPO){
			encontrado = true;
			turno =  tablero.getFicha(fila, columna);
		}
		else{
			//Buscamos Grupos por columnas
			cont = buscarColumnas(fila, columna, tablero);
			if(cont == MAX_GRUPO){
				encontrado = true;
				turno =  tablero.getFicha(fila, columna);
			}
			else{
				//Buscamos Grupos en diagonal (+, +)
				cont = buscarDiagonalDesc(fila, columna, tablero);
				if(cont == MAX_GRUPO){
					encontrado = true;
					turno =  tablero.getFicha(fila, columna);
				}
				else{
					//Buscamos Grupos en diagonal (+, -)
					cont = buscarDiagonalAsc(fila, columna, tablero);
					if(cont == MAX_GRUPO){
						encontrado = true;
						turno =  tablero.getFicha(fila, columna);
					}
				}
			}
		}
		return encontrado;
	}
	
	/** Busca grupos en horizontal
	 * @param fila, Parametro fila
	 * @param columna, Parametro columna
	 * @return cont, con el numero de elementos encontrados en el grupo
	 */
	static private int buscarFilas(int fila, int columna, Tablero tablero){
		int columnas, iniCol, finCol, cont;
		Ficha miFicha, fichaAux;
		
		cont = 1;
		columnas = tablero.getColumnas();
		iniCol = columna - 2;
		finCol = columna + 2;
				
		if(iniCol < 0)
			iniCol = 0;
		if(finCol > columnas-1)
			finCol = columnas-1;
		
		while(iniCol<finCol && cont<MAX_GRUPO){  //Nos aseguramos de que ini y fin no se crucen y de que la partida no este terminada
			miFicha = tablero.getFicha(fila, iniCol);
			fichaAux = tablero.getFicha(fila, iniCol+1);
			if(!miFicha.equals(fichaAux))  //Y comparamos de ini a fin
				cont = 1;
			else
				cont++;
			
			iniCol++;
		}	
		return cont;
	}
	
	/** Busca grupos en vertical
	 * @param fila, Parametro fila
	 * @param columna, Parametro columna
	 * @return cont, con el numero de elementos encontrados en el grupo
	 */
	static private int buscarColumnas(int fila, int columna, Tablero tablero){
		int filas, iniFila, finFila, cont;
		Ficha miFicha, fichaAux;
		
		cont = 1;
		filas = tablero.getFilas();
		iniFila = fila - 2;
		finFila = fila + 2;
		
		if(iniFila < 0)
			iniFila = 0;
		if(finFila > filas-1)
			finFila = filas-1;
		
		while(iniFila<finFila && cont<MAX_GRUPO){  //Nos aseguramos de que ini y fin no se crucen y de que la partida no este terminada
			miFicha = tablero.getFicha(iniFila, columna);
			fichaAux = tablero.getFicha(iniFila+1, columna);
			if(!miFicha.equals(fichaAux))  //Y comparamos de ini a fin
				cont = 1;
			else
				cont++;
			
			iniFila++;
		}
		return cont;
	}
	
	/** Busca grupos en diagonal, en sentido descendente
	 * @param fila, Parametro fila
	 * @param columna, Parametro columna
	 * @return cont, con el numero de elementos encontrados en el grupo
	 */
	static private int buscarDiagonalDesc(int fila, int columna, Tablero tablero){
		int filas, columnas, iniCol, iniFila, finCol, finFila, cont;
		Ficha miFicha, fichaAux;
		
		cont = 1;
		filas = tablero.getFilas();
		columnas = tablero.getColumnas();
		iniFila = fila - 2;
		iniCol = columna - 2;
		finFila = fila + 2;
		finCol = columna + 2;
		
		if(iniFila < 0){
			iniCol = iniCol - iniFila;
			iniFila = 0;
		}
		if(finFila > filas-1){
			finCol = finCol + (filas - 1 - finFila);
			finFila = filas-1;
		}
		
		if(iniCol < 0){
			iniFila = iniFila - iniCol;
			iniCol = 0;
		}
		if(finCol >columnas-1){
			finFila = finFila + (columnas - 1 - finCol);
			finCol = columnas-1;
		}
		
		while(iniCol<finCol && cont<MAX_GRUPO){  //Nos aseguramos de que ini y fin no se crucen y de que la partida no este terminada
			miFicha = tablero.getFicha(iniFila, iniCol);
			fichaAux = tablero.getFicha(iniFila+1, iniCol+1);
			if(!miFicha.equals(fichaAux))  //Y comparamos de ini a fin
				cont=1;
			else
				cont++;
			
			iniCol++;
			iniFila++;
		}
		return cont;
	}
	
	/** Busca grupos en diagonal, en sentido ascendente
	 * @param fila, Parametro fila
	 * @param columna, Parametro columna
	 * @return cont, con el numero de elementos encontrados en el grupo
	 */
	static private int buscarDiagonalAsc(int fila, int columna, Tablero tablero){
		int filas, columnas, iniCol, iniFila, finCol, finFila, cont;
		Ficha miFicha, fichaAux;
		
		cont = 1;
		filas = tablero.getFilas();
		columnas = tablero.getColumnas();
		iniFila = fila + 2;
		iniCol = columna - 2;
		finFila = fila - 2;
		finCol = columna + 2;
		
		if(finFila < 0){
			finCol = finCol + finFila;
			finFila = 0;
		}
		if(iniFila > filas-1){
			iniCol = iniCol + (iniFila - (filas - 1));
			iniFila = filas-1;
		}
		
		if(iniCol < 0){
			iniFila = iniFila + iniCol;
			iniCol = 0;
		}
		if(finCol > columnas-1){
			finFila = finFila - (columnas - 1 - finCol);
			finCol = columnas-1;
		}
		
		while(iniCol<finCol && cont<MAX_GRUPO){  //Nos aseguramos de que ini y fin no se crucen y de que la partida no este terminada
			miFicha = tablero.getFicha(iniFila, iniCol);
			fichaAux = tablero.getFicha(iniFila-1, iniCol+1);
			if(!miFicha.equals(fichaAux))  //Y comparamos de ini a fin
				cont=1;
			else
				cont++;
			
			iniCol++;
			iniFila--;
		}
		return cont;
	}
}