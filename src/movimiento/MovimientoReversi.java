package movimiento;

import java.util.ArrayList;

import reglas.ReglasReversi;
import excepciones.MovimientoInvalido;
import logica.Coordenadas;
import logica.Ficha;
import logica.Tablero;

public class MovimientoReversi extends Movimiento{
	private ArrayList <Movimiento> fichasVolteadas;   //Array recolector de las fichas volteadas en cada movimiento
	private ArrayList <Movimiento> fichasPosibles;
	private ArrayList <Coordenadas> dir;
	
	public MovimientoReversi(int fila, int columna, Ficha turno) {
		super(columna, turno);
		this.fila = fila;
		fichasVolteadas = new ArrayList <Movimiento> ();
		fichasPosibles = new ArrayList <Movimiento> ();
		dir = new ArrayList <Coordenadas> ();
	}
	
	public MovimientoReversi(Ficha turno){
		super(turno);
		fichasPosibles = new ArrayList <Movimiento> ();
		dir = new ArrayList <Coordenadas> ();
	}

	@Override
	public void ejecutaMovimiento(Tablero tablero) throws MovimientoInvalido {
		boolean valido = movimientoValido(tablero);
		if(valido)
			tablero.ponFicha(fila, columna, turno);   //Ponemos la ficha
		else
			tablero.ponFicha(-1, -1, turno);  // Si no es valido sacamos la ficha del tablero para que salte la excepción
	}

	@Override
	public void undo(Tablero tablero){
		tablero.ponFicha(fila, columna, Ficha.VACIA);   //Vaciamos la ultima ficha puesta
		for(int i=0; i<fichasVolteadas.size(); i++){    //Voleamos las fichas que fueron cambiadas tras el movimiento anterior al undo
			Movimiento mov = fichasVolteadas.get(i);
			tablero.ponFicha(mov.getFila(), mov.getColumna(), mov.getJugador());
		}
	}

	@Override
	protected boolean movimientoValido(Tablero tablero){
		boolean valido = false;		
		if(columna>=0 && columna<ReglasReversi.COLUMNAS && fila>=0 && fila<ReglasReversi.FILAS){  //Comprueba que la fila y la columna sea correcta
			comprobarPosibles(tablero);
			for(int i=0; i<fichasPosibles.size(); i++){
				if(fila == fichasPosibles.get(i).fila && columna == fichasPosibles.get(i).columna && turno == fichasPosibles.get(i).turno){
					pintarFichas(tablero, dir.get(i));
					valido = true;
				}
			}
		}
		
		return valido;
	}
	
	public void comprobarPosibles(Tablero tablero){
		for(int i=0; i<ReglasReversi.FILAS; i++){
			for(int j=0; j<ReglasReversi.COLUMNAS; j++){
				if(tablero.getFicha(i, j) == Ficha.VACIA){
					//VERTICAL
						//Comprobar grupo ascendente
						if(i-1>=0){
							int x = i-1;
							while(tablero.getFicha(x, j) != turno && tablero.getFicha(x, j) != Ficha.VACIA && x-1>=0)
								x--;
							
							if(tablero.getFicha(x, j) == turno && x<i-1){
								fichasPosibles.add(new MovimientoReversi(i, j, turno));
								dir.add(Coordenadas.NORTE);
							}
						}
						
						//Comprobar grupo descendente
						if(i+1<ReglasReversi.FILAS){
							int x = i+1;
							while(tablero.getFicha(x, j) != turno && tablero.getFicha(x, j) != Ficha.VACIA && x+1<ReglasReversi.FILAS)
								x++;
	
							if(tablero.getFicha(x, j) == turno && x>i+1){
								fichasPosibles.add(new MovimientoReversi(i, j, turno));
								dir.add(Coordenadas.SUR);
							}
						}
					
					//HORIZONTAL
						//Comprobar grupo a izquierdas
						if(j-1>=0){
							int y = j-1;
							while(tablero.getFicha(i, y) != turno && tablero.getFicha(i, y) != Ficha.VACIA && y-1>=0)
								y--;

							if(tablero.getFicha(i, y) == turno && y<j-1){
								fichasPosibles.add(new MovimientoReversi(i, j, turno));
								dir.add(Coordenadas.OESTE);
							}
						}
						
						//Comprobar grupo a derechas
						if(j+1<ReglasReversi.COLUMNAS){
							int y = j+1;
							while(tablero.getFicha(i, y) != turno && tablero.getFicha(i, y) != Ficha.VACIA && y+1<ReglasReversi.COLUMNAS){
								y++;
							}
							if(tablero.getFicha(i, y) == turno && y>j+1){
								fichasPosibles.add(new MovimientoReversi(i, j, turno));
								dir.add(Coordenadas.ESTE);
							}
						}
						
					//DIAGONALES
						//Comprobar grupo arriba izquierda
						if(i-1>=0 && j-1>=0){
							int x = i-1;
							int y = j-1;
							while(tablero.getFicha(x, y) != turno && tablero.getFicha(x, y) != Ficha.VACIA && x-1>=0 && y-1>=0){
								x--;
								y--;
							}
							if(tablero.getFicha(x, y) == turno && x<i-1 && y<j-1){
								fichasPosibles.add(new MovimientoReversi(i, j, turno));
								dir.add(Coordenadas.NO);
							}
						}
											
						//Comprobar grupo arrbia derecha
						if(i-1>=0 && j+1<ReglasReversi.COLUMNAS){
							int x = i-1;
							int y = j+1;
							while(tablero.getFicha(x, y) != turno && tablero.getFicha(x, y) != Ficha.VACIA && x-1>=0 && y+1<ReglasReversi.COLUMNAS){
								x--;
								y++;
							}
							if(tablero.getFicha(x, y) == turno && x<i-1 && y>j+1){
								fichasPosibles.add(new MovimientoReversi(i, j, turno));
								dir.add(Coordenadas.NE);
							}
						}
						
						//Comprobar grupo abajo izquierda
						if(i+1<ReglasReversi.FILAS && j-1>=0){
							int x = i+1;
							int y = j-1;
							while(tablero.getFicha(x, y) != turno && tablero.getFicha(x, y) != Ficha.VACIA && x+1<ReglasReversi.FILAS && y-1>=0){
								x++;
								y--;
							}
							if(tablero.getFicha(x, y) == turno && x>i+1 && y<j-1){
								fichasPosibles.add(new MovimientoReversi(i, j, turno));
								dir.add(Coordenadas.SO);
							}
						}
						
						//Comprobar grupo abajo derecha
						if(i+1<ReglasReversi.FILAS && j+1<ReglasReversi.COLUMNAS){
							int x = i+1;
							int y = j+1;
							while(tablero.getFicha(x, y) != turno && tablero.getFicha(x, y) != Ficha.VACIA && x+1<ReglasReversi.FILAS && y+1<ReglasReversi.COLUMNAS){
								x++;
								y++;
							}
							if(tablero.getFicha(x, y) == turno && x>i+1 && y>j+1){
								fichasPosibles.add(new MovimientoReversi(i, j, turno));
								dir.add(Coordenadas.SE);
							}
						}
				}
			}
		}
	}
	
	private void pintarFichas(Tablero tablero, Coordenadas dir){
		if(dir == Coordenadas.NORTE){
			int i = fila-1;
			while(tablero.getFicha(i, columna)!=turno){
				fichasVolteadas.add(new MovimientoReversi(i, columna, turnoContrario(turno)));
				tablero.ponFicha(i, columna, turno);
				i--;
			}
		}
		else if(dir == Coordenadas.SUR){
			int i = fila+1;
			while(tablero.getFicha(i, columna)!=turno){
				fichasVolteadas.add(new MovimientoReversi(i, columna, turnoContrario(turno)));
				tablero.ponFicha(i, columna, turno);
				i++;
			}
		}
		else if(dir == Coordenadas.OESTE){
			int j = columna-1;
			while(tablero.getFicha(fila, j)!=turno){
				fichasVolteadas.add(new MovimientoReversi(fila, j, turnoContrario(turno)));
				tablero.ponFicha(fila, j, turno);
				j--;
			}		
		}
		else if(dir == Coordenadas.ESTE){
			int j = columna+1;
			while(tablero.getFicha(fila, j)!=turno){
				fichasVolteadas.add(new MovimientoReversi(fila, j, turnoContrario(turno)));
				tablero.ponFicha(fila, j, turno);
				j++;
			}
		}
		else if(dir == Coordenadas.NO){
			int i = fila-1;
			int j = columna-1;
			while(tablero.getFicha(i, j)!=turno){
				fichasVolteadas.add(new MovimientoReversi(i, j, turnoContrario(turno)));
				tablero.ponFicha(i, j, turno);
				i--;
				j--;
			}
		}
		else if(dir == Coordenadas.NE){
			int i = fila-1;
			int j = columna+1;
			while(tablero.getFicha(i, j)!=turno){
				fichasVolteadas.add(new MovimientoReversi(i, j, turnoContrario(turno)));
				tablero.ponFicha(i, j, turno);
				i--;
				j++;
			}
		}
		else if(dir == Coordenadas.SO){
			int i = fila+1;
			int j = columna-1;
			while(tablero.getFicha(i, j)!=turno){
				fichasVolteadas.add(new MovimientoReversi(i, j, turnoContrario(turno)));
				tablero.ponFicha(i, j, turno);
				i++;
				j--;
			}
		}
		else if(dir == Coordenadas.SE){
			int i = fila+1;
			int j = columna+1;
			while(tablero.getFicha(i, j)!=turno){
				fichasVolteadas.add(new MovimientoReversi(i, j, turnoContrario(turno)));
				tablero.ponFicha(i, j, turno);
				i++;
				j++;
			}
		}
	}
	
	public Ficha turnoContrario(Ficha turnoActual){
		if(turnoActual == Ficha.BLANCA) //Guardamos en el Array fichasVolteadas las fichas que cambian de color
			return Ficha.NEGRA;
		else
			return Ficha.BLANCA;
	}

	@Override
	protected int buscarFila(int columna, Tablero tablero){
		return this.fila;
	}

	public ArrayList <Movimiento> getFichasPosibles() {
		return fichasPosibles;
	}
}