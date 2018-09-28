package comandos;

import control.ControladorConsola;
import excepciones.ErrorDeEjecucion;

public interface Comando {
	void execute(ControladorConsola control) throws ErrorDeEjecucion;
	Comando parsea(String[] cadena);
	String textoAyuda();
}