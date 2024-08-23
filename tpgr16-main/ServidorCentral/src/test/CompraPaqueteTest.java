package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import excepciones.CostoMayorACeroExcepcion;
import excepciones.DescuentoMayorACeroExcepcion;
import excepciones.ExisteKeyWord;
import excepciones.FechaInvalidaException;
import excepciones.OfertaLaboralYaExisteException;
import excepciones.OfertaNoExisteException;
import excepciones.PaqueteNoExisteException;
import excepciones.PaqueteNoTieneMasTipoPubExcepcion;
import excepciones.PaqueteRegistrado;
import excepciones.PaqueteYaCompradoException;
import excepciones.TipoPubNoExisteException;
import excepciones.TipoPubRegistradoEnPaqueteException;
import excepciones.TipoPubliRegistradoException;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioRegistradoException;
import logica.Fabrica;
import logica.datatypes.DtEmpresa;
import logica.datatypes.DtOferta;
import logica.datatypes.DtPaquetePub;
import logica.datatypes.DtTipoPub;
import logica.datatypes.EstadoOferta;
import logica.interfaces.IOfertaLab;
import logica.interfaces.IUsuario;
import logica.manejadores.ManejadorOfertaLaboral;
import logica.manejadores.ManejadorPaqueteTipoPub;
import logica.manejadores.ManejadorTipoPublicacion;
import logica.manejadores.ManejadorUsuarios;


class CompraPaqueteTest {
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
	}
	
	@Test
	public void pruebaCompra() {
		LocalDate ahora = LocalDate.now();
		DtEmpresa emp = null;
		DtPaquetePub paq = null;
		try {
			//ingreso tipoPub y paquete
			controladorOferta.ingresarDatosTipoPublicacion("super GRANDE", "SID SADESAE", 231, 3, (float) 1000, ahora);
			controladorOferta.ingresarDatosPaquete("PAQUETE GRANDE", "" , null, ahora, 23, 50);
			
			paq = controladorOferta.obtenerPaquete("PAQUETE GRANDE");
			DtTipoPub tPub = controladorOferta.obtenerTipoPublicacion("super GRANDE");
			
			//ingreso empresa y agrego tipoPub a paq
			controladorUsuario.ingresarDatosEmpresa("empresa", "dasd", "dasd", "xxx", "a@æ", null, "das", "");
			controladorOferta.agregarTipoPubAPaquete(3, tPub.getNombre(), paq.getNombre());
			assertEquals(controladorOferta.obtenerCantPaqPub(paq.getNombre(), tPub.getNombre()), 3);

			emp =(DtEmpresa) controladorUsuario.obtenerUsuario("empresa");
			
			
			controladorUsuario.comprarPaquete(emp.getNickname(), paq.getNombre(), ahora);
			DtPaquetePub[] paqValido = controladorUsuario.obtenerPaquetesCompradosValidos(emp.getNickname(), "super GRANDE");
			assertEquals(paq.getNombre(), paqValido[0].getNombre());
			controladorOferta.ingresarKeyWord("lalo");
			controladorOferta.ingresarDatosOfertaLaboral("oferta generica", "", null, "", (float) 100, "", "", ahora, new String[]{"lalo"}, emp.getNickname(), tPub.getNombre(),
					paq.getNombre());
			
			paqValido = controladorUsuario.obtenerPaquetesCompradosValidos(emp.getNickname(), "super GRANDE"); 
			
			assertEquals(controladorOferta.obtenerCantPaqPub(paq.getNombre(), tPub.getNombre()), 3);
			assertEquals(controladorUsuario.obtenerCantPaqPubEmpresa(paqValido[0].getNombre(), tPub.getNombre(), emp.getNickname()), 2);
			

			DtOferta oferta = null;
			try {
				oferta = controladorOferta.obtenerDtOferta("oferta generica");
			} catch (OfertaNoExisteException e) {
				e.printStackTrace();
			}

			DtOferta[] ofertaI = controladorOferta.obtenerOfertasIngresadas(emp.getNickname());
			DtOferta[] ofertaR = controladorOferta.obtenerOfertasRechazadas(emp.getNickname());
			DtOferta[] ofertaC = controladorOferta.obtenerOfertasConfirmadas(emp.getNickname());
			//test ingresada
			assertEquals(oferta.getNombre(), ofertaI[0].getNombre());
			assertNotEquals(null, ofertaC);
			assertNotEquals(null, ofertaR);
			
			//test rechazada
			controladorOferta.actualizarOfertaEstado(oferta.getNombre(), EstadoOferta.RECHAZADO);
			ofertaI = controladorOferta.obtenerOfertasIngresadas(emp.getNickname());
			ofertaR = controladorOferta.obtenerOfertasRechazadas(emp.getNickname());
			ofertaC = controladorOferta.obtenerOfertasConfirmadas(emp.getNickname());
			
			assertEquals(oferta.getNombre(), ofertaR[0].getNombre());
			assertNotEquals(null, ofertaI);
			assertNotEquals(null, ofertaC);

			//test confirmada
			controladorOferta.actualizarOfertaEstado(oferta.getNombre(), EstadoOferta.CONFIRMADO);
			ofertaI = controladorOferta.obtenerOfertasIngresadas(emp.getNickname());
			ofertaR = controladorOferta.obtenerOfertasRechazadas(emp.getNickname());
			ofertaC = controladorOferta.obtenerOfertasConfirmadas(emp.getNickname());
			
			assertEquals(oferta.getNombre(), ofertaC[0].getNombre());
			assertNotEquals(null, ofertaR);
			assertNotEquals(null, ofertaI);
			
			DtOferta[] ofertaC2 = controladorOferta.obtenerOfertasConfirmadas(emp.getNickname(), new String[]{"lalo"});
			assertEquals(ofertaC2[0].getNombre(), ofertaC[0].getNombre());

			try {
				DtPaquetePub[] paqsSinComprar = controladorOferta.obtenerPaquetesNoComprados();
				assertEquals(null, paqsSinComprar);
			} catch (PaqueteNoExisteException e) {
				assertEquals("No hay paquetes sin comprar registrados", e.getMessage());
			}
			
			controladorOferta.ingresarDatosPaquete("PAQUETE GRANDE SUPER", "" , null, ahora, 23, 50);
			
			DtPaquetePub paqueteNuevo = controladorOferta.obtenerPaquete("PAQUETE GRANDE SUPER");
			DtPaquetePub[] paqSinComprar = controladorOferta.obtenerPaquetesNoComprados();
			assertEquals(paqSinComprar[0].getNombre(), paqueteNuevo.getNombre());
			controladorOferta.ingresarDatosOfertaLaboral("oferta generica2", "", null, "", (float) 100, "", "", ahora, new String[]{"lalo"}, emp.getNickname(),
					tPub.getNombre(), paq.getNombre());
			controladorOferta.ingresarDatosOfertaLaboral("oferta generica3", "", null, "", (float) 100, "", "", ahora, new String[]{"lalo"}, emp.getNickname(),
					tPub.getNombre(), paq.getNombre());
			controladorOferta.ingresarDatosOfertaLaboral("oferta generica4", "", null, "", (float) 100, "", "", ahora, new String[]{"lalo"}, emp.getNickname(),
					tPub.getNombre(), paq.getNombre());
			controladorOferta.ingresarDatosOfertaLaboral("oferta generica5", "", null, "", (float) 100, "", "", ahora, new String[]{"lalo"}, emp.getNickname(),
					tPub.getNombre(), paq.getNombre());


		} catch (TipoPubliRegistradoException | FechaInvalidaException | 
				PaqueteRegistrado | DescuentoMayorACeroExcepcion | CostoMayorACeroExcepcion | 
				TipoPubRegistradoEnPaqueteException | PaqueteNoExisteException | TipoPubNoExisteException | 
				UsuarioRegistradoException | UsuarioNoExisteException | OfertaLaboralYaExisteException | ExisteKeyWord | PaqueteNoTieneMasTipoPubExcepcion | PaqueteYaCompradoException e) {
			assertEquals("El paquete ya no tiene el tipo de publicación disponible", e.getMessage());
		}
		try {
			controladorUsuario.comprarPaquete(emp.getNickname(), paq.getNombre(), ahora);
		} catch (UsuarioNoExisteException | PaqueteYaCompradoException e) {
			assertEquals("El paquete ya ha sido comprado, no se puedo reiterar la compra", e.getMessage());
		}


	}
	
}
