package test;



import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import excepciones.FechaInvalidaException;
import excepciones.PaqueteNoExisteException;
import excepciones.PaqueteRegistrado;
import excepciones.CostoMayorACeroExcepcion;
import excepciones.DescuentoMayorACeroExcepcion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;

import logica.Fabrica;
import logica.datatypes.DtPaquetePub;
import logica.interfaces.IOfertaLab;
import logica.manejadores.ManejadorOfertaLaboral;
import logica.manejadores.ManejadorPaqueteTipoPub;
import logica.manejadores.ManejadorTipoPublicacion;
import logica.manejadores.ManejadorUsuarios;

public class CrearPaqueteTest {
		
		private static IOfertaLab controladorOfertaLab;
		
		@BeforeAll
		public static void iniciar() {
			Fabrica fabrica = Fabrica.getInstance();
			controladorOfertaLab = fabrica.getIOfertaLaboral();
			ManejadorOfertaLaboral.getInstance().eliminarOfertas();
			ManejadorOfertaLaboral.getInstance().eliminarKeyWords();
			ManejadorUsuarios.getInstance().eliminarUsuarios();
			ManejadorTipoPublicacion.getInstance().eliminarTiposPublicaciones();
			ManejadorPaqueteTipoPub.getInstance().eliminarPaqutes();
		}
		
		@SuppressWarnings("unused")
		@Test
		void testRegistrarPaqueteOK() {
			
			// Arreglos de datos de prueba Paquete
			String[] nombresPaquete = {"Básico", "Destacado", "Premium", "Express"};
			String[] descripcion = {"Publica ofertas laborales en nuestra plataforma por un período de 30 días", "Publica ofertas laborales destacadas que se mostrará en la parte superior de los resultados de búsqueda por 45 días" , "Publica ofertas laborales premium que incluye promoción en nuestras redes sociales y listado en la sección destacada por 60 días", "Publica ofertas laborales urgentes resaltada en color y se mostrará en la sección de urgente por 15 días."};
			int[] periodo = {30, 45, 60, 15};
			float[] descuento = {20, 10, 15, 5};
			LocalDate[] fechaPaquete = { 
				LocalDate.of(2023, 8, 16),
				LocalDate.of(2023, 8, 15),
				LocalDate.of(2023, 8, 14),
				LocalDate.of(2023, 8, 13)
			};
				
			for (int i = 0; i < nombresPaquete.length; i++) {
				try {
			        // Registrar un paquete
			        controladorOfertaLab.ingresarDatosPaquete(nombresPaquete[i], descripcion[i] , null, fechaPaquete[i], periodo[i], descuento[i]);
			        ManejadorPaqueteTipoPub manejadorPaquete = ManejadorPaqueteTipoPub.getInstance();
			        DtPaquetePub paquete = manejadorPaquete.obtenerDtPaquete(nombresPaquete[i]);
			      
			        assertEquals(paquete.getNombre(), nombresPaquete[i]);
			        assertEquals(paquete.getDescripcion(), descripcion[i]);
			        assertEquals(paquete.getPeriodoValidez(), periodo[i]);
			        assertEquals(paquete.getDescuento(), descuento[i]);
			        assertEquals(paquete.getFechaAlta(), fechaPaquete[i]);
			        
			} catch (PaqueteRegistrado | PaqueteNoExisteException | DescuentoMayorACeroExcepcion | CostoMayorACeroExcepcion |FechaInvalidaException e) {
			    fail(e.getMessage());
			    e.printStackTrace();
			} 
			 
		}
		//nombre ya registrado 
			
		try {
			 controladorOfertaLab.ingresarDatosPaquete("Básico", "" , null, LocalDate.of(2023, 8, 16), 2, 2);
		      
			 } catch ( PaqueteRegistrado | DescuentoMayorACeroExcepcion | CostoMayorACeroExcepcion | FechaInvalidaException  e) {
				 assertEquals(e.getMessage(), "El nombre del paquete ya se encuentra registrado");
			 }
			
		//Paquete no existe
		try {
			 ManejadorPaqueteTipoPub manejadorPaquete = ManejadorPaqueteTipoPub.getInstance();
			 DtPaquetePub paquete = manejadorPaquete.obtenerDtPaquete("Hiper");
		      
			 } catch (PaqueteNoExisteException  e) {
				 assertEquals(e.getMessage(), "No hay paquete registrado");
			 }
		
		//descuento menor a 0
		try {
			controladorOfertaLab.ingresarDatosPaquete("normal", "" , null, LocalDate.of(2023, 8, 16), 2, -2);
			ManejadorPaqueteTipoPub manejadorPaquete = ManejadorPaqueteTipoPub.getInstance();
			DtPaquetePub paquete = manejadorPaquete.obtenerDtPaquete("normal");
		     
			} catch (PaqueteRegistrado | DescuentoMayorACeroExcepcion | CostoMayorACeroExcepcion |FechaInvalidaException | PaqueteNoExisteException e) {
				 assertEquals(e.getMessage(), "El descuento no puede ser menor a 0 ni mayor a 100");		
			}
		 
		//fecha mayor a la actual
		try {
			controladorOfertaLab.ingresarDatosPaquete("super", "" , null, LocalDate.of(2024, 8, 16), 2, 2);
			ManejadorPaqueteTipoPub manejadorPaquete = ManejadorPaqueteTipoPub.getInstance();
			DtPaquetePub paquete = manejadorPaquete.obtenerDtPaquete("super");
		    
			} catch (PaqueteRegistrado | DescuentoMayorACeroExcepcion | CostoMayorACeroExcepcion | PaqueteNoExisteException |FechaInvalidaException e) {
				 assertEquals(e.getMessage(), "La fecha debe tener menos de 80 años y ser menor o igual a la actual");		
			}
		
		try {
			controladorOfertaLab.ingresarDatosPaquete("normal", "" , null, LocalDate.of(2023, 8, 16), 75, 45);
			ManejadorPaqueteTipoPub manejadorPaquete = ManejadorPaqueteTipoPub.getInstance();
			DtPaquetePub paquete = manejadorPaquete.obtenerDtPaquete("normal");
		     
			} catch (PaqueteRegistrado | DescuentoMayorACeroExcepcion | CostoMayorACeroExcepcion |FechaInvalidaException | PaqueteNoExisteException e) {
				 assertEquals(e.getMessage(), "El costo no puede ser negativo");		
			}
		
		try {
			controladorOfertaLab.ingresarDatosPaquete("normal", "" , null, LocalDate.of(2023, 8, 16), 75, 45);
			ManejadorPaqueteTipoPub manejadorPaquete = ManejadorPaqueteTipoPub.getInstance();
			DtPaquetePub paquete = manejadorPaquete.obtenerDtPaquete("normal");
		     
			} catch (PaqueteRegistrado | DescuentoMayorACeroExcepcion | CostoMayorACeroExcepcion |FechaInvalidaException | PaqueteNoExisteException e) {
				 assertEquals(e.getMessage(), "El nombre del paquete ya se encuentra registrado");		
			}
		}
}
