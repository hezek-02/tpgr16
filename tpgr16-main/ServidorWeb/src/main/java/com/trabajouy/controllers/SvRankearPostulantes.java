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
import com.trabajouy.webservices.EstadoOferta;
import com.trabajouy.webservices.NoPoseePostulacionesException_Exception;
import com.trabajouy.webservices.OfertaNoExisteException_Exception;
import com.trabajouy.webservices.PaqueteYaCompradoException_Exception;
import com.trabajouy.webservices.PostulacionYaExisteException;
import com.trabajouy.webservices.PostulacionYaExisteException_Exception;
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
@WebServlet ("/rankear")
public class SvRankearPostulantes  extends HttpServlet  {

	private static final long serialVersionUID = 1L;
	private DtEmpresa empresa;
	
	public SvRankearPostulantes() {
        super();
    }
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response, String ofertaSelected) throws ServletException, IOException, TipoPubNoExisteException_Exception, OfertaNoExisteException_Exception {	 

		WebServicesIUsuarioService userService = new WebServicesIUsuarioService();
        WebServicesIUsuario userPort = userService.getWebServicesIUsuarioPort();
        
    	WebServicesIOfertaService service = new WebServicesIOfertaService();
    	WebServicesIOferta port = service.getWebServicesIOfertaPort();
		
		String nomOferta = ofertaSelected;
		
		request.setAttribute("propietario", false);
		request.setAttribute("vencidaConfirmada",false);
		DtOferta ofertaLaboral;
		try {
			
		ofertaLaboral = port.obtenerDtOferta(nomOferta);
		request.setAttribute("ofertaLab", ofertaLaboral);
		//Comprueba si esta para rankear 
		if (ofertaLaboral.getEstado().equals(EstadoOferta.CONFIRMADO) && port.estaVencida(nomOferta))
			request.setAttribute("vencidaConfirmada",true);
		empresa = (DtEmpresa) request.getSession().getAttribute("usuario_logueado");
    	DtEmpresa empPropietaria = userPort.obtenerEmpresaDadoOferta(nomOferta);
		if	(empPropietaria.getNickname().equals(empresa.getNickname()))
			request.setAttribute("propietario", true);
		DtPostulacion[] postulantes = port.obtenerPostulaciones(nomOferta).getItem().toArray(new DtPostulacion[0]);
		request.setAttribute("listaPostulantes", postulantes);
		request.getRequestDispatcher("/WEB-INF/ofertas/rankear.jsp").forward(request, response);
		} catch (OfertaNoExisteException_Exception | TipoPubNoExisteException_Exception | NoPoseePostulacionesException_Exception | UsuarioNoExisteException_Exception e) {
			request.setAttribute("error", "No existen postulaciones");
			request.getRequestDispatcher("/consulta-oferta").forward(request, response);
		}
		
	}
		
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nomOferta = request.getParameter("oferta");

		try {
			processRequest(request, response, nomOferta);
		} catch (ServletException | IOException | TipoPubNoExisteException_Exception | OfertaNoExisteException_Exception e) {
			e.printStackTrace();
		}

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String rank = request.getParameter("rank");
		String ofertaSelected = request.getParameter("ofertaSelected");
		String nickPostulante = request.getParameter("nickPostulante");
		
		if (rank != null && ofertaSelected != null && nickPostulante != null) {
			WebServicesIUsuarioService serviceUsuario = new WebServicesIUsuarioService(); 
			WebServicesIUsuario portUsuario = serviceUsuario.getWebServicesIUsuarioPort(); 
				int nuevoRank = Integer.parseInt(rank);
				try {
					portUsuario.rankearPostulante(ofertaSelected, nickPostulante, nuevoRank);
					request.setAttribute("success", "Se ha asignado un ranking al postulante");
					processRequest(request, response, ofertaSelected);
				} catch (PaqueteYaCompradoException_Exception | RankingYaOcupadoException_Exception
						| UsuarioNoExisteException_Exception | TipoPubNoExisteException_Exception | OfertaNoExisteException_Exception e) {
					request.setAttribute("error", e.getMessage());
					try {
						processRequest(request, response, ofertaSelected);
					} catch (ServletException | IOException | TipoPubNoExisteException_Exception
							| OfertaNoExisteException_Exception e1) {
						e1.printStackTrace();
					}
				}
		}else {
			response.sendRedirect("home.jsp");
		}

	}
}