package test;


import org.junit.jupiter.api.AfterAll;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import excepciones.PaqueteNoExisteException;
import excepciones.TipoPubNoExisteException;
import logica.Fabrica;
import logica.datatypes.DtEmpresa;
import logica.datatypes.DtOferta;
import logica.datatypes.DtPostulacion;
import logica.datatypes.DtTipoPub;
import logica.datatypes.DtUsuario;
import logica.datatypes.EstadoOferta;
import logica.datatypes.DtPostulante;
import logica.interfaces.IOfertaLab;
import logica.interfaces.IUsuario;
import logica.manejadores.ManejadorOfertaLaboral;
import logica.manejadores.ManejadorPaqueteTipoPub;
import logica.manejadores.ManejadorTipoPublicacion;
import logica.manejadores.ManejadorUsuarios;

public class SeguidoresTests {
	private static IOfertaLab interfazOfertaLaboral;
	private static IUsuario interfazUsuario;
	
	private static ManejadorOfertaLaboral manejadorOferta;
	private static ManejadorUsuarios manejadorUsuario;
	private static ManejadorTipoPublicacion manejadorTipo;
	private static ManejadorPaqueteTipoPub manejadorPaquete;
    
    private static final String WILLY = "willy";
    private static final String PEDRO = "pedro";

	@BeforeAll
	public static void iniciar() {
		Fabrica fabrica = Fabrica.getInstance(); 
		interfazOfertaLaboral = fabrica.getIOfertaLaboral();
		interfazUsuario = fabrica.getIUsuario();
		
		manejadorOferta = ManejadorOfertaLaboral.getInstance();
		manejadorUsuario = ManejadorUsuarios.getInstance();
		manejadorTipo = ManejadorTipoPublicacion.getInstance();
		manejadorPaquete = ManejadorPaqueteTipoPub.getInstance();
		
		manejadorOferta.eliminarOfertas();
		manejadorOferta.eliminarKeyWords();
		manejadorUsuario.eliminarUsuarios();
		manejadorTipo.eliminarTiposPublicaciones();
		manejadorPaquete.eliminarPaqutes();
		try {
			interfazUsuario.cargarDatos();
			interfazOfertaLaboral.cargarDatos();
			interfazUsuario.cargarDatosCompraPaquetes();
			interfazOfertaLaboral.cargarDatosAltaOfertas();
			interfazUsuario.cargarDatosPostulaciones();
			interfazOfertaLaboral.cargaPersistenciaOfertasFinalizadas();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@AfterAll
	public static void limpiar() {
		manejadorOferta.eliminarOfertas();
		manejadorOferta.eliminarKeyWords();
		manejadorUsuario.eliminarUsuarios();
		manejadorTipo.eliminarTiposPublicaciones();
		manejadorPaquete.eliminarPaqutes();
	}
	
	@Test
	public void testObtenerSeguidores() throws Exception {
	    DtUsuario[] seguidores = interfazUsuario.obtenerSeguidores("lgarcia");
	    assertNotNull(seguidores);
	    assertTrue(seguidores.length > 0);
	}
			
	@Test
	public void testObtenerSeguidos() throws Exception {
	    DtUsuario[] seguidos = interfazUsuario.obtenerSeguidos("lgarcia");
	    assertNotNull(seguidos);
	    assertTrue(seguidos.length > 0);
	}
}
