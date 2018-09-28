package reglas;

import logica.Ficha;
import logica.Tablero;

/** Clase Reglas Gravity*/
public class ReglasGravity extends ReglasJuego {
	public static int FILAS = 10;
	public static int COLUMNAS = 10;
	
	public ReglasGravity(int filas, int columnas){
		ReglasGravity.FILAS = filas;
		ReglasGravity.COLUMNAS = columnas;
	}
	public ReglasGravity(){
		ReglasGravity.FILAS = 10;
		ReglasGravity.COLUMNAS = 10;
	}
	
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
	
	public static int getFilas(){
		return FILAS;
	}
	
	public static int getColumnas(){
		return COLUMNAS;
	}
}