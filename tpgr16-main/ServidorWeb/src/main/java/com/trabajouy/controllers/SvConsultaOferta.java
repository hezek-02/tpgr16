package com.trabajouy.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import com.trabajouy.models.TipoUsuario;
import com.trabajouy.webservices.DtEmpresa;
import com.trabajouy.webservices.DtOferta;
import com.trabajouy.webservices.DtOfertaArray;
import com.trabajouy.webservices.DtPaquetePub;
import com.trabajouy.webservices.DtPostulacion;
import com.trabajouy.webservices.DtPostulacionArray;
import com.trabajouy.webservices.DtPostulante;
import com.trabajouy.webservices.DtPostulanteArray;
import com.trabajouy.webservices.DtUsuario;
import com.trabajouy.webservices.EstadoOferta;
import com.trabajouy.webservices.NoPoseePostulacionesException_Exception;
import com.trabajouy.webservices.OfertaNoExisteException_Exception;
import com.trabajouy.webservices.PaqueteNoExisteException_Exception;
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


@WebServlet("/consulta-oferta")
public class SvConsultaOferta extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SvConsultaOferta() {
		super();
	}
	
	public void initSession(HttpServletRequest request) {
		String session = (String)request.getSession().getAttribute("sesion");
		if (session == null) {
			request.getSession().setAttribute("sesion", "NO_LOGIN");
		}
	}
	
	//no cargan los que no tienen postulantes parece
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, TipoPubNoExisteException_Exception, UsuarioNoExisteException_Exception, OfertaNoExisteException_Exception {
		SvOfertasLaborales.cargaKeyWords(request);
		
		// Crea una instancia del servicio web llamado WebServicesIOfertaService
		WebServicesIOfertaService serviceOferta = new WebServicesIOfertaService(); 
		WebServicesIUsuarioService serviceUsuario = new WebServicesIUsuarioService(); 

		// Obtiene un puerto (endpoint) del servicio web, a través del cual se realizarán las comunicaciones
		WebServicesIOferta portOferta = serviceOferta.getWebServicesIOfertaPort(); 
		WebServicesIUsuario portUsuario = serviceUsuario.getWebServicesIUsuarioPort();
		
		String ofertaSeleccionada = request.getParameter("oferta");
		DtOferta oferta = portOferta.obtenerDtOferta(ofertaSeleccionada);
		
		
		if (request.getSession().getAttribute("tipo_usuario") == TipoUsuario.POSTULANTE && oferta.getEstado() == EstadoOferta.CONFIRMADO) {
			DtPostulante postulante = (DtPostulante) request.getSession().getAttribute("usuario_logueado");
			//System.out.println("well well well look what we have here");
			DtOfertaArray ofertasFav = portUsuario.obtenerOfertasFavoritas(postulante.getNickname());
			DtOferta[] listaOfertasFav = ofertasFav.getItem().toArray(new DtOferta[0]);
			boolean es_fav = false;
			for(DtOferta favoritos: listaOfertasFav) {
				if (favoritos.getNombre().equals(ofertaSeleccionada)) {
					es_fav = true;
					//System.out.println(favoritos.getNombre());
					//System.out.println(ofertaSeleccionada);
					//System.out.println("");
				}
			}
			request.setAttribute("fav", es_fav);
			if (request.getParameter("modo") != null) {
				ofertaFavorita(request, response);
			}
		}
		
		DtPostulanteArray postulantesFav = portOferta.obtenerPostulantesFavoritos(ofertaSeleccionada);
		DtPostulante[] listaPostulantesFav = postulantesFav.getItem().toArray(new DtPostulante[0]);
		request.setAttribute("postulantesFav", listaPostulantesFav);
		
		
		
		DtPostulacionArray postulacionesArray;
		DtPostulacion[] postulaciones = null;
		try {
			postulacionesArray = portOferta.obtenerPostulaciones(oferta.getNombre());
			postulaciones = postulacionesArray.getItem().toArray(new DtPostulacion[0]);

		} catch (NoPoseePostulacionesException_Exception | UsuarioNoExisteException_Exception e) {
			e.printStackTrace();
		}
		

		if (postulaciones != null) {
			Collection<DtUsuario> postulantes = new ArrayList<>();
			for (DtPostulacion post : postulaciones) {
				postulantes.add(portUsuario.obtenerPostulante(post.getNicknamePostulante())); 
			}
			request.setAttribute("postulantes", postulantes);
		}
		
		DtPaquetePub paquete = null;
		try {
			paquete = portOferta.obtenerPaqueteCompra(oferta.getNombre());
		} catch (PaqueteNoExisteException_Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("paquete", paquete);
		
		
    	DtEmpresa emp = portUsuario.obtenerEmpresaDadoOferta(oferta.getNombre());
    	DtUsuario usuario_log = null;
    	if (request.getSession().getAttribute("usuario_logueado") != null)
    		usuario_log = (DtUsuario) request.getSession().getAttribute("usuario_logueado");
    	
		if (request.getSession().getAttribute("tipo_usuario") != null && request.getSession().getAttribute("tipo_usuario").equals(TipoUsuario.EMPRESA) && usuario_log != null && (emp.getNickname()).equals(usuario_log.getNickname())){
			request.setAttribute("propietario", true);
		}
		
    	request.setAttribute("vencida", false);

		if (portOferta.estaVencida(oferta.getNombre())) {
			request.setAttribute("vencida", true);
		}
		
		SvPostularse.validarPostulacion(request, response, ofertaSeleccionada);
		
		request.setAttribute("oferta", oferta);
//    	
		request.setAttribute("ofertaSeleccionada", oferta);
    	request.setAttribute("empresaDeOferta", emp);
		
		request.getRequestDispatcher("/WEB-INF/ofertas/consultaOferta.jsp").forward(request, response);
	}
	
	private void ofertaFavorita(HttpServletRequest request, HttpServletResponse response) throws UsuarioNoExisteException_Exception {
		if (request.getParameter("modo") != null) {
    		WebServicesIUsuarioService serviceUsuario = new WebServicesIUsuarioService();
    		WebServicesIUsuario portUsuario = serviceUsuario.getWebServicesIUsuarioPort();
    		
    		String ofertaSeleccionada = request.getParameter("oferta");
    		String modo = request.getParameter("modo");
    		DtPostulante postulante = (DtPostulante) request.getSession().getAttribute("usuario_logueado");
    		
    		//System.out.println("ofertasxd");
    		if (modo.equals("fav")) {
    			portUsuario.agregarOfertaFavorita(postulante.getNickname(), ofertaSeleccionada);
    		} else if (modo.equals("erase_fav")) {
    			portUsuario.eliminarOfertaFavorita(postulante.getNickname(), ofertaSeleccionada);
    		}
    		verificarFav(request, response);
    	}
	}
	
	protected void verificarFav(HttpServletRequest request, HttpServletResponse response) throws UsuarioNoExisteException_Exception {
		WebServicesIUsuarioService serviceUsuario = new WebServicesIUsuarioService();
		WebServicesIUsuario portUsuario = serviceUsuario.getWebServicesIUsuarioPort();
		
		DtPostulante postulante = (DtPostulante) request.getSession().getAttribute("usuario_logueado");
		String ofertaSeleccionada = request.getParameter("oferta");
		DtOfertaArray ofertasFav = portUsuario.obtenerOfertasFavoritas(postulante.getNickname());
		DtOferta[] listaOfertasFav = ofertasFav.getItem().toArray(new DtOferta[0]);
		boolean es_fav = false;
		for(DtOferta favoritos: listaOfertasFav) {
			if (favoritos.getNombre().equals(ofertaSeleccionada)) {
				es_fav = true;
				//System.out.println(favoritos.getNombre());
				//System.out.println(ofertaSeleccionada);
				//System.out.println("");
			}
		}
		//System.out.println(es_fav);
		request.setAttribute("fav", es_fav);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		try {
			processRequest(request, response);
		} catch (ServletException | IOException | TipoPubNoExisteException_Exception
				| UsuarioNoExisteException_Exception | OfertaNoExisteException_Exception e) {
			request.setAttribute("error", e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			try {
				processRequest(request, response);
			} catch (ServletException | IOException | TipoPubNoExisteException_Exception
					| UsuarioNoExisteException_Exception | OfertaNoExisteException_Exception e) {
				request.setAttribute("error", e.getMessage());
				e.printStackTrace();
			}
		
	}
}
