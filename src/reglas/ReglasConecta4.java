package reglas;

import logica.Ficha;
import logica.Tablero;

/** Clase Reglas Conecta 4*/
public class ReglasConecta4 extends ReglasJuego {
	public static final int FILAS = 6;
	public static final int COLUMNAS = 7;
	
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
		boolean encontrado;
		Ficha ganador = Ficha.VACIA;
		
		encontrado = ReglasJuegoCuatroEnLinea.cuatroEnLinea(fila, columna, ultimo, tablero);  //Y buscamos si se forma grupo
		if(encontrado)
			ganador = ultimo;
		
		return ganador;
	}
	
	/** Determina si el juego termina en tablas
	 * @param tablero
	 * @return tablas
	 */
	@Override
	public boolean tablas(Tablero tablero){
		boolean tablas = tablero.completo();
		return tablas;
	}
	
	public static int getColumnas(){
		return COLUMNAS;
	}	
}