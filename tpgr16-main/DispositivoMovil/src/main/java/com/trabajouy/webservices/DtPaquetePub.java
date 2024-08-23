
package com.trabajouy.webservices;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dtPaquetePub complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="dtPaquetePub">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="descuento" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         <element name="costo" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         <element name="image" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         <element name="periodoValidez" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         <element name="fechaAlta" type="{http://webservices/}localDate" minOccurs="0"/>
 *         <element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="fechaAltaTexto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtPaquetePub", propOrder = {
    "nombre",
    "descuento",
    "costo",
    "image",
    "periodoValidez",
    "fechaAlta",
    "descripcion",
    "fechaAltaTexto"
})
public class DtPaquetePub {

    protected String nombre;
    protected Float descuento;
    protected Float costo;
    protected byte[] image;
    protected Integer periodoValidez;
    protected LocalDate fechaAlta;
    protected String descripcion;
    protected String fechaAltaTexto;

    /**
     * Obtiene el valor de la propiedad nombre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Define el valor de la propiedad nombre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombre(String value) {
        this.nombre = value;
    }

    /**
     * Obtiene el valor de la propiedad descuento.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getDescuento() {
        return descuento;
    }

    /**
     * Define el valor de la propiedad descuento.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setDescuento(Float value) {
        this.descuento = value;
    }

    /**
     * Obtiene el valor de la propiedad costo.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getCosto() {
        return costo;
    }

    /**
     * Define el valor de la propiedad costo.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setCosto(Float value) {
        this.costo = value;
    }

    /**
     * Obtiene el valor de la propiedad image.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getImage() {
        return image;
    }

    /**
     * Define el valor de la propiedad image.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setImage(byte[] value) {
        this.image = value;
    }

    /**
     * Obtiene el valor de la propiedad periodoValidez.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPeriodoValidez() {
        return periodoValidez;
    }

    /**
     * Define el valor de la propiedad periodoValidez.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPeriodoValidez(Integer value) {
        this.periodoValidez = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaAlta.
     * 
     * @return
     *     possible object is
     *     {@link LocalDate }
     *     
     */
    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    /**
     * Define el valor de la propiedad fechaAlta.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalDate }
     *     
     */
    public void setFechaAlta(LocalDate value) {
        this.fechaAlta = value;
    }

    /**
     * Obtiene el valor de la propiedad descripcion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Define el valor de la propiedad descripcion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcion(String value) {
        this.descripcion = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaAltaTexto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaAltaTexto() {
        return fechaAltaTexto;
    }

    /**
     * Define el valor de la propiedad fechaAltaTexto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaAltaTexto(String value) {
        this.fechaAltaTexto = value;
    }

}
