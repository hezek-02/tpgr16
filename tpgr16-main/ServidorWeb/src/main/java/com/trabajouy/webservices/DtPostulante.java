
package com.trabajouy.webservices;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dtPostulante complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="dtPostulante">
 *   <complexContent>
 *     <extension base="{http://webservices/}dtUsuario">
 *       <sequence>
 *         <element name="curriculum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="fechaNacimiento" type="{http://webservices/}localDate" minOccurs="0"/>
 *         <element name="fechaNacimientoTexto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="nacionalidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       </sequence>
 *     </extension>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtPostulante", propOrder = {
    "curriculum",
    "fechaNacimiento",
    "fechaNacimientoTexto",
    "nacionalidad"
})
public class DtPostulante
    extends DtUsuario
{

    protected String curriculum;
    protected LocalDate fechaNacimiento;
    protected String fechaNacimientoTexto;
    protected String nacionalidad;

    /**
     * Obtiene el valor de la propiedad curriculum.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurriculum() {
        return curriculum;
    }

    /**
     * Define el valor de la propiedad curriculum.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurriculum(String value) {
        this.curriculum = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaNacimiento.
     * 
     * @return
     *     possible object is
     *     {@link LocalDate }
     *     
     */
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Define el valor de la propiedad fechaNacimiento.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalDate }
     *     
     */
    public void setFechaNacimiento(LocalDate value) {
        this.fechaNacimiento = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaNacimientoTexto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaNacimientoTexto() {
        return fechaNacimientoTexto;
    }

    /**
     * Define el valor de la propiedad fechaNacimientoTexto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaNacimientoTexto(String value) {
        this.fechaNacimientoTexto = value;
    }

    /**
     * Obtiene el valor de la propiedad nacionalidad.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNacionalidad() {
        return nacionalidad;
    }

    /**
     * Define el valor de la propiedad nacionalidad.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNacionalidad(String value) {
        this.nacionalidad = value;
    }

}
