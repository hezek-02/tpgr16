
package logica.controladores;

import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import excepciones.ExisteKeyWord;
import excepciones.FechaInvalidaException;
import excepciones.NoPoseePostulacionesException;
import excepciones.TipoPubliRegistradoException;
import excepciones.UsuarioNoExisteException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import excepciones.CostoMayorACeroExcepcion;
import excepciones.DescuentoMayorACeroExcepcion;
import excepciones.OfertaLaboralYaExisteException;
import excepciones.OfertaNoExisteException;
import excepciones.PaqueteNoExisteException;
import excepciones.PaqueteNoTieneMasTipoPubExcepcion;
import excepciones.PaqueteRegistrado;
import excepciones.TipoPubNoExisteException;
import excepciones.TipoPubRegistradoEnPaqueteException;
import logica.datatypes.DtEmpresa;
import logica.datatypes.DtKeyWord;
import logica.datatypes.DtTipoPub;
import logica.datatypes.EstadoOferta;
import logica.datatypes.DtOferta;
import logica.datatypes.DtPaqPub;
import logica.datatypes.DtPaquetePub;
import logica.datatypes.DtPostulacion;
import logica.datatypes.DtPostulante;
import logica.interfaces.IOfertaLab;
import logica.manejadores.ManejadorOfertaLaboral;
import logica.manejadores.ManejadorTipoPublicacion;
import logica.manejadores.ManejadorUsuarios;
import logica.modelos.CompraPaquete;
import logica.modelos.Empresa;
import logica.modelos.KeyWord;
import logica.modelos.OfertaLab;
import logica.modelos.PaqPub;
import logica.modelos.PaqueteTipoPub;
import logica.modelos.Postulacion;
import logica.modelos.Postulante;
import logica.modelos.TipoPublicacion;
import logica.modelos.Usuario;
import persistence.EmpresaJPA;
import persistence.OfertaLabJPA;
import persistence.PostulacionJPA;
import persistence.PostulanteJPA;
import logica.manejadores.ManejadorPaqueteTipoPub;

public class ControladorOfertaLab implements IOfertaLab {
	
	
	public ControladorOfertaLab() {}
	
	public void ingresarKeyWord(String keyN) throws ExisteKeyWord{
		ManejadorOfertaLaboral manejadorOfertas = ManejadorOfertaLaboral.getInstance();
		if (manejadorOfertas.existeKeyWord(keyN))
			throw new ExisteKeyWord("La KeyWord ya se encuentra registrada");
		KeyWord keyword = new KeyWord(keyN);
		manejadorOfertas.agregarKeyWord(keyN, keyword); 
	}
	 
	public void ingresarDatosTipoPublicacion(String nombre, String descripcion, int duracion, int exposicion, Float costo, LocalDate fechaAlta) throws TipoPubliRegistradoException, FechaInvalidaException {
		if (fechaAlta.isAfter(LocalDate.now()) || fechaAlta.isBefore(LocalDate.now().minusYears(80)))
			throw new FechaInvalidaException("La fecha debe tener menos de 80 años y ser menor o igual a la actual");
		ManejadorTipoPublicacion manejador = ManejadorTipoPublicacion.getInstance();
		if (manejador.existeTipoPublicacion(nombre)) 
			throw new TipoPubliRegistradoException("El nombre del tipo de publicación se encuentra registrado");
		TipoPublicacion tipoP = new TipoPublicacion(nombre, descripcion, duracion, exposicion, costo, fechaAlta);
		manejador.agregarTipoPublicacion(tipoP);
	}
	
	//sin paquete
	public void ingresarDatosOfertaLaboral(String nombre, String descripcion, byte[] img, String horario, Float remuneracion, String ciudad, String departamento, LocalDate fechaDeAlta, String[] keywords, String empresaNom, String tipoPubNom) throws OfertaLaboralYaExisteException, FechaInvalidaException, UsuarioNoExisteException, CostoMayorACeroExcepcion {
		if (remuneracion <= 0) {
			throw new CostoMayorACeroExcepcion("La remuneración no puede ser un negativa, ni cero");
		}
		if (fechaDeAlta.isAfter(LocalDate.now()) || fechaDeAlta.isBefore(LocalDate.now().minusYears(80))) {
			throw new FechaInvalidaException("La fecha debe tener menos de 80 años y ser menor o igual a la actual");
		}
		ManejadorOfertaLaboral manejadorOferta = ManejadorOfertaLaboral.getInstance();	
		if (manejadorOferta.obtenerOferta(nombre) != null) {
			throw new OfertaLaboralYaExisteException("El nombre de la Oferta ya se encuentra registrado");
		}
		Empresa empresa = (Empresa) ManejadorUsuarios.getInstance().obtenerUsuario(empresaNom);
		TipoPublicacion tipo = (TipoPublicacion) ManejadorTipoPublicacion.getInstance().obtenerTipoPublicacion(tipoPubNom);

		if (img == null ||  img.length == 0) {
			try {
				img =  getFile("ofertas-img/Generica.jpg");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		OfertaLab oferta = new OfertaLab(nombre, descripcion , img, ciudad, departamento, horario, remuneracion, fechaDeAlta, tipo.getCosto());
		oferta.setDuracion(tipo.getDuracion());
		oferta.setEmpresa(empresa);
		oferta.setTipo(tipo);
		manejadorOferta.agregarOferta(oferta);
		empresa.setOferta(oferta);

		if (keywords!=null) {
			int largoKeys = keywords.length;
			KeyWord keyW=null;
			for (int i=0; i<largoKeys; i++) {
				keyW = manejadorOferta.obtenerKeyWord(keywords[i]);
				oferta.agregarKeyword(keyW); //doble linkeo
				keyW.agregarOfertaLab(oferta);
			}
		}
	}
	
	//con paquete
	public void ingresarDatosOfertaLaboral(String nombre, String descripcion, byte[] img, String horario, 
			Float remuneracion, String ciudad, String departamento, LocalDate fechaDeAlta, 
			String[] keywords, String empresaNom, String tipoPubNom, String paquetePubNom) 
			throws OfertaLaboralYaExisteException, FechaInvalidaException, UsuarioNoExisteException, CostoMayorACeroExcepcion, PaqueteNoTieneMasTipoPubExcepcion, TipoPubNoExisteException {
		
		ManejadorOfertaLaboral manejadorOferta = ManejadorOfertaLaboral.getInstance();	
		if (remuneracion <= 0) {
			throw new CostoMayorACeroExcepcion("La remuneración no puede ser un negativa, ni cero");
		}
		if (fechaDeAlta.isAfter(LocalDate.now()) || fechaDeAlta.isBefore(LocalDate.now().minusYears(80))) {
			throw new FechaInvalidaException("La fecha debe tener menos de 80 años y ser menor o igual a la actual");
		}
		if (manejadorOferta.obtenerOferta(nombre) != null) { 
			throw new OfertaLaboralYaExisteException("El nombre de la Oferta ya se encuentra registrado");
		}
		Empresa empresa = (Empresa) ManejadorUsuarios.getInstance().obtenerUsuario(empresaNom);
		TipoPublicacion tipo = (TipoPublicacion) ManejadorTipoPublicacion.getInstance().obtenerTipoPublicacion(tipoPubNom);
		PaqueteTipoPub paqueteTipoPub = ManejadorPaqueteTipoPub.getInstance().obtenerPaqueteTipoPub(paquetePubNom);
		
		float descuentoPaq = paqueteTipoPub.obtenerDtPaq().getDescuento(); //defino costo en base a paquete
		float costo = tipo.getCosto() * (1 - descuentoPaq/100);
		CompraPaquete paqueteComprado = empresa.obtenerPaqueteComprado(paquetePubNom);
			try {
				if (ManejadorPaqueteTipoPub.getInstance().obtenerCantPaqPubEmpresa(paquetePubNom, tipoPubNom, empresaNom)<=0) {
					throw new PaqueteNoTieneMasTipoPubExcepcion("El paquete ya no tiene el tipo de publicación disponible");
				}
			} catch (UsuarioNoExisteException | TipoPubNoExisteException | PaqueteNoTieneMasTipoPubExcepcion e) {
				e.printStackTrace();
			}
		paqueteComprado.reducirCantidadTipoPub(tipo.getDtTipoPublicacion()); //reduzco cantidad de paquete-tipoPub
		
		if (img == null ||  img.length == 0) {
			try {
				img =  getFile("ofertas-img/Generica.jpg");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		OfertaLab oferta = new OfertaLab(nombre, descripcion, img, ciudad, departamento, horario, remuneracion, fechaDeAlta, costo);
		oferta.setDuracion(tipo.getDtTipoPublicacion().getDuracion());
		oferta.setEmpresa(empresa);
		oferta.setTipo(tipo);
		manejadorOferta.agregarOferta(oferta);
		empresa.setOferta(oferta);
		oferta.agregarCompraPaquete(paqueteComprado);
		
		if (keywords!=null) {
			int largoKeys = keywords.length;
			KeyWord keyW=null;
			for (int i=0; i<largoKeys; i++) {
				keyW = manejadorOferta.obtenerKeyWord(keywords[i]);
				oferta.agregarKeyword(keyW); //doble linkeo
				keyW.agregarOfertaLab(oferta);
			}
		}
	}

	public DtTipoPub[] obtenerTipoPublicaciones() throws TipoPubNoExisteException {
		ManejadorTipoPublicacion manejador = ManejadorTipoPublicacion.getInstance();
		TipoPublicacion[] tipos = manejador.obtenerTiposPublicaciones();
		if (tipos == null || tipos.length == 0 ) {
			throw new TipoPubNoExisteException("No hay tipos de publicacion registrados");
		}
		List<DtTipoPub> dtTipoPublicaciones = new ArrayList<>();
		for (TipoPublicacion tipoPublicacion : tipos) {
			dtTipoPublicaciones.add(tipoPublicacion.getDtTipoPublicacion());
		}
		return dtTipoPublicaciones.toArray(new DtTipoPub[0]);
	}
	
	public  DtTipoPub[] obtenerTipoPublicaciones(String paqueteNombre) throws TipoPubNoExisteException, PaqueteNoExisteException{
		ManejadorPaqueteTipoPub manejadorPaquete = ManejadorPaqueteTipoPub.getInstance();
		PaqueteTipoPub paquete = manejadorPaquete.obtenerPaqueteTipoPub(paqueteNombre);
		
		if (paquete == null) {
			throw new PaqueteNoExisteException("El paquete no existe");
		}
		
		ManejadorTipoPublicacion manejador = ManejadorTipoPublicacion.getInstance();
		TipoPublicacion[] tipos = manejador.obtenerTiposPublicaciones(paqueteNombre);
		
		if (tipos == null || tipos.length == 0) {
			throw new TipoPubNoExisteException("No hay tipos de publicacion registrados");
		}
		
		List<DtTipoPub> dtTipoPublicaciones = new ArrayList<>();

		for (TipoPublicacion tipoPublicacion : tipos) {
			dtTipoPublicaciones.add(tipoPublicacion.getDtTipoPublicacion());
		}
		return dtTipoPublicaciones.toArray(new DtTipoPub[0]);
	}


	public DtKeyWord[] obtenerKeyWords() {
		ManejadorOfertaLaboral manejadorOfertas = ManejadorOfertaLaboral.getInstance();
		KeyWord[] keysW = manejadorOfertas.obtenerKeyWords();
		int largo = keysW.length;
		DtKeyWord[] dtKeyW = new DtKeyWord[largo];
		for (int i=0 ; i<largo ; i++) {
			dtKeyW[i] = keysW[i].obtenerDtKeyWord();
		}
		return dtKeyW;
	}
	
	//crear paquete 
	public void ingresarDatosPaquete(String nombre, String descripcion, byte[] img, LocalDate fechaAlta, int periodoValidez, float descuento)throws PaqueteRegistrado, DescuentoMayorACeroExcepcion, CostoMayorACeroExcepcion, FechaInvalidaException {
		if (fechaAlta.isAfter(LocalDate.now()) || fechaAlta.isBefore(LocalDate.now().minusYears(80)))
			throw new FechaInvalidaException("La fecha debe tener menos de 80 años y ser menor o igual a la actual");
		ManejadorPaqueteTipoPub manejadorPaq = ManejadorPaqueteTipoPub.getInstance();
		if (descuento < 0 || descuento > 100)
			throw new DescuentoMayorACeroExcepcion("El descuento no puede ser menor a 0 ni mayor a 100");
		if (manejadorPaq.existePaqueteTipoPub(nombre))
			throw new PaqueteRegistrado("El nombre del paquete ya se encuentra registrado");
		if (img == null ||  img.length == 0) {
			try {
				img =  getFile("paquetes-img/Generica.jpg");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		PaqueteTipoPub paquete = new PaqueteTipoPub(nombre, descripcion, img, fechaAlta, periodoValidez, descuento);
		manejadorPaq.agregarPaqueteTipoPublicacion(paquete);
	}
	
	public DtOferta obtenerDtOferta(String nombreOferta) throws OfertaNoExisteException {
		ManejadorOfertaLaboral manejadorOferta = ManejadorOfertaLaboral.getInstance();
		if (manejadorOferta.obtenerOferta(nombreOferta) != null) {
			return manejadorOferta.obtenerDtOferta(nombreOferta);
		}else {
			return obtenerOfertaFinalizada(nombreOferta);
		}
	}
	
	public DtOferta[] obtenerOfertas(String usuarioNom) throws UsuarioNoExisteException {
		ManejadorUsuarios manejador = ManejadorUsuarios.getInstance();
		Usuario usuario = manejador.obtenerUsuario(usuarioNom);
		List<OfertaLab> ofertas = usuario.getOfertas();
		List<DtOferta> dtOfertaList = new ArrayList<>();
		for (OfertaLab oferta : ofertas) {
			DtOferta dtOferta = oferta.obtenerDtOferta();
			dtOfertaList.add(dtOferta);
		}
		return dtOfertaList.toArray(new DtOferta[dtOfertaList.size()]);
	}
	
	public DtOferta[] obtenerOfertasIngresadas(String empresaNom) throws UsuarioNoExisteException {
		DtOferta[] dtOfertas = obtenerOfertas(empresaNom);
		List<DtOferta> dtOfertaList = new ArrayList<>();
		for (DtOferta oferta : dtOfertas) {
			if (oferta.getEstado()==EstadoOferta.INGRESADO) {
				dtOfertaList.add(oferta);
			}
		}
		return dtOfertaList.toArray(new DtOferta[dtOfertaList.size()]);
	}
	
	public DtOferta[] obtenerOfertasConfirmadas(String empresaNom) throws UsuarioNoExisteException {
	    Empresa empresa = (Empresa) ManejadorUsuarios.getInstance().obtenerUsuario(empresaNom);
	    List<OfertaLab> ofertas = empresa.getOfertas();
	    List<DtOferta> validDtOfertas = new ArrayList<>();
	    for (OfertaLab oferta : ofertas) {
	        if (oferta.getEstado()==EstadoOferta.CONFIRMADO) {
	            validDtOfertas.add(oferta.obtenerDtOferta());
	        }
	    }
	    return validDtOfertas.toArray(new DtOferta[validDtOfertas.size()]);		
	}
	
	public DtOferta[] obtenerOfertasFinalizadas() {
		EntityManagerFactory entManagerFactory = null;
		EntityManager entManager = null;
		List<DtOferta> ofertasEncontradasDt = new ArrayList<DtOferta>();
		try {
		entManagerFactory = Persistence.createEntityManagerFactory("Servidor");
		entManager = entManagerFactory.createEntityManager();
		TypedQuery<OfertaLabJPA> query = entManager.createQuery("SELECT v FROM OfertaLabJPA v", OfertaLabJPA.class);
		List<OfertaLabJPA> ofertasEncontradas = query.getResultList();		
		for (OfertaLabJPA oferta : ofertasEncontradas) 
			ofertasEncontradasDt.add(new DtOferta(oferta.getNombre(), oferta.getDescripcion(), oferta.getImage(), oferta.getCiudad(), oferta.getDepartamento(), oferta.getHorario(), oferta.getRemuneracion(), oferta.getFecha_Alta(), oferta.getCosto(), 0, EstadoOferta.FINALIZADA, null));
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			entManager.close();
			entManagerFactory.close();
		}
		return ofertasEncontradasDt.toArray(new DtOferta[ofertasEncontradasDt.size()]);
	}
	
	public DtOferta obtenerOfertaFinalizada(String nomOferta) {
		EntityManagerFactory entManagerFactory = null;
		EntityManager entManager = null;
		OfertaLabJPA ofertaLabDb = null;
		DtOferta ofertaFinalizadaDt = null;
		try {
			entManagerFactory = Persistence.createEntityManagerFactory("Servidor");
			entManager = entManagerFactory.createEntityManager();
	        
			TypedQuery<OfertaLabJPA> query = entManager.createQuery(
				    "SELECT o FROM OfertaLabJPA o WHERE o.nombre = :nomOferta",
				    OfertaLabJPA.class
				);
	        query.setParameter("nomOferta", nomOferta);
	        try {
		        ofertaLabDb = query.getSingleResult();
		    } catch (NoResultException e) {
		    	throw new OfertaNoExisteException("No se encontró oferta");
		    }   
	        ofertaFinalizadaDt = new DtOferta(ofertaLabDb.getNombre(), ofertaLabDb.getDescripcion(), ofertaLabDb.getImage(), ofertaLabDb.getCiudad(), ofertaLabDb.getDepartamento(), ofertaLabDb.getHorario(), ofertaLabDb.getRemuneracion(), ofertaLabDb.getFecha_Alta(), ofertaLabDb.getCosto(), 0, EstadoOferta.FINALIZADA, null);

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			entManager.close();
			entManagerFactory.close();
		}
		return ofertaFinalizadaDt;
	}
	
	//ofertas confirmadas o finalizadas
	public DtOferta[] obtenerOfertasFinalizadas(String empresaNom) throws UsuarioNoExisteException { 
		EntityManagerFactory entManagerFactory = null;
	    EntityManager entityManager = null;
	    List<OfertaLabJPA> ofertasFinalizadas = null;
	    List<DtOferta> dtOfertasFin = new ArrayList<DtOferta>();
	    try {
			entManagerFactory = Persistence.createEntityManagerFactory("Servidor");
			entityManager = entManagerFactory.createEntityManager();
	        
			TypedQuery<OfertaLabJPA> query = entityManager.createQuery(
				    "SELECT o FROM OfertaLabJPA o WHERE o.empresa.nickname = :nickEmp",
				    OfertaLabJPA.class
				);
	        query.setParameter("nickEmp", empresaNom);
	        ofertasFinalizadas = query.getResultList();
	        if (ofertasFinalizadas == null || ofertasFinalizadas.isEmpty()) {
	        	throw new OfertaNoExisteException("No posee ofertas");
	        }
	        for (OfertaLabJPA oferta : ofertasFinalizadas) 
	        	dtOfertasFin.add(new DtOferta(oferta.getNombre(), oferta.getDescripcion(), oferta.getImage(), oferta.getCiudad(), oferta.getDepartamento(), oferta.getHorario(), oferta.getRemuneracion(), oferta.getFecha_Alta(), oferta.getCosto(), 0, EstadoOferta.FINALIZADA, null));
	    } catch (Exception e) {
	    	e.printStackTrace();
	    } finally {
	         entityManager.close();
	         entManagerFactory.close();
	    }
		return dtOfertasFin.toArray(new DtOferta[dtOfertasFin.size()]);
	}
	   
	
	public DtOferta[] obtenerOfertasConfirmadas(String empresaNom,	String[] keywordArray) throws UsuarioNoExisteException {
	    Empresa empresa = (Empresa) ManejadorUsuarios.getInstance().obtenerUsuario(empresaNom);
	    List<OfertaLab> ofertas = empresa.getOfertas();
	    List<DtOferta> validDtOfertas = new ArrayList<>();
	    for (OfertaLab oferta : ofertas) {
	        if (oferta.getEstado()==EstadoOferta.CONFIRMADO && oferta.contieneKeyWord(keywordArray)) {
	            validDtOfertas.add(oferta.obtenerDtOferta());
	        }
	    }
	    return validDtOfertas.toArray(new DtOferta[validDtOfertas.size()]);		
	}
	
	public boolean vencida(String nomOferta) {
		OfertaLab oferta = ManejadorOfertaLaboral.getInstance().obtenerOferta(nomOferta);
		if (oferta != null && oferta.getEstado() != EstadoOferta.FINALIZADA)
			return 	oferta.ofertaEstaVencida();
		else
			return false;
	}
	
	public  DtPaquetePub obtenerPaquete(String nombrePaquete) throws PaqueteNoExisteException{
	    ManejadorPaqueteTipoPub manejadorPaquetes = ManejadorPaqueteTipoPub.getInstance();
	    return manejadorPaquetes.obtenerDtPaquete(nombrePaquete);		
	}
	
	public DtOferta[] obtenerOfertasRechazadas(String empresaNombre) throws UsuarioNoExisteException {
	    Empresa empresa = (Empresa) ManejadorUsuarios.getInstance().obtenerUsuario(empresaNombre);
	    List<DtOferta> ofertasRechazadas = new ArrayList<>();
	    List<OfertaLab> ofertas = empresa.getOfertas();
	    for (OfertaLab oferta : ofertas) {
	        if (oferta.getEstado()==EstadoOferta.RECHAZADO) {
	        	ofertasRechazadas.add(oferta.obtenerDtOferta());
	        }
	    }
	    return ofertasRechazadas.toArray(new DtOferta[ofertasRechazadas.size()]);		
	}
	
	public DtPostulacion[] obtenerPostulaciones(String ofertaNom) throws NoPoseePostulacionesException {
	    ManejadorOfertaLaboral manejador = ManejadorOfertaLaboral.getInstance();
	    if (manejador.obtenerOferta(ofertaNom) != null) {

		    OfertaLab oferta = manejador.obtenerOferta(ofertaNom);
		    List<Postulacion> postulaciones = oferta.getPostulaciones();
		    if (postulaciones.isEmpty()) {
		        throw new NoPoseePostulacionesException("No existen postulaciones");
		    }
		    List<DtPostulacion> dtPostulacionesList = new ArrayList<>();
		    for (Postulacion postulacion : postulaciones) {
		        dtPostulacionesList.add(postulacion.getDtPostulacion());
		    }
		    return dtPostulacionesList.toArray(new DtPostulacion[0]);
	    }else {
	    	DtPostulacion[] dtPostulaciones = obtenerPostulacionesFinalizadas(ofertaNom);
	        if ( dtPostulaciones == null || dtPostulaciones.length == 0) {
	        	throw new NoPoseePostulacionesException("No existen postulaciones");
	        } 
	        return dtPostulaciones;
	    }
	}
	
	private DtPostulacion[] obtenerPostulacionesFinalizadas(String ofertaNom) throws NoPoseePostulacionesException {
		EntityManagerFactory entManagerFactory = null;
	    EntityManager entityManager = null;
	    List<PostulacionJPA> postulacionesFinalizadas = null;
	    List<DtPostulacion> dtPostulaciones = new ArrayList<DtPostulacion>();
	    try {
			entManagerFactory = Persistence.createEntityManagerFactory("Servidor");
			entityManager = entManagerFactory.createEntityManager();
	        
			TypedQuery<PostulacionJPA> query = entityManager.createQuery(
				    "SELECT p FROM PostulacionJPA p WHERE p.oferta.nombre = :ofertaNom",
				    PostulacionJPA.class
				);
	        query.setParameter("ofertaNom", ofertaNom);
	        postulacionesFinalizadas = query.getResultList();
	        for (PostulacionJPA pos : postulacionesFinalizadas) {
	        	dtPostulaciones.add(new DtPostulacion(pos.getCv(), pos.getMotivacion(), pos.getFecha_postulacion(), pos.getOferta().getNombre(), pos.getPostulante().getNickname(), ""));
	        }

	    } catch (Exception e) {
	    	e.printStackTrace();
	    } finally {
	         entityManager.close();
	         entManagerFactory.close();
	    }
		return dtPostulaciones.toArray(new DtPostulacion[0]);
	}
	
	public DtPaquetePub[] obtenerPaquetes() throws PaqueteNoExisteException {
		ManejadorPaqueteTipoPub manejadorPaquetes = ManejadorPaqueteTipoPub.getInstance();
		PaqueteTipoPub[] paquetes = manejadorPaquetes.obtenerPaquetesTipoPub();
		if (paquetes == null || paquetes.length == 0) {
			throw new PaqueteNoExisteException("No hay paquetes registrados");
		}
		DtPaquetePub[] dtPaquetes = new DtPaquetePub[paquetes.length];
		for (int i = 0; i < paquetes.length; i++) {
			dtPaquetes[i] = paquetes[i].obtenerDtPaq();
		}
		return dtPaquetes;
	}
	
	public DtPaquetePub[] obtenerPaquetesNoComprados() throws PaqueteNoExisteException{
		ManejadorPaqueteTipoPub manejadorPaquete = ManejadorPaqueteTipoPub.getInstance();
		PaqueteTipoPub[] paquetes = manejadorPaquete.obtenerPaquetesTipoPubSinComprar();
		if (paquetes == null || paquetes!=null && paquetes.length==0) {
			throw new PaqueteNoExisteException("No hay paquetes sin comprar registrados");
		}
		DtPaquetePub[] dtPaquetes = new DtPaquetePub[paquetes.length];
		for (int i = 0; i < paquetes.length; i++) {
			dtPaquetes[i] = paquetes[i].obtenerDtPaq();
		}
		return dtPaquetes;
	}

	public DtTipoPub obtenerTipoPublicacion(String tipoPubNombre) throws TipoPubNoExisteException {
		ManejadorTipoPublicacion manejador = ManejadorTipoPublicacion.getInstance();
		return manejador.obtenerDtTipoPublicacion(tipoPubNombre);
	}
	
	public DtPaqPub[] obtenerPaqPubs(String paquetePubNombre) {
	    ManejadorPaqueteTipoPub mPaqTipoP = ManejadorPaqueteTipoPub.getInstance();
	    PaqueteTipoPub paqueteTipoPub = mPaqTipoP.obtenerPaqueteTipoPub(paquetePubNombre);
	    List<PaqPub> colPaqPub = paqueteTipoPub.obtenerColPaqPub();

	    List<DtPaqPub> dtPaqPubList = new ArrayList<>();

	    for (PaqPub paqPub : colPaqPub) {
	        dtPaqPubList.add(paqPub.getDtPaqPub());
	    }
	    return dtPaqPubList.toArray(new DtPaqPub[0]);
	}
	
	public DtTipoPub[] obtenerTipoPublicacionesQueNoEstanEnPaquete(String paquete)
			throws TipoPubNoExisteException, PaqueteNoExisteException {
		ManejadorTipoPublicacion manejador = ManejadorTipoPublicacion.getInstance();
		TipoPublicacion[] tipos = manejador.obtenerTiposPublicacionesQueNoEstanEnPaquete(paquete);
		if (tipos == null) {
			throw new TipoPubNoExisteException("No hay tipos de publicacion registrados");
		}
		List<DtTipoPub> dtTipoPublicaciones = new ArrayList<>();
		for (TipoPublicacion tipoPublicacion : tipos) {
			dtTipoPublicaciones.add(tipoPublicacion.getDtTipoPublicacion());
		}
		return dtTipoPublicaciones.toArray(new DtTipoPub[0]);
	}
	
	public void agregarTipoPubAPaquete(int cantidad, String tipoPubNombre, String paquetePubNombre) throws TipoPubRegistradoEnPaqueteException{
		ManejadorPaqueteTipoPub ManejadorPaquete = ManejadorPaqueteTipoPub.getInstance();
		ManejadorTipoPublicacion manejadorTipoPublicacion = ManejadorTipoPublicacion.getInstance();
		
		PaqueteTipoPub pakete = ManejadorPaquete.obtenerPaqueteTipoPub(paquetePubNombre);
		TipoPublicacion tipo = manejadorTipoPublicacion.obtenerTipoPublicacion(tipoPubNombre);
		
		if (pakete.existeTipoPub(tipo)) {
			throw new TipoPubRegistradoEnPaqueteException("El paquete ya tiene asociado dicho tipo de publicación"); 
		} 
		PaqPub paqPub = new PaqPub(cantidad, tipo, pakete);
		pakete.setCosto(tipo.getCosto(), cantidad);
		pakete.agregarTipoPub(paqPub);
		tipo.agregarPaq(paqPub);
	}
	
	public int obtenerCantPaqPub(String paquetePubNombre, String tipoPubNombre) throws TipoPubNoExisteException {
		ManejadorTipoPublicacion mTipoPub = ManejadorTipoPublicacion.getInstance();
		ManejadorPaqueteTipoPub mPaqTipoPub = ManejadorPaqueteTipoPub.getInstance();
		PaqueteTipoPub paq = mPaqTipoPub.obtenerPaqueteTipoPub(paquetePubNombre);
		return paq.obtenerCantTipoPub(mTipoPub.obtenerDtTipoPublicacion(tipoPubNombre));
	}
	
	public void actualizarOfertaEstado(String ofertaNom, EstadoOferta estado) {
		ManejadorOfertaLaboral mOfertaLab = ManejadorOfertaLaboral.getInstance();
		OfertaLab oferta = mOfertaLab.obtenerOferta(ofertaNom);
		if (oferta != null) {
			if(estado == EstadoOferta.FINALIZADA) {
				finalizarOfertaPersistir(oferta);
				ManejadorOfertaLaboral.getInstance().eliminarOferta(ofertaNom);
			}
			oferta.setEstado(estado);
		}
	}
	
	private void finalizarOfertaPersistir(OfertaLab oferta) {
		EntityManagerFactory entManagerFactory = null;
	    EntityManager entityManager = null;
	    try {
			entManagerFactory = Persistence.createEntityManagerFactory("Servidor");
			entityManager = entManagerFactory.createEntityManager();
			//info de oferta
			TypedQuery<OfertaLabJPA> query3 = entityManager.createQuery(
			        "SELECT o FROM OfertaLabJPA o WHERE o.nombre = :nombre", OfertaLabJPA.class);
			    query3.setParameter("nombre", oferta.getNombre());
			    OfertaLabJPA ofertaExiste;
		    try {
		    	ofertaExiste = query3.getSingleResult();
		    } catch (NoResultException e) {
		    	ofertaExiste = null;
		    }   
		    
		    if (ofertaExiste == null) {	
				OfertaLabJPA ofertaPersistir = new OfertaLabJPA();
				ofertaPersistir.setNombre(oferta.getNombre());
				ofertaPersistir.setDescripcion(oferta.getDescripcion());
				ofertaPersistir.setHorario(oferta.getHorario());
				ofertaPersistir.setRemuneracion(oferta.getRemuneracion());
				ofertaPersistir.setDepartamento(oferta.getDepartamento());
				ofertaPersistir.setCiudad(oferta.getCiudad());
				ofertaPersistir.setTipo_pub(oferta.getTipo().getNombre());
				ofertaPersistir.setFecha_Alta(oferta.getFecha());
				ofertaPersistir.setFecha_Baja(LocalDate.now());
				ofertaPersistir.setCosto(oferta.getCoste());
				if (oferta.obtenerCompraPaquete() != null)
					ofertaPersistir.setPaquete(oferta.obtenerCompraPaquete().getPaquete().getNombrePaq());//null si no se ingresa
				ofertaPersistir.setImage(oferta.getImage());
				//info empresa
				Empresa empresa = oferta.getEmpresa();
				TypedQuery<EmpresaJPA> query = entityManager.createQuery(
				        "SELECT o FROM EmpresaJPA o WHERE o.nickname= :nombre", EmpresaJPA.class);
				    query.setParameter("nombre", empresa.getNickname());
				
				EmpresaJPA empresaExiste;
			    try {
			        empresaExiste = query.getSingleResult();
			    } catch (NoResultException e) {
			        empresaExiste = null;
			    }   
				if (empresaExiste == null) {
					EmpresaJPA empresaPersistir = new EmpresaJPA();
					empresaPersistir.setNickname(empresa.getNickname());
					empresaPersistir.setNombre(empresa.getNombre());
					empresaPersistir.setApellido(empresa.getApellido());
					empresaPersistir.setEmail(empresa.getCorreo());
					empresaPersistir.setDescripcion(empresa.getDescripcion());
					empresaPersistir.setSitio_web(empresa.getSitioWeb());
					ofertaPersistir.setEmpresa(empresaPersistir);
				}else
					ofertaPersistir.setEmpresa(empresaExiste);
				//persistir oferta
				entityManager.getTransaction().begin();
				entityManager.persist(ofertaPersistir);
				entityManager.getTransaction().commit();
		    
				//info postulacion
				List<Postulacion> postulaciones = oferta.getPostulaciones();
				if (postulaciones != null && postulaciones.size() > 0) {
					for (Postulacion postulacion : postulaciones) {
						PostulacionJPA postulacionPersistir = new PostulacionJPA();
						postulacionPersistir.setCv(postulacion.getCVReducido());
						postulacionPersistir.setMotivacion(postulacion.getMotivacion());
						postulacionPersistir.setFecha_postulacion(postulacion.getFechaPostulacion());
						postulacionPersistir.setOferta(ofertaPersistir);
						//postulante
						Postulante postulante = postulacion.getPostulante();
						TypedQuery<PostulanteJPA> query2 = entityManager.createQuery(
						        "SELECT o FROM PostulanteJPA o WHERE o.nickname = :nombre", PostulanteJPA.class);
						    query2.setParameter("nombre", postulante.getNickname());
						PostulanteJPA postulanteExiste;
					    try {
					    	postulanteExiste = query2.getSingleResult();
					    } catch (NoResultException e) {
					    	postulanteExiste = null;
					    }   
						if (postulanteExiste == null) {
							PostulanteJPA postulantePersistir = new PostulanteJPA();
							postulantePersistir.setNickname(postulante.getNickname());
							postulantePersistir.setNombre(postulante.getNombre());
							postulantePersistir.setApellido(postulante.getApellido());
							postulantePersistir.setEmail(postulante.getCorreo());
							postulantePersistir.setFecha_nacimiento(postulante.getFechaNacimiento());
							postulantePersistir.setNacionalidad(postulante.getNacionalidad());
							postulacionPersistir.setPostulante(postulantePersistir);
						}else
							postulacionPersistir.setPostulante(postulanteExiste);
						entityManager.getTransaction().begin();
						entityManager.persist(postulacionPersistir);
						entityManager.getTransaction().commit();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
	    }finally {
			entityManager.close();
			entManagerFactory.close();
	    }
	}
	
	public DtEmpresa obtenerEmpresa(String ofertaNom) {		
		DtEmpresa empresa = null;
		EntityManagerFactory entManagerFactory = null;
	    EntityManager entityManager = null;
		if (ManejadorOfertaLaboral.getInstance().obtenerOferta(ofertaNom) != null) {
			empresa = (DtEmpresa)  ManejadorOfertaLaboral.getInstance().obtenerOferta(ofertaNom).getEmpresa().obtenerDtUsu();
		}else{
			try {
			entManagerFactory = Persistence.createEntityManagerFactory("Servidor");
			entityManager = entManagerFactory.createEntityManager();
			TypedQuery<OfertaLabJPA> query = entityManager.createQuery(
			    "SELECT o FROM OfertaLabJPA o WHERE o.nombre = :ofertaNom",
			    OfertaLabJPA.class
			);
			query.setParameter("ofertaNom", ofertaNom);
			OfertaLabJPA oferta = query.getSingleResult();
			
	        empresa = (DtEmpresa) ManejadorUsuarios.getInstance().obtenerDtUsuario(oferta.getEmpresa().getNickname());

			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				entityManager.close();
				entManagerFactory.close();
			}
		}
		return empresa;
	}
	
	public DtPaquetePub[] obtenerPaquetesValidos(String empresaNombre) throws UsuarioNoExisteException {
        Empresa emp = (Empresa) ManejadorUsuarios.getInstance().obtenerUsuario(empresaNombre);
        LocalDate fechaActual = LocalDate.now();
        CompraPaquete[] paquetesComprados =  emp.obtenerPaquetesComprados();
        List<DtPaquetePub> paquetesValidos = new ArrayList<>();
        for (CompraPaquete paqueteCompra : paquetesComprados) {
            if (!paqueteCompra.getFechaDeVencimiento().isBefore(fechaActual)) {
                paquetesValidos.add(paqueteCompra.getPaquete().obtenerDtPaq());
            }
        }
        return paquetesValidos.toArray(new DtPaquetePub[0]);
    }
	
	public 	DtPaquetePub obtenerPaqueteCompra(String ofertaNombre) throws PaqueteNoExisteException  {
		if (ManejadorOfertaLaboral.getInstance().obtenerOferta(ofertaNombre) != null) {
			CompraPaquete CPaquete = ManejadorOfertaLaboral.getInstance().obtenerOferta(ofertaNombre).obtenerCompraPaquete();
			if (CPaquete == null) 
				throw new PaqueteNoExisteException("No posee paquete");
			else {
				DtPaquetePub paquete = obtenerPaquete(CPaquete.getPaquete().getNombrePaq());
				return paquete;
			}
		}else {
			DtPaquetePub paquete = obtenerPaqueteCompraFinalizado(ofertaNombre);
			if (paquete == null)
				throw new PaqueteNoExisteException("No posee paquete");
			return paquete;
		}
	}
	
	private DtPaquetePub obtenerPaqueteCompraFinalizado(String ofertaNom) throws PaqueteNoExisteException{
		EntityManagerFactory entManagerFactory = null;
	    EntityManager entityManager = null;
	    DtPaquetePub paquete = null;
	    try {
			entManagerFactory = Persistence.createEntityManagerFactory("Servidor");
			entityManager = entManagerFactory.createEntityManager();
	        
			TypedQuery<OfertaLabJPA> query = entityManager.createQuery(
				    "SELECT o FROM OfertaLabJPA o WHERE o.nombre = :ofertaNom",
				    OfertaLabJPA.class
				);
	        query.setParameter("ofertaNom", ofertaNom);
	        OfertaLabJPA oferta = query.getSingleResult();
	        System.out.println(oferta.getPaquete());
	        
	        if (oferta.getPaquete() != null) 
	            paquete = obtenerPaquete(oferta.getPaquete());
	    } catch (Exception e) {
	    	e.printStackTrace();
	    } finally {
	         entityManager.close();
	         entManagerFactory.close();
	    }

	    return paquete;
	}
	
	private byte[] getFile(String name) throws  IOException {
	    byte[] byteArray = null;
	    try {
	         File archivo = new File("media/"+name);
	        try (FileInputStream streamer = new FileInputStream(archivo)) {
				byteArray = new byte[streamer.available()];
				streamer.read(byteArray);
			}
	    } catch (IOException e) {
	            throw e;
	    }
	    return byteArray;
	}
	
	public DtOferta[] busquedaOfertas(String busqueda, String[] keywordArray) throws UsuarioNoExisteException {
		OfertaLab[] ofertas = ManejadorOfertaLaboral.getInstance().obtenerOfertas();
		
		// Filtrar ofertas por estado confirmado o finalizado
		OfertaLab[] ofertasFiltradas = Arrays.stream(ofertas)
				.filter(oferta -> oferta.getEstado().equals(EstadoOferta.CONFIRMADO))
			    .toArray(OfertaLab[]::new);
		
		// Filtrar ofertas por nombre o descripcion
		if (busqueda != null && !busqueda.isBlank()) {
			ofertasFiltradas = Arrays.stream(ofertasFiltradas)
			        .filter(oferta -> oferta.getNombre().contains(busqueda) || oferta.getDescripcion().contains(busqueda))
			        .toArray(OfertaLab[]::new);
		}
		
		// Filtrar ofertas por keywords
		if (keywordArray != null && keywordArray.length > 0) {
			ofertasFiltradas = Arrays.stream(ofertasFiltradas)
					.filter(oferta -> {
						for (String keyword : keywordArray) {
							if (!oferta.contieneKeyWord(new String[] { keyword })) {
								return false;
				            }
				        }
						return true;
					})
				    .toArray(OfertaLab[]::new);
		}
		
		// Ordenar ofertas por exposición del TipoPublicacion
	    Arrays.sort(ofertasFiltradas, (oferta1, oferta2) -> {
	        int expo1 = oferta1.getTipo().getExposicion();
	        int expo2 = oferta2.getTipo().getExposicion();
	        return Integer.compare(expo2, expo1); // Orden descendente
	    });
	    
	 // Remover cualquier entrada null de ofertasFiltradas
	    ofertasFiltradas = Arrays.stream(ofertasFiltradas)
	                             .filter(Objects::nonNull)
	                             .toArray(OfertaLab[]::new);

	    // Convertir a array de DtOferta
	    return Arrays.stream(ofertasFiltradas)
	        .map(OfertaLab::obtenerDtOferta)
	        .toArray(DtOferta[]::new);
	}
	
	
	public DtOferta[] obtenerOfertasPostuladas(String postulante) throws NoPoseePostulacionesException, UsuarioNoExisteException {
	    ManejadorUsuarios manejadorUsuarios = ManejadorUsuarios.getInstance();
	    
	    if (!manejadorUsuarios.existeUsuario(postulante)) {
	        throw new UsuarioNoExisteException("El Usuario no existe!");
	    }
	    
	    Postulante usuario = (Postulante) manejadorUsuarios.obtenerUsuario(postulante);
	    return usuario.getOfertas().stream()
	                  .map(OfertaLab::obtenerDtOferta)
	                  .toArray(DtOferta[]::new);
	}

	public DtPostulante[] obtenerPostulantesFavoritos(String oferta){
		OfertaLab ofer = ManejadorOfertaLaboral.getInstance().obtenerOferta(oferta);
		if (ofer != null) {
			List<Postulante> postulantes = ofer.getPostulantesFavoritos();
			List<DtPostulante> res = new ArrayList<DtPostulante>();
			for (Postulante post : postulantes) {
				res.add((DtPostulante) post.obtenerDtUsu());
			}
			return res.toArray(new DtPostulante[0]);	
		}
		return new DtPostulante[0];

	}
	
	public void cargarVisitas(String oferta, int visitas) {
		OfertaLab ofer = ManejadorOfertaLaboral.getInstance().obtenerOferta(oferta);
		ofer.setVisitas(visitas);	
	}

	public void visitar(String oferta) {
		OfertaLab ofer = ManejadorOfertaLaboral.getInstance().obtenerOferta(oferta);
		if (ofer != null)
			ofer.visitar();
	}
	
	public void cargarDatos() throws Exception {

		//Alta tipo Pub
		ingresarDatosTipoPublicacion("Premium", "Obtén máxima visibilidad" , 30, 1, (float) 4000.0, LocalDate.of(2023, 8, 10));
		ingresarDatosTipoPublicacion("Destacada", "Destaca tu anuncio" , 15, 2, (float) 500.0, LocalDate.of(2023, 8, 5));
		ingresarDatosTipoPublicacion("Estándar", "Mejora la posición de tu anuncio" , 20, 3, (float) 150.0, LocalDate.of(2023, 8, 15));
		ingresarDatosTipoPublicacion("Básica", "Publica de forma sencilla en la lista de ofertas" , 7, 4, (float) 50.0, LocalDate.of(2023, 8, 7));

		//Alta paquetes
		
		ingresarDatosPaquete("Básico", "Publica ofertas laborales en nuestra plataforma por un período de 30 días", getFile("paquetes-img/Básico.jpg"), LocalDate.of(2023, 8, 16), 30, 20);
		ingresarDatosPaquete("Destacado", "Publica ofertas laborales destacadas que se mostrará en la parte superior de los resultados de búsqueda por 45 días", getFile("paquetes-img/Destacado.jpg"), LocalDate.of(2023, 8, 15), 45, 10);
		ingresarDatosPaquete("Premium", "Publica ofertas laborales premium que incluye promoción en nuestras redes sociales y listado en la sección destacada por 60 días", getFile("paquetes-img/Premium.jpg"), LocalDate.of(2023, 8, 14), 60, 15);
		ingresarDatosPaquete("Express", "Publica ofertas laborales urgentes resaltada en color y se mostrará en la sección de urgente por 15 días.", getFile("paquetes-img/Express.jpg"), LocalDate.of(2023, 8, 13), 15, 5);

		//Agregar tipo pub a paquete
		//agregarTipoPubAPaquete(1, TipoPublicacion tp, PaqueteTipoPub paq);
		
		agregarTipoPubAPaquete(1, "Premium", "Básico");
		agregarTipoPubAPaquete(1, "Destacada", "Básico");
		agregarTipoPubAPaquete(1, "Estándar", "Básico");
		
		agregarTipoPubAPaquete(2, "Estándar", "Destacado");
		agregarTipoPubAPaquete(1, "Básica", "Destacado");
		
		agregarTipoPubAPaquete(2, "Premium", "Premium");
		agregarTipoPubAPaquete(2, "Estándar", "Premium");
		
		agregarTipoPubAPaquete(2, "Destacada", "Express");

		
		//Alta de KeyWords (NO GUI)
		ingresarKeyWord("Tiempo completo");
		ingresarKeyWord("Medio tiempo");
		ingresarKeyWord("Remoto"); 
		ingresarKeyWord("Freelance");
		ingresarKeyWord("Temporal");
		ingresarKeyWord("Permanente");
		ingresarKeyWord("Computación"); 
		ingresarKeyWord("Administración");
		ingresarKeyWord("Logística");
		ingresarKeyWord("Contabilidad");
	} 
	
	public void cargarDatosAltaOfertas() throws Exception {
		//Alta OfertaLab
		
		ingresarDatosOfertaLaboral("Desarrollador Frontend", "Únete a nuestro equipo de desarrollo frontend y crea experiencias de usuario excepcionales.", getFile("ofertas-img/Desarrollador Frontend.jpg"), "09:00:18:00", (float) 90000, "Montevideo", "Montevideo", LocalDate.of(2023, 9, 30), new String[]{"Tiempo completo", "Medio tiempo", "Remoto", "Freelance", "Temporal", "Permanente" }, "EcoTech", "Premium", "Básico");
		ingresarDatosOfertaLaboral("Estratega de Negocios", "Forma parte de nuestro equipo de estrategia y contribuye al crecimiento de las empresas clientes.", getFile("ofertas-img/Estratega de Negocios.jpg"), "08:00:17:00", (float) 80000.0, "Maldonado", "Punta del Este", LocalDate.of(2023, 9, 29), new String[] {"Temporal"}, "GlobalHealth", "Estándar");
		ingresarDatosOfertaLaboral("Diseñador UX-UI", "Trabaja en colaboración con nuestro talentoso equipo de diseño para crear soluciones impactantes.", getFile("ofertas-img/Diseñador UX-UI.jpg"), "14:00:18:00", (float) 65000.0, "Colonia", "Rosario", LocalDate.of(2023, 10, 29), new String[]{"Medio tiempo", "Remoto", "Permanente"}, "FusionTech", "Estándar");
		ingresarDatosOfertaLaboral("Analista de Datos", "Ayuda a nuestros clientes a tomar decisiones informadas basadas en análisis y visualizaciones de datos.", getFile("ofertas-img/Analista de Datos.jpg") , "09:00:13:00", (float) 40000.0, "Maldonado", "Maldonado", LocalDate.of(2023, 10, 19), new String[]{"Medio tiempo"}, "ANTEL", "Premium");
		ingresarDatosOfertaLaboral("Content Manager", "Gestiona y crea contenido persuasivo y relevante para impulsar la presencia en línea de nuestros clientes.", getFile("ofertas-img/Content Manager.jpg") , "18:00:22:00", (float) 10000.0, "Montevideo", "Montevideo", LocalDate.of(2023, 10, 20), new String[]{"Freelance"}, "MIEM", "Destacada");
		ingresarDatosOfertaLaboral("Soporte Técnico", "Ofrece un excelente servicio de soporte técnico a nuestros clientes, resolviendo problemas y brindando soluciones.", getFile("ofertas-img/Soporte Técnico.jpg"), "09:00:18:00", (float) 30000.0, "Lavalleja", "Minas", LocalDate.of(2023, 11, 2), new String[]{"Tiempo completo"}, "TechSolutions", "Básica", "Destacado");
		ingresarDatosOfertaLaboral("A. de Marketing Digital", "Únete a nuestro equipo de marketing y trabaja en estrategias digitales innovadoras.", getFile("ofertas-img/A. de Marketing Digital.jpg"), "10:00:19:00", (float) 80000.0, "Flores", "Flores", LocalDate.of(2023, 11, 2),  new String[]{"Freelance"}, "EcoTech", "Premium");
		ingresarDatosOfertaLaboral("Contador Senior", "Únete a nuestro equipo contable y ayuda en la gestión financiera de la empresa.", getFile("ofertas-img/Contador Senior.jpg") , "08:30:17:30", (float) 100000.0, "Colonia", "Colonia Suiza", LocalDate.of(2023, 11, 4),  new String[]{"Tiempo completo"}, "GlobalHealth", "Destacada");
		ingresarDatosOfertaLaboral("Técnico-a Básico Red", "RÉGIMEN DE CONTRATO EN FUNCION PUBLICA EN UN TODO DE ACUERDO A LA NORMATIVA VIGENTE (LEY 16.127, DE 7 DE AGOSTO DE 1990, ART. 1°, LITERAL A) Y B) CON LA MODIFICACIÓN INTRODUCIDA POR EL ART. 11 DE LA LEY 17.930 DE 19 DE DICIEMBRE DE 2005).", getFile("ofertas-img/Técnico-a Básico Red.jpg"), "09:00:17:00", (float) 40000.0, "Paysandú", "Paysandú", LocalDate.of(2023, 10, 29), new String[]{"Temporal"}, "ANTEL", "Premium");
		ingresarDatosOfertaLaboral("Desarrollador de Software Senior", "Únete a nuestro equipo y lidera proyectos de desarrollo de software sostenible y ecológico. Impulsa la innovación y contribuye a un futuro más verde.", getFile("ofertas-img/Desarrollador de Software Senior.jpg"), "09:00:16:00", (float) 123000.0, "Montevideo", "Montevideo", LocalDate.of(2023, 11, 4), new String[]{"Tiempo completo", "Permanente", "Logística"}, "EcoTech", "Destacada", "Básico");
		ingresarDatosOfertaLaboral("Desarrollador de Software Full Stack", "Únete a nuestro equipo para crear soluciones de software personalizadas de extremo a extremo. Colabora en proyectos emocionantes y desafiantes.", getFile("ofertas-img/Desarrollador de Software Full Stack.jpg"), "04:00:13:00", (float) 135000.0, "Río Negro", "Fray Bentos", LocalDate.of(2023, 10, 25), new String[]{"Remoto"}, "TechSolutions", "Premium");
		ingresarDatosOfertaLaboral("Gerente de Proyecto", "Únete a nuestro equipo de gestión de proyectos y lidera la entrega exitosa de soluciones de software personalizadas. Colabora con equipos multidisciplinarios y clientes exigentes.", getFile("ofertas-img/Gerente de Proyecto.jpg"), "04:00:12:00", (float) 230000.0, "Montevideo", "Montevideo", LocalDate.of(2023, 11, 5), new String[]{"Remoto", "Permanente"}, "TechSolutions", "Destacada");
		ingresarDatosOfertaLaboral("Ingeniero de Calidad de Software", "Asegura la calidad de nuestros productos de software sostenibles. Únete a nosotros para garantizar un impacto positivo en el medio ambiente.", getFile("ofertas-img/Ingeniero de Calidad de Software.jpg"), "14:00:18:00", (float) 60000.0, "Montevideo", "Montevideo", LocalDate.of(2023, 11, 1), new String[]{"Tiempo completo", "Contabilidad"}, "EcoTech", "Premium");
	
		//visitas
		cargarVisitas("Desarrollador Frontend", 5);
		cargarVisitas("Estratega de Negocios", 10);
		cargarVisitas("Diseñador UX-UI", 0);
		cargarVisitas("Analista de Datos", 15);
		cargarVisitas("Content Manager", 20);
		cargarVisitas("Soporte Técnico", 25);
		cargarVisitas("A. de Marketing Digital", 30);
		cargarVisitas("Contador Senior", 35);
		cargarVisitas("Técnico-a Básico Red", 40);
		cargarVisitas("Desarrollador de Software Senior", 45);
		cargarVisitas("Desarrollador de Software Full Stack", 50);
		cargarVisitas("Gerente de Proyecto", 55);
		cargarVisitas("Ingeniero de Calidad de Software", 7);
	}
	
	public void cargaPersistenciaOfertasFinalizadas() {

		actualizarOfertaEstado("Desarrollador Frontend", EstadoOferta.CONFIRMADO);
		actualizarOfertaEstado("Estratega de Negocios", EstadoOferta.CONFIRMADO);
		actualizarOfertaEstado("Diseñador UX-UI", EstadoOferta.CONFIRMADO);
		actualizarOfertaEstado("Analista de Datos", EstadoOferta.INGRESADO);
		actualizarOfertaEstado("Content Manager", EstadoOferta.FINALIZADA);
		actualizarOfertaEstado("Soporte Técnico", EstadoOferta.CONFIRMADO);
		actualizarOfertaEstado("A. de Marketing Digital", EstadoOferta.CONFIRMADO);
		actualizarOfertaEstado("Contador Senior", EstadoOferta.RECHAZADO);
		actualizarOfertaEstado("Técnico-a Básico Red", EstadoOferta.CONFIRMADO);
		actualizarOfertaEstado("Desarrollador de Software Senior", EstadoOferta.INGRESADO);
		actualizarOfertaEstado("Desarrollador de Software Full Stack", EstadoOferta.INGRESADO);
		actualizarOfertaEstado("Gerente de Proyecto", EstadoOferta.CONFIRMADO);
		actualizarOfertaEstado("Ingeniero de Calidad de Software", EstadoOferta.INGRESADO);
		
		//Elimina las ofertas finalizadas anteriormente, temporal
		DtOferta[] ofertasDtCol = obtenerOfertasFinalizadas();
		for (DtOferta ofertaDt : ofertasDtCol) {
			OfertaLab oferta = ManejadorOfertaLaboral.getInstance().obtenerOferta(ofertaDt.getNombre());
			if (oferta != null) {
				String nomOferta = oferta.getNombre();
				oferta.setEstado(EstadoOferta.FINALIZADA);
				ManejadorOfertaLaboral.getInstance().eliminarOferta(nomOferta);
			}
		}
	}

}



