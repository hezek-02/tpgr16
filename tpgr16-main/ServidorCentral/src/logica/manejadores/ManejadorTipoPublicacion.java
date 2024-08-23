package logica.manejadores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import excepciones.TipoPubNoExisteException;
import logica.datatypes.DtTipoPub;
import logica.modelos.PaqPub;
import logica.modelos.TipoPublicacion;

public class ManejadorTipoPublicacion {
	private Map<String, TipoPublicacion> tipos;

	private static ManejadorTipoPublicacion instance = null;
	

	private ManejadorTipoPublicacion() {
		this.tipos = new HashMap<String, TipoPublicacion>();
	}

	public static ManejadorTipoPublicacion getInstance(){
		if (instance == null)
			instance = new ManejadorTipoPublicacion();
		return instance;
	}

	public void agregarTipoPublicacion(TipoPublicacion tipo) {
		this.tipos.put(tipo.getNombre(), tipo);
	}

	public TipoPublicacion obtenerTipoPublicacion(String nombre) {
		return this.tipos.get(nombre);
	}

	public TipoPublicacion[] obtenerTiposPublicaciones() {
		return this.tipos.values().toArray(new TipoPublicacion[this.tipos.size()]);
	}
	
	public TipoPublicacion[] obtenerTiposPublicacionesQueNoEstanEnPaquete(String paq) {
        List<TipoPublicacion> listaTiposPubsDePaquete = new ArrayList<>();
		for (TipoPublicacion tipoPub: tipos.values()) {
			List<PaqPub> paqPubLista = tipoPub.obtenerListaPaqPub();
			boolean estaEnPaquete = false;
			for (PaqPub paqPubItem : paqPubLista) {
				if (paqPubItem.getDtPaqPub().getNombrePaquete().equals(paq)) {
					estaEnPaquete = true;
				}
			}
			if (!estaEnPaquete) {
				listaTiposPubsDePaquete.add(tipoPub);
			}
		}
		return listaTiposPubsDePaquete.toArray(new TipoPublicacion[listaTiposPubsDePaquete.size()]);
	}

	
	public TipoPublicacion[] obtenerTiposPublicaciones(String paquete) {
        List<TipoPublicacion> listaTiposPubsDePaquete = new ArrayList<>();
		for (TipoPublicacion tipoPub: tipos.values()) {
			List<PaqPub> paqPubLista = tipoPub.obtenerListaPaqPub();
			boolean estaEnPaquete = false;
			for (PaqPub paqPubItem : paqPubLista) {
				if (paqPubItem.getDtPaqPub().getNombrePaquete().equals(paquete)) {
					listaTiposPubsDePaquete.add(tipoPub);
				}
			}
		}
		return listaTiposPubsDePaquete.toArray(new TipoPublicacion[listaTiposPubsDePaquete.size()]);
	}


	public boolean existeTipoPublicacion(String nombre) {
		return this.tipos.containsKey(nombre);
	}

	public void eliminarTipoPublicacion(String nombre) {
		this.tipos.remove(nombre);
	}

	public void eliminarTiposPublicaciones() {
		this.tipos.clear();
	}
	
	public DtTipoPub obtenerDtTipoPublicacion(String nombre) throws TipoPubNoExisteException{
		TipoPublicacion tipo = this.obtenerTipoPublicacion(nombre);
		if (tipo==null)
			throw new TipoPubNoExisteException("No existe tipo publicacion!");
		return tipo.getDtTipoPublicacion();
	}
}
