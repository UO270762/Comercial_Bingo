package logica;

import java.util.ArrayList;

public class Juego {

	public static final String FICHERO_PREMIOS  = "files/premios.dat";
	
	public static final int NUMERO_TIRADAS = 15;
	
	public CatalogoPremios catalogo;
	
	public BaseDatosClientes baseClientes;
	
	public BaseDatosTicket tickets;
	
	public Carton carton;
	
	public boolean bingo;
	
	public String codigoJuego;
	
	public int lineaSinCantarMarcada;
	
	public int numeroLineasCantadas;
	
	public int numeroDeTiradas;
	
	public boolean numeroMagicoObtenido;
	
	public Juego() {
		this.catalogo = new CatalogoPremios();
		this.carton = new Carton();
		this.baseClientes = new BaseDatosClientes();
		this.tickets = new BaseDatosTicket();
		this.bingo = false;
		this.numeroLineasCantadas = 0;
		this.lineaSinCantarMarcada = -1;
		this.numeroDeTiradas = NUMERO_TIRADAS;
		this.numeroMagicoObtenido = false;
		this.codigoJuego = "";
	}
	
	public Juego(int numeroDeTiradas) {
		this.catalogo = new CatalogoPremios();
		this.baseClientes = new BaseDatosClientes();
		this.tickets = new BaseDatosTicket();
		this.bingo = false;
		this.numeroLineasCantadas = 0;
		this.lineaSinCantarMarcada = -1;
		this.numeroDeTiradas = numeroDeTiradas;
		this.numeroMagicoObtenido = false;
	}
	
	/**
	 * Metodo que inicializa el carton
	 */
	public void inicializarCarton() {
		this.carton.asignarNumerosCarton();
		this.carton.asignarNumeroMagico();
	}
	/**
	 * Metodo que valida un ticket que recibe como parametro
	 * con aquellos que estan en la base de datos
	 * 
	 * @param codigoValidar
	 * @return true si se ecuentra en la base de datos, false si no
	 */
	public boolean validarTicket(String codigoValidar) {
		return tickets.validarTicket(codigoValidar);
	}
	
	/**
	 * Metodo que valida el documento de identificacion que
	 * se pasa como parametro.
	 * 
	 * @param documentoIdent
	 * @return true si esta registrado, false si no
	 */
	public boolean validarDocumentoIdent(String documentoIdent) {
		return baseClientes.validarDocumentoIdent(documentoIdent);
	}
	
	/**
	 * Metodo que devuelve el saldo de un cliente que se busca en la base
	 * con el documento identificativo que se pasa como parametro
	 * 
	 * @param documentoIdent
	 * @return el saldo del cliente
	 */
	public double getSaldoCliente(String documentoIdent) {
		Cliente cliente = baseClientes.getClienteByDocumento(documentoIdent);
		if(cliente != null) {
			return cliente.getSaldo();
		} else {
			return 0.0;
		}
	}
	
	/**
	 * Metodo que comprueba si la partida ha finalizado 
	 * (cuando se quede sin tiradas o cante bingo)
	 * 
	 * @return true si se ha acabado la partida, false si no
	 */
	public boolean isPartidaFinalizada() {
		if(this.numeroDeTiradas == 0 || this.bingo) {
			return true;
		} else {
			return false;
		}
	}
	
	public CatalogoPremios getCatalogo() {
		return catalogo;
	}

	public void setCatalogo(CatalogoPremios catalogo) {
		this.catalogo = catalogo;
	}

	public BaseDatosTicket getTickets() {
		return tickets;
	}

	public void setTickets(BaseDatosTicket tickets) {
		this.tickets = tickets;
	}

	public boolean isBingo() {
		return bingo;
	}

	public void setBingo(boolean bingo) {
		this.bingo = bingo;
	}

	public int getNumeroLineasCantadas() {
		return numeroLineasCantadas;
	}

	public void setNumeroLineasCantadas(int numeroLineasCantadas) {
		this.numeroLineasCantadas = numeroLineasCantadas;
	}

	public int getNumeroDeTiradas() {
		return numeroDeTiradas;
	}

	public void setNumeroDeTiradas(int numeroDeTiradas) {
		this.numeroDeTiradas = numeroDeTiradas;
	}

	public boolean isNumeroMagicoObtenido() {
		return numeroMagicoObtenido;
	}

	public void setNumeroMagicoObtenido(boolean numeroMagicoObtenido) {
		this.numeroMagicoObtenido = numeroMagicoObtenido;
	}

	public BaseDatosClientes getBaseClientes() {
		return baseClientes;
	}

	public void setCarton(Carton carton) {
		this.carton = carton;
	}

	public Carton getCarton() {
		return carton;
	}
	
	public void quitarTirada() {
		this.numeroDeTiradas--;
	}

	/**
	 * Metodo que marca una posicion en el array que recibe como string de formato x#y
	 * 
	 * @param idBoton
	 */
	public void marcar(String idBoton) {
		String[] posicionMarcar = idBoton.split("#");
		int x = Integer.valueOf(posicionMarcar[0]);
		int y = Integer.valueOf(posicionMarcar[1]);
		carton.marcarNumero(x,y);
		if(carton.sePuedeCantarLaLinea(x)) {
			this.lineaSinCantarMarcada = x;
		}
	}
	
	/**
	 * Metodo que devuelve si hay lineas marcadas sin cantar
	 * 
	 * @return true si la hay, false si no hay
	 */
	public boolean sePuedeCantarLinea() {
		return (this.lineaSinCantarMarcada >= 0);
	}

	/**
	 * Metodo que resetea la lineaMarcadaSinCantar
	 */
	public void resetLineaSinCantar() {
		this.lineaSinCantarMarcada = -1;
	}
	/**
	 * Metodo que incrementa el numero de lineas cantadas, comprueba si la linea
	 * a cantar contiene el numero magico y restaura el atributo de la linea
	 * marcada sin cantar
	 */
	public void cantarLinea() {
		this.numeroLineasCantadas++;
		numMagicoEnLineaCantanda(this.lineaSinCantarMarcada);
		resetLineaSinCantar();
	}
	/**
	 * Metodo que canta bingo
	 */
	public void cantarBingo() {
		this.bingo = true;
		this.numeroMagicoObtenido = true;
	}
	
	/**
	 * Metodo que devuelve si hay bingo
	 * 
	 * @return true si hay bingo, false si no
	 */
	public boolean hayBingo() {
		return this.carton.hayBingo();
	}
	
	/**
	 * Metodo que comprueba si en una linea cantada esta el numero magico 
	 * 
	 * @param lineaCantada
	 */
	public void numMagicoEnLineaCantanda(int lineaCantada) {
		int lineaNumMagico = carton.lineaDelNumeroMagico();
		if(lineaCantada == lineaNumMagico) {
			this.numeroMagicoObtenido = true; 
		}
	}
	
	/**
	 * Metodo que devuelve un ArrayList con una lista de premios que corresponde
	 * segun el premio
	 * 
	 * @return arrayList con los premios o una vacia si no hay
	 */
	public ArrayList<Premio> getListaPremiosPartida() {
		if(this.bingo) {
			return catalogo.filtrarPremiosBingo();
		} else if(this.numeroLineasCantadas > 0) {
			return catalogo.filtrarPremiosLinea();
		} else {
			return new ArrayList<Premio>();
		}
	}
	
	/**
	 * Metodo que devuelve el nume de premios a esocger (independiente de tipo)
	 * 
	 * @return 1 si bingo o una linea cantada, 2 si dos lineas cantadas
	 */
	public int numeroDePremiosAEscoger() {
		if(this.bingo) {
			return 1;
		} else {
			return numeroLineasCantadas;
		}
	}

	/**
	 * Metodo que registra un jugador en la base de jugadores
	 * 
	 * @param cliente
	 */
	public void registrarJugadorEnBase(Cliente cliente) {
		this.baseClientes.registrarJugadorEnBase(cliente);
	}
	
	/**
	 * Metodo que devuelve un Cliente mediante el objeto de identificacion
	 * 
	 * @param id
	 * @return cliente con ese documento identificativo
	 */
	public Cliente getClientePorIdentificacion(String id) {
		return baseClientes.getClienteByDocumento(id);
	}
	/**
	 * Metodo que aumenta al saldo de un cliente los 20 de euros de un nuevo numero magico
	 * 
	 * @param id
	 */
	public void aumentarSaldoNumMagico(String id) {
		this.baseClientes.getClienteByDocumento(id).aumentarNumMagico();
	}

	/**
	 * Metodo que actualiza el fichero con la base de los clientes
	 */
	public void actualizarBaseDatosClientes() {
		this.baseClientes.actualizarBaseClientes();
	}
	/**
	 * Metodo que establece el ticket de juego por el que se pasa como parametro
	 * 
	 * @param codigo
	 */
	public void setTicketJuego(String codigo) {
		this.codigoJuego = codigo;
	}
	/**
	 * Metodo que elimina un ticket de juego gastado
	 */
	public void eliminaTicket() {
		ArrayList<Ticket> nuevaLista = new ArrayList<Ticket>();
		for(int i = 0; i < tickets.getTickets().size(); i++) {
			if(((tickets.getTickets().get(i)).getCodigoTicket()).contentEquals(this.codigoJuego)) {
				//Lo omite
			} else {
				nuevaLista.add(tickets.getTickets().get(i));
			}
		}
		tickets.establecerNuevaLista(nuevaLista);
		
		actualizarBaseDatosTickets();
	}
	/**
	 * Metodo que actualiza el fichero de tickets
	 */
	private void actualizarBaseDatosTickets() {
		this.tickets.actualizarBaseTickets();
	}
	/**
	 * Metodo que quita el bingo
	 */
	public void quitarBingo() {
		this.bingo = false;
	}
}
