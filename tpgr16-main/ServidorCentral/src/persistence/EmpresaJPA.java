package persistence;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Empresa")
@DiscriminatorValue("1")
@PrimaryKeyJoinColumn(name = "ID_USUARIO")
public class EmpresaJPA extends UsuarioJPA implements Serializable{
	private static final long serialVersionUID = 1L;
	
    @Column(nullable = false, length = 1000)
	private String descripcion;
    
	@Column(nullable = true)
	private String sitio_web;

	public EmpresaJPA() {}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getSitio_web() {
		return sitio_web;
	}

	public void setSitio_web(String sitio_web) {
		this.sitio_web = sitio_web;
	}

}
