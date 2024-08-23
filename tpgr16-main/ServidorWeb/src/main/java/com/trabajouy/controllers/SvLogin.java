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
import com.trabajouy.webservices.DtEmpresa;
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
		EstadoSesion nuevoEstado;

		try {	
			DtUsuario usuario = getUserByEmailOrNickname(emailOrNickname);	
			nuevoEstado = attemptLogin(usuario, password, session, request);
		} catch(Exception error) {
			System.out.print(error.getMessage());
			request.setAttribute("errorMessage", error.getMessage());
			nuevoEstado = EstadoSesion.LOGIN_INCORRECTO;
		}
		
		session.setAttribute("estado_sesion", nuevoEstado);
		
		if (nuevoEstado == EstadoSesion.LOGIN_INCORRECTO) {
		    request.getRequestDispatcher("/WEB-INF/auth/login.jsp").forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + "/home");
		}		
    }
    
    private DtUsuario getUserByEmailOrNickname(String emailOrNickname) throws Exception {
        WebServicesIUsuarioService userService = new WebServicesIUsuarioService();
        WebServicesIUsuario userPort = userService.getWebServicesIUsuarioPort();
        return userPort.obtenerUsuario(emailOrNickname);
    }
    
    private EstadoSesion attemptLogin(DtUsuario usuario, String password, HttpSession session, HttpServletRequest request) {
    	if (usuario.getPassword().equals(password)) {
			if (usuario instanceof DtEmpresa) {
				session.setAttribute("tipo_usuario",TipoUsuario.EMPRESA);
			}else if (usuario instanceof DtPostulante) {
				session.setAttribute("tipo_usuario",TipoUsuario.POSTULANTE);
			}
			session.setAttribute("usuario_logueado", usuario);
			return EstadoSesion.LOGIN_CORRECTO;
		}
    	
    	request.setAttribute("errorMessage", "Contraseña incorrecta!");	
    	return EstadoSesion.LOGIN_INCORRECTO;
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sesion = request.getSession();
		if (sesion.getAttribute("estado_sesion") == EstadoSesion.LOGIN_CORRECTO) {
			request.getRequestDispatcher("/home").forward(request, response);
		} else {
			request.getRequestDispatcher("/WEB-INF/auth/login.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
}
