package logica.manejadores;

import java.util.Map;
import java.util.HashMap;
import logica.datatypes.DtOferta;
import logica.modelos.KeyWord;
import logica.modelos.OfertaLab;

public class ManejadorOfertaLaboral {
    private Map<String, OfertaLab> ofertas;
	private Map<String, KeyWord> colKeyWord;
    private static ManejadorOfertaLaboral instance = null;

    private ManejadorOfertaLaboral() {
        this.ofertas = new HashMap<>();
        this.colKeyWord = new HashMap<>();
    }

    public static ManejadorOfertaLaboral getInstance() {
        if (instance == null) {
            instance = new ManejadorOfertaLaboral();
        }
        return instance;
    }

    public void eliminarOferta(String nombre) {
        this.ofertas.remove(nombre);
    }
    
    public void eliminarOfertas() {
    	this.ofertas.clear();
    }

    public void eliminarKeyWords() {
    	this.colKeyWord.clear();
    }

    public void agregarOferta(OfertaLab oferta) {
        this.ofertas.put(oferta.getNombre(), oferta);
    }

    public OfertaLab obtenerOferta(String nombre) {
        return this.ofertas.get(nombre);
    }

    public DtOferta obtenerDtOferta(String nombre) {
        OfertaLab oferta = this.obtenerOferta(nombre);
        return oferta.obtenerDtOferta();
    }

    public OfertaLab[] obtenerOfertas() {
    	if (this.ofertas.isEmpty()) {
			return null;
		}
		return this.ofertas.values().toArray(new OfertaLab[0]);
    }
    	
    
    public  KeyWord[] obtenerKeyWords() {
        KeyWord[] KeysW = colKeyWord.values().toArray(new KeyWord[0]); //new KeyWord[0] plantilla de datos para el metodo toArray, values() obtiene los objetos del map
        return KeysW;
    }
    	
	public void eliminarKeyWord(String keyName) {
		this.colKeyWord.remove(keyName);

	}

	public void agregarKeyWord(String keyName, KeyWord keyW ) {
		this.colKeyWord.put(keyName, keyW);
	}
	
	
	public boolean existeKeyWord(String keyName) {
		return this.colKeyWord.containsKey(keyName);
	}
	
	public KeyWord obtenerKeyWord(String keyName) {
        if (colKeyWord.get(keyName)==null)
        	return null;
		return colKeyWord.get(keyName);
	}

}
