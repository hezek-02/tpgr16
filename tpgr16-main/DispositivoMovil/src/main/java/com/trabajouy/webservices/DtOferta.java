
package com.trabajouy.webservices;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dtOferta complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="dtOferta">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="ciudad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="coste" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         <element name="departamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="duracion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="estado" type="{http://webservices/}estadoOferta" minOccurs="0"/>
 *         <element name="fechaAltaTexto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="fechaBajaTexto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="horarioTexto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="image" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         <element name="keyWords" type="{http://webservices/}keyWord" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="remuneracion" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtOferta", propOrder = {
    "ciudad",
    "coste",
    "departamento",
    "descripcion",
    "duracion",
    "estado",
    "fechaAltaTexto",
    "fechaBajaTexto",
    "horarioTexto",
    "image",
    "keyWords",
    "nombre",
    "remuneracion"
})
public class DtOferta {

    protected String ciudad;
    protected Float coste;
    protected String departamento;
    protected String descripcion;
    protected int duracion;
    @XmlSchemaType(name = "string")
    protected EstadoOferta estado;
    protected String fechaAltaTexto;
    protected String fechaBajaTexto;
    protected String horarioTexto;
    protected byte[] image;
    protected List<KeyWord> keyWords;
    protected String nombre;
    protected Float remuneracion;

    /**
     * Obtiene el valor de la propiedad ciudad.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * Define el valor de la propiedad ciudad.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCiudad(String value) {
        this.ciudad = value;
    }

    /**
     * Obtiene el valor de la propiedad coste.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getCoste() {
        return coste;
    }

    /**
     * Define el valor de la propiedad coste.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setCoste(Float value) {
        this.coste = value;
    }

    /**
     * Obtiene el valor de la propiedad departamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * Define el valor de la propiedad departamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartamento(String value) {
        this.departamento = value;
    }

    /**
     * Obtiene el valor de la propiedad descripcion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Define el valor de la propiedad descripcion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcion(String value) {
        this.descripcion = value;
    }

    /**
     * Obtiene el valor de la propiedad duracion.
     * 
     */
    public int getDuracion() {
        return duracion;
    }

    /**
     * Define el valor de la propiedad duracion.
     * 
     */
    public void setDuracion(int value) {
        this.duracion = value;
    }

    /**
     * Obtiene el valor de la propiedad estado.
     * 
     * @return
     *     possible object is
     *     {@link EstadoOferta }
     *     
     */
    public EstadoOferta getEstado() {
        return estado;
    }

    /**
     * Define el valor de la propiedad estado.
     * 
     * @param value
     *     allowed object is
     *     {@link EstadoOferta }
     *     
     */
    public void setEstado(EstadoOferta value) {
        this.estado = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaAltaTexto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaAltaTexto() {
        return fechaAltaTexto;
    }

    /**
     * Define el valor de la propiedad fechaAltaTexto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaAltaTexto(String value) {
        this.fechaAltaTexto = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaBajaTexto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaBajaTexto() {
        return fechaBajaTexto;
    }

    /**
     * Define el valor de la propiedad fechaBajaTexto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaBajaTexto(String value) {
        this.fechaBajaTexto = value;
    }

    /**
     * Obtiene el valor de la propiedad horarioTexto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHorarioTexto() {
        return horarioTexto;
    }

    /**
     * Define el valor de la propiedad horarioTexto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHorarioTexto(String value) {
        this.horarioTexto = value;
    }

    /**
     * Obtiene el valor de la propiedad image.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getImage() {
        return image;
    }

    /**
     * Define el valor de la propiedad image.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setImage(byte[] value) {
        this.image = value;
    }

    /**
     * Gets the value of the keyWords property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the keyWords property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKeyWords().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KeyWord }
     * 
     * 
     * @return
     *     The value of the keyWords property.
     */
    public List<KeyWord> getKeyWords() {
        if (keyWords == null) {
            keyWords = new ArrayList<>();
        }
        return this.keyWords;
    }

    /**
     * Obtiene el valor de la propiedad nombre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Define el valor de la propiedad nombre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombre(String value) {
        this.nombre = value;
    }

    /**
     * Obtiene el valor de la propiedad remuneracion.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getRemuneracion() {
        return remuneracion;
    }

    /**
     * Define el valor de la propiedad remuneracion.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setRemuneracion(Float value) {
        this.remuneracion = value;
    }

}
