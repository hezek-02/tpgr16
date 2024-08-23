package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import excepciones.OfertaNoExisteException;
import excepciones.PaqueteNoExisteException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import logica.Fabrica;
import logica.datatypes.DtEmpresa;
import logica.datatypes.DtOferta;
import logica.datatypes.DtPaquetePub;
import logica.datatypes.DtPostulacion;
import logica.datatypes.DtTipoPub;
import logica.datatypes.EstadoOferta;
import logica.datatypes.DtPostulante;
import logica.interfaces.IOfertaLab;
import logica.interfaces.IUsuario;
import logica.manejadores.ManejadorOfertaLaboral;
import logica.manejadores.ManejadorTipoPublicacion;
import logica.manejadores.ManejadorUsuarios;
import persistence.EmpresaJPA;
import persistence.OfertaLabJPA;
import persistence.PostulanteJPA;


public class OfertasFinalizadasExtendedTest {
	
	private static IOfertaLab interfazOfertaLaboral;
	private static IUsuario interfazUsuario;
	
	private static ManejadorOfertaLaboral manejadorOferta;
	private static ManejadorUsuarios manejadorUsuario;
	private static ManejadorTipoPublicacion manejadorTipo;
	
    private static final String TP_DIAMANTE = "Diamante";
    private static final String TP_ORO = "Oro";
    private static final String TP_BRONCE = "Bronce";
    
    private static final String SOFTWARE_DEVELOPER = "Software Developer";
    
    private static final String BOOTIA = "bootia";
    private static final String GLOBANT = "globant";
    private static final String MELI = "mercadolibre";
    private static final String FEMPRESA = "empresa-finalizada";
    
    private static final String WILLY = "willy";
    private static final String PEDRO = "pedro";
    
    private static final String K_PART_TIME = "Part-Time";
    private static final String K_FULL_TIME = "Full-Time";
    private static final String K_REMOTO = "Remoto";
    private static final String K_TEMPORAL = "Temporal";
    private static final String K_JAVA = "Java";

	@BeforeAll
	public static void iniciar() {
		Fabrica fabrica = Fabrica.getInstance();
		interfazOfertaLaboral = fabrica.getIOfertaLaboral();
		interfazUsuario = fabrica.getIUsuario();
		
		manejadorOferta = ManejadorOfertaLaboral.getInstance();
		manejadorUsuario = ManejadorUsuarios.getInstance();
		manejadorTipo = ManejadorTipoPublicacion.getInstance();
	}
	
	@BeforeEach
	public void limpiar() {
		manejadorOferta.eliminarOfertas();
		manejadorOferta.eliminarKeyWords();
		manejadorUsuario.eliminarUsuarios();
		manejadorTipo.eliminarTiposPublicaciones();
	}
	
	@Test
	public void testObtenerEmpresas() throws Exception {
		setupEmpresas();
		
		DtEmpresa[] empresas = interfazUsuario.obtenerEmpresas();
		
		assertNotNull(empresas);
        assertTrue(empresas.length > 0);
		
        List<String> nombresEmpresas = Arrays.stream(empresas)
        		.map(DtEmpresa::getNickname)
                .collect(Collectors.toList());
        
		assertTrue(nombresEmpresas.contains(BOOTIA));
		assertTrue(nombresEmpresas.contains(GLOBANT));
		assertTrue(nombresEmpresas.contains(MELI));
	}
	
	@Test
	public void testObtenerOfertasDeEmpresa() throws Exception {
		setupEmpresas();
		setupKeywords();
		setupTiposPublicacion();
		
		DtEmpresa empresa = (DtEmpresa) manejadorUsuario.obtenerDtUsuario(BOOTIA);
		DtTipoPub tipo = manejadorTipo.obtenerDtTipoPublicacion(TP_DIAMANTE);
		
		interfazOfertaLaboral.ingresarDatosOfertaLaboral(
			SOFTWARE_DEVELOPER,                                 
	        "A full-time position for software developers with experience in Java",  
	        null,
	        "9am - 5pm",                                         
	        1000.50f,                                            
	        "Springfield",                                       
	        "IT",                                               
	        LocalDate.now().minusDays(1),                                     
	        new String[]{K_JAVA, K_FULL_TIME},       
	        empresa.getNickname(),                                             
	        tipo.getNombre()                                                 
	    );
		
		DtOferta[] ofertas = interfazOfertaLaboral.obtenerOfertas(empresa.getNickname());
		
		assertNotNull(ofertas);
        assertEquals(1, ofertas.length);
	}
	
	@Test
	public void testObtenerPostulacionesDeOferta() throws Exception {
		setupEmpresas();
		setupPostulantes();
		setupTiposPublicacion();		
		setupKeywords();
		
		DtEmpresa empresa = (DtEmpresa) manejadorUsuario.obtenerDtUsuario(BOOTIA);
		DtPostulante postulante = (DtPostulante) manejadorUsuario.obtenerDtUsuario(WILLY);
		DtTipoPub tipo = manejadorTipo.obtenerDtTipoPublicacion(TP_DIAMANTE);
		
		interfazOfertaLaboral.ingresarDatosOfertaLaboral(
			SOFTWARE_DEVELOPER,                                 
	        "A full-time position for software developers with experience in Java",  
	        null,
	        "9am - 5pm",                                         
	        1000.50f,                                            
	        "Springfield",                                       
	        "IT",                                               
	        LocalDate.now().minusDays(1),                                     
	        new String[]{K_JAVA, K_FULL_TIME},       
	        empresa.getNickname(),                                             
	        tipo.getNombre()                                                 
	    );
		
		DtOferta oferta = manejadorOferta.obtenerDtOferta(SOFTWARE_DEVELOPER);
		
		interfazUsuario.ingresarDatosPostulacion("I'm a Canadian mathematician and computer scientist, who received the Turing Award in 1989", "Quiero aportar valor a la compania como desarrollador", LocalDate.now().minusDays(1), oferta.getNombre(),
				postulante.getNickname(),null);
		
		DtPostulacion[] postulaciones = interfazOfertaLaboral.obtenerPostulaciones(oferta.getNombre());
		
		assertNotNull(postulaciones);
		assertEquals(1, postulaciones.length);
		
	    DtPostulacion postulacion = postulaciones[0];

	    // Chequear cada atributo de postulacion
	    assertEquals("I'm a Canadian mathematician and computer scientist, who received the Turing Award in 1989", postulacion.getCvReducido());
	    assertEquals("Quiero aportar valor a la compania como desarrollador", postulacion.getMotivacion());
	    assertEquals(LocalDate.now().minusDays(1), postulacion.getFechaPostulacion());
	    assertEquals(SOFTWARE_DEVELOPER, postulacion.getNombreOferta());
	    assertEquals(WILLY, postulacion.getNicknamePostulante());
	}
	
	@Test
	public void testListarOfertasDeEmpresa() throws Exception {
		setupEmpresas();
		setupPostulantes();
		setupTiposPublicacion();		
		setupKeywords();
		
		DtEmpresa empresa = (DtEmpresa) manejadorUsuario.obtenerDtUsuario(BOOTIA);
		DtPostulante postulante = (DtPostulante) manejadorUsuario.obtenerDtUsuario(WILLY);
		DtTipoPub tipo = manejadorTipo.obtenerDtTipoPublicacion(TP_DIAMANTE);
		
		interfazOfertaLaboral.ingresarDatosOfertaLaboral(
			SOFTWARE_DEVELOPER,                                 
	        "A full-time position for software developers with experience in Java", 
	        null,
	        "9am - 5pm",                                         
	        1000.50f,                                            
	        "Springfield",                                       
	        "IT",                                               
	        LocalDate.now().minusDays(1),                                     
	        new String[]{K_JAVA, K_FULL_TIME},       
	        empresa.getNickname(),                                             
	        tipo.getNombre()                                                 
	    );
		interfazOfertaLaboral.actualizarOfertaEstado(manejadorOferta.obtenerDtOferta(SOFTWARE_DEVELOPER).getNombre(), EstadoOferta.CONFIRMADO);
		DtOferta[] ofertas = interfazOfertaLaboral.obtenerOfertasConfirmadas(empresa.getNickname());
		
		assertNotNull(ofertas);
		assertEquals(1, ofertas.length);
		
		DtEmpresa globant = (DtEmpresa) manejadorUsuario.obtenerDtUsuario(GLOBANT);
		DtOferta[] ofertasGlobant = interfazOfertaLaboral.obtenerOfertasIngresadas(globant.getNickname());
		assertEquals(0, ofertasGlobant.length);
	}
	
	@Test
	public void testOfertaEstaVencida() throws Exception {
		setupEmpresas();
		setupPostulantes();
		setupTiposPublicacion();		
		setupKeywords();
		setupOfertas();
		
		boolean estaVencida = interfazOfertaLaboral.vencida("Desarrollador de Software Senior");
		boolean noEstaVencida = interfazOfertaLaboral.vencida("Analista de Datos");
		boolean noEstaVencidaPuesFinalizada = interfazOfertaLaboral.vencida("Oferta Finalizada");
		
		assertTrue(estaVencida);
		assertFalse(noEstaVencida);
		assertFalse(noEstaVencidaPuesFinalizada);
	}
	
	@Test 
	public void testObtenerOfertaFinalizada() throws Exception {
		setupEmpresas();
		setupPostulantes();
		setupTiposPublicacion();		
		setupKeywords();
		setupOfertas();
		
		DtOferta ofertaFinalizada = interfazOfertaLaboral.obtenerDtOferta("Oferta Finalizada");
		
		assertNotNull(ofertaFinalizada);
	}
	
	@Test 
	public void testObtenerOfertasFinalizadas() throws Exception {
		setupEmpresas();
		setupPostulantes();
		setupTiposPublicacion();		
		setupKeywords();
		setupOfertas();
		
		DtOferta[] ofertasFinalizadas = interfazOfertaLaboral.obtenerOfertasFinalizadas();
		
		assertNotNull(ofertasFinalizadas);
	}
	
	@Test 
	public void testObtenerOfertasFinalizadasDeEmpresa() throws Exception {
		setupEmpresas();
		setupPostulantes();
		setupTiposPublicacion();		
		setupKeywords();
		setupOfertas();
		
		DtOferta[] ofertasFinalizadas = interfazOfertaLaboral.obtenerOfertasFinalizadas(MELI);
		
		assertNotNull(ofertasFinalizadas);
	}
	
	
	// EL SIGUIENTE TEST DEBE ESTAR EN OTRO ARCHIVO:
	
	@Test 
	public void testFinalizarOfertaPersistencia() throws Exception {
	    // Setup methods
	    setupEmpresas();
	    setupPostulantes();
	    setupTiposPublicacion();       
	    setupKeywords();
	    setupOfertas();
	    setupPostulaciones();
	    
	    EntityManagerFactory entManagerFactory = null;
	    EntityManager entityManager = null;
	    
	    
	    entManagerFactory = Persistence.createEntityManagerFactory("Servidor");
	    entityManager = entManagerFactory.createEntityManager();
	    
	    entityManager.getTransaction().begin();

	    // Fetch the IDs first
	    TypedQuery<OfertaLabJPA> ofertaQuery = entityManager.createQuery(
	        "SELECT o FROM OfertaLabJPA o WHERE o.nombre = :nombre", OfertaLabJPA.class);
	    ofertaQuery.setParameter("nombre", "Oferta A Finalizar");
	    OfertaLabJPA oferta = null;
        try {
        	oferta = ofertaQuery.getSingleResult();
	    } catch (NoResultException e) {
	    	assertEquals(oferta, null);
	    }   
	    
	    TypedQuery<PostulanteJPA> postulanteQuery = entityManager.createQuery(
	        "SELECT p FROM UsuarioJPA p WHERE p.nickname = :nickname", PostulanteJPA.class);
	    postulanteQuery.setParameter("nickname", WILLY);
	    PostulanteJPA postulante = null; 
        try {
        	postulante = postulanteQuery.getSingleResult();
	    } catch (NoResultException e) {
	    	assertEquals(postulante, null);
	    }   
	    // Delete PostulacionJPA records
	    Query deletePostulacionQuery = entityManager.createQuery(
	        "DELETE FROM PostulacionJPA o WHERE o.oferta = :oferta AND o.postulante = :postulante");
	    deletePostulacionQuery.setParameter("oferta", oferta);
	    deletePostulacionQuery.setParameter("postulante", postulante);
	    int rowsDeletedPostulacion = deletePostulacionQuery.executeUpdate();

	    // Delete OfertaLabJPA records
	    Query deleteOfertaQuery = entityManager.createQuery(
	        "DELETE FROM OfertaLabJPA o WHERE o.nombre = :nombre");
	    deleteOfertaQuery.setParameter("nombre", "Oferta A Finalizar");
	    int rowsDeletedOferta = deleteOfertaQuery.executeUpdate();

	    // Delete PostulanteJPA records
	    Query deletePostulanteQuery = entityManager.createQuery(
	        "DELETE FROM PostulanteJPA o WHERE o.nickname = :nickname");
	    deletePostulanteQuery.setParameter("nickname", WILLY);
	    int rowsDeletedPostulante = deletePostulanteQuery.executeUpdate();
	    
	    // Delete EmpresaJPA records
	    Query deleteEmpresaQuery = entityManager.createQuery(
	        "DELETE FROM EmpresaJPA e WHERE e.nickname = :nickname");
	    deleteEmpresaQuery.setParameter("nickname", FEMPRESA);
	    int rowsDeletedEmpresa = deleteEmpresaQuery.executeUpdate();

	    entityManager.getTransaction().commit();
	    
	    interfazOfertaLaboral.actualizarOfertaEstado("Oferta A Finalizar", EstadoOferta.FINALIZADA);
	}
	
	
	@Test
	public void testObtenerEmpresaFinalizada() throws Exception {
	    // Setup methods
	    setupEmpresas();
	    setupPostulantes();
	    setupTiposPublicacion();       
	    setupKeywords();
	    setupOfertas();
	    setupPostulaciones();
		DtEmpresa empresa = interfazOfertaLaboral.obtenerEmpresa("Oferta Finalizada");
		assertEquals(empresa.getNickname(), GLOBANT);
		
		DtPaquetePub paquete = null;
		try {
			paquete = interfazOfertaLaboral.obtenerPaqueteCompra("Oferta Finalizada");
			fail("debe ser nulo");
		} catch (PaqueteNoExisteException e) {
			assertEquals(paquete, null);
		}
	}

	
	
	
	public void setupEmpresas() throws Exception {
		interfazUsuario.ingresarDatosEmpresa(BOOTIA, "Bootia", "SAS", "xxx", "bootia@bootia.dev", null, "A small software factory", "bootia.dev");
		interfazUsuario.ingresarDatosEmpresa(GLOBANT, "Globant", "SAS", "xxx", "globant@globant.com", null, "We make the best products globally", "globant.com");
		interfazUsuario.ingresarDatosEmpresa(MELI, "Mercado Libre", "xxx", "Company", "mercadolibre@mercadolibre.com", null, "We sell goods to good people", "mercadolibre.com");
		interfazUsuario.ingresarDatosEmpresa(FEMPRESA, "Empresa Finalizada", "xxx", "Company", "finalizada@finalizada.com", null, "We sell goods to good people", "finalizada.com");

		
	}
	
	public void setupPostulantes() throws Exception {
		interfazUsuario.ingresarDatosPostulante(WILLY, "William", "Kahan", "xxx", "wkahan@gmail.com", null, "Canada", LocalDate.now().minusYears(50));
		interfazUsuario.ingresarDatosPostulante(PEDRO, "Pedro", "Caraman", "xxx", "pcaraman@gmail.com", null, "Uruguay", LocalDate.now().minusYears(50));
	}
	
	public void setupKeywords() throws Exception {
		interfazOfertaLaboral.ingresarKeyWord(K_FULL_TIME);
		interfazOfertaLaboral.ingresarKeyWord(K_PART_TIME);
		interfazOfertaLaboral.ingresarKeyWord(K_REMOTO);
		interfazOfertaLaboral.ingresarKeyWord(K_TEMPORAL);
		interfazOfertaLaboral.ingresarKeyWord(K_JAVA);
	
	}
	
	public void setupTiposPublicacion() throws Exception {
		interfazOfertaLaboral.ingresarDatosTipoPublicacion(TP_DIAMANTE, "El mejor tipo de publicacion", 10, 2, 200f, LocalDate.now().minusDays(1));
		interfazOfertaLaboral.ingresarDatosTipoPublicacion(TP_ORO, "El mejor tipo de publicacion calidad-precio", 10, 2, 200f, LocalDate.now().minusDays(1));
		interfazOfertaLaboral.ingresarDatosTipoPublicacion(TP_BRONCE, "El peor tipo de publicacion", 10, 2, 200f, LocalDate.now().minusDays(1));
	}
	
	private void setupOfertas() throws Exception {
		byte[] imgDummy = new byte[0];
		interfazOfertaLaboral.ingresarDatosOfertaLaboral("Estratega de Negocios", "Forma parte de nuestro equipo de estrategia y contribuye al crecimiento de las empresas clientes.", imgDummy, "08:00:17:00", (float) 80000.0, "Maldonado", "Punta del Este", LocalDate.now(), new String[] {K_TEMPORAL}, BOOTIA, TP_DIAMANTE);
		interfazOfertaLaboral.ingresarDatosOfertaLaboral("Diseñador UX-UI", "Trabaja en colaboración con nuestro talentoso equipo de diseño para crear soluciones impactantes.", imgDummy, "14:00:18:00", (float) 65000.0, "Colonia", "Rosario", LocalDate.now(), new String[]{K_FULL_TIME, K_REMOTO, K_TEMPORAL}, MELI, TP_DIAMANTE);
		interfazOfertaLaboral.ingresarDatosOfertaLaboral("Analista de Datos", "Ayuda a nuestros clientes a tomar decisiones informadas basadas en análisis y visualizaciones de datos.", imgDummy, "09:00:13:00", (float) 40000.0, "Maldonado", "Maldonado", LocalDate.now(), new String[]{K_FULL_TIME}, GLOBANT, TP_DIAMANTE);
		interfazOfertaLaboral.ingresarDatosOfertaLaboral("Desarrollador de Software Senior", "Únete a nuestro equipo y lidera proyectos de desarrollo de software sostenible y ecológico. Impulsa la innovación y contribuye a un futuro más verde.", imgDummy, "09:00:16:00", (float) 123000.0, "Montevideo", "Montevideo", LocalDate.now().minusYears(2), new String[]{K_TEMPORAL, K_FULL_TIME}, GLOBANT, TP_ORO);
		interfazOfertaLaboral.ingresarDatosOfertaLaboral("Oferta Finalizada", "Únete a nuestro equipo y lidera proyectos de desarrollo de software sostenible y ecológico. Impulsa la innovación y contribuye a un futuro más verde.", imgDummy, "09:00:16:00", (float) 123000.0, "Montevideo", "Montevideo", LocalDate.now(), new String[]{K_TEMPORAL, K_FULL_TIME}, GLOBANT, TP_ORO);
		interfazOfertaLaboral.ingresarDatosOfertaLaboral("Oferta Finalizada 2", "Únete a nuestro equipo y lidera proyectos de desarrollo de software sostenible y ecológico. Impulsa la innovación y contribuye a un futuro más verde.", imgDummy, "09:00:16:00", (float) 123000.0, "Montevideo", "Montevideo", LocalDate.now(), new String[]{K_TEMPORAL, K_FULL_TIME}, MELI, TP_ORO);
		interfazOfertaLaboral.ingresarDatosOfertaLaboral("Oferta A Finalizar", "Únete a nuestro equipo y lidera proyectos de desarrollo de software sostenible y ecológico. Impulsa la innovación y contribuye a un futuro más verde.", imgDummy, "09:00:16:00", (float) 123000.0, "Montevideo", "Montevideo", LocalDate.now(), new String[]{K_TEMPORAL, K_FULL_TIME}, FEMPRESA, TP_ORO);
		
		interfazOfertaLaboral.actualizarOfertaEstado("Estratega de Negocios", EstadoOferta.CONFIRMADO);
		interfazOfertaLaboral.actualizarOfertaEstado("Diseñador UX-UI", EstadoOferta.CONFIRMADO);
		interfazOfertaLaboral.actualizarOfertaEstado("Analista de Datos", EstadoOferta.CONFIRMADO);
		interfazOfertaLaboral.actualizarOfertaEstado("Desarrollador de Software Senior", EstadoOferta.CONFIRMADO);
		interfazOfertaLaboral.actualizarOfertaEstado("Oferta Finalizada", EstadoOferta.FINALIZADA);
		interfazOfertaLaboral.actualizarOfertaEstado("Oferta Finalizada 2", EstadoOferta.FINALIZADA);
	}
	
	private void setupPostulaciones() throws Exception {
		interfazUsuario.ingresarDatosPostulacion("Este es el curriculum de un postulante", "Motivacion", LocalDate.now(), "Oferta A Finalizar", WILLY, "");
	}
}
