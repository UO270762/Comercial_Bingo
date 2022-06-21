package logica;

public class Ticket {

	private String codigoTicket;
	
	private double importe;
	
	public Ticket(String codigoTicket, double importe) {
		this.codigoTicket = codigoTicket;
		this.importe = importe;
	}

	public double getImporte() {
		return importe;
	}

	public String getCodigoTicket() {
		return codigoTicket;
	}
	
	public String serialize() {
		return this.codigoTicket + "@" + this.importe;
	}
}
