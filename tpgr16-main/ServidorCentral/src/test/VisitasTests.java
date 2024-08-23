package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import logica.Fabrica;
import logica.datatypes.DtOferta;
import logica.datatypes.DtPostulante;
import logica.interfaces.IOfertaLab;
import logica.interfaces.IUsuario;
import logica.manejadores.ManejadorOfertaLaboral;
import logica.manejadores.ManejadorPaqueteTipoPub;
import logica.manejadores.ManejadorTipoPublicacion;
import logica.manejadores.ManejadorUsuarios;
import logica.modelos.OfertaLab;

public class VisitasTests {
	private static IOfertaLab interfazOfertaLaboral;
	private static IUsuario interfazUsuario;
	
	private static ManejadorOfertaLaboral manejadorOferta;
	private static ManejadorUsuarios manejadorUsuario;
	private static ManejadorTipoPublicacion manejadorTipo;
	private static ManejadorPaqueteTipoPub manejadorPaquete;

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
	public void testVisitar() throws Exception {
    	OfertaLab ofer = ManejadorOfertaLaboral.getInstance().obtenerOferta("Desarrollador Frontend");
    	ofer.visitar();
    	assertEquals(6, ofer.getVisitas());
    }
    
}
