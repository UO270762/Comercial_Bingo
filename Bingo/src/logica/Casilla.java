package logica;

public class Casilla {

	public int numero;

	public boolean isSelected;
	
	public boolean isNumeroMagico;
	
	public Casilla() {
		this.numero = -1;
		this.isSelected = false;
		this.isNumeroMagico = false;
	}
	public Casilla(int numero, boolean isSelected, boolean isNumeroMagico) {
		this.numero = numero;
		this.isSelected = isSelected;
		this.isNumeroMagico = isNumeroMagico;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public boolean isNumeroMagico() {
		return isNumeroMagico;
	}

	public void setNumeroMagico(boolean isNumeroMagico) {
		this.isNumeroMagico = isNumeroMagico;
	}
}
