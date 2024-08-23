package persistence;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name = "Usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TIPO_USUARIO")
public abstract class UsuarioJPA implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int id;
    @Column(unique = true,nullable = false)
	private String nickname;
    @Column(unique = true, nullable = false)
	private String email;
    @Column(nullable = false)
	private String nombre;
    @Column(nullable = false)
	private String apellido;
	
	protected UsuarioJPA() {}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public String getNickname() {
		return nickname;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getApellido() {
		return apellido;
	}
	
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}


}
