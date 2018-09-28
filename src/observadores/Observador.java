package observadores;

import excepciones.MovimientoInvalido;
import logica.Ficha;
import logica.TableroSoloLectura;

public interface Observador {
	//Gesti�n Partida
	public void onReset(TableroSoloLectura tab, Ficha turno);
	public void onPartidaTerminada(TableroSoloLectura tablero, Ficha ganador);
	public void onCambioJuego(TableroSoloLectura tab, Ficha turno);
	
	//Gesti�n Movimientos
	public void onUndoNotPossible();
	public void onUndo(TableroSoloLectura tablero, Ficha turno, boolean hayMas);
	public void onMovimientoStart(Ficha turno);
	public void onMovimientoEnd(TableroSoloLectura tablero, Ficha turno);
	public void onMovimientoIncorrecto(MovimientoInvalido movimientoException) throws MovimientoInvalido;
	public void onExit();
}