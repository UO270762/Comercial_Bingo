package logica;

public class Cliente {

	public String documentoIdent;
	
	public String nombre;
	
	public String apellidos;
	
	public int saldo;
	
	public Cliente(String documentoIdent, String nombre, String apellidos, int saldo) {
		this.documentoIdent = documentoIdent;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.saldo = saldo;
	}

	public String getDocumentoIdent() {
		return documentoIdent;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public int getSaldo() {
		return saldo;
	}

	/**
	 * Metodo que serializa un cliente
	 * 
	 * @return el string del cliente searilzado para añadir al fichero
	 */
	public String serialize() {
		return this.documentoIdent +"@"+ this.nombre +"@"+ this.apellidos +"@"+ this.saldo;
	}
	
	/**
	 * Metodo que suma un bono de numero magico
	 */
	public void aumentarNumMagico() {
		this.saldo += 20;
	}	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apellidos == null) ? 0 : apellidos.hashCode());
		result = prime * result + ((documentoIdent == null) ? 0 : documentoIdent.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + saldo;
		return result;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if(this.documentoIdent.contentEquals(other.getDocumentoIdent())) {
			return true;
		} else {
			return false;
		}
	}
}
