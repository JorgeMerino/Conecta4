package excepciones;

public class ErrorDeEjecucion extends Throwable{
	public ErrorDeEjecucion(String mensaje) {
		super(mensaje);
	}
}