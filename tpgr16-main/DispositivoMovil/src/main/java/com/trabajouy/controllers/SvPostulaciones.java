package com.trabajouy.controllers;

import java.io.IOException;

import com.trabajouy.models.EstadoSesion;
import com.trabajouy.webservices.DtOferta;
import com.trabajouy.webservices.DtOfertaArray;
import com.trabajouy.webservices.DtPostulacion;
import com.trabajouy.webservices.DtUsuario;
import com.trabajouy.webservices.NoPoseePostulacionesException_Exception;
import com.trabajouy.webservices.TipoPubNoExisteException_Exception;
import com.trabajouy.webservices.UsuarioNoExisteException_Exception;
import com.trabajouy.webservices.WebServicesIOferta;
import com.trabajouy.webservices.WebServicesIOfertaService;
import com.trabajouy.webservices.WebServicesIUsuario;
import com.trabajouy.webservices.WebServicesIUsuarioService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet ("/postulaciones")
public class SvPostulaciones extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public SvPostulaciones() {
        super();
    }
	
    public void initSession(HttpServletRequest request) {
    	HttpSession session = request.getSession();
		if (session.getAttribute("estado_sesion") == null) {
			session.setAttribute("estado_sesion", EstadoSesion.NO_LOGIN);
		}
	}
    
	public static EstadoSesion getEstado(HttpServletRequest request){
		return (EstadoSesion) request.getSession().getAttribute("estado_sesion");
	}
	
	/* Muestra todas las ofertas postuladas del usuario logeado en postulaciones.jsp
	 * De otra manera redirige a postulacion.jsp
	 * */
	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	 
		initSession(request);
		switch(getEstado(request)){
		case NO_LOGIN:
			response.sendRedirect(request.getContextPath() + "/login");
			break;
		case LOGIN_CORRECTO:
			try {
				DtUsuario usuario = (DtUsuario) request.getSession().getAttribute("usuario_logueado");
				String ofertaNombre = (String) request.getParameter("oferta");
				if (ofertaNombre != null) {					
					DtPostulacion postulacion = getPostulacion(request, response, usuario.getNickname(), ofertaNombre);
					DtOferta oferta = getOferta(ofertaNombre);
					request.setAttribute("oferta", oferta);
					request.setAttribute("postulacion", postulacion);
					request.getRequestDispatcher("/WEB-INF/postulacion.jsp").forward(request, response);
				} else {
					DtOferta[] ofertasPostuladas = getOfertasPostuladas(request, response, usuario.getNickname());
					request.setAttribute("ofertas", ofertasPostuladas);
					request.getRequestDispatcher("/WEB-INF/postulaciones.jsp").forward(request, response);
				}
			}catch(UsuarioNoExisteException_Exception | NoPoseePostulacionesException_Exception | TipoPubNoExisteException_Exception error) {
				error.printStackTrace();
				request.setAttribute("errorMessage", error.getMessage());
				request.getRequestDispatcher("/WEB-INF/postulaciones.jsp").forward(request, response);
			}
			break;
		}
	}
		
    public DtOferta[] getOfertasPostuladas(HttpServletRequest request,HttpServletResponse response, String postulante) throws UsuarioNoExisteException_Exception, NoPoseePostulacionesException_Exception {
    	WebServicesIOfertaService service = new WebServicesIOfertaService();
    	WebServicesIOferta port = service.getWebServicesIOfertaPort();
    	DtOfertaArray ofertas = port.obtenerOfertasPostuladas(postulante);
    	DtOferta[] listaOfertas = ofertas.getItem().toArray(new DtOferta[0]);
    	return listaOfertas;
    }
    
    public DtPostulacion getPostulacion(HttpServletRequest request,HttpServletResponse response, String postulante, String oferta) {
		WebServicesIUsuarioService serviceUsuario = new WebServicesIUsuarioService();
		WebServicesIUsuario portUsuario = serviceUsuario.getWebServicesIUsuarioPort();
		DtPostulacion postulacion = portUsuario.obtenerPostulacion(oferta, postulante);
		return postulacion;
    }
    
    public DtOferta getOferta(String oferta) throws TipoPubNoExisteException_Exception {
		WebServicesIOfertaService service = new WebServicesIOfertaService();
		WebServicesIOferta port = service.getWebServicesIOfertaPort();
		DtOferta dtOferta = port.obtenerDtOferta(oferta);
		return dtOferta;
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (ServletException | IOException  e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (ServletException | IOException  e) {
			e.printStackTrace();
		}
	}
}