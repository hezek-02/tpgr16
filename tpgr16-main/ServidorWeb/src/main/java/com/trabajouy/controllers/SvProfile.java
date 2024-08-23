package com.trabajouy.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.trabajouy.webservices.DtEmpresa;
import com.trabajouy.webservices.DtOferta;
import com.trabajouy.webservices.DtOfertaArray;
import com.trabajouy.webservices.DtPostulacion;
import com.trabajouy.webservices.DtPostulante;
import com.trabajouy.webservices.DtUsuario;
import com.trabajouy.webservices.UsuarioNoExisteException_Exception;
import com.trabajouy.webservices.WebServicesIOferta;
import com.trabajouy.webservices.WebServicesIOfertaService;
import com.trabajouy.webservices.WebServicesIUsuario;
import com.trabajouy.webservices.WebServicesIUsuarioService;

@WebServlet ("/profile")
public class SvProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String modo = null;
    private boolean yaSeguido;
    public SvProfile() {
        super(); 
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
		request.getRequestDispatcher("/WEB-INF/usuarios/perfil.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebServicesIUsuarioService serviceUsuario = new WebServicesIUsuarioService(); 
		WebServicesIUsuario portUsuario = serviceUsuario.getWebServicesIUsuarioPort(); 
		
		String nickUsu = request.getParameter("nickname");
		DtUsuario usuario = null;	
		
		modo = (String) request.getParameter("modo");
		if(modo == null) 
			doGet(request, response);
		processRequest(request, response);
		try {
			usuario = portUsuario.obtenerUsuario(nickUsu);
			request.setAttribute("usuPerfil", usuario);
		} catch (UsuarioNoExisteException_Exception e) {
			e.printStackTrace();
		}
    	
    	if (modo != null && modo.equals("seguir") && !yaSeguido) {
			seguirAUsuario(usuario, request, response);
    	}else if (modo != null && modo.equals("dejarDeSeguir") && yaSeguido) {
    		dejarDeSeguir(usuario, request, response);
    	}
    	
    	response.sendRedirect("/ServidorWeb/profile?nickname="+nickUsu);
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SvOfertasLaborales.cargaKeyWords(request);
		
		WebServicesIUsuarioService serviceUsuario = new WebServicesIUsuarioService(); 
		WebServicesIUsuario portUsuario = serviceUsuario.getWebServicesIUsuarioPort(); 
		
		String nickUsu = request.getParameter("nickname");
		DtUsuario usuario = null;
    	try {
			if (portUsuario.esEmpresa(nickUsu)) {
				usuario = portUsuario.obtenerEmpresa(nickUsu);
				handleEmpresaUser((DtEmpresa) usuario, request);
				request.setAttribute("isEmpresa", true);
				request.setAttribute("usuPerfil", usuario);
				
			}else {
				usuario = portUsuario.obtenerPostulante(nickUsu);
				handlePostulanteUser((DtPostulante) usuario, request);
				request.setAttribute("isEmpresa", false);
				request.setAttribute("usuPerfil", usuario);
			}
			
		} catch (UsuarioNoExisteException_Exception e) {
			e.printStackTrace();
		}
    	try {
			obtenerSeguidores(usuario, request, response);
		} catch (UsuarioNoExisteException_Exception e) {
			e.printStackTrace();
		}
		try {
			obtenerSeguidos(usuario, request, response);
		} catch (UsuarioNoExisteException_Exception e) {
			e.printStackTrace();
		}
		yaSeguido = validarSeguimiento(usuario, request, response);
	}
	
	
	private void handleEmpresaUser(DtEmpresa empresa, HttpServletRequest request) throws UsuarioNoExisteException_Exception {
		WebServicesIOfertaService serviceOferta = new WebServicesIOfertaService(); 
		WebServicesIOferta portOferta = serviceOferta.getWebServicesIOfertaPort();
		
		request.setAttribute("ofertasIngresadas", portOferta.obtenerOfertasIngresadas(empresa.getNickname()).getItem());
		request.setAttribute("ofertasRechazadas", portOferta.obtenerOfertasRechazadas(empresa.getNickname()).getItem());
		request.setAttribute("ofertasConfirmadas", portOferta.obtenerOfertasConfirmadas(empresa.getNickname()).getItem());
		request.setAttribute("ofertasFinalizadas", portOferta.obtenerOfertasFinalizadas(empresa.getNickname()).getItem());
		request.setAttribute("paquetesDeEmpresa", portOferta.obtenerPaquetesValidos(empresa.getNickname()).getItem());
	}
	
	private void handlePostulanteUser(DtPostulante postulante, HttpServletRequest request) throws UsuarioNoExisteException_Exception {
		// Crea una instancia del servicio web llamado WebServicesIOfertaService
		WebServicesIOfertaService serviceOferta = new WebServicesIOfertaService(); 
		WebServicesIUsuarioService serviceUsuario = new WebServicesIUsuarioService(); 
		// Obtiene un puerto (endpoint) del servicio web, a través del cual se realizarán las comunicaciones
		WebServicesIOferta portOferta = serviceOferta.getWebServicesIOfertaPort(); 
		WebServicesIUsuario portUsuario = serviceUsuario.getWebServicesIUsuarioPort(); 
		DtOfertaArray listaOfertasPostuladoArray = portOferta.obtenerOfertas(postulante.getNickname());
		List<DtOferta> listaOfertaPostulado = listaOfertasPostuladoArray.getItem();
		request.setAttribute("ofertasPostulado", listaOfertaPostulado);
		
		Collection<DtPostulacion> colPostulaciones = new ArrayList<>();
		for(DtOferta oferta: listaOfertaPostulado) {
			colPostulaciones.add(portUsuario.obtenerPostulacion(oferta.getNombre(), postulante.getNickname()));
		}
		request.setAttribute("postulacionesUsuario", colPostulaciones);
	}
	
	private void obtenerSeguidores(DtUsuario usuario, HttpServletRequest request, HttpServletResponse response) throws UsuarioNoExisteException_Exception {
		WebServicesIUsuarioService serviceUsuario = new WebServicesIUsuarioService(); 
		WebServicesIUsuario portUsuario = serviceUsuario.getWebServicesIUsuarioPort();
		List<DtUsuario> colSeguidores = null;
		colSeguidores = (List<DtUsuario>) portUsuario.obtenerSeguidores(usuario.getNickname()).getItem();
		request.setAttribute("Seguidores", colSeguidores);
	}
	
	private boolean validarSeguimiento(DtUsuario usuario, HttpServletRequest request, HttpServletResponse response) {
		DtUsuario usuarioLog = (DtUsuario) request.getSession().getAttribute("usuario_logueado");
		WebServicesIUsuarioService serviceUsuario = new WebServicesIUsuarioService(); 
		WebServicesIUsuario portUsuario = serviceUsuario.getWebServicesIUsuarioPort();
		boolean yaSigue = false;
		if(usuarioLog != null && usuario != null) {
			try {
				yaSigue = portUsuario.yaSeguido(usuarioLog.getNickname(), usuario.getNickname());
				request.setAttribute("yaSeguido", yaSigue);
			} catch (UsuarioNoExisteException_Exception e) {
				e.printStackTrace();
			}
		}
		request.setAttribute("yaSeguido", yaSigue);
		return yaSigue;
	}
	
	private void obtenerSeguidos(DtUsuario usuario, HttpServletRequest request, HttpServletResponse response) throws UsuarioNoExisteException_Exception {

		WebServicesIUsuarioService serviceUsuario = new WebServicesIUsuarioService(); 
		WebServicesIUsuario portUsuario = serviceUsuario.getWebServicesIUsuarioPort();
		List<DtUsuario> colSeguidos = null;
		colSeguidos = (List<DtUsuario>) portUsuario.obtenerSeguidos(usuario.getNickname()).getItem();
		request.setAttribute("Seguidos", colSeguidos);

	}
	
	private void seguirAUsuario(DtUsuario usuario, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DtUsuario usuarioLog = (DtUsuario) request.getSession().getAttribute("usuario_logueado");
		WebServicesIUsuarioService serviceUsuario = new WebServicesIUsuarioService(); 
		WebServicesIUsuario portUsuario = serviceUsuario.getWebServicesIUsuarioPort();
		if(usuarioLog != null && usuario != null) {
			try {
				portUsuario.agregarSeguidor(usuarioLog.getNickname(), usuario.getNickname());	
			} catch (UsuarioNoExisteException_Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void dejarDeSeguir(DtUsuario usuario, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DtUsuario usuarioLog = (DtUsuario) request.getSession().getAttribute("usuario_logueado");
		WebServicesIUsuarioService serviceUsuario = new WebServicesIUsuarioService(); 
		WebServicesIUsuario portUsuario = serviceUsuario.getWebServicesIUsuarioPort();
		if(usuarioLog != null &&  usuario != null) {
			try {
				portUsuario.eliminarSeguidor(usuarioLog.getNickname(),  usuario.getNickname());	
				yaSeguido = false;
				request.setAttribute("yaSeguido", yaSeguido);
				obtenerSeguidores(usuario, request, response);
				obtenerSeguidos(usuario, request, response);
				request.getRequestDispatcher("/WEB-INF/usuarios/perfil.jsp").forward(request, response);
			} catch (UsuarioNoExisteException_Exception  e) {
				e.printStackTrace();
			}
		}
	}
}

