package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import excepciones.PaqueteNoExisteException;
import excepciones.TipoPubNoExisteException;
import logica.Fabrica;
import logica.datatypes.DtEmpresa;
import logica.datatypes.DtOferta;
import logica.datatypes.DtPostulacion;
import logica.datatypes.DtTipoPub;
import logica.datatypes.EstadoOferta;
import logica.datatypes.DtPostulante;
import logica.interfaces.IOfertaLab;
import logica.interfaces.IUsuario;
import logica.manejadores.ManejadorOfertaLaboral;
import logica.manejadores.ManejadorPaqueteTipoPub;
import logica.manejadores.ManejadorTipoPublicacion;
import logica.manejadores.ManejadorUsuarios;


public class ConsultaTipoPublicacionTest {
	
	private static IOfertaLab interfazOfertaLaboral;
	private static IUsuario interfazUsuario;
	
	private static ManejadorOfertaLaboral manejadorOferta;
	private static ManejadorUsuarios manejadorUsuario;
	private static ManejadorTipoPublicacion manejadorTipo;
	private static ManejadorPaqueteTipoPub manejadorPaquete;
	
    private static final String TP_DIAMANTE = "Diamante";
    private static final String TP_ORO = "Oro";
    private static final String TP_BRONCE = "Bronce";
    
    private static final String PAQUETE_PREMIUM = "Premium";
    private static final String PAQUETE_BASICO = "Basico"; 
    private static final String PAQUETE_DIAMANTE = "Diamante";

	@BeforeAll
	public static void iniciar() {
		Fabrica fabrica = Fabrica.getInstance(); 
		interfazOfertaLaboral = fabrica.getIOfertaLaboral();
		interfazUsuario = fabrica.getIUsuario();
		
		manejadorOferta = ManejadorOfertaLaboral.getInstance();
		manejadorUsuario = ManejadorUsuarios.getInstance();
		manejadorTipo = ManejadorTipoPublicacion.getInstance();
		manejadorPaquete = ManejadorPaqueteTipoPub.getInstance();
	}
	
	@BeforeEach
	public void limpiar() {
		manejadorOferta.eliminarOfertas();
		manejadorOferta.eliminarKeyWords();
		manejadorUsuario.eliminarUsuarios();
		manejadorTipo.eliminarTiposPublicaciones();
		manejadorPaquete.eliminarPaqutes();
	}
	
	@Test
	public void testObtenerTipoPublicaciones() throws Exception {
	    setupTiposPublicacion();

	    DtTipoPub[] tiposPublicaciones = interfazOfertaLaboral.obtenerTipoPublicaciones();

	    assertNotNull(tiposPublicaciones);
	    assertTrue(tiposPublicaciones.length > 0);

	    List<String> nombresTipos = Arrays.stream(tiposPublicaciones)
	            .map(DtTipoPub::getNombre)
	            .collect(Collectors.toList());

	    assertTrue(nombresTipos.contains(TP_DIAMANTE));
	    assertTrue(nombresTipos.contains(TP_ORO));
	    assertTrue(nombresTipos.contains(TP_BRONCE));

	    manejadorTipo.eliminarTiposPublicaciones();
	
	    Exception exception = assertThrows(TipoPubNoExisteException.class, () -> {
	        interfazOfertaLaboral.obtenerTipoPublicaciones();
	    });

	    String expectedMessage = "No hay tipos de publicacion registrados";
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	
	@Test
	public void testObtenerTipoPublicacionesDePaquete() throws Exception {
	    setupTiposPublicacion();
	    setupPaquetes(); 

	    DtTipoPub[] tiposPublicaciones = interfazOfertaLaboral.obtenerTipoPublicaciones(PAQUETE_BASICO);

	    assertNotNull(tiposPublicaciones);
	    assertTrue(tiposPublicaciones.length > 0);

	    List<String> nombresTipos = Arrays.stream(tiposPublicaciones)
	            .map(DtTipoPub::getNombre)
	            .collect(Collectors.toList());
	    
	    assertTrue(nombresTipos.contains(TP_DIAMANTE));
	    assertTrue(nombresTipos.contains(TP_ORO));
	    assertFalse(nombresTipos.contains(TP_BRONCE));

	    // Test con un paquete que no existe
	    Exception exceptionNoPaquete = assertThrows(PaqueteNoExisteException.class, () -> {
	        interfazOfertaLaboral.obtenerTipoPublicaciones("Inexistente");
	    });

	    String actualMessage = exceptionNoPaquete.getMessage();
	    assertTrue(actualMessage.contains("El paquete no existe"));
	    
	    manejadorTipo.eliminarTiposPublicaciones();
	    
	    // Test cuando no existen tipos de publicaciones
	    Exception exceptionNoTipos = assertThrows(TipoPubNoExisteException.class, () -> {
	        interfazOfertaLaboral.obtenerTipoPublicaciones(PAQUETE_DIAMANTE);
	    });

	    String actualMessageTipos = exceptionNoTipos.getMessage();
	    assertTrue(actualMessageTipos.contains("No hay tipos de publicacion registrados"));
	}
	
	public void setupTiposPublicacion() throws Exception {
		interfazOfertaLaboral.ingresarDatosTipoPublicacion(TP_DIAMANTE, "El mejor tipo de publicacion", 10, 2, 200f, LocalDate.now().minusDays(1));
		interfazOfertaLaboral.ingresarDatosTipoPublicacion(TP_ORO, "El mejor tipo de publicacion calidad-precio", 10, 2, 200f, LocalDate.now().minusDays(1));
		interfazOfertaLaboral.ingresarDatosTipoPublicacion(TP_BRONCE, "El peor tipo de publicacion", 10, 2, 200f, LocalDate.now().minusDays(1));
	}
	
	public void setupPaquetes() throws Exception {
	    byte[] imgDummy = new byte[0];
	    LocalDate fechaAlta = LocalDate.now();
	    int periodoValidez = 30; 
	    float descuento = 10.0f; 
	    interfazOfertaLaboral.ingresarDatosPaquete(PAQUETE_PREMIUM, "Descripcion Paquete Premium", imgDummy, fechaAlta, periodoValidez, descuento);
	    interfazOfertaLaboral.ingresarDatosPaquete(PAQUETE_DIAMANTE, "Descripcion Paquete Diamante", imgDummy, fechaAlta, periodoValidez, descuento);
	    interfazOfertaLaboral.ingresarDatosPaquete(PAQUETE_BASICO, "Descripcion Paquete Basic", imgDummy, fechaAlta, periodoValidez, descuento);
	    
	    interfazOfertaLaboral.agregarTipoPubAPaquete(1, TP_DIAMANTE, PAQUETE_BASICO);
	    interfazOfertaLaboral.agregarTipoPubAPaquete(1, TP_ORO, PAQUETE_BASICO);
	}
}
