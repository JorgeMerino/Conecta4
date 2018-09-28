package vistas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import logica.Ficha;
import logica.TipoJuego;
import logica.TipoJugador;
import control.ControladorGUI;

public class VistaGUIPanelEste{
	private ControladorGUI control;
	protected JPanel panelEste;
	private JPanel panelOpcionesJuego;
	private JPanel panelPartida;
	private JPanel panelJugadores;
	private JPanel panelJugadoresEtiquetas;
	private JPanel panelJugadoresDesplegables;
	private JPanel panelCambioJuego;
	private JPanel panelCentral;
	private JPanel panelSalir;
	protected JButton btnDeshacer;
	private JButton btnReiniciar;
	private JButton btnCambiarJuego;
	private JButton btnSalir;	
	protected JComboBox<String> cmbJugadorBlancas;
	protected JComboBox<String> cmbJugadorNegras;
	private JComboBox<String> cmbTipoJuego;
	private JLabel lblFilas;
	private JLabel lblColumnas;
	private JLabel lblJugadorBlancas;
	private JLabel lblJugadorNegras;
	private JTextField txtFilas;
	private JTextField txtColumnas;
	private String[] listaJugadores = {"Humano", "Automático"};
	private String[] listaJuegos = {"Conecta 4", "Complica", "Gravity", "Reversi", "Tres en Raya"};
	private TipoJuego tipoJuego;
	private TipoJugador jugadorBlancas;
	private TipoJugador jugadorNegras;
	
	public VistaGUIPanelEste(ControladorGUI control){
		this.control = control;
		tipoJuego = control.getTipoJuego();
		jugadorBlancas = TipoJugador.HUMANO;
		jugadorNegras = TipoJugador.HUMANO;
		
		//PANEL PARTIDA
		btnDeshacer = new JButton("Deshacer");
		btnDeshacer.addActionListener (new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				deshacer();
			} 
		});
		
		btnReiniciar = new JButton("Reiniciar");
		btnReiniciar.addActionListener (new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				reiniciar();
			} 
		});
		
		panelPartida = new JPanel();
		panelPartida.setLayout(new FlowLayout());
		panelPartida.setBorder(BorderFactory.createTitledBorder("Partida"));
		panelPartida.add(btnDeshacer);
		panelPartida.add(btnReiniciar);
		
		
		//PANEL GESTIÓN DE JUGADORES
		lblJugadorBlancas = new JLabel("J. de Blancas");
		lblJugadorNegras = new JLabel("J. de Negras");	
		panelJugadoresEtiquetas = new JPanel();
		panelJugadoresEtiquetas.setLayout(new BorderLayout());
		panelJugadoresEtiquetas.add(lblJugadorBlancas, BorderLayout.NORTH);
		panelJugadoresEtiquetas.add(lblJugadorNegras, BorderLayout.SOUTH);
		
		cmbJugadorBlancas = new JComboBox<String>(listaJugadores);
		cmbJugadorNegras = new JComboBox<String>(listaJugadores);
		cmbJugadorBlancas.addActionListener (new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(cmbJugadorBlancas.getSelectedItem().toString().equalsIgnoreCase("AUTOMÁTICO"))
					jugadorBlancas = TipoJugador.AUTO;
				else
					jugadorBlancas = TipoJugador.HUMANO;
				
				ponJugador(Ficha.BLANCA, jugadorBlancas);
			} 
		});
		cmbJugadorNegras.addActionListener (new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(cmbJugadorNegras.getSelectedItem().toString().equalsIgnoreCase("AUTOMÁTICO"))
					jugadorNegras = TipoJugador.AUTO;
				else
					jugadorNegras = TipoJugador.HUMANO;
				
				ponJugador(Ficha.NEGRA, jugadorNegras);
			} 
		});
		panelJugadoresDesplegables = new JPanel();
		panelJugadoresDesplegables.setLayout(new BorderLayout());
		panelJugadoresDesplegables.add(cmbJugadorBlancas, BorderLayout.NORTH);
		panelJugadoresDesplegables.add(cmbJugadorNegras, BorderLayout.SOUTH);
		
		panelJugadores = new JPanel();
		panelJugadores.setLayout(new BorderLayout());
		panelJugadores.setBorder(BorderFactory.createTitledBorder("Gestión de Jugadores"));
		panelJugadores.add(panelJugadoresEtiquetas, BorderLayout.WEST);
		panelJugadores.add(panelJugadoresDesplegables, BorderLayout.EAST);
		
		
		//PANEL CAMBIO DE JUEGO		
		lblFilas = new JLabel("Filas");
		lblColumnas = new JLabel("Columnas");
		txtFilas = new JTextField(3);
		txtColumnas = new JTextField(3);
		lblFilas.setVisible(false);
		lblColumnas.setVisible(false);
		txtFilas.setVisible(false);
		txtColumnas.setVisible(false);
		
		cmbTipoJuego = new JComboBox<String>(listaJuegos);
		
		switch(control.getTipoJuego()){
			case C4:
				cmbTipoJuego.setSelectedItem("Conecta 4");
				tipoJuego = TipoJuego.C4;
			break;
			case CO:
				cmbTipoJuego.setSelectedItem("Complica");
				tipoJuego = TipoJuego.CO;
			break;
			case GR:
				cmbTipoJuego.setSelectedItem("Gravity");
				tipoJuego = TipoJuego.GR;
				lblFilas.setVisible(true);
				lblColumnas.setVisible(true);
				txtFilas.setVisible(true);
				txtColumnas.setVisible(true);
			break;
			case RV:
				cmbTipoJuego.setSelectedItem("Reversi");
				tipoJuego = TipoJuego.RV;
			break;
			default:
				cmbTipoJuego.setSelectedItem("Tres en Raya");
				tipoJuego = TipoJuego.TR;
				lblFilas.setVisible(true);
				lblColumnas.setVisible(true);
				txtFilas.setVisible(true);
				txtColumnas.setVisible(true);
			break;
		}
		
		cmbTipoJuego.addActionListener (new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				lblFilas.setVisible(false);
				lblColumnas.setVisible(false);
				txtFilas.setVisible(false);
				txtColumnas.setVisible(false);
				txtFilas.setText(null);
				txtColumnas.setText(null);
				
				if(cmbTipoJuego.getSelectedItem().equals("Conecta 4"))
					tipoJuego = TipoJuego.C4;
				else if(cmbTipoJuego.getSelectedItem().equals("Complica"))
					tipoJuego = TipoJuego.CO;		
				else if(cmbTipoJuego.getSelectedItem().equals("Gravity")){
					tipoJuego = TipoJuego.GR;
					lblFilas.setVisible(true);
					lblColumnas.setVisible(true);
					txtFilas.setVisible(true);
					txtColumnas.setVisible(true);
				}
				else if(cmbTipoJuego.getSelectedItem().equals("Reversi"))
					tipoJuego = TipoJuego.RV;
				else{
					tipoJuego = TipoJuego.TR;
					lblFilas.setVisible(true);
					lblColumnas.setVisible(true);
					txtFilas.setVisible(true);
					txtColumnas.setVisible(true);
				}
			} 
		});
		
		btnCambiarJuego = new JButton("Cambiar");
		btnCambiarJuego.addActionListener (new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				int fila, columna;
				if((tipoJuego == TipoJuego.GR || tipoJuego == TipoJuego.TR) && ((txtFilas.getText()).length() != 0 && (txtColumnas.getText()).length() != 0)){
					try{
						fila = Integer.parseInt(txtFilas.getText());
						columna = Integer.parseInt(txtColumnas.getText());
						jugar(fila, columna);
					}
					catch(NumberFormatException exp){
						JOptionPane.showMessageDialog(null, "Sólo se permiten datos numericos", "Atención", JOptionPane.WARNING_MESSAGE);
						txtFilas.setText(null);
						txtColumnas.setText(null);
					}
				}
				else
					jugar();
			} 
		});
		
		panelCentral = new JPanel();
		panelCentral.setLayout(new FlowLayout());
		panelCentral.add(lblFilas);
		panelCentral.add(txtFilas);
		panelCentral.add(lblColumnas);
		panelCentral.add(txtColumnas);		
		
		panelCambioJuego = new JPanel();
		panelCambioJuego.setLayout(new BorderLayout());
		panelCambioJuego.setBorder(BorderFactory.createTitledBorder("Cambio de Juego"));
		panelCambioJuego.add(cmbTipoJuego, BorderLayout.NORTH);
		panelCambioJuego.add(panelCentral, BorderLayout.CENTER);
		panelCambioJuego.add(btnCambiarJuego, BorderLayout.SOUTH);
		
		
		//PANEL SALIR
		btnSalir = new JButton("Salir");
		btnSalir.addActionListener (new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				salir();
			} 
		});
		
		panelSalir = new JPanel();
		panelSalir.setLayout(new FlowLayout());
		panelSalir.add(btnSalir);
		
		
		//PANEL OPCIONES JUEGO
		panelOpcionesJuego = new JPanel();
		panelOpcionesJuego.setLayout(new BorderLayout());
		panelOpcionesJuego.add(panelPartida, BorderLayout.NORTH);
		panelOpcionesJuego.add(panelJugadores, BorderLayout.CENTER);
		panelOpcionesJuego.add(panelCambioJuego, BorderLayout.SOUTH);
		
		
		//PANEL ESTE
		panelEste = new JPanel();
		panelEste.setLayout(new BorderLayout());
		panelEste.add(panelOpcionesJuego, BorderLayout.NORTH);
		panelEste.add(panelSalir, BorderLayout.SOUTH);
	}
	
	private void deshacer(){
		control.deshacer();
	}
	
	public void reiniciar(){
		control.reiniciar();
	}
	
	public void jugar(int fila, int columna){
		control.jugar(tipoJuego, fila, columna);
	}
	
	public void jugar(){
		control.jugar(tipoJuego);
	}
	
	public void ponJugador(Ficha ficha, TipoJugador tipoJugador){
		control.cambioJugador(ficha, tipoJugador);
	}
	
	public void salir(){
		control.salir();
	}
}