package test;




import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import excepciones.FechaInvalidaException;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioRegistradoException;
import logica.datatypes.DtEmpresa;
import logica.datatypes.DtPostulante;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import logica.datatypes.DtUsuario;
import logica.Fabrica;
import logica.interfaces.IUsuario;
import logica.manejadores.ManejadorUsuarios;

class ModificarUsuarioTest {

	private static IUsuario controladorUsuario;
	
	@BeforeAll
	public static void iniciar() {
		Fabrica fabrica = Fabrica.getInstance();
		controladorUsuario = fabrica.getIUsuario();
		
	}


	//TEST MODIFICAR USUARIO
	@Test
	void testModificarUsuarioOK() {
	    try {
	    	//CV
	    	//EMPRESA
	    	String nickname = "cosmiccoffee"; //No se modifican
	    	String nombre = "Cosmic Coffee Co.";
	    	String apellido = "Starbrews";
	    	String correo = "info@cosmiccoffee.com"; //No se modifican
	    	String descripcion = "Explorando los sabores del universo en cada taza";
	    	String link = "http://www.cosmiccoffee.com";
	    	controladorUsuario.ingresarDatosEmpresa(nickname, nombre, apellido, "xxx", correo, null, descripcion, link);
	    	validarDatosEmpresa(nickname, nombre, apellido, correo, descripcion, link);
	    	//Se cambian los datos
	    	nombre = "Galactic Beans";
	    	apellido = "Stellar Roasters";
	    	descripcion = "Exploring the universe's flavors in every cup";
	    	link = "http://www.galacticbeans.com";
	        controladorUsuario.modificarUsuario(nickname, nombre, apellido, descripcion, link, null);
	        validarDatosEmpresa(nickname, nombre, apellido, correo, descripcion, link);
	        //POSTULANTES
	        nickname = "stellarApplicant"; // No se modifican
	        nombre = "Astrid";
	        apellido = "Nova";
	        correo = "astrid.nova@example.com"; // No se modifican
	        LocalDate fech = LocalDate.of(1995, 4, 10);
	        String pais = "United States"; 
	    	controladorUsuario.ingresarDatosPostulante(nickname, nombre, apellido, "xxx", correo, null, pais, fech);
	    	validarDatosPostulante(nickname, nombre, apellido, correo, pais, fech);
	    	nombre = "Erik";
	    	apellido = "Lund";
	    	fech = LocalDate.of(1988, 6, 20);
	    	pais = "Norway";
	    	controladorUsuario.modificarUsuario(nickname, nombre, apellido, fech, pais, null);
	    	validarDatosPostulante(nickname, nombre, apellido, correo, pais, fech);

	    } catch (UsuarioRegistradoException | FechaInvalidaException | UsuarioNoExisteException e) {
	        fail("Error, no se esperaban excepciones");
	    }
	    //CI
	    try {
	    	//EMPRESA
	    	//Empresa no registrada
	    	String nickname = "Whaccoffee"; //No se modifican
	    	String nombre = "Cosmic Coffee Co.";
	    	String apellido = "Starbrews";
	    	String correo = "info@NICE.com"; //No se modifican
	    	String descripcion = "adasdas los sabores del universo en cada taza";
	    	String link = "http://www.yesIKNow.com";
	    	controladorUsuario.modificarUsuario(nickname, nombre, apellido, descripcion, link, null);
	    	validarDatosEmpresa(nickname, nombre, apellido, correo, descripcion, link);
	        fail("Error,  se esperaban excepciones");
	    }catch (UsuarioNoExisteException e) {
	    	assertEquals(e.getMessage(), "El Usuario no existe!");
	    }
	    try {
	    	//EMPRESA
	    	String nickname = "Whaccoffee"; //No se modifican
	    	String nombre = "Cosmic Coffee Co.";
	    	String apellido = "Starbrews";
	    	String correo = "info@NICE.com"; //No se modifican
	    	String descripcion = "adasdas los sabores del universo en cada taza";
	    	String link = "http://www.yesIKNow.com";
	    	controladorUsuario.ingresarDatosEmpresa(nickname, nombre, apellido, "xxx", correo, null, descripcion, link);
	    	validarDatosEmpresa(nickname, nombre, apellido, correo, descripcion, link);
	    	//Se cambia dato de clave correo
	    	nickname = "Whaccoffee"; //No se modifican
	    	nombre = "Galactic Beans";
	    	apellido = "Stellar Roasters";
	    	correo = "info@Diff.com"; //se modifica
	    	descripcion = "Exploring the universe's flavors in every cup";
	    	link = "http://www.galacticbeans.com";
	        controladorUsuario.modificarUsuario(nickname, nombre, apellido, descripcion, link, null);
	    	ManejadorUsuarios manejadorUsuarios = ManejadorUsuarios.getInstance();
	        DtUsuario duNick = manejadorUsuarios.obtenerDtUsuario(nickname);
	        // Verificar los datos del usuario registrado
	        assertEquals(duNick.getNickname(), nickname);
	        assertEquals(duNick.getNombre(), nombre);
	        assertEquals(duNick.getApellido(), apellido);
	        assertEquals(duNick.getCorreo(), "info@NICE.com"); //No se altera
	        if (duNick instanceof DtEmpresa) {
	        	DtEmpresa dpNick  = (DtEmpresa) duNick;
		        assertEquals(dpNick.getDescripcion(), descripcion);
		        assertEquals(dpNick.getSitioWeb(), link);
	        }
	    }catch (UsuarioRegistradoException | FechaInvalidaException | UsuarioNoExisteException e) {
	        fail("Error, no se esperaban excepciones");
	    }
        try {
		    //POSTULANTES
	        String nickname = "StellaAtdasst"; // No se modifican
	        String nombre = "Astrid";
	        String apellido = "Nova";
	        String correo = "Nenio"; // No se modifican
	        LocalDate fech = LocalDate.of(1995, 4, 10);
	        String pais = "United States"; 
	    	controladorUsuario.modificarUsuario(nickname, nombre, apellido, fech, pais, null);
	    	validarDatosPostulante(nickname, nombre, apellido, correo, pais, fech);
	    }catch (FechaInvalidaException | UsuarioNoExisteException e) {
	        assertEquals(e.getMessage(), "El Usuario no existe!");
        }
	    //Pruebas de Fecha
        try {
	        String nickname = "StellaAtdasst"; // No se modifican
	        String nombre = "Astrid";
	        String apellido = "Nova";
	        String correo = "Nenio"; // No se modifican
	        LocalDate fech = LocalDate.of(1995, 4, 10);
	        String pais = "United States"; 
	    	controladorUsuario.ingresarDatosPostulante(nickname, nombre, apellido, "xxx", correo, null, pais, fech);
	    	validarDatosPostulante(nickname, nombre, apellido, correo, pais, fech);
	    	nombre = "Erik";
	    	apellido = "Lund";
	    	fech = LocalDate.of(1108, 6, 20);
	    	pais = "Norway";
	    	controladorUsuario.modificarUsuario(nickname, nombre, apellido, fech, pais, null);
	    	validarDatosPostulante(nickname, nombre, apellido, correo, pais, fech);
	    }catch (UsuarioRegistradoException | FechaInvalidaException | UsuarioNoExisteException e) {
	        assertEquals(e.getMessage(), "La fecha debe tener menos de 80 años y ser menor o igual a la actual");
        }
        try {
		    //POSTULANTES
	        String nickname = "Gerardo"; // No se modifican
	        String nombre = "Astrid";
	        String apellido = "Nova";
	        String correo = "Geri@example.com"; // No se modifican
	        LocalDate fech = LocalDate.of(1995, 4, 10);
	        String pais = "United States"; 
	    	
	    	controladorUsuario.ingresarDatosPostulante(nickname, nombre, apellido, "xxx", correo, null, pais, fech);
	    	validarDatosPostulante(nickname, nombre, apellido, correo, pais, fech);
	    	
	    	nombre = "Kant";
	    	apellido = "Lund";
	    	fech = LocalDate.of(2988, 6, 20);
	    	pais = "Norway";
	
	    	controladorUsuario.modificarUsuario(nickname, nombre, apellido, fech, pais, null);
	    	validarDatosPostulante(nickname, nombre, apellido, correo, pais, fech);
	    }catch (UsuarioRegistradoException | FechaInvalidaException | UsuarioNoExisteException e) {
	        assertEquals(e.getMessage(), "La fecha debe tener menos de 80 años y ser menor o igual a la actual");
        }
        try {
		    //POSTULANTES
	        String nickname = "Kevin"; // No se modifican
	        String nombre = "Astrid";
	        String apellido = "Nova";
	        String correo = "Kevinho@xample.com"; // No se modifican
	        LocalDate fech = LocalDate.of(1995, 4, 10);
	        String pais = "United States"; 
	    	
	    	controladorUsuario.ingresarDatosPostulante(nickname, nombre, apellido, "xxx", correo, null, pais, fech);
	    	validarDatosPostulante(nickname, nombre, apellido, correo, pais, fech);
	    	
	    	nombre = "Kant";
	    	apellido = "Lund";
	    	fech = LocalDate.of(2017, 6, 20);
	    	pais = "Norway";
	
	    	controladorUsuario.modificarUsuario(nickname, nombre, apellido, fech, pais, null);
	    	validarDatosPostulante(nickname, nombre, apellido, correo, pais, fech);
	    }catch (UsuarioRegistradoException | FechaInvalidaException | UsuarioNoExisteException e) {
	        assertEquals(e.getMessage(), "La fecha debe ser válida. Debe ser mayor de edad");
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