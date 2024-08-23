package logica.manejadores;
import java.util.Map;

import excepciones.UsuarioNoExisteException;
import java.util.HashMap;
import logica.datatypes.DtUsuario;
import logica.modelos.Usuario;


public class ManejadorUsuarios {
	private Map<String, Usuario> colNickUsuarios;
	private Map<String, Usuario> colMailUsuarios;
	
	private static ManejadorUsuarios instance = null;
	
	private ManejadorUsuarios() {
		this.colNickUsuarios = new HashMap<>();
		this.colMailUsuarios = new HashMap<>();
	}
	
	public static ManejadorUsuarios getInstance(){
		if (instance == null)
			instance = new ManejadorUsuarios();
		return instance;
	}

//	public void eliminarUsuario(String nick) {
//		this.colMailUsuarios.remove(obtenerUsuario(nick).getCorreo());
//		this.colNickUsuarios.remove(nick);
//
//	}

	public void eliminarUsuarios() {
		this.colMailUsuarios.clear();
		this.colNickUsuarios.clear();
	}

	public void agregarUsuario(Usuario usuario) {
		this.colNickUsuarios.put(usuario.getNickname(), usuario);
		this.colMailUsuarios.put(usuario.getCorreo(), usuario);
	}
	
	public Usuario obtenerUsuario(String nick) throws UsuarioNoExisteException {//nick u correo
		if (this.colNickUsuarios.get(nick)!=null)
			return this.colNickUsuarios.get(nick);
		else if (this.colMailUsuarios.get(nick)!=null)
			return this.colMailUsuarios.get(nick);
		else
			throw new UsuarioNoExisteException("El Usuario no existe!");
	}
	
	public boolean existeUsuario(String nick) {//nick u correo
		return this.colNickUsuarios.containsKey(nick) || this.colMailUsuarios.containsKey(nick);
	}
	
	public Usuario[] obtenerUsuarios() {
		if (colNickUsuarios.isEmpty()) {
			return null;
		}
		return colNickUsuarios.values().toArray(new Usuario[0]);
	}

	
	public DtUsuario obtenerDtUsuario(String clave) throws UsuarioNoExisteException {
		Usuario usu = this.obtenerUsuario(clave);
		if (usu != null) {
			return usu.obtenerDtUsu();
		}
		else
			throw new UsuarioNoExisteException("El Usuario no existe!");
	}
	
	
 
}
