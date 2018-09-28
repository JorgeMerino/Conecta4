package comandos;

import control.ControladorConsola;
import factorias.FactoriaJuego;
import factorias.FactoriaJuego3Raya;
import factorias.FactoriaJuegoComplica;
import factorias.FactoriaJuegoConecta4;
import factorias.FactoriaJuegoGravity;
import factorias.FactoriaJuegoReversi;
import excepciones.ErrorDeEjecucion;

public class Jugar implements Comando {
	private static FactoriaJuego factoria;
	private static int filas;
	private static int columnas;
	
	public Jugar(){	}
	
	public Jugar(FactoriaJuego factoria) {
		Jugar.factoria = factoria;
	}
	
	@Override
	public void execute(ControladorConsola control) throws ErrorDeEjecucion{
		control.jugar();
	}
	
	@Override
	public Comando parsea(String[] cadena) {
		if(cadena.length != 2 && cadena.length != 4)
			return null;
		else if(cadena[0].equalsIgnoreCase("JUGAR")){
			filas = 0;
			columnas = 0;
			if(cadena[1].equalsIgnoreCase("C4"))
				return new Jugar(new FactoriaJuegoConecta4());
			else if(cadena[1].equalsIgnoreCase("CO"))
				return new Jugar(new FactoriaJuegoComplica());
			else if(cadena[1].equalsIgnoreCase("GR")){
				if(cadena.length == 4){
					filas = Integer.parseInt(cadena[2]);
					columnas = Integer.parseInt(cadena[3]);
				}
				return new Jugar(new FactoriaJuegoGravity());
			}
			else if(cadena[1].equalsIgnoreCase("RV"))
				return new Jugar(new FactoriaJuegoReversi());
			else if(cadena[1].equalsIgnoreCase("TR")){
				if(cadena.length == 4){
					filas = Integer.parseInt(cadena[2]);
					columnas = Integer.parseInt(cadena[3]);
				}
				return new Jugar(new FactoriaJuego3Raya());
			}
			else
				return null;
		}
		else
			return null;
	}
	
	@Override
	public String textoAyuda() {
		return "JUGAR [c4|co|gr] [tamX tamY]: Cambia el tipo de juego." + System.getProperty("line.separator");
	}
	
	public static FactoriaJuego getFactoria(){
		return factoria;
	}
	
	public static int getFilas(){
		return filas;
	}
	
	public static int getColumnas(){
		return columnas;
	}
}