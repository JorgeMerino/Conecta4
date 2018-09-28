package reglas;

import logica.Ficha;
import logica.Tablero;

/** Clase Reglas 3 Raya*/
public class Reglas3Raya extends ReglasJuego {
	public static int FILAS = 3;
	public static int COLUMNAS = 3;
	
	public Reglas3Raya(int filas, int columnas){
		Reglas3Raya.FILAS = filas;
		Reglas3Raya.COLUMNAS = columnas;
	}
	public Reglas3Raya(){
		Reglas3Raya.FILAS = 3;
		Reglas3Raya.COLUMNAS = 3;
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
		
		encontrado = ReglasJuegoTresEnLinea.tresEnLinea(fila, columna, ultimo, tablero);  //Y buscamos si se forma grupo
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