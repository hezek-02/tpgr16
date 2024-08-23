package logica.datatypes;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({
	DtEmpresa.class,
	DtPostulante.class
})
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class DtUsuario {
	
	private String nickname;
	private String nombre;
	private String apellido;
	private String correo;
	private String password;
	private byte[] image;
	
	/**
	 * @param nickname
	 * @param nombre
	 * @param apellido
	 * @param correo
	 */
	public DtUsuario(String nickname, String nombre, String apellido, String correo, String password, byte[] image) {
		this.setNickname(nickname);
		this.setNombre(nombre);
		this.setApellido(apellido);
		this.setCorreo(correo);
		this.setPassword(password);
		this.setImage(image);
	}
	
	public DtUsuario() {
	}
	
	public String getNickname() {
		return this.nickname;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public String getApellido() {
		return this.apellido;
	}
	
	public String getCorreo() {
		return this.correo;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public byte[] getImage() {
		return this.image;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setImage(byte[] image) {
		this.image = image;
	}
	
	 /* 
	  * Sirve para mostrar textualmente la información del usuario, por ejemplo en un ComboBox
     */
    public abstract String toString();


}
