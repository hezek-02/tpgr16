package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import excepciones.UsuarioNoExisteException;
import excepciones.PaqueteNoExisteException;
import excepciones.TipoPubNoExisteException;
import logica.Fabrica;
import logica.datatypes.DtCompraPaquete;
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

public class FavoritosTests {
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
	public void testObtenerOfertasFavoritas() throws Exception {
	    DtOferta[] favoritos = interfazUsuario.obtenerOfertasFavoritas("lgarcia");
	    interfazUsuario.eliminarOfertaFavorita("lgarcia", "Desarrollador Frontend");
	    assertNotNull (favoritos);
	    assertTrue(favoritos.length > 0);
    }


    @Test
	public void testObtenerPostulantesFavoritos() throws Exception {
    	DtPostulante[] favoritos = interfazOfertaLaboral.obtenerPostulantesFavoritos("Desarrollador Frontend");
    	assertNotNull(favoritos);
    	assertTrue(favoritos.length > 0);
	}

}
