package persistence;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class PostulacionPK implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "ID_OFERTA")
	private int oferta_fk;
    @Column(name = "ID_POSTULANTE")
	private int postulante_fk;
	
    public PostulacionPK() {}

	public int getOferta() {
		return oferta_fk;
	}

	public void setOferta(int oferta) {
		this.oferta_fk = oferta;
	}

	public int getPostulante() {
		return postulante_fk;
	}

	public void setPostulante(int postulante) {
		this.postulante_fk = postulante;
	}

	public int hashCode() {
		return Objects.hash(oferta_fk, postulante_fk);
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PostulacionPK other = (PostulacionPK) obj;
		return oferta_fk == other.oferta_fk && postulante_fk == other.postulante_fk;
	}
    
}