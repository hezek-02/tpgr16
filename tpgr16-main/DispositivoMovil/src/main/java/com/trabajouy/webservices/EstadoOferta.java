
package com.trabajouy.webservices;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para estadoOferta.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <pre>{@code
 * <simpleType name="estadoOferta">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="INGRESADO"/>
 *     <enumeration value="CONFIRMADO"/>
 *     <enumeration value="RECHAZADO"/>
 *     <enumeration value="FINALIZADA"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "estadoOferta")
@XmlEnum
public enum EstadoOferta {

    INGRESADO,
    CONFIRMADO,
    RECHAZADO,
    FINALIZADA;

    public String value() {
        return name();
    }

    public static EstadoOferta fromValue(String v) {
        return valueOf(v);
    }

}
