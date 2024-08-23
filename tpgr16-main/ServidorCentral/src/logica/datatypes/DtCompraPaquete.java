package logica.datatypes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class DtCompraPaquete {
	private LocalDate fechaDeCompra;
	private LocalDate fechaDeVencimiento;
	private String paquete;
	private String empresa;
	
	/**
	 * 
	 * @param fechaDeCompra
	 * @param fechaDeVencimiento
	 * @param paquete
	 * @param empresa
	 */
	
	public DtCompraPaquete(LocalDate fechaDeCompra, LocalDate fechaDeVencimiento, String paquete, String empresa) {
		this.fechaDeCompra = fechaDeCompra;
		this.fechaDeVencimiento = fechaDeVencimiento;
		this.paquete = paquete;
		this.empresa = empresa;
	}
	
	public LocalDate getFechaCompra() {
		return fechaDeCompra;
	}

	@XmlElement
	public String getFechaDeCompraTexto() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return this.fechaDeCompra.format(formatter);
	}
	
	public LocalDate getFechaVencimiento() {
		return fechaDeVencimiento;
	}
	
	@XmlElement
	public String getfechaDeVencimientoTexto() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return this.fechaDeVencimiento.format(formatter);
	}
	
	public String getPaquete() {
		return paquete;
	}
	
	public String getEmpresa() {
		return empresa;
	}
}