package logica;

import logica.controladores.ControladorOfertaLab;
import logica.controladores.ControladorUsuario;
import logica.interfaces.IOfertaLab;
import logica.interfaces.IUsuario;

public class Fabrica {
	private static Fabrica instance = null;
	
	
	private Fabrica() {}
	
	public static Fabrica getInstance(){
		if (instance == null)
			instance = new Fabrica();
		return instance;
	}
	
	public IUsuario getIUsuario() {
		IUsuario Iusuario = new ControladorUsuario();
		return  Iusuario;
	}
	
	public IOfertaLab getIOfertaLaboral() {
		IOfertaLab Ioferta = new ControladorOfertaLab();
		return Ioferta;
	}
	
}
