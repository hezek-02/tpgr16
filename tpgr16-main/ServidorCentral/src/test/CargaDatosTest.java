package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import logica.Fabrica;
import logica.interfaces.IOfertaLab;
import logica.interfaces.IUsuario;
import logica.manejadores.ManejadorOfertaLaboral;
import logica.manejadores.ManejadorPaqueteTipoPub;
import logica.manejadores.ManejadorTipoPublicacion;
import logica.manejadores.ManejadorUsuarios;

public class CargaDatosTest {
	
	private static IOfertaLab interfazOfertaLaboral;
	private static IUsuario interfazUsuario;
	
	private static ManejadorOfertaLaboral manejadorOferta;
	private static ManejadorUsuarios manejadorUsuario;
	private static ManejadorTipoPublicacion manejadorTipo;
	private static ManejadorPaqueteTipoPub manejadorPaquete;
	
	@BeforeAll
	public static void setup() {
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
	}
	
	@AfterAll
	public static void cleanAfter() {
		manejadorOferta.eliminarOfertas();
		manejadorOferta.eliminarKeyWords();
		manejadorUsuario.eliminarUsuarios();
		manejadorTipo.eliminarTiposPublicaciones();
		manejadorPaquete.eliminarPaqutes();
	}
	
	@Test
	public void testCargaDatosControladores() throws Exception {
		interfazUsuario.cargarDatos();
		interfazOfertaLaboral.cargarDatos();
		interfazUsuario.cargarDatosCompraPaquetes();
		interfazOfertaLaboral.cargarDatosAltaOfertas();
		interfazUsuario.cargarDatosPostulaciones();
		interfazOfertaLaboral.cargaPersistenciaOfertasFinalizadas();
	}
	
}
