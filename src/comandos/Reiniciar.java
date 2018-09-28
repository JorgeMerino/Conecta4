package comandos;

import control.ControladorConsola;
import excepciones.ErrorDeEjecucion;

public class Reiniciar implements Comando {
	@Override
	public void execute(ControladorConsola control) throws ErrorDeEjecucion {
		control.reiniciar();
	}
	@Override
	public Comando parsea(String[] cadena) {
		if(cadena.length != 1)
			return null;
		else if(cadena[0].equalsIgnoreCase("REINICIAR"))
			return new Reiniciar();
		else
			return null;
	}
	@Override
	public String textoAyuda() {
		return "REINICIAR: Resetea el juego." + System.getProperty("line.separator");
	}	
}