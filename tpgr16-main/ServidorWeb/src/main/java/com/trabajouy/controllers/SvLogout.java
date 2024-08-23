package com.trabajouy.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.trabajouy.models.EstadoSesion;

@WebServlet ("/logout")
public class SvLogout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SvLogout() {
        super();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String estadoCarga = null;
    	if (request.getSession().getAttribute("carga") == "OK") { // TODO: esto va a ser eliminado eventualmente
    		estadoCarga = "OK";
    	}
    	request.getSession().invalidate();
    	request.getSession().setAttribute("estado_sesion", EstadoSesion.NO_LOGIN);
    	request.getSession().setAttribute("carga", estadoCarga);
    	response.sendRedirect("home");
    
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
}
