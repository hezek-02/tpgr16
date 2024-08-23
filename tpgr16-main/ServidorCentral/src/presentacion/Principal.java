package presentacion;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import logica.Fabrica;
import logica.interfaces.IOfertaLab;
import logica.interfaces.IUsuario;
import webservices.WebServicesIOferta;
import webservices.WebServicesIUsuario;


public class Principal {
	private JFrame ventanaPrincipal;
	private AltaUsuario registrarUsuarioInternalFrame;
	private ConsultaUsuario consultaUsuarioInternalFrame;
	private ModificarUsuario modificarUsuarioInternalFrame;
	private AltaOfertaLaboral altaOfertaLaboralInternalFrame;
	private AltaTipoPublicacion altaTipoPubliInternalFrame;
	private PostulacionAOfertaLaboral postularAOfertaInternalFrame;
	private AltaPaqueteTipoPublicacion altaPaqueteInternalFrame;
	private AgregarTipoPubAPaquete agregarTipoPubInternalFrame;
	private ConsultaPaquetesTipoPub consultaPaqueteInternalFrame;
	private ConsultaOfertaLaboral consultaOfertaLaboralInternalFrame;
	private ConsultaTipoPublicacion consultaTPInternalFrame;
	private AceptarRechazarOfertaLaboral confirmarOfertaLaboralInternalFrame;
	private OfertasMasVisitadas ofertasMasVisitadasInternalFrame;
	
	public static void main(String[] args) { 
		
		EventQueue.invokeLater(new Runnable() { //define la ejecución de la GUI, actualizaciones en la misma
			public void run() {

				Principal window = new Principal();
				window.ventanaPrincipal.setVisible(true);
				
				try {
			        WebServicesIUsuario usuarioService = new WebServicesIUsuario();
			        usuarioService.publicar(); 

			        WebServicesIOferta ofertaService = new WebServicesIOferta();
			        ofertaService.publicar();
			        

				} catch (Exception e) { 
					e.printStackTrace();
				} 
				
				
			}
		});
	}
	
	public Principal() {
		
		Fabrica fabrica = Fabrica.getInstance();
		IUsuario interfazUsuario = fabrica.getIUsuario();
		IOfertaLab interfazOfertaLaboral = fabrica.getIOfertaLaboral();
		
		inicializar(interfazUsuario,interfazOfertaLaboral);

		
		ventanaPrincipal.getContentPane().setLayout(null);//define que al mostrar los internal frames dentro, no esten atados al layout del pane
		
		// Alta Usuario
		registrarUsuarioInternalFrame = new AltaUsuario(interfazUsuario);
		registrarUsuarioInternalFrame.setVisible(false);
		registrarUsuarioInternalFrame.altaCombo();
    	registrarUsuarioInternalFrame.actualizarForm();
		
		// Alta Oferta Laboral
		altaOfertaLaboralInternalFrame = new AltaOfertaLaboral(interfazOfertaLaboral, interfazUsuario);
		altaOfertaLaboralInternalFrame.setVisible(false);
		
		// Consulta Oferta Laboral
		consultaOfertaLaboralInternalFrame = new ConsultaOfertaLaboral(interfazUsuario, interfazOfertaLaboral);
		consultaOfertaLaboralInternalFrame.setVisible(false);
		
		// Consulta Usuario
		consultaUsuarioInternalFrame = new ConsultaUsuario(interfazUsuario, interfazOfertaLaboral, consultaOfertaLaboralInternalFrame);
		consultaUsuarioInternalFrame.setVisible(false);
		
		// Modificar Usuario
		modificarUsuarioInternalFrame = new ModificarUsuario(interfazUsuario);
		modificarUsuarioInternalFrame.setVisible(false);
		
		// Alta Tipo Publicacion
		altaTipoPubliInternalFrame = new AltaTipoPublicacion(interfazOfertaLaboral);
		altaTipoPubliInternalFrame.setVisible(false);
		
		// Postulacion a Oferta Laboral
		postularAOfertaInternalFrame = new PostulacionAOfertaLaboral(interfazUsuario, interfazOfertaLaboral);
		postularAOfertaInternalFrame.setVisible(false);
		
		//alta paquete
		altaPaqueteInternalFrame = new AltaPaqueteTipoPublicacion(interfazOfertaLaboral);
		altaPaqueteInternalFrame.setVisible(false);
		
		//agregar tipo publicacion a paquete
		agregarTipoPubInternalFrame = new AgregarTipoPubAPaquete(interfazOfertaLaboral);
		agregarTipoPubInternalFrame.setVisible(false);
		
		consultaTPInternalFrame = new ConsultaTipoPublicacion(interfazOfertaLaboral);
		consultaTPInternalFrame.setVisible(false);
		//consulta paquete
		consultaPaqueteInternalFrame = new ConsultaPaquetesTipoPub(interfazOfertaLaboral, consultaTPInternalFrame);
		consultaPaqueteInternalFrame.setVisible(false);
		
		//confirmar oferta
		confirmarOfertaLaboralInternalFrame = new AceptarRechazarOfertaLaboral(interfazOfertaLaboral,interfazUsuario);
		confirmarOfertaLaboralInternalFrame.setVisible(false);
		
		//ofertas mas visitadas
		ofertasMasVisitadasInternalFrame = new OfertasMasVisitadas(interfazUsuario, interfazOfertaLaboral);
		ofertasMasVisitadasInternalFrame.setVisible(false);
		
		//
		ventanaPrincipal.getContentPane().add(consultaTPInternalFrame);
		ventanaPrincipal.getContentPane().add(registrarUsuarioInternalFrame);
		ventanaPrincipal.getContentPane().add(consultaUsuarioInternalFrame); 
		ventanaPrincipal.getContentPane().add(modificarUsuarioInternalFrame);
		ventanaPrincipal.getContentPane().add(altaOfertaLaboralInternalFrame);
		ventanaPrincipal.getContentPane().add(altaTipoPubliInternalFrame);
		ventanaPrincipal.getContentPane().add(postularAOfertaInternalFrame);
		ventanaPrincipal.getContentPane().add(altaPaqueteInternalFrame);
		ventanaPrincipal.getContentPane().add(agregarTipoPubInternalFrame);
		ventanaPrincipal.getContentPane().add(consultaPaqueteInternalFrame);
		ventanaPrincipal.getContentPane().add(consultaOfertaLaboralInternalFrame);
		ventanaPrincipal.getContentPane().add(confirmarOfertaLaboralInternalFrame);
		ventanaPrincipal.getContentPane().add(ofertasMasVisitadasInternalFrame);


	}
	
	private void inicializar(IUsuario interfazUsuario,IOfertaLab interfazOfertaLab) {
		ventanaPrincipal = new JFrame();
		ventanaPrincipal.setTitle("Servidor Central");
		ventanaPrincipal.setBounds(100, 100, 650, 650);
		ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		ventanaPrincipal.setJMenuBar(menuBar);
		
		JMenu menuInicio = new JMenu("Inicio");
		menuBar.add(menuInicio);
		
		//Defino y añado menuItem (Salir) a menu (Inicio)
		JMenuItem menuItemSalir = new JMenuItem("Salir");
		menuItemSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				ventanaPrincipal.setVisible(false);
				ventanaPrincipal.dispose();//libera recursos de la ventana, la elimina
			}
		});
		
		JMenuItem mntmCargarDatos = new JMenuItem("Cargar datos");
		mntmCargarDatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					cargasPorDefecto(interfazUsuario,interfazOfertaLab);
				    JOptionPane.showMessageDialog(ventanaPrincipal.getContentPane(), "Los datos se han cargado con éxito", "Carga de datos", JOptionPane.INFORMATION_MESSAGE);	
				    altaOfertaLaboralInternalFrame.cargarKeyWord();
				}catch (Exception el) {
				    JOptionPane.showMessageDialog(ventanaPrincipal.getContentPane(), "Los datos ya han sido cargados", "Carga de datos", JOptionPane.WARNING_MESSAGE);	
				    el.printStackTrace();
				}
			}

		});
		menuInicio.add(mntmCargarDatos);
		menuInicio.add(menuItemSalir);
		
		//Defino y añado menu (Registro)
		JMenu menuRegistro = new JMenu("Usuario");
		menuBar.add(menuRegistro);
		
		JMenuItem menuItemAgregarUsuario= new JMenuItem("Agregar Usuario");
		menuItemAgregarUsuario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	registrarUsuarioInternalFrame.limpiarEntrada();
            	registrarUsuarioInternalFrame.modoAlta("Postulante");     	
            	registrarUsuarioInternalFrame.setVisible(true);
      
            }
        });
		menuRegistro.add(menuItemAgregarUsuario);
		//OfertaLab
		JMenu mnOfertasLab = new JMenu("Ofertas Lab");
		menuBar.add(mnOfertasLab);
		
		JMenuItem menuItemAgregarOfertaLaboral = new JMenuItem("Agregar Oferta Laboral");
		menuItemAgregarOfertaLaboral.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				altaOfertaLaboralInternalFrame.limpiarEntrada();
				altaOfertaLaboralInternalFrame.cargarKeyWord();
				altaOfertaLaboralInternalFrame.setVisible(true);
			}
		});
		mnOfertasLab.add(menuItemAgregarOfertaLaboral);
		
		//TipoPub
		JMenuItem mntmAltaTipoPubli = new JMenuItem("Agregar Tipo Publicacion");
		mntmAltaTipoPubli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				altaTipoPubliInternalFrame.limpiarEntrada();
				altaTipoPubliInternalFrame.setVisible(true);	
			}
		});
		mnOfertasLab.add(mntmAltaTipoPubli);
		
		//Aceptar/Rechazar OfertaLab
		JMenuItem menuItemConfirmarOfertaLaboral = new JMenuItem("Aceptar/Rechazar Oferta Laboral");
		menuItemConfirmarOfertaLaboral.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				confirmarOfertaLaboralInternalFrame.setVisible(true);
			}
		});
		mnOfertasLab.add(menuItemConfirmarOfertaLaboral);

		
		//Agregar Paq
		JMenuItem mntmAgregarPaquete = new JMenuItem("Agregar Paquete");
		mntmAgregarPaquete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				altaPaqueteInternalFrame.limpiarEntrada();
				altaPaqueteInternalFrame.setVisible(true);
			}
		});
		mnOfertasLab.add(mntmAgregarPaquete);
		
		//AgregarTipoPubAPaq
		JMenuItem mntmAgregarTipoPubAPaq = new JMenuItem("Agregar Tipo Publicacion a Paquete");
		mntmAgregarTipoPubAPaq.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				agregarTipoPubInternalFrame.limpiarEntrada();
				agregarTipoPubInternalFrame.setVisible(true);
			}
		});
		mnOfertasLab.add(mntmAgregarTipoPubAPaq);
		JMenu mnConsultas = new JMenu("Consultas");
		menuBar.add(mnConsultas);
		
		//ModificarUsu
		JMenuItem mntmModificarUsuario = new JMenuItem("Modificar Usuario");
		mntmModificarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				modificarUsuarioInternalFrame.limpiarEntrada();
				modificarUsuarioInternalFrame.setVisible(true);	
			}
		});
		menuRegistro.add(mntmModificarUsuario); 
		
		//PostularUsuAOferta
		JMenuItem mntmPostularAOferta = new JMenuItem("Postular a Oferta");
		mntmPostularAOferta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				postularAOfertaInternalFrame.limpiarEntrada();
				postularAOfertaInternalFrame.setVisible(true);
			}
		});
		menuRegistro.add(mntmPostularAOferta);
		
		//ConsultaUsu
		JMenuItem mntmUsuarios = new JMenuItem("Usuarios");
		mntmUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				consultaUsuarioInternalFrame.limpiarEntrada();
				consultaUsuarioInternalFrame.setVisible(true);
				consultaUsuarioInternalFrame.cargaUsuarios();
			}
		});
		mnConsultas.add(mntmUsuarios);
		
		//ConsultaPaquete
		JMenuItem mntmPaquetes = new JMenuItem("Paquetes");
		mntmPaquetes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				consultaPaqueteInternalFrame.limpiarEntrada();
				consultaPaqueteInternalFrame.setVisible(true);
			}
		});
		mnConsultas.add(mntmPaquetes);
		
		
		// Consulta Oferta
		JMenuItem mntmOfertas = new JMenuItem("Ofertas");
		mntmOfertas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				consultaOfertaLaboralInternalFrame.modoInteractivo();
				consultaOfertaLaboralInternalFrame.limpiarEntrada();
				consultaOfertaLaboralInternalFrame.setVisible(true);
			}
		});
		mnConsultas.add(mntmOfertas);
		
		JMenuItem mntmVisitas = new JMenuItem("Ofertas mas Visitadas");
		mntmVisitas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				ofertasMasVisitadasInternalFrame.cargarDatos();
				ofertasMasVisitadasInternalFrame.setVisible(true);
			}
		});
		mnConsultas.add(mntmVisitas);
	}
	
	
	private void cargasPorDefecto(IUsuario interfazUsuario,IOfertaLab interfazOfertaLab) throws Exception{
		interfazUsuario.cargarDatos();
		interfazOfertaLab.cargarDatos();
		interfazUsuario.cargarDatosCompraPaquetes();
		interfazOfertaLab.cargarDatosAltaOfertas();
		interfazUsuario.cargarDatosPostulaciones();
		interfazOfertaLab.cargaPersistenciaOfertasFinalizadas();
	}
}
