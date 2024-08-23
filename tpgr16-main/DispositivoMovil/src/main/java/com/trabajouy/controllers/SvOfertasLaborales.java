package com.trabajouy.controllers;

import java.io.IOException;
import java.util.Arrays;

import com.trabajouy.models.EstadoSesion;
import com.trabajouy.webservices.DtEmpresa;
import com.trabajouy.webservices.DtEmpresaArray;
import com.trabajouy.webservices.DtKeyWord;
import com.trabajouy.webservices.DtKeyWordArray;
import com.trabajouy.webservices.DtOferta;
import com.trabajouy.webservices.DtOfertaArray;
import com.trabajouy.webservices.DtPostulante;
import com.trabajouy.webservices.EstadoOferta;
import com.trabajouy.webservices.StringArray;
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

@WebServlet ("/ofertas")
public class SvOfertasLaborales extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private WebServicesIOferta portOferta;
	private WebServicesIUsuario portUsuario;
	
    public SvOfertasLaborales() {
    	super();
        WebServicesIOfertaService service = new WebServicesIOfertaService();
        portOferta = service.getWebServicesIOfertaPort();
        
    	WebServicesIUsuarioService serviceUsuario = new WebServicesIUsuarioService();
		portUsuario = serviceUsuario.getWebServicesIUsuarioPort();
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
    
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, UsuarioNoExisteException_Exception, TipoPubNoExisteException_Exception {
    	initSession(request);
    	switch(getEstado(request)){
			case NO_LOGIN:
				request.getRequestDispatcher("login").forward(request, response);
				break;
			case LOGIN_CORRECTO:
				String ofertaNombre = (String) request.getParameter("oferta");
				if (ofertaNombre != null) {
					consultaOferta(request, response, ofertaNombre);
				} else {
					listarOfertas(request, response);
				}
				break;
    	}
	}
    
    public void consultaOferta(HttpServletRequest request, HttpServletResponse response, String ofertaNombre) throws ServletException, IOException {
        try {
            DtOferta oferta = portOferta.obtenerDtOferta(ofertaNombre);
            DtEmpresa empresa = portUsuario.obtenerEmpresaDadoOferta(oferta.getNombre());
            request.setAttribute("empresaDeOferta", empresa);
            request.setAttribute("vencida", portOferta.estaVencida(oferta.getNombre()));
            validarPostulacion(request, response, oferta);
            request.setAttribute("oferta", oferta);
            request.getRequestDispatcher("/WEB-INF/oferta.jsp").forward(request, response);
        } catch (TipoPubNoExisteException_Exception | UsuarioNoExisteException_Exception error) {
        	error.printStackTrace();
            request.setAttribute("errorMessage", error.getMessage());
            request.getRequestDispatcher("/WEB-INF/oferta.jsp").forward(request, response);
        }
    }
    
    public void listarOfertas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
    		String ofertaFinalizar = request.getParameter("finalizarOferta");   
        	
    		if (ofertaFinalizar != null) {
    			finalizarOferta(request,response,ofertaFinalizar);
    		}
    		
    		// obtener empresas para listar en dropdown
    		DtEmpresaArray empresas = portUsuario.obtenerEmpresas();
    		DtEmpresa[] listaEmpresas = empresas.getItem().toArray(new DtEmpresa[0]);
    		request.setAttribute("empresas", listaEmpresas);
    		
    		// obtener keywords para listar en dropdown
    		DtKeyWordArray listaKeysArray = portOferta.obtenerKeyWords();
        	DtKeyWord[] listaKeys = listaKeysArray.getItem().toArray(new DtKeyWord[0]);
    		request.setAttribute("keywords", listaKeys);
    		
    		String empresa = (String) request.getParameter("empresa");
    		String keyword = (String) request.getParameter("keyword");
    		
    		DtOfertaArray ofertas;
    		StringArray keywordName = new StringArray();
    		
    		if (keyword != null) {
    			String[] keywordArray = new String[] { keyword };
        		keywordName.getItem().addAll(Arrays.asList(keywordArray));
    		}
    		
    		if (empresa != null && keyword != null) {
        		ofertas = portOferta.obtenerOfertasConfirmadasPorEmpresaYKeywords(empresa, keywordName);
    					
    		} else if (empresa != null) {
    			ofertas = portOferta.obtenerOfertasConfirmadas(empresa);
            	
    		} else if (keyword != null) {
        		ofertas = portOferta.obtenerOfertasConfirmadasConFiltro(keywordName); // filtro keyWord unica
        		
    		} else {
    			ofertas = portOferta.obtenerOfertasConfirmadasSinFiltro(); //sin filtro
    		}
        	 
    		DtOferta[] listaOfertas = ofertas.getItem().toArray(new DtOferta[0]);
    		request.setAttribute("ofertas", listaOfertas);
    		request.getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
    		
    	} catch (UsuarioNoExisteException_Exception error) {
    		error.printStackTrace();
            request.setAttribute("errorMessage", error.getMessage());
            request.getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
    	}
    }
    
    public void validarPostulacion(HttpServletRequest request, HttpServletResponse response, DtOferta oferta) {
    	try {
        	request.setAttribute("no_postulado", true);
        	DtPostulante postulante = (DtPostulante) request.getSession().getAttribute("usuario_logueado");
    		if (portUsuario.existePostulacion(oferta.getNombre(), postulante.getNickname())) {
    			request.setAttribute("errorMessage", "Ya existe esta postulacion");
    			request.setAttribute("no_postulado", false);
    		}else if ((boolean) request.getAttribute("vencida")){
    			request.setAttribute("errorMessage", "La oferta se encuentra vencida, no es pósible postularse");
    		}
    	} catch (UsuarioNoExisteException_Exception error) {
    		error.printStackTrace();
            request.setAttribute("errorMessage", error.getMessage());
    	}
    }
    
    public void finalizarOferta(HttpServletRequest request,HttpServletResponse response, String ofertaFinalizar) {
    	portOferta.actualizarEstadoDeOferta(ofertaFinalizar, EstadoOferta.FINALIZADA);
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request,response);
		} catch (ServletException | IOException | UsuarioNoExisteException_Exception | TipoPubNoExisteException_Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request,response);
		} catch (ServletException | IOException | UsuarioNoExisteException_Exception | TipoPubNoExisteException_Exception e) {
			e.printStackTrace();
		}
	}
}

