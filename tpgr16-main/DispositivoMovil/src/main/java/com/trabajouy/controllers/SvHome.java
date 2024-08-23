package com.trabajouy.controllers;

import java.io.IOException;

import com.trabajouy.models.EstadoSesion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet ("/home")
public class SvHome extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SvHome() {
        super();
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
    
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		initSession(request);
		request.setAttribute("vista","home"); // TODO: remove this
		switch(getEstado(request)){
			case NO_LOGIN:
				response.sendRedirect(request.getContextPath() + "/login");
				break;
			case LOGIN_CORRECTO:  	
				request.getRequestDispatcher("ofertas").forward(request, response);
				break;
		}
    		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
