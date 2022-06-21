package igu;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import java.awt.Image;

import javax.help.*;
import java.net.*;
import java.io.*;

import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.LineBorder;
import javax.swing.JList;
import javax.swing.JOptionPane;

import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import logica.Juego;
import logica.Premio;

import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class VentanaPremios extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelContent;
	private JPanel panelPremios;
	private JPanel panelCabecera;
	private JScrollPane scrollPanePremios;
	private JLabel label;
	private JLabel label_1;
	private JTextArea txtrseleccioneDeLa;
	@SuppressWarnings("rawtypes")
	private JList listPremios;
	private JPanel panelBotones;
	private JButton buttonPremios;
	private JButton btnQuitarPremio;
	private JButton btnSiguiente;
	private JButton btnAyuda;

	private Juego juego;
	
	private int premiosSeleccionados;
	
	private ProcesaBotonPremios pbp;
	
	private Locale localizacion;
	
	private DefaultListModel<Premio> dfp;
	
	public class ProcesaBotonPremios implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton boton = (JButton) e.getSource();
			String idBoton = boton.getActionCommand();
			escogerPremio(idBoton);
			boton.setBackground(new Color( 222, 218, 218));
		}
	}
	
	/**
	 * Create the frame.
	 * @param juego 
	 */
	public VentanaPremios(Juego juego, Locale localizacion) {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				asocioImagenBotones();
			}
		});
		this.juego = juego;
		this.pbp = new ProcesaBotonPremios();
		this.localizacion = localizacion;
		
		cargaAyuda();
		
		setTitle("Seleccion de premios");
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaPremios.class.getResource("/img/iconos/Icono.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 829, 744);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPanelContent(), BorderLayout.SOUTH);
		contentPane.add(getPanelPremios(), BorderLayout.CENTER);
		contentPane.add(getPanelCabecera(), BorderLayout.NORTH);
		setLocationRelativeTo(null);
		
		this.setMinimumSize(new Dimension(450, 400));
		
		cargarBotones();
		
		if(!localizacion.equals(new Locale("es"))) {
			localizar();
		}
	}

	private JPanel getPanelContent() {
		if (panelContent == null) {
			panelContent = new JPanel();
			panelContent.setBackground(new Color(255, 255, 255));
			panelContent.setLayout(new BorderLayout(0, 0));
			panelContent.add(getScrollPanePremios());
			panelContent.add(getPanelBotones(), BorderLayout.SOUTH);
		}
		return panelContent;
	}
	private JPanel getPanelPremios() {
		if (panelPremios == null) {
			panelPremios = new JPanel();
			panelPremios.setBorder(new LineBorder(new Color(0, 51, 204), 2));
			panelPremios.setLayout(new GridLayout(0, 5, 3, 3));
		}
		return panelPremios;
	}
	private JPanel getPanelCabecera() {
		if (panelCabecera == null) {
			panelCabecera = new JPanel();
			panelCabecera.setBackground(new Color(255, 255, 255));
			panelCabecera.setLayout(new GridLayout(0, 3, 0, 0));
			panelCabecera.add(getLabel());
			panelCabecera.add(getTxtrseleccioneDeLa());
			panelCabecera.add(getLabel_1());
		}
		return panelCabecera;
	}
	private JScrollPane getScrollPanePremios() {
		if (scrollPanePremios == null) {
			scrollPanePremios = new JScrollPane();
			scrollPanePremios.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Premios seleccionados", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			scrollPanePremios.setViewportView(getList());
		}
		return scrollPanePremios;
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("");
			label.setBackground(new Color(255, 255, 255));
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			label.setIcon(new ImageIcon(VentanaPremios.class.getResource("/img/iconos/Brillos.png")));
		}
		return label;
	}
	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel("");
			label_1.setHorizontalAlignment(SwingConstants.LEFT);
			label_1.setIcon(new ImageIcon(VentanaPremios.class.getResource("/img/iconos/Brillos.png")));
		}
		return label_1;
	}
	private JTextArea getTxtrseleccioneDeLa() {
		if (txtrseleccioneDeLa == null) {
			txtrseleccioneDeLa = new JTextArea();
			txtrseleccioneDeLa.setEditable(false);
			txtrseleccioneDeLa.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 16));
			txtrseleccioneDeLa.setText("         -Seleccione de la lista de\r\n              premios disponible-");
		}
		return txtrseleccioneDeLa;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private JList getList() {
		if (listPremios == null) {
			listPremios = new JList();
			dfp = new DefaultListModel<Premio>();
			listPremios.setModel(dfp);
			listPremios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		return listPremios;
	}
	private JPanel getPanelBotones() {
		if (panelBotones == null) {
			panelBotones = new JPanel();
			panelBotones.setLayout(new GridLayout(0, 3, 0, 0));
			panelBotones.add(getBtnQuitarPremio());
			panelBotones.add(getBtnSiguiente());
			panelBotones.add(getBtnAyuda());
		}
		return panelBotones;
	}
	private JButton getBtnQuitarPremio() {
		if (btnQuitarPremio == null) {
			btnQuitarPremio = new JButton("Quitar premio");
			btnQuitarPremio.setMnemonic('q');
			btnQuitarPremio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					quitarPremio();
				}
			});
			btnQuitarPremio.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 13));
		}
		return btnQuitarPremio;
	}
	/**
	 * Metodo que quita un premio de la lista
	 */
	protected void quitarPremio() {
		int premioBorrarIndex = listPremios.getSelectedIndex();
		if(premioBorrarIndex >= 0) {
			Premio premio = dfp.get(premioBorrarIndex);
			for(int i = 0; i < dfp.size(); i++) {
				Premio p = dfp.get(i);
				if(p.equals(premio)) {
					dfp.remove(premioBorrarIndex);
					premiosSeleccionados--;
					reactivarBotones();
				}
			}
		}
	}
	/**
	 * Metodo que reactiva los botones al borrar un premio, en caso de que hubiera
	 * mas premios seleccionados aparte de que hemos elminado
	 * 
	 * @param codigo
	 */
	private void reactivarBotones() {
		for(int i = 0; i < panelPremios.getComponents().length; i++) {
			JButton boton = (JButton) panelPremios.getComponent(i);
			boton.setEnabled(true);
		}
	}

	private JButton getBtnSiguiente() {
		if (btnSiguiente == null) {
			btnSiguiente = new JButton("Siguiente");
			btnSiguiente.setMnemonic('s');
			btnSiguiente.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(todosPremiosPosiblesSelec()) {
						if(poseeNumeroMagico()) {
							habilitarVentanaNMagico();
						} else {
							habilitarVentanaFinal();
						}
					} else {
						informarFaltanPremios();
					}
				}
			});
			btnSiguiente.setBackground(new Color(51, 153, 51));
			btnSiguiente.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 13));
		}
		return btnSiguiente;
	}
	
	/**
	 * Metodo que informa de que faltan premios
	 */
	protected void informarFaltanPremios() {
		ResourceBundle textos = ResourceBundle.getBundle("rcs/texto",localizacion);
		
		int res = JOptionPane.showConfirmDialog(this, textos.getString("textoAvisoFaltanPremios"),"ADVERTENCIA", JOptionPane.CANCEL_OPTION);
		if(res == JOptionPane.YES_OPTION ) {
			if(poseeNumeroMagico()) {
				habilitarVentanaNMagico();
			} else {
				habilitarVentanaFinal();
			}
		}
	}

	/**
	 * Metodo que habilita la ventana final (si todo es correcto)
	 */
	protected void habilitarVentanaFinal() {
		VentanaFinal vf = new VentanaFinal(listaPremios(),localizacion);
		vf.setVisible(true);
		this.dispose();
	}
	/**
	 * Metodo que devuelve si posee el numero magico
	 * 
	 * @return true si tiene numero magico, false si no
	 */
	protected boolean poseeNumeroMagico() {
		return juego.isNumeroMagicoObtenido();
	}
	/**
	 * Metodo que habilita la ventana referente al numero magico (si todo es correcto)
	 */
	protected void habilitarVentanaNMagico() {
		VentanaNumeroMagico vnm = new VentanaNumeroMagico(this);
		vnm.setVisible(true);
		this.dispose();
	}

	private JButton getBtnAyuda() {
		if (btnAyuda == null) {
			btnAyuda = new JButton("Ayuda");
			btnAyuda.setMnemonic('y');
			btnAyuda.setBackground(new Color(255, 204, 51));
			btnAyuda.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 13));
		}
		return btnAyuda;
	}
	
	private JButton getButtonPremio() {
		buttonPremios = new JButton("");
		buttonPremios.setEnabled(true);
		buttonPremios.setForeground(new Color(0,0,0));
		buttonPremios.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
		buttonPremios.addActionListener(pbp);
		buttonPremios.setFont(new Font("Source Sans Pro Black", Font.BOLD, 25));
		
		return buttonPremios;
	}
	
	/**
	 * Metodo que carga los botones con los premios segun lo que se haya conseguido en el juego
	 */
	public void cargarBotones() {
		ArrayList<Premio> premios = juego.getListaPremiosPartida();
		for(int i = 0; i < premios.size(); i++) {
			panelPremios.add(getButtonPremio());
		}
		asignarImagenBotones(premios);
	}
	
	/**
	 * Metodo que asigna la imagen a los botones
	 * 
	 * @param premios
	 */
	private void asignarImagenBotones(ArrayList<Premio> premios) {
		for(int i = 0; i < premios.size(); i++) {
			JButton boton = (JButton) panelPremios.getComponent(i);
			String fichero = "/img/" + premios.get(i).getCodigo()+".png";
			boton.setIcon(cargaImagen(fichero));
			boton.setActionCommand(premios.get(i).getCodigo());
		}
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
	 * Metodo que establece la imagen adaptada al nuevo tamaño del panel
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
	 * metodo que asocia imagen adaptada a los botones
	 */
	public void asocioImagenBotones() {
		ArrayList<Premio> premios = juego.getListaPremiosPartida();
		for (int i = 0; i < panelPremios.getComponents().length; i++)
		{
			JButton boton = (JButton) (panelPremios.getComponents()[i]);
			String fichero = "/img/" + premios.get(i).getCodigo()+".png";
			setImagenAdaptada(boton, fichero);
		}
	}
	/**
	 * Metodo que escoge premio
	 * 
	 * @param idPremio
	 */
	public void escogerPremio(String idPremio) {
		premiosSeleccionados++;
		if(todosPremiosPosiblesSelec()) {
			deshabilitarTodosLosBotones();
		} 
		Premio premio = getPremioById(idPremio);
		if(premio != null) {
			dfp.addElement(premio);
		}
		
	}
	/**
	 * Metodo que devuelve el premio por su ID
	 * 
	 * @param idPremio
	 * @return premio con la id que se pasa como parametro
	 */
	private Premio getPremioById(String idPremio) {
		ArrayList<Premio> premios = juego.getListaPremiosPartida();
		for(int i = 0; i < premios.size(); i++) {
			if(premios.get(i).getCodigo().contentEquals(idPremio)) {
				return premios.get(i);
			}
		}
		return null;
	}

	/**
	 * Metodo que comprueba si se han escogido todos los premios posibles
	 * 
	 * @return true si ya se han escogido todos los premios posibles
	 */
	private boolean todosPremiosPosiblesSelec() {
		return this.juego.numeroDePremiosAEscoger() - this.premiosSeleccionados == 0;
	}
	
	/**
	 * Netodo que deshabilita todos los botones del tablero
	 */
	private void deshabilitarTodosLosBotones() {
		for(int i = 0; i < panelPremios.getComponents().length; i++) {
			JButton boton = (JButton) (panelPremios.getComponents()[i]);
			boton.setEnabled(false);
		}
	}
	/**
	 * Metodo que devuelve la lista de premios escogidos
	 * 
	 * @return arraylist con los premios seleccionados
	 */
	public ArrayList<Premio> listaPremios() {
		ArrayList<Premio> ret = new ArrayList<Premio>();
		for(int i = 0; i < dfp.getSize(); i++) {
			ret.add(dfp.get(i));
		}
		return ret;
	}
	/**
	 * Metodo que devuelve el juego
	 * 
	 * @return juego
	 */
	public Juego getJuego() {
		return this.juego;
	}
	/**
	 * Metodo que devuelve la localizacion de la aplicacion
	 * 
	 * @return localizacion
	 */
	public Locale getLocalizacion() {
		return this.localizacion;
	}
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
		   hb.enableHelpOnButton(getBtnAyuda(), "reclamar premios", hs);
	}
	/**
	 * Metodo que localiza la aplicacion si es necesario
	 */
	private void localizar() {
		ResourceBundle textos = ResourceBundle.getBundle("rcs/texto",localizacion);
		
		this.setTitle(textos.getString("textoPremiosTitulo"));
		
		getTxtrseleccioneDeLa().setText(textos.getString("textoCabeceraPremios"));
		
		getBtnQuitarPremio().setText(textos.getString("textoQuitarPremio"));
		
		getBtnSiguiente().setText(textos.getString("textoSiguiente"));
		
		scrollPanePremios.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), textos.getString("textoPrinciaplPremiosSelec"), TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
	
		getBtnAyuda().setText(textos.getString("textoAyuda"));
		
		getBtnQuitarPremio().setMnemonic(textos.getString("nmmQuitarPremio").charAt(0));
		
		getBtnSiguiente().setMnemonic(textos.getString("nmmSiguiente").charAt(0));
		
		getBtnAyuda().setMnemonic(textos.getString("nmmAyuda").charAt(0));
		
	}
}
