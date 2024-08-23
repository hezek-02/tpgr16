package com.trabajouy.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.trabajouy.models.TipoUsuario;
import com.trabajouy.webservices.DtEmpresa;
import com.trabajouy.webservices.DtOferta;
import com.trabajouy.webservices.DtPostulacion;
import com.trabajouy.webservices.DtPostulacionArray;
import com.trabajouy.webservices.DtPostulante;
import com.trabajouy.webservices.EstadoOferta;
import com.trabajouy.webservices.NoPoseePostulacionesException_Exception;
import com.trabajouy.webservices.OfertaNoExisteException_Exception;
import com.trabajouy.webservices.PaqueteYaCompradoException_Exception;
import com.trabajouy.webservices.RankingYaOcupadoException_Exception;
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

@SuppressWarnings("unused")
@WebServlet ("/PostOL")
public class SvPostulacion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DtPostulante postulante;
	private DtOferta ofertaLaboral;
	
	public SvPostulacion() {
        super();
    }
	
	private void processRequest(HttpServletRequest req, HttpServletResponse resp, String ofSelected, String NickPostulante) throws ServletException, IOException, NoPoseePostulacionesException_Exception, UsuarioNoExisteException_Exception, TipoPubNoExisteException_Exception, OfertaNoExisteException_Exception {	 
		SvOfertasLaborales.cargaKeyWords(req);
		
		// Crea una instancia del servicio web llamado WebServicesIOfertaService
		WebServicesIOfertaService serviceOferta = new WebServicesIOfertaService(); 
		WebServicesIUsuarioService serviceUsuario = new WebServicesIUsuarioService(); 

		// Obtiene un puerto (endpoint) del servicio web, a través del cual se realizarán las comunicaciones
		WebServicesIOferta portOferta = serviceOferta.getWebServicesIOfertaPort(); 
		WebServicesIUsuario portUsuario = serviceUsuario.getWebServicesIUsuarioPort(); 
		//Para rankear
    	req.setAttribute("vencidaConfirmada",false);
    	
		ofertaLaboral = portOferta.obtenerDtOferta(ofSelected);
		//Comprueba si esta para rankear 
		if (ofertaLaboral.getEstado().equals(EstadoOferta.CONFIRMADO) && portOferta.estaVencida(ofSelected))
	    	req.setAttribute("vencidaConfirmada",true);
		
		if(req.getSession().getAttribute("tipo_usuario") == TipoUsuario.EMPRESA){
			if(ofertaLaboral != null) {
				req.setAttribute("oferta", ofertaLaboral);
				
				Collection<DtPostulacion> listaPostulantes = new ArrayList<>();
				DtPostulacionArray postus = portOferta.obtenerPostulaciones(ofertaLaboral.getNombre());
				listaPostulantes = postus.getItem(); 
				
				DtPostulacion postu;
				try {
					//cargaPostulante
					for(DtPostulacion p: listaPostulantes) {
						if(p.getNicknamePostulante().equals(NickPostulante)) {
							postu = p;
							req.setAttribute("Postulante", postu);
						}
					}
					req.getRequestDispatcher("/WEB-INF/ofertas/postulacion.jsp").forward(req, resp);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else if (req.getSession().getAttribute("tipo_usuario") == TipoUsuario.POSTULANTE) {
			if(ofertaLaboral != null) {
				req.setAttribute("oferta", ofertaLaboral);
				
				DtPostulante po = (DtPostulante) req.getSession().getAttribute("usuario_logueado");
				DtPostulacion postulante = portUsuario.obtenerPostulacion(ofertaLaboral.getNombre(), po.getNickname());
				/*try {*/
				    if (portUsuario.existePostulacion(ofertaLaboral.getNombre(), po.getNickname())) {
				        req.setAttribute("Postulante", postulante);
				        req.getRequestDispatcher("/WEB-INF/ofertas/postulacion.jsp").forward(req, resp);
				    } /*else {
				        throw new PostulacionYaExisteException("No existe postulación del usuario a la oferta laboral");
				    }
				} catch (PostulacionYaExisteException e) {
				    req.setAttribute("error", "No existe postulación del usuario a la oferta laboral");
				    req.getRequestDispatcher("/WEB-INF/PostOL.jsp").forward(req, resp);
				}*/


			}
		}
	}

			
	/*public void listarPostPostu(HttpServletRequest request, HttpServletResponse response) {
		IOfertaLab interfazOfertaLaboral = SvHome.obtenerInterfazOferta();
		IUsuario interfazUsuario = SvHome.obtenerInterfazUsu();
		
		String ofSelected = request.getParameter("o");
		ofertaLaboral = interfazOfertaLaboral.obtenerDtOferta(ofSelected);
		request.setAttribute("oferta", ofertaLaboral);
		Collection<DtPostulacion> listaPostulantes = new ArrayList<>();
		listaPostulantes.addAll(Arrays.asList(interfazOfertaLaboral.obtenerPostulaciones(ofertaLaboral)));
		
		postulante = (DtPostulante) request.getSession().getAttribute("usuario_logueado");
		DtPostulacion postu;
		try {
			//cargaPostulante
			if(interfazUsuario.existePostulacion(ofertaLaboral, postulante)) {
				for(DtPostulacion p: listaPostulantes) {
					if(p.getNicknamePostulante().equals(postulante.getNickname())) {
						postu = p;
						request.setAttribute("Postulante", postu);
					}
				}
				request.getRequestDispatcher("/WEB-INF/ofertas/postulacion.jsp").forward(request, response);
			} else {
				throw new Exception("No existe postulacion del usuario" + postulante.getNickname() + "a la oferta" + ofertaLaboral.getNombre());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
		
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ofSelected = request.getParameter("o");
		String NickPostulante = request.getParameter("post");

		try {
			processRequest(request, response, ofSelected, NickPostulante);
		} catch (ServletException | IOException | NoPoseePostulacionesException_Exception
				| UsuarioNoExisteException_Exception | TipoPubNoExisteException_Exception | OfertaNoExisteException_Exception e) {
			e.printStackTrace();
		}

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
}