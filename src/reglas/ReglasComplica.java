package reglas;

import logica.Ficha;
import logica.Tablero;

/** Clase Reglas Complica*/
public class ReglasComplica extends ReglasJuego {
	public static final int FILAS = 7;
	public static final int COLUMNAS = 4;
	
	/** Inicia el tablero a 0
	 * @return tablero inicado
	 */		
	@Override
	public Tablero iniciaTablero(){
		return new Tablero(FILAS,COLUMNAS);
	}
	
	@Override
	public Ficha jugadorInicial(){
		return Ficha.BLANCA;
	}
	
	/** Determina si hay un ganador 
	 * @param fila
	 * @param columna
	 * @param ultimo, ultimo tuno que se ha efectuado
	 * @param tablero
	 * @return ganador
	 */
	@Override
	public Ficha hayGanador(int fila, int columna, Ficha ultimo, Tablero tablero){
		boolean encontrado = false;
		Ficha ganador = Ficha.VACIA;
		int i = 0;
		Ficha primeraEncontrada = Ficha.VACIA;
		Ficha segundaEncontrada = Ficha.VACIA;

		if(fila > 0){   //Si hay hueco en la columna se añade la ficha y se comprueban grupos para esa posicion
			encontrado = ReglasJuegoCuatroEnLinea.cuatroEnLinea(fila, columna, ultimo, tablero);
			if (encontrado)
				ganador = ultimo;
		}
		else{	   //Si no hay hueco se añade la ficha y se comprueban grupos para toda la columna
			while(i<ReglasComplica.FILAS && segundaEncontrada == Ficha.VACIA){
				encontrado = ReglasJuegoCuatroEnLinea.cuatroEnLinea(i, columna, ultimo, tablero);
				if(encontrado && primeraEncontrada == Ficha.VACIA)
					primeraEncontrada = tablero.getFicha(i, columna);
				else if(encontrado && primeraEncontrada != Ficha.VACIA)
					segundaEncontrada = tablero.getFicha(i, columna);
				
				i++;
			}
			if(primeraEncontrada != Ficha.VACIA){
				if(primeraEncontrada == segundaEncontrada || segundaEncontrada == Ficha.VACIA){
					encontrado = true;
					ganador = primeraEncontrada;
				}
				else
					encontrado = false;
			}
			else
				encontrado = false;
		}
		return ganador;
	}
	
	/** Determina tablas, en el Complica nunca se puede dar el caso de tablas
	 * @param tablero
	 * @return tablas
	 */	
	@Override
	public boolean tablas(Tablero tablero){
		boolean tablas = false;
		//En el Complica NUNCA se puede dar el caso de que la partida termine en tablas
		return tablas;
	}
	
	public static int getColumnas(){
		return COLUMNAS;
	}
}