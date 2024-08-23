package logica.modelos;

import java.util.List;

import java.time.LocalDate;
import java.util.ArrayList;
import logica.datatypes.DtPostulante;
import logica.datatypes.DtUsuario;

public class Postulante extends Usuario{
	private String pais;
	private LocalDate fechNac;
    private List<Postulacion> postulaciones;
    private List<OfertaLab> Ofertasfavoritas;
	
	public Postulante(String nickname, String nombre, String apellido, String password, String correo, byte[] image, LocalDate fechNac, String pais) {
		super(nickname, nombre, apellido, password, correo, image);
		this.fechNac = fechNac;
		this.pais = pais;
		this.postulaciones = new ArrayList<Postulacion>();
		this.Ofertasfavoritas = new ArrayList<OfertaLab>();
	}

	public Postulacion obtenerPostulacion(OfertaLab oferta){
        for (Postulacion postulacion : postulaciones) {
            if (postulacion.getOferta().getNombre().equals(oferta.getNombre())) {
                return postulacion;
            }
        }
        return null;
    }
	
	public LocalDate getFechaNacimiento() {
		return fechNac;
	}

	public void setFechaNac(LocalDate fechNac) {
		this.fechNac = fechNac;
	}
	
	public String getNacionalidad() {
		return pais;
	}

	public void setNacionalidad(String pais) {
		this.pais = pais;
	}
	
	//CV vacio
	@Override
	public DtUsuario obtenerDtUsu() {
		return new DtPostulante(this.getNickname(),  this.getNombre(), this.getApellido(), this.getCorreo(), this.getPassword(), this.getImage(), this.getNacionalidad(), this.getFechaNacimiento(), "");
	}
	
	public void agregarPostulacion(Postulacion post) {
		this.postulaciones.add(post);
	}
	
	public boolean existePostulacion(OfertaLab oferta) {
		for (Postulacion post : this.postulaciones) {
			if (post.getOferta() == oferta)
				return true;
		}
		return false;
	}
	
	public List<OfertaLab> getOfertas() {
		List<OfertaLab> ofertas = new ArrayList<>();
		for (Postulacion post : this.postulaciones) {
			ofertas.add(post.getOferta());
		}
		return ofertas;
	}

	public void eliminarPostulacion(Postulacion postulacion) {
	    this.postulaciones.remove(postulacion);
	}
	
	public void agregarOfertaFavorita(OfertaLab oferta) {
		this.Ofertasfavoritas.add(oferta);
	}
	
	public void eliminarOfertaFavorita(OfertaLab oferta) {
		this.Ofertasfavoritas.remove(oferta);
	}

	public List<OfertaLab> getOfertasFavoritas() {
		return this.Ofertasfavoritas;
	}
}
