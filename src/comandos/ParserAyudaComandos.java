package comandos;

public class ParserAyudaComandos {
	private static Comando[] comandos = {
		new Salir(), new Deshacer(), new Jugar(),new Poner(),
		new PonJugador(), new Ayuda(), new Reiniciar()
	};
	static public String AyudaComandos(){
		String mensajeAyuda = "\t\t AYUDA" + System.getProperty("line.separator");
		for(int i=0; i<7; i++)
			mensajeAyuda = mensajeAyuda + comandos[i].textoAyuda();
		return mensajeAyuda;
		// devuelve el texto asociado al comando Ayuda
	}
	static public Comando parsea(String[] cadenas){
		Comando comando = null;
		
		int i = 0;	
		while(comando == null && i<7){
			comando = comandos[i].parsea(cadenas);
			i++;
		}

		return comando;
	}
}