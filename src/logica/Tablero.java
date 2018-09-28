package logica;

/** Clase Tablero, lleva las funciones relativas a la gestión del tablero*/
public class Tablero implements TableroSoloLectura{
	private int filas;
	private int columnas;
	private Ficha[][] tablero;
	
	/** Constructora de Tablero
	 * @param numFilas es inicializado
	 * @param numColumnas es inicializado.
	 * Con numFilas y numColumnas genera el tablero
	 */
	public Tablero(int numFilas, int numColumnas){
		this.filas = numFilas;
		this.columnas = numColumnas;
		tablero = new Ficha[this.filas][this.columnas];
		iniciar();
	}
	
	/** Inicia el tablero a VACIA*/
	public void iniciar(){
		for(int i=0; i<filas; i++){
			for(int j=0; j<columnas; j++)
				tablero[i][j] = Ficha.VACIA;
		}	
	}
	
	/** Pone una ficha, en una posicion determinada
	 * @param fila, Parametro fila
	 * @param columna, Parametro columna
	 * @param ficha, la cual se asigna a la posicion tablero[fila][columna]
	 */
	public void ponFicha(int fila, int columna, Ficha ficha){
		tablero[fila][columna] = ficha;		
	}
	
	/** Devuelve el valor de una posición del tablero
	 * @param fila, Parametro fila
	 * @param columna, Parametro columna
	 * @return tablero[fila][columna], con el valor de ficha que le corresponda
	 */
	@Override
	public Ficha getFicha(int fila, int columna){
		return tablero[fila][columna];
	}
	
	@Override
	public int getFilas(){
		return filas;
	}
	
	@Override
	public int getColumnas(){
		return columnas;
	}
	
	/** Comprueba que el tablero esté completo
	 * @return completo, inicando si el tablero está completo o no
	 */
	public boolean completo(){
		boolean completo = true;
		int i, j;
		
		i=0;
		while(i<filas && completo){
			j = 0;
			while(j<columnas && completo){
				if(tablero[i][j].equals(Ficha.VACIA)){
					completo = false;
				}
				j++;
			}
			i++;
		}
		
		return completo;
	}
	
	/** Da un valor tipo String al enumerado de Ficha
	 * @param color, con el cual asignaremos su letra
	 * @return letraFicha, con la letra distintica de cada ficha
	 */
    public String evaluarFicha (Ficha color) {
    	String letraFicha;
    	
		 switch(color) {
			 case VACIA: 
				 letraFicha = "O";
			 break;
			 case BLANCA: 
				 letraFicha = "B";
			 break;
			 default: 
				 letraFicha = "N";
			 break;
		 }
		 
		 return letraFicha;
    }
	
    /** Función donde se retorna la matriz que Controlador mostrará por pantalla
     * @return matrizImpresa, con el String de todo del tablero
     */
	@Override
	public String toString() {
		String matrizImpresa = "";
		String letraFicha;
		
		//Metemos cada elemento de la matriz a matrizImpresa
		for(int i=0; i<filas; i++){
			for(int j=0; j<columnas; j++){
				letraFicha = evaluarFicha(tablero[i][j]);
				matrizImpresa = matrizImpresa + letraFicha;
				if(j<columnas-1)
					matrizImpresa = matrizImpresa + " ";
			}
			//Introducimos salto de linea al final de cada fila
			matrizImpresa = matrizImpresa + System.getProperty("line.separator");
		}
		
		//Insertamos una linea de fin de tablero
		for(int j=0; j<columnas; j++){
			matrizImpresa = matrizImpresa + "-";
			if(j<columnas-1)
					matrizImpresa = matrizImpresa + "-";
		}
		matrizImpresa = matrizImpresa + System.getProperty("line.separator");
		
		//Insertamos los numeros correspondientes a las columnas, de uno a MAX
		for(int j=0; j<columnas; j++){
			matrizImpresa = matrizImpresa + (j+1);
			if(j<columnas-1)
				matrizImpresa = matrizImpresa + " ";
		}
		matrizImpresa = matrizImpresa + System.getProperty("line.separator");
		
		return matrizImpresa;
	}
}