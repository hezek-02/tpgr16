package logica.modelos;

import logica.datatypes.DtPaqPub;

public class PaqPub {
	private int cantidad;
	private TipoPublicacion tipoPublicacion;
	private PaqueteTipoPub paquete;
	
	public PaqPub(int cant, TipoPublicacion tipoPublicacion, PaqueteTipoPub paquete) {
		this.cantidad = cant;
		this.paquete = paquete;
		this.tipoPublicacion = tipoPublicacion;
	}
		
	public TipoPublicacion getTipoPub() {
		return this.tipoPublicacion;
	}
	
	public PaqueteTipoPub getPaqueteTipoPub() {
		return this.paquete;
	}

	public int getCantidad() {
		return cantidad;
	}
	
	public void reducirCantidad() {
		this.cantidad = this.cantidad-1;
	}

	public DtPaqPub getDtPaqPub() {
		DtPaqPub dTpaqPub = new DtPaqPub(this.paquete.getNombrePaq(), this.tipoPublicacion.getNombre(), getCantidad());
		dTpaqPub.setCosteDescuento((float) (this.tipoPublicacion.getCosto() * (1 - this.paquete.getDescuentoPaquete() / 100.0)));
		return dTpaqPub;
	}
}
