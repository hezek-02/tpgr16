package logica.modelos;

import java.util.ArrayList;
import java.util.List;
import logica.datatypes.DtUsuario;

public abstract class Usuario {
	private String nickname;
	private String correo;
	private String nombre;
	private String apellido;
	private String password;
	private byte[] image;
	private List<Usuario> seguidores; //qienes me siguen
	private List<Usuario> seguidos; //los q yo sigo
	
	public Usuario(String nickname, String nombre, String apellido, String password, String correo, byte[] image) {
		this.setNickname(nickname);
		this.setNombre(nombre);
		this.setApellido(apellido);
		this.setCorreo(correo);
		this.setPassword(password);
		this.setImage(image);
        this.seguidos = new ArrayList<Usuario>();
        this.seguidores = new ArrayList<Usuario>();
	}

	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getApellido() {
		return apellido;
	}
	
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	public abstract DtUsuario obtenerDtUsu();
	
	public abstract List<OfertaLab> getOfertas();

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
	
	//seguir y seguidores
	public List<Usuario> getSeguidores() {
	    return this.seguidores;
	}
	
	public List<Usuario> getSeguidos() {
		return this.seguidos;
	}
	
	public void agregarSeguidor(Usuario seguidor) { 
		this.seguidores.add(seguidor); //agrego quien me sigue
	}
	
	public void agregarSeguido(Usuario seguido) {
		this.seguidos.add(seguido); //agrego a quien sigo
	}
	
	public void eliminarSeguidor(Usuario seguidor) {
		this.seguidores.remove(seguidor); //elimino a quien me siguen
	}
	
	public void eliminarSeguido(Usuario seguido) {
		this.seguidos.remove(seguido); //elimino a quien sigo
	}
	
	public boolean yaSeguido(Usuario usuario) {
		boolean res = false;
		if (this.seguidos == null || this.seguidos.isEmpty())
			return res;
		for (Usuario usu : this.seguidos) {
			if (usu == usuario) 
				res = true;
		}
		return res;
	}
}
