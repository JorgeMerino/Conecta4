package main;

import java.util.Scanner;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import reglas.Reglas3Raya;
import reglas.ReglasGravity;
import reglas.ReglasJuego;
import vistas.VistaConsola;
import vistas.VistaGUI;
import logica.Partida;
import logica.TipoJuego;
import control.ControladorConsola;
import control.ControladorGUI;
import excepciones.DatosIncorrectos;
import excepciones.ErrorDeEjecucion;
import excepciones.MovimientoInvalido;
import factorias.FactoriaJuego;
import factorias.FactoriaJuego3Raya;
import factorias.FactoriaJuegoComplica;
import factorias.FactoriaJuegoConecta4;
import factorias.FactoriaJuegoGravity;
import factorias.FactoriaJuegoReversi;

/**
 * Clase principal que se encarga de iniciar la partida creando
 * un controlador y una partida, y llamando a los métodos
 * correspondientes para iniciar el juego.*/
public class Main{
	public static void main(String[] args){
		ReglasJuego regla;
		Partida partida;
		FactoriaJuego factoria;
		TipoJuego tipoJuego;
				
		String arg_invalidos="";
		Options options = new Options();
		
		options.addOption("g", "game", true, "Tipo de juego (c4, co, gr, rv, tr). Por defecto, c4.");
		options.addOption("h", "help", false, "Muestra esta ayuda.");
		options.addOption("u", "ui", true, "Tipo de interfaz (console, window). Por defecto, console.");
		options.addOption("x", "tamX", true, "Número de columnas del tablero (modo Gravity, diez por defecto).");
		options.addOption("y", "tamY", true, "Número de filas del tablero (modo Gravity, diez por defecto).");
		
		CommandLineParser parser = new BasicParser();

		try{
			CommandLine cmd = parser.parse(options, args);
			String juego = cmd.getOptionValue("g");
			
			if(cmd.getArgs().length>0){
				for(int i=0;i<cmd.getArgs().length;i++)
					arg_invalidos += cmd.getArgs()[i]+" ";
			}
						
			if(cmd.hasOption("h")){
				new HelpFormatter().printHelp(Main.class.getCanonicalName(), options);
				System.exit(1);
			}
			
			else if(cmd.hasOption("g")){
				if(cmd.getArgs().length>0)
					throw new ParseException("Argumentos no entendidos: "+arg_invalidos);
				
				switch(juego){
					case "c4":
						if(cmd.hasOption("x") || cmd.hasOption("y"))
							throw new ParseException("No se permite -x o -y para c4");
						
						factoria = new FactoriaJuegoConecta4();
						regla = factoria.creaReglas();
						partida = new Partida(regla);
						tipoJuego = TipoJuego.C4;
					break;
					
					case "co":
						if(cmd.hasOption("x") || cmd.hasOption("y"))
							throw new ParseException("No se permite -x o -y para co");

						factoria = new FactoriaJuegoComplica();
						regla = factoria.creaReglas();
						partida = new Partida(regla);
						tipoJuego = TipoJuego.CO;
					break;
					
					case "gr":
						factoria = new FactoriaJuegoGravity();
						if(!(cmd.hasOption("x") || cmd.hasOption("y")))
							regla =factoria.creaReglas();
						else{
							int x = Integer.parseInt(cmd.getOptionValue("x"));
							int y = Integer.parseInt(cmd.getOptionValue("y"));
							regla = new ReglasGravity(x, y);
						}
						partida = new Partida(regla);
						factoria = new FactoriaJuegoGravity();
						tipoJuego = TipoJuego.GR;
					break;
					
					case "rv":
						if(cmd.hasOption("x") || cmd.hasOption("y"))
							throw new ParseException("No se permite -x o -y para rv");
						
						factoria = new FactoriaJuegoReversi();
						regla = factoria.creaReglas();
						partida = new Partida(regla);
						tipoJuego = TipoJuego.RV;
					break;
					
					case "tr":
						factoria = new FactoriaJuego3Raya();
						if(!(cmd.hasOption("x") || cmd.hasOption("y")))
							regla =factoria.creaReglas();
						else{
							int x = Integer.parseInt(cmd.getOptionValue("x"));
							int y = Integer.parseInt(cmd.getOptionValue("y"));
							regla = new Reglas3Raya(x, y);
						}
						partida = new Partida(regla);
						factoria = new FactoriaJuego3Raya();
						tipoJuego = TipoJuego.TR;
					break;
					
					default:
						throw new ParseException("Juego '" + cmd.getOptionValue("g") + "' incorrecto");
				}
				
				if(cmd.hasOption("u")){
					String modo = cmd.getOptionValue("u");
					switch(modo){
						case "console":
							Scanner in = new Scanner(System.in);
							ControladorConsola control = new ControladorConsola(factoria, partida, in);
							partida.addObservador(new VistaConsola());
							partida.reset(regla);
							
							try{
								control.run();					
							}
							catch (MovimientoInvalido e){
								System.out.println(e.getMessage());
							}
							catch (DatosIncorrectos e){
								System.out.println(e.getMessage());
							}
							catch (ErrorDeEjecucion e){
								System.out.println(e.getMessage());
								System.out.println("Se reiniciará la partida");
								main(args);
							}	
						break;
						
						case "window":
							ControladorGUI controlGUI = new ControladorGUI(partida, factoria, tipoJuego);
							VistaGUI v = new VistaGUI(controlGUI);
							partida.addObservador(v);
							partida.reset(regla);
							v.start();
						break;
						
						default:
							throw new ParseException("Modo de juego '" + cmd.getOptionValue("u") + "' incorrecto");
					}
				}				
			}
			else{   //Si no se introduce ninún argumento se ejecuta Connecta 4 en modo Consola, por defecto
				factoria = new FactoriaJuegoConecta4();
				regla = factoria.creaReglas();
				partida = new Partida(regla);
				tipoJuego = TipoJuego.C4;
				
				Scanner in = new Scanner(System.in);
				ControladorConsola control = new ControladorConsola(factoria, partida, in);
				partida.addObservador(new VistaConsola());
				partida.reset(regla);
				try{
					control.run();					
				}
				catch (MovimientoInvalido e){
					System.out.println(e.getMessage());
				}
				catch (DatosIncorrectos e){
					System.out.println(e.getMessage());
				}
				catch (ErrorDeEjecucion e){
					System.out.println(e.getMessage());
					System.out.println("Se reiniciará la partida");
					main(args);
				}
			}
		}
		catch(ParseException exp){
			System.err.println("Uso incorrecto: "+exp.getMessage()+"\nUse -h|--help para ver más detalles.");
			System.exit(1);
		}
		catch(NumberFormatException exp){
			System.err.println("Uso incorrecto: "+exp.getMessage()+"\nUse -h|--help para ver más detalles.");
			System.exit(1);
		}
	}
}