package webservices;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Properties;

import excepciones.FechaInvalidaException;
import excepciones.PaqueteNoExisteException;
import excepciones.PaqueteYaCompradoException;
import excepciones.PostulacionYaExisteException;
import excepciones.RankingYaOcupadoException;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioRegistradoException;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;
import jakarta.jws.soap.SOAPBinding.Style;
import jakarta.xml.ws.Endpoint;
import logica.Fabrica;
import logica.datatypes.DtCompraPaquete;
import logica.datatypes.DtEmpresa;
import logica.datatypes.DtOferta;
import logica.datatypes.DtPostulacion;
import logica.datatypes.DtPostulante;
import logica.datatypes.DtUsuario;
import logica.interfaces.IOfertaLab;
import logica.interfaces.IUsuario;

@WebService
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class WebServicesIUsuario {

	private IOfertaLab interfazOfertaLaboral;
	private IUsuario interfazUsuario;
	
    private Endpoint endpoint = null;

    public WebServicesIUsuario(){
    	interfazOfertaLaboral = Fabrica.getInstance().getIOfertaLaboral();
    	interfazUsuario = Fabrica.getInstance().getIUsuario();
    }

    /* Operaciones las cuales quiero publicar */

    @WebMethod(exclude = true)
    public void publicar(){
         endpoint = Endpoint.publish(obtenerURLDesdeConfiguracion(), this);
    }

    @WebMethod(exclude = true)
    public Endpoint getEndpoint() {
            return endpoint;
    }
    
    //OBTENER DATATYPES CONCRETOS, REFERIDOS A USUARIO
    @WebMethod
    public DtPostulacion obtenerPostulacion(String nomOferta,String nomPostu) {
    	return interfazUsuario.obtenerPostulacion(nomOferta, nomPostu);
    }
    
    @WebMethod
    public DtEmpresa obtenerEmpresa(String nomUsu) throws UsuarioNoExisteException{	 
    	DtUsuario usuarioDt = interfazUsuario.obtenerUsuario(nomUsu);
    	if (usuarioDt instanceof DtEmpresa)
    		return (DtEmpresa) usuarioDt;
    	else
    		return null;
    }
    
    @WebMethod
    public DtPostulante obtenerPostulante(String nomUsu) throws UsuarioNoExisteException{	 
    	DtUsuario usuarioDt = interfazUsuario.obtenerUsuario(nomUsu);
    	if (usuarioDt instanceof DtPostulante)
    		return (DtPostulante) usuarioDt;
    	else
    		return null;
    }
    
    @WebMethod
    public DtEmpresa obtenerEmpresaDadoOferta(String nomOferta) throws UsuarioNoExisteException{	 
    	return interfazOfertaLaboral.obtenerEmpresa(nomOferta);
    }
    
    //OBTENER COLECCIONES DE DATATYPES, REFERIDOS A USUARIO
    @WebMethod
    public DtEmpresa[] obtenerEmpresas() throws UsuarioNoExisteException{	 
    	return interfazUsuario.obtenerEmpresas();
    }
    
    @WebMethod
    public DtPostulante[] obtenerPostulantes() throws UsuarioNoExisteException{	 
    	return interfazUsuario.obtenerPostulantes();
    }
    
    public DtUsuario obtenerUsuario(String nickname) throws UsuarioNoExisteException {
    	return interfazUsuario.obtenerUsuario(nickname);
    }
    
    public DtEmpresa[] busquedaEmpresas(String busqueda) throws UsuarioNoExisteException {
    	return interfazUsuario.busquedaEmpresas(busqueda);
    }
    
    // ALTA USUARIO - EMPRESA
    @WebMethod
	public void ingresarDatosEmpresa(String nickname, String nombre, String apellido, String password, String correo, byte[] image, String descripcion, String link) throws UsuarioRegistradoException, FechaInvalidaException {
    	interfazUsuario.ingresarDatosEmpresa(nickname, nombre, apellido, password, correo, image, descripcion, link);
    }
    
    // ALTA USUARIO - POSTULANTE
	public void ingresarDatosPostulante(String nickname, String nombre, String apellido, String password, String correo, byte[] image, String pais, String fechaNac) throws UsuarioRegistradoException, FechaInvalidaException {
		LocalDate fechaNacimiento = LocalDate.parse(fechaNac);
		interfazUsuario.ingresarDatosPostulante(nickname, nombre, apellido, password, correo, image, pais, fechaNacimiento);
	}
    
	//POSTULARSE A OFERTA LABORAL
	@WebMethod
	public void ingresarDatosPostulacion(String cvReducido,String motivacion,String nomOferta,String nickPostulante, String videoUrl) throws PostulacionYaExisteException, FechaInvalidaException, UsuarioNoExisteException {
    	LocalDate fechaDeAlta = LocalDate.now();

		interfazUsuario.ingresarDatosPostulacion(cvReducido, motivacion, fechaDeAlta, nomOferta, nickPostulante,  videoUrl);
	}
	
    //CHEQUEOS
    @WebMethod
	public Boolean existePostulacion(String nomOferta,String nickPostulante) throws UsuarioNoExisteException{
    	return interfazUsuario.existePostulacion(nomOferta, nickPostulante);
    }
    
    @WebMethod
    public boolean esEmpresa(String nomUsu) throws UsuarioNoExisteException{	 
    	DtUsuario usuarioDt = interfazUsuario.obtenerUsuario(nomUsu);
    	if (usuarioDt instanceof DtEmpresa)
    		return true;
    	else
    		return false;
    }
    
    //MODIFICAR USUARIOS
    @WebMethod
    public void modificarPostulante(String nickname, String nombre, String apellido, String fechaNacimientoStr, String nacionalidad, byte[] imgName) throws FechaInvalidaException, UsuarioNoExisteException {
    	LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoStr);
    	interfazUsuario.modificarUsuario(nickname, nombre, apellido, fechaNacimiento, nacionalidad, imgName);
    }

    @WebMethod
    public void modificarEmpresa(String nickname, String nombre, String apellido, String descripcion, String websiteLink, byte[] imgName) throws FechaInvalidaException, UsuarioNoExisteException {
    	interfazUsuario.modificarUsuario(nickname, nombre, apellido, descripcion, websiteLink, imgName);
    }
    
    //SEGUIDORES Y SEGUIDOS
    @WebMethod
    public DtUsuario[] obtenerSeguidores(String nickname) throws UsuarioNoExisteException{
    	return interfazUsuario.obtenerSeguidores(nickname);
    }
    
    @WebMethod
    public DtUsuario[] obtenerSeguidos(String nickname) throws UsuarioNoExisteException{
    	return interfazUsuario.obtenerSeguidos(nickname);
    }
    
    @WebMethod
    public void agregarSeguidor(String nick1, String nick2) throws UsuarioNoExisteException{
    	interfazUsuario.agregarSeguidor(nick1, nick2);
    }
    
    @WebMethod
    public void eliminarSeguidor(String nick1, String nick2) throws UsuarioNoExisteException{
    	interfazUsuario.eliminarSeguidor(nick1, nick2);
    }

    @WebMethod
    public boolean yaSeguido(String nick1, String nick2) throws UsuarioNoExisteException{
    	return interfazUsuario.yaSeguido(nick1, nick2);
    }
    //COMPRAR PAQUETE
    @WebMethod
    public void comprarPaquete(String nomEmpresa, String nomPaquete) throws UsuarioNoExisteException, PaqueteYaCompradoException{
    	interfazUsuario.comprarPaquete(nomEmpresa, nomPaquete, LocalDate.now());
    }
    
    //OTORGAR POSICION EN OFERTA A POSTULANTE
    @WebMethod
    public void rankearPostulante(String nomOferta, String nomPostulante, int rank) throws UsuarioNoExisteException, PaqueteYaCompradoException, RankingYaOcupadoException{
    	interfazUsuario.rankearPostulante(nomOferta, nomPostulante, LocalDate.now(), rank);
    }
    
    @WebMethod
    public DtCompraPaquete obtenerCompra(String empresa, String paquete) throws PaqueteNoExisteException {
    	return interfazUsuario.obtenerCompra(empresa, paquete);
    }
    
    @WebMethod
    public void agregarOfertaFavorita(String postulante, String oferta) throws UsuarioNoExisteException {
    	interfazUsuario.agregarOfertaFavorita(postulante, oferta);
    }
    
    @WebMethod
    public void eliminarOfertaFavorita(String postulante, String oferta) throws UsuarioNoExisteException {
    	interfazUsuario.eliminarOfertaFavorita(postulante, oferta);
    }
    
    @WebMethod
    public DtOferta[] obtenerOfertasFavoritas(String postulante) throws UsuarioNoExisteException {
    	return interfazUsuario.obtenerOfertasFavoritas(postulante);
    }
    
    @WebMethod
    public String healthCheck(){
    	return "Server Running - Response 200";
    }
    
    private String obtenerURLDesdeConfiguracion() {
        Properties properties = new Properties();

        try (InputStream input = new FileInputStream("/ens/devel01/tpgr16/tpgr16/config.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                System.err.println("No se pudo cargar el archivo de configuración. FALLO USUARIO");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String wsIP = properties.getProperty("ws_ip");
        String wsPort = properties.getProperty("ws_port");
        System.out.println(wsIP);

        return "http://" + wsIP + ":" + wsPort + "/usuario";
    }
}
