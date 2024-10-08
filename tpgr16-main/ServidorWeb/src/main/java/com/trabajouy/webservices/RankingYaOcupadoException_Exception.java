
package com.trabajouy.webservices;

import jakarta.xml.ws.WebFault;


/**
 * This class was generated by the XML-WS Tools.
 * XML-WS Tools 4.0.0
 * Generated source version: 3.0
 * 
 */
@WebFault(name = "RankingYaOcupadoException", targetNamespace = "http://webservices/")
public class RankingYaOcupadoException_Exception
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private RankingYaOcupadoException faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public RankingYaOcupadoException_Exception(String message, RankingYaOcupadoException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param cause
     * @param faultInfo
     * @param message
     */
    public RankingYaOcupadoException_Exception(String message, RankingYaOcupadoException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: com.trabajouy.webservices.RankingYaOcupadoException
     */
    public RankingYaOcupadoException getFaultInfo() {
        return faultInfo;
    }

}
