package persistence;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "Ofertas_finalizadas")
public class OfertaLabJPA implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private int idOferta;
	
	@Column(nullable = false, unique = true)
	private String nombre;
	
	@Column(nullable = false, length = 1000)
	private String descripcion;
	
    @Column(nullable = false)
	private String horario;
    
	@Column(nullable = false)
	private Float remuneracion;
	
    @Column(nullable = false)
	private String departamento;
    
    @Column(nullable = false)
	private String ciudad;
    
	@Column(nullable = false)
	private String tipo_pub;
	
	@Column(nullable = false)
	private LocalDate fecha_Alta;
	
	@Column(nullable = false)
	private LocalDate fecha_Baja;
	
	@Column(nullable = false)
	private Float costo;
	
	@Column(nullable = true)
	private String paquete;
	
	@Lob
	@Column(nullable = false)
	private byte[] image;
	
    @ManyToOne(cascade = CascadeType.ALL)
    private EmpresaJPA empresa;

	public OfertaLabJPA(){}

	public int getIdOferta() {
		return idOferta;
	}

	public void setIdOferta(int idOferta) {
		this.idOferta = idOferta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public Float getRemuneracion() {
		return remuneracion;
	}

	public void setRemuneracion(Float remuneracion) {
		this.remuneracion = remuneracion;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getTipo_pub() {
		return tipo_pub;
	}

	public void setTipo_pub(String tipo_pub) {
		this.tipo_pub = tipo_pub;
	}

	public LocalDate getFecha_Alta() {
		return fecha_Alta;
	}

	public void setFecha_Alta(LocalDate fecha_Alta) {
		this.fecha_Alta = fecha_Alta;
	}

	public LocalDate getFecha_Baja() {
		return fecha_Baja;
	}

	public void setFecha_Baja(LocalDate fecha_Baja) {
		this.fecha_Baja = fecha_Baja;
	}

	public Float getCosto() {
		return costo;
	}

	public void setCosto(Float costo) {
		this.costo = costo;
	}

	public String getPaquete() {
		return paquete;
	}

	public void setPaquete(String paquete) {
		this.paquete = paquete;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public EmpresaJPA getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaJPA empresa) {
		this.empresa = empresa;
	}
	
}

