package com.trabajouy.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.trabajouy.models.TipoUsuario;
import com.trabajouy.webservices.DtEmpresa;
import com.trabajouy.webservices.DtOferta;
import com.trabajouy.webservices.DtPostulante;
import com.trabajouy.webservices.FechaInvalidaException_Exception;
import com.trabajouy.webservices.OfertaNoExisteException_Exception;
import com.trabajouy.webservices.PostulacionYaExisteException_Exception;
import com.trabajouy.webservices.TipoPubNoExisteException_Exception;
import com.trabajouy.webservices.UsuarioNoExisteException_Exception;
import com.trabajouy.webservices.WebServicesIOferta;
import com.trabajouy.webservices.WebServicesIOfertaService;
import com.trabajouy.webservices.WebServicesIUsuario;
import com.trabajouy.webservices.WebServicesIUsuarioService;


@WebServlet("/postularse")
public class SvPostularse extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public SvPostularse() {
        super();
    }
    
    private void processRequest(HttpServletRequest request, HttpServletResponse response,String ofertaSeleccionada) throws ServletException, IOException, UsuarioNoExisteException_Exception, TipoPubNoExisteException_Exception, OfertaNoExisteException_Exception {
		if (request.getSession().getAttribute("tipo_usuario") != TipoUsuario.POSTULANTE) {
			//solo postulantes se pueden postular
			response.sendRedirect(request.getContextPath() + "/home");
		}
		
		WebServicesIOfertaService service = new WebServicesIOfertaService();

		WebServicesIUsuarioService serviceUsuario = new WebServicesIUsuarioService();

    	WebServicesIOferta portOferta = service.getWebServicesIOfertaPort();

		WebServicesIUsuario portUsuario = serviceUsuario.getWebServicesIUsuarioPort();
		
    	DtOferta oferta = portOferta.obtenerDtOferta(ofertaSeleccionada);
    	DtEmpresa emp = portUsuario.obtenerEmpresaDadoOferta(oferta.getNombre());
    	
    	request.setAttribute("ofertaSeleccionada", oferta);
    	request.setAttribute("empresaDeOferta", emp);
    	request.getRequestDispatcher("/WEB-INF/usuarios/postularse.jsp").forward(request, response);
	}

    
    public static void validarPostulacion(HttpServletRequest request, HttpServletResponse response, String ofertaSeleccionada) throws UsuarioNoExisteException_Exception, TipoPubNoExisteException_Exception, OfertaNoExisteException_Exception {
    	request.setAttribute("no_postulado", true);
		WebServicesIOfertaService service = new WebServicesIOfertaService();

		WebServicesIUsuarioService serviceUsuario = new WebServicesIUsuarioService();

    	WebServicesIOferta portOferta = service.getWebServicesIOfertaPort();

		WebServicesIUsuario portUsuario = serviceUsuario.getWebServicesIUsuarioPort();
    	
    	if (request.getSession().getAttribute("tipo_usuario") == TipoUsuario.POSTULANTE) {
	    	DtPostulante postulante =(DtPostulante) request.getSession().getAttribute("usuario_logueado");
	    	DtOferta oferta = portOferta.obtenerDtOferta(ofertaSeleccionada);
			if (portUsuario.existePostulacion(oferta.getNombre(), postulante.getNickname())) {
				//request.setAttribute("error", "Ya existe esta postulacion");
				request.setAttribute("no_postulado", false);
			}else if ((boolean) request.getAttribute("vencida")){
				request.setAttribute("error", "La oferta se encuentra vencida, no es pósible postularse");
			}
    	}
    }

	private void postularse(HttpServletRequest request, HttpServletResponse response, String ofertaSeleccionada) throws ServletException, IOException, UsuarioNoExisteException_Exception, TipoPubNoExisteException_Exception, OfertaNoExisteException_Exception {
    	DtPostulante postulante =(DtPostulante) request.getSession().getAttribute("usuario_logueado");
    	
		WebServicesIOfertaService service = new WebServicesIOfertaService();

		WebServicesIUsuarioService serviceUsuario = new WebServicesIUsuarioService();

    	WebServicesIOferta portOferta = service.getWebServicesIOfertaPort();

		WebServicesIUsuario portUsuario = serviceUsuario.getWebServicesIUsuarioPort();

    	DtOferta oferta = portOferta.obtenerDtOferta(ofertaSeleccionada);
    	
    	
    	String cvReducido = request.getParameter("cvReducido");
    	String motivacion = request.getParameter("motivacion");
    	String videoUrl = request.getParameter("videoLink");
		try {
			portUsuario.ingresarDatosPostulacion(cvReducido, motivacion, oferta.getNombre(), postulante.getNickname(), videoUrl);
		} catch (FechaInvalidaException_Exception | PostulacionYaExisteException_Exception
				| UsuarioNoExisteException_Exception e) {
			request.setAttribute("error",e.getMessage());
			processRequest(request,response,ofertaSeleccionada);
		}
		request.getRequestDispatcher("home").forward(request, response);
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ofertaSeleccionada = request.getParameter("oferta");
		try {
			processRequest(request,response,ofertaSeleccionada);
		} catch (ServletException | IOException | UsuarioNoExisteException_Exception
				| TipoPubNoExisteException_Exception | OfertaNoExisteException_Exception e) {
			request.setAttribute("error",e.getMessage());
			e.printStackTrace();
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ofertaSeleccionada = request.getParameter("oferta");
		String action = (String) request.getParameter("accion");
	    if (action!=null && action.equals("postularse") && ofertaSeleccionada != null ) {
			try {
				postularse(request, response, ofertaSeleccionada);
			} catch (ServletException | IOException | UsuarioNoExisteException_Exception
					| TipoPubNoExisteException_Exception | OfertaNoExisteException_Exception e) {
				e.printStackTrace();
			}
	    } 
	}



}
