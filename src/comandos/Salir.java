package comandos;

import control.ControladorConsola;
import excepciones.ErrorDeEjecucion;

public class Salir implements Comando {	
	@Override
	public void execute(ControladorConsola control) throws ErrorDeEjecucion{
		control.salir();
	}
	@Override
	public Comando parsea(String[] cadena) {
		if(cadena.length != 1) 
			return null;
		else if(cadena[0].equalsIgnoreCase("SALIR"))
			return new Salir();
		else
			return null;
	}
	@Override
	public String textoAyuda() {
		return "SALIR: Termina la aplicación." + System.getProperty("line.separator");
	}
}