package com.trabajouy.controllers;

import java.io.IOException;
import java.util.Arrays;

import com.trabajouy.webservices.DtEmpresa;
import com.trabajouy.webservices.DtKeyWord;
import com.trabajouy.webservices.DtKeyWordArray;
import com.trabajouy.webservices.DtOferta;
import com.trabajouy.webservices.DtOfertaArray;
import com.trabajouy.webservices.DtPaquetePub;
import com.trabajouy.webservices.DtPaquetePubArray;
import com.trabajouy.webservices.DtTipoPub;
import com.trabajouy.webservices.DtTipoPubArray;
import com.trabajouy.webservices.EstadoOferta;
import com.trabajouy.webservices.StringArray;
import com.trabajouy.webservices.TipoPubNoExisteException_Exception;
import com.trabajouy.webservices.UsuarioNoExisteException_Exception;
import com.trabajouy.webservices.WebServicesIOferta;
import com.trabajouy.webservices.WebServicesIOfertaService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet ("/ofertas")
public class SvOfertasLaborales extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String[] keywordArray;
	
    public SvOfertasLaborales() {
        super();
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, UsuarioNoExisteException_Exception {
		String ofertaFinalizar = req.getParameter("finalizarOferta");
		if (ofertaFinalizar != null)
			finalizarOferta(req,resp,ofertaFinalizar);
		listarOfertas(req, resp);
	}
    
    //carga de keywords
    public static void cargaKeyWords(HttpServletRequest req) {	
    	WebServicesIOfertaService service = new WebServicesIOfertaService();
    	WebServicesIOferta port = service.getWebServicesIOfertaPort();
    	DtKeyWordArray listaKeysArray = port.obtenerKeyWords();
    	DtKeyWord[] listaKeys = listaKeysArray.getItem().toArray(new DtKeyWord[0]);
		req.setAttribute("keys", listaKeys);
    }
    
    //carga de tipos publicacion
    public static void cargaTiposPublicacion(HttpServletRequest req) throws  TipoPubNoExisteException_Exception {	
    	WebServicesIOfertaService service = new WebServicesIOfertaService();
    	WebServicesIOferta port = service.getWebServicesIOfertaPort();
    	DtTipoPubArray listaTiposPubArray = port.obtenerTiposPublicacion();
    	DtTipoPub[] listaTiposPub = listaTiposPubArray.getItem().toArray(new DtTipoPub[0]);
		req.setAttribute("tiposPub", listaTiposPub);
    }
    
    //carga de paquetes comprados por empresa
    public static void cargarPaquetesEmpresa(HttpServletRequest req,DtEmpresa empresaDt,String tipoPub) throws UsuarioNoExisteException_Exception, TipoPubNoExisteException_Exception {
    	WebServicesIOfertaService service = new WebServicesIOfertaService();
    	WebServicesIOferta port = service.getWebServicesIOfertaPort();
    	DtPaquetePubArray listaPaquetesArray = port.obtenerPaquetes(empresaDt.getNickname(), tipoPub);
    	DtPaquetePub[] listaPaquetes = listaPaquetesArray.getItem().toArray(new DtPaquetePub[0]);
		req.setAttribute("paquetes", listaPaquetes);
    }
    
    public void finalizarOferta(HttpServletRequest request,HttpServletResponse response, String ofertaFinalizar) {
    	WebServicesIOfertaService service = new WebServicesIOfertaService();
    	WebServicesIOferta port = service.getWebServicesIOfertaPort();
    	port.actualizarEstadoDeOferta(ofertaFinalizar, EstadoOferta.FINALIZADA);
    }
    
    public void listarOfertas(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, UsuarioNoExisteException_Exception {
    	cargaKeyWords(request);
    	
    	WebServicesIOfertaService service = new WebServicesIOfertaService();
    	WebServicesIOferta port = service.getWebServicesIOfertaPort();
    	
    	DtOfertaArray ofertas = null;
    	
    	if (keywordArray != null) {
    		StringArray keyWordNames = new StringArray();
    		keyWordNames.getItem().addAll(Arrays.asList(keywordArray));
    		ofertas = port.obtenerOfertasConfirmadasConFiltro(keyWordNames); //filtro keyWords
    	}else
    		ofertas = port.obtenerOfertasConfirmadasSinFiltro(); //sin filtro
    	
    	DtOferta[] listaOfertas = ofertas.getItem().toArray(new DtOferta[0]);
		request.setAttribute("ofertas", listaOfertas);
		request.getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String keywords = request.getParameter("keywords");
	    if (keywords != null) {
		    keywordArray = keywords.split("_");
	    }else {
	    	keywordArray = null;
	    }
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
