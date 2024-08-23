package logica.interfaces;
import logica.datatypes.DtCompraPaquete;
import logica.datatypes.DtEmpresa;
import logica.datatypes.DtOferta;
import logica.datatypes.DtPaquetePub;
import logica.datatypes.DtPostulacion;
import logica.datatypes.DtPostulante;
import logica.datatypes.DtUsuario;
import excepciones.PostulacionYaExisteException;
import excepciones.RankingYaOcupadoException;
import excepciones.TipoPubNoExisteException;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioRegistradoException;
import java.time.LocalDate;
import excepciones.FechaInvalidaException;
import excepciones.PaqueteNoExisteException;
import excepciones.PaqueteYaCompradoException;

public interface IUsuario {
	
	public abstract void ingresarDatosPostulante(String nickname, String nombre, String apellido, String password, String correo, byte[] image, String pais, LocalDate fechaNac) throws UsuarioRegistradoException, FechaInvalidaException;

	public abstract void ingresarDatosEmpresa(String nickname, String nombre, String apellido, String password, String correo, byte[] image, String descripcion, String link) throws UsuarioRegistradoException, FechaInvalidaException;

	public abstract void ingresarDatosPostulacion(String cvReducido, String motivacion, LocalDate fechaPostulacion, String oferta, String postulante, String videoUrl) throws PostulacionYaExisteException, FechaInvalidaException, UsuarioNoExisteException;

	public abstract void modificarUsuario(String nickname, String nombre, String apellido, LocalDate fechaNac, String pais, byte[] image) throws FechaInvalidaException, UsuarioNoExisteException;

	public abstract void modificarUsuario(String nickname, String nombre, String apellido, String descripcion, String link, byte[] image) throws UsuarioNoExisteException;

	public abstract DtUsuario obtenerUsuario(String emailOrNickname) throws UsuarioNoExisteException;
	
	public abstract boolean existePostulacion(String ofertaDt, String postulanteDt) throws UsuarioNoExisteException;
	
	public abstract DtUsuario[] obtenerUsuarios() throws UsuarioNoExisteException;

	public abstract DtEmpresa[] obtenerEmpresas();
	
	public abstract DtEmpresa[] busquedaEmpresas(String busqueda);
	
	public abstract int obtenerCantPaqPubEmpresa(String paquete, String tipoPub, String empresaDt) throws UsuarioNoExisteException, TipoPubNoExisteException; 

	public abstract DtPostulante[] obtenerPostulantes() throws UsuarioNoExisteException;

	public abstract void cargarDatosPostulaciones() throws Exception;
	
	public abstract void comprarPaquete(String empresa, String paquete, LocalDate fecha) throws UsuarioNoExisteException, PaqueteYaCompradoException;
	
	public abstract DtPaquetePub[] obtenerPaquetesCompradosValidos(String empresa, String tipoPub) throws UsuarioNoExisteException, TipoPubNoExisteException;

	public abstract void cargarDatos() throws Exception;

	public abstract void cargarDatosCompraPaquetes() throws Exception;
	
	public abstract DtPostulacion obtenerPostulacion(String oferta, String postulante);
	
	public abstract DtUsuario[] obtenerSeguidores(String nickname) throws UsuarioNoExisteException; 
	
	public abstract DtUsuario[] obtenerSeguidos(String nickname) throws UsuarioNoExisteException;
	
	public abstract void agregarSeguidor(String nick1, String nick2) throws UsuarioNoExisteException;
	
	public abstract void eliminarSeguidor(String nick1, String nick2) throws UsuarioNoExisteException;
	
	public abstract boolean yaSeguido(String nick1, String nick2) throws UsuarioNoExisteException;

	public abstract void rankearPostulante(String nomOferta, String nomPostulante, LocalDate fechaRank, int rank) throws UsuarioNoExisteException, RankingYaOcupadoException;
	
	public abstract DtCompraPaquete obtenerCompra(String empresa, String paquete) throws PaqueteNoExisteException;

	public abstract void agregarOfertaFavorita(String postulante, String oferta) throws UsuarioNoExisteException;
	
	public abstract void eliminarOfertaFavorita(String postulante, String oferta) throws UsuarioNoExisteException;
	
	public abstract DtOferta[] obtenerOfertasFavoritas(String postulante) throws UsuarioNoExisteException;

}
