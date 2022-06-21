package igu;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.Juego;

import javax.help.*;
import java.net.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.io.*;

import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class VentanaInicio extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblbienvenidoAlBingo;
	private JLabel lblIntroduzcaElCodigo;
	private JTextField textFieldCodigo;
	private JButton btnComprobarSaldo;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JButton btnAyuda;
	private JLabel labelImagen;
	private JLabel labelImagen2;
	
	private Juego juego;
	private JButton buttonEs;
	private JButton buttonEn;

	private Locale localizacion;
	/**
	 * Create the frame.
	 */
	public VentanaInicio() {
		this.juego = new Juego();
		this.localizacion = new Locale("es");
		cargaAyuda();
		
		setResizable(false);
		setTitle("Bingo promociones");
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaInicio.class.getResource("/img/iconos/Icono.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 749, 419);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblbienvenidoAlBingo());
		contentPane.add(getLblIntroduzcaElCodigo());
		contentPane.add(getTextFieldCodigo());
		contentPane.add(getBtnComprobarSaldo());
		contentPane.add(getBtnAceptar());
		contentPane.add(getBtnCancelar());
		contentPane.add(getBtnAyuda());
		contentPane.add(getLabelImagen());
		contentPane.add(getLabelImagen2());
		contentPane.add(getButtonEs());
		contentPane.add(getButtonEn());
		setLocationRelativeTo(null);
	}
	private JLabel getLblbienvenidoAlBingo() {
		if (lblbienvenidoAlBingo == null) {
			lblbienvenidoAlBingo = new JLabel("\u00A1Bienvenido al bingo!");
			lblbienvenidoAlBingo.setFont(new Font("Source Sans Pro Black", Font.ITALIC, 40));
			lblbienvenidoAlBingo.setBounds(194, 119, 391, 51);
		}
		return lblbienvenidoAlBingo;
	}
	private JLabel getLblIntroduzcaElCodigo() {
		if (lblIntroduzcaElCodigo == null) {
			lblIntroduzcaElCodigo = new JLabel("Introduzca el codigo promocional de su ticket");
			lblIntroduzcaElCodigo.setDisplayedMnemonic('t');
			lblIntroduzcaElCodigo.setLabelFor(getTextFieldCodigo());
			lblIntroduzcaElCodigo.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 20));
			lblIntroduzcaElCodigo.setBounds(176, 190, 425, 26);
		}
		return lblIntroduzcaElCodigo;
	}
	private JTextField getTextFieldCodigo() {
		if (textFieldCodigo == null) {
			textFieldCodigo = new JTextField();
			textFieldCodigo.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldCodigo.setBounds(257, 227, 235, 26);
			textFieldCodigo.setColumns(10);
		}
		return textFieldCodigo;
	}
	private JButton getBtnComprobarSaldo() {
		if (btnComprobarSaldo == null) {
			btnComprobarSaldo = new JButton("Consultar saldo");
			btnComprobarSaldo.setMnemonic('r');
			btnComprobarSaldo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					consultaSaldo();
				}
			});
			btnComprobarSaldo.setFont(new Font("Source Sans Pro Black", Font.BOLD, 13));
			btnComprobarSaldo.setBounds(36, 307, 161, 53);
		}
		return btnComprobarSaldo;
	}
	
	private JButton getBtnAceptar() {
		if (btnAceptar == null) {
			btnAceptar = new JButton("Aceptar");
			btnAceptar.setMnemonic('c');
			btnAceptar.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 15));
			btnAceptar.setBackground(new Color(50, 205, 50));
			btnAceptar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(codigoValido()) {
						habilitarVentanaJuego();
					} else {
						informarCodigoNegativo();
					}
				}
			});
			btnAceptar.setBounds(367, 314, 96, 39);
		}
		return btnAceptar;
	}
	/**
	 * Metodo que informa de un codigo negativo
	 */
	protected void informarCodigoNegativo() {
		ResourceBundle textos = ResourceBundle.getBundle("rcs/texto",localizacion);
		JOptionPane.showMessageDialog(this, textos.getString("textoTicketMal")
				,"Informacion usuario",1);
	}
	/**
	 * Metodo que valida el codigo introducido en el campo de texto
	 * habilitado para ello
	 * 
	 * @return true si es codigo es valido, false si no
	 */
	private boolean codigoValido() {
		String codigoValidar = textFieldCodigo.getText().trim();
		return juego.validarTicket(codigoValidar);
	}
	
	/**
	 * Metodo que habilita la ventana de juego
	 */
	private void habilitarVentanaJuego() {
		this.juego.setTicketJuego(textFieldCodigo.getText());
		VentanaPrincipal vpj = new VentanaPrincipal(this.juego,this.localizacion);
		vpj.setVisible(true);
		this.dispose();
	}
	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.setMnemonic('l');
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					inicializarVentana();
				}
			});
			btnCancelar.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 15));
			btnCancelar.setBackground(new Color(255, 51, 51));
			btnCancelar.setBounds(489, 314, 96, 39);
		}
		return btnCancelar;
	}
	/**
	 * Metodo que inicializa la ventana 
	 */
	protected void inicializarVentana() {
		getTextFieldCodigo().setText("");
		this.localizacion = new Locale("es");
		localizar("es");
	}
	private JButton getBtnAyuda() {
		if (btnAyuda == null) {
			btnAyuda = new JButton("Ayuda");
			btnAyuda.setMnemonic('y');
			btnAyuda.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 15));
			btnAyuda.setBackground(new Color(255, 215, 0));
			btnAyuda.setBounds(609, 314, 96, 39);
		}
		return btnAyuda;
	}
	private JLabel getLabelImagen() {
		if (labelImagen == null) {
			labelImagen = new JLabel("");
			labelImagen.setIcon(new ImageIcon(VentanaInicio.class.getResource("/img/iconos/Icono.png")));
			labelImagen.setBounds(59, 93, 125, 99);
		}
		return labelImagen;
	}
	private JLabel getLabelImagen2() {
		if (labelImagen2 == null) {
			labelImagen2 = new JLabel("");
			labelImagen2.setIcon(new ImageIcon(VentanaInicio.class.getResource("/img/iconos/Icono.png")));
			labelImagen2.setBounds(591, 93, 125, 99);
		}
		return labelImagen2;
	}
	/**
	 * Metodo que carga el sistema de ayuda
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
		   hb.enableHelpOnButton(getBtnAyuda(), "introduccion", hs);
		 }
	private JButton getButtonEs() {
		if (buttonEs == null) {
			buttonEs = new JButton("Espa\u00F1ol");
			buttonEs.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					localizar("es");
				}
			});
			buttonEs.setContentAreaFilled(false);
			buttonEs.setBorderPainted(false);
			buttonEs.setHorizontalTextPosition(SwingConstants.CENTER);
			buttonEs.setVerticalAlignment(SwingConstants.BOTTOM);
			buttonEs.setVerticalTextPosition(SwingConstants.BOTTOM);
			buttonEs.setMnemonic('s');
			buttonEs.setIcon(new ImageIcon(VentanaInicio.class.getResource("/img/iconos/espa\u00F1a.png")));
			buttonEs.setBounds(530, 16, 91, 66);
		}
		return buttonEs;
	}
	private JButton getButtonEn() {
		if (buttonEn == null) {
			buttonEn = new JButton("English");
			buttonEn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					localizar("en");
					ajustarTamañosIngles();
				}
			});
			buttonEn.setBorderPainted(false);
			buttonEn.setContentAreaFilled(false);
			buttonEn.setHorizontalTextPosition(SwingConstants.CENTER);
			buttonEn.setVerticalTextPosition(SwingConstants.BOTTOM);
			buttonEn.setVerticalAlignment(SwingConstants.TOP);
			buttonEn.setMnemonic('n');
			buttonEn.setIcon(new ImageIcon(VentanaInicio.class.getResource("/img/iconos/inglaterra.png")));
			buttonEn.setBounds(609, 11, 92, 67);
		}
		return buttonEn;
	}
	/**
	 * Metodo que informa del saldo
	 * 
	 * @param documentoIdent
	 */
	private void informarDelSaldo(String documentoIdent) {
		ResourceBundle textos = ResourceBundle.getBundle("rcs/texto",localizacion);
		Double saldo = juego.getSaldoCliente(documentoIdent);
		JOptionPane.showMessageDialog(this, textos.getString("textoInformarSaldo")+ saldo + "€");
	}
	/**
	 * Metodo que hace una consulta de saldo solicitando al cliente su documento identificativo
	 */
	protected void consultaSaldo() {
		ResourceBundle textos = ResourceBundle.getBundle("rcs/texto",localizacion);
		String res = JOptionPane.showInputDialog(this, textos.getString("textoInicioIdentificacion"));
		if(res != null) {
			if(juego.validarDocumentoIdent(res)) {
				informarDelSaldo(res);
			} else {
				JOptionPane.showMessageDialog(this, textos.getString("textoInicioIdentificacionIncorrecta"),
						"Ha ocurrido un problema", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			//no hace nada
		}
	}
	/**
	 * Ajusta el tamaño a elementos en ingles
	 */
	protected void ajustarTamañosIngles() {
		getLblbienvenidoAlBingo().setFont(new Font("Source Sans Pro Black", Font.ITALIC, 35));
	}
	
	/**
	 * Metodo que localiza la aplicacion
	 * @param codeLocalicacion
	 */
	protected void localizar(String codeLocalicacion) {
		this.localizacion = new Locale(codeLocalicacion);
		
		ResourceBundle textos = ResourceBundle.getBundle("rcs/texto",localizacion);
		
		setTitle(textos.getString("textoInicioTitulo"));
		
		getLblbienvenidoAlBingo().setText(textos.getString("textoInicio"));
		
		getLblIntroduzcaElCodigo().setText(textos.getString("textoInicioCodigo"));
		
		getBtnComprobarSaldo().setText(textos.getString("textoInicioConsultaSaldo"));
		
		getBtnAceptar().setText(textos.getString("textoInicioAceptar"));
		
		getBtnCancelar().setText(textos.getString("textoCancelar"));
		
		getBtnAyuda().setText(textos.getString("textoAyuda"));
		
		getBtnComprobarSaldo().setMnemonic(textos.getString("nmmConsultarSaldo").charAt(0));
		
		getBtnAceptar().setMnemonic(textos.getString("nmmAceptar").charAt(0));
		
		getBtnCancelar().setMnemonic(textos.getString("nmmCancelar").charAt(0));
		
		getBtnAyuda().setMnemonic(textos.getString("nmmAyuda").charAt(0));
	}
}
