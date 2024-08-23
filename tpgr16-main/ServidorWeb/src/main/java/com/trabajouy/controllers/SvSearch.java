package com.trabajouy.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import com.trabajouy.webservices.DtEmpresa;
import com.trabajouy.webservices.DtEmpresaArray;
import com.trabajouy.webservices.DtOferta;
import com.trabajouy.webservices.DtOfertaArray;
import com.trabajouy.webservices.StringArray;
import com.trabajouy.webservices.UsuarioNoExisteException_Exception;
import com.trabajouy.webservices.WebServicesIOferta;
import com.trabajouy.webservices.WebServicesIOfertaService;
import com.trabajouy.webservices.WebServicesIUsuario;
import com.trabajouy.webservices.WebServicesIUsuarioService;


@WebServlet ("/search")
public class SvSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public SvSearch() {
        super();
        
    }
    
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, UsuarioNoExisteException_Exception { 	
    	SvOfertasLaborales.cargaKeyWords(request);
    	
    	WebServicesIOfertaService serviceOferta = new WebServicesIOfertaService();
		WebServicesIOferta portOferta = serviceOferta.getWebServicesIOfertaPort();
		
		WebServicesIUsuarioService serviceUsuario = new WebServicesIUsuarioService();
		WebServicesIUsuario portUsuario = serviceUsuario.getWebServicesIUsuarioPort();
    	
		String orden = request.getParameter("orden");
		String busqueda = request.getParameter("busqueda");
		String keywords = request.getParameter("keywords");
		
		String[] keywordArray = null;
		StringArray keyWordNames = new StringArray();
    	 
    	if (keywords != null) {
  		    keywordArray = keywords.split("_");
  		    keyWordNames.getItem().addAll(Arrays.asList(keywordArray));
  	    }
    	
    	DtOfertaArray ofertas = portOferta.busquedaOfertas(busqueda, keyWordNames);
    	DtEmpresaArray empresas = portUsuario.busquedaEmpresas(busqueda);
    	
    	DtOferta[] listaOfertas = ofertas.getItem().toArray(new com.trabajouy.webservices.DtOferta[0]);
    	DtEmpresa[] listaEmpresas = empresas.getItem().toArray(new com.trabajouy.webservices.DtEmpresa[0]);
    	
    	if ("alfabetico".equals(orden)) { // Ordenar las ofertas y empresas alfabéticamente por nombre
    	    Arrays.sort(listaOfertas, Comparator.comparing(DtOferta::getNombre));
    	    Arrays.sort(listaEmpresas, Comparator.comparing(DtEmpresa::getNombre));
    	}


		request.setAttribute("ofertas", listaOfertas);
		request.setAttribute("empresas", listaEmpresas);
		request.setAttribute("cantidad_resultados", listaOfertas.length + listaEmpresas.length);
		request.getRequestDispatcher("/WEB-INF/search/search.jsp").forward(request, response);
 	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request,response);
		} catch (ServletException | IOException | UsuarioNoExisteException_Exception e) {
			e.printStackTrace();
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request,response);
		} catch (ServletException | IOException | UsuarioNoExisteException_Exception e) {
			e.printStackTrace();
		}
	}

}
