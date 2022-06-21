package logica;

import java.util.ArrayList;
import java.util.Random;

public class Carton {

	public static final int TAMANO_CARTON = 3;
	
	private Casilla[][] casillas;
	
	public Carton() {
		this.setCasillas(new Casilla[TAMANO_CARTON][TAMANO_CARTON]);
		rellenarCarton();
	}

	public Casilla[][] getCasillas() {
		return casillas;
	}

	public void setCasillas(Casilla[][] casillas) {
		this.casillas = casillas;
	}
	
//	public int getLineasMarcadas() {
//		return this.lineasMarcadas;
//	}
	
	/**
	 * Metodo que rellena el carton con casilla genericas
	 */
	public void rellenarCarton() {
		for(int i = 0; i < TAMANO_CARTON; i++) {
			for(int j = 0; j < TAMANO_CARTON; j++) {
				casillas[i][j]= new Casilla();
			}
		}
	}
	/**
	 * Metodo que asigna los numeros al carton, en la columna 0 los valores
	 * iran de 0-10, en la segunda de 11-20, y en la tercera 21-30 
	 */
	public void asignarNumerosCarton() {
		ArrayList<Integer> colum1 = new ArrayList<Integer>();
		ArrayList<Integer> colum2 = new ArrayList<Integer>();
		ArrayList<Integer> colum3 = new ArrayList<Integer>();
		
		for(int i = 0; i < 3; i++) {
			Random r = new Random();
			
			int numeroCol1 = r.nextInt(10) + 1;
			while(casillas[i][0].getNumero() == numeroCol1 || colum1.contains(numeroCol1)) {
				 numeroCol1 = r.nextInt(10) + 1;
			}
			casillas[i][0].setNumero(numeroCol1);
			colum1.add(numeroCol1);
			
			int numeroCol2 = r.nextInt(10) + 11;
			while(casillas[i][1].getNumero() == numeroCol2 || colum2.contains(numeroCol2)) {
				 numeroCol2 = r.nextInt(10) + 11;
			}
			casillas[i][1].setNumero(numeroCol2);
			colum2.add(numeroCol2);
			
			int numeroCol3 = r.nextInt(10) + 21;
			while(casillas[i][2].getNumero() == numeroCol2 || colum3.contains(numeroCol3)) {
				 numeroCol3 = r.nextInt(10) + 21;
			}
			casillas[i][2].setNumero(numeroCol3);
			colum3.add(numeroCol3);
		}
	}
	
	/**
	 * Metodo que escoge un numero del carton como el numero magico
	 */
	public void asignarNumeroMagico() {
		Random r = new Random();
		int columna = r.nextInt(3);
		int fila = r.nextInt(3);
		
		casillas[columna][fila].setNumeroMagico(true);
	}

	/**
	 * Metodo que marca un numero en el carton
	 * 
	 * @param x
	 * @param y
	 */
	public void marcarNumero(int x, int y) {
		casillas[x][y].setSelected(true);
	}
	
	/**
	 * Metodo que cuadno se marca un numero hace la comprobacion de si se puede
	 * cantar la linea donde se encuentra
	 * 
	 * @param x
	 * @return true si se puede cantar la linea, false si no
	 */
	public boolean sePuedeCantarLaLinea(int x) {
		for(int i = 0; i < TAMANO_CARTON; i++) {
			if(!casillas[x][i].isSelected()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Metodo que recorre el tablero para ver si todo el carton
	 * esta marcado y hay bingo 
	 * 
	 * @return true si hay ingo, false si no
	 */
	public boolean hayBingo() {
		for(int i = 0; i < TAMANO_CARTON; i++) {
			for(int j = 0; j < TAMANO_CARTON; j++) {
				if(!casillas[i][j].isSelected()) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Devuelve la liena donde se encuentra el num magico
	 * 
	 * @return linea donde se encuentra el numero magico
	 */
	public int lineaDelNumeroMagico() {
		for(int i = 0; i < TAMANO_CARTON; i++) {
			for(int j = 0; j < TAMANO_CARTON; j++) {
				if(casillas[i][j].isNumeroMagico) {
						return i;
					}
				}
			}
		return -1;
	}
	
}
