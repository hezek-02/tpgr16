
package com.trabajouy.webservices;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dtPaqPub complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="dtPaqPub">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="tipoPub" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="cantidad" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="paqueteTipoPub" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="costeDescuento" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtPaqPub", propOrder = {
    "tipoPub",
    "cantidad",
    "paqueteTipoPub",
    "costeDescuento"
})
public class DtPaqPub {

    protected String tipoPub;
    protected int cantidad;
    protected String paqueteTipoPub;
    protected float costeDescuento;

    /**
     * Obtiene el valor de la propiedad tipoPub.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoPub() {
        return tipoPub;
    }

    /**
     * Define el valor de la propiedad tipoPub.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoPub(String value) {
        this.tipoPub = value;
    }

    /**
     * Obtiene el valor de la propiedad cantidad.
     * 
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Define el valor de la propiedad cantidad.
     * 
     */
    public void setCantidad(int value) {
        this.cantidad = value;
    }

    /**
     * Obtiene el valor de la propiedad paqueteTipoPub.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaqueteTipoPub() {
        return paqueteTipoPub;
    }

    /**
     * Define el valor de la propiedad paqueteTipoPub.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaqueteTipoPub(String value) {
        this.paqueteTipoPub = value;
    }

    /**
     * Obtiene el valor de la propiedad costeDescuento.
     * 
     */
    public float getCosteDescuento() {
        return costeDescuento;
    }

    /**
     * Define el valor de la propiedad costeDescuento.
     * 
     */
    public void setCosteDescuento(float value) {
        this.costeDescuento = value;
    }

}
