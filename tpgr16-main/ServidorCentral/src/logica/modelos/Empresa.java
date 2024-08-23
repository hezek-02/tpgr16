package logica.modelos;

import logica.datatypes.DtEmpresa;
import logica.datatypes.DtUsuario;
import java.util.List;
import java.util.ArrayList;

public class Empresa extends Usuario{
	private String descripcion;
	private String linkWeb;
	private List<OfertaLab> ofertas;
	private List<CompraPaquete> paqsComprados;

	
	public Empresa(String nickname, String nombre, String apellido, String password, String correo, byte[] image, String descripcion, String link) {
		super(nickname, nombre, apellido, password, correo, image);
		this.descripcion = descripcion;
		this.linkWeb = link;
		ofertas = new ArrayList<OfertaLab>();
		paqsComprados = new ArrayList<CompraPaquete>();
	}

	public String getSitioWeb() {
		return linkWeb;
	}

	public void setSitioWeb(String linkWeb) {
		this.linkWeb = linkWeb;
	}

	public CompraPaquete obtenerPaqueteComprado(String paqueteNom)  {
		CompraPaquete compra = null;
		for (CompraPaquete paqueteComprado: paqsComprados) {
			if (paqueteComprado.getPaquete() != null && paqueteComprado.getPaquete().getNombrePaq().equals(paqueteNom)) {
				compra = paqueteComprado;
			}
		}			
		return compra;
	}
	
	public CompraPaquete[] obtenerPaquetesComprados() {
	    if (paqsComprados != null) {
	        return paqsComprados.toArray(new CompraPaquete[0]);
	    } else {
	        return null;
	    }
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	public String setDescripcion(String descripcion) {
		return this.descripcion= descripcion;
	}
	
	public DtUsuario obtenerDtUsu() {
		return new DtEmpresa(this.getNickname(),  this.getNombre(), this.getApellido(), this.getCorreo(), this.getPassword(), this.getImage(), this.getDescripcion(), this.getSitioWeb());
	}

	public List<OfertaLab> getOfertas() {
		return this.ofertas;
	}
	
	public void setOferta(OfertaLab oferta) {
		this.ofertas.add(oferta);
	}

	public void agregarCompraPaq(CompraPaquete compraPaquete) {
		this.paqsComprados.add(compraPaquete);
	}
	
	public void eliminarOferta(OfertaLab ofertaLab) {
	    this.ofertas.remove(ofertaLab);
	}
}
