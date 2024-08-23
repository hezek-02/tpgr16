
package com.trabajouy.webservices;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.trabajouy.webservices package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CostoMayorACeroExcepcion_QNAME = new QName("http://webservices/", "CostoMayorACeroExcepcion");
    private final static QName _FechaInvalidaException_QNAME = new QName("http://webservices/", "FechaInvalidaException");
    private final static QName _NoPoseePostulacionesException_QNAME = new QName("http://webservices/", "NoPoseePostulacionesException");
    private final static QName _OfertaLaboralYaExisteException_QNAME = new QName("http://webservices/", "OfertaLaboralYaExisteException");
    private final static QName _PaqueteNoExisteException_QNAME = new QName("http://webservices/", "PaqueteNoExisteException");
    private final static QName _PaqueteNoTieneMasTipoPubExcepcion_QNAME = new QName("http://webservices/", "PaqueteNoTieneMasTipoPubExcepcion");
    private final static QName _TipoPubNoExisteException_QNAME = new QName("http://webservices/", "TipoPubNoExisteException");
    private final static QName _UsuarioNoExisteException_QNAME = new QName("http://webservices/", "UsuarioNoExisteException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.trabajouy.webservices
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CostoMayorACeroExcepcion }
     * 
     * @return
     *     the new instance of {@link CostoMayorACeroExcepcion }
     */
    public CostoMayorACeroExcepcion createCostoMayorACeroExcepcion() {
        return new CostoMayorACeroExcepcion();
    }

    /**
     * Create an instance of {@link FechaInvalidaException }
     * 
     * @return
     *     the new instance of {@link FechaInvalidaException }
     */
    public FechaInvalidaException createFechaInvalidaException() {
        return new FechaInvalidaException();
    }

    /**
     * Create an instance of {@link NoPoseePostulacionesException }
     * 
     * @return
     *     the new instance of {@link NoPoseePostulacionesException }
     */
    public NoPoseePostulacionesException createNoPoseePostulacionesException() {
        return new NoPoseePostulacionesException();
    }

    /**
     * Create an instance of {@link OfertaLaboralYaExisteException }
     * 
     * @return
     *     the new instance of {@link OfertaLaboralYaExisteException }
     */
    public OfertaLaboralYaExisteException createOfertaLaboralYaExisteException() {
        return new OfertaLaboralYaExisteException();
    }

    /**
     * Create an instance of {@link PaqueteNoExisteException }
     * 
     * @return
     *     the new instance of {@link PaqueteNoExisteException }
     */
    public PaqueteNoExisteException createPaqueteNoExisteException() {
        return new PaqueteNoExisteException();
    }

    /**
     * Create an instance of {@link PaqueteNoTieneMasTipoPubExcepcion }
     * 
     * @return
     *     the new instance of {@link PaqueteNoTieneMasTipoPubExcepcion }
     */
    public PaqueteNoTieneMasTipoPubExcepcion createPaqueteNoTieneMasTipoPubExcepcion() {
        return new PaqueteNoTieneMasTipoPubExcepcion();
    }

    /**
     * Create an instance of {@link TipoPubNoExisteException }
     * 
     * @return
     *     the new instance of {@link TipoPubNoExisteException }
     */
    public TipoPubNoExisteException createTipoPubNoExisteException() {
        return new TipoPubNoExisteException();
    }

    /**
     * Create an instance of {@link UsuarioNoExisteException }
     * 
     * @return
     *     the new instance of {@link UsuarioNoExisteException }
     */
    public UsuarioNoExisteException createUsuarioNoExisteException() {
        return new UsuarioNoExisteException();
    }

    /**
     * Create an instance of {@link DtTipoPub }
     * 
     * @return
     *     the new instance of {@link DtTipoPub }
     */
    public DtTipoPub createDtTipoPub() {
        return new DtTipoPub();
    }

    /**
     * Create an instance of {@link LocalDate }
     * 
     * @return
     *     the new instance of {@link LocalDate }
     */
    public LocalDate createLocalDate() {
        return new LocalDate();
    }

    /**
     * Create an instance of {@link DtOferta }
     * 
     * @return
     *     the new instance of {@link DtOferta }
     */
    public DtOferta createDtOferta() {
        return new DtOferta();
    }

    /**
     * Create an instance of {@link KeyWord }
     * 
     * @return
     *     the new instance of {@link KeyWord }
     */
    public KeyWord createKeyWord() {
        return new KeyWord();
    }

    /**
     * Create an instance of {@link DtKeyWord }
     * 
     * @return
     *     the new instance of {@link DtKeyWord }
     */
    public DtKeyWord createDtKeyWord() {
        return new DtKeyWord();
    }

    /**
     * Create an instance of {@link DtPaquetePub }
     * 
     * @return
     *     the new instance of {@link DtPaquetePub }
     */
    public DtPaquetePub createDtPaquetePub() {
        return new DtPaquetePub();
    }

    /**
     * Create an instance of {@link DtPaqPub }
     * 
     * @return
     *     the new instance of {@link DtPaqPub }
     */
    public DtPaqPub createDtPaqPub() {
        return new DtPaqPub();
    }

    /**
     * Create an instance of {@link DtPostulacion }
     * 
     * @return
     *     the new instance of {@link DtPostulacion }
     */
    public DtPostulacion createDtPostulacion() {
        return new DtPostulacion();
    }

    /**
     * Create an instance of {@link DtTipoPubArray }
     * 
     * @return
     *     the new instance of {@link DtTipoPubArray }
     */
    public DtTipoPubArray createDtTipoPubArray() {
        return new DtTipoPubArray();
    }

    /**
     * Create an instance of {@link DtOfertaArray }
     * 
     * @return
     *     the new instance of {@link DtOfertaArray }
     */
    public DtOfertaArray createDtOfertaArray() {
        return new DtOfertaArray();
    }

    /**
     * Create an instance of {@link DtKeyWordArray }
     * 
     * @return
     *     the new instance of {@link DtKeyWordArray }
     */
    public DtKeyWordArray createDtKeyWordArray() {
        return new DtKeyWordArray();
    }

    /**
     * Create an instance of {@link DtPaquetePubArray }
     * 
     * @return
     *     the new instance of {@link DtPaquetePubArray }
     */
    public DtPaquetePubArray createDtPaquetePubArray() {
        return new DtPaquetePubArray();
    }

    /**
     * Create an instance of {@link DtPaqPubArray }
     * 
     * @return
     *     the new instance of {@link DtPaqPubArray }
     */
    public DtPaqPubArray createDtPaqPubArray() {
        return new DtPaqPubArray();
    }

    /**
     * Create an instance of {@link DtPostulacionArray }
     * 
     * @return
     *     the new instance of {@link DtPostulacionArray }
     */
    public DtPostulacionArray createDtPostulacionArray() {
        return new DtPostulacionArray();
    }

    /**
     * Create an instance of {@link StringArray }
     * 
     * @return
     *     the new instance of {@link StringArray }
     */
    public StringArray createStringArray() {
        return new StringArray();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CostoMayorACeroExcepcion }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CostoMayorACeroExcepcion }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservices/", name = "CostoMayorACeroExcepcion")
    public JAXBElement<CostoMayorACeroExcepcion> createCostoMayorACeroExcepcion(CostoMayorACeroExcepcion value) {
        return new JAXBElement<>(_CostoMayorACeroExcepcion_QNAME, CostoMayorACeroExcepcion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FechaInvalidaException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FechaInvalidaException }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservices/", name = "FechaInvalidaException")
    public JAXBElement<FechaInvalidaException> createFechaInvalidaException(FechaInvalidaException value) {
        return new JAXBElement<>(_FechaInvalidaException_QNAME, FechaInvalidaException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NoPoseePostulacionesException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link NoPoseePostulacionesException }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservices/", name = "NoPoseePostulacionesException")
    public JAXBElement<NoPoseePostulacionesException> createNoPoseePostulacionesException(NoPoseePostulacionesException value) {
        return new JAXBElement<>(_NoPoseePostulacionesException_QNAME, NoPoseePostulacionesException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OfertaLaboralYaExisteException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link OfertaLaboralYaExisteException }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservices/", name = "OfertaLaboralYaExisteException")
    public JAXBElement<OfertaLaboralYaExisteException> createOfertaLaboralYaExisteException(OfertaLaboralYaExisteException value) {
        return new JAXBElement<>(_OfertaLaboralYaExisteException_QNAME, OfertaLaboralYaExisteException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PaqueteNoExisteException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PaqueteNoExisteException }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservices/", name = "PaqueteNoExisteException")
    public JAXBElement<PaqueteNoExisteException> createPaqueteNoExisteException(PaqueteNoExisteException value) {
        return new JAXBElement<>(_PaqueteNoExisteException_QNAME, PaqueteNoExisteException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PaqueteNoTieneMasTipoPubExcepcion }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PaqueteNoTieneMasTipoPubExcepcion }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservices/", name = "PaqueteNoTieneMasTipoPubExcepcion")
    public JAXBElement<PaqueteNoTieneMasTipoPubExcepcion> createPaqueteNoTieneMasTipoPubExcepcion(PaqueteNoTieneMasTipoPubExcepcion value) {
        return new JAXBElement<>(_PaqueteNoTieneMasTipoPubExcepcion_QNAME, PaqueteNoTieneMasTipoPubExcepcion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoPubNoExisteException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TipoPubNoExisteException }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservices/", name = "TipoPubNoExisteException")
    public JAXBElement<TipoPubNoExisteException> createTipoPubNoExisteException(TipoPubNoExisteException value) {
        return new JAXBElement<>(_TipoPubNoExisteException_QNAME, TipoPubNoExisteException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UsuarioNoExisteException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UsuarioNoExisteException }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservices/", name = "UsuarioNoExisteException")
    public JAXBElement<UsuarioNoExisteException> createUsuarioNoExisteException(UsuarioNoExisteException value) {
        return new JAXBElement<>(_UsuarioNoExisteException_QNAME, UsuarioNoExisteException.class, null, value);
    }

}
