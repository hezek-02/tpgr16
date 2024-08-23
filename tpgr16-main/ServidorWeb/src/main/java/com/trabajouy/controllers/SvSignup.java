package com.trabajouy.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import com.trabajouy.models.EstadoSesion;
import com.trabajouy.models.TipoUsuario;
import com.trabajouy.webservices.DtUsuario;
import com.trabajouy.webservices.UsuarioNoExisteException_Exception;
import com.trabajouy.webservices.WebServicesIUsuario;
import com.trabajouy.webservices.WebServicesIUsuarioService;


@WebServlet ("/signup")
@MultipartConfig
public class SvSignup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SvSignup() {
        super();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession objSesion = request.getSession();
    	
    	// User datos
    	String nickname = request.getParameter("nickname");
    	String email = request.getParameter("email");
    	String nombre = request.getParameter("nombre");
    	String apellido = request.getParameter("apellido");
    	String password = request.getParameter("password");
    	@SuppressWarnings("unused")
		String confirmPassword = request.getParameter("confirmPassword");
    	
    	String userType = request.getParameter("userType");

    	// Postulante datos
    	String fechaNacimiento = request.getParameter("nacimiento");
    	String nacionalidad = request.getParameter("nacionalidad");

    	// Empresa datos
    	String descripcion = request.getParameter("descripcion");
    	String websiteLink = request.getParameter("websiteLink");

		EstadoSesion nuevoEstado;
		
	  	//almacenar imágen, (si trae)
  		Part imgPart = request.getPart("imagen");//archivo(imagen) por partes

		 // Lee el contenido del archivo y lo convierte a bytes
		InputStream inputStream = imgPart.getInputStream();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int bytesRead;
		 
		while ((bytesRead = inputStream.read(buffer)) != -1) {
		    outputStream.write(buffer, 0, bytesRead);
		}
		 
		byte[] imagenBytes = outputStream.toByteArray();
        
		
		try {
			WebServicesIUsuarioService serviceUsuario = new WebServicesIUsuarioService(); 
			WebServicesIUsuario portUsuario = serviceUsuario.getWebServicesIUsuarioPort(); 
			
			if (TipoUsuario.EMPRESA.toString().equals(userType)) {
				portUsuario.ingresarDatosEmpresa(nickname, nombre, apellido, password, email, imagenBytes, descripcion, websiteLink);
				objSesion.setAttribute("tipo_usuario",TipoUsuario.EMPRESA);

			} else if (TipoUsuario.POSTULANTE.toString().equals(userType)) {
				portUsuario.ingresarDatosPostulante(nickname, nombre, apellido, password, email, imagenBytes, nacionalidad, fechaNacimiento);
				objSesion.setAttribute("tipo_usuario",TipoUsuario.POSTULANTE);
			}
			
			nuevoEstado = EstadoSesion.LOGIN_CORRECTO;
			
			DtUsuario usuario = portUsuario.obtenerUsuario(nickname); // TODO: Chequear porque no funciona con email
			request.getSession().setAttribute("usuario_logueado", usuario);
		} catch (Exception ex) {
			System.out.print("Error: " + ex.getMessage());
			request.setAttribute("errorMessage", ex.getMessage());
			nuevoEstado = EstadoSesion.NO_LOGIN;
		}

		objSesion.setAttribute("estado_sesion", nuevoEstado);

		if (nuevoEstado == EstadoSesion.NO_LOGIN) {
		    request.getRequestDispatcher("/WEB-INF/auth/signup.jsp").forward(request, response);
		} else {
		    request.getRequestDispatcher("/home").forward(request, response);
		}
    }
    
    
    private boolean isNicknameAvailable(String nickname) {
    	if (nickname == null || nickname.trim().isEmpty()) {
	        return false;
	    }
    	try {
    		WebServicesIUsuarioService serviceUsuario = new WebServicesIUsuarioService(); 
    		WebServicesIUsuario portUsuario = serviceUsuario.getWebServicesIUsuarioPort(); 
        	DtUsuario usuario = portUsuario.obtenerUsuario(nickname);
        	return usuario == null;
    	} catch (UsuarioNoExisteException_Exception error) {
    		return true;
    	}    	
    }

    private boolean isEmailAvailable(String email) {
        return isNicknameAvailable(email);
    }

    protected void doCheck(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nickname = request.getParameter("nickname");
        String email = request.getParameter("email");

        boolean nicknameAvailable = isNicknameAvailable(nickname);
        boolean emailAvailable = isEmailAvailable(email);
        
        // Establecer el tipo de contenido de la respuesta
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        
        // Obtener el PrintWriter para enviar la respuesta
        PrintWriter out = response.getWriter();
        
        // Enviar la respuesta
        if(nickname != null && !nicknameAvailable) {
            out.print("El nick está en uso");
        } else if(email != null && !emailAvailable) {
            out.print("El email está en uso");
        } else {
            out.print("DISPONIBLE");
        }
        
        out.close();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sesion = request.getSession();
		
		if (request.getParameter("nickname") != null || request.getParameter("email") != null) {
			doCheck(request, response);
		} else {
			if (sesion.getAttribute("estado_sesion") == EstadoSesion.LOGIN_CORRECTO) {
				request.getRequestDispatcher("/home").forward(request, response);
			} else {
				request.getRequestDispatcher("/WEB-INF/auth/signup.jsp").forward(request, response);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
