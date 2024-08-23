package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import excepciones.ExisteKeyWord;
import logica.Fabrica;
import logica.datatypes.DtEmpresa;
import logica.datatypes.DtOferta;
import logica.datatypes.DtTipoPub;
import logica.datatypes.EstadoOferta;
import logica.interfaces.IOfertaLab;
import logica.interfaces.IUsuario;
import logica.manejadores.ManejadorOfertaLaboral;
import logica.manejadores.ManejadorPaqueteTipoPub;
import logica.manejadores.ManejadorTipoPublicacion;
import logica.manejadores.ManejadorUsuarios;

public class BusquedaTest {
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
    
	private static final String EMPRESA_SIN_PAQUETE = "EmpresaSinPaquete";
    private static final String BOOTIA = "bootia";
    private static final String GLOBANT = "globant";
    private static final String MELI = "Mercadolibre";
    
    private static final String K_FULL_TIME = "Full-Time";
    private static final String K_REMOTO = "Remoto";
    private static final String K_TEMPORAL = "Temporal";


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
    public void testBusquedaOfertas() throws Exception {
		setupKeywords();
		setupEmpresas();
	    setupTiposPublicacion();
	    setupOfertas();
	    
	    // Test de busqueda sin keywords
        String busqueda = "Estratega";
        
        DtOferta[] ofertasEncontradas = interfazOfertaLaboral.busquedaOfertas(busqueda, null);
        
        assertNotNull(ofertasEncontradas);
        assertTrue(ofertasEncontradas.length > 0);
        
        // Validar que las ofertas encontradas realmente contengan las palabras clave
        for (DtOferta oferta : ofertasEncontradas) {
            assertTrue(oferta.getNombre().contains(busqueda) || oferta.getDescripcion().contains(busqueda));        
        }
	}
	
	@Test
    public void testBusquedaOfertasConKeywords() throws Exception {
		setupKeywords();
		setupEmpresas();
	    setupTiposPublicacion();
	    setupOfertas();
	    
	    // Test de busqueda sin keywords
        String busqueda = "equipo";
        String[] keywordArray = new String[]{K_TEMPORAL, K_FULL_TIME};
        
        DtOferta[] ofertasEncontradas = interfazOfertaLaboral.busquedaOfertas(busqueda, keywordArray);
        
        assertNotNull(ofertasEncontradas);
        assertTrue(ofertasEncontradas.length > 0);
        
        // Validar que las ofertas encontradas realmente contengan las palabras clave
        for (DtOferta oferta : ofertasEncontradas) {
            assertTrue(oferta.getNombre().contains(busqueda) || oferta.getDescripcion().contains(busqueda));        
        }
	}
	
	@Test
    public void testBusquedaEmpresas() throws Exception {
		setupEmpresas();
	    
	    // Test de busqueda sin keywords
        String busqueda = "bootia";
        
        DtEmpresa[] empresasEncontradas = interfazUsuario.busquedaEmpresas(busqueda);
        
        assertNotNull(empresasEncontradas);
        assertTrue(empresasEncontradas.length > 0);
        
        // Validar que las ofertas encontradas realmente contengan las palabras clave
        for (DtEmpresa empresa : empresasEncontradas) {
            assertTrue(empresa.getNickname().contains(busqueda) || empresa.getDescripcion().contains(busqueda));        
        }
	}
	
	private void setupEmpresas() throws Exception {
		interfazUsuario.ingresarDatosEmpresa(BOOTIA, "Bootia", "SAS", "xxx", "bootia@bootia.dev", null, "A small software factory", "bootia.dev");
		interfazUsuario.ingresarDatosEmpresa(GLOBANT, "Globant", "SAS", "xxx", "globant@globant.com", null, "We make the best products globally", "globant.com");
		interfazUsuario.ingresarDatosEmpresa(MELI, "Mercado Libre", "xxx", "Company", "mercadolibre@mercadolibre.com", null, "We sell goods to good people", "mercadolibre.com");
		interfazUsuario.ingresarDatosEmpresa(EMPRESA_SIN_PAQUETE, "Empresa sin paquete", "xxx", "Company", "sinpaquete@paquete.com", null, "We sell goods to good people", "sinpaquete.com");
	}
	
	private void setupTiposPublicacion() throws Exception {
		interfazOfertaLaboral.ingresarDatosTipoPublicacion(TP_DIAMANTE, "El mejor tipo de publicacion", 10, 2, 200f, LocalDate.now().minusDays(1));
		interfazOfertaLaboral.ingresarDatosTipoPublicacion(TP_ORO, "El mejor tipo de publicacion calidad-precio", 10, 2, 200f, LocalDate.now().minusDays(1));
		interfazOfertaLaboral.ingresarDatosTipoPublicacion(TP_BRONCE, "El peor tipo de publicacion", 10, 2, 200f, LocalDate.now().minusDays(1));
	}
	
	private void setupOfertas() throws Exception {
		byte[] imgDummy = new byte[0];
		interfazOfertaLaboral.ingresarDatosOfertaLaboral("Estratega de Negocios", "Forma parte de nuestro equipo de estrategia y contribuye al crecimiento de las empresas clientes.", imgDummy, "08:00:17:00", (float) 80000.0, "Maldonado", "Punta del Este", LocalDate.of(2023, 9, 29), new String[] {K_TEMPORAL}, BOOTIA, TP_DIAMANTE);
		interfazOfertaLaboral.ingresarDatosOfertaLaboral("Diseñador UX-UI", "Trabaja en colaboración con nuestro talentoso equipo de diseño para crear soluciones impactantes.", imgDummy, "14:00:18:00", (float) 65000.0, "Colonia", "Rosario", LocalDate.of(2023, 10, 29), new String[]{K_FULL_TIME, K_REMOTO, K_TEMPORAL}, MELI, TP_DIAMANTE);
		interfazOfertaLaboral.ingresarDatosOfertaLaboral("Analista de Datos", "Ayuda a nuestros clientes a tomar decisiones informadas basadas en análisis y visualizaciones de datos.", imgDummy, "09:00:13:00", (float) 40000.0, "Maldonado", "Maldonado", LocalDate.of(2023, 10, 19), new String[]{K_FULL_TIME}, GLOBANT, TP_DIAMANTE);
		interfazOfertaLaboral.ingresarDatosOfertaLaboral("Desarrollador de Software Senior", "Únete a nuestro equipo y lidera proyectos de desarrollo de software sostenible y ecológico. Impulsa la innovación y contribuye a un futuro más verde.", imgDummy, "09:00:16:00", (float) 123000.0, "Montevideo", "Montevideo", LocalDate.of(2023, 11, 4), new String[]{K_TEMPORAL, K_FULL_TIME}, GLOBANT, TP_ORO);
	
		interfazOfertaLaboral.actualizarOfertaEstado("Estratega de Negocios", EstadoOferta.CONFIRMADO);
		interfazOfertaLaboral.actualizarOfertaEstado("Diseñador UX-UI", EstadoOferta.CONFIRMADO);
		interfazOfertaLaboral.actualizarOfertaEstado("Analista de Datos", EstadoOferta.CONFIRMADO);
		interfazOfertaLaboral.actualizarOfertaEstado("Desarrollador de Software Senior", EstadoOferta.CONFIRMADO);
	}
	
	private void setupKeywords() throws ExisteKeyWord {
		interfazOfertaLaboral.ingresarKeyWord(K_FULL_TIME);
		interfazOfertaLaboral.ingresarKeyWord(K_REMOTO);
		interfazOfertaLaboral.ingresarKeyWord(K_TEMPORAL);
	}
}
