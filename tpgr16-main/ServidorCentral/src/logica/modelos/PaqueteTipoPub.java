package logica.modelos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import logica.datatypes.DtPaquetePub;
import logica.datatypes.DtTipoPub;


public class PaqueteTipoPub {
	private String nombre;
	private byte[] image;
	private String descripcion;
	private LocalDate fechaAlta;
	private int periodoValidez;
	private float descuento;
	private float costoBase;
	private List<PaqPub> paqPub;
	private List<CompraPaquete> compraPaqs;
	private boolean comprado;

	
	//private Set<TipoPublicacion> tipoPublicacion;
	
	public PaqueteTipoPub(String nombre, String descripcion, byte[] image, LocalDate fechaAlta, int periodoValidez, float descuento) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fechaAlta = fechaAlta;
		this.periodoValidez = periodoValidez;
		this.descuento = descuento;
		this.paqPub = new ArrayList<>();
		this.setCompraPaqs(new ArrayList<>());
		this.setImage(image);
		this.setComprado(false);
	}
	
	public String getNombrePaq() {
		return this.nombre;
	}
	public String getDescripcionPaq() {
		return this.descripcion;
	}
	public LocalDate getFechaAltaPaq() {
		return this.fechaAlta;
	}
	public int getPeriodoValidezPaq() {
		return this.periodoValidez;
	}
	public float getDescuentoPaquete() {
		return this.descuento;
	}
	public float getCostoBasePaq() {
		return this.costoBase;
	}
	
	public DtPaquetePub obtenerDtPaq() {
		return new DtPaquetePub(this.getNombrePaq(), this.getImage(), this.getDescuentoPaquete(), this.getCostoBasePaq(), this.getPeriodoValidezPaq(), this.getFechaAltaPaq(), this.getDescripcionPaq());
	}

	public void agregarTipoPub(PaqPub paqPub) {
		this.paqPub.add(paqPub);
	}
	
	public List<PaqPub> obtenerColPaqPub(){
		return this.paqPub;
	}

	public boolean existeTipoPub(TipoPublicacion tipo) {
		for (PaqPub paqPub : this.paqPub) {
			if (paqPub.getTipoPub() == tipo)
				return true;
		}
		return false;
	}

	public int obtenerCantTipoPub(DtTipoPub tipoPub) {
		int cant=0;
		for (PaqPub paqP : this.paqPub) {
			if (paqP.getTipoPub().getNombre().equals(tipoPub.getNombre())) {
				cant = paqP.getCantidad();
			}
		}
		return cant;
	}
	
	public void eliminarTipos() {
		this.paqPub.clear();
	}

	public void setCosto(float costo, int cantidad) {
		this.costoBase += costo*cantidad * (1-(descuento/100.0));
	}

	public List<CompraPaquete> getCompraPaqs() {
		return compraPaqs;
	}

	public void setCompraPaqs(List<CompraPaquete> compraPaqs) {
		this.compraPaqs = compraPaqs;
	}

	public void agregarCompraPaq(CompraPaquete compraPaquete) {
		this.compraPaqs.add(compraPaquete);
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public void reducirCantidadTipoPub(DtTipoPub tipoDt) {
		for (PaqPub tipoPubPaq: paqPub) {
			if (tipoPubPaq.getTipoPub().getNombre() == tipoDt.getNombre()) {
				tipoPubPaq.reducirCantidad();
			}
		}
	}

	public boolean isComprado() {
		return comprado;
	}

	public void setComprado(boolean comprado) {
		this.comprado = comprado;
	}

}
