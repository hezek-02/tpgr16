package logica.datatypes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class DtPaquetePub {
	private String nombre;
	private Float descuento;
	private Float costo;
	private byte[] image;
	private Integer periodoValidez;
	private LocalDate fechaAlta;
	private String descripcion;
	
	public DtPaquetePub(String nombre, byte[] image, Float descuento, Float costo, Integer periodoValidez, LocalDate fechaAlta, String descripcion) {
		this.nombre = nombre;
		this.descuento = descuento;
		this.costo = costo;
		this.periodoValidez = periodoValidez;
		this.fechaAlta = fechaAlta;
		this.descripcion = descripcion;
		this.setImage(image);
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public Float getDescuento() {
		return descuento;
	}
	
	public Float getCosto() {
		return costo;
	}
	
	public Integer getPeriodoValidez() {
		return periodoValidez;
	}
	
	public LocalDate getFechaAlta() {
		return fechaAlta;
	}
	
	@XmlElement
	public String getFechaAltaTexto() {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Define el formato deseado
	    return this.fechaAlta.format(formatter); // Formatea la fecha y la devuelve como texto
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	@Override
	public String toString() {
		return this.nombre;
	}

	public byte[]  getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
	

}
