package logica.interfaces;

import java.time.LocalDate;

import excepciones.CostoMayorACeroExcepcion;
import excepciones.ExisteKeyWord;
import excepciones.FechaInvalidaException;
import excepciones.NoPoseePostulacionesException;
import excepciones.OfertaLaboralYaExisteException;
import excepciones.OfertaNoExisteException;
import excepciones.PaqueteNoExisteException;
import excepciones.PaqueteNoTieneMasTipoPubExcepcion;
import excepciones.PaqueteRegistrado;
import excepciones.TipoPubNoExisteException;
import excepciones.TipoPubRegistradoEnPaqueteException;
import excepciones.TipoPubliRegistradoException;
import excepciones.UsuarioNoExisteException;
import excepciones.DescuentoMayorACeroExcepcion;
import logica.datatypes.DtEmpresa;
import logica.datatypes.DtKeyWord;
import logica.datatypes.DtOferta;
import logica.datatypes.DtPaqPub;
import logica.datatypes.DtPaquetePub;
import logica.datatypes.DtPostulacion;
import logica.datatypes.DtPostulante;
import logica.datatypes.DtTipoPub;
import logica.datatypes.EstadoOferta;

public interface IOfertaLab {
	
	public abstract void ingresarKeyWord(String nombre) throws ExisteKeyWord;

	public abstract void ingresarDatosOfertaLaboral(String nombre, String descripcion, byte[] img, String horario,
			Float remuneracion, String cuidad, String departamento, LocalDate fechaDeAlta,
			String[] keywords, String empresa, String tipo) throws OfertaLaboralYaExisteException, FechaInvalidaException, UsuarioNoExisteException, CostoMayorACeroExcepcion;

	public abstract void ingresarDatosOfertaLaboral(String nombre, String descripcion, byte[] img, String horario, 
			Float remuneracion, String ciudad, String departamento, LocalDate fechaDeAlta, 
			String[] keywords, String empresa, String tipo, String paquetePub) throws OfertaLaboralYaExisteException, FechaInvalidaException, UsuarioNoExisteException, CostoMayorACeroExcepcion, PaqueteNoTieneMasTipoPubExcepcion, TipoPubNoExisteException;

	public abstract void ingresarDatosTipoPublicacion(String nombre, String descripcion, int duracion, int exposicion, Float costo, LocalDate fechaAlta) throws TipoPubliRegistradoException, FechaInvalidaException;

	public abstract void ingresarDatosPaquete(String nombre, String descripcion, byte[] img, LocalDate fechaAlta, int periodoValidez, float descuento) throws PaqueteRegistrado, DescuentoMayorACeroExcepcion, CostoMayorACeroExcepcion, FechaInvalidaException;

	public abstract DtOferta obtenerDtOferta(String nombreOferta) throws OfertaNoExisteException ;
	
	public boolean vencida(String nomOferta);
	
	public abstract DtTipoPub[] obtenerTipoPublicaciones() throws TipoPubNoExisteException;
	
	public abstract DtTipoPub[] obtenerTipoPublicacionesQueNoEstanEnPaquete(String paquete) throws TipoPubNoExisteException, PaqueteNoExisteException;

	public abstract DtTipoPub obtenerTipoPublicacion(String nombre) throws TipoPubNoExisteException;

	public abstract DtPaqPub[] obtenerPaqPubs(String paquete);

	public abstract int obtenerCantPaqPub(String paquete, String tipoPub) throws TipoPubNoExisteException;

	public abstract DtKeyWord[] obtenerKeyWords();

	public abstract DtOferta[] obtenerOfertas(String usuario) throws UsuarioNoExisteException;
	
	public abstract DtOferta[] obtenerOfertasIngresadas(String emp) throws UsuarioNoExisteException;
	
	public abstract DtOferta[] obtenerOfertasFinalizadas(String empresaNom) throws UsuarioNoExisteException;
	
	public DtOferta[] obtenerOfertasFinalizadas();
	
	public abstract DtOferta[] obtenerOfertasConfirmadas(String emp) throws UsuarioNoExisteException;
	
	public abstract DtOferta[] obtenerOfertasConfirmadas(String emp, String[] keywordArray) throws UsuarioNoExisteException;

	public DtOferta[] obtenerOfertasRechazadas(String emp) throws UsuarioNoExisteException;
	
	public DtOferta[] busquedaOfertas(String busqueda, String[] keywordArray) throws UsuarioNoExisteException;

	public abstract DtPaquetePub[] obtenerPaquetes() throws PaqueteNoExisteException;
	
	public abstract DtPaquetePub[] obtenerPaquetesNoComprados() throws PaqueteNoExisteException;
	
	public abstract DtPostulacion[] obtenerPostulaciones(String oferta) throws NoPoseePostulacionesException;
		
	public abstract DtEmpresa obtenerEmpresa(String oferta);
	
	public abstract DtPaquetePub obtenerPaquete(String nombrePaquete) throws PaqueteNoExisteException;

	public abstract void agregarTipoPubAPaquete(int cantidad, String tipoPub, String paquete) throws TipoPubRegistradoEnPaqueteException;

	public abstract void actualizarOfertaEstado(String oferta, EstadoOferta estado);
	
	public abstract DtPaquetePub[] obtenerPaquetesValidos(String empresa) throws UsuarioNoExisteException;
	
	public abstract void cargarDatos() throws Exception;

	public abstract void cargarDatosAltaOfertas() throws Exception;
	
	public abstract DtPaquetePub obtenerPaqueteCompra(String oferta) throws PaqueteNoExisteException;

	public abstract void cargaPersistenciaOfertasFinalizadas();

	public abstract DtOferta[] obtenerOfertasPostuladas(String postulante) throws NoPoseePostulacionesException, UsuarioNoExisteException;
	
	public abstract DtPostulante[] obtenerPostulantesFavoritos(String oferta);
	
	public abstract void cargarVisitas(String oferta, int visitas);

	public abstract void visitar(String oferta);

	public abstract DtTipoPub[] obtenerTipoPublicaciones(String string) throws TipoPubNoExisteException, PaqueteNoExisteException;
}
