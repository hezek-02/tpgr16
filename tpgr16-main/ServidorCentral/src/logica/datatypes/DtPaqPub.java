package logica.datatypes;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class DtPaqPub {

	private String tipoPub;
	private int cantidad;
	private String paqueteTipoPub;
	private float costeDescuento;
	
	public DtPaqPub(String paqueteTipoPub, String tipoPub, int cantidad) {
		this.cantidad= cantidad;
		this.paqueteTipoPub = paqueteTipoPub;
		this.tipoPub = tipoPub;
	}
	
	public String getNombrePaquete() {
		return paqueteTipoPub;
	}
	
	public String getNombreTipoPub() {
		return tipoPub;
	}
	
	public int getCantidad() {
		return cantidad;
	}
	

	
	@Override
	public String toString() {
		return getNombreTipoPub()+ " (cant:"+getCantidad()+") (costo c/desc:"+getCosteDescuento()+")";
	}

	public float getCosteDescuento() {//descuentoTPub
		return costeDescuento;
	}

	public void setCosteDescuento(float descuento) {
		this.costeDescuento = (float) descuento;
	}
	

}
