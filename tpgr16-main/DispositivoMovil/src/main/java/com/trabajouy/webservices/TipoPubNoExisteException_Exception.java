
package com.trabajouy.webservices;

import jakarta.xml.ws.WebFault;


/**
 * This class was generated by the XML-WS Tools.
 * XML-WS Tools 4.0.0
 * Generated source version: 3.0
 * 
 */
@WebFault(name = "TipoPubNoExisteException", targetNamespace = "http://webservices/")
public class TipoPubNoExisteException_Exception
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private TipoPubNoExisteException faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public TipoPubNoExisteException_Exception(String message, TipoPubNoExisteException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param cause
     * @param faultInfo
     * @param message
     */
    public TipoPubNoExisteException_Exception(String message, TipoPubNoExisteException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: com.trabajouy.webservices.TipoPubNoExisteException
     */
    public TipoPubNoExisteException getFaultInfo() {
        return faultInfo;
    }

}