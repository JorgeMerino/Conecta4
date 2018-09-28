package excepciones;

public class DatosIncorrectos extends Throwable{
	public DatosIncorrectos(String mensaje) {
		super(mensaje);
	}
}