package logica;

import java.util.ArrayList;

public class CatalogoPremios {

	public static final String FICHERO_PREMIOS = "files/premios.dat";
	
	public ArrayList<Premio> catalogoPremios;
	
	public CatalogoPremios() {
		this.catalogoPremios = new ArrayList<Premio>();
		cargarPremios();
	}

	/**
	 * Metodo que carga los premios de fichero
	 */
	private void cargarPremios() {
		FileUtil.cargarPremiosFichero(FICHERO_PREMIOS, this.catalogoPremios);
	}
	
	/**
	 * Metodo que devuelve la lista de premios para una linea
	 * 
	 * @return lista de premios filtrados por linea
	 */
	public ArrayList<Premio> filtrarPremiosLinea() {
		ArrayList<Premio> ret = new ArrayList<Premio>();
		for(Premio prm: catalogoPremios) {
			if(prm.getTipoPremio() == 'l') {
				ret.add(prm);
			}
		}
		return ret;
	}
	
	/**
	 * Metodo que devuelve la lista de premios para un bingo
	 * 
	 * @return lista de premios filtrada por bingo
	 */
	public ArrayList<Premio> filtrarPremiosBingo() {
		ArrayList<Premio> ret = new ArrayList<Premio>();
		for(Premio prm: catalogoPremios) {
			if(prm.getTipoPremio() == 'b') {
				ret.add(prm);
			}
		}
		return ret;
	}
	
}
