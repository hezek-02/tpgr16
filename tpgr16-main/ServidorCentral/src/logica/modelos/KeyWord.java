package logica.modelos;

import java.util.HashSet;
import java.util.Set;

import logica.datatypes.DtKeyWord;					

public class KeyWord {
	private String nombreKey;
	private Set<OfertaLab>  colOfertasLab;
		
	public KeyWord(String nombre) {
		this.setNombreKey(nombre);
        colOfertasLab = new HashSet<>(); 
	}
	
	public DtKeyWord obtenerDtKeyWord() {
		return new DtKeyWord(this.nombreKey);
	}
	
	public void agregarOfertaLab(OfertaLab oferta) {
		this.colOfertasLab.add(oferta);
	}
	
	public void eliminarOfertaLab(OfertaLab oferta) {
		this.colOfertasLab.remove(oferta);
	}

	public String getNombreKey() {
		return nombreKey;
	}

	public void setNombreKey(String nombreKey) {
		this.nombreKey = nombreKey;
	}
}
