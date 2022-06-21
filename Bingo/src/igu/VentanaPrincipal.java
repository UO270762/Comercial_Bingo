package igu;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;

import javax.help.*;
import java.net.*;
import java.io.*;

import logica.Casilla;
import logica.Juego;
import logica.Premio;

import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.LineBorder;
import java.awt.Font;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

public class VentanaPrincipal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelCarton;
	private JPanel panelInfo;
	private JPanel panelCartonNum;
	private JPanel panelBotonSiguiente;
	private JButton btnSiguiente;
	private JButton buttonCarton;
	private JLabel labelBrillos1;
	private JPanel panelBotonesCarton;
	private JButton btnCantarLinea;
	private JButton btnBingo;
	private JLabel lblbingoCompraY;
	private JLabel labelBrillos2;
	private JMenuBar menuBar;
	private JMenu mnAyuda;
	private JSeparator separator;
	private JPanel panelBombo;
	private JPanel panelEstado;
	private JLabel labelNumeroBombo;
	private JTextField textFieldNumBombo;
	private JLabel labelLineasCantadas;
	private JTextField textFieldLineasCantadas;
	private JLabel labelTiradasRestantes;
	private JTextField textFieldTiradasRestantes;
	private JSeparator separator_1;

	public static final int TAMANO_PANEL_CARTON = 9;
	
	public static final int TAMANO_CARTON = 3;
	
	private Juego juego;
	private ProcesaBotonCarton pbc;
	
	private Locale localizacion;
	
	public int numeroTirada;
	
	private ArrayList<Integer> numerosDelBombo;
	private JButton btnSiguienteTirada;
	private JMenuItem mntmAcercaDe;
	private JSeparator separator_2;
	private JMenuItem mntmContenidos;

	public class ProcesaBotonCarton implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton boton = (JButton) e.getSource();
			String idBoton = boton.getActionCommand();
			marcarEnCarton(idBoton);
			boton.setEnabled(false);
			boton.setBackground(new Color( 222, 218, 218));
		}
	}
	/**
	 * Create the frame.
	 * @param localizacion 
	 */
	public VentanaPrincipal(Juego juego, Locale localizacion) {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				adaptaImagenBombo();
			}
		});
		this.juego = juego;
		this.pbc = new ProcesaBotonCarton();
		this.numeroTirada = 0;
		this.localizacion = localizacion;
		cargaAyuda();
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaPrincipal.class.getResource("/img/iconos/Icono.png")));
		setTitle("Bingo juega y gana");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 679, 744);
		setJMenuBar(getMenuBar_1());
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanelCarton(), BorderLayout.CENTER);
		contentPane.add(getPanelInfo(), BorderLayout.NORTH);
		contentPane.add(getPanelBotonSiguiente(), BorderLayout.SOUTH);
		setLocationRelativeTo(null);
		
		this.setMinimumSize(new Dimension(450, 400));
		
		if(!localizacion.equals(new Locale("es"))) {
			localizar();
		}
		
		iniciaJuego();
	}

	private JPanel getPanelCarton() {
		if (panelCarton == null) {
			panelCarton = new JPanel();
			panelCarton.setLayout(new GridLayout(2, 2, 0, 0));
			panelCarton.add(getPanelCartonNum());
			panelCarton.add(getPanelBombo());
			panelCarton.add(getPanelBotonesCarton());
			panelCarton.add(getPanelEstado());
		}
		return panelCarton;
	}
	private JPanel getPanelInfo() {
		if (panelInfo == null) {
			panelInfo = new JPanel();
			panelInfo.setBackground(Color.WHITE);
			panelInfo.add(getLabelBrillos1());
			panelInfo.add(getLblbingoCompraY());
			panelInfo.add(getLabelBrillos2());
		}
		return panelInfo;
	}
	private JPanel getPanelCartonNum() {
		if (panelCartonNum == null) {
			panelCartonNum = new JPanel();
			panelCartonNum.setBackground(Color.WHITE);
			panelCartonNum.setBorder(new LineBorder(new Color(255, 200, 0), 3, true));
			panelCartonNum.setLayout(new GridLayout(3, 3, 0, 0));
		}
		return panelCartonNum;
	}
	private JPanel getPanelBotonSiguiente() {
		if (panelBotonSiguiente == null) {
			panelBotonSiguiente = new JPanel();
			panelBotonSiguiente.setLayout(new GridLayout(1, 1, 0, 0));
			panelBotonSiguiente.add(getBtnSiguiente());
		}
		return panelBotonSiguiente;
	}
	private JButton getBtnSiguiente() {
		if (btnSiguiente == null) {
			btnSiguiente = new JButton("Siguiente");
			btnSiguiente.setMnemonic('g');
			btnSiguiente.setEnabled(false);
			btnSiguiente.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(!hayNumeroSinMarcar() && !hayLineaSinCantar() && !hayBingoSinCantar()) {
						if(opcionAPremios()) {
							habilitarVentanaPremios();
						} else {
							habilitarVentanaFinal();
						}
					} else if(hayNumeroSinMarcar()) {
						tiradaSinCantarSiguiente();
					} else if(hayLineaSinCantar()) {
						lineaSinCantarSiguiente();
					} else if(hayBingoSinCantar()) {
						bingoSinCantarSiguiente();
					}
				}
			});
			btnSiguiente.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 18));
			btnSiguiente.setBackground(new Color(46, 139, 87));
		}
		return btnSiguiente;
	}
	private JLabel getLabelBrillos1() {
		if (labelBrillos1 == null) {
			labelBrillos1 = new JLabel("");
			labelBrillos1.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/img/iconos/Brillos.png")));
		}
		return labelBrillos1;
	}
	private JPanel getPanelBotonesCarton() {
		if (panelBotonesCarton == null) {
			panelBotonesCarton = new JPanel();
			panelBotonesCarton.setBackground(Color.WHITE);
			panelBotonesCarton.setLayout(new GridLayout(5, 1, 0, 0));
			panelBotonesCarton.add(getSeparator());
			panelBotonesCarton.add(getBtnCantarLinea());
			panelBotonesCarton.add(getSeparator_1());
			panelBotonesCarton.add(getBtnBingo());
		}
		return panelBotonesCarton;
	}
	private JButton getBtnCantarLinea() {
		if (btnCantarLinea == null) {
			btnCantarLinea = new JButton("Cantar linea");
			btnCantarLinea.setMnemonic('c');
			btnCantarLinea.setEnabled(false);
			btnCantarLinea.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cantarLinea();
				}
			});
			btnCantarLinea.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 15));
		}
		return btnCantarLinea;
	}
	private JButton getBtnBingo() {
		if (btnBingo == null) {
			btnBingo = new JButton("Cantar bingo");
			btnBingo.setMnemonic('n');
			btnBingo.setEnabled(false);
			btnBingo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cantarBingo();
				}
			});
			btnBingo.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 15));
		}
		return btnBingo;
	}
	
	private JLabel getLblbingoCompraY() {
		if (lblbingoCompraY == null) {
			lblbingoCompraY = new JLabel("-Bingo compra y \u00A1gana!-");
			lblbingoCompraY.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 18));
		}
		return lblbingoCompraY;
	}
	private JLabel getLabelBrillos2() {
		if (labelBrillos2 == null) {
			labelBrillos2 = new JLabel("");
			labelBrillos2.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/img/iconos/Brillos.png")));
		}
		return labelBrillos2;
	}
	private JMenuBar getMenuBar_1() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			menuBar.add(getMnAyuda());
		}
		return menuBar;
	}
	private JMenu getMnAyuda() {
		if (mnAyuda == null) {
			mnAyuda = new JMenu("Ayuda");
			mnAyuda.setMnemonic('y');
			mnAyuda.add(getMntmContenidos());
			mnAyuda.add(getSeparator_2());
			mnAyuda.add(getMntmAcercaDe());
		}
		return mnAyuda;
	}
	private JSeparator getSeparator() {
		if (separator == null) {
			separator = new JSeparator();
		}
		return separator;
	}
	private JPanel getPanelBombo() {
		if (panelBombo == null) {
			panelBombo = new JPanel();
			panelBombo.setBackground(Color.WHITE);
			panelBombo.setLayout(new GridLayout(1, 1, 0, 0));
			panelBombo.add(getButtonSiguienteTirada());
		}
		return panelBombo;
	}
	private JPanel getPanelEstado() {
		if (panelEstado == null) {
			panelEstado = new JPanel();
			panelEstado.setFont(new Font("Source Sans Pro Black", Font.BOLD, 12));
			panelEstado.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Estado", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			panelEstado.setBackground(Color.WHITE);
			panelEstado.setLayout(new GridLayout(3, 2, 0, 0));
			panelEstado.add(getLabelNumeroBombo());
			panelEstado.add(getTextFieldNumBombo());
			panelEstado.add(getLabelLineasCantadas());
			panelEstado.add(getTextFieldLineasCantadas());
			panelEstado.add(getLabelTiradasRestantes());
			panelEstado.add(getTextFieldTiradasRestantes());
		}
		return panelEstado;
	}
	private JLabel getLabelNumeroBombo() {
		if (labelNumeroBombo == null) {
			labelNumeroBombo = new JLabel("Numero del bombo:");
			labelNumeroBombo.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 14));
		}
		return labelNumeroBombo;
	}
	private JTextField getTextFieldNumBombo() {
		if (textFieldNumBombo == null) {
			textFieldNumBombo = new JTextField();
			textFieldNumBombo.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldNumBombo.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 20));
			textFieldNumBombo.setEditable(false);
			textFieldNumBombo.setDisabledTextColor(Color.WHITE);
			textFieldNumBombo.setColumns(10);
		}
		return textFieldNumBombo;
	}
	private JLabel getLabelLineasCantadas() {
		if (labelLineasCantadas == null) {
			labelLineasCantadas = new JLabel("Lineas cantadas:");
			labelLineasCantadas.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 14));
		}
		return labelLineasCantadas;
	}
	private JTextField getTextFieldLineasCantadas() {
		if (textFieldLineasCantadas == null) {
			textFieldLineasCantadas = new JTextField();
			textFieldLineasCantadas.setText("0");
			textFieldLineasCantadas.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldLineasCantadas.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 20));
			textFieldLineasCantadas.setEditable(false);
			textFieldLineasCantadas.setDisabledTextColor(Color.WHITE);
			textFieldLineasCantadas.setColumns(10);
		}
		return textFieldLineasCantadas;
	}
	private JLabel getLabelTiradasRestantes() {
		if (labelTiradasRestantes == null) {
			labelTiradasRestantes = new JLabel("Tiradas Restantes:");
			labelTiradasRestantes.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 14));
		}
		return labelTiradasRestantes;
	}
	private JTextField getTextFieldTiradasRestantes() {
		if (textFieldTiradasRestantes == null) {
			textFieldTiradasRestantes = new JTextField();
			textFieldTiradasRestantes.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldTiradasRestantes.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 20));
			textFieldTiradasRestantes.setEditable(false);
			textFieldTiradasRestantes.setDisabledTextColor(Color.WHITE);
			textFieldTiradasRestantes.setColumns(10);
		}
		return textFieldTiradasRestantes;
	}
	private JSeparator getSeparator_1() {
		if (separator_1 == null) {
			separator_1 = new JSeparator();
			separator_1.setForeground(new Color(255, 255, 255));
		}
		return separator_1;
	}
	private JButton getButtonSiguienteTirada() {
		if (btnSiguienteTirada == null) {
			btnSiguienteTirada =   new JButton("Siguiente Tirada");
			btnSiguienteTirada.setMnemonic('S');
			btnSiguienteTirada.setContentAreaFilled(false);
			btnSiguienteTirada.setBorderPainted(false);
			btnSiguienteTirada.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(!hayNumeroSinMarcar() && !hayLineaSinCantar() && !hayBingoSinCantar()) {
						sacarNumeroBombo();
					} else if(hayNumeroSinMarcar()){
						tiradaSinMarcar();
					} else if(hayLineaSinCantar()) {
						lineaSinCantar();
					} else if(hayBingoSinCantar()) {
						bingoSinCantar();
					}
				}
			});
			btnSiguienteTirada.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnSiguienteTirada.setVerticalAlignment(SwingConstants.BOTTOM);
			btnSiguienteTirada.setHorizontalTextPosition(SwingConstants.CENTER);
			btnSiguienteTirada.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 14));
		}
		return btnSiguienteTirada;
	}
	private JSeparator getSeparator_2() {
		if (separator_2 == null) {
			separator_2 = new JSeparator();
		}
		return separator_2;
	}
	private JMenuItem getMntmAcercaDe() {
		if (mntmAcercaDe == null) {
			mntmAcercaDe = new JMenuItem("Acerca de");
			mntmAcercaDe.setMnemonic('r');
			mntmAcercaDe.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
			mntmAcercaDe.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					mostrarCopyright();
				}
			});
		}
		return mntmAcercaDe;
	}
	/**
	 * Metodo que comprueba antes de pasar que hay una tirada sin cantar
	 */
	protected void tiradaSinCantarSiguiente() {
		ResourceBundle textos = ResourceBundle.getBundle("rcs/texto",localizacion);
		
		int res = JOptionPane.showConfirmDialog(this, textos.getString("textoSacarNumero"),"ADVERTENCIA", JOptionPane.CANCEL_OPTION);
		if(res == JOptionPane.YES_OPTION ) {
			for(int i = 0; i < TAMANO_PANEL_CARTON; i++) {
				JButton boton = (JButton) panelCartonNum.getComponent(i);
				if(boton.isEnabled()) {
					boton.setEnabled(false);
				}
			}
		}
	}
	/**
	 * Metodo que comprueba antes de pasar a siguiente ventana 
	 * 
	 */
	protected void lineaSinCantarSiguiente() {
		ResourceBundle textos = ResourceBundle.getBundle("rcs/texto",localizacion);
		
		int res = JOptionPane.showConfirmDialog(this, textos.getString("textoAvisoLinea"),"ADVERTENCIA", JOptionPane.CANCEL_OPTION);
		if(res == JOptionPane.YES_OPTION ) {
			juego.resetLineaSinCantar();
			btnCantarLinea.setEnabled(false);
		}
	}

	/**
	 * Comprueba si hay linea que se puede cantar 
	 * 
	 * @return true si el boton de cantar esta activo
	 */
	public boolean hayLineaSinCantar() {
		return btnCantarLinea.isEnabled();
	}
	/**
	 * 
	 */
	protected void lineaSinCantar() {
		ResourceBundle textos = ResourceBundle.getBundle("rcs/texto",localizacion);
		
		int res = JOptionPane.showConfirmDialog(this, textos.getString("textoAvisoLinea"),"ADVERTENCIA", JOptionPane.CANCEL_OPTION);
		if(res == JOptionPane.YES_OPTION ) {
			juego.resetLineaSinCantar();
			btnCantarLinea.setEnabled(false);
			sacarNumeroBombo();
		}
	}
	
	/**
	 * Metodo que simula sacar un numero del bombo
	 */
	protected void sacarNumeroBombo() {
		this.numeroTirada = numeroTirada + 1;
		
		if(numeroTirada == 1) {
			this.juego.eliminaTicket();
		}
		Random r = new Random();
		int numeroBombo = r.nextInt(30) + 1;
		while(numerosDelBombo.contains(numeroBombo)) {
			numeroBombo = r.nextInt(30) + 1;
		}
		numerosDelBombo.add(numeroBombo);
		JButton botonNumero = buscarBotonSegunNumero(numeroBombo);
		if(botonNumero != null) {
			botonNumero.setEnabled(true);
			botonNumero.grabFocus();
			this.getRootPane().setDefaultButton(botonNumero);
		}
		textFieldNumBombo.setText(Integer.toString(numeroBombo));
		juego.quitarTirada();
		if(juego.numeroDeTiradas == 0) {
			finalizarJuego();
		}
		ResourceBundle textos = ResourceBundle.getBundle("rcs/texto",localizacion);
		
		JOptionPane.showMessageDialog(this, textos.getString("textoNumeroTirada") + " " + numeroBombo);
		textFieldTiradasRestantes.setText(Integer.toString(juego.getNumeroDeTiradas()));
	}

	/**
	 * Metodo que devuelve el boton con el numero asignado
	 * que se pasa como parametro, si no esta devuelve null
	 * 
	 * @param numeroBombo
	 * @return boton con el numero que se pasa como parametro o null si no esta en el panel
	 */
	private JButton buscarBotonSegunNumero(int numeroBombo) {
		for(int i = 0; i < TAMANO_PANEL_CARTON; i++) {
			JButton boton = (JButton) panelCartonNum.getComponent(i);
			int numeroEnBoton = Integer.valueOf(boton.getText());
			if(numeroEnBoton == numeroBombo) {
				return boton;
			}
		}
		return null;
	}
	/**
	 * Metodo que canta una linea
	 */
	protected void cantarLinea() {
		juego.cantarLinea();
		textFieldLineasCantadas.setText(Integer.toString(juego.getNumeroLineasCantadas()));
		btnCantarLinea.setEnabled(false);
	}
	/**
	 * Metodo que canta bingo
	 */
	protected void cantarBingo() {
		juego.cantarBingo();
		btnSiguiente.setEnabled(true);
		btnBingo.setEnabled(false);
		btnSiguienteTirada.setEnabled(false);
	}
	/**
	 * Metodo si se tiene un bingo sin cantar
	 */
	protected void bingoSinCantar() {
		ResourceBundle textos = ResourceBundle.getBundle("rcs/texto",localizacion);
		
		int res = JOptionPane.showConfirmDialog(this, textos.getString("textoAvisoBingo"),"ADVERTENCIA", JOptionPane.CANCEL_OPTION);
		if(res == JOptionPane.YES_OPTION ) {
			juego.quitarBingo();
			btnBingo.setEnabled(false);
			sacarNumeroBombo();
		}
	}
	/**
	 * Metodo que devuelve el bingo sin cantar
	 * 
	 * @return si el boton de bingo esta activo
	 */
	protected boolean hayBingoSinCantar() {
		return btnBingo.isEnabled();
	}

	private JButton getButtonCarton() {
		buttonCarton = new JButton("");
		buttonCarton.setEnabled(false);
		buttonCarton.setForeground(new Color(0,0,0));
		buttonCarton.addActionListener(pbc);
		buttonCarton.setFont(new Font("Source Sans Pro Black", Font.BOLD, 25));
		
		return buttonCarton;
	}
	/**
	 * metodo que devuelve si en la partida se tiene derecho a algun premio
	 * 
	 * @return true si tiene derecho, false si no
	 */
	protected boolean opcionAPremios() {
		if(juego.numeroDePremiosAEscoger() > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Metodo que habilita la ventana de premios y cierra esta
	 */
	protected void habilitarVentanaPremios() {
		VentanaPremios vp = new VentanaPremios(this.juego,this.localizacion);
		vp.setVisible(true);
		this.dispose();
	}
	/**
	 * Metodo que habilita la ventana final y cierra esta
	 */
	protected void habilitarVentanaFinal() {
		ArrayList<Premio> ret = new ArrayList<Premio>();
		VentanaFinal vf = new VentanaFinal(ret,localizacion);
		this.dispose();
		vf.setVisible(true);
	}
	/**
	 * Metodo que inica el juego
	 */
	public void iniciaJuego() {
		textFieldTiradasRestantes.setText(Integer.toString(juego.getNumeroDeTiradas()));
		cargarBotones();
		this.numerosDelBombo = new ArrayList<Integer>();
		juego.inicializarCarton();
		asignarNumerosCarton();
		asociarImagenBombo();
	}
	/**
	 * Metodo que finaliza el juego
	 */
	public void finalizarJuego() {
		ResourceBundle textos = ResourceBundle.getBundle("rcs/texto",localizacion);
		
		JOptionPane.showMessageDialog(this, textos.getString("textoUltimaTirada"));
		btnSiguienteTirada.setEnabled(false);
		btnSiguiente.setEnabled(true);
		
	}
	/**
	 * Metodo que asigna a los botones del carton, sus valores de la Casilla
	 * correspondiente en la matriz Carton
	 */
	private void asignarNumerosCarton() {
		Casilla[][] casillas = juego.getCarton().getCasillas();
		int numCasilla = 0;
		for(int i = 0; i < TAMANO_CARTON; i++) {
			for(int j = 0; j < TAMANO_CARTON; j++) {
				JButton boton = (JButton) panelCartonNum.getComponent(numCasilla);
				boton.setText(Integer.toString(casillas[i][j].getNumero()));
				boton.setActionCommand(i+"#"+j);
				if(casillas[i][j].isNumeroMagico) {
					boton.setBackground(new Color(179, 169, 13));
				} else {
					boton.setBackground(new Color(44, 65, 171));
				}
				numCasilla++;
			}
		}
		
	}
	/**
	 * Metodo que carga los botones al tablero carton
	 */
	private void cargarBotones() {
		for(int i = 0; i < TAMANO_PANEL_CARTON; i++) {
			panelCartonNum.add(getButtonCarton());
		}
	}
	
	/**
	 * Metodo que marca en el carton 
	 * 
	 * @param idBoton id con la posicion en el carton en formato x#y
	 */
	public void marcarEnCarton(String idBoton) {
		juego.marcar(idBoton);
		if(juego.hayBingo()) {
			btnBingo.setEnabled(true);
		}
		else if(juego.sePuedeCantarLinea() && !juego.hayBingo()) {
			btnCantarLinea.setEnabled(true);
		}
	}
	
	/**
	 * Metodo que informa la usuario de que va a tirar sin haber marcado previamente un numero
	 * de su carton
	 */
	public void tiradaSinMarcar() {
		ResourceBundle textos = ResourceBundle.getBundle("rcs/texto",localizacion);
		
		int res = JOptionPane.showConfirmDialog(this, textos.getString("textoSacarNumero"),"ADVERTENCIA", JOptionPane.CANCEL_OPTION);
		if(res == JOptionPane.YES_OPTION ) {
			for(int i = 0; i < TAMANO_PANEL_CARTON; i++) {
				JButton boton = (JButton) panelCartonNum.getComponent(i);
				if(boton.isEnabled()) {
					boton.setEnabled(false);
				}
			}
			sacarNumeroBombo();
		}
	}
	
	/**
	 * Comrpueba si hay algun numero que podria ser marcado sin marcar
	 * 
	 * @return true si lo hay, false si no
	 */
	public boolean hayNumeroSinMarcar() {
		for(int i = 0; i < TAMANO_PANEL_CARTON; i++) {
			JButton boton = (JButton) panelCartonNum.getComponent(i);
			if(boton.isEnabled()) {
				return true;
			}
		}
		return false;
	}
	protected void asociarImagenBombo() {
		JButton boton = (JButton) getButtonSiguienteTirada();
		String fichero = "/img/iconos/Bombo.png";
		boton.setIcon(cargaImagen(fichero));
	}
	/**
	 * Metodo que carga la imagen a un boton
	 * 
	 * @param fichero
	 * @return icono de imagem
	 */
	private ImageIcon cargaImagen(String fichero) {
		return new ImageIcon(VentanaPremios.class.getResource(fichero));
	}
	/**
	 * Metodo que adapta la imagen del bombo
	 */
	protected void adaptaImagenBombo() {
		String rutaImagen = "/img/iconos/Bombo.png";
		setImagenAdaptada(getButtonSiguienteTirada(), rutaImagen);
	}
	/**
	 * Metodo que establece la imagen adaptada
	 * 
	 * @param boton
	 * @param rutaImagen
	 */
	private void setImagenAdaptada(JButton boton, String rutaImagen){
		Image imgOriginal = new ImageIcon(getClass().getResource(rutaImagen)).getImage(); 
		Image imgEscalada = imgOriginal.getScaledInstance(boton.getWidth(),boton.getHeight(), Image.SCALE_FAST);
		ImageIcon icon = new ImageIcon(imgEscalada);
		boton.setIcon(icon);
	}
	
	/**
	 * Metodo que muestra un mensaje de Copyright
	 */
	protected void mostrarCopyright() {
		ResourceBundle textos = ResourceBundle.getBundle("rcs/texto",localizacion);
		
		JOptionPane.showMessageDialog(this, textos.getString("textoCopyright"));
	}
	/**
	 * Metodo que gestiona si se quiere pasar de ventana con bingo disponible
	 */
	protected void bingoSinCantarSiguiente() {
		ResourceBundle textos = ResourceBundle.getBundle("rcs/texto",localizacion);
		
		int res = JOptionPane.showConfirmDialog(this, textos.getString("textoAvisoBingo"),"ADVERTENCIA", JOptionPane.CANCEL_OPTION);
		if(res == JOptionPane.YES_OPTION ) {
			juego.quitarBingo();
			btnBingo.setEnabled(false);
		}
	}
	/**
	 * Metodo que carga la ayuda
	 */
	private void cargaAyuda(){

		   URL hsURL;
		   HelpSet hs;

		    try {
			    	File fichero = new File("help/ayuda.hs");
			    	hsURL = fichero.toURI().toURL();
			        hs = new HelpSet(null, hsURL);
			      }

		    catch (Exception e){
		      System.out.println("Ayuda no encontrada");
		      return;
		   }

		   HelpBroker hb = hs.createHelpBroker();

		   hb.enableHelpKey(getRootPane(),"introduccion", hs);
		   hb.enableHelpOnButton(getMntmContenidos(), "como jugar", hs);
	}
	/**
	 * Metodo que localiza la aplicacion si es necesario
	 */
	private void localizar() {
		ResourceBundle textos = ResourceBundle.getBundle("rcs/texto",localizacion);
		
		setTitle(textos.getString("textoPrincipalTitulo"));
		
		getLblbingoCompraY().setText(textos.getString("textoTituloPrincipal"));
		
		getButtonSiguienteTirada().setText(textos.getString("textoBomboSiguienteTiradaPrincipal"));
		
		getBtnCantarLinea().setText(textos.getString("textoCantarLineaPrincipal"));
		
		getBtnBingo().setText(textos.getString("textoCantarBingoPrincipal"));
		
		panelEstado.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), textos.getString("textoTituloEstado"), TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		getLabelNumeroBombo().setText(textos.getString("textoNumBomboPrincipal"));
		
		getLabelLineasCantadas().setText(textos.getString("textoLineasCantadasPrincipal"));
		
		getLabelTiradasRestantes().setText(textos.getString("textoTiradaRestPrincipal"));
		
		getBtnSiguiente().setText(textos.getString("textoSiguiente"));
		
		getMnAyuda().setText(textos.getString("textoAyuda"));
		
		getMntmContenidos().setText(textos.getString("textoContenidosPrincipal"));
		
		getMntmAcercaDe().setText(textos.getString("textoAcercaDePrincipal"));
		
		getMnAyuda().setMnemonic(textos.getString("nmmAyuda").charAt(0));
		
		getMntmContenidos().setMnemonic(textos.getString("nmmContenidos").charAt(0));
		
		getMntmAcercaDe().setMnemonic(textos.getString("nmmAcercaDe").charAt(0));
		
		getButtonSiguienteTirada().setMnemonic(textos.getString("nmmSiguienteTirada").charAt(0));
		
		getBtnCantarLinea().setMnemonic(textos.getString("nmmCantarLinea").charAt(0));
		
		getBtnBingo().setMnemonic(textos.getString("nmmCantarBingo").charAt(0));
		
		getBtnSiguiente().setMnemonic(textos.getString("nmmSiguiente").charAt(0));
	}
	private JMenuItem getMntmContenidos() {
		if (mntmContenidos == null) {
			mntmContenidos = new JMenuItem("Contenidos");
			mntmContenidos.setMnemonic('n');
		}
		return mntmContenidos;
	}
}
