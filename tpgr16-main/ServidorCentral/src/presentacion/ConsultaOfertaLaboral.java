package presentacion;
import javax.swing.JInternalFrame;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import logica.datatypes.DtEmpresa;
import logica.datatypes.DtUsuario;
import logica.datatypes.DtOferta;
import logica.datatypes.DtPostulacion;
import logica.interfaces.*;

import com.toedter.calendar.JDateChooser;

import excepciones.NoPoseePostulacionesException;

import javax.swing.JList;
import java.awt.Font;
import javax.swing.JSpinner;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class ConsultaOfertaLaboral extends JInternalFrame{
	private IUsuario interfazUsuario;
	private IOfertaLab interfazOfertaLaboral;

	DtUsuario usu;
	private JTextArea descripcionField;
	private JTextField nombreField;
	private JTextField ciudadField;
	private JTextField deptoField;
	private JSpinner horarioField;
	private JTextField remuneracionField;
	private JTextField costeField;
	
	private JLabel lblNombre;
	private JLabel lblDescripcion;
	private JSeparator separator;
	private JLabel lblListaOfertasLaborales;
	private JLabel lblCiudad;
	private JLabel lblDepartamento;
	private JLabel lblHorario;
	private JLabel lblRemuneracion;
	private JLabel lblFechaAlta;
	private JLabel lblCoste;
	
	private JList<DtPostulacion> postulacionesList;
	private JSpinner horarioField_1;
	private JSpinner horarioField_2;
	private JSpinner horarioField_3;
	private JLabel lblA;
	private JLabel lblA_1;
	private JLabel lblA_2;
	private JComboBox<DtEmpresa> comboBoxEmpresas;
	private JComboBox<DtOferta> comboBoxOfertas;
	private JDateChooser fechaAltaField;
	private JScrollPane scrollPane;
	private JLabel lblDuracion;
	private JDateChooser fechaDuracion;
	private JScrollPane scrollPane_1;
	
	
	public ConsultaOfertaLaboral(IUsuario interfazUsu, IOfertaLab iOfertaLaboral) {
		interfazUsuario = interfazUsu;
		interfazOfertaLaboral = iOfertaLaboral;
		
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
		setClosable(true);
		setTitle("Consulta de Oferta Laboral");
		setBounds(0, 0, 600,600);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{164, 55, 40, 55, 55, 55, 40, 55, 0};
		gridBagLayout.rowHeights = new int[]{0, 19, 0, 0, 99, 27, 0, 0, 27, 0, 0, 0, 59, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 0.0, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 1.0, 1.0};
		getContentPane().setLayout(gridBagLayout);
		
		comboBoxOfertas = new JComboBox<DtOferta>();
		comboBoxOfertas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				mostrarOfertaSeleccionada((DtOferta)comboBoxOfertas.getSelectedItem());
			}
		});
		
		JLabel lblListaDeEmpresas = new JLabel("Empresa:");
		GridBagConstraints gbc_lblListaDeEmpresas = new GridBagConstraints();
		gbc_lblListaDeEmpresas.anchor = GridBagConstraints.EAST;
		gbc_lblListaDeEmpresas.insets = new Insets(0, 0, 5, 5);
		gbc_lblListaDeEmpresas.gridx = 0;
		gbc_lblListaDeEmpresas.gridy = 0;
		getContentPane().add(lblListaDeEmpresas, gbc_lblListaDeEmpresas);
		
		comboBoxEmpresas = new JComboBox<DtEmpresa>();
		comboBoxEmpresas.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent evento) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent evento) {
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent evento) {
				cargarEmpresas();
			}
		});
		
		comboBoxEmpresas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				limpiarEntrada();
				mostrarOfertasDeEmpresa((DtEmpresa)comboBoxEmpresas.getSelectedItem());

			}
		});
		GridBagConstraints gbc_comboBoxEmpresas = new GridBagConstraints();
		gbc_comboBoxEmpresas.gridwidth = 7;
		gbc_comboBoxEmpresas.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxEmpresas.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxEmpresas.gridx = 1;
		gbc_comboBoxEmpresas.gridy = 0;
		getContentPane().add(comboBoxEmpresas, gbc_comboBoxEmpresas);

		lblListaOfertasLaborales = new JLabel("Oferta: ");
		GridBagConstraints gbc_lblListaOfertasLaborales = new GridBagConstraints();
		gbc_lblListaOfertasLaborales.anchor = GridBagConstraints.EAST;
		gbc_lblListaOfertasLaborales.insets = new Insets(0, 0, 5, 5);
		gbc_lblListaOfertasLaborales.gridx = 0;
		gbc_lblListaOfertasLaborales.gridy = 1;
		getContentPane().add(lblListaOfertasLaborales, gbc_lblListaOfertasLaborales);
		GridBagConstraints gbc_comboBoxOfertas = new GridBagConstraints();
		gbc_comboBoxOfertas.gridwidth = 7;
		gbc_comboBoxOfertas.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxOfertas.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxOfertas.gridx = 1;
		gbc_comboBoxOfertas.gridy = 1;
		getContentPane().add(comboBoxOfertas, gbc_comboBoxOfertas);
		
		separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.insets = new Insets(0, 80, 5, 0);
		gbc_separator.gridx = 8;
		gbc_separator.gridy = 2;
		getContentPane().add(separator, gbc_separator);
		
		lblNombre = new JLabel("Nombre: ");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 0;
		gbc_lblNombre.gridy = 3;
		getContentPane().add(lblNombre, gbc_lblNombre);
		
		nombreField = new JTextField();
		nombreField.setEditable(false);
		GridBagConstraints gbc_nombreField = new GridBagConstraints();
		gbc_nombreField.gridwidth = 7;
		gbc_nombreField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nombreField.insets = new Insets(0, 0, 5, 5);
		gbc_nombreField.gridx = 1;
		gbc_nombreField.gridy = 3;
		getContentPane().add(nombreField, gbc_nombreField);
		
		lblDescripcion = new JLabel("Descripcion: ");
		GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
		gbc_lblDescripcion.anchor = GridBagConstraints.EAST;
		gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcion.gridx = 0;
		gbc_lblDescripcion.gridy = 4;
		getContentPane().add(lblDescripcion, gbc_lblDescripcion);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 7;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 4;
		getContentPane().add(scrollPane, gbc_scrollPane);
		
		descripcionField = new JTextArea();
		descripcionField.setEditable(false);
		descripcionField.setEnabled(false);
		descripcionField.setLineWrap(true);
        descripcionField.setWrapStyleWord(true);       
		scrollPane.setViewportView(descripcionField);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				limpiarEntrada();
				setVisible(false);
			}
		});
		
		lblCiudad = new JLabel("Ciudad:");
		GridBagConstraints gbc_lblCiudad = new GridBagConstraints();
		gbc_lblCiudad.anchor = GridBagConstraints.EAST;
		gbc_lblCiudad.insets = new Insets(0, 0, 5, 5);
		gbc_lblCiudad.gridx = 0;
		gbc_lblCiudad.gridy = 5;
		getContentPane().add(lblCiudad, gbc_lblCiudad);
		
		ciudadField = new JTextField();
		ciudadField.setEditable(false);
		GridBagConstraints gbc_ciudadField = new GridBagConstraints();
		gbc_ciudadField.gridwidth = 7;
		gbc_ciudadField.insets = new Insets(0, 0, 5, 5);
		gbc_ciudadField.fill = GridBagConstraints.HORIZONTAL;
		gbc_ciudadField.gridx = 1;
		gbc_ciudadField.gridy = 5;
		getContentPane().add(ciudadField, gbc_ciudadField);
		
		lblDepartamento = new JLabel("Departamento:");
		GridBagConstraints gbc_lblDepartamento = new GridBagConstraints();
		gbc_lblDepartamento.anchor = GridBagConstraints.EAST;
		gbc_lblDepartamento.insets = new Insets(0, 0, 5, 5);
		gbc_lblDepartamento.gridx = 0;
		gbc_lblDepartamento.gridy = 6;
		getContentPane().add(lblDepartamento, gbc_lblDepartamento);
		
		deptoField = new JTextField();
		deptoField.setEditable(false);
		GridBagConstraints gbc_deptoField = new GridBagConstraints();
		gbc_deptoField.gridwidth = 7;
		gbc_deptoField.insets = new Insets(0, 0, 5, 5);
		gbc_deptoField.fill = GridBagConstraints.HORIZONTAL;
		gbc_deptoField.gridx = 1;
		gbc_deptoField.gridy = 6;
		getContentPane().add(deptoField, gbc_deptoField);
		
		lblHorario = new JLabel("Horario(24Hs):");
		GridBagConstraints gbc_lblHorario = new GridBagConstraints();
		gbc_lblHorario.anchor = GridBagConstraints.EAST;
		gbc_lblHorario.insets = new Insets(0, 0, 5, 5);
		gbc_lblHorario.gridx = 0;
		gbc_lblHorario.gridy = 7;
		getContentPane().add(lblHorario, gbc_lblHorario);
		
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, 23, 1);
		horarioField = new JSpinner(spinnerModel);
		horarioField.setEnabled(false);
		GridBagConstraints gbc_horarioField = new GridBagConstraints();
		gbc_horarioField.insets = new Insets(0, 0, 5, 5);
		gbc_horarioField.fill = GridBagConstraints.HORIZONTAL;
		gbc_horarioField.gridx = 1;
		gbc_horarioField.gridy = 7;
		getContentPane().add(horarioField, gbc_horarioField);
		
		lblA_1 = new JLabel(":");
		GridBagConstraints gbc_lblA_1 = new GridBagConstraints();
		gbc_lblA_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblA_1.gridx = 2;
		gbc_lblA_1.gridy = 7;
		getContentPane().add(lblA_1, gbc_lblA_1);
		
        SpinnerNumberModel spinnerModel2 = new SpinnerNumberModel(0, 0, 59, 1);
		horarioField_1 = new JSpinner(spinnerModel2);
		horarioField_1.setEnabled(false);
		GridBagConstraints gbc_horarioField_1 = new GridBagConstraints();
		gbc_horarioField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_horarioField_1.insets = new Insets(0, 0, 5, 5);
		gbc_horarioField_1.gridx = 3;
		gbc_horarioField_1.gridy = 7;
		getContentPane().add(horarioField_1, gbc_horarioField_1);
		
		lblA = new JLabel("-");
		GridBagConstraints gbc_lblA = new GridBagConstraints();
		gbc_lblA.insets = new Insets(0, 0, 5, 5);
		gbc_lblA.gridx = 4;
		gbc_lblA.gridy = 7;
		getContentPane().add(lblA, gbc_lblA);
		
        SpinnerNumberModel spinnerModel3 = new SpinnerNumberModel(0, 0, 23, 1);
		horarioField_2 = new JSpinner(spinnerModel3);
		horarioField_2.setEnabled(false);
		GridBagConstraints gbc_horarioField_2 = new GridBagConstraints();
		
		gbc_horarioField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_horarioField_2.insets = new Insets(0, 0, 5, 5);
		gbc_horarioField_2.gridx = 5;
		gbc_horarioField_2.gridy = 7;
		getContentPane().add(horarioField_2, gbc_horarioField_2);
		
		lblA_2 = new JLabel(":");
		GridBagConstraints gbc_lblA_2 = new GridBagConstraints();
		gbc_lblA_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblA_2.gridx = 6;
		gbc_lblA_2.gridy = 7;
		getContentPane().add(lblA_2, gbc_lblA_2);
        SpinnerNumberModel spinnerModel4 = new SpinnerNumberModel(0, 0, 59, 1);

		horarioField_3 = new JSpinner(spinnerModel4);
		horarioField_3.setEnabled(false);
		GridBagConstraints gbc_horarioField_3 = new GridBagConstraints();
		gbc_horarioField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_horarioField_3.insets = new Insets(0, 0, 5, 5);
		gbc_horarioField_3.gridx = 7;
		gbc_horarioField_3.gridy = 7;
		getContentPane().add(horarioField_3, gbc_horarioField_3);
		
		lblRemuneracion = new JLabel("Remuneracion:");
		GridBagConstraints gbc_lblRemuneracion = new GridBagConstraints();
		gbc_lblRemuneracion.anchor = GridBagConstraints.EAST;
		gbc_lblRemuneracion.insets = new Insets(0, 0, 5, 5);
		gbc_lblRemuneracion.gridx = 0;
		gbc_lblRemuneracion.gridy = 8;
		getContentPane().add(lblRemuneracion, gbc_lblRemuneracion);
		
		remuneracionField = new JTextField();
		remuneracionField.setEditable(false);
		GridBagConstraints gbc_remuneracionField = new GridBagConstraints();
		gbc_remuneracionField.gridwidth = 7;
		gbc_remuneracionField.insets = new Insets(0, 0, 5, 5);
		gbc_remuneracionField.fill = GridBagConstraints.HORIZONTAL;
		gbc_remuneracionField.gridx = 1;
		gbc_remuneracionField.gridy = 8;
		getContentPane().add(remuneracionField, gbc_remuneracionField);
		
		lblFechaAlta = new JLabel("Fecha Alta:");
		GridBagConstraints gbc_lblFechaAlta = new GridBagConstraints();
		gbc_lblFechaAlta.anchor = GridBagConstraints.EAST;
		gbc_lblFechaAlta.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaAlta.gridx = 0;
		gbc_lblFechaAlta.gridy = 9;
		getContentPane().add(lblFechaAlta, gbc_lblFechaAlta);
		
		fechaAltaField = new JDateChooser();
		fechaAltaField.setEnabled(false);
		GridBagConstraints gbc_fechaAltaField = new GridBagConstraints();
		gbc_fechaAltaField.gridwidth = 7;
		gbc_fechaAltaField.insets = new Insets(0, 0, 5, 5);
		gbc_fechaAltaField.fill = GridBagConstraints.BOTH;
		gbc_fechaAltaField.gridx = 1;
		gbc_fechaAltaField.gridy = 9;
		getContentPane().add(fechaAltaField, gbc_fechaAltaField);
		
		lblDuracion = new JLabel("Hasta(duracion):");
		lblDuracion.setFont(new Font("Dialog", Font.BOLD, 12));
		GridBagConstraints gbc_lblDuracion = new GridBagConstraints();
		gbc_lblDuracion.anchor = GridBagConstraints.EAST;
		gbc_lblDuracion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDuracion.gridx = 0;
		gbc_lblDuracion.gridy = 10;
		getContentPane().add(lblDuracion, gbc_lblDuracion);
		
		fechaDuracion = new JDateChooser();
		fechaDuracion.setEnabled(false);
		GridBagConstraints gbc_fechaDuracion = new GridBagConstraints();
		gbc_fechaDuracion.gridwidth = 7;
		gbc_fechaDuracion.insets = new Insets(0, 0, 5, 5);
		gbc_fechaDuracion.fill = GridBagConstraints.BOTH;
		gbc_fechaDuracion.gridx = 1;
		gbc_fechaDuracion.gridy = 10;
		getContentPane().add(fechaDuracion, gbc_fechaDuracion);
		
		lblCoste = new JLabel("Coste:");
		GridBagConstraints gbc_lblCoste = new GridBagConstraints();
		gbc_lblCoste.anchor = GridBagConstraints.EAST;
		gbc_lblCoste.insets = new Insets(0, 0, 5, 5);
		gbc_lblCoste.gridx = 0;
		gbc_lblCoste.gridy = 11;
		getContentPane().add(lblCoste, gbc_lblCoste);
		
		costeField = new JTextField();
		costeField.setEditable(false);
		GridBagConstraints gbc_costeField = new GridBagConstraints();
		gbc_costeField.gridwidth = 7;
		gbc_costeField.insets = new Insets(0, 0, 5, 5);
		gbc_costeField.fill = GridBagConstraints.HORIZONTAL;
		gbc_costeField.gridx = 1;
		gbc_costeField.gridy = 11;
		getContentPane().add(costeField, gbc_costeField);
		
		scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridwidth = 7;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_1.gridx = 1;
		gbc_scrollPane_1.gridy = 12;
		getContentPane().add(scrollPane_1, gbc_scrollPane_1);
		
		postulacionesList = new JList<DtPostulacion>();
		scrollPane_1.setViewportView(postulacionesList);
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.gridwidth = 7;
		gbc_btnCancelar.insets = new Insets(10, 5, 0, 5);
		gbc_btnCancelar.gridx = 1;
		gbc_btnCancelar.gridy = 13;
		getContentPane().add(btnCancelar, gbc_btnCancelar);
		
	}
	
	private void mostrarOfertaSeleccionada(DtOferta oferta) {
		if (oferta == null) return;
		this.nombreField.setVisible(true);
		this.lblDepartamento.setVisible(true);
		this.scrollPane.setVisible(true);
		this.lblFechaAlta.setVisible(true);
		this.lblCoste.setVisible(true);
		this.lblHorario.setVisible(true);
		this.lblRemuneracion.setVisible(true);
		this.lblNombre.setVisible(true);
		this.lblCiudad.setVisible(true);
		this.lblDepartamento.setVisible(true);
		this.lblA_1.setVisible(true);
		this.lblA_2.setVisible(true);
		this.lblA.setVisible(true);
		this.lblDescripcion.setVisible(true);
		this.lblDuracion.setVisible(true);
		this.fechaDuracion.setVisible(true);
		this.nombreField.setVisible(true);
		this.descripcionField.setVisible(true);
		this.ciudadField.setVisible(true);
		this.deptoField.setVisible(true);
		this.horarioField.setVisible(true);
		this.horarioField_1.setVisible(true);
		this.horarioField_2.setVisible(true);
		this.horarioField_3.setVisible(true);
		this.remuneracionField.setVisible(true);
		this.fechaAltaField.setVisible(true);
		this.costeField.setVisible(true);
		this.fechaAltaField.setVisible(true);
		this.nombreField.setText(oferta.getNombre());
		this.descripcionField.setText(oferta.getDescripcion());
		this.ciudadField.setText(oferta.getCiudad());
		this.deptoField.setText(oferta.getDepartamento());
		String horario = oferta.getHorario(); 
		String[] partes = horario.split(":"); // Dividir la cadena en partes usando el separador ":"

		Integer horario1 = Integer.parseInt(partes[0]); 
		Integer horario2 = Integer.parseInt(partes[1]); 
		Integer horario3 = Integer.parseInt(partes[2]);
		Integer horario4 = Integer.parseInt(partes[3]); 
		
		this.horarioField.setValue(horario1);
		this.horarioField_1.setValue(horario2);
		this.horarioField_2.setValue(horario3);
		this.horarioField_3.setValue(horario4);
		
		
		int anio = oferta.getFechaAlta().plusDays(oferta.getDuracion()).getYear();
        int mes = oferta.getFechaAlta().plusDays(oferta.getDuracion()).getMonthValue();
        int dia = oferta.getFechaAlta().plusDays(oferta.getDuracion()).getDayOfMonth();
	    Calendar calendar = Calendar.getInstance();
	    calendar.set(Calendar.YEAR, anio);
	    calendar.set(Calendar.MONTH, mes-1); // Mes (0-11)
	    calendar.set(Calendar.DAY_OF_MONTH, dia);
	    this.fechaDuracion.setCalendar(calendar);
	
		this.remuneracionField.setText(oferta.getRemuneracion().toString());
		
		int anioAlta = oferta.getFechaAlta().getYear();
        int mesAlta = oferta.getFechaAlta().getMonthValue();
        int diaAlta = oferta.getFechaAlta().getDayOfMonth();
	    Calendar calendar1 = Calendar.getInstance();
	    calendar1.set(Calendar.YEAR, anioAlta);
	    calendar1.set(Calendar.MONTH, mesAlta-1); // Mes (0-11)
	    calendar1.set(Calendar.DAY_OF_MONTH, diaAlta);
	    this.fechaAltaField.setCalendar(calendar1);
	    
		this.costeField.setText(oferta.getCoste().toString());
		
		try {
			this.cargarPostulacionesDeOferta(oferta);
		} catch (NoPoseePostulacionesException e) {
			
		}
	} 
	
	public void cargarEmpresas() {
		try {
			DtEmpresa[] empresas = interfazUsuario.obtenerEmpresas();
			if (empresas != null) {
				DefaultComboBoxModel<DtEmpresa> model = new DefaultComboBoxModel<DtEmpresa>(empresas);
				comboBoxEmpresas.setModel(model);
			}
			comboBoxEmpresas.repaint();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	private void cargarPostulacionesDeOferta(DtOferta oferta) throws NoPoseePostulacionesException {
		DtPostulacion[] postulaciones = interfazOfertaLaboral.obtenerPostulaciones(oferta.getNombre());
		if (postulaciones == null) {
			this.postulacionesList.setVisible(false);
			this.scrollPane_1.setVisible(false);
		}else {
			DefaultListModel<DtPostulacion> postulacionesListModel = new DefaultListModel<>();
			for (DtPostulacion postulacion : postulaciones) {
				postulacionesListModel.addElement(postulacion);
			}
			this.postulacionesList.setModel(postulacionesListModel);
			this.scrollPane_1.setVisible(true);
			this.postulacionesList.setVisible(true);
			//this.postulacionesList.repaint();
		}
	}
	
	private void mostrarOfertasDeEmpresa(DtEmpresa empresa) {
		try {
			if (empresa !=null) {
				DtOferta[] ofertas = interfazOfertaLaboral.obtenerOfertas(empresa.getNickname());
				if (ofertas != null) {
					DefaultComboBoxModel<DtOferta> model = new DefaultComboBoxModel<DtOferta>(ofertas);
					comboBoxOfertas.setModel(model);
				}
				comboBoxEmpresas.repaint();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cargarDatosOferta(DtOferta oferta, DtUsuario usuario) {
		DtEmpresa empresa=null;
		if (usuario instanceof DtEmpresa) {
			empresa = (DtEmpresa) usuario;
		}else {
			empresa = interfazOfertaLaboral.obtenerEmpresa(oferta.getNombre());
		}
		
		this.comboBoxEmpresas.addItem(empresa);
		this.comboBoxEmpresas.setSelectedItem(empresa);
		this.comboBoxEmpresas.setEditable(false);
		this.comboBoxEmpresas.setEnabled(false);
		
		this.comboBoxOfertas.addItem(oferta);
		this.comboBoxOfertas.setSelectedItem(oferta);
		this.comboBoxOfertas.setEditable(false);
		this.comboBoxOfertas.setEnabled(false);
		mostrarOfertaSeleccionada(oferta);
	}
	
	public void modoInteractivo() {
		//this.comboBoxEmpresas.setEditable(true);
		this.comboBoxEmpresas.setEnabled(true);
		//this.comboBoxOfertas.setEditable(true);
		this.comboBoxOfertas.setEnabled(true);
	}


	public  void limpiarEntrada() {
		this.fechaDuracion.setVisible(false);
		this.lblDuracion.setVisible(false);

		this.nombreField.setVisible(false);
		this.lblDepartamento.setVisible(false);
		this.scrollPane.setVisible(false);
		this.postulacionesList.setVisible(false);
		this.lblFechaAlta.setVisible(false);
		this.lblCoste.setVisible(false);
		this.lblHorario.setVisible(false);
		this.lblRemuneracion.setVisible(false);
		this.lblNombre.setVisible(false);
		this.lblCiudad.setVisible(false);
		this.lblDepartamento.setVisible(false);
		this.lblA_1.setVisible(false);
		this.lblA_2.setVisible(false);
		this.lblA.setVisible(false);
		this.lblDescripcion.setVisible(false);
		
		this.scrollPane_1.setVisible(false);
		this.fechaAltaField.setVisible(false);
		this.descripcionField.setText("");
		this.nombreField.setVisible(false);
		this.ciudadField.setVisible(false);
		this.deptoField.setVisible(false);
		this.remuneracionField.setVisible(false);
		this.costeField.setVisible(false);
		this.horarioField.setVisible(false);
		this.horarioField_1.setVisible(false);
		this.horarioField_2.setVisible(false);
		this.horarioField_3.setVisible(false);
		this.fechaAltaField.setDate(null);

	}
}
