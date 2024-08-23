package com.trabajouy.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

import com.trabajouy.webservices.DtTipoPub;
import com.trabajouy.webservices.DtTipoPubArray;
import com.trabajouy.webservices.TipoPubNoExisteException_Exception;
import com.trabajouy.webservices.WebServicesIOferta;
import com.trabajouy.webservices.WebServicesIOfertaService;

@WebServlet ("/tipos-publicacion")
public class SvTiposPublicacion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public SvTiposPublicacion() {
        super();
    }
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, TipoPubNoExisteException_Exception {
		WebServicesIOfertaService serviceOferta = new WebServicesIOfertaService();
		WebServicesIOferta portOferta = serviceOferta.getWebServicesIOfertaPort();
		
		SvOfertasLaborales.cargaKeyWords(request);
		
		String tipoPubSeleccionada = request.getParameter("tipo");
		
		if (tipoPubSeleccionada != null) {
			DtTipoPub tipoPublicacion = portOferta.obtenerTipoPublicacion(tipoPubSeleccionada);
			request.setAttribute("tipo", tipoPublicacion);
			request.getRequestDispatcher("/WEB-INF/ofertas/tipoPublicacion.jsp").forward(request, response);
		} else {
			try {
				DtTipoPubArray tiposPublicaciones = portOferta.obtenerTiposPublicacion();
				Collection<DtTipoPub> tiposPublicacion = new ArrayList<>();
				tiposPublicacion.addAll(tiposPublicaciones.getItem());
				request.setAttribute("tipos_publicacion", tiposPublicacion);
				request.getRequestDispatcher("/WEB-INF/ofertas/listarTiposPublicacion.jsp").forward(request, response);
			} catch (TipoPubNoExisteException_Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try {
	        processRequest(request, response);
	    } catch (TipoPubNoExisteException_Exception e) {
	        e.printStackTrace();
	        request.setAttribute("error", e.getMessage());
	        //response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request.");
	    }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   try {
	        processRequest(request, response);
	    } catch (TipoPubNoExisteException_Exception e) {
	        e.printStackTrace();
	        request.setAttribute("error", e.getMessage());
	    }
	}
	
}
