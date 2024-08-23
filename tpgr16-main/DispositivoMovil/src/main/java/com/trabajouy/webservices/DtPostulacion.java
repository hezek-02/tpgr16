
package com.trabajouy.webservices;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dtPostulacion complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="dtPostulacion">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="cvReducido" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="fechaPostulacionTexto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="motivacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="nicknamePostulante" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="nombreOferta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="videoUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtPostulacion", propOrder = {
    "cvReducido",
    "fechaPostulacionTexto",
    "motivacion",
    "nicknamePostulante",
    "nombreOferta",
    "videoUrl"
})
public class DtPostulacion {

    protected String cvReducido;
    protected String fechaPostulacionTexto;
    protected String motivacion;
    protected String nicknamePostulante;
    protected String nombreOferta;
    protected String videoUrl;

    /**
     * Obtiene el valor de la propiedad cvReducido.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCvReducido() {
        return cvReducido;
    }

    /**
     * Define el valor de la propiedad cvReducido.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCvReducido(String value) {
        this.cvReducido = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaPostulacionTexto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaPostulacionTexto() {
        return fechaPostulacionTexto;
    }

    /**
     * Define el valor de la propiedad fechaPostulacionTexto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaPostulacionTexto(String value) {
        this.fechaPostulacionTexto = value;
    }

    /**
     * Obtiene el valor de la propiedad motivacion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMotivacion() {
        return motivacion;
    }

    /**
     * Define el valor de la propiedad motivacion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMotivacion(String value) {
        this.motivacion = value;
    }

    /**
     * Obtiene el valor de la propiedad nicknamePostulante.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNicknamePostulante() {
        return nicknamePostulante;
    }

    /**
     * Define el valor de la propiedad nicknamePostulante.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNicknamePostulante(String value) {
        this.nicknamePostulante = value;
    }

    /**
     * Obtiene el valor de la propiedad nombreOferta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreOferta() {
        return nombreOferta;
    }

    /**
     * Define el valor de la propiedad nombreOferta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreOferta(String value) {
        this.nombreOferta = value;
    }

    /**
     * Obtiene el valor de la propiedad videoUrl.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVideoUrl() {
        return videoUrl;
    }

    /**
     * Define el valor de la propiedad videoUrl.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVideoUrl(String value) {
        this.videoUrl = value;
    }

}
