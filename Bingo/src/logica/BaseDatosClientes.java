package logica;

import java.util.ArrayList;

public class BaseDatosClientes {

	public static final String FICHERO_CLIENTES = "files/clientes.dat";
	
	public ArrayList<Cliente> baseCliente;
	
	public BaseDatosClientes() {
		this.baseCliente = new ArrayList<Cliente>();
		cargarClientes();
	}

	private void cargarClientes() {
		FileUtil.cargarClientesFichero(FICHERO_CLIENTES, this.baseCliente);
	}
	
	public ArrayList<Cliente> getClientes() {
		return this.baseCliente;
	}
	
	/**
	 * Metodo que valida el documento de identificacion que se le pasa como
	 * parametro, con los que se cargar del fichero
	 * 
	 * @param documentoIdent
	 * @return true si esta registrado, false si no
	 */
	public boolean validarDocumentoIdent(String documentoIdent) {
		for(Cliente cliente: baseCliente) {
			if(cliente.getDocumentoIdent().contentEquals(documentoIdent)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Metodo que devuelve el cliente que tiene como documento de identficacion
	 * aquel que se pasa como parametro
	 * 
	 * @param documentoIdent 
	 * @return el cliente que tiene cono documento de identificaion el que se pasa
	 * 			como parametro en caso contrario null
	 */
	public Cliente getClienteByDocumento(String documentoIdent) {
		for(Cliente cliente: baseCliente) {
			if(cliente.getDocumentoIdent().contentEquals(documentoIdent)) {
				return cliente;
			}
		}
		return null;
	}
	
	/**
	 * Metodo que registra un jugador en la base
	 * 
	 * @param cliente
	 */
	public void registrarJugadorEnBase(Cliente cliente) {
		baseCliente.add(cliente);
		registrarEnBase(cliente);
	}
	
	/**
	 * Metodo que registra en el fichero de la base de cliente uno nuevo
	 * 
	 * @param cliente
	 */
	private void registrarEnBase(Cliente cliente) {
		FileUtil.añadirClienteAFichero(FICHERO_CLIENTES, cliente);
	}
	
	/**
	 * Metodo que actualiza el fichero de clientes
	 */
	public void actualizarBaseClientes() {
		FileUtil.actualizarBaseDeClientes(FICHERO_CLIENTES, baseCliente);
	}
}
