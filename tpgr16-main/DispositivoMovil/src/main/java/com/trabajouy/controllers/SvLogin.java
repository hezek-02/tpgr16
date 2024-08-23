package com.trabajouy.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import com.trabajouy.models.EstadoSesion;
import com.trabajouy.models.TipoUsuario;
import com.trabajouy.webservices.DtPostulante;
import com.trabajouy.webservices.DtUsuario;
import com.trabajouy.webservices.WebServicesIUsuario;
import com.trabajouy.webservices.WebServicesIUsuarioService;


@WebServlet ("/login")
public class SvLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SvLogin() {
        super();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	HttpSession session = request.getSession();
	    String emailOrNickname = request.getParameter("emailOrNickname");
	    String password = request.getParameter("password");
	    EstadoSesion nuevoEstado = EstadoSesion.NO_LOGIN;

		try {
			DtUsuario usuario = getUserByEmailOrNickname(emailOrNickname);
			nuevoEstado = attemptLogin(usuario, password, session, request);
		} catch(Exception error) {
			request.setAttribute("errorMessage", error.getMessage());
		}
		
		session.setAttribute("estado_sesion", nuevoEstado);
		
		if (nuevoEstado == EstadoSesion.NO_LOGIN) {
		    request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + "/home");
		}
    }
    
    private DtUsuario getUserByEmailOrNickname(String emailOrNickname) throws Exception {
        WebServicesIUsuarioService userService = new WebServicesIUsuarioService();
        WebServicesIUsuario userPort = userService.getWebServicesIUsuarioPort();
        return userPort.obtenerUsuario(emailOrNickname);
    }
    
    private EstadoSesion attemptLogin(DtUsuario user, String password, HttpSession session, HttpServletRequest request) {
        if (user.getPassword().equals(password)) {
            if (user instanceof DtPostulante) {
                session.setAttribute("tipo_usuario", TipoUsuario.POSTULANTE);
                session.setAttribute("usuario_logueado", user);
                return EstadoSesion.LOGIN_CORRECTO;
            }else {
            	request.setAttribute("errorMessage", "El Usuario no existe!");
            }
        }
        return EstadoSesion.NO_LOGIN;
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sesion = request.getSession();
		if (sesion.getAttribute("estado_sesion") == EstadoSesion.LOGIN_CORRECTO) {
			response.sendRedirect(request.getContextPath() + "/home");
		} else {
			request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
}
