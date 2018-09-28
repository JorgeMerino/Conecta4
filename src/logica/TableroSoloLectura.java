package logica;

public interface TableroSoloLectura {
	int getFilas();
	int getColumnas();
	Ficha getFicha(int fila, int columnas);
	@Override
	String toString();
}
