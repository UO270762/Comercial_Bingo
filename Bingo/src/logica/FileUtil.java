package logica;

import java.io.*;
import java.util.*;

public abstract class FileUtil {
	
	/**
	 * Metodo que carga premios del fichero, recibe la ruta del fichero y la lista donde cargar los premios
	 * 
	 * @param nombreFicheroEntrada
	 * @param listaPremios
	 */
	public static void cargarPremiosFichero(String nombreFicheroEntrada, List<Premio> listaPremios) {
		
	    String linea;
	    String[] datosPremio= null;	   
	    
	    try {
	    	   BufferedReader fichero = new BufferedReader(new FileReader(nombreFicheroEntrada));
	    		while (fichero.ready()) {
	    			linea = fichero.readLine();
	    			datosPremio = linea.split("@");
	    			if(linea.trim().length() != 0) {
	    				listaPremios.add(new Premio(datosPremio[0], datosPremio[1],datosPremio[2].charAt(0)));
	    			}
	    		}
	    		fichero.close();
	    }
	    catch (FileNotFoundException fnfe) {
	      System.out.println("El archivo no se ha encontrado.");
	    }
	    catch (IOException ioe) {
	      new RuntimeException("Error de entrada/salida.");
	    } 
	  }
	
	/**
	 * Metodo que carga los clientes del fichero, recibe la ruta del fichero y la lista donde cargar los clientes.
	 * 
	 * @param nombreFicheroEntrada
	 * @param listaCliente
	 */
	public static void cargarClientesFichero (String nombreFicheroEntrada, List<Cliente> listaCliente) {
		
	    String linea;
	    String[] datosCliente= null;	   
	    
	    try {
	    	   BufferedReader fichero = new BufferedReader(new FileReader(nombreFicheroEntrada));
	    		while (fichero.ready()) {
	    			linea = fichero.readLine();
	    			datosCliente = linea.split("@");
	    			if(linea.trim().length() != 0) {
	    				listaCliente.add(new Cliente(datosCliente[0], datosCliente[1], datosCliente[2], Integer.valueOf(datosCliente[3])));
	    			}
	    		}
	    		fichero.close();
	    }
	    catch (FileNotFoundException fnfe) {
	      System.out.println("El archivo no se ha encontrado.");
	    }
	    catch (IOException ioe) {
	      new RuntimeException("Error de entrada/salida.");
	    } 
	  }
	/**
	 * Metodo que carga los tickets del fichero, recibe la ruta del fichero y la lista donde cargar los tickets
	 * 
	 * @param nombreFicheroEntrada
	 * @param tickets
	 */
	public static void cargarTicketFichero(String nombreFicheroEntrada, List<Ticket> tickets) {
		
	    String linea;
	    String[] datosTicket= null;	   
	    
	    try {
	    	   BufferedReader fichero = new BufferedReader(new FileReader(nombreFicheroEntrada));
	    		while (fichero.ready()) {
	    			linea = fichero.readLine();
	    			datosTicket = linea.split("@");
	    			if(linea.trim().length() != 0) {
	    				tickets.add(new Ticket(datosTicket[0], Double.valueOf(datosTicket[1])));
	    			}
	    		}
	    		fichero.close();
	    }
	    catch (FileNotFoundException fnfe) {
	      System.out.println("El archivo no se ha encontrado.");
	    }
	    catch (IOException ioe) {
	      new RuntimeException("Error de entrada/salida.");
	    }
	  }
	/**
	 * Metodoq que añade un cliente al fichero de clientes, recibe la ruta del fichero y el cliente
	 * 
	 * @param nombreFichero
	 * @param cliente
	 */
	public static void añadirClienteAFichero(String nombreFichero, Cliente cliente) {
		try {
			BufferedWriter fichero = new BufferedWriter(new FileWriter(nombreFichero,true));
			try {
				fichero.write(cliente.serialize());
				fichero.newLine();
			} finally {
				fichero.close();
			}
		}
		catch (FileNotFoundException fnfe) {
			      System.out.println("El archivo no se ha encontrado.");
		} catch (IOException ioe ) {
			throw new RuntimeException(ioe);
		}
	}
	/**
	 * Metodo que actualiza el fichero de la base de clientes, recibe la ruta del fichero y la lista de clientes
	 * 
	 * @param nombreFichero
	 * @param clientes
	 */
	public static void actualizarBaseDeClientes(String nombreFichero, ArrayList<Cliente> clientes) {
		try {
			BufferedWriter fichero = new BufferedWriter(new FileWriter(nombreFichero,false));
			try {
				for(Cliente cli: clientes) {
					fichero.write(cli.serialize());
					fichero.newLine();
				}
			} finally {
				fichero.close();
			}
		} catch (IOException ioe ) {
			throw new RuntimeException(ioe);
		}
	}
	/**
	 * Metodo que actualiza el fichero de la base de tickets, recibe la ruta del fichero y la lista de tickets
	 * 
	 * @param nombreFichero
	 * @param tickets
	 */
	public static void actualizarBaseTickets(String nombreFichero, ArrayList<Ticket> tickets) {
		try {
			BufferedWriter fichero = new BufferedWriter(new FileWriter(nombreFichero,false));
			try {
				for(Ticket t: tickets) {
					fichero.write(t.serialize());
					fichero.newLine();
				}
			} finally {
				fichero.close();
			}
		} catch (IOException ioe ) {
			throw new RuntimeException(ioe);
		}
	}
}
