package logica;

public class Premio {

	private String codigo;
	
	private String nombre;
	
	private char tipoPremio;
	
	public Premio(String codigo, String nombre, char tipoPremio) {
		this.setCodigo(codigo);
		this.setNombre(nombre);
		this.setTipoPremio(tipoPremio);
	}

	public String getCodigo() {
		return codigo;
	}

	private void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	private void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public char getTipoPremio() {
		return tipoPremio;
	}

	private void setTipoPremio(char tipoPremio) {
		this.tipoPremio = tipoPremio;
	}
	
	public String toString() {
		return this.nombre;
	}
}
