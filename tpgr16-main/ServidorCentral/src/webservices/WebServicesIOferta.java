package webservices;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import excepciones.CostoMayorACeroExcepcion;
import excepciones.FechaInvalidaException;
import excepciones.NoPoseePostulacionesException;
import excepciones.OfertaLaboralYaExisteException;
import excepciones.OfertaNoExisteException;
import excepciones.PaqueteNoExisteException;
import excepciones.PaqueteNoTieneMasTipoPubExcepcion;
import excepciones.TipoPubNoExisteException;
import excepciones.UsuarioNoExisteException;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;
import jakarta.jws.soap.SOAPBinding.Style;
import jakarta.xml.ws.Endpoint;
import logica.Fabrica;
import logica.datatypes.DtEmpresa;
import logica.datatypes.DtKeyWord;
import logica.datatypes.DtOferta;
import logica.datatypes.DtPaqPub;
import logica.datatypes.DtPaquetePub;
import logica.datatypes.DtPostulacion;
import logica.datatypes.DtPostulante;
import logica.datatypes.DtTipoPub;
import logica.datatypes.EstadoOferta;
import logica.interfaces.IOfertaLab;
import logica.interfaces.IUsuario;

@WebService
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class WebServicesIOferta {
	
	private IOfertaLab interfazOfertaLaboral;
	private IUsuario interfazUsuario;
	
    private Endpoint endpoint = null;

    public WebServicesIOferta(){
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
    
    
    // OBTENER COLECCIONES DE DATATYPES, REFERIDOS A OFERTA
    @WebMethod
    public DtPostulacion[] obtenerPostulaciones(String nomOferta) throws UsuarioNoExisteException, NoPoseePostulacionesException{	 
    	DtPostulacion[] postulaciones = interfazOfertaLaboral.obtenerPostulaciones(nomOferta);
    	return	postulaciones;	
    }
    
    @WebMethod
    public DtOferta[] obtenerOfertasConfirmadasSinFiltro() throws UsuarioNoExisteException{	 
    	DtEmpresa[] empresas = interfazUsuario.obtenerEmpresas();
        List<DtOferta> listaOfertas = new ArrayList<>();
        for (DtEmpresa emp: empresas) {
            listaOfertas.addAll(Arrays.asList(interfazOfertaLaboral.obtenerOfertasConfirmadas(emp.getNickname())));
        }
        return listaOfertas.toArray(new DtOferta[0]);
    }
    
    @WebMethod
    public DtOferta[] obtenerOfertasConfirmadasConFiltro(String[] keywordsArray) throws UsuarioNoExisteException{	 
    	DtEmpresa[] empresas = interfazUsuario.obtenerEmpresas();
        List<DtOferta> listaOfertas = new ArrayList<>();
        for (DtEmpresa emp: empresas) {
            listaOfertas.addAll(Arrays.asList(interfazOfertaLaboral.obtenerOfertasConfirmadas(emp.getNickname(),keywordsArray)));
        }
        return listaOfertas.toArray(new DtOferta[0]);
    }
    
    @WebMethod
    public DtOferta[] obtenerOfertasConfirmadasPorEmpresaYKeywords(String empresa, String[] keywordsArray) throws UsuarioNoExisteException {
    	return interfazOfertaLaboral.obtenerOfertasConfirmadas(empresa, keywordsArray);
    }
    
    @WebMethod
    public DtOferta[] busquedaOfertas(String busqueda, String[] keywordsArray) throws UsuarioNoExisteException {
    	DtOferta[] ofertas = interfazOfertaLaboral.busquedaOfertas(busqueda, keywordsArray);
    	return  ofertas;
    }
    
    @WebMethod
    public DtTipoPub[] obtenerTiposPublicacion() throws TipoPubNoExisteException {
    	DtTipoPub[] tiposPub = interfazOfertaLaboral.obtenerTipoPublicaciones();
    	return tiposPub;
    }
    
    @WebMethod
    public DtKeyWord[] obtenerKeyWords(){
    	DtKeyWord[] keys = interfazOfertaLaboral.obtenerKeyWords(); 
    	return keys;
    }
    
    @WebMethod
    public DtPaquetePub[] obtenerPaquetes(String empresaNom, String tipoPubNombre) throws UsuarioNoExisteException, TipoPubNoExisteException {
    	DtPaquetePub[] paquetes = interfazUsuario.obtenerPaquetesCompradosValidos(empresaNom,tipoPubNombre);
	   	return paquetes;
    }
    
    @WebMethod
    public DtOferta[] obtenerOfertas(String empresaNom) throws UsuarioNoExisteException {
    	return interfazOfertaLaboral.obtenerOfertas(empresaNom);
    }
    
    @WebMethod
    public DtOferta[] obtenerOfertasIngresadas(String empresaNom) throws UsuarioNoExisteException {
    	return interfazOfertaLaboral.obtenerOfertasIngresadas(empresaNom);
    }
    
    @WebMethod
    public DtOferta[] obtenerOfertasRechazadas(String empresaNom) throws UsuarioNoExisteException {
    	return interfazOfertaLaboral.obtenerOfertasRechazadas(empresaNom);
    }
    
    @WebMethod
    public DtOferta[] obtenerOfertasConfirmadas(String empresaNom) throws UsuarioNoExisteException {
    	return interfazOfertaLaboral.obtenerOfertasConfirmadas(empresaNom);
    }
    
    @WebMethod
    public DtOferta[] obtenerOfertasFinalizadas(String empresaNom) throws UsuarioNoExisteException {
    	return interfazOfertaLaboral.obtenerOfertasFinalizadas(empresaNom);
    }
    
    public DtOferta[] obtenerOfertasPostuladas(String postulante) throws UsuarioNoExisteException, NoPoseePostulacionesException {
    	return interfazOfertaLaboral.obtenerOfertasPostuladas(postulante);
    }
    
    @WebMethod
    public DtPaquetePub[] obtenerPaquetesValidos(String empresaNom) throws UsuarioNoExisteException {
    	return interfazOfertaLaboral.obtenerPaquetesValidos(empresaNom);
    } 
    
    @WebMethod
    public DtPaquetePub[] obtenerPaquetesRegistrados() throws PaqueteNoExisteException {
    	DtPaquetePub[] paquetes = interfazOfertaLaboral.obtenerPaquetes();
    	return paquetes;
    }
    
    @WebMethod 
    public DtPaqPub[] obtenerPublicacionesPaquetes(String nomPaquete) throws PaqueteNoExisteException, TipoPubNoExisteException {
    	DtPaqPub[] pubsPaquetes = interfazOfertaLaboral.obtenerPaqPubs(nomPaquete);
    	return pubsPaquetes;
    }

    //OBTENER DATATYPES CONCRETOS, REFERIDOS A OFERTA
    @WebMethod
    public DtPaquetePub obtenerPaqueteCompra(String nomOferta) throws PaqueteNoExisteException {
    	return interfazOfertaLaboral.obtenerPaqueteCompra(nomOferta);
    }  
    
    @WebMethod
    public DtTipoPub obtenerTipoPublicacion(String tipoPubNombre) throws TipoPubNoExisteException {
    	return interfazOfertaLaboral.obtenerTipoPublicacion(tipoPubNombre);
    }
    
    @WebMethod
    public DtPaquetePub obtenerPaquete(String paqueteNombre) throws TipoPubNoExisteException, PaqueteNoExisteException {
    	return interfazOfertaLaboral.obtenerPaquete(paqueteNombre);
    }
    
    @WebMethod
    public DtOferta obtenerDtOferta(String nomOferta) throws TipoPubNoExisteException, OfertaNoExisteException {
    	return interfazOfertaLaboral.obtenerDtOferta(nomOferta); 
    }
    
    
    //ALTA OFERTA LABORAL CON/SIN PAQUETE
    @WebMethod
    public void ingresarDatosOfertaLaboralConPaquete(String nombre, String descripcion, byte[] image, String horario, Float remuneracion, String ciudad, String departamento,
    		 String[] keywordsArray, String empresaNombre, String tipoPubNombre, String paqueteNombre) throws TipoPubNoExisteException, OfertaLaboralYaExisteException, FechaInvalidaException, UsuarioNoExisteException, CostoMayorACeroExcepcion, PaqueteNoTieneMasTipoPubExcepcion, PaqueteNoExisteException {
    	
    	LocalDate fechaDeAlta = LocalDate.now();

    	interfazOfertaLaboral.ingresarDatosOfertaLaboral(nombre, descripcion, image, horario, remuneracion, ciudad, departamento,
    			fechaDeAlta, keywordsArray,	empresaNombre, tipoPubNombre, paqueteNombre);
    }
    
    @WebMethod
    public void ingresarDatosOfertaLaboralSinPaquete(String nombre, String descripcion, byte[]  image, String horario, Float remuneracion,
    		String ciudad, String departamento, String[] keywordsArray, String empresaNombre, String tipoPubNombre) throws TipoPubNoExisteException, OfertaLaboralYaExisteException, FechaInvalidaException, UsuarioNoExisteException, CostoMayorACeroExcepcion {
    	
    	LocalDate fechaDeAlta = LocalDate.now();
    	interfazOfertaLaboral.ingresarDatosOfertaLaboral(nombre, descripcion, image, horario, remuneracion, ciudad, departamento,
    			fechaDeAlta, keywordsArray, empresaNombre, tipoPubNombre);
    }
    
    // ACTUALIZAR ESTADO DE OFERTA
    @WebMethod
	public void actualizarEstadoDeOferta(String nomOferta, EstadoOferta nuevoEstado) {
		interfazOfertaLaboral.actualizarOfertaEstado(nomOferta, nuevoEstado);
	}
	
    //CHEQUEOS
    @WebMethod
	public Boolean estaVencida(String nomOferta) throws UsuarioNoExisteException{
    	return interfazOfertaLaboral.vencida(nomOferta);
    }
    
    @WebMethod
    public DtPostulante[] obtenerPostulantesFavoritos(String oferta) {
    	return interfazOfertaLaboral.obtenerPostulantesFavoritos(oferta);
    }
    
    @WebMethod
	public void cargarVisitas(String oferta, int visitas) {
    	interfazOfertaLaboral.cargarVisitas(oferta, visitas);
    }
    
    @WebMethod
	public void visitar(String oferta) {
    	interfazOfertaLaboral.visitar(oferta);
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
                System.err.println("No se pudo cargar el archivo de configuración. FALLO OFERTA");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String wsIP = properties.getProperty("ws_ip");
        String wsPort = properties.getProperty("ws_port");

        return "http://" + wsIP + ":" + wsPort + "/oferta";
    }
}
