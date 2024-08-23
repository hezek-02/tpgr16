package com.trabajouy.controllers;

import com.trabajouy.models.TipoUsuario;
import com.trabajouy.webservices.DtCompraPaquete;
import com.trabajouy.webservices.DtEmpresa;
import com.trabajouy.webservices.DtPaqPub;
import com.trabajouy.webservices.DtPaqPubArray;
import com.trabajouy.webservices.DtPaquetePub;
import com.trabajouy.webservices.DtPaquetePubArray;
import com.trabajouy.webservices.DtUsuario;
import com.trabajouy.webservices.PaqueteNoExisteException_Exception;
import com.trabajouy.webservices.PaqueteYaCompradoException_Exception;
import com.trabajouy.webservices.TipoPubNoExisteException_Exception;
import com.trabajouy.webservices.UsuarioNoExisteException_Exception;
import com.trabajouy.webservices.WebServicesIOferta;
import com.trabajouy.webservices.WebServicesIOfertaService;
import com.trabajouy.webservices.WebServicesIUsuario;
import com.trabajouy.webservices.WebServicesIUsuarioService;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/paquetes")
public class SvPaquetes extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public SvPaquetes() {
		super();
	}

	public void initSession(HttpServletRequest request) {
		String session = (String)request.getSession().getAttribute("sesion");
		if (session == null) {
			request.getSession().setAttribute("sesion", "NO_LOGIN");
		}
	}	

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException, UsuarioNoExisteException_Exception, TipoPubNoExisteException_Exception, PaqueteNoExisteException_Exception {
		WebServicesIOfertaService serviceOferta = new WebServicesIOfertaService();
		WebServicesIOferta portOferta = serviceOferta.getWebServicesIOfertaPort();
		
		SvOfertasLaborales.cargaKeyWords(request);
			
		String paquete = request.getParameter("paquete");
		
		if (paquete == null) {
			try {
				DtPaquetePubArray paquetes = portOferta.obtenerPaquetesRegistrados();
				DtPaquetePub[] paqs = paquetes.getItem().toArray(new DtPaquetePub[0]);
				request.setAttribute("paquetes", paqs);
				request.getRequestDispatcher("/WEB-INF/paquetes/listarPaquetes.jsp").forward(request, response);
			} catch (PaqueteNoExisteException_Exception e) {
				e.printStackTrace();
			}	
		}else {
			DtPaquetePub paquetePub = portOferta.obtenerPaquete(paquete);
			
			DtPaqPubArray paqPubs = portOferta.obtenerPublicacionesPaquetes(paquete);
			Collection<DtPaqPub> tiposEnPaquete = new ArrayList<>();
			tiposEnPaquete.addAll(paqPubs.getItem());
			request.setAttribute("tiposEnPaquete", tiposEnPaquete);
			
			DtUsuario usu = (DtUsuario) request.getSession().getAttribute("usuario_logueado");
			if (usu != null && request.getSession().getAttribute("tipo_usuario").equals(TipoUsuario.EMPRESA)) {
				DtEmpresa empresa = (DtEmpresa) usu;
				DtPaquetePub[] paquetes = portOferta.obtenerPaquetesValidos(empresa.getNickname()).getItem().toArray(new DtPaquetePub[0]);
				for (DtPaquetePub paq : paquetes) {
					if (paquetePub.getNombre().equals(paq.getNombre())) {
						request.setAttribute("comprado", true);
					}
				}
				obtenerCompra(usu, paquetePub, request, response);
				
			}
			request.setAttribute("paquete", paquetePub);
			request.getRequestDispatcher("/WEB-INF/paquetes/consultaPaquete.jsp").forward(request, response);
		}
	}
	
	private void obtenerCompra(DtUsuario usu, DtPaquetePub paquetePub, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebServicesIUsuarioService serviceUsuario = new WebServicesIUsuarioService();
		WebServicesIUsuario portUsuario = serviceUsuario.getWebServicesIUsuarioPort();
		DtCompraPaquete compra = null;
		try {
			compra = portUsuario.obtenerCompra(usu.getNickname(), paquetePub.getNombre());
		} catch (PaqueteNoExisteException_Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("InfoCompra", compra);
		request.setAttribute("paquete", paquetePub);
		request.getRequestDispatcher("/WEB-INF/paquetes/consultaPaquete.jsp").forward(request, response);
	}
	
	private void comprar(HttpServletRequest request, HttpServletResponse response, String paqueteSeleccionado) throws  PaqueteNoExisteException_Exception, UsuarioNoExisteException_Exception, ServletException, IOException, TipoPubNoExisteException_Exception {
		WebServicesIOfertaService serviceOferta = new WebServicesIOfertaService(); 
		WebServicesIUsuarioService serviceUsuario = new WebServicesIUsuarioService(); 

		WebServicesIOferta portOferta = serviceOferta.getWebServicesIOfertaPort(); 
		WebServicesIUsuario portUsuario = serviceUsuario.getWebServicesIUsuarioPort(); 

		System.out.println(paqueteSeleccionado);
		DtPaquetePub paquete = portOferta.obtenerPaquete(paqueteSeleccionado);
		DtEmpresa empresa = (DtEmpresa) request.getSession().getAttribute("usuario_logueado");
		try {
			portUsuario.comprarPaquete(empresa.getNickname(), paquete.getNombre()); //Falta agregar la operacion al webservice de Usuario
			request.getRequestDispatcher("home").forward(request, response);
		}catch(PaqueteYaCompradoException_Exception e) {
			request.setAttribute("error", e.getMessage());
			processRequest(request, response);
			e.printStackTrace();
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (PaqueteNoExisteException_Exception | ServletException | IOException | UsuarioNoExisteException_Exception | TipoPubNoExisteException_Exception e) {
			e.printStackTrace();
			
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String paqueteSeleccionado = request.getParameter("paquete");
		String action = (String) request.getParameter("accion");
		if (action != null && action.equals("comprar") && paqueteSeleccionado != null) {
				try {
					comprar(request, response, paqueteSeleccionado);
				} catch (PaqueteNoExisteException_Exception | UsuarioNoExisteException_Exception | ServletException
						| IOException | TipoPubNoExisteException_Exception e) {
					e.printStackTrace();
				}
		}
	}
}
