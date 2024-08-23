package com.trabajouy.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.trabajouy.webservices.DtEmpresaArray;
import com.trabajouy.webservices.DtPostulanteArray;
import com.trabajouy.webservices.DtUsuario;
import com.trabajouy.webservices.WebServicesIUsuario;
import com.trabajouy.webservices.WebServicesIUsuarioService;

@WebServlet ("/usuarios")
public class SvUsuarios extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SvUsuarios() {
        super();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebServicesIUsuarioService serviceUsuario = new WebServicesIUsuarioService(); 
		WebServicesIUsuario portUsuario = serviceUsuario.getWebServicesIUsuarioPort();
		
		SvOfertasLaborales.cargaKeyWords(request);
    	
	    try {
	        DtEmpresaArray empresas = portUsuario.obtenerEmpresas();
	        DtPostulanteArray postulantes = portUsuario.obtenerPostulantes();
	        List<DtUsuario> listaUsuarios = new ArrayList<>(empresas.getItem());
	        listaUsuarios.addAll(postulantes.getItem());
	        request.setAttribute("usuarios", listaUsuarios);
	        request.getRequestDispatcher("/WEB-INF/usuarios/listarUsuarios.jsp").forward(request, response);
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.sendRedirect("WEB-INF/error-pages/500.jsp");
	    }
    }

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
