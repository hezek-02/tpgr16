package logica.modelos;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import logica.datatypes.DtOferta;
import logica.datatypes.EstadoOferta;

public class OfertaLab {
	private int idOferta;
	private String nombre;
	private String descripcion;
	private String horario;
	private Float remuneracion;
	private String departamento;
	private String ciudad;
	private LocalDate fechaAlta;
	private Float coste;
	private byte[] image;
	private EstadoOferta estado; 
	private int duracion;
	private Map<String, KeyWord> keywords;
	private CompraPaquete compraPaq;
	private TipoPublicacion tipo;
	private List<Postulacion> postulaciones;
    private Empresa empresa;
    private List<Postulante> favoritos;
    private int visitas;

	public OfertaLab(String nombre, String descripcion, byte[] image, String ciudad, String departamento, String horario, Float remuneracion, LocalDate fecha, Float coste) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.setImage(image);
		this.ciudad = ciudad;
		this.departamento = departamento;
		this.horario = horario;
		this.remuneracion = remuneracion;
		this.fechaAlta = fecha;
		this.coste = coste;
		this.tipo = null;
		this.empresa = null;
		this.keywords = new HashMap<String, KeyWord>();
		this.postulaciones = new ArrayList<Postulacion>();
		this.compraPaq = null;
		this.setEstado(EstadoOferta.INGRESADO);
		this.favoritos = new ArrayList<Postulante>();
		this.visitas = 0;
	}
	
	public OfertaLab() {}
	
	public int getId() {
		return idOferta;
	}
	public void setId(int id) {
		this.idOferta = id;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}
	
	public String getCiudad() {
		return this.ciudad;
	}
	
	public String getDepartamento() {
		return this.departamento;
	}
	
	public String getHorario() {
		return this.horario;
	}
	
	public int getDuracion() {
		return this.duracion;
	}
	
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	
	public Float getRemuneracion() {
		return this.remuneracion;
	}
	public LocalDate getFecha() {
		return this.fechaAlta;
	}
	public Float getCoste() {
		return this.coste;
	}
	public TipoPublicacion getTipo() {
		return this.tipo;
	}
	public Empresa getEmpresa() {
		return this.empresa;
	}
	public void setTipo(TipoPublicacion tipo) {
		this.tipo = tipo;
	}
	
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	public void agregarKeyword(KeyWord keyword) {
		this.keywords.put(keyword.getNombreKey(), keyword);
	}
	
	public KeyWord[] getKeyWords() {
		Collection<KeyWord> keywordCollection = keywords.values();
	    KeyWord[] keywordArray = keywordCollection.toArray(new KeyWord[0]);
	    return keywordArray;
	}
	
	public boolean contieneKeyWord(String[] nombreKey) {
		boolean contiene = true;
	    for (String key : nombreKey) {
	        if (!this.keywords.containsKey(key)) {
	        	contiene = false;
	        	break;
	        }
	    }
	    return contiene; 
	}
	
	public DtOferta obtenerDtOferta() {
		return new DtOferta(getNombre(), getDescripcion(), getImage(), getCiudad(), getDepartamento(), getHorario(), getRemuneracion(), getFecha(), getCoste(), getDuracion(), getEstado(), getKeyWords());
	}

	public void agregarPostulante(Postulacion post) {
		this.postulaciones.add(post);
	}

	public List<Postulacion> getPostulaciones() {
		return this.postulaciones;
	}
	
	public boolean ofertaEstaVencida() {
		return LocalDate.now().isAfter(fechaAlta.plusDays(this.tipo.getDuracion()));	
	}

	public boolean ofertaEstaVencida(LocalDate fechaPostu) {
		return fechaPostu.isAfter(fechaAlta.plusDays(this.tipo.getDuracion()));	
	}
	
	public EstadoOferta getEstado() {
		return estado;
	}
	
	public void finalizarOfertaEliminar() {
        for(Postulacion postulacion: this.postulaciones) {
			postulacion.eliminarPostulacion();
        }
		this.postulaciones.clear(); 
		if (empresa != null)
			this.empresa.eliminarOferta(this); 
		this.compraPaq = null;
		this.empresa = null;
		this.tipo = null; 
		for (KeyWord key : keywords.values()) {
		    key.eliminarOfertaLab(this);
		}
		this.keywords.clear();
		this.favoritos.clear();
	}
	
	public void setEstado(EstadoOferta nuevoEstado) {
		this.estado = nuevoEstado;
		if (nuevoEstado == EstadoOferta.FINALIZADA) {
			finalizarOfertaEliminar();
		}
	}
	
	public byte[] getImage() {
		return image;
	}
	
	public void setImage(byte[] img) {
		this.image = img;
	}
	
	public void agregarCompraPaquete(CompraPaquete paqueteComprado) {
		this.compraPaq = paqueteComprado;
	}
	
	public CompraPaquete obtenerCompraPaquete( ) {
		return this.compraPaq;
	}

	public boolean yaExisteRanking(int rank) {
		for (Postulacion postulacion : postulaciones) {
			if (postulacion.getRanking() == rank) {
				return true;
			}
		}
		return false;
	}
	
	public void agregarFavorito(Postulante post) {
		this.favoritos.add(post);
	}
	
	public void eliminarFavorito(Postulante post) {
		this.favoritos.remove(post);
	}
	
	public List<Postulante> getPostulantesFavoritos() {
		return this.favoritos;
	}
	
	public int getVisitas() {
		return this.visitas;
	}
	
	public void setVisitas(int visitas) {
		this.visitas = visitas;
	}
	
	public void visitar() {
		this.visitas++;
	}
}

