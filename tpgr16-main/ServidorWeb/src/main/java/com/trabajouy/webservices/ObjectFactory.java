
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

    private final static QName _FechaInvalidaException_QNAME = new QName("http://webservices/", "FechaInvalidaException");
    private final static QName _PaqueteNoExisteException_QNAME = new QName("http://webservices/", "PaqueteNoExisteException");
    private final static QName _PaqueteYaCompradoException_QNAME = new QName("http://webservices/", "PaqueteYaCompradoException");
    private final static QName _PostulacionYaExisteException_QNAME = new QName("http://webservices/", "PostulacionYaExisteException");
    private final static QName _RankingYaOcupadoException_QNAME = new QName("http://webservices/", "RankingYaOcupadoException");
    private final static QName _UsuarioNoExisteException_QNAME = new QName("http://webservices/", "UsuarioNoExisteException");
    private final static QName _UsuarioRegistradoException_QNAME = new QName("http://webservices/", "UsuarioRegistradoException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.trabajouy.webservices
     * 
     */
    public ObjectFactory() {
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
     * Create an instance of {@link PaqueteNoExisteException }
     * 
     * @return
     *     the new instance of {@link PaqueteNoExisteException }
     */
    public PaqueteNoExisteException createPaqueteNoExisteException() {
        return new PaqueteNoExisteException();
    }

    /**
     * Create an instance of {@link PaqueteYaCompradoException }
     * 
     * @return
     *     the new instance of {@link PaqueteYaCompradoException }
     */
    public PaqueteYaCompradoException createPaqueteYaCompradoException() {
        return new PaqueteYaCompradoException();
    }

    /**
     * Create an instance of {@link PostulacionYaExisteException }
     * 
     * @return
     *     the new instance of {@link PostulacionYaExisteException }
     */
    public PostulacionYaExisteException createPostulacionYaExisteException() {
        return new PostulacionYaExisteException();
    }

    /**
     * Create an instance of {@link RankingYaOcupadoException }
     * 
     * @return
     *     the new instance of {@link RankingYaOcupadoException }
     */
    public RankingYaOcupadoException createRankingYaOcupadoException() {
        return new RankingYaOcupadoException();
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
     * Create an instance of {@link UsuarioRegistradoException }
     * 
     * @return
     *     the new instance of {@link UsuarioRegistradoException }
     */
    public UsuarioRegistradoException createUsuarioRegistradoException() {
        return new UsuarioRegistradoException();
    }

    /**
     * Create an instance of {@link DtCompraPaquete }
     * 
     * @return
     *     the new instance of {@link DtCompraPaquete }
     */
    public DtCompraPaquete createDtCompraPaquete() {
        return new DtCompraPaquete();
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
     * Create an instance of {@link DtEmpresa }
     * 
     * @return
     *     the new instance of {@link DtEmpresa }
     */
    public DtEmpresa createDtEmpresa() {
        return new DtEmpresa();
    }

    /**
     * Create an instance of {@link DtPostulante }
     * 
     * @return
     *     the new instance of {@link DtPostulante }
     */
    public DtPostulante createDtPostulante() {
        return new DtPostulante();
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
     * Create an instance of {@link DtPostulacion }
     * 
     * @return
     *     the new instance of {@link DtPostulacion }
     */
    public DtPostulacion createDtPostulacion() {
        return new DtPostulacion();
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
     * Create an instance of {@link DtEmpresaArray }
     * 
     * @return
     *     the new instance of {@link DtEmpresaArray }
     */
    public DtEmpresaArray createDtEmpresaArray() {
        return new DtEmpresaArray();
    }

    /**
     * Create an instance of {@link DtUsuarioArray }
     * 
     * @return
     *     the new instance of {@link DtUsuarioArray }
     */
    public DtUsuarioArray createDtUsuarioArray() {
        return new DtUsuarioArray();
    }

    /**
     * Create an instance of {@link DtPostulanteArray }
     * 
     * @return
     *     the new instance of {@link DtPostulanteArray }
     */
    public DtPostulanteArray createDtPostulanteArray() {
        return new DtPostulanteArray();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link PaqueteYaCompradoException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PaqueteYaCompradoException }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservices/", name = "PaqueteYaCompradoException")
    public JAXBElement<PaqueteYaCompradoException> createPaqueteYaCompradoException(PaqueteYaCompradoException value) {
        return new JAXBElement<>(_PaqueteYaCompradoException_QNAME, PaqueteYaCompradoException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PostulacionYaExisteException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PostulacionYaExisteException }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservices/", name = "PostulacionYaExisteException")
    public JAXBElement<PostulacionYaExisteException> createPostulacionYaExisteException(PostulacionYaExisteException value) {
        return new JAXBElement<>(_PostulacionYaExisteException_QNAME, PostulacionYaExisteException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RankingYaOcupadoException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RankingYaOcupadoException }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservices/", name = "RankingYaOcupadoException")
    public JAXBElement<RankingYaOcupadoException> createRankingYaOcupadoException(RankingYaOcupadoException value) {
        return new JAXBElement<>(_RankingYaOcupadoException_QNAME, RankingYaOcupadoException.class, null, value);
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

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UsuarioRegistradoException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UsuarioRegistradoException }{@code >}
     */
    @XmlElementDecl(namespace = "http://webservices/", name = "UsuarioRegistradoException")
    public JAXBElement<UsuarioRegistradoException> createUsuarioRegistradoException(UsuarioRegistradoException value) {
        return new JAXBElement<>(_UsuarioRegistradoException_QNAME, UsuarioRegistradoException.class, null, value);
    }

}
