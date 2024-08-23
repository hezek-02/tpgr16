package logica.datatypes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class DtTipoPub {

	private String nombre;
	private String descripcion;
	private int exposicion;
	private int duracion;
	private Float costo;
	private LocalDate fechaAlta;


	/**
	 * @param nombre
	 * @param descripcion
	 * @param exposicion
	 * @param duracion
	 * @param costo 
	 * @param fechaAlta 
	 */
	public DtTipoPub(String nombre, String descripcion, int exposicion, int duracion, Float costo, LocalDate fechaAlta) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.exposicion = exposicion;
		this.duracion = duracion;
		this.costo = costo;
		this.fechaAlta = fechaAlta;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public int getExposicion() {
		return this.exposicion;
	}

	public int getDuracion() {
		return duracion;
	}

	public Float getCosto() {
		return costo;
	}

	public LocalDate getFechaAlta() {
		return fechaAlta;
	}

	@XmlElement
	public String getFechaAltaTexto() {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Define el formato deseado
	    return this.fechaAlta.format(formatter); // Formatea la fecha y la devuelve como texto
	}
	
	@Override
	public String toString() {
		return this.nombre;
	}

}
