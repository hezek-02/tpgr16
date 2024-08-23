package test;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import excepciones.FechaInvalidaException;
import excepciones.PostulacionYaExisteException;
import excepciones.UsuarioNoExisteException;
import logica.Fabrica;
import logica.datatypes.DtOferta;
import logica.datatypes.DtUsuario;
import logica.interfaces.IOfertaLab;
import logica.interfaces.IUsuario;
import logica.manejadores.ManejadorOfertaLaboral;
import logica.manejadores.ManejadorPaqueteTipoPub;
import logica.manejadores.ManejadorTipoPublicacion;
import logica.manejadores.ManejadorUsuarios;


public class PostulacionAOfertaTest {
	private static IUsuario controladorUsuario;
	private static IOfertaLab controladorOferta;
	private static  ManejadorUsuarios manejadorU;
	private static ManejadorOfertaLaboral manejadorOf;
	private static ManejadorTipoPublicacion manejadorTP;
	private static ManejadorPaqueteTipoPub manejadorP;
	
	
	@BeforeAll
	public static void iniciar() throws Exception {
		Fabrica fabrica = Fabrica.getInstance();
		controladorUsuario = fabrica.getIUsuario();
		controladorOferta = fabrica.getIOfertaLaboral();
		manejadorU = ManejadorUsuarios.getInstance();
		manejadorOf = ManejadorOfertaLaboral.getInstance();	
		manejadorTP = ManejadorTipoPublicacion.getInstance();
		manejadorP = ManejadorPaqueteTipoPub.getInstance();
		
		manejadorOf.eliminarOfertas();
		manejadorOf.eliminarKeyWords();
		manejadorU.eliminarUsuarios();
		manejadorTP.eliminarTiposPublicaciones();
		manejadorP.eliminarPaqutes();
		
		controladorUsuario.cargarDatos();
		controladorOferta.cargarDatos();
		controladorUsuario.cargarDatosCompraPaquetes();
		controladorOferta.cargarDatosAltaOfertas();
		controladorUsuario.cargarDatosPostulaciones();
		
	}
	
	
	@ParameterizedTest
	@CsvSource({
		"I hava a bachelor's degree Pls Give Job, Money, 2023-08-26, EcoTech, lgarcia, Desarrollador Frontend,Ya existe esta postulacion",
		"Honestly not much because I just graduated, I need to pay rent, 3049-01-12, FusionTech, sebgon, Desarrollador Frontend,La fecha debe tener menos de 80 años y ser menor o igual a la actual",
		"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s when an unknown printer took a "
		+ "galley of type and scrambled it to make a type specimen book. It has survived not only five centuries but also the leap into electronic typesetting remaining essentially unchanged. "
		+ "It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages and more recently with desktop publishing software like Aldus PageMaker including "
		+ "versions of Lorem Ipsum, Plata o plomo, 2000-01-01, GlobalHealth, lgarcia, Estratega de Negocios, Ya existe esta postulacion"
	
	})
	void testPostulacionAOfertaException(String curriculum, String motivacion, String fechaPost, String empr, String nombrePostulante, String nombreOferta, String expectException)  {
		try { 
			LocalDate fechaPostulacion = LocalDate.parse(fechaPost);
			DtUsuario postulante = manejadorU.obtenerUsuario(nombrePostulante).obtenerDtUsu();
			DtOferta oferta = manejadorOf.obtenerOferta(nombreOferta).obtenerDtOferta();
			controladorUsuario.ingresarDatosPostulacion(curriculum, motivacion, fechaPostulacion, oferta.getNombre(), postulante.getNickname(),"");
			
			assertEquals(null, expectException);
		} catch (PostulacionYaExisteException | FechaInvalidaException | UsuarioNoExisteException e) {
			assertEquals(expectException, e.getMessage());
		}
		
	}
	
	
	@ParameterizedTest
	@CsvSource({
		"Tengo un iq mayor al promedio, Un palo verde, 1999-05-12, EcoTech, maro, Estratega de Negocios",
		
	})
	void testPostulacionAOfertaOK(String curriculum, String motivacion, String fechaPost, String nombreEmpresa, String nombrePostulante, String nombreOferta) throws Exception {
		LocalDate fechaPostulacion = LocalDate.parse(fechaPost);	
		DtUsuario postulante = manejadorU.obtenerUsuario(nombrePostulante).obtenerDtUsu();
		DtOferta oferta = manejadorOf.obtenerOferta(nombreOferta).obtenerDtOferta();
		controladorUsuario.ingresarDatosPostulacion(curriculum, motivacion, fechaPostulacion,  oferta.getNombre(), postulante.getNickname(),"");	
	}


	@ParameterizedTest
	@CsvSource({
		"lgarcia, Desarrollador Frontend",
		"lgarcia, Estratega de Negocios",
		"matilo, Estratega de Negocios",
		"maro, Desarrollador Frontend",
		"javierf, Diseñador UX-UI",
		"valen25, Estratega de Negocios"
	})
	void cargaPostulacionesOK(String nicknameUser, String nombreOferta) {
		String expectException = "Ya existe esta postulacion";
		DtUsuario postulante = null;
		DtOferta oferta = null;
		try {
			
			String fecha = "2023-08-26";
			LocalDate fechaPostulacion = LocalDate.parse(fecha);
			postulante = manejadorU.obtenerUsuario(nicknameUser).obtenerDtUsu();
			oferta = manejadorOf.obtenerOferta(nombreOferta).obtenerDtOferta();
			assertEquals(nicknameUser, postulante.getNickname());
			assertEquals(nombreOferta, oferta.getNombre());
			controladorUsuario.ingresarDatosPostulacion("dasd", "dasdas", fechaPostulacion, oferta.getNombre(), postulante.getNickname(),"");
			boolean existe = controladorUsuario.existePostulacion(oferta.getNombre(), postulante.getNickname());
			assertEquals(existe, false);
			
		}catch (PostulacionYaExisteException | FechaInvalidaException | UsuarioNoExisteException e) {
			assertEquals(expectException, e.getMessage());
		}
		
		boolean existe=false;
		try {
			existe = controladorUsuario.existePostulacion(oferta.getNombre(),  postulante.getNickname());

		} catch (UsuarioNoExisteException e) {
			assertEquals(existe, true);
		}
		
	}
} 
