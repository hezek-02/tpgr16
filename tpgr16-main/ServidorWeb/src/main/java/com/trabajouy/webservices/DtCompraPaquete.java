
package com.trabajouy.webservices;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dtCompraPaquete complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="dtCompraPaquete">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="fechaDeCompraTexto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="fechaDeVencimientoTexto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtCompraPaquete", propOrder = {
    "fechaDeCompraTexto",
    "fechaDeVencimientoTexto"
})
public class DtCompraPaquete {

    protected String fechaDeCompraTexto;
    protected String fechaDeVencimientoTexto;

    /**
     * Obtiene el valor de la propiedad fechaDeCompraTexto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaDeCompraTexto() {
        return fechaDeCompraTexto;
    }

    /**
     * Define el valor de la propiedad fechaDeCompraTexto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaDeCompraTexto(String value) {
        this.fechaDeCompraTexto = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaDeVencimientoTexto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaDeVencimientoTexto() {
        return fechaDeVencimientoTexto;
    }

    /**
     * Define el valor de la propiedad fechaDeVencimientoTexto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaDeVencimientoTexto(String value) {
        this.fechaDeVencimientoTexto = value;
    }

}
