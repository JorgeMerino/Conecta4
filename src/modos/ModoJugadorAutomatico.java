package modos;

import control.ControladorGUI;

public class ModoJugadorAutomatico implements ModoJugador{
	private Thread hebraAuto;
	private ControladorGUI control;
	
	public ModoJugadorAutomatico(ControladorGUI control){
		this.control = control;
	}

	@Override
	public void comenzar(){
		hebraAuto = new Thread(){
			@Override
			public void run(){
				if(!control.getPartida().getTerminada()){
					try {
						Thread.sleep(1000);
						control.ponAleatorio();
					}
					catch (InterruptedException e){}
				}
			}
		};
		hebraAuto.start();
	}

	@Override
	public void terminar(){
			hebraAuto.interrupt();
	}
}