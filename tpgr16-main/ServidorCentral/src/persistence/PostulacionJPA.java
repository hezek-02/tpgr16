package persistence;


import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "Postulaciones")
public class PostulacionJPA implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private PostulacionPK id;
	
	@Column(nullable = false,length = 1000)
	private String cv;
	
    @Column(nullable = false,length = 1000)
	private String motivacion;
    
    @Column(nullable = false)
	private LocalDate fecha_postulacion;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_OFERTA")
    @MapsId("oferta_fk")
	private OfertaLabJPA oferta;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_POSTULANTE")
    @MapsId("postulante_fk")
	private PostulanteJPA postulante;
    
	public PostulacionJPA() {}

	public PostulacionPK getId() {
		return id;
	}

	public void setId(PostulacionPK id) {
		this.id = id;
	}

	public String getCv() {
		return cv;
	}

	public void setCv(String cv) {
		this.cv = cv;
	}

	public String getMotivacion() {
		return motivacion;
	}

	public void setMotivacion(String motivacion) {
		this.motivacion = motivacion;
	}

	public LocalDate getFecha_postulacion() {
		return fecha_postulacion;
	}

	public void setFecha_postulacion(LocalDate fecha_postulacion) {
		this.fecha_postulacion = fecha_postulacion;
	}

	public OfertaLabJPA getOferta() {
		return oferta;
	}

	public void setOferta(OfertaLabJPA oferta) {
		this.oferta = oferta;
	}

	public PostulanteJPA getPostulante() {
		return postulante;
	}

	public void setPostulante(PostulanteJPA postulante) {
		this.postulante = postulante;
	}
	

	
}
