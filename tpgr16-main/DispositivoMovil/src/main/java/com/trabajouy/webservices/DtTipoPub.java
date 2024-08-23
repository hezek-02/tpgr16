
package com.trabajouy.webservices;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dtTipoPub complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="dtTipoPub">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="exposicion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="duracion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="costo" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         <element name="fechaAlta" type="{http://webservices/}localDate" minOccurs="0"/>
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
@XmlType(name = "dtTipoPub", propOrder = {
    "nombre",
    "descripcion",
    "exposicion",
    "duracion",
    "costo",
    "fechaAlta",
    "fechaAltaTexto"
})
public class DtTipoPub {

    protected String nombre;
    protected String descripcion;
    protected int exposicion;
    protected int duracion;
    protected Float costo;
    protected LocalDate fechaAlta;
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
     * Obtiene el valor de la propiedad exposicion.
     * 
     */
    public int getExposicion() {
        return exposicion;
    }

    /**
     * Define el valor de la propiedad exposicion.
     * 
     */
    public void setExposicion(int value) {
        this.exposicion = value;
    }

    /**
     * Obtiene el valor de la propiedad duracion.
     * 
     */
    public int getDuracion() {
        return duracion;
    }

    /**
     * Define el valor de la propiedad duracion.
     * 
     */
    public void setDuracion(int value) {
        this.duracion = value;
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
