package logica.datatypes;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class DtPostulante extends DtUsuario {
	
	private String nacionalidad;
	private LocalDate fechaNacimiento;
	private String curriculum;

	/**
	 * @param nickname
	 * @param nombre
	 * @param apellido
	 * @param correo
	 * @param nacionalidad
	 * @param string 
	 * @param fechaNacimiento
	 * @param curriculum
	 */
	public DtPostulante(String nickname, String nombre, String apellido, String correo, String password, byte[] image, String nacionalidad, LocalDate fechaNacimiento, String curriculum) {
		super(nickname, nombre, apellido, correo, password, image);
		this.setNacionalidad(nacionalidad);
		this.setFechaNacimiento(fechaNacimiento);
		this.setCurriculum(curriculum);
	}
	
	public DtPostulante() {}
	@XmlElement
	public String getNacionalidad() {
		return this.nacionalidad;
	}
	
	public LocalDate getFechaNacimiento() {
		return this.fechaNacimiento;
	}
	@XmlElement
	public String getCurriculum() {
		return this.curriculum;
	}
	
	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}
	
	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	@XmlElement
	public String getFechaNacimientoTexto() {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Define el formato deseado
	    return this.fechaNacimiento.format(formatter); // Formatea la fecha y la devuelve como texto
	}
	
	public void setCurriculum(String curriculum) {
		this.curriculum = curriculum;
	}


	@Override
	public String toString() {
        return "Postulante: " + getNickname() ;

	}

}
