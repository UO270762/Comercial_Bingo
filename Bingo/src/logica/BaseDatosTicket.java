package logica;


import java.util.ArrayList;

public class BaseDatosTicket {

	public static final String FICHERO_TICKETS = "files/tickets.dat";
	
	public ArrayList<Ticket> tickets;
	
	public BaseDatosTicket() {
		this.tickets = new ArrayList<Ticket>();
		cargarTickets();
	}

	private void cargarTickets() {
		FileUtil.cargarTicketFichero(FICHERO_TICKETS, tickets);
	}
	
	public ArrayList<Ticket> getTickets(){
		return this.tickets;
	}
	
	/**
	 * Metodo que comprueba si un ticket con el codigo que se pasa como parametro
	 * se encuentra en la base de datos
	 * 
	 * @param codigoValidar
	 * @return true si el ticket se encuentra, false si no
	 */
	public boolean validarTicket(String codigoValidar) {
		for(Ticket ticket: tickets) {
			if(ticket.getCodigoTicket().contentEquals(codigoValidar)
					&& ticket.getImporte() >= 30.0) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Establecer una nueva lista de tickets
	 * 
	 * @param lista
	 */
	public void establecerNuevaLista(ArrayList<Ticket> lista) {
		this.tickets = lista;
	}
	/**
	 * Metodo que actualiza la base de tickets
	 */
	public void actualizarBaseTickets() {
		FileUtil.actualizarBaseTickets(FICHERO_TICKETS, tickets);
	}
}
