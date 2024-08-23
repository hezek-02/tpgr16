package logica.controladores;

import excepciones.PostulacionYaExisteException;
import excepciones.RankingYaOcupadoException;
import excepciones.TipoPubNoExisteException;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioRegistradoException;
import excepciones.FechaInvalidaException;
import excepciones.PaqueteNoExisteException;
import excepciones.PaqueteYaCompradoException;
import logica.datatypes.DtCompraPaquete;
import logica.datatypes.DtEmpresa;
import logica.datatypes.DtOferta;
import logica.datatypes.DtPaquetePub;
import logica.datatypes.DtPostulacion;
import logica.datatypes.DtPostulante;
import logica.datatypes.DtUsuario;
import logica.interfaces.IUsuario;
import logica.manejadores.ManejadorOfertaLaboral;
import logica.manejadores.ManejadorPaqueteTipoPub;
import logica.manejadores.ManejadorTipoPublicacion;
import logica.manejadores.ManejadorUsuarios;
import logica.modelos.CompraPaquete;
import logica.modelos.Empresa;
import logica.modelos.OfertaLab;
import logica.modelos.PaqueteTipoPub;
import logica.modelos.Postulacion;
import logica.modelos.Postulante;
import logica.modelos.TipoPublicacion;
import logica.modelos.Usuario;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class ControladorUsuario implements IUsuario{
	
	public ControladorUsuario() {}

	public void ingresarDatosPostulante(String nickname, String nombre, String apellido, String password, String correo, byte[] image, String pais, LocalDate fechaNac) throws UsuarioRegistradoException, FechaInvalidaException {
		ManejadorUsuarios manejadorUsuarios = ManejadorUsuarios.getInstance();
		if (manejadorUsuarios.existeUsuario(nickname) || manejadorUsuarios.existeUsuario(correo))
			throw new UsuarioRegistradoException("El correo o nickname ya se encuentra registrado");
		if (fechaNac.isAfter(LocalDate.now()) || fechaNac.isBefore(LocalDate.now().minusYears(80)))
			throw new FechaInvalidaException("La fecha debe tener menos de 80 años y ser menor o igual a la actual");
		if (fechaNac.isAfter(LocalDate.now().minusYears(18)))
			throw new FechaInvalidaException("La fecha debe ser válida. Debe ser mayor de edad");
		if (image == null || image.length == 0) {
			try {
				image =  getFile("usuarios-img/Generica.png");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Usuario usuario = new Postulante(nickname, nombre, apellido, password, correo, image, fechaNac, pais);
		manejadorUsuarios.agregarUsuario(usuario);
	}

	public void ingresarDatosEmpresa(String nickname, String nombre, String apellido, String password, String correo, byte[] image, String descripcion, String link) throws UsuarioRegistradoException {
		ManejadorUsuarios manejadorUsuarios = ManejadorUsuarios.getInstance();
		if (manejadorUsuarios.existeUsuario(nickname) || manejadorUsuarios.existeUsuario(correo))
			throw new UsuarioRegistradoException("El correo o nickname ya se encuentra registrado");
		if (image == null || image.length == 0) {
			try {
				image =  getFile("usuarios-img/Generica.png");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Usuario usuario = new Empresa(nickname, nombre, apellido, password, correo, image, descripcion, link);
		manejadorUsuarios.agregarUsuario(usuario);
	}
	
	public DtUsuario obtenerUsuario(String emailOrNickname) throws UsuarioNoExisteException {
		ManejadorUsuarios manejador = ManejadorUsuarios.getInstance();
		Usuario usuario = manejador.obtenerUsuario(emailOrNickname);
		if (usuario == null) throw new UsuarioNoExisteException("El Usuario no existe!");
		return usuario.obtenerDtUsu();
	}


	public DtUsuario[] obtenerUsuarios() throws UsuarioNoExisteException {
		ManejadorUsuarios manejadorUsuarios = ManejadorUsuarios.getInstance();
		Usuario[] usuarios = manejadorUsuarios.obtenerUsuarios();
		if (usuarios != null) {
			DtUsuario[] dtUsuarios = new DtUsuario[usuarios.length];
			for (int i = 0; i < usuarios.length; i++) {
				dtUsuarios[i] = usuarios[i].obtenerDtUsu();
			}
			return dtUsuarios;
		} else
			throw new UsuarioNoExisteException("No hay usuarios registrados");
	}

	public DtEmpresa[] obtenerEmpresas() {
		ManejadorUsuarios manejador = ManejadorUsuarios.getInstance();
		Usuario[] usuarios = manejador.obtenerUsuarios();
		if (usuarios == null) return null;
		List<DtEmpresa> dtEmpresaList = new ArrayList<>();
		for (Usuario usuario : usuarios) {
			if (usuario instanceof Empresa) {
				DtEmpresa dtEmpresa = (DtEmpresa) usuario.obtenerDtUsu();
				dtEmpresaList.add(dtEmpresa);
			}
		}
		return dtEmpresaList.toArray(new DtEmpresa[0]);
	}
	
	public DtEmpresa[] busquedaEmpresas(String busqueda) {
		Usuario[] usuarios = ManejadorUsuarios.getInstance().obtenerUsuarios();
		Empresa[] empresas = Arrays.stream(usuarios)
			    .filter(usuario -> usuario instanceof Empresa)
			    .map(usuario -> (Empresa) usuario)
			    .toArray(Empresa[]::new);
		
		 // Filtrar empresas por nombre o descripcion
	    if (busqueda != null && !busqueda.isBlank()) {
	        empresas = Arrays.stream(empresas)
	                .filter(empresa -> empresa.getNickname().contains(busqueda) || empresa.getDescripcion().contains(busqueda))
	                .toArray(Empresa[]::new);
	    }
	    
	    // Order by the date of the last published job offer in descending order
	    Arrays.sort(empresas, (empresa1, empresa2) -> {
	        LocalDate fecha1 = (empresa1.getOfertas().isEmpty()) ? LocalDate.MIN : empresa1.getOfertas().stream().max(Comparator.comparing(OfertaLab::getFecha)).get().getFecha();
	        LocalDate fecha2 = (empresa2.getOfertas().isEmpty()) ? LocalDate.MIN : empresa2.getOfertas().stream().max(Comparator.comparing(OfertaLab::getFecha)).get().getFecha();
	        return fecha2.compareTo(fecha1);
	    });

	    // Convert to array of DtEmpresa
	    return Arrays.stream(empresas)
	        .map(Empresa::obtenerDtUsu)
	        .toArray(DtEmpresa[]::new);
	}
	
	public DtPostulante[] obtenerPostulantes() {
		ManejadorUsuarios manejador = ManejadorUsuarios.getInstance();
		Usuario[] usuarios = manejador.obtenerUsuarios();
		if (usuarios == null) return null;
		List<DtPostulante> dtPostList = new ArrayList<>();
		for (Usuario usuario : usuarios) {
			if (usuario instanceof Postulante) {
				DtPostulante dtPost = (DtPostulante) usuario.obtenerDtUsu();
				dtPostList.add(dtPost);
			}
		}

		return dtPostList.toArray(new DtPostulante[0]);
	}
	
	
	public void modificarUsuario(String clave, String nombre, String apellido, LocalDate fechaNac, String pais, byte[] image) throws FechaInvalidaException, UsuarioNoExisteException{
		ManejadorUsuarios manejadorUsuarios = ManejadorUsuarios.getInstance();
		if (!manejadorUsuarios.existeUsuario(clave))
			throw new UsuarioNoExisteException("El Usuario no existe!");
		if (fechaNac.isAfter(LocalDate.now()) || fechaNac.isBefore(LocalDate.now().minusYears(80)))
			throw new FechaInvalidaException("La fecha debe tener menos de 80 años y ser menor o igual a la actual"); //control si es mas vieja q 80años
		if (fechaNac.isAfter(LocalDate.now().minusYears(18)))
			throw new FechaInvalidaException("La fecha debe ser válida. Debe ser mayor de edad");
		Postulante usu =(Postulante) manejadorUsuarios.obtenerUsuario(clave);
		usu.setNombre(nombre);
		usu.setApellido(apellido);
		usu.setNacionalidad(pais);
		usu.setFechaNac(fechaNac);
		if (image != null && image.length > 0)
			usu.setImage(image);
	}
	public void modificarUsuario(String clave, String nombre, String apellido, String descripcion, String link, byte[] image) throws UsuarioNoExisteException{
		ManejadorUsuarios manejadorUsuarios = ManejadorUsuarios.getInstance();
		if (!manejadorUsuarios.existeUsuario(clave))
			throw new UsuarioNoExisteException("El Usuario no existe!");
		Empresa usu = (Empresa) manejadorUsuarios.obtenerUsuario(clave);
		usu.setNombre(nombre);
		usu.setApellido(apellido);
		usu.setDescripcion(descripcion);
		usu.setSitioWeb(link);
		if (image != null && image.length > 0)
			usu.setImage(image);
	}
	
	public void ingresarDatosPostulacion(String cvReducido, String motivacion, LocalDate fechaPostulacion, String ofertaNom, String postulanteNom, String videoUrl) throws PostulacionYaExisteException, FechaInvalidaException, UsuarioNoExisteException {
		if (fechaPostulacion.isAfter(LocalDate.now()) || fechaPostulacion.isBefore(LocalDate.now().minusYears(80))) {
			throw new FechaInvalidaException("La fecha debe tener menos de 80 años y ser menor o igual a la actual");
		}
		
		ManejadorUsuarios manejadorUsuario = ManejadorUsuarios.getInstance();
		ManejadorOfertaLaboral manejadorOferta = ManejadorOfertaLaboral.getInstance();
		OfertaLab oferta = manejadorOferta.obtenerOferta(ofertaNom);
		
		if (oferta != null && oferta.ofertaEstaVencida(fechaPostulacion)) {
			throw new PostulacionYaExisteException("No es posible postularse, la oferta ya no se encuentra vigente");
		}
		Postulante postulante = (Postulante) manejadorUsuario.obtenerUsuario(postulanteNom);
		
		if (postulante.existePostulacion(oferta)) {
			throw new PostulacionYaExisteException("Ya existe esta postulacion");
		}
		
		Postulacion post = new Postulacion(cvReducido, motivacion, fechaPostulacion, oferta, postulante);
		if (videoUrl != null && videoUrl!="") {
			post.setVideoUrl(videoUrl);
		}
		postulante.agregarPostulacion(post);
		oferta.agregarPostulante(post);
	} 
	
	public boolean existePostulacion(String ofertaNom, String postulanteNom) throws UsuarioNoExisteException {
		ManejadorUsuarios manejadorUsuario = ManejadorUsuarios.getInstance();
		ManejadorOfertaLaboral manejadorOferta = ManejadorOfertaLaboral.getInstance();
		OfertaLab oferta = manejadorOferta.obtenerOferta(ofertaNom);
		Postulante postulante = (Postulante) manejadorUsuario.obtenerUsuario(postulanteNom);
		return  postulante.existePostulacion(oferta);
	}
	
	public void comprarPaquete(String empresaNom, String paqueteNom, LocalDate fecha) throws UsuarioNoExisteException, PaqueteYaCompradoException {
	    Empresa emp = (Empresa) ManejadorUsuarios.getInstance().obtenerUsuario(empresaNom);
	    PaqueteTipoPub paqueteOriginal = ManejadorPaqueteTipoPub.getInstance().obtenerPaqueteTipoPub(paqueteNom);
	    CompraPaquete[] paquetesComprados =  emp.obtenerPaquetesComprados();
	    for (CompraPaquete paqueteCompra : paquetesComprados) { 
	    	if (paqueteCompra.getPaquete().getNombrePaq().equals(paqueteNom)) {
	    		throw new PaqueteYaCompradoException("El paquete ya ha sido comprado, no se puedo reiterar la compra");
	    	}
	    }
	    paqueteOriginal.setComprado(true);
	    PaqueteTipoPub paq = ManejadorPaqueteTipoPub.getInstance().obtenerCopiaPaqueteTipoPub(paqueteNom);
	    CompraPaquete compra = new CompraPaquete(fecha);
	    compra.agregarEmpresa(emp); 
	    compra.agregarPaquete(paq);
	}

	public DtPaquetePub[] obtenerPaquetesCompradosValidos(String empresaNom, String tipoPubNom) throws UsuarioNoExisteException, TipoPubNoExisteException {
	    Empresa emp = (Empresa) ManejadorUsuarios.getInstance().obtenerUsuario(empresaNom);
	    TipoPublicacion tipo = ManejadorTipoPublicacion.getInstance().obtenerTipoPublicacion(tipoPubNom);
	    LocalDate fechaActual = LocalDate.now();
	    CompraPaquete[] paquetesComprados =  emp.obtenerPaquetesComprados();
	    List<DtPaquetePub> paquetesValidos = new ArrayList<>();
	    for (CompraPaquete paqueteCompra : paquetesComprados) {
	    	int cantidad = obtenerCantPaqPubEmpresa( paqueteCompra.getPaquete().getNombrePaq(), tipoPubNom, empresaNom);
	    	if (!paqueteCompra.getFechaDeVencimiento().isBefore(fechaActual) && 
		    		paqueteCompra.getPaquete().existeTipoPub(tipo) && cantidad > 0) {
	    		paquetesValidos.add(paqueteCompra.getPaquete().obtenerDtPaq());
	    	}
	    }
	    return paquetesValidos.toArray(new DtPaquetePub[0]);
	}
	
	public int obtenerCantPaqPubEmpresa(String paqueteNom, String tipoPubNom, String empresaNom) throws UsuarioNoExisteException, TipoPubNoExisteException {
	    ManejadorPaqueteTipoPub manejadorPaquetes = ManejadorPaqueteTipoPub.getInstance();
	    return manejadorPaquetes.obtenerCantPaqPubEmpresa(paqueteNom, tipoPubNom, empresaNom);
	}
	
	public DtPostulacion obtenerPostulacion(String ofertaNom, String postulanteNom) {
		ManejadorOfertaLaboral manejador = ManejadorOfertaLaboral.getInstance();
		OfertaLab ofertaPostulacion = manejador.obtenerOferta(ofertaNom);
	    List<Postulacion> postulaciones = ofertaPostulacion.getPostulaciones();
	    if (postulaciones.isEmpty()) {
	        return null;
	    }
	    DtPostulacion res = null;
	    for (Postulacion postulacion : postulaciones) { 
	        if (postulacion.getPostulante().getNickname().equals(postulanteNom)) {
	        	res = postulacion.getDtPostulacion();
	        }
	    }
	   	return res;
	}
	
	//seguidores y seguidos
	public DtUsuario[] obtenerSeguidores(String nickname) throws UsuarioNoExisteException {
		ManejadorUsuarios manejadorUsuario = ManejadorUsuarios.getInstance();
		Usuario usuario = (Usuario) manejadorUsuario.obtenerUsuario(nickname);
		List<Usuario> seguidores = usuario.getSeguidores();
		List<DtUsuario> res = new ArrayList<DtUsuario>();
		for (Usuario seguidor : seguidores) {
			res.add(seguidor.obtenerDtUsu());
		}
		if (res == null || res.isEmpty()) {
			throw new UsuarioNoExisteException("No posee seguidores");
		}
		return res.toArray(new DtUsuario[0]);
	}
	
	public DtUsuario[] obtenerSeguidos(String nickname) throws UsuarioNoExisteException {
		ManejadorUsuarios manejadorUsuario = ManejadorUsuarios.getInstance();
		Usuario usuario = (Usuario) manejadorUsuario.obtenerUsuario(nickname);
		List<Usuario> seguidos = usuario.getSeguidos();
		List<DtUsuario> res = new ArrayList<DtUsuario>();
		for (Usuario sigue : seguidos) {
			res.add(sigue.obtenerDtUsu());
		}
		if (res == null || res.isEmpty()) {
			throw new UsuarioNoExisteException("No posee seguidores");
		}
		return res.toArray(new DtUsuario[0]);
	}
	
	public void agregarSeguidor(String nomSigue, String nomSeguido) throws UsuarioNoExisteException {
		ManejadorUsuarios manejadorUsuario = ManejadorUsuarios.getInstance();
		Usuario seguidor = (Usuario) manejadorUsuario.obtenerUsuario(nomSigue);
		Usuario seguido = (Usuario) manejadorUsuario.obtenerUsuario(nomSeguido);
		seguidor.agregarSeguido(seguido);//agergo qien sigo (vista seguidor)
		seguido.agregarSeguidor(seguidor);//agrego qien me sigue (vista seguidor)
	}
	
	public void eliminarSeguidor(String nomSigue, String nomSeguido) throws UsuarioNoExisteException {
		ManejadorUsuarios manejadorUsuario = ManejadorUsuarios.getInstance();
		Usuario seguidor = (Usuario) manejadorUsuario.obtenerUsuario(nomSigue);
		Usuario seguido = (Usuario) manejadorUsuario.obtenerUsuario(nomSeguido);
		seguidor.eliminarSeguido(seguido); //elimino qien sigo (vista seguidor)
		seguido.eliminarSeguidor(seguidor); //elimino quien me sigue (vista seguidor)
	}
	
	public boolean yaSeguido(String nomSigue, String nomSeguido) throws UsuarioNoExisteException {
		ManejadorUsuarios manejadorUsuario = ManejadorUsuarios.getInstance();
		Usuario seguidor = (Usuario) manejadorUsuario.obtenerUsuario(nomSigue);
		Usuario seguido = (Usuario) manejadorUsuario.obtenerUsuario(nomSeguido);
		return seguidor.yaSeguido(seguido);
	}

	public void rankearPostulante(String nomOferta, String nomPostulante, LocalDate fechaRank, int rank) throws UsuarioNoExisteException, RankingYaOcupadoException {
		OfertaLab oferta = ManejadorOfertaLaboral.getInstance().obtenerOferta(nomOferta);
		Postulante postulante = (Postulante) ManejadorUsuarios.getInstance().obtenerUsuario(nomPostulante);
		Postulacion postulacion = postulante.obtenerPostulacion(oferta);
		if (oferta.yaExisteRanking(rank))
			throw new RankingYaOcupadoException("Debe seleccionar otro número el actual ya se encuentra ocupado.");
		if (postulacion.getRanking()>0)
			throw new RankingYaOcupadoException("Ya se le asignó un puesto");
		postulacion.setFechaRanking(fechaRank);
		postulacion.setRanking(rank);
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

	public void cargarDatos() throws Exception{
		//CargaPostulantes
		ingresarDatosPostulante("lgarcia", "Lucía", "García", "awdrg543", "lgarcia85@gmail.com", getFile("usuarios-img/lgarcia.jpg"), "Uruguaya", LocalDate.of(1985, 3, 15));
		ingresarDatosPostulante("matilo", "Matías", "López", "edrft543", "matias.lopez90@hotmail.com", getFile("usuarios-img/matilo.jpg"), "Argentina", LocalDate.of(1990, 8, 21));
		ingresarDatosPostulante("maro", "María", "Rodríguez", "r5t6y7u8", "marrod@gmail.com", getFile("usuarios-img/maro.jpg"), "Uruguaya", LocalDate.of(1988, 11, 10));
		ingresarDatosPostulante("javierf", "Javier", "Fernández", "45idgaf67", "javierf93@yahoo.com", getFile("usuarios-img/javierf.jpg"), "Mexicana", LocalDate.of(1993, 6, 5));
		ingresarDatosPostulante("valen25", "Valentina", "Martínez", "poiuy987", "vale87@gmail.com", getFile("usuarios-img/valen25.jpg"), "Uruguaya", LocalDate.of(1987, 2, 25));
		ingresarDatosPostulante("andpe12", "Andrés", "Pérez", "xdrgb657", "anpe92@hotmail.com", getFile("usuarios-img/andpe12.jpg"), "Chilena", LocalDate.of(1992, 4, 12));
		ingresarDatosPostulante("sicam", "Camila", "Silva", "mnjkiu89", "camilasilva89@gmail.com", getFile("usuarios-img/sicam.jpg"), "Uruguaya", LocalDate.of(1989, 9, 30));
		ingresarDatosPostulante("sebgon", "Sebastián", "González", "ytrewq10", "gonza95@yahoo.com", getFile("usuarios-img/sebgon.jpg"), "Colombiana", LocalDate.of(1995, 1, 18));
		ingresarDatosPostulante("isabel", "Isabella", "López", "sbsplol1", "loisa@gmail.com", getFile("usuarios-img/isabel.jpg"), "Uruguaya", LocalDate.of(1991, 7, 7));
		ingresarDatosPostulante("marram02", "Martín", "Ramírez", "okmnji98", "marram@hotmail.com", getFile("usuarios-img/marram02.jpg"), "Argentina", LocalDate.of(1986, 2, 12));


		//CargaEmpresas
		ingresarDatosEmpresa("EcoTech", "Sophia", "Johnson", "qsxcdw43", "info@EcoTech.com", getFile("usuarios-img/EcoTech.jpg"), "EcoTech Innovations es una empresa líder en soluciones tecnológicas sostenibles. Nuestro enfoque se centra en desarrollar y comercializar productos y servicios que aborden los desafíos ambientales más apremiantes de nuestro tiempo. Desde sistemas de energía renovable y dispositivos de monitorización ambiental hasta soluciones de gestión de residuos inteligentes, nuestra misión es proporcionar herramientas que permitan a las empresas y comunidades adoptar prácticas más ecológicas sin comprometer la eficiencia. Creemos en la convergencia armoniosa entre la tecnología y la naturaleza, y trabajamos incansablemente para impulsar un futuro más limpio y sostenible.", "http://www.EcoTechInnovations.com");
		ingresarDatosEmpresa("FusionTech", "William", "Smith", "qpwoei586", "contacto@FusionTech.net", getFile("usuarios-img/FusionTech.jpg"), "FusionTech Dynamics es una empresa pionera en el ámbito de la inteligencia artificial y la automatización avanzada. Nuestro equipo multidisciplinario de ingenieros, científicos de datos y desarrolladores crea soluciones innovadoras que aprovechan la potencia de la IA para transformar industrias. Desde la optimización de procesos industriales hasta la creación de asistentes virtuales altamente personalizados, nuestro objetivo es revolucionar la forma en que las empresas operan y se conectan con sus clientes. Creemos en la sinergia entre la mente humana y las capacidades de la IA, y trabajamos para construir un mundo donde la tecnología mejore y amplíe nuestras capacidades innatas.", "http://www.FusionTechDynamics.net");
		ingresarDatosEmpresa("GlobalHealth", "Isabella", "Brown", "asdfg654", "jobs@GlobalHealth.uy", getFile("usuarios-img/GlobalHealth.jpg"), "GlobalHealth Dynamics es una empresa comprometida con el avance de la atención médica a nivel mundial. Como líderes en el campo de la salud digital, desarrollamos plataformas y herramientas que permiten a los profesionales de la salud ofrecer diagnósticos más precisos, tratamientos personalizados y seguimiento continuo de los pacientes. Nuestra visión es crear un ecosistema de salud conectado en el que los datos médicos se utilicen de manera ética y segura para mejorar la calidad de vida de las personas. A través de la innovación constante y la colaboración con expertos médicos, estamos dando forma al futuro de la atención médica, donde la tecnología y la compasión se unen para salvar vidas y mejorar el bienestar en todo el mundo.", "http://www.GlobalHealthDynamics.uy/info");
		ingresarDatosEmpresa("ANTEL", "Washington", "Rocha", "2nru096", "jarrington@ANTEL.com.uy", getFile("usuarios-img/ANTEL.jpg"), "En Antel, te brindamos servicios de vanguardia en tecnología de comunicación en Telefonía Móvil, Fija, Banda Ancha y Datos.", "ANTEL.com.uy");
		ingresarDatosEmpresa("MIEM", "Pablo" , "Bengoechea", "ibii4xo", "eldiez@MIEM.org.uy", getFile("usuarios-img/MIEM.jpg"), "Balance Energético Nacional (BEN). La Dirección Nacional de Energía (DNE) del Ministerio de Industria, Energía y Minería (MIEM) presenta anualmente el BEN.", "MIEM.com.uy");
		ingresarDatosEmpresa("TechSolutions", "Mercedes", "Venn", "1ngs03p", "Mercedes@TechSolutions.com.uy", getFile("usuarios-img/TechSolutions.jpg"), "\"TechSolutions Inc.\" es una empresa líder en el sector de tecnología de la información y el software. Se especializa en el desarrollo de soluciones de software personalizadas para empresas de diversos tamaños y sectores. Su enfoque se centra en la creación de aplicaciones empresariales innovadoras que optimizan procesos, mejoran la eficiencia y brindan una ventaja competitiva a sus clientes.", "TechSolutions.com");
		
		//seguidores
		agregarSeguidor("lgarcia", "EcoTech");
		agregarSeguidor("lgarcia", "FusionTech");
		agregarSeguidor("lgarcia", "GlobalHealth");
		agregarSeguidor("lgarcia", "ANTEL");
		agregarSeguidor("lgarcia", "MIEM");
		
		agregarSeguidor("matilo", "EcoTech");
		
		agregarSeguidor("maro", "FusionTech");
		agregarSeguidor("maro", "GlobalHealth");
		agregarSeguidor("maro", "MIEM");
		agregarSeguidor("maro", "TechSolutions");

		agregarSeguidor("javierf", "FusionTech");
		agregarSeguidor("javierf", "ANTEL");
		
		agregarSeguidor("valen25", "GlobalHealth");
		agregarSeguidor("valen25", "MIEM");
		agregarSeguidor("valen25", "TechSolutions");
		
		agregarSeguidor("andpe12", "FusionTech");
		agregarSeguidor("andpe12", "ANTEL");
		agregarSeguidor("andpe12", "MIEM");
		
		agregarSeguidor("sicam", "EcoTech");
		agregarSeguidor("sicam", "ANTEL");
		
		agregarSeguidor("sebgon", "FusionTech");
		agregarSeguidor("sebgon", "GlobalHealth");
		
		agregarSeguidor("isabel", "lgarcia");
		agregarSeguidor("isabel", "EcoTech");
		agregarSeguidor("isabel", "FusionTech");
		agregarSeguidor("isabel", "MIEM");
		
		agregarSeguidor("EcoTech", "lgarcia");
		agregarSeguidor("EcoTech", "FusionTech");
		
		agregarSeguidor("FusionTech", "GlobalHealth");
		
		agregarSeguidor("GlobalHealth", "lgarcia");
		agregarSeguidor("GlobalHealth", "ANTEL");
		agregarSeguidor("GlobalHealth", "MIEM");
		agregarSeguidor("GlobalHealth", "TechSolutions");
		
		agregarSeguidor("ANTEL", "MIEM");
		
		agregarSeguidor("MIEM", "ANTEL");
		
		agregarSeguidor("TechSolutions", "MIEM");
	
	}

	public void cargarDatosCompraPaquetes() throws Exception{

		comprarPaquete("EcoTech",
				"Básico", LocalDate.of(2023, 10, 30));
		comprarPaquete("TechSolutions",
				"Destacado", LocalDate.of(2023, 10, 8));
		comprarPaquete("EcoTech", 
				"Premium", LocalDate.of(2023, 10, 31));
		comprarPaquete("FusionTech", 
				"Destacado", LocalDate.of(2023, 10, 13));
		comprarPaquete("EcoTech", 
				"Express", LocalDate.of(2023, 10, 1));

	}
	
	
	
	public void cargarDatosPostulaciones() throws Exception {
		//Carga Postulaciones
		ingresarDatosPostulacion(
		    "Licenciada en Administración, experiencia en gestión de equipos y proyectos. Conocimientos en Microsoft Office.",
		    "Estoy emocionada por la oportunidad de formar parte de un equipo dinámico y contribuir con mis habilidades de liderazgo.",
		    LocalDate.of(2023, 10, 1),
			"Desarrollador Frontend",
			"lgarcia","https://www.youtube.com/embed/sqh77QZS0G4"
		);
	
		ingresarDatosPostulacion(
		    "Estudiante de Comunicación, habilidades en redacción y manejo de redes sociales. Experiencia en prácticas en medios locales.",
		    "Me encantaría formar parte de un equipo que me permita desarrollar mis habilidades en comunicación y marketing.",
		    LocalDate.of(2023, 9, 30),
		    "Estratega de Negocios",
			"matilo","https://www.youtube.com/embed/ekm1D3sKoVA"
		);
	
		ingresarDatosPostulacion(
		    "Ingeniero en Sistemas, experiencia en desarrollo web y aplicaciones móviles. Conocimientos en JavaScript y React.",
		    "Me entusiasma la posibilidad de trabajar en proyectos desafiantes y seguir creciendo como profesional en el campo de la tecnología.",
		    LocalDate.of(2023, 10, 2),
		    "Desarrollador Frontend",
			"maro",""
	
		);
	
		ingresarDatosPostulacion(
		    "Técnico en Electricidad, experiencia en mantenimiento industrial. Conocimientos en lectura de planos eléctricos.",
		    "Estoy interesado en formar parte de un equipo que me permita aplicar mis habilidades técnicas y contribuir al mantenimiento eficiente.",
		    LocalDate.of(2023, 10, 30),
		    "Diseñador UX-UI",
			"javierf","https://www.youtube.com/embed/uNCzhfQCqAs"
		);
	
		ingresarDatosPostulacion(
		    "Músico profesional, experiencia en espectáculos en vivo. Habilidades en canto y guitarra.",
		    "Me gustaría combinar mi pasión por la música con una oportunidad laboral que me permita seguir creciendo como artista.",
		    LocalDate.of(2023, 9, 30),
		    "Estratega de Negocios",
			"valen25","https://www.youtube.com/embed/jwiV9gbjEi8"
		);
	
		ingresarDatosPostulacion(
		    "Licenciada en Administración, me considero genia, experiencia en gestión de equipos y proyectos. Conocimientos en Microsoft Office.",
		    "Estoy emocionada por la oportunidad de formar parte de un equipo dinámico y contribuir con mis habilidades de liderazgo.",
		    LocalDate.of(2023, 10, 2),
		    "Estratega de Negocios",
			"lgarcia",""
		);
		
		ingresarDatosPostulacion(
		    "Licenciada en Administración, me considero genia, experiencia en gestión de equipos y proyectos. Conocimientos en Microsoft Office.",
		    "Estoy emocionada por la oportunidad de formar parte de un equipo dinámico y contribuir con mis habilidades de liderazgo.",
		    LocalDate.of(2023, 10, 21),
		    "Content Manager",
			"lgarcia",""
		);
		
		ingresarDatosPostulacion(
		    "Me manejo las redes, tengo 20M de seguidores.",
		    "Me gustaría combinar mi pasión por la música con una oportunidad laboral que me permita seguir creciendo como artista.",
		    LocalDate.of(2023, 10, 22),
		    "Content Manager",
			"valen25","https://www.youtube.com/embed/jwiV9gbjEi8"
		);
		
		//resultados de postulacion
		rankearPostulante("Desarrollador Frontend", "maro", LocalDate.now(), 1);
		rankearPostulante("Desarrollador Frontend", "lgarcia", LocalDate.now(), 2);
		rankearPostulante("Estratega de Negocios", "matilo", LocalDate.now(), 3);
		rankearPostulante("Estratega de Negocios", "valen25", LocalDate.now(), 2);
		rankearPostulante("Estratega de Negocios", "lgarcia", LocalDate.now(), 1);

		//favoritos
		agregarOfertaFavorita("lgarcia", "Desarrollador Frontend");
		agregarOfertaFavorita("lgarcia", "A. de Marketing Digital");
		agregarOfertaFavorita("lgarcia", "Gerente de Proyecto");
		agregarOfertaFavorita("matilo", "A. de Marketing Digital");
		agregarOfertaFavorita("maro", "Desarrollador Frontend");
		agregarOfertaFavorita("maro", "Gerente de Proyecto");
		agregarOfertaFavorita("javierf", "A. de Marketing Digital");
		agregarOfertaFavorita("valen25", "Técnico-a Básico Red");
		agregarOfertaFavorita("valen25", "A. de Marketing Digital");
		
	}
	
	public DtCompraPaquete obtenerCompra(String empresa, String paquete) throws PaqueteNoExisteException {
		DtCompraPaquete compra = null;
		try {
			Empresa emp = (Empresa) ManejadorUsuarios.getInstance().obtenerUsuario(empresa);
			CompraPaquete paq = emp.obtenerPaqueteComprado(paquete);
			if (paq == null) 
				throw new PaqueteNoExisteException("Paquete sin comprar");
			compra = paq.getCompraPaquete();
		} catch (UsuarioNoExisteException  e) {
			e.printStackTrace();
		}
		
		return compra;
		
	}

	public void agregarOfertaFavorita(String postulante, String oferta) throws UsuarioNoExisteException {
		Postulante post = (Postulante) ManejadorUsuarios.getInstance().obtenerUsuario(postulante);
		OfertaLab ofer = ManejadorOfertaLaboral.getInstance().obtenerOferta(oferta);
		
		post.agregarOfertaFavorita(ofer);
		ofer.agregarFavorito(post);
	}
	
	public void eliminarOfertaFavorita(String postulante, String oferta) throws UsuarioNoExisteException {
		Postulante post = (Postulante) ManejadorUsuarios.getInstance().obtenerUsuario(postulante);
		OfertaLab ofer = ManejadorOfertaLaboral.getInstance().obtenerOferta(oferta);
		
		post.eliminarOfertaFavorita(ofer);
		ofer.eliminarFavorito(post);
	}
	
	public DtOferta[] obtenerOfertasFavoritas(String postulante) throws UsuarioNoExisteException {
		Postulante post = (Postulante) ManejadorUsuarios.getInstance().obtenerUsuario(postulante);
		List<OfertaLab> ofertas = post.getOfertasFavoritas();
		List<DtOferta> res = new ArrayList<DtOferta>();
		for (OfertaLab oferta : ofertas) {
			res.add(oferta.obtenerDtOferta());
		}
		//exception
		return res.toArray(new DtOferta[0]);
	}
	
}
