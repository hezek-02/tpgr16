package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import excepciones.CostoMayorACeroExcepcion;
import excepciones.DescuentoMayorACeroExcepcion;
import excepciones.FechaInvalidaException;
import excepciones.PaqueteRegistrado;
import excepciones.TipoPubNoExisteException;
import excepciones.TipoPubliRegistradoException;
import logica.Fabrica;
import logica.datatypes.DtTipoPub;
import logica.interfaces.IOfertaLab;
import logica.manejadores.ManejadorOfertaLaboral;
import logica.manejadores.ManejadorPaqueteTipoPub;
import logica.manejadores.ManejadorTipoPublicacion;
import logica.manejadores.ManejadorUsuarios;
import logica.modelos.TipoPublicacion;


class AltaTipoPublicacionTest {
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
		
		
		for (int i = 0; i < nombres.length; i++) {
			try {
				//Registrar tipo publicacion
				controladorOferta.ingresarDatosTipoPublicacion(nombres[i], descripciones[i], duraciones[i], exposicion[i], costoB[i], fechaAlta[i]);
				DtTipoPub tipoPublicacion = manejadorTP.obtenerDtTipoPublicacion(nombres[i]);
				
				assertEquals(tipoPublicacion.getNombre(), nombres[i]);
		        assertEquals(tipoPublicacion.getDescripcion(), descripciones[i]);
		        assertEquals(tipoPublicacion.getDuracion(), duraciones[i]);
		        assertEquals(tipoPublicacion.getExposicion(), exposicion[i]);
		        assertEquals(tipoPublicacion.getCosto(), costoB[i]);
		        assertEquals(tipoPublicacion.getFechaAlta(), fechaAlta[i]);
			} catch (TipoPubNoExisteException | TipoPubliRegistradoException | FechaInvalidaException e) {
				fail(e.getMessage());
				e.printStackTrace();
			}
		}
		//Nombre ya registrado
		try {
			controladorOferta.ingresarDatosTipoPublicacion("Destacada", "Destaca tu anuncio", 3, 2, 23f, LocalDate.of(2023, 5, 8));
		} catch (TipoPubliRegistradoException | FechaInvalidaException e) {
			assertEquals(e.getMessage(), "El nombre del tipo de publicación se encuentra registrado");
		}
		try {
			controladorOferta.ingresarDatosTipoPublicacion("Nova", "Destaca tu anuncio x 100", 3, 2, 23f, LocalDate.of(1200, 5, 8));
		} catch (TipoPubliRegistradoException | FechaInvalidaException e) {
			assertEquals(e.getMessage(), "La fecha debe tener menos de 80 años y ser menor o igual a la actual");
		}
		try {
			controladorOferta.ingresarDatosTipoPublicacion("Nova3.0", "Destaca tu anuncio x 100", 3, 2, 23f, LocalDate.of(3000, 5, 8));
		} catch (TipoPubliRegistradoException | FechaInvalidaException e) {
			assertEquals(e.getMessage(), "La fecha debe tener menos de 80 años y ser menor o igual a la actual");
		}
		
		//Nombre tipo publicación no existe
		try {
			DtTipoPub tipoPublicacion = manejadorTP.obtenerDtTipoPublicacion("Común");
		} catch (TipoPubNoExisteException e) {
			assertEquals(e.getMessage(), "No existe tipo publicacion!");
		}
		//obtener publis
		try {
			DtTipoPub[] tipoPubs = controladorOferta.obtenerTipoPublicaciones();
			controladorOferta.ingresarDatosPaquete("Básico", "" , null, LocalDate.of(2023, 8, 16), 2, 2);
			ManejadorPaqueteTipoPub mpaq = ManejadorPaqueteTipoPub.getInstance();
			for (int i=0 ; i < 4 ; i++) {
				TipoPublicacion[] tipoPubPaq = manejadorTP.obtenerTiposPublicaciones("Básico");
			}
		} catch (TipoPubNoExisteException | PaqueteRegistrado | DescuentoMayorACeroExcepcion | CostoMayorACeroExcepcion | FechaInvalidaException e) {
			assertEquals(e.getMessage(), "No existe tipo publicacion!");
		}
	}
}

