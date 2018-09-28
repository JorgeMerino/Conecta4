package vistas;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import control.ControladorGUI;
import excepciones.MovimientoInvalido;
import logica.Ficha;
import logica.TableroSoloLectura;
import observadores.Observador;

public class VistaGUI extends JFrame implements Observador{
	private ControladorGUI control;
	private Container panelPrincipal;
	private VistaGUIPanelOeste panelOeste;
	private VistaGUIPanelEste panelEste;
	private static TableroSoloLectura tableroROM;
	private JButton [][] boton;
	private int filas, columnas;
	
	//CONSTRUCOTORA DE LA VISTA DE USUARIO
	public VistaGUI(ControladorGUI control) throws HeadlessException{
		super("Práctica 4 - TP");
		this.control = control;
		//PANEL OESTE
		panelOeste = new VistaGUIPanelOeste(control);
		
		//PANEL ESTE
		panelEste = new VistaGUIPanelEste(control);
		
		panelPrincipal = this.getContentPane();
		panelPrincipal.setLayout(new BorderLayout());
		panelPrincipal.add(panelOeste.panelOeste, BorderLayout.CENTER);
		panelPrincipal.add(panelEste.panelEste, BorderLayout.EAST);
	}
	
	//GESTIÓN DE PARTIDA
	@Override
	public void onReset(TableroSoloLectura tablero, Ficha turno){
		tableroROM = tablero;
		filas = tableroROM.getFilas();
		columnas = tableroROM.getColumnas();

		panelOeste.panelTablero.setVisible(false);
		panelOeste.panelTablero = new JPanel();
		panelOeste.panelTablero.setLayout(new GridLayout(filas, columnas));
		if(turno == Ficha.BLANCA)
			panelOeste.lblInfo.setText("Juegan Blancas");
		else
			panelOeste.lblInfo.setText("Juegan Negras");
		
		//PONEMOS LOS JUGADORES COMO 'HUMANOS'
		panelEste.cmbJugadorBlancas.setSelectedIndex(0);
		panelEste.cmbJugadorNegras.setSelectedIndex(0);
		panelEste.cmbJugadorBlancas.setEnabled(true);
		panelEste.cmbJugadorNegras.setEnabled(true);
		//INHABILITAMOS DESHACER Y HABILITAMOS PONER ALEATORIO y creamos el tablero
		panelEste.btnDeshacer.setEnabled(false);
		panelOeste.btnAleatorio.setEnabled(true);
		boton = new JButton[filas][columnas];
		for(int i = 0; i<filas; i++){
			for(int j = 0; j<columnas; j++){
				boton[i][j] = new JButton();
				panelOeste.panelTablero.add(boton[i][j]);
				boton[i][j].addActionListener (new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e){
						if(e.getSource() instanceof JButton){
							posicionBoton((JButton)e.getSource());
						}
					} 
				});
			}
		}
		imprimirTablero();
		panelOeste.panelJuego.add(panelOeste.panelTablero, BorderLayout.CENTER);
	}
	
	@Override
	public void onPartidaTerminada(TableroSoloLectura tablero, Ficha ganador){
		tableroROM = tablero;
		filas = tableroROM.getFilas();
		columnas = tableroROM.getColumnas();
		
		panelOeste.panelTablero.setVisible(false);
		panelOeste.panelTablero = new JPanel();
		panelOeste.panelTablero.setLayout(new GridLayout(filas, columnas));
		imprimirTablero();
		if(ganador != Ficha.VACIA){
			if(ganador == Ficha.BLANCA)
				panelOeste.lblInfo.setText("Ganan las Blancas");
			else
				panelOeste.lblInfo.setText("Ganan las Negras");
		}
		else
			panelOeste.lblInfo.setText("El Juego termina en Tablas");
		
		//INHABILITAMOS el TABLERO y el boton DESHACER y PONER ALEATORIO
		panelEste.btnDeshacer.setEnabled(false);
		panelOeste.btnAleatorio.setEnabled(false);
		panelEste.cmbJugadorBlancas.setEnabled(false);
		panelEste.cmbJugadorNegras.setEnabled(false);
		bloquearTablero(tableroROM);
	}
	
	@Override
	public void onCambioJuego(TableroSoloLectura tablero, Ficha turno){
		tableroROM = tablero;
		filas = tableroROM.getFilas();
		columnas = tableroROM.getColumnas();
		
		panelOeste.panelTablero.setVisible(false);
		panelOeste.panelTablero = new JPanel();
		panelOeste.panelTablero.setLayout(new GridLayout(filas, columnas));
		if(turno == Ficha.BLANCA)
			panelOeste.lblInfo.setText("Juegan Blancas");
		else
			panelOeste.lblInfo.setText("Juegan Negras");
		
		//PONEMOS LOS JUGADORES COMO 'HUMANOS'
		panelEste.cmbJugadorBlancas.setSelectedIndex(0);
		panelEste.cmbJugadorNegras.setSelectedIndex(0);
		panelEste.cmbJugadorBlancas.setEnabled(true);
		panelEste.cmbJugadorNegras.setEnabled(true);
		//INHABILITAMOS DESHACER Y HABILITAMOS PONER ALEATORIO y creamos el tablero
		panelEste.btnDeshacer.setEnabled(false);
		panelOeste.btnAleatorio.setEnabled(true);
		boton = new JButton[filas][columnas];
		for(int i = 0; i<filas; i++){
			for(int j = 0; j<columnas; j++){
				boton[i][j] = new JButton();
				panelOeste.panelTablero.add(boton[i][j]);
				boton[i][j].addActionListener (new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e){
						if(e.getSource() instanceof JButton){
							posicionBoton((JButton)e.getSource());
						}
					} 
				});
			}
		}
		imprimirTablero();
		panelOeste.panelJuego.add(panelOeste.panelTablero, BorderLayout.CENTER);
	}
	
	@Override
	public void onExit(){
		int msg = JOptionPane.showConfirmDialog(null, "¿Realmente desea salir de Connecta 4?", "Información", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
			if(msg == JOptionPane.YES_OPTION)
				System.exit(0);
	}
	
	//GESTIÓN DE MOVIMIENTOS
	@Override
	public void onUndoNotPossible(){
		JOptionPane.showMessageDialog(null, "Imposible Deshacer", "Atención", JOptionPane.WARNING_MESSAGE);
	}
	
	@Override
	public void onUndo(TableroSoloLectura tablero, Ficha turno, boolean hayMas){
		tableroROM = tablero;
		filas = tableroROM.getFilas();
		columnas = tableroROM.getColumnas();
		
		panelOeste.panelTablero.setVisible(false);
		panelOeste.panelTablero = new JPanel();
		panelOeste.panelTablero.setLayout(new GridLayout(filas, columnas));
		imprimirTablero();
		if(turno == Ficha.BLANCA)
			panelOeste.lblInfo.setText("Juegan Blancas");
		else
			panelOeste.lblInfo.setText("Juegan Negras");
		
		if(!hayMas)
			panelEste.btnDeshacer.setEnabled(false);
	}
	
	@Override
	public void onMovimientoStart(Ficha turno){
		if(turno == Ficha.BLANCA)
			panelOeste.lblInfo.setText("Juegan Blancas");
		else
			panelOeste.lblInfo.setText("Juegan Negras");				
	}
	
	@Override
	public void onMovimientoEnd(TableroSoloLectura tablero, Ficha turno){  //El turno ya ha sido cambiado, respecto al turno jugado
		tableroROM = tablero;
		filas = tableroROM.getFilas();
		columnas = tableroROM.getColumnas();
		
		panelOeste.panelTablero.setVisible(false);
		panelOeste.panelTablero = new JPanel();
		panelOeste.panelTablero.setLayout(new GridLayout(filas, columnas));
		imprimirTablero();
		
		if(turno == Ficha.BLANCA){
			if(panelEste.cmbJugadorBlancas.getSelectedItem().equals("Humano")){
				panelEste.btnDeshacer.setEnabled(true);
				panelOeste.btnAleatorio.setEnabled(true);
				panelEste.cmbJugadorBlancas.setEnabled(true);
				panelEste.cmbJugadorNegras.setEnabled(true);
				desbloquearTablero(tableroROM);
			}
			else{
				panelEste.btnDeshacer.setEnabled(false);
				panelOeste.btnAleatorio.setEnabled(false);
				panelEste.cmbJugadorBlancas.setEnabled(false);
				panelEste.cmbJugadorNegras.setEnabled(false);
				bloquearTablero(tableroROM);
			}
		}
		else{
			if(panelEste.cmbJugadorNegras.getSelectedItem().equals("Humano")){
				panelEste.btnDeshacer.setEnabled(true);
				panelOeste.btnAleatorio.setEnabled(true);
				panelEste.cmbJugadorBlancas.setEnabled(true);
				panelEste.cmbJugadorNegras.setEnabled(true);
				desbloquearTablero(tableroROM);
			}
			else{
				panelEste.btnDeshacer.setEnabled(false);
				panelOeste.btnAleatorio.setEnabled(false);
				panelEste.cmbJugadorBlancas.setEnabled(false);
				panelEste.cmbJugadorNegras.setEnabled(false);
				bloquearTablero(tableroROM);
			}
		}
	}
	
	@Override
	public void onMovimientoIncorrecto(MovimientoInvalido movimientoException){
		JOptionPane.showMessageDialog(null, movimientoException.getMessage(), "Atención", JOptionPane.WARNING_MESSAGE);
	}
	
	//MÉTODOS COMPLEMENTARIOS
	private void posicionBoton(JButton boton){
		int filas = tableroROM.getFilas();
		int columnas = tableroROM.getColumnas();
		int i, j;
		boolean encontrado = false;
		
		i=0;
		while(i<filas && !encontrado){
			j=0;
			while(j<columnas && !encontrado){
				if(boton.equals(this.boton[i][j])){
					control.poner(i, j);
					encontrado = true;
				}
				j++;
			}
			i++;
		}
	}
	
	private void imprimirTablero(){
		for(int i = 0; i<filas; i++){
			for(int j = 0; j<columnas; j++){
				if(tableroROM.getFicha(i, j) == Ficha.BLANCA)
					boton[i][j].setBackground(java.awt.Color.white);
				else if(tableroROM.getFicha(i, j) == Ficha.NEGRA)
					boton[i][j].setBackground(java.awt.Color.black);
				else
					boton[i][j].setBackground(java.awt.Color.cyan);
				
				panelOeste.panelTablero.add(boton[i][j]);
			}
		}
		
		panelOeste.panelJuego.add(panelOeste.panelTablero, BorderLayout.CENTER);
	}
	
	private void bloquearTablero(TableroSoloLectura tablero){
		for(int i = 0; i<filas; i++){
			for(int j = 0; j<columnas; j++){
				boton[i][j].setEnabled(false);
			}
		}
	}
	
	private void desbloquearTablero(TableroSoloLectura tablero){
		for(int i = 0; i<filas; i++){
			for(int j = 0; j<columnas; j++){
				boton[i][j].setEnabled(true);
			}
		}
	}
	
	//GETS
	public static TableroSoloLectura getTablero(){
		return tableroROM;
	}
	
	public void start(){
		EventQueue.invokeLater(new Runnable(){
			@Override
			public void run(){
				setLocation(100, 50);
				setSize(600,  400);
				setResizable(true);
				setVisible(true);
				setDefaultCloseOperation(EXIT_ON_CLOSE);
			}
		});
	}
}