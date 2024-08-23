
package com.trabajouy.webservices;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dtKeyWord complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="dtKeyWord">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="nombreKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtKeyWord", propOrder = {
    "nombreKey"
})
public class DtKeyWord {

    protected String nombreKey;

    /**
     * Obtiene el valor de la propiedad nombreKey.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreKey() {
        return nombreKey;
    }

    /**
     * Define el valor de la propiedad nombreKey.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreKey(String value) {
        this.nombreKey = value;
    }

}
