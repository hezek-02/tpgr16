
package com.trabajouy.webservices;

import jakarta.xml.ws.WebFault;


/**
 * This class was generated by the XML-WS Tools.
 * XML-WS Tools 4.0.0
 * Generated source version: 3.0
 * 
 */
@WebFault(name = "PaqueteNoTieneMasTipoPubExcepcion", targetNamespace = "http://webservices/")
public class PaqueteNoTieneMasTipoPubExcepcion_Exception
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private PaqueteNoTieneMasTipoPubExcepcion faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public PaqueteNoTieneMasTipoPubExcepcion_Exception(String message, PaqueteNoTieneMasTipoPubExcepcion faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param cause
     * @param faultInfo
     * @param message
     */
    public PaqueteNoTieneMasTipoPubExcepcion_Exception(String message, PaqueteNoTieneMasTipoPubExcepcion faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: com.trabajouy.webservices.PaqueteNoTieneMasTipoPubExcepcion
     */
    public PaqueteNoTieneMasTipoPubExcepcion getFaultInfo() {
        return faultInfo;
    }

}
