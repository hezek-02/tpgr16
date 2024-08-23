package logica.datatypes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.xml.bind.annotation.XmlAccessType;
import logica.modelos.KeyWord;
import persistence.OfertaLabJPA;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class DtOferta {
	private String nombre;
	private String descripcion;
	private String ciudad;
	private String departamento;
	private String horario;
	private byte[] image;
	private Float remuneracion;
	private Float coste;
	private EstadoOferta estado;
	private LocalDate fechaAlta;
	private KeyWord[] keys;
	private int duracion;
	
	public DtOferta(String nombre, String descripcion, byte[] image, String ciudad, String departamento, String horario,  Float remuneracion, LocalDate fechaAlta, Float coste, int duracion, EstadoOferta estado, KeyWord[] keyWords) {
		this.nombre = nombre;
		this.setImage(image);
		this.descripcion = descripcion;
		this.ciudad = ciudad;
		this.departamento = departamento;
		this.horario = horario;
		this.remuneracion = remuneracion;
		this.fechaAlta = fechaAlta;
		this.coste = coste;
		this.duracion = duracion;
		this.setEstado(estado);
		this.setKeyWords(keyWords);
	}
	@XmlElement
	public String getNombre() {
		return this.nombre;
	}
	@XmlElement
	public String getDescripcion() {
		return this.descripcion;
	}
	@XmlElement
	public String getCiudad() {
		return this.ciudad;
	}
	@XmlElement
	public int getDuracion() {
		return this.duracion;
	}
	@XmlElement
	public String getDepartamento() {
		return this.departamento;
	}
	
	public String getHorario() {
		return this.horario;
	}
	@XmlElement
	public Float getRemuneracion() {
		return this.remuneracion;
	}
	
	public LocalDate getFechaAlta() {
		return this.fechaAlta;
	}
	
	@XmlElement
	public String getFechaAltaTexto() {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Define el formato deseado
	    return this.fechaAlta.format(formatter); // Formatea la fecha y la devuelve como texto
	}
	
	@XmlElement
	public String getFechaBajaTexto() {
		EntityManagerFactory entManagerFactory = null;
	    EntityManager entityManager = null;
	    OfertaLabJPA ofertaFinalizada = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Define el formato deseado
        if(this.getEstado() == EstadoOferta.FINALIZADA) {
		    try {
				entManagerFactory = Persistence.createEntityManagerFactory("Servidor");
				entityManager = entManagerFactory.createEntityManager();
		        
				TypedQuery<OfertaLabJPA> query = entityManager.createQuery(
					    "SELECT o FROM OfertaLabJPA o WHERE o.nombre = :nomOferta",
					    OfertaLabJPA.class
					);
		        query.setParameter("nomOferta", this.getNombre());
		        ofertaFinalizada = query.getSingleResult();
		    } finally {
		         entityManager.close();
		         entManagerFactory.close();
		    }
		    return ofertaFinalizada.getFecha_Baja().format(formatter); // Formatea la fecha y la devuelve como texto
        }else {
        	return "";
        }
	}
	
	@XmlElement
	public Float getCoste() {
		return this.coste;
	}
	
	public String toString() {
		return this.getNombre() + " ("+getEstado()+")";
	}

	@XmlElement
	public EstadoOferta getEstado() {
		return estado;
	}

	public void setEstado(EstadoOferta estado) {
		this.estado = estado;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	@XmlElement
	public KeyWord[] getKeyWords() {
		return keys;
	}

	public void setKeyWords(KeyWord[] keys) {
		this.keys = keys;
	}
	
	@XmlElement
	public String getHorarioTexto() {
		String[] horarioPartes = this.horario.split(":");
		return horarioPartes[0]+":"+horarioPartes[1]+" - "+horarioPartes[2]+":"+horarioPartes[3];
	}
}
