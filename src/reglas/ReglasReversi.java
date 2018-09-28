package reglas;

import logica.Ficha;
import logica.Tablero;

public class ReglasReversi extends ReglasJuego{
	public static int FILAS = 8;
	public static int COLUMNAS = 8;
	
	@Override
	public Tablero iniciaTablero() {
		Tablero tablero = new Tablero(FILAS,COLUMNAS);
		//Añadimos fichas iniciales (inicio = (0, 0))
		tablero.ponFicha(3, 3, Ficha.BLANCA);
		tablero.ponFicha(3, 4, Ficha.NEGRA);
		tablero.ponFicha(4, 3, Ficha.NEGRA);
		tablero.ponFicha(4, 4, Ficha.BLANCA);
		
		return tablero;
	}
	
	@Override
	public Ficha jugadorInicial(){
		return Ficha.NEGRA;
	}

	@Override
	public Ficha hayGanador(int fila, int columna, Ficha ultimo, Tablero tablero){
		int contBlancas = 0, contNegras = 0;
		Ficha ganador = Ficha.VACIA;
		
		if(tablero.completo()){
			for(int i=0; i<FILAS; i++){
				for(int j=0; j<COLUMNAS; j++){
					if(tablero.getFicha(i, j) == Ficha.BLANCA)
						contBlancas++;
					if(tablero.getFicha(i, j) == Ficha.NEGRA)
						contNegras++;
				}
			}
		}
		
		if(contBlancas > contNegras)
			ganador = Ficha.BLANCA;
		else if(contNegras > contBlancas)
			ganador = Ficha.NEGRA;
		
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