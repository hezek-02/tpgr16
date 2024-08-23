package test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import excepciones.FechaInvalidaException;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioRegistradoException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import logica.Fabrica;
import logica.datatypes.DtEmpresa;
import logica.datatypes.DtPostulante;
import logica.datatypes.DtUsuario;
import logica.interfaces.IUsuario;
import logica.manejadores.ManejadorUsuarios;

class AltaUsuarioTest {

	private static IUsuario controladorUsuario;
	
	@BeforeAll
	public static void iniciar() {
		Fabrica fabrica = Fabrica.getInstance();
		controladorUsuario = fabrica.getIUsuario();		
	}
	
	//TEST ALTA USUARIO(POST)
	@ParameterizedTest
	@CsvSource({
        	"userio1, Juan, Pérez, juan@example.com, Argentina, 2000-03-04,",
	        "usedsdadasr1, Juan, Pérez, juan@example.com, Argentina, 2000-03-04,El correo o nickname ya se encuentra registrado",
	        "user1, Juan, Pérez, juan@example.com, Argentina, 2000-03-04,El correo o nickname ya se encuentra registrado",
	        "user1, Juan, Pérez, juan@example.com, Argentina, 2000-03-04,El correo o nickname ya se encuentra registrado",
	        "user2, María, Gómez, maria@example.com, España, 1924-03-03,La fecha debe tener menos de 80 años y ser menor o igual a la actual", 
	        "user3, Alex, Smith, alex@example.com, Estados Unidos, 2024-04-02,La fecha debe tener menos de 80 años y ser menor o igual a la actual",
	        "giannis123, Giannis, Antetokoumppo, giannis@example.com, Grecia, 2023-05-01, La fecha debe ser válida. Debe ser mayor de edad",
	        "giannis123, Giannis, Antetokoumppo, giannis@example.com, Grecia, 1983-05-01, ",
	        "user3, Alex, Smith, alex@example.com, Estados Unidos, 2002-04-02,"
	})
	void testRegistrarPostulanteOK(String nickname, String nombre, String apellido, String correo, String pais, String fechaNac, String expectException) {
	    try {
	        LocalDate fechaNacimiento = LocalDate.parse(fechaNac);
	        controladorUsuario.ingresarDatosPostulante(nickname, nombre, apellido, "xxx", correo, null, pais, fechaNacimiento);
		    assertEquals(null, expectException);
		    validarDatosPostulante(nickname, nombre, apellido, correo, pais, fechaNacimiento);
	    } catch (UsuarioRegistradoException | FechaInvalidaException e) {
	        assertEquals(expectException, e.getMessage());
	    }
	}
	
	
	//TEST ALTA USUARIO(EMPRESA)
	@ParameterizedTest
	@CsvSource({
		 	"emp1, Juan, Pérez, juan@Sino.com, Esta es una breve descripcion de mi personasdad asdasd, youtube.com,",
	        "emp2, Juan, Pérez, juan@Sino.com, Esta es una breve descripcion de mi personasdad asdasd, youtube.com,El correo o nickname ya se encuentra registrado",
	        "userdasda1, Juan, Pérez, juan@ee.com, Esta es una breve descripcion de mi personasdad asdasd, youtube.com,",	        
	        "emp, María, Gómez, maria@emp.com, Descripción1, links1,", 
	        "emp3, Alex, Smith,alex@empresa.com, Descripción2, links2,",
	        "eze, Giannis, Antetokoumppo, giannis@exacrasck.com, Descripción3, links3,",
	        "eze, Giannis, Antetokoumppo, giannis@exacrasck.com, Descripción3, links3,El correo o nickname ya se encuentra registrado",
	        "profe, Giannis, Antetokoumppo, giadasannis@.com, Descripción4, links4,",
	        "user3fsdfsd, Alex, Smith, alex@empresa.com, Descripción5, links5,El correo o nickname ya se encuentra registrado"
	})
	void testRegistrarEmpresaOK(String nickname, String nombre, String apellido, String correo, String descripcion, String link, String expectException) {
	    try {
	    	controladorUsuario.ingresarDatosEmpresa(nickname, nombre, apellido, "xxx", correo, null, descripcion, link);
		    assertEquals(null, expectException);
	        validarDatosEmpresa(nickname, nombre, apellido, correo, descripcion, link);
	    } catch (UsuarioRegistradoException | FechaInvalidaException e) {
	        assertEquals(expectException, e.getMessage());
	    }
	}
	
	@Test
	void testUsuNoExiste() {
        try {
        	ManejadorUsuarios manejadorUsuarios = ManejadorUsuarios.getInstance();
	        manejadorUsuarios.obtenerDtUsuario("");
	    }catch (UsuarioNoExisteException e) {
	    	assertEquals(e.getMessage(), "El Usuario no existe!");
	    }
        try {
        	ManejadorUsuarios manejadorUsuarios = ManejadorUsuarios.getInstance();
	        manejadorUsuarios.obtenerDtUsuario("PerroHIDshsoadfhioasdfbasiofbadsgbasfjbsvchjbadsbhjkbdshjdhjbsdhjbjaibcfjbdsfcoijabsdfdoubdsjuiobfijadsbfjisabfijadsbf");
	    }catch (UsuarioNoExisteException e) {
	    	assertEquals(e.getMessage(), "El Usuario no existe!");
	    }

	}
	
	private void validarDatosEmpresa(String nickname, String nombre, String apellido, String correo, String descripcion, String link) {
		try { 
			ManejadorUsuarios manejadorUsuarios = ManejadorUsuarios.getInstance();
	        DtUsuario duNick = manejadorUsuarios.obtenerDtUsuario(nickname);
	   
	        // Verificar los datos del usuario registrado
	        assertEquals(duNick.getNickname(), nickname);
	        assertEquals(duNick.getNombre(), nombre);
	        assertEquals(duNick.getApellido(), apellido);
	        assertEquals(duNick.getCorreo(), correo);
	        if (duNick instanceof DtEmpresa) {
	        	DtEmpresa dpNick  = (DtEmpresa) duNick;
		        assertEquals(dpNick.getDescripcion(), descripcion);
		        assertEquals(dpNick.getSitioWeb(), link);
	        }
	        	
	        DtUsuario duCorreo = manejadorUsuarios.obtenerDtUsuario(nickname);
	        // Verificar los datos del usuario registrado
	        assertEquals(duCorreo.getNickname(), nickname);
	        assertEquals(duCorreo.getNombre(), nombre);
	        assertEquals(duCorreo.getApellido(), apellido);
	        assertEquals(duCorreo.getCorreo(), correo);
	        if (duCorreo instanceof DtEmpresa) {
	        	DtEmpresa dpCorreo = (DtEmpresa) duCorreo;
		        assertEquals(dpCorreo.getDescripcion(), descripcion);
		        assertEquals(dpCorreo.getSitioWeb(), link);
	        }
		}catch (UsuarioNoExisteException e) {
			fail("Error, no se esperaban excepciones");
		}
	}

	private void validarDatosPostulante(String nickname, String nombre, String apellido, String correo, String pais, LocalDate fechaNacimiento) {
		try { 
			ManejadorUsuarios manejadorUsuarios = ManejadorUsuarios.getInstance();
	        DtUsuario duNick = manejadorUsuarios.obtenerDtUsuario(nickname);
	        
	        // Verificar los datos del usuario registrado
	        assertEquals(duNick.getNickname(), nickname);
	        assertEquals(duNick.getNombre(), nombre);
	        assertEquals(duNick.getApellido(), apellido);
	        assertEquals(duNick.getCorreo(), correo);
	        if (duNick instanceof DtPostulante) {
	        	DtPostulante dpNick  = (DtPostulante) duNick;
		        assertEquals(dpNick.getNacionalidad(), pais);
		        assertEquals(dpNick.getFechaNacimiento(), fechaNacimiento);
	        }
	        	
	        DtUsuario duCorreo = manejadorUsuarios.obtenerDtUsuario(nickname);
	        
	        // Verificar los datos del usuario registrado
	        assertEquals(duCorreo.getNickname(), nickname);
	        assertEquals(duCorreo.getNombre(), nombre);
	        assertEquals(duCorreo.getApellido(), apellido);
	        assertEquals(duCorreo.getCorreo(), correo);
	        if (duCorreo instanceof DtPostulante) {
	        	DtPostulante dpCorreo = (DtPostulante) duCorreo;
		        assertEquals(dpCorreo.getNacionalidad(), pais);
		        assertEquals(dpCorreo.getFechaNacimiento(), fechaNacimiento);
	        }

	    } catch (UsuarioNoExisteException e) {
	    	fail("No se esperaban excepciones");
	    }
	}

}