package comandos;

import control.ControladorConsola;
import excepciones.ErrorDeEjecucion;

/**Clase Comando Ayuda*/
public class Ayuda implements Comando {
	/**Ejecuta el comando ayuda*/
	@Override
	public void execute(ControladorConsola control) throws ErrorDeEjecucion {
		control.ayuda();
	}
	
	/**Parsea el comando*/
	@Override
	public Comando parsea(String[] cadena) {
		if(cadena.length != 1)
			return null;
		else if(cadena[0].equalsIgnoreCase("AYUDA"))
			return new Ayuda();
		else
			return null;
	}
	
	/**Muestra la ayuda del comando*/
	@Override
	public String textoAyuda() {
		return "AYUDA: Muestra los comandos del juego." + System.getProperty("line.separator");
	}
}