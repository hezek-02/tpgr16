package logica.modelos;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import logica.datatypes.DtTipoPub;

public class TipoPublicacion {
	private String nombre;
	private String descripcion;
	private int exposicion;
	private int duracion;
	private Float costo;
	private LocalDate fechaAlta;
	private List<PaqPub> paqPub;
	
	public TipoPublicacion(String nombre, String descripcion, int duracion, int exposicion, Float costo, LocalDate fecha) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.exposicion = exposicion;
		this.duracion = duracion; 
		this.costo = costo;
		this.fechaAlta = fecha;
		this.paqPub = new ArrayList<>();

	}
	
	public List<PaqPub> obtenerListaPaqPub(){
		return paqPub;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	public int getDuracion() {
		return this.duracion;
	}
	public Float getCosto() {
		return this.costo;
	}
	public LocalDate getFechaAlta() {
		return this.fechaAlta;
	}
	public String getDescripcion() {
		return this.descripcion;
	}
	public int getExposicion() {
		return this.exposicion;
	}
//	public void setNombre(String nombre) {
//		this.nombre = nombre;
//	}
//	public void setCosto(Float costo) {
//		this.costo = costo;
//	}
//	public void setDuracion(int dur) {
//		this.duracion = dur;
//	}
//	public void setFecha(LocalDate fecha) {
//		this.fechaAlta = fecha;
//	}
//	public void setDescripcion(String descripcion) {
//		this.descripcion = descripcion;
//	}
//	public void setExposicion(int exposicion) {
//		this.exposicion = exposicion;
//	}
	public DtTipoPub getDtTipoPublicacion() {
		return new DtTipoPub(getNombre(), getDescripcion(), getExposicion(), getDuracion(), getCosto(), getFechaAlta());
	}

	public void agregarPaq(PaqPub paqPub) {
		this.paqPub.add(paqPub);
	}
}