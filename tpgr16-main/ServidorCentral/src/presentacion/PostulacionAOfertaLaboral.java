package presentacion;

import logica.datatypes.DtEmpresa;
import logica.interfaces.IUsuario;
import logica.interfaces.IOfertaLab;
import logica.datatypes.DtUsuario;
import logica.datatypes.DtOferta;
import excepciones.FechaInvalidaException;
import excepciones.PostulacionYaExisteException;
import excepciones.UsuarioNoExisteException;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;

@SuppressWarnings("serial")
public class PostulacionAOfertaLaboral extends JInternalFrame {
	
	private IOfertaLab interfazOferta;
	private IUsuario interfazUsuario;
	
	private JTextArea DescripcionField;
	private JTextField DepartamentoField;
	private JTextField CiudadField;
	private JTextField RemuneracionField;
	private JTextField MotivacionField;
	private JTextArea CVField;
	private JDateChooser FechaPost;
	
	private JComboBox<DtEmpresa> comboBoxEmpresa;
	private JComboBox<DtUsuario> comboBoxPostulante;
	private JComboBox<DtOferta> comboBoxOferta;
	private JSpinner horarioField_1;
	private JSpinner horarioField_3; 
	private JSpinner horarioField_4;
	private JSpinner horarioField_2; 
	
	public PostulacionAOfertaLaboral(IUsuario iUsuario, IOfertaLab iOferta) {
		
		interfazUsuario = iUsuario;
		interfazOferta = iOferta;
		
		getContentPane().setFont(new Font("Dialog", Font.PLAIN, 15));
		setTitle("Postulacion a Oferta Laboral");
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(0, 0, 560,505);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{124, 60, 40, 60, 55, 60, 40, 60, 0};
		gridBagLayout.rowHeights = new int[]{27, 25, 0, 76, 0, 0, 0, 0, 0, 84, 0, 0, 35, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblNewLabel_7 = new JLabel("Ingrese los datos deseados:");
		lblNewLabel_7.setFont(new Font("Dialog", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
		gbc_lblNewLabel_7.gridwidth = 8;
		gbc_lblNewLabel_7.fill = GridBagConstraints.VERTICAL;
		gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_7.gridx = 0;
		gbc_lblNewLabel_7.gridy = 0;
		getContentPane().add(lblNewLabel_7, gbc_lblNewLabel_7);
		
		comboBoxEmpresa = new JComboBox<DtEmpresa>();
		comboBoxEmpresa.setMaximumRowCount(7);
		comboBoxEmpresa.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent event) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent event) {
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent event) {
				cargarEmpresas();
			}
		});
		comboBoxEmpresa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				cargarOfertas((DtEmpresa) comboBoxEmpresa.getSelectedItem());
			}
		});
		
		JLabel lblPostEmpresa = new JLabel("Empresa:");
		GridBagConstraints gbc_lblPostEmpresa = new GridBagConstraints();
		gbc_lblPostEmpresa.anchor = GridBagConstraints.EAST;
		gbc_lblPostEmpresa.insets = new Insets(0, 0, 5, 5);
		gbc_lblPostEmpresa.gridx = 0;
		gbc_lblPostEmpresa.gridy = 1;
		getContentPane().add(lblPostEmpresa, gbc_lblPostEmpresa);
		GridBagConstraints gbc_comboBoxEmpresa = new GridBagConstraints();
		gbc_comboBoxEmpresa.anchor = GridBagConstraints.NORTH;
		gbc_comboBoxEmpresa.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxEmpresa.gridwidth = 7;
		gbc_comboBoxEmpresa.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxEmpresa.gridx = 1;
		gbc_comboBoxEmpresa.gridy = 1;
		getContentPane().add(comboBoxEmpresa, gbc_comboBoxEmpresa);
		
		
		comboBoxOferta = new JComboBox<DtOferta>();
		comboBoxOferta.setMaximumRowCount(7);
		comboBoxOferta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				llenarCampos((DtOferta) comboBoxOferta.getSelectedItem());
			}
		});
		
		JLabel lblPostOferta = new JLabel("Oferta:");
		GridBagConstraints gbc_lblPostOferta = new GridBagConstraints();
		gbc_lblPostOferta.anchor = GridBagConstraints.EAST;
		gbc_lblPostOferta.insets = new Insets(0, 0, 5, 5);
		gbc_lblPostOferta.gridx = 0;
		gbc_lblPostOferta.gridy = 2;
		getContentPane().add(lblPostOferta, gbc_lblPostOferta);
		GridBagConstraints gbc_comboBoxOferta = new GridBagConstraints();
		gbc_comboBoxOferta.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxOferta.gridwidth = 7;
		gbc_comboBoxOferta.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxOferta.gridx = 1;
		gbc_comboBoxOferta.gridy = 2;
		getContentPane().add(comboBoxOferta, gbc_comboBoxOferta);
		
		JLabel lblNewLabel = new JLabel("Descripcion:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 3;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 7;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 3;
		getContentPane().add(scrollPane, gbc_scrollPane);
		
		DescripcionField = new JTextArea();
		DescripcionField.setEnabled(false);
		DescripcionField.setLineWrap(true);
        DescripcionField.setWrapStyleWord(true);        
		scrollPane.setViewportView(DescripcionField);
		DescripcionField.setEditable(false);
		DescripcionField.setColumns(2);
		
		JLabel lblNewLabel_1 = new JLabel("Departamento:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 4;
		getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		DepartamentoField = new JTextField();
		DepartamentoField.setEditable(false);
		GridBagConstraints gbc_DepartamentoField = new GridBagConstraints();
		gbc_DepartamentoField.gridwidth = 7;
		gbc_DepartamentoField.insets = new Insets(0, 0, 5, 0);
		gbc_DepartamentoField.fill = GridBagConstraints.HORIZONTAL;
		gbc_DepartamentoField.gridx = 1;
		gbc_DepartamentoField.gridy = 4;
		getContentPane().add(DepartamentoField, gbc_DepartamentoField);
		DepartamentoField.setColumns(2);
		
		JLabel lblNewLabel_2 = new JLabel("Ciudad:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 5;
		getContentPane().add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		CiudadField = new JTextField();
		CiudadField.setEditable(false);
		GridBagConstraints gbc_CiudadField = new GridBagConstraints();
		gbc_CiudadField.gridwidth = 7;
		gbc_CiudadField.insets = new Insets(0, 0, 5, 0);
		gbc_CiudadField.fill = GridBagConstraints.HORIZONTAL;
		gbc_CiudadField.gridx = 1;
		gbc_CiudadField.gridy = 5;
		getContentPane().add(CiudadField, gbc_CiudadField);
		CiudadField.setColumns(2);
		
		JLabel lblHorario = new JLabel("Horario:");
		GridBagConstraints gbc_lblHorario = new GridBagConstraints();
		gbc_lblHorario.anchor = GridBagConstraints.EAST;
		gbc_lblHorario.insets = new Insets(0, 0, 5, 5);
		gbc_lblHorario.gridx = 0;
		gbc_lblHorario.gridy = 6;
		getContentPane().add(lblHorario, gbc_lblHorario);
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, 23, 1);
		horarioField_1 = new JSpinner(spinnerModel);
		horarioField_1.setEnabled(false);
		GridBagConstraints gbc_horarioField_1 = new GridBagConstraints();
		gbc_horarioField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_horarioField_1.insets = new Insets(0, 0, 5, 5);
		gbc_horarioField_1.gridx = 1;
		gbc_horarioField_1.gridy = 6;
		getContentPane().add(horarioField_1, gbc_horarioField_1);
		
		JLabel entre_1 = new JLabel(":");
		GridBagConstraints gbc_entre_1 = new GridBagConstraints();
		gbc_entre_1.insets = new Insets(0, 0, 5, 5);
		gbc_entre_1.gridx = 2;
		gbc_entre_1.gridy = 6;
		getContentPane().add(entre_1, gbc_entre_1);
        SpinnerNumberModel spinnerModel2 = new SpinnerNumberModel(0, 0, 59, 1);
		
		horarioField_2 = new JSpinner(spinnerModel2);
		horarioField_2.setEnabled(false);
		GridBagConstraints gbc_horarioField_2 = new GridBagConstraints();
		gbc_horarioField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_horarioField_2.insets = new Insets(0, 0, 5, 5);
		gbc_horarioField_2.gridx = 3;
		gbc_horarioField_2.gridy = 6;
		getContentPane().add(horarioField_2, gbc_horarioField_2);
		
		JLabel entre = new JLabel("-");
		GridBagConstraints gbc_entre = new GridBagConstraints();
		gbc_entre.insets = new Insets(0, 0, 5, 5);
		gbc_entre.gridx = 4;
		gbc_entre.gridy = 6;
		getContentPane().add(entre, gbc_entre);
        SpinnerNumberModel spinnerModel3 = new SpinnerNumberModel(0, 0, 23, 1);
		horarioField_3 = new JSpinner(spinnerModel3);
		horarioField_3.setEnabled(false);
		GridBagConstraints gbc_horarioField_3 = new GridBagConstraints();
		gbc_horarioField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_horarioField_3.insets = new Insets(0, 0, 5, 5);
		gbc_horarioField_3.gridx = 5;
		gbc_horarioField_3.gridy = 6;
		getContentPane().add(horarioField_3, gbc_horarioField_3);
		
		JLabel entre_1_1 = new JLabel(":");
		GridBagConstraints gbc_entre_1_1 = new GridBagConstraints();
		gbc_entre_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_entre_1_1.gridx = 6;
		gbc_entre_1_1.gridy = 6;
		getContentPane().add(entre_1_1, gbc_entre_1_1);
        SpinnerNumberModel spinnerModel4 = new SpinnerNumberModel(0, 0, 59, 1);
		horarioField_4 = new JSpinner(spinnerModel4);
		horarioField_4.setEnabled(false);
		GridBagConstraints gbc_horarioField_4 = new GridBagConstraints();
		gbc_horarioField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_horarioField_4.insets = new Insets(0, 0, 5, 0);
		gbc_horarioField_4.gridx = 7;
		gbc_horarioField_4.gridy = 6;
		getContentPane().add(horarioField_4, gbc_horarioField_4);
		
		JLabel lblNewLabel_3 = new JLabel("Remuneracion:");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 7;
		getContentPane().add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		RemuneracionField = new JTextField();
		RemuneracionField.setEditable(false);
		GridBagConstraints gbc_RemuneracionField = new GridBagConstraints();
		gbc_RemuneracionField.gridwidth = 7;
		gbc_RemuneracionField.insets = new Insets(0, 0, 5, 0);
		gbc_RemuneracionField.fill = GridBagConstraints.HORIZONTAL;
		gbc_RemuneracionField.gridx = 1;
		gbc_RemuneracionField.gridy = 7;
		getContentPane().add(RemuneracionField, gbc_RemuneracionField);
		RemuneracionField.setColumns(2);
		
		JLabel lblPostPostulante = new JLabel("Postulante:");
		GridBagConstraints gbc_lblPostPostulante = new GridBagConstraints();
		gbc_lblPostPostulante.anchor = GridBagConstraints.EAST;
		gbc_lblPostPostulante.insets = new Insets(0, 0, 5, 5);
		gbc_lblPostPostulante.gridx = 0;
		gbc_lblPostPostulante.gridy = 8;
		getContentPane().add(lblPostPostulante, gbc_lblPostPostulante);
		
		
		comboBoxPostulante = new JComboBox<DtUsuario>();
		comboBoxPostulante.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent event) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent event) {
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent event) {
				cargarPostulantes();
			}
		});

		GridBagConstraints gbc_comboBoxPostulante = new GridBagConstraints();
		gbc_comboBoxPostulante.gridwidth = 7;
		gbc_comboBoxPostulante.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxPostulante.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxPostulante.gridx = 1;
		gbc_comboBoxPostulante.gridy = 8;
		getContentPane().add(comboBoxPostulante, gbc_comboBoxPostulante);
		
		JLabel lblCV = new JLabel("CV:");
		GridBagConstraints gbc_lblCV = new GridBagConstraints();
		gbc_lblCV.anchor = GridBagConstraints.EAST;
		gbc_lblCV.insets = new Insets(0, 0, 5, 5);
		gbc_lblCV.gridx = 0;
		gbc_lblCV.gridy = 9;
		getContentPane().add(lblCV, gbc_lblCV);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridwidth = 7;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_1.gridx = 1;
		gbc_scrollPane_1.gridy = 9;
		getContentPane().add(scrollPane_1, gbc_scrollPane_1);
		
		CVField = new JTextArea();
		CVField.setLineWrap(true);
		CVField.setWrapStyleWord(true);        
		scrollPane_1.setViewportView(CVField);
		scrollPane_1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		CVField.setColumns(2);
		
		JLabel lblMotivacion = new JLabel("Motivacion:");
		GridBagConstraints gbc_lblMotivacion = new GridBagConstraints();
		gbc_lblMotivacion.anchor = GridBagConstraints.EAST;
		gbc_lblMotivacion.insets = new Insets(0, 0, 5, 5);
		gbc_lblMotivacion.gridx = 0;
		gbc_lblMotivacion.gridy = 10;
		getContentPane().add(lblMotivacion, gbc_lblMotivacion);
		
		MotivacionField = new JTextField();
		GridBagConstraints gbc_MotivacionField = new GridBagConstraints();
		gbc_MotivacionField.gridwidth = 7;
		gbc_MotivacionField.insets = new Insets(0, 0, 5, 0);
		gbc_MotivacionField.fill = GridBagConstraints.HORIZONTAL;
		gbc_MotivacionField.gridx = 1;
		gbc_MotivacionField.gridy = 10;
		getContentPane().add(MotivacionField, gbc_MotivacionField);
		MotivacionField.setColumns(2);
		
		JLabel lblFecha = new JLabel("Fecha Postulacion:");
		GridBagConstraints gbc_lblFecha = new GridBagConstraints();
		gbc_lblFecha.anchor = GridBagConstraints.EAST;
		gbc_lblFecha.insets = new Insets(0, 0, 5, 5);
		gbc_lblFecha.gridx = 0;
		gbc_lblFecha.gridy = 11;
		getContentPane().add(lblFecha, gbc_lblFecha);
		
		FechaPost = new JDateChooser();
		GridBagConstraints gbc_FechaPost = new GridBagConstraints();
		gbc_FechaPost.gridwidth = 7;
		gbc_FechaPost.fill = GridBagConstraints.BOTH;
		gbc_FechaPost.insets = new Insets(0, 0, 5, 0);
		gbc_FechaPost.gridx = 1;
		gbc_FechaPost.gridy = 11;
		getContentPane().add(FechaPost, gbc_FechaPost);
		
		JButton btnAceptarButton = new JButton("Registrar");
		btnAceptarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				registrarPostulacion();
			}
		});
		
		GridBagConstraints gbc_btnAceptarButton = new GridBagConstraints();
		gbc_btnAceptarButton.gridwidth = 3;
		gbc_btnAceptarButton.anchor = GridBagConstraints.ABOVE_BASELINE;
		gbc_btnAceptarButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnAceptarButton.gridx = 1;
		gbc_btnAceptarButton.gridy = 12;
		getContentPane().add(btnAceptarButton, gbc_btnAceptarButton);
		
		JButton btnCancelarButton = new JButton("Cancelar");
		btnCancelarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				limpiarEntrada();
				setVisible(false);
			}
		});
		GridBagConstraints gbc_btnCancelarButton = new GridBagConstraints();
		gbc_btnCancelarButton.gridwidth = 3;
		gbc_btnCancelarButton.gridx = 5;
		gbc_btnCancelarButton.gridy = 12;
		getContentPane().add(btnCancelarButton, gbc_btnCancelarButton);
		
	}
	
	public void cargarEmpresas() {
		try {
			DtEmpresa[] empresas = interfazUsuario.obtenerEmpresas();
			if (empresas != null) {
				DefaultComboBoxModel<DtEmpresa> model = new DefaultComboBoxModel<DtEmpresa>(empresas);
				comboBoxEmpresa.setModel(model);
			}

			comboBoxEmpresa.repaint();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cargarPostulantes() {
		try {
			DtUsuario[] usuarios = interfazUsuario.obtenerPostulantes();
			if (usuarios != null) {
				DefaultComboBoxModel<DtUsuario> model = new DefaultComboBoxModel<DtUsuario>(usuarios);
				comboBoxPostulante.setModel(model);
			}
		}catch (UsuarioNoExisteException e) {
			
		}
		comboBoxPostulante.repaint();
	}
	
	public void cargarOfertas(DtEmpresa empresa) {
		try {
			DtOferta[] ofertas = interfazOferta.obtenerOfertasConfirmadas(empresa.getNickname());
			DefaultComboBoxModel<DtOferta> model = new DefaultComboBoxModel<DtOferta>(ofertas);
			comboBoxOferta.setModel(model);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void limpiarEntrada() {
		this.DescripcionField.setText("");
		this.DepartamentoField.setText("");
		this.CiudadField.setText("");
		this.horarioField_1.setValue(0);
		this.horarioField_2.setValue(0);
		this.horarioField_3.setValue(0);
		this.horarioField_4.setValue(0);
		this.RemuneracionField.setText("");
		this.CVField.setText("");
		this.MotivacionField.setText("");
		this.FechaPost.setDate(null);
	}

	private void registrarPostulacion() {
		DtEmpresa empresa = (DtEmpresa) comboBoxEmpresa.getSelectedItem();
		DtUsuario postulante = (DtUsuario) comboBoxPostulante.getSelectedItem();
		DtOferta oferta = (DtOferta) comboBoxOferta.getSelectedItem();
		String curriculum = CVField.getText();
		String motivacion = MotivacionField.getText();
		
		if (FechaPost.getDate() == null) {
			JOptionPane.showMessageDialog(this, "Debe rellenar los campos obligatorios", "Postulacion a Oferta Laboral", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		int dia = FechaPost.getJCalendar().getDayChooser().getDay();
        int mes = FechaPost.getJCalendar().getMonthChooser().getMonth() + 1; // Sumar 1 para obtener el valor de mes convencional
        int anio = FechaPost.getJCalendar().getYearChooser().getYear();
        
		LocalDate fecha = LocalDate.of(anio, mes, dia);
		
		if (!checkDatos(curriculum, motivacion, empresa, postulante, oferta, fecha)) {
			JOptionPane.showMessageDialog(this, "Debe rellenar los campos obligatorios", "Postulacion a Oferta Laboral", JOptionPane.ERROR_MESSAGE);
			return;
		} 
		else {
			try { 
				interfazUsuario.ingresarDatosPostulacion(curriculum, motivacion, fecha, oferta.getNombre(), postulante.getNickname(),"");
				JOptionPane.showMessageDialog(this, "La Postulacion se ha creado con éxito", "Postulacion a Oferta Laboral", JOptionPane.INFORMATION_MESSAGE);
				limpiarEntrada();
			} catch (FechaInvalidaException | UsuarioNoExisteException | PostulacionYaExisteException   error) {
				JOptionPane.showMessageDialog(this, error.getMessage(), "Postulacion a Oferta Laboral", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	
	private void llenarCampos(DtOferta oferta) {
		if (oferta != null) {
			DescripcionField.setText(oferta.getDescripcion());
			DepartamentoField.setText(oferta.getDepartamento());
			CiudadField.setText(oferta.getCiudad());
			String horario = oferta.getHorario(); 
			String[] partes = horario.split(":"); // Dividir la cadena en partes usando el separador ":"
	
			Integer horario1 = Integer.parseInt(partes[0]); 
			Integer horario2 = Integer.parseInt(partes[1]); 
			Integer horario3 = Integer.parseInt(partes[2]);
			Integer horario4 = Integer.parseInt(partes[3]); 
			
			this.horarioField_1.setValue(horario1);
			this.horarioField_2.setValue(horario2);
			this.horarioField_3.setValue(horario3);
			this.horarioField_4.setValue(horario4);
	
			RemuneracionField.setText("" + oferta.getRemuneracion());
		}
	}
	

	private boolean checkDatos(String curriculum, String motivacion, DtEmpresa empresa, DtUsuario postulante, DtOferta oferta, LocalDate fecha) {
		if (curriculum.isEmpty() || motivacion.isEmpty() || empresa == null || postulante == null || oferta == null || fecha == null){
			return false; 
		}
		return true;
	}
}




















