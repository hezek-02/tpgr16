package com.trabajouy.controllers;


import jakarta.servlet.Filter;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import com.trabajouy.webservices.WebServicesIOferta;
import com.trabajouy.webservices.WebServicesIOfertaService;

/**
 * Servlet Filter implementation class VisitasFilter
 */
@WebFilter("/consulta-oferta")
public class VisitasFilter extends HttpFilter implements Filter {
       
    private static final long serialVersionUID = 1L;

	public VisitasFilter() {
        super();
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {		
		HttpServletRequest req = (HttpServletRequest) request;
		String ofertaSeleccionada = request.getParameter("oferta");
		
		WebServicesIOfertaService serviceOferta = new WebServicesIOfertaService();
		WebServicesIOferta portOferta = serviceOferta.getWebServicesIOfertaPort(); 
		
		portOferta.visitar(ofertaSeleccionada);
		
		chain.doFilter(req, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {		
	}
}