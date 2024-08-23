package test;

import logica.Fabrica;
import logica.datatypes.DtEmpresa;
import logica.datatypes.DtTipoPub;
import logica.interfaces.IOfertaLab;
import logica.interfaces.IUsuario;
import logica.manejadores.ManejadorOfertaLaboral;
import logica.manejadores.ManejadorTipoPublicacion;
import logica.manejadores.ManejadorUsuarios;
import logica.modelos.OfertaLab;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import excepciones.CostoMayorACeroExcepcion;
import excepciones.FechaInvalidaException;
import excepciones.OfertaLaboralYaExisteException;

public class AltaOfertaLaboralTest {
	private static IOfertaLab interfazOfertaLaboral;
	private static IUsuario interfazUsuario;
	
	private static ManejadorOfertaLaboral manejadorOferta;
	private static ManejadorUsuarios manejadorUsuario;
	private static ManejadorTipoPublicacion manejadorTipo;

    private static final String DIAMANTE = "Diamante";
    private static final String BOOTIA = "bootia";
    private static final String WILLY = "willy";
    private static final String FULL_TIME = "Full-Time";
    private static final String JAVA = "Java";

	@BeforeAll
	public static void setup() {
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
	public void testRegistrarOfertaLaboralExitosa() throws Exception {
		setupEmpresas();
		setupTiposPublicacion();
		setupKeywords();
		
		String nombre = "Analista de Datos";
		String descripcion = "Ayuda a nuestros clientes a tomar decisiones informadas basadas en an´alisis y visualizaciones de datos.";
		String horario = "09:00 - 13:00";
		Float remuneracion = 40000f;
		String ciudad = "Maldonado";
		String departamento = "Maldonado";
		LocalDate fechaAlta = LocalDate.now().minusYears(1);
		
		DtTipoPub tipoPublicacion = manejadorTipo.obtenerDtTipoPublicacion(DIAMANTE);
		DtEmpresa empresa = (DtEmpresa) manejadorUsuario.obtenerDtUsuario(BOOTIA);
	    String[] keywords = new String[]{JAVA, FULL_TIME};

		interfazOfertaLaboral.ingresarDatosOfertaLaboral(nombre, descripcion, null, horario, remuneracion, ciudad, departamento, fechaAlta, keywords
				, empresa.getNickname(), tipoPublicacion.getNombre());
		OfertaLab oferta = manejadorOferta.obtenerOferta(nombre);
		
		assertNotNull(oferta);
		assertEquals(nombre, oferta.getNombre());
		assertEquals(descripcion, oferta.getDescripcion());
		assertEquals(horario, oferta.getHorario());
		assertEquals(remuneracion, oferta.getRemuneracion());
		assertEquals(ciudad, oferta.getCiudad());
		assertEquals(departamento, oferta.getDepartamento());
		assertEquals(fechaAlta, oferta.getFecha());
		assertEquals(empresa.getNickname(), oferta.getEmpresa().getNickname());
		assertEquals(tipoPublicacion.getNombre(), oferta.getTipo().getNombre());
	}
	
	@Test
	public void testOfertaLaboralYaExiste() throws OfertaLaboralYaExisteException, Exception {
		setupEmpresas();
		setupTiposPublicacion();
		setupKeywords();
		
		String nombre = "Analista de Datos";
		String descripcion = "Ayuda a nuestros clientes a tomar decisiones informadas basadas en an´alisis y visualizaciones de datos.";
		String horario = "09:00 - 13:00";
		Float remuneracion = 40000f;
		String ciudad = "Maldonado";
		String departamento = "Maldonado";
		LocalDate fechaAlta = LocalDate.now().minusYears(1);
		
	    String[] keywords = new String[]{JAVA, FULL_TIME};
	    
		interfazOfertaLaboral.ingresarDatosOfertaLaboral(nombre, descripcion, null, horario, remuneracion, ciudad, departamento, fechaAlta, keywords, BOOTIA, DIAMANTE);
		
	    Exception exception = assertThrows(OfertaLaboralYaExisteException.class, () -> {
			interfazOfertaLaboral.ingresarDatosOfertaLaboral(nombre, descripcion, null, horario, remuneracion, ciudad, departamento, fechaAlta, keywords, BOOTIA, DIAMANTE);
	    });

	    String expectedMessage = "El nombre de la Oferta ya se encuentra registrado";
	    String actualMessage = exception.getMessage();
	    
	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	public void testFechaInvalidaOfertaLabora() throws FechaInvalidaException, Exception {
		setupEmpresas();
		setupTiposPublicacion();
		setupKeywords();
		
		String nombre = "Analista de Datos";
		String descripcion = "Ayuda a nuestros clientes a tomar decisiones informadas basadas en an´alisis y visualizaciones de datos.";
		String horario = "09:00 - 13:00";
		Float remuneracion = 40000f;
		String ciudad = "Maldonado";
		String departamento = "Maldonado";
		LocalDate fechaAlta = LocalDate.now().plusDays(1);
		
		DtTipoPub tipoPublicacion = manejadorTipo.obtenerDtTipoPublicacion(DIAMANTE);
		DtEmpresa empresa = (DtEmpresa) manejadorUsuario.obtenerDtUsuario(BOOTIA);
	    String[] keywords = new String[]{JAVA, FULL_TIME};
	    
	    Exception exception = assertThrows(FechaInvalidaException.class, () -> {
	    	interfazOfertaLaboral.ingresarDatosOfertaLaboral(nombre, descripcion, null, horario, remuneracion, ciudad, departamento, fechaAlta, keywords, empresa.getNickname(), tipoPublicacion.getNombre());
	    });
	    String expectedMessage = "La fecha debe tener menos de 80 años y ser menor o igual a la actual";
	    String actualMessage = exception.getMessage();
	    
	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	public void testRemuneracionNegativaOfertaLabora() throws CostoMayorACeroExcepcion, Exception {
		setupEmpresas();
		setupTiposPublicacion();
		setupKeywords();
		
		String nombre = "Experto en compras";
		String descripcion = "Ayuda a nuestros clientes a tomar decisiones informadas basadas en an´alisis y visualizaciones de datos.";
		String horario = "09:00 - 13:00";
		Float remuneracion = -40000f;
		String ciudad = "Maldonado";
		String departamento = "Maldonado";
		LocalDate fechaAlta = LocalDate.now();
		
		DtTipoPub tipoPublicacion = manejadorTipo.obtenerDtTipoPublicacion(DIAMANTE);
		DtEmpresa empresa = (DtEmpresa) manejadorUsuario.obtenerDtUsuario(BOOTIA);
	    String[] keywords = new String[]{JAVA, FULL_TIME};
	    
	    Exception exception = assertThrows(CostoMayorACeroExcepcion.class, () -> {
			interfazOfertaLaboral.ingresarDatosOfertaLaboral(nombre, descripcion, null, horario, remuneracion, ciudad, departamento, fechaAlta, keywords, empresa.getNickname(), tipoPublicacion.getNombre());
	    });
	    
	    String expectedMessage = "La remuneración no puede ser un negativa, ni cero";
	    String actualMessage = exception.getMessage();
	    
	    assertTrue(actualMessage.contains(expectedMessage));
	}

	public void setupEmpresas() throws Exception {
		interfazUsuario.ingresarDatosEmpresa(BOOTIA, "Bootia", "SAS", "xxx", "bootia@bootia.dev", null, "A small software factory", "bootia.dev");
		interfazUsuario.ingresarDatosEmpresa("globant", "Globant", "SAS", "xxx", "globant@globant.com", null, "We make the best products globally", "globant.com");
		interfazUsuario.ingresarDatosEmpresa("mercadolibre", "Mercado Libre", "xxx", "Company", "mercadolibre@mercadolibre.com", null, "We sell goods to good people", "mercadolibre.com");
	}
	
	public void setupPostulantes() throws Exception {
		interfazUsuario.ingresarDatosPostulante(WILLY, "William", "Kahan", "xxx", "wkahan@gmail.com", null, "Canada", LocalDate.now().minusYears(50));
		interfazUsuario.ingresarDatosPostulante("pedro", "Pedro", "Caraman", "xxx", "pcaraman@gmail.com", null, "Uruguay", LocalDate.now().minusYears(50));
	}
	
	public void setupKeywords() throws Exception {
		interfazOfertaLaboral.ingresarKeyWord(FULL_TIME);
		interfazOfertaLaboral.ingresarKeyWord("Part-Time");
		interfazOfertaLaboral.ingresarKeyWord(JAVA);
	}
	
	public void setupTiposPublicacion() throws Exception {
		interfazOfertaLaboral.ingresarDatosTipoPublicacion(DIAMANTE, "El mejor tipo de publicacion", 10, 2, 200f, LocalDate.now().minusDays(1));
		interfazOfertaLaboral.ingresarDatosTipoPublicacion("Oro", "El mejor tipo de publicacion calidad-precio", 10, 2, 200f, LocalDate.now().minusDays(1));
		interfazOfertaLaboral.ingresarDatosTipoPublicacion("Bronce", "El peor tipo de publicacion", 10, 2, 200f, LocalDate.now().minusDays(1));
	}
}
