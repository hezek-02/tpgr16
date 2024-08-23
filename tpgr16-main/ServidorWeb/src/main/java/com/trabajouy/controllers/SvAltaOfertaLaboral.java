package com.trabajouy.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.*;
import java.util.Arrays;

import com.trabajouy.models.TipoUsuario;
import com.trabajouy.webservices.CostoMayorACeroExcepcion_Exception;
import com.trabajouy.webservices.DtEmpresa;
import com.trabajouy.webservices.DtPaquetePub;
import com.trabajouy.webservices.DtTipoPub;
import com.trabajouy.webservices.FechaInvalidaException_Exception;
import com.trabajouy.webservices.OfertaLaboralYaExisteException_Exception;
import com.trabajouy.webservices.PaqueteNoExisteException_Exception;
import com.trabajouy.webservices.PaqueteNoTieneMasTipoPubExcepcion_Exception;
import com.trabajouy.webservices.StringArray;
import com.trabajouy.webservices.TipoPubNoExisteException_Exception;
import com.trabajouy.webservices.UsuarioNoExisteException_Exception;
import com.trabajouy.webservices.WebServicesIOferta;
import com.trabajouy.webservices.WebServicesIOfertaService;


@WebServlet ("/alta-oferta")
@MultipartConfig
public class SvAltaOfertaLaboral extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private DtEmpresa empresaDt;
    public SvAltaOfertaLaboral() {
        super();
    }
    
    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, TipoPubNoExisteException_Exception  {
    	if (req.getSession().getAttribute("tipo_usuario") != TipoUsuario.EMPRESA) {
			//solo empresas pueden dar de alta ofertas
            resp.sendRedirect(req.getContextPath() + "/home");
		}
		
		empresaDt = (DtEmpresa) req.getSession().getAttribute("usuario_logueado");
		cargarParametrosDeOferta(req, resp);
		
		req.getRequestDispatcher("/WEB-INF/ofertas/altaOfertaLaboral.jsp").forward(req, resp);
	}

    private void cargarParametrosDeOferta(HttpServletRequest req, HttpServletResponse resp) throws TipoPubNoExisteException_Exception {
    	SvOfertasLaborales.cargaKeyWords(req);
    	SvOfertasLaborales.cargaTiposPublicacion(req);
	}

    private void altaOferta(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException , PaqueteNoExisteException_Exception, TipoPubNoExisteException_Exception {
    	WebServicesIOfertaService service = new WebServicesIOfertaService();
    	WebServicesIOferta port = service.getWebServicesIOfertaPort();
    	
  		String nombre = request.getParameter("nombre");

  	    String descripcion = request.getParameter("descripcion");

  	    String ciudad = request.getParameter("ciudad");

  	    String departamento = request.getParameter("departamento");

  	    String horarioDesde = request.getParameter("horaDesde");

  	    String horarioHasta = request.getParameter("horaHasta");

  	    String horario = horarioDesde + ":" + horarioHasta;

  	    String remuneracionStr = request.getParameter("remuneracion");

  	    Float remuneracion = Float.parseFloat(remuneracionStr);

  		//hallar tipoPub
  		
  		String tipoPub = request.getParameter("seleccionTipoPub");
  		if (tipoPub.isBlank()){
	    	request.setAttribute("error","ATENCION: No se ha seleccionado un tipo de publicación");
	    	request.getRequestDispatcher("alta-oferta").forward(request, response);
	    	processRequest(request,response);

  		}
  		DtTipoPub tipoPubDt = port.obtenerTipoPublicacion(tipoPub);

	    String[] keyWords = request.getParameterValues("seleccionKeyWords");
  		//hallar paquete (si corresponde)
  		String paquete = request.getParameter("Paquete");
  		
  		DtPaquetePub paqueteDt = null;
  		if (paquete != null){
  			paqueteDt  = port.obtenerPaquete(paquete);
  		}
	  	//almacenar imágen, (si trae)
  		Part imgPart = request.getPart("imagen");//archivo(imagen) por partes

		 // Lee el contenido del archivo y lo convierte a bytes
		InputStream inputStream = imgPart.getInputStream();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int bytesRead;
		 
		while ((bytesRead = inputStream.read(buffer)) != -1) {
		    outputStream.write(buffer, 0, bytesRead);
		}
		 
		byte[] imagenBytes = outputStream.toByteArray();
	    try {
	    	StringArray keysArray = new StringArray();
	    	if (keyWords != null) {
	    		keysArray.getItem().addAll(Arrays.asList(keyWords));
	    	}
	    	
			if (paqueteDt != null) {
				port.ingresarDatosOfertaLaboralConPaquete(nombre, descripcion, imagenBytes, horario, remuneracion, ciudad, 
						departamento, keysArray, empresaDt.getNickname(), tipoPubDt.getNombre(), paqueteDt.getNombre());
			}else {
		    	port.ingresarDatosOfertaLaboralSinPaquete(nombre, descripcion, imagenBytes, horario, remuneracion, ciudad,
		    			departamento, keysArray, empresaDt.getNickname(), tipoPubDt.getNombre());
			}
	    	request.setAttribute("success","Se ha ingresado la oferta laboral con éxito, en breves sera Confirmada o Rechazada");
	    	processRequest(request, response);

	    } catch (CostoMayorACeroExcepcion_Exception | FechaInvalidaException_Exception
				| OfertaLaboralYaExisteException_Exception | PaqueteNoTieneMasTipoPubExcepcion_Exception
				| TipoPubNoExisteException_Exception | UsuarioNoExisteException_Exception e) {
	    	request.setAttribute("error", e.getMessage());
			e.printStackTrace();
		}
	    processRequest(request, response);
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		String tipoPubSeleccionado = request.getParameter("tipoPub");
  		
	    if (tipoPubSeleccionado!=null) {
	    	try {
	    		request.setAttribute("tipoPubSelected", tipoPubSeleccionado);
				SvOfertasLaborales.cargarPaquetesEmpresa(request, empresaDt,tipoPubSeleccionado);
			} catch ( UsuarioNoExisteException_Exception | TipoPubNoExisteException_Exception e) {
		    	request.setAttribute("error", e.getMessage());
				e.printStackTrace();
			}
	    }
			try {
				processRequest(request,response);
			} catch (ServletException | IOException | TipoPubNoExisteException_Exception e) {
				e.printStackTrace();
			}
	}
		

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String action = (String) request.getParameter("accion");
	    if (action!=null && action.equals("alta")) {
				try {
					altaOferta(request,response);
				} catch (IOException | ServletException | PaqueteNoExisteException_Exception
						| TipoPubNoExisteException_Exception e) {
					e.printStackTrace();
				}

	    } else {
				try {
					processRequest(request,response);
				} catch (ServletException | IOException | TipoPubNoExisteException_Exception e) {
					e.printStackTrace();
				}

	    }
	}
}
