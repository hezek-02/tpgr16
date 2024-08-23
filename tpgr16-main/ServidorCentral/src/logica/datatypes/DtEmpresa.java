package logica.datatypes;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class DtEmpresa extends DtUsuario {
	
	private String descripcion;
	private String linkSitioWeb;

	/**
	 * @param nickname
	 * @param nombre
	 * @param apellido
	 * @param correo
	 * @param descripcion
	 * @param linkSitioWeb
	 * @param string 
	 */
	public DtEmpresa(String nickname, String nombre, String apellido, String correo, String password, byte[] image, String descripcion, String linkSitioWeb) {
		super(nickname, nombre, apellido, correo, password, image);		
		this.setDescripcion(descripcion);
		this.setSitioWeb(linkSitioWeb);
	}
	
	public DtEmpresa() {}
	
	public String getDescripcion() {
		return this.descripcion;
	}
	
	public String getSitioWeb() {
		return this.linkSitioWeb;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public void setSitioWeb(String linkSitioWeb) {
		this.linkSitioWeb = linkSitioWeb;
	}

	public String toString() {
        return "Empresa: " + getNickname();
	}

}
