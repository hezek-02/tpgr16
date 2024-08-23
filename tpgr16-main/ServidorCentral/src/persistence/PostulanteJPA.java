package persistence;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@SuppressWarnings("serial")
@Entity
@Table(name = "Postulante")
@DiscriminatorValue("2")
@PrimaryKeyJoinColumn(name = "ID_USUARIO")
public class PostulanteJPA extends UsuarioJPA implements Serializable{
	
    @Column(nullable = false)
	private String nacionalidad;
    
    @Column(nullable = false)
	private LocalDate fecha_nacimiento;
	
	public PostulanteJPA() {}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public LocalDate getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}

	


}
