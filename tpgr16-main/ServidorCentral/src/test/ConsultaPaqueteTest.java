package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import excepciones.PaqueteNoExisteException;
import excepciones.UsuarioNoExisteException;
import logica.Fabrica;
import logica.datatypes.DtPaquetePub;
import logica.interfaces.IOfertaLab;
import logica.interfaces.IUsuario;
import logica.manejadores.ManejadorOfertaLaboral;
import logica.manejadores.ManejadorPaqueteTipoPub;
import logica.manejadores.ManejadorTipoPublicacion;
import logica.manejadores.ManejadorUsuarios;

public class ConsultaPaqueteTest {
	private static IOfertaLab interfazOfertaLaboral;
	private static IUsuario interfazUsuario;
	
	private static ManejadorOfertaLaboral manejadorOferta;
	private static ManejadorUsuarios manejadorUsuario;
	private static ManejadorTipoPublicacion manejadorTipo;
	private static ManejadorPaqueteTipoPub manejadorPaquete;
	
	private static final String EMPRESA_SIN_PAQUETE = "EmpresaSinPaquete";
    private static final String BOOTIA = "bootia";
    private static final String GLOBANT = "globant";
    private static final String MELI = "Mercadolibre";

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
	public void testObtenerPaquetes() throws Exception {
	    setupPaquetes();
	    
	    DtPaquetePub[] paquetes = interfazOfertaLaboral.obtenerPaquetes();
	    
	    assertNotNull(paquetes);
	    assertTrue(paquetes.length > 0);

	    manejadorPaquete.eliminarPaqutes();
	    
	    Exception exception = assertThrows(PaqueteNoExisteException.class, () -> {
	        interfazOfertaLaboral.obtenerPaquetes();
	    });

	    String expectedMessage = "No hay paquetes registrados";
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	
	@Test
	public void testObtenerPaquetesValidos() throws Exception {
	    setupEmpresas();
	    setupPaquetes();
	    setupCompraPaquetes();

	    DtPaquetePub[] paquetesValidos = interfazOfertaLaboral.obtenerPaquetesValidos(BOOTIA);

	    assertNotNull(paquetesValidos);
	    assertTrue(paquetesValidos.length > 0);
	}
	
	public void setupPaquetes() throws Exception {
	    byte[] imgDummy = new byte[0];
	    LocalDate fechaAlta = LocalDate.now();
	    int periodoValidez = 30; 
	    float descuento = 10.0f; 

	    interfazOfertaLaboral.ingresarDatosPaquete(PAQUETE_PREMIUM, "Descripcion Paquete Premium", imgDummy, fechaAlta, periodoValidez, descuento);
	    interfazOfertaLaboral.ingresarDatosPaquete(PAQUETE_DIAMANTE, "Descripcion Paquete Diamante", imgDummy, fechaAlta, periodoValidez, descuento);
	    interfazOfertaLaboral.ingresarDatosPaquete(PAQUETE_BASICO, "Descripcion Paquete Basic", imgDummy, fechaAlta, periodoValidez, descuento);
	}
	
	public void setupEmpresas() throws Exception {
		interfazUsuario.ingresarDatosEmpresa(BOOTIA, "Bootia", "SAS", "xxx", "bootia@bootia.dev", null, "A small software factory", "bootia.dev");
		interfazUsuario.ingresarDatosEmpresa(GLOBANT, "Globant", "SAS", "xxx", "globant@globant.com", null, "We make the best products globally", "globant.com");
		interfazUsuario.ingresarDatosEmpresa(MELI, "Mercado Libre", "xxx", "Company", "mercadolibre@mercadolibre.com", null, "We sell goods to good people", "mercadolibre.com");
		interfazUsuario.ingresarDatosEmpresa(EMPRESA_SIN_PAQUETE, "Empresa sin paquete", "xxx", "Company", "sinpaquete@paquete.com", null, "We sell goods to good people", "sinpaquete.com");

	}
	
	public void setupCompraPaquetes() throws Exception {
		LocalDate ahora = LocalDate.now();
		interfazUsuario.comprarPaquete(BOOTIA, PAQUETE_BASICO , ahora);
		interfazUsuario.comprarPaquete(GLOBANT, PAQUETE_BASICO, ahora);
		interfazUsuario.comprarPaquete(MELI, PAQUETE_BASICO, ahora);
	}
}
