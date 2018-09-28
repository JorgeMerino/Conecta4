package comandos;

import control.ControladorConsola;
import logica.Ficha;
import logica.TipoJugador;

public class PonJugador implements Comando {
	private static Ficha color;
	private static TipoJugador tipoJugador;
	public PonJugador(Ficha color, TipoJugador tipoJugador){
		PonJugador.color = color;
		PonJugador.tipoJugador = tipoJugador;
	}
	
	public PonJugador(){ }
	
	@Override
	public void execute(ControladorConsola control) {
		control.ponjugador();
	}
	
	@Override
	public Comando parsea(String[] cadena) {
		if(cadena.length != 3)
			return null;
		else if(cadena[0].equalsIgnoreCase("PONJUGADOR")){
			if(cadena[1].equalsIgnoreCase("B")){
				if(cadena[2].equalsIgnoreCase("H")){
					return new PonJugador(Ficha.BLANCA, TipoJugador.HUMANO);
				}
				else if(cadena[2].equalsIgnoreCase("A")){
					return new PonJugador(Ficha.BLANCA, TipoJugador.AUTO);
				}
				else
					return null;
			}
			else if(cadena[1].equalsIgnoreCase("N")){
				if(cadena[2].equalsIgnoreCase("H")){
					return new PonJugador(Ficha.NEGRA, TipoJugador.HUMANO);
				}
				else if(cadena[2].equalsIgnoreCase("A")){
					return new PonJugador(Ficha.NEGRA, TipoJugador.AUTO);
				}
				else
					return null;
			}
			else
				return null;
		}
		else
			return null;
	}
	
	@Override
	public String textoAyuda() {
		return "JUGADOR [blancas|negras] [humano|automático]: Cambia el tipo de jugador." + System.getProperty("line.separator");
	}
	
	public static Ficha getColor(){
		return color;
	}
	public static TipoJugador getTipoJugador(){
		return tipoJugador;
	}
}