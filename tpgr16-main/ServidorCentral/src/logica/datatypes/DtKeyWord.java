package logica.datatypes;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)

public class DtKeyWord {
	private String nombreKey;
	
	public DtKeyWord(String nombreKey) {
		this.setNombreKey(nombreKey);
	}

	public String getNombreKey() {
		return nombreKey;
	}

	public void setNombreKey(String nombreKey) {
		this.nombreKey = nombreKey;
	}
	
	public String toString() {
        return nombreKey;
	}


}
