package igu;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.help.*;
import java.net.*;
import java.io.*;

import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import logica.Cliente;
import logica.Premio;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;

public class VentanaNumeroMagico extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblhasSidoPremiado;
	private JLabel labelBrillo1;
	private JLabel labelBrillo2;
	private JLabel lblRegistrateParaObtener;
	private JLabel lblDocumentoIdentificativo;
	private JTextField textFieldCoDocuemnto;
	private JLabel lblNombre;
	private JTextField textFieldNombre;
	private JButton btnOIdentificate;
	private JButton btnRegistro;
	private JButton btnCancelar;
	private JPanel panelRegistro;
	private JLabel lblyaEstasRegistrado;
	private JLabel lblApellidos;
	private JTextField textFieldApellidos;
	
	private VentanaPremios vp;
	private JButton btnAyuda;
	
	/**
	 * Create the frame.
	 */
	public VentanaNumeroMagico(VentanaPremios ventanaPremios) {
		this.vp = ventanaPremios;
		
		cargaAyuda();
		
		setTitle("Premio numero magico");
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaNumeroMagico.class.getResource("/img/iconos/Icono.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 534, 518);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblhasSidoPremiado());
		contentPane.add(getLabelBrillo1());
		contentPane.add(getLabelBrillo2());
		contentPane.add(getLblRegistrateParaObtener());
		contentPane.add(getBtnOIdentificate());
		contentPane.add(getBtnCancelar());
		contentPane.add(getPanelRegistro());
		contentPane.add(getLblyaEstasRegistrado());
		contentPane.add(getBtnAyuda());
		setLocationRelativeTo(null);
		
		compruebaLocalizacion();
	}

	private JLabel getLblhasSidoPremiado() {
		if (lblhasSidoPremiado == null) {
			lblhasSidoPremiado = new JLabel("\u00A1Has sido premiado por el numero magico!");
			lblhasSidoPremiado.setFont(new Font("Tahoma", Font.ITALIC, 17));
			lblhasSidoPremiado.setBounds(93, 47, 331, 28);
		}
		return lblhasSidoPremiado;
	}
	private JLabel getLabelBrillo1() {
		if (labelBrillo1 == null) {
			labelBrillo1 = new JLabel("");
			labelBrillo1.setIcon(new ImageIcon(VentanaNumeroMagico.class.getResource("/img/iconos/Brillos.png")));
			labelBrillo1.setBounds(25, 38, 62, 48);
		}
		return labelBrillo1;
	}
	private JLabel getLabelBrillo2() {
		if (labelBrillo2 == null) {
			labelBrillo2 = new JLabel("");
			labelBrillo2.setIcon(new ImageIcon(VentanaNumeroMagico.class.getResource("/img/iconos/Brillos.png")));
			labelBrillo2.setBounds(434, 38, 62, 48);
		}
		return labelBrillo2;
	}
	private JLabel getLblRegistrateParaObtener() {
		if (lblRegistrateParaObtener == null) {
			lblRegistrateParaObtener = new JLabel("Registrate para obtener un bono de compras valorado en 20\u20AC");
			lblRegistrateParaObtener.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 15));
			lblRegistrateParaObtener.setBounds(47, 107, 431, 28);
		}
		return lblRegistrateParaObtener;
	}
	private JLabel getLblDocumentoIdentificativo() {
		if (lblDocumentoIdentificativo == null) {
			lblDocumentoIdentificativo = new JLabel("Documento identificativo:");
			lblDocumentoIdentificativo.setDisplayedMnemonic('d');
			lblDocumentoIdentificativo.setLabelFor(getTextFieldCoDocuemnto());
			lblDocumentoIdentificativo.setBounds(10, 23, 166, 14);
			lblDocumentoIdentificativo.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 13));
		}
		return lblDocumentoIdentificativo;
	}
	private JTextField getTextFieldCoDocuemnto() {
		if (textFieldCoDocuemnto == null) {
			textFieldCoDocuemnto = new JTextField();
			textFieldCoDocuemnto.setBounds(172, 21, 255, 20);
			textFieldCoDocuemnto.setColumns(10);
		}
		return textFieldCoDocuemnto;
	}
	private JLabel getLblNombre() {
		if (lblNombre == null) {
			lblNombre = new JLabel("Nombre:");
			lblNombre.setDisplayedMnemonic('n');
			lblNombre.setLabelFor(getTextFieldNombre());
			lblNombre.setBounds(113, 58, 63, 14);
			lblNombre.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 13));
		}
		return lblNombre;
	}
	private JTextField getTextFieldNombre() {
		if (textFieldNombre == null) {
			textFieldNombre = new JTextField();
			textFieldNombre.setBounds(172, 56, 255, 20);
			textFieldNombre.setColumns(10);
		}
		return textFieldNombre;
	}
	private JButton getBtnOIdentificate() {
		if (btnOIdentificate == null) {
			btnOIdentificate = new JButton("Identificate");
			btnOIdentificate.setMnemonic('d');
			btnOIdentificate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					identificacion();
				}
			});
			btnOIdentificate.setBounds(183, 361, 109, 35);
		}
		return btnOIdentificate;
	}
	/**
	 * Metodo que gestiona la identificacion de un usuario
	 */
	protected void identificacion() {
		ResourceBundle textos = ResourceBundle.getBundle("rcs/texto",vp.getLocalizacion());
		
		String res = JOptionPane.showInputDialog(this, textos.getString("textoInicioIdentificacion"));
		if(res != null) {
			if(vp.getJuego().validarDocumentoIdent(res)) {
				Cliente cliente = vp.getJuego().getClientePorIdentificacion(res);
				informarDelLogin(res, cliente);
			} else {
				JOptionPane.showMessageDialog(this, textos.getString("textoInicioIdentificacionIncorrecta"),
						"Ha ocurrido un problema", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			//no hace nada
		}
	}
	/**
	 * Metodo que informa del saldo y nos lleva a la ventana final
	 * 
	 * @param documentoIdent
	 */
	private void informarDelLogin(String documentoIdent, Cliente cliente) {
		ResourceBundle textos = ResourceBundle.getBundle("rcs/texto",vp.getLocalizacion());
		
		String nombreCliente = cliente.getNombre();
		vp.getJuego().aumentarSaldoNumMagico(documentoIdent);
		Double saldo = vp.getJuego().getSaldoCliente(documentoIdent);
		JOptionPane.showMessageDialog(this, textos.getString("textoInformarLogin1")+ " "+ nombreCliente + "\n"+
				textos.getString("textoInformarLogin2") + saldo + "€-");
		vp.getJuego().actualizarBaseDatosClientes();
		this.dispose();
		ArrayList<Premio> listaPremios = vp.listaPremios();
		Premio bono = new Premio("0000", "Bono valor 20€", 'b');
		listaPremios.add(bono);//Añade como premio el bono a finde mostrarlo en la pantalla final
		VentanaFinal vf = new VentanaFinal(listaPremios,vp.getLocalizacion());
		vf.setVisible(true);
	}
	private JButton getBtnRegistro() {
		if (btnRegistro == null) {
			btnRegistro = new JButton("Registrarme");
			btnRegistro.setMnemonic('r');
			btnRegistro.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(estanTodosLosDatosCorrectos()) {
						registrarEnBase();
						habilitarVentanaFinalRegistro();
					} else {
						informarDatosMal();
					}
				}
			});
			btnRegistro.setBounds(158, 127, 113, 35);
		}
		return btnRegistro;
	}
	protected void registrarEnBase() {
		String documento= textFieldCoDocuemnto.getText();
		String nombre = textFieldNombre.getText();
		String apellidos = textFieldApellidos.getText();
		
		Cliente cliente = new Cliente(documento, nombre, apellidos,20);
		
		vp.getJuego().registrarJugadorEnBase(cliente);
	}

	/**
	 * Metodo que habilita la ventana final e informa al usuario de que ha sido registrado con exito
	 */
	protected void habilitarVentanaFinalRegistro() {
		ResourceBundle textos = ResourceBundle.getBundle("rcs/texto",vp.getLocalizacion());
		
		JOptionPane.showMessageDialog(this,textos.getString("textoResgistro1")+ getTextFieldNombre().getText() + "\n" + textos.getString("textoResgistro2"));
		this.dispose();
		ArrayList<Premio> listaPremios = vp.listaPremios();
		Premio bono = new Premio("0000", "Bono valor 20€", 'b');
		listaPremios.add(bono);//Añade como premio el bono a finde mostrarlo en la pantalla final
		VentanaFinal vf = new VentanaFinal(listaPremios,vp.getLocalizacion());
		vf.setVisible(true);
	}
	/**
	 * Metodo que comprueba que los datos esten correctos para registro
	 * 
	 * @return true si los datos estan correctes, false si no
	 */
	protected boolean estanTodosLosDatosCorrectos() {
		String documento= textFieldCoDocuemnto.getText().trim();
		String nombre = textFieldNombre.getText().trim();
		String apellidos = textFieldApellidos.getText().trim();
		if(documento.length() == 0 || nombre.length() == 0 || apellidos.length() == 0 || estaYaRegistrado()) {
			return false;
		} else {
			return true;
		}
	}
	/**
	 * Metodo que devuelve si ya esta registrado en la base de cliente
	 * 
	 * @return true si ya esta registrado, false si no
	 */
	private boolean estaYaRegistrado() {
		String documento = textFieldCoDocuemnto.getText().trim();
		String nombre = textFieldNombre.getText().trim();
		String apellidos = textFieldApellidos.getText().trim();
		
		Cliente cliente = new Cliente(documento, nombre, apellidos,20);
		
		ResourceBundle textos = ResourceBundle.getBundle("rcs/texto",vp.getLocalizacion());
		
		for(Cliente clien: vp.getJuego().getBaseClientes().getClientes()) {
			if(clien.equals(cliente) ) {
				JOptionPane.showMessageDialog(this, textos.getString("textoYaEstasRegistro"));
				return true;
			}
		}
		
		return false;
	}

	public void informarDatosMal() {
		ResourceBundle textos = ResourceBundle.getBundle("rcs/texto",vp.getLocalizacion());
		
		JOptionPane.showMessageDialog(this, textos.getString("textoDatosMalRegistro"),
				"Ha ocurrido un problema", JOptionPane.ERROR_MESSAGE);
	}
	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.setMnemonic('c');
			btnCancelar.setBackground(new Color(220, 20, 60));
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					informarPerderNumMagico();
				}
			});
			btnCancelar.setBounds(258, 433, 102, 35);
		}
		return btnCancelar;
	}
	/**
	 * Metodo que informa de que si continua perdera el numero magico, si elige continuar se habilita la ventana final
	 */
	protected void informarPerderNumMagico() {
		ResourceBundle textos = ResourceBundle.getBundle("rcs/texto",vp.getLocalizacion());
		
		int res = JOptionPane.showConfirmDialog(this,textos.getString("textoPierdesNumMagRegistro"),"Cuidado", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if(res == JOptionPane.YES_OPTION) {
			this.dispose();
			VentanaFinal vf = new VentanaFinal(vp.listaPremios(),vp.getLocalizacion());
			vf.setVisible(true);
		}
	}

	private JPanel getPanelRegistro() {
		if (panelRegistro == null) {
			panelRegistro = new JPanel();
			panelRegistro.setBorder(new LineBorder(new Color(0, 0, 0)));
			panelRegistro.setBackground(Color.WHITE);
			panelRegistro.setBounds(29, 146, 449, 173);
			panelRegistro.setLayout(null);
			panelRegistro.add(getBtnRegistro());
			panelRegistro.add(getLblDocumentoIdentificativo());
			panelRegistro.add(getLblNombre());
			panelRegistro.add(getTextFieldCoDocuemnto());
			panelRegistro.add(getTextFieldNombre());
			panelRegistro.add(getLblApellidos());
			panelRegistro.add(getTextFieldApellidos());
		}
		return panelRegistro;
	}
	private JLabel getLblyaEstasRegistrado() {
		if (lblyaEstasRegistrado == null) {
			lblyaEstasRegistrado = new JLabel("-\u00BFYa estas registrado?-");
			lblyaEstasRegistrado.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 15));
			lblyaEstasRegistrado.setBounds(156, 330, 166, 20);
		}
		return lblyaEstasRegistrado;
	}
	private JLabel getLblApellidos() {
		if (lblApellidos == null) {
			lblApellidos = new JLabel("Apellidos:");
			lblApellidos.setLabelFor(getTextFieldApellidos());
			lblApellidos.setDisplayedMnemonic('p');
			lblApellidos.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 13));
			lblApellidos.setBounds(107, 98, 69, 14);
		}
		return lblApellidos;
	}
	private JTextField getTextFieldApellidos() {
		if (textFieldApellidos == null) {
			textFieldApellidos = new JTextField();
			textFieldApellidos.setBounds(172, 96, 255, 20);
			textFieldApellidos.setColumns(10);
		}
		return textFieldApellidos;
	}
	/**
	 * Metodo que comprueba si hay que localizar la aplicacion
	 */
	private void compruebaLocalizacion() {
		if(!vp.getLocalizacion().equals(new Locale("es"))) {
			localizar();
		}
	}
	/**
	 * Metodo que carga la ayuda
	 */
	private void cargaAyuda() {
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
		   hb.enableHelpOnButton(getBtnAyuda(), "registro/Identificacion", hs);
	}
	
	/**
	 * Metodo que localiza la aplicacion si es necesario
	 */
	private void localizar() {
		ResourceBundle textos = ResourceBundle.getBundle("rcs/texto",vp.getLocalizacion());
		
		this.setTitle(textos.getString("textoNumMagicoTitulo"));
		
		getLblhasSidoPremiado().setText(textos.getString("textoCabecera"));
		
		getLblRegistrateParaObtener().setText(textos.getString("textoCabeceraRegistroNumMag"));
		
		getLblDocumentoIdentificativo().setText(textos.getString("textoDocIdenNumMag"));
		
		getLblNombre().setText(textos.getString("textoNombreNumMag"));
		
		getLblApellidos().setText(textos.getString("textoApellidosNumMag"));
		
		getLblyaEstasRegistrado().setText(textos.getString("textoYaEstaRegistradoNumMag"));
		
		getBtnRegistro().setText(textos.getString("textoRegistroNumMag"));
		
		getBtnOIdentificate().setText(textos.getString("textoIdentNumMag"));
		
		getBtnCancelar().setText(textos.getString("textoCancelar"));
		
		getBtnAyuda().setText(textos.getString("textoAyuda"));
		
		getLblDocumentoIdentificativo().setDisplayedMnemonic(textos.getString("nmmDocumento").charAt(0));
		
		getLblNombre().setDisplayedMnemonic(textos.getString("nmmNombre").charAt(0));
		
		getLblApellidos().setDisplayedMnemonic(textos.getString("nmmApellidos").charAt(0));
		
		getBtnRegistro().setMnemonic(textos.getString("nmmRegistrarme").charAt(0));
		
		getBtnOIdentificate().setMnemonic(textos.getString("nmmIdentificare").charAt(0));
		
		getBtnCancelar().setMnemonic(textos.getString("nmmCancelarNMagico").charAt(0));
		
		getBtnAyuda().setMnemonic(textos.getString("nmmAyuda").charAt(0));
	}
	private JButton getBtnAyuda() {
		if (btnAyuda == null) {
			btnAyuda = new JButton("Ayuda");
			btnAyuda.setMnemonic('y');
			btnAyuda.setBackground(new Color(255, 215, 0));
			btnAyuda.setBounds(370, 433, 108, 35);
		}
		return btnAyuda;
	}
}
