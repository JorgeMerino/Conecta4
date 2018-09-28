package comandos;

import control.ControladorConsola;
import excepciones.ErrorDeEjecucion;

public class Poner implements Comando {
	@Override
	public void execute(ControladorConsola control) throws ErrorDeEjecucion{
		control.poner();
	}
	@Override
	public Comando parsea(String[] cadena) {
		if(cadena.length != 1)
			return null;
		else if(cadena[0].equalsIgnoreCase("PONER"))
			return new Poner();
		else
			return null;
	}	
	@Override
	public String textoAyuda() {
		return "PONER: Introduce la siguiente ficha." + System.getProperty("line.separator");
	}
}