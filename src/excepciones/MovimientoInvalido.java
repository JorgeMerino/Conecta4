package excepciones;

public class MovimientoInvalido extends Throwable {
	public MovimientoInvalido(String mensaje) {
		super(mensaje);
	}
}