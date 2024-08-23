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
    	request.getSession().invalidate();
    	request.getSession().setAttribute("estado_sesion", EstadoSesion.NO_LOGIN);
    	response.sendRedirect("home");
    
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
}
