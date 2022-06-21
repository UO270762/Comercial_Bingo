package igu;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import logica.Premio;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;

public class VentanaFinal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblesperamosVolverA;
	private JLabel labelBrillo1;
	private JLabel labelBrillo2;
	private JScrollPane scrollPanePremios;
	private JTextArea textAreaPremios;
	private JButton btnFinalizar;

	private ArrayList<Premio> premios;
	
	private Locale localizacion;
	/**
	 * Contructor de la ventana final
	 * 
	 * @param premios premios
	 * @param localizacion localizacion
	 */
	public VentanaFinal(ArrayList<Premio> premios, Locale localizacion) {
		this.premios = premios;
		this.localizacion = localizacion;
		
		setTitle("Resumen partida");
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaFinal.class.getResource("/img/iconos/Icono.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 653, 376);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblesperamosVolverA());
		contentPane.add(getLabelBrillo1());
		contentPane.add(getLabelBrillo2());
		contentPane.add(getScrollPanePremios());
		contentPane.add(getBtnFinalizar());
		setLocationRelativeTo(null);
		
		cargarRegalos();
		
		if(!localizacion.equals(new Locale("es"))) {
			localizar();
		}
	}
	/**
	 * Metodo que carga los regalos
	 */
	private void cargarRegalos() {
		if(premios.size() == 0) {
			ResourceBundle textos = ResourceBundle.getBundle("rcs/texto",localizacion);
			
			textAreaPremios.setText(textos.getString("textoSinPremiosFinal"));
		} else {
			String texto = "";
			for(Premio pr: premios) {
				texto += pr.toString() + "\n";
			}
			textAreaPremios.setText(texto);
		}
	}

	private JLabel getLblesperamosVolverA() {
		if (lblesperamosVolverA == null) {
			lblesperamosVolverA = new JLabel("\u00A1Esperamos volver a verte!");
			lblesperamosVolverA.setFont(new Font("Source Sans Pro Black", Font.ITALIC, 30));
			lblesperamosVolverA.setBounds(126, 36, 374, 33);
		}
		return lblesperamosVolverA;
	}
	private JLabel getLabelBrillo1() {
		if (labelBrillo1 == null) {
			labelBrillo1 = new JLabel("");
			labelBrillo1.setIcon(new ImageIcon(VentanaFinal.class.getResource("/img/iconos/Brillos.png")));
			labelBrillo1.setBounds(39, 25, 77, 57);
		}
		return labelBrillo1;
	}
	private JLabel getLabelBrillo2() {
		if (labelBrillo2 == null) {
			labelBrillo2 = new JLabel("");
			labelBrillo2.setIcon(new ImageIcon(VentanaFinal.class.getResource("/img/iconos/Brillos.png")));
			labelBrillo2.setBounds(510, 25, 64, 57);
		}
		return labelBrillo2;
	}
	private JScrollPane getScrollPanePremios() {
		if (scrollPanePremios == null) {
			scrollPanePremios = new JScrollPane();
			scrollPanePremios.setBorder(new TitledBorder(null, "Resumen de premios", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			scrollPanePremios.setBounds(39, 117, 564, 152);
			scrollPanePremios.setViewportView(getTextAreaPremios());
		}
		return scrollPanePremios;
	}
	private JTextArea getTextAreaPremios() {
		if (textAreaPremios == null) {
			textAreaPremios = new JTextArea();
			textAreaPremios.setEditable(false);
		}
		return textAreaPremios;
	}
	private JButton getBtnFinalizar() {
		if (btnFinalizar == null) {
			btnFinalizar = new JButton("Finalizar");
			btnFinalizar.setMnemonic('f');
			btnFinalizar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					habilitarVentanaInicio();
				}
			});
			btnFinalizar.setBackground(new Color(0, 128, 0));
			btnFinalizar.setBounds(491, 293, 122, 33);
		}
		return btnFinalizar;
	}
	/**
	 * Metodo que habilita la ventana de inicio
	 */
	protected void habilitarVentanaInicio() {
		VentanaInicio vi = new VentanaInicio();
		vi.setVisible(true);
		this.dispose();
	}
	/**
	 * Metodo que localiza la aplicacion
	 */
	private void localizar() {
		ResourceBundle textos = ResourceBundle.getBundle("rcs/texto",localizacion);
		
		this.setTitle(textos.getString("textoTituloFinal"));
		
		getLblesperamosVolverA().setText(textos.getString("textoCabeceraFinal"));
		
		scrollPanePremios.setBorder(new TitledBorder(null, textos.getString("textoResPremiosFinal"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		getBtnFinalizar().setText(textos.getString("textoFinalizarFinal"));
		
		getBtnFinalizar().setMnemonic(textos.getString("nmmFinalizar").charAt(0));
	}
}
