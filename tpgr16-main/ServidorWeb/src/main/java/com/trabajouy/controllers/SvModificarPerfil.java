package com.trabajouy.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.trabajouy.models.TipoUsuario;
import com.trabajouy.webservices.DtUsuario;
import com.trabajouy.webservices.FechaInvalidaException_Exception;
import com.trabajouy.webservices.UsuarioNoExisteException_Exception;
import com.trabajouy.webservices.WebServicesIUsuario;
import com.trabajouy.webservices.WebServicesIUsuarioService;


/**
 * Servlet implementation class SvModificarPerfil
 */

@WebServlet ("/modificarPerfil")
@MultipartConfig
public class SvModificarPerfil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SvModificarPerfil() {
        super();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response, DtUsuario usuario) throws ServletException, IOException, FechaInvalidaException_Exception, UsuarioNoExisteException_Exception{
    	// Crea una instancia del servicio web llamado WebServicesIOfertaService
    	WebServicesIUsuarioService serviceUsuario = new WebServicesIUsuarioService(); 
    	// Obtiene un puerto (endpoint) del servicio web, a través del cual se realizarán las comunicaciones
    	WebServicesIUsuario portUsuario = serviceUsuario.getWebServicesIUsuarioPort(); 

    	String nombre = request.getParameter("nombre");
    	String apellido = request.getParameter("apellido");
    	
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

    	
    	if (request.getSession().getAttribute("tipo_usuario") == TipoUsuario.POSTULANTE) {
    		//DtPostulante postu = (DtPostulante) usuario;
    		
        	String fechaNacimientoStr = request.getParameter("nacimiento");
        	String nacionalidad = request.getParameter("nacionalidad");
        	//modificar la fecha

        	portUsuario.modificarPostulante(usuario.getNickname(), nombre, apellido, fechaNacimientoStr, nacionalidad,imagenBytes);
    		
    	}else if (request.getSession().getAttribute("tipo_usuario") == TipoUsuario.EMPRESA) {
        	//DtEmpresa empresa = (DtEmpresa) usuario;
        	String descripcion = request.getParameter("descripcion");
        	String websiteLink = request.getParameter("sitio_web");
        	if (websiteLink==null) {
        		websiteLink = "";
        	}
        	portUsuario.modificarEmpresa(usuario.getNickname(), nombre, apellido, descripcion, websiteLink,imagenBytes);
    	}
		request.getRequestDispatcher("/profile?nickname="+usuario.getNickname()).forward(request, response);

    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	DtUsuario usuario = (DtUsuario) request.getSession().getAttribute("usuario_logueado");
		try {
			processRequest(request, response,usuario);
		} catch (ServletException | IOException | FechaInvalidaException_Exception | UsuarioNoExisteException_Exception e) {
			request.setAttribute("error", e.getMessage());
			e.printStackTrace();
			request.getRequestDispatcher("/profile?nickname="+usuario.getNickname()).forward(request, response);
		} 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	
}
