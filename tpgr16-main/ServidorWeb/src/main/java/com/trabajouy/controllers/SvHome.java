package com.trabajouy.controllers;

import java.io.IOException;

import com.trabajouy.models.EstadoSesion;
import com.trabajouy.models.TipoUsuario;
import com.trabajouy.webservices.DtOferta;
import com.trabajouy.webservices.DtOfertaArray;
import com.trabajouy.webservices.DtPostulante;
import com.trabajouy.webservices.UsuarioNoExisteException_Exception;
import com.trabajouy.webservices.WebServicesIUsuario;
import com.trabajouy.webservices.WebServicesIUsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet ("/home")
public class SvHome extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SvHome() {
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
    
    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, UsuarioNoExisteException_Exception {
    	initSession(req);
		req.setAttribute("vista","home");
		switch(getEstado(req)){
			case LOGIN_INCORRECTO:
				req.getSession().setAttribute("estado_sesion", EstadoSesion.NO_LOGIN);
				req.getRequestDispatcher("WEB-INF/auth/login.jsp").forward(req, resp); 
				break;
			case NO_LOGIN:
				req.getRequestDispatcher("ofertas").forward(req, resp);
				break;
			case LOGIN_CORRECTO:
				if (req.getSession().getAttribute("tipo_usuario").equals(TipoUsuario.POSTULANTE)) {
					ofertaFavorita(req, resp);
					//setFavoritos(req, resp);
				}
				req.getRequestDispatcher("ofertas").forward(req, resp);
				break;
		}
	}
    
    private void ofertaFavorita(HttpServletRequest request, HttpServletResponse response) throws UsuarioNoExisteException_Exception {
    	if (request.getParameter("modo") != null) {
    		WebServicesIUsuarioService serviceUsuario = new WebServicesIUsuarioService();
    		WebServicesIUsuario portUsuario = serviceUsuario.getWebServicesIUsuarioPort();
    		
    		String ofertaSeleccionada = request.getParameter("oferta");
    		String modo = request.getParameter("modo");
    		DtPostulante postulante = (DtPostulante) request.getSession().getAttribute("usuario_logueado");
    		//System.out.println("ofertasxd");
    		if (modo.equals("favorite")) {
    			portUsuario.agregarOfertaFavorita(postulante.getNickname(), ofertaSeleccionada);
    			//System.out.println("agregoF");
    		} else if (modo.equals("erase_favorite")) {
    			portUsuario.eliminarOfertaFavorita(postulante.getNickname(), ofertaSeleccionada);
    			//System.out.println("quitoF");
    		}
    	}
    	setFavoritos(request, response);
    }
    
    private void setFavoritos(HttpServletRequest request, HttpServletResponse response) throws UsuarioNoExisteException_Exception {
    	WebServicesIUsuarioService serviceUsuario = new WebServicesIUsuarioService();
		WebServicesIUsuario portUsuario = serviceUsuario.getWebServicesIUsuarioPort();
		
		//System.out.println("entro");
		DtPostulante postulante = (DtPostulante) request.getSession().getAttribute("usuario_logueado");
		DtOfertaArray ofertasFav = portUsuario.obtenerOfertasFavoritas(postulante.getNickname());
		DtOferta[] listaOfertasFav = ofertasFav.getItem().toArray(new DtOferta[0]);
		request.setAttribute("favoritas", listaOfertasFav);
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (ServletException | IOException | UsuarioNoExisteException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (ServletException | IOException | UsuarioNoExisteException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
