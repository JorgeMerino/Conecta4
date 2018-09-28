package comandos;

import control.ControladorConsola;
import excepciones.ErrorDeEjecucion;

public class Deshacer implements Comando {
	@Override
	public void execute(ControladorConsola control) throws ErrorDeEjecucion{
		control.deshacer();
	}
	@Override
	public Comando parsea(String[] cadena) {
		if(cadena.length != 1)
			return null;
		else if(cadena[0].equalsIgnoreCase("DESHACER"))
			return new Deshacer();
		else
			return null;
	}
	@Override
	public String textoAyuda() {
		return "DESHACER: Deshacer el último movimiento." + System.getProperty("line.separator");
	}
}