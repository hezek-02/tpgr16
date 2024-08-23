package com.trabajouy.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import com.trabajouy.webservices.DtOferta;
import com.trabajouy.webservices.DtPostulacion;
import com.trabajouy.webservices.NoPoseePostulacionesException_Exception;
import com.trabajouy.webservices.OfertaNoExisteException_Exception;
import com.trabajouy.webservices.TipoPubNoExisteException_Exception;
import com.trabajouy.webservices.UsuarioNoExisteException_Exception;
import com.trabajouy.webservices.WebServicesIOferta;
import com.trabajouy.webservices.WebServicesIOfertaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet ("/PostusOL")
public class SvPostulaciones extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public SvPostulaciones() {
        super();
    }
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, TipoPubNoExisteException_Exception, OfertaNoExisteException_Exception {	 

        
    	WebServicesIOfertaService service = new WebServicesIOfertaService();
    	WebServicesIOferta port = service.getWebServicesIOfertaPort();
		
		String ofSelected = request.getParameter("ofertaPostulantes");
		DtOferta ofertaLaboral = port.obtenerDtOferta(ofSelected);
		request.setAttribute("ofertaL", ofertaLaboral);
		
		try {
			Collection<DtPostulacion> postulantes = new ArrayList<>(port.obtenerPostulaciones(ofertaLaboral.getNombre()).getItem());
			request.setAttribute("listaPostulantes", postulantes);
			request.getRequestDispatcher("/WEB-INF/ofertas/postulacion.jsp").forward(request, response);
		} catch (NoPoseePostulacionesException_Exception | UsuarioNoExisteException_Exception | ServletException
				| IOException e) {
			e.printStackTrace();
			request.setAttribute("error", "No existen postulaciones a la oferta laboral");
			request.setAttribute("OfertaLab", ofertaLaboral);
			request.getRequestDispatcher("consulta-postu").forward(request, response);
		}
		
		/*try {
			if(interfazOfertaLaboral.obtenerPostulaciones(ofertaLaboral) != null) {
				Collection<DtPostulacion> postulantes = new ArrayList<>(Arrays.asList(interfazOfertaLaboral.obtenerPostulaciones(ofertaLaboral)));
				request.setAttribute("listaPostulantes", postulantes);
			}else{
		        throw new NoExistenPostulacionesAOfertaException("No existen postulaciones a la oferta laboral");
		    }
			request.getRequestDispatcher("/WEB-INF/ofertas/postulacionesOferta.jsp").forward(request, response);
		} catch (PostulacionYaExisteException e) {
		    request.setAttribute("error", "No existen postulaciones a la oferta laboral");
		    request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
		}*/
	}
		
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (ServletException | IOException | TipoPubNoExisteException_Exception | OfertaNoExisteException_Exception e) {
			e.printStackTrace();
		}

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (ServletException | IOException | TipoPubNoExisteException_Exception | OfertaNoExisteException_Exception  e) {
			e.printStackTrace();
		}

	}
}