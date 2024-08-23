package logica.manejadores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import excepciones.PaqueteNoExisteException;
import excepciones.TipoPubNoExisteException;
import excepciones.UsuarioNoExisteException;
import logica.datatypes.DtPaquetePub;
import logica.modelos.CompraPaquete;
import logica.modelos.Empresa;
import logica.modelos.PaqPub;
import logica.modelos.PaqueteTipoPub;

public class ManejadorPaqueteTipoPub {
	private Map<String, PaqueteTipoPub> paquetes;

	private static ManejadorPaqueteTipoPub instance = null;
	

	private ManejadorPaqueteTipoPub() {
		this.paquetes = new HashMap<String, PaqueteTipoPub>();
	}

	public static ManejadorPaqueteTipoPub getInstance(){
		if (instance == null)
			instance = new ManejadorPaqueteTipoPub();
		return instance;
	}

	public void agregarPaqueteTipoPublicacion(PaqueteTipoPub tipo) {
		this.paquetes.put(tipo.getNombrePaq(), tipo);
	}

	public PaqueteTipoPub obtenerPaqueteTipoPub(String nombre) {
		return this.paquetes.get(nombre);
	}

	public PaqueteTipoPub[] obtenerPaquetesTipoPub() {
		return this.paquetes.values().toArray(new PaqueteTipoPub[this.paquetes.size()]); //si se da el largo de los elementos es mas eficiente
	}

	public PaqueteTipoPub[] obtenerPaquetesTipoPubSinComprar() {
        List<PaqueteTipoPub> listaPaquetesNoCompados = new ArrayList<>();
        for (PaqueteTipoPub paq: paquetes.values()) {
			if (!paq.isComprado()) {
				listaPaquetesNoCompados.add(paq);
			}
		}
		return listaPaquetesNoCompados.toArray(new PaqueteTipoPub[listaPaquetesNoCompados.size()]); //menos eficiente se da
	}

	public boolean existePaqueteTipoPub(String nombre) {
		return this.paquetes.containsKey(nombre);
	}

	public void eliminarPaqueteTipoPub(String nombre) {
		this.paquetes.remove(nombre);
	}
	
	public void eliminarPaqutes() {
		for (PaqueteTipoPub paquete : this.paquetes.values()) {
			paquete.eliminarTipos();
		}
		this.paquetes.clear();
	}
	
	public DtPaquetePub obtenerDtPaquete(String nombre) throws PaqueteNoExisteException {
		PaqueteTipoPub paquete = this.obtenerPaqueteTipoPub(nombre);
		if (paquete != null) {
			return paquete.obtenerDtPaq();
		}
		else
			throw new PaqueteNoExisteException("No hay paquete registrado");
	}

	public PaqueteTipoPub obtenerCopiaPaqueteTipoPub(String nombre) {
		PaqueteTipoPub paqCopia = null;
		PaqueteTipoPub paqOriginal = this.obtenerPaqueteTipoPub(nombre); 
		paqCopia = new PaqueteTipoPub(paqOriginal.getNombrePaq(), paqOriginal.getDescripcionPaq(), 
				paqOriginal.getImage(), paqOriginal.getFechaAltaPaq(), paqOriginal.getPeriodoValidezPaq(), 
				paqOriginal.getDescuentoPaquete());
		List<PaqPub> paqPubsOriginal = paqOriginal.obtenerColPaqPub();
		
		for (PaqPub paqPub : paqPubsOriginal) {
			PaqPub paqPubCopia = new PaqPub(paqPub.getCantidad(), paqPub.getTipoPub(), paqCopia);
			paqCopia.setCosto(paqPub.getTipoPub().getCosto(), paqPub.getCantidad());
			paqCopia.agregarTipoPub(paqPubCopia);  
			
		}
		return paqCopia;
	}
	
	public int obtenerCantPaqPubEmpresa(String paquete, String tipoPub, String empresaDt) throws UsuarioNoExisteException, TipoPubNoExisteException {
	    Empresa emp = (Empresa) ManejadorUsuarios.getInstance().obtenerUsuario(empresaDt);
	    CompraPaquete paqueteComprado =  emp.obtenerPaqueteComprado(paquete);
	    return  paqueteComprado.getPaquete().obtenerCantTipoPub(ManejadorTipoPublicacion.getInstance().obtenerDtTipoPublicacion(tipoPub));
	}


}
