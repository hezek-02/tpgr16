package logica.datatypes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class DtPostulacion {

	private String cvReducido;
	private String motivacion;
	private LocalDate fechaPostulacion;
	private String nombreOferta;
	private String nicknamePostulante;
	private String videoUrl;
	private LocalDate fechaRanking;
	private int ranking;

	public DtPostulacion(String cvReducido, String motivacion, LocalDate fechaPostulacion, String nombreOferta, String nicknamePostulante,String videoUrl) {
		this.cvReducido = cvReducido;
		this.motivacion = motivacion;
		this.fechaPostulacion = fechaPostulacion;
		this.fechaRanking = fechaPostulacion;
		this.nombreOferta = nombreOferta;
		this.nicknamePostulante = nicknamePostulante;
		this.videoUrl = videoUrl;
	}
	@XmlElement	
	public String getCvReducido() {
		return this.cvReducido;
	}
	@XmlElement
	public String getMotivacion() {
		return this.motivacion;
	}

	public LocalDate getFechaPostulacion() {
		return this.fechaPostulacion;
	}
	
	@XmlElement
	public String getFechaPostulacionTexto() {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Define el formato deseado
	    return this.fechaPostulacion.format(formatter); // Formatea la fecha y la devuelve como texto
	}
	
	
	@XmlElement
	public String getFechaRankingTexto() {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Define el formato deseado
	    return this.fechaRanking.format(formatter); // Formatea la fecha y la devuelve como texto
	}
	
	@XmlElement
	public String getNombreOferta() {
		return this.nombreOferta;
	}
	@XmlElement
	public String getNicknamePostulante() {
		return this.nicknamePostulante;
	}

    public String toString() {
        return "Postulante: " + this.nicknamePostulante + " " + this.fechaPostulacion;
    }

	@XmlElement
	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	
	@XmlElement
	public int getRanking() {
		return ranking;
	}
	
	public void setRanking(int ranking) {
		this.ranking = ranking;
	}
	public LocalDate getFechaRanking() {
		return fechaRanking;
	}
	public void setFechaRanking(LocalDate fechaRanking) {
		this.fechaRanking = fechaRanking;
	}

}
