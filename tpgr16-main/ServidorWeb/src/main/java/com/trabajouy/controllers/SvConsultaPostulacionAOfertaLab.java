package com.trabajouy.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.trabajouy.models.TipoUsuario;
import com.trabajouy.webservices.DtEmpresa;
import com.trabajouy.webservices.DtOferta;
import com.trabajouy.webservices.DtPostulacion;
import com.trabajouy.webservices.DtPostulante;
import com.trabajouy.webservices.DtUsuario;
import com.trabajouy.webservices.NoPoseePostulacionesException_Exception;
import com.trabajouy.webservices.OfertaNoExisteException_Exception;
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
@WebServlet ("/consulta-postu")
public class SvConsultaPostulacionAOfertaLab extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DtEmpresa empresa;
	private DtPostulante postulante;

	public SvConsultaPostulacionAOfertaLab() {
        super();
    }
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, TipoPubNoExisteException_Exception, NoPoseePostulacionesException_Exception, UsuarioNoExisteException_Exception, OfertaNoExisteException_Exception { 
		SvOfertasLaborales.cargaKeyWords(request);
		
		WebServicesIOfertaService serviceOferta = new WebServicesIOfertaService(); 
		WebServicesIUsuarioService serviceUsuario = new WebServicesIUsuarioService(); 

		WebServicesIOferta portOferta = serviceOferta.getWebServicesIOfertaPort(); 
		WebServicesIUsuario portUsuario = serviceUsuario.getWebServicesIUsuarioPort(); 
		
		if(request.getParameter("ofertaPostulantes") == null && request.getSession().getAttribute("tipo_usuario") == TipoUsuario.EMPRESA) {
			processCargaEmpresa(request,response);
		} else if(request.getSession().getAttribute("tipo_usuario").equals(TipoUsuario.EMPRESA)) {
			String ofSelected = request.getParameter("ofertaPostulantes");
			DtOferta ofertaLaboral = portOferta.obtenerDtOferta(ofSelected);
			request.setAttribute("ofertaL", ofertaLaboral);
			DtPostulacion[] postulaciones = portOferta.obtenerPostulaciones(ofertaLaboral.getNombre()).getItem().toArray(new DtPostulacion[0]);;
			if(postulaciones != null && postulaciones.length > 0) {
				Collection<DtUsuario> postulantes= new ArrayList<DtUsuario>();
				for (DtPostulacion postus: postulaciones) {
					postulantes.add(portUsuario.obtenerUsuario(postus.getNicknamePostulante()));
				}
				request.setAttribute("usuarios", postulantes);
				request.getRequestDispatcher("/WEB-INF/usuarios/listarUsuarios.jsp").forward(request, response);
			}else{
				request.setAttribute("error","No existen postulaciones a la oferta laboral");
				request.getRequestDispatcher("/home").forward(request, response);
			}
		}
		if (request.getSession().getAttribute("tipo_usuario") == TipoUsuario.POSTULANTE) {
			DtPostulante postulante = (DtPostulante) request.getSession().getAttribute("usuario_logueado");
			Collection<DtOferta> listaOL = new ArrayList<>();
			DtEmpresa[] empresas = portUsuario.obtenerEmpresas().getItem().toArray(new DtEmpresa[0]);
			request.setAttribute("vista", "postulante_ofertas");
			for (DtEmpresa emp: empresas) {
				DtOferta[] ofertas = portOferta.obtenerOfertasConfirmadas(emp.getNickname()).getItem().toArray(new DtOferta[0]);
				for (DtOferta oferta: ofertas) {
					if (portUsuario.existePostulacion(oferta.getNombre(), postulante.getNickname())) {
						listaOL.add(oferta);
					}
				}
			}
			DtOferta[] arrayDtOferta = listaOL.toArray(new DtOferta[0]);
			if(arrayDtOferta != null && arrayDtOferta.length > 0) {
				request.setAttribute("ofertas", arrayDtOferta);
				request.getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
			}else {
				request.setAttribute("error", "El postulante no posee ninguna postulación");
				request.getRequestDispatcher("/home").forward(request, response);
			}

		}
	}

	private void processCargaEmpresa(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Crea una instancia del servicio web llamado WebServicesIOfertaService
		WebServicesIOfertaService serviceOferta = new WebServicesIOfertaService(); 
		WebServicesIUsuarioService serviceUsuario = new WebServicesIUsuarioService(); 

		// Obtiene un puerto (endpoint) del servicio web, a través del cual se realizarán las comunicaciones
		WebServicesIOferta portOferta = serviceOferta.getWebServicesIOfertaPort(); 
		WebServicesIUsuario portUsuario = serviceUsuario.getWebServicesIUsuarioPort(); 
		empresa = (DtEmpresa) request.getSession().getAttribute("usuario_logueado");
		try {
			//cargaOL
			request.setAttribute("vista", "empresa_ofertas");
			DtOferta[] listaOL = null;
			listaOL = portOferta.obtenerOfertasConfirmadas(empresa.getNickname()).getItem().toArray(new DtOferta[0]);
			
			if(listaOL != null && listaOL.length > 0 ) {
				request.setAttribute("ofertas", listaOL);
				request.getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
			}

		} catch (Exception e) {
			request.setAttribute("error", "La empresa no posee ofertas confirmadas");
			request.getRequestDispatcher("/home").forward(request, response);
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			try {
				processRequest(request, response);
			} catch (ServletException | IOException | TipoPubNoExisteException_Exception
					| NoPoseePostulacionesException_Exception | UsuarioNoExisteException_Exception | OfertaNoExisteException_Exception e) {
				request.setAttribute("error", e.getMessage());
				request.getRequestDispatcher("/home").forward(request, response);
			}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			try {
				processRequest(request, response);
			} catch (ServletException | IOException | TipoPubNoExisteException_Exception
					| NoPoseePostulacionesException_Exception | UsuarioNoExisteException_Exception | OfertaNoExisteException_Exception e) {
				request.setAttribute("error", e.getMessage());
				request.getRequestDispatcher("/home").forward(request, response);
			}

	}
}