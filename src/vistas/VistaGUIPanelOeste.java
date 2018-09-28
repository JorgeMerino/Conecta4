package vistas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import control.ControladorGUI;

public class VistaGUIPanelOeste{
	private ControladorGUI control;
	protected JPanel panelOeste;
	protected JPanel panelJuego;
	protected JPanel panelTablero;	
	private JPanel panelAleatorio;
	protected JButton btnAleatorio;
	protected JLabel lblInfo;
	
	public VistaGUIPanelOeste(ControladorGUI control){
		this.control = control;
		
		//PANEL JUEGO
		panelTablero = new JPanel();
		lblInfo = new JLabel("Juegan Blancas", SwingConstants.CENTER);
		lblInfo.setFont(new java.awt.Font("Arial", 0, 25));
		
		panelJuego = new JPanel();
		panelJuego.setLayout(new BorderLayout());
		panelJuego.add(panelTablero, BorderLayout.CENTER);
		panelJuego.add(lblInfo, BorderLayout.SOUTH);
		
		//PANEL ALEATORIO
		btnAleatorio = new JButton("Poner Aleatorio");
		btnAleatorio.addActionListener (new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(e.getSource() instanceof JButton)
					ponerAleatorio();
			} 
		});
		
		panelAleatorio = new JPanel();
		panelAleatorio.setLayout(new FlowLayout());	
		panelAleatorio.add(btnAleatorio);		
		
		//PANEL OESTE
		panelOeste = new JPanel();
		panelOeste.setLayout(new BorderLayout());
		panelOeste.add(panelJuego, BorderLayout.CENTER);
		panelOeste.add(panelAleatorio, BorderLayout.SOUTH);
	}
	
	private void ponerAleatorio(){
		control.ponAleatorio();
	}
}
