package logica.modelos;

import java.time.LocalDate;

import logica.datatypes.DtCompraPaquete;
import logica.datatypes.DtTipoPub;

public class CompraPaquete {
	private LocalDate fechaDeCompra;
	private LocalDate fechaDeVencimiento;
	private PaqueteTipoPub paquete;
	private Empresa empresa;

	public CompraPaquete(LocalDate fechaDeCompra) {
		this.setFechaDeCompra(fechaDeCompra);
	}

	public LocalDate getFechaDeCompra() {
		return fechaDeCompra;
	}

	public void setFechaDeCompra(LocalDate fechaDeCompra) {
		this.fechaDeCompra = fechaDeCompra;
	}

	public PaqueteTipoPub getPaquete() {
		return paquete;
	}

	public void setPaquete(PaqueteTipoPub paquete) {
		this.paquete = paquete;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public void agregarEmpresa(Empresa emp) {
		this.empresa = emp;
		emp.agregarCompraPaq(this);
	}

	public void agregarPaquete(PaqueteTipoPub paq) {
		this.paquete = paq;
		paq.agregarCompraPaq(this);
		this.setFechaDeVencimiento(this.fechaDeCompra.plusDays(paq.getPeriodoValidezPaq()));
	}

	public  void reducirCantidadTipoPub(DtTipoPub tipoDt) {
		this.paquete.reducirCantidadTipoPub(tipoDt);
	}

	public LocalDate getFechaDeVencimiento() {
		return fechaDeVencimiento;
	}

	public DtCompraPaquete getCompraPaquete() {
		LocalDate venc = this.fechaDeCompra.plusDays(paquete.getPeriodoValidezPaq());
		return new DtCompraPaquete(this.getFechaDeCompra(), venc, this.getPaquete().getNombrePaq(), this.getEmpresa().getNombre());
	}
	
	public void setFechaDeVencimiento(LocalDate fechaDeVencimiento) {
		this.fechaDeVencimiento = fechaDeVencimiento;
	}

}
