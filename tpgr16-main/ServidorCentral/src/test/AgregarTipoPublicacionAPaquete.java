package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import excepciones.FechaInvalidaException;
import excepciones.PaqueteNoExisteException;
import excepciones.PaqueteRegistrado;
import excepciones.TipoPubNoExisteException;
import excepciones.TipoPubRegistradoEnPaqueteException;
import excepciones.TipoPubliRegistradoException;
import excepciones.CostoMayorACeroExcepcion;
import excepciones.DescuentoMayorACeroExcepcion;
import logica.Fabrica;
import logica.datatypes.DtPaqPub;
import logica.datatypes.DtPaquetePub;
import logica.datatypes.DtTipoPub;
import logica.interfaces.IOfertaLab;
import logica.manejadores.ManejadorOfertaLaboral;
import logica.manejadores.ManejadorPaqueteTipoPub;
import logica.manejadores.ManejadorTipoPublicacion;
import logica.manejadores.ManejadorUsuarios;

class AgregarTipoPublicacionAPaquete {
	private static IOfertaLab controladorOferta;
	private static ManejadorOfertaLaboral manejadorOf;
	private static ManejadorTipoPublicacion manejadorTP;
	
	@BeforeAll
	public static void iniciar() {
		Fabrica fabrica = Fabrica.getInstance();
		controladorOferta = fabrica.getIOfertaLaboral();
		manejadorOf = ManejadorOfertaLaboral.getInstance();	
		manejadorTP = ManejadorTipoPublicacion.getInstance();
		manejadorOf.eliminarOfertas();
		manejadorOf.eliminarKeyWords();
		ManejadorUsuarios.getInstance().eliminarUsuarios();
		manejadorTP.eliminarTiposPublicaciones();
		ManejadorPaqueteTipoPub.getInstance().eliminarPaqutes();

	}	

	@SuppressWarnings("unused")
	@Test
	public void testRegistrarTipoPubExitosa() {		
		String[] nombres = {"Premium", "Destacada", "Estándar", "Básica"};
		String[] descripciones = {"Obtén máxima visibilidad", "Destaca tu anuncio", "Mejora la posición de tu anuncio", "Publica de forma sencilla en la lista de ofertas"};
		int[] duraciones = {30, 15, 20, 7};
		int[] exposicion = {1, 2, 3, 4};
		Float[] costoB = {4000f, 500f, 150f, 50f};
		LocalDate[] fechaAlta = { LocalDate.of(2023, 8, 10), LocalDate.of(2023, 8, 15), LocalDate.of(2023, 8, 5), LocalDate.of(2023, 8, 7)};
		float[] descuento = {20, 10, 15, 5};
		float[] costoBase = {0, 0, 0, 0};
		DtPaqPub[] paqPub=null; 
		for (int i = 0; i < nombres.length; i++) {
			try {
				//Registrar tipo publicacion
				controladorOferta.ingresarDatosTipoPublicacion(nombres[i], descripciones[i], duraciones[i], exposicion[i], costoB[i], fechaAlta[i]);
				ManejadorPaqueteTipoPub manejadorPaquete = ManejadorPaqueteTipoPub.getInstance();
				controladorOferta.ingresarDatosPaquete(nombres[i], descripciones[i] , null, fechaAlta[i], duraciones[i], descuento[i]);
				DtPaquetePub paquete = manejadorPaquete.obtenerDtPaquete(nombres[i]);
				DtTipoPub tipoPublicacion = manejadorTP.obtenerDtTipoPublicacion(nombres[i]);
				controladorOferta.agregarTipoPubAPaquete(3, tipoPublicacion.getNombre(), paquete.getNombre());
				controladorOferta.obtenerCantPaqPub(paquete.getNombre(), tipoPublicacion.getNombre() );
				paqPub = controladorOferta.obtenerPaqPubs(paquete.getNombre());

				

				assertEquals(tipoPublicacion.getNombre(), nombres[i]); 
		        assertEquals(tipoPublicacion.getDescripcion(), descripciones[i]);
		        assertEquals(tipoPublicacion.getDuracion(), duraciones[i]);
		        assertEquals(tipoPublicacion.getExposicion(), exposicion[i]);
		        assertEquals(tipoPublicacion.getCosto(), costoB[i]);
		        assertEquals(tipoPublicacion.getFechaAlta(), fechaAlta[i]);
		        
		        
			} catch (TipoPubNoExisteException | TipoPubliRegistradoException | FechaInvalidaException | PaqueteRegistrado | DescuentoMayorACeroExcepcion | CostoMayorACeroExcepcion | PaqueteNoExisteException | TipoPubRegistradoEnPaqueteException e) {
				fail(e.getMessage());
			}
		}
		assertEquals(paqPub[0].getNombrePaquete(), nombres[3]);
		assertEquals(paqPub[0].getCantidad(), 3); 
		assertEquals(paqPub[0].getNombreTipoPub(), nombres[3]);
	
		try {
			ManejadorPaqueteTipoPub manejadorPaquete = ManejadorPaqueteTipoPub.getInstance();
			DtPaquetePub paquete = manejadorPaquete.obtenerDtPaquete("Premium");
			DtTipoPub tipoPublicacion = manejadorTP.obtenerDtTipoPublicacion("Premium");
			controladorOferta.agregarTipoPubAPaquete(3, tipoPublicacion.getNombre(), paquete.getNombre());
		} catch (TipoPubRegistradoEnPaqueteException | PaqueteNoExisteException | TipoPubNoExisteException  e) {
			assertEquals(e.getMessage(), "El paquete ya tiene asociado dicho tipo de publicación");
		}
		
		
		
	}
}

