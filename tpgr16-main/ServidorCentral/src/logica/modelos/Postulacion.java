package logica.modelos;


import java.time.LocalDate;
import logica.datatypes.DtPostulacion;

public class Postulacion  {
	private String cvRed;
	private String motivacion;
	private LocalDate fechaPost;
	private LocalDate fechaRanking;
	private OfertaLab oferta;
	private Postulante postulante;
	private String videoUrl;
	private int ranking;
	
	public Postulacion(String cvRed, String motivacion, LocalDate fecha, OfertaLab oferta, Postulante postulante) {
		this.cvRed = cvRed;
		this.motivacion = motivacion;
		this.fechaPost = fecha;
		this.oferta = oferta;
		this.postulante = postulante;
		this.videoUrl = "";
		this.ranking = 0;
		this.setFechaRanking(LocalDate.now());
	}

	public String getCVReducido() {
		return this.cvRed;
	}
	
	public String getMotivacion() {
		return this.motivacion;
	}
	
	public LocalDate getFechaPostulacion() {
		return this.fechaPost;
	}
	
	public OfertaLab getOferta() {
		return this.oferta;
	}
	
	public Postulante getPostulante() {
		return this.postulante;
	}
	
	public DtPostulacion getDtPostulacion() {
		 DtPostulacion postulacionDt = new DtPostulacion(getCVReducido(), getMotivacion(), getFechaPostulacion(), getOferta().getNombre(), getPostulante().getNickname(), getVideoUrl());
		 postulacionDt.setRanking(this.ranking);
		 postulacionDt.setFechaRanking(this.fechaRanking);
		 return postulacionDt;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public void eliminarPostulacion() {
		this.postulante.eliminarPostulacion(this);
		this.postulante = null;
		this.oferta = null;
	}

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
