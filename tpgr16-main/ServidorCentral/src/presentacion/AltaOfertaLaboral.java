package presentacion;

import excepciones.CostoMayorACeroExcepcion;
import excepciones.FechaInvalidaException;
import excepciones.OfertaLaboralYaExisteException;
import excepciones.UsuarioNoExisteException;
import logica.datatypes.DtEmpresa;
import java.time.LocalDate;
import logica.datatypes.DtKeyWord;
import logica.datatypes.DtTipoPub;
import logica.interfaces.IOfertaLab;
import logica.interfaces.IUsuario;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import javax.swing.JScrollPane;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Cursor;
import javax.swing.JTextArea;
import javax.swing.JSpinner;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;

@SuppressWarnings("serial")
public class AltaOfertaLaboral extends JInternalFrame {
	
	private IOfertaLab interfazOfertaLab;
	private IUsuario interfazUsuario;

	private JFormattedTextField remuneracionField;
	private JTextField ciudadField;
	private JTextField departamentoField;
	private JSpinner horarioField_4;
	private JTextField nombreField;
	private JDateChooser dateChooser;
	private JLabel lblHorario;
	private JLabel lblCiudad;
	private JLabel lblDepartamento;
	private JLabel lblFechaDeAlta;
	private JLabel lblIngreseLosDatos;
	private JLabel lblKeywords;
	private JSeparator separator;
	private JCheckBox checkbox;
	private JComboBox<DtEmpresa> comboBoxEmpresas;
	private JComboBox<DtTipoPub> comboBoxTiposDePublicacion;
	private JLabel lblTipoDePublicac;
	private JPanel listKeys;
	private JScrollPane scrollPane;
	private JLabel lblDescripcion;
	private JTextArea descripcionField;
	private JScrollPane scrollPane_1;
	private JSpinner horarioField_1;
	private JSpinner horarioField_2;
	private JSpinner horarioField_3;
	private JLabel entre;
	private JLabel entre_1;
	private JLabel entre_2;
	private int horario0, horario1, horario2, horario3;

	public AltaOfertaLaboral(IOfertaLab iOfertaLab, IUsuario iUsuario) {
		setToolTipText("");
		setForeground(Color.WHITE);
		getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		getContentPane().setFont(new Font("Dialog", Font.BOLD, 15));
		
	    interfazOfertaLab = iOfertaLab;
		interfazUsuario = iUsuario;
		
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
		setClosable(true);
		setTitle("Registrar Oferta Laboral");
		setBounds(0, 0, 575, 450);
		
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{5, 118, 55, 0, 55, 55, 55, 0, 55, -72, 76}; // adjusted widths for better alignment
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 65, 0, 0, 0, 0, 0, 65, 37};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};  // adjusted for horizontal stretch of middle columns
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        getContentPane().setLayout(gridBagLayout);
		
		lblIngreseLosDatos = new JLabel("Ingrese los datos,('*' campos obligatorios)");
		lblIngreseLosDatos.setFont(new Font("Dialog", Font.BOLD, 14));
		GridBagConstraints gbc_lblIngreseLosDatos = new GridBagConstraints();
		gbc_lblIngreseLosDatos.fill = GridBagConstraints.VERTICAL;
		gbc_lblIngreseLosDatos.gridwidth = 9;
		gbc_lblIngreseLosDatos.insets = new Insets(25, 0, 5, 15);
		gbc_lblIngreseLosDatos.gridx = 1;
		gbc_lblIngreseLosDatos.gridy = 0;
		getContentPane().add(lblIngreseLosDatos, gbc_lblIngreseLosDatos);
		
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

        JLabel lblEmpresa = new JLabel("Empresa*: ");
        lblEmpresa.setHorizontalAlignment(SwingConstants.RIGHT); // Align right
        GridBagConstraints gbc_lblEmpresa = new GridBagConstraints();
        gbc_lblEmpresa.insets = new Insets(0, 0, 5, 5);
        gbc_lblEmpresa.anchor = GridBagConstraints.EAST;
        gbc_lblEmpresa.gridx = 1;
        gbc_lblEmpresa.gridy = 1;
        getContentPane().add(lblEmpresa, gbc_lblEmpresa);

		GridBagConstraints gbc_keyWordsSelected = new GridBagConstraints();
		gbc_keyWordsSelected.gridwidth = 8;
		gbc_keyWordsSelected.insets = new Insets(0, 0, 5, 5);
		gbc_keyWordsSelected.fill = GridBagConstraints.HORIZONTAL;
		gbc_keyWordsSelected.gridx = 2;
		gbc_keyWordsSelected.gridy = 1;
		getContentPane().add(comboBoxEmpresas, gbc_keyWordsSelected);
		
		lblTipoDePublicac = new JLabel("Tipo de publicación*: ");
		lblTipoDePublicac.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblTipoDePublicac = new GridBagConstraints();
		gbc_lblTipoDePublicac.gridwidth = 2;
		gbc_lblTipoDePublicac.anchor = GridBagConstraints.EAST;
		gbc_lblTipoDePublicac.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipoDePublicac.gridx = 0;
		gbc_lblTipoDePublicac.gridy = 2;
		getContentPane().add(lblTipoDePublicac, gbc_lblTipoDePublicac);


		comboBoxTiposDePublicacion = new JComboBox<DtTipoPub>();
		comboBoxTiposDePublicacion.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent evento) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent evento) {
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent evento) {
				cargarTiposDePublicacion();
			}
		});

		GridBagConstraints gbc_comboBoxTiposDePublicacion = new GridBagConstraints();
		gbc_comboBoxTiposDePublicacion.gridwidth = 8;
		gbc_comboBoxTiposDePublicacion.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxTiposDePublicacion.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxTiposDePublicacion.gridx = 2;
		gbc_comboBoxTiposDePublicacion.gridy = 2;
		getContentPane().add(comboBoxTiposDePublicacion, gbc_comboBoxTiposDePublicacion);
		
		JLabel lblNombre = new JLabel("Nombre*: ");
		lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 3;
		getContentPane().add(lblNombre, gbc_lblNombre);
		
		nombreField = new JTextField();
		nombreField.setColumns(2);
		GridBagConstraints gbc_nombreField = new GridBagConstraints();
		gbc_nombreField.weightx = 0.1;
		gbc_nombreField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nombreField.gridwidth = 8;
		gbc_nombreField.insets = new Insets(0, 0, 5, 5);
		gbc_nombreField.gridx = 2;
		gbc_nombreField.gridy = 3;
		getContentPane().add(nombreField, gbc_nombreField);
		
		lblDescripcion = new JLabel("Descripción*: ");
		GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
		gbc_lblDescripcion.anchor = GridBagConstraints.EAST;
		gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcion.gridx = 1;
		gbc_lblDescripcion.gridy = 4;
		getContentPane().add(lblDescripcion, gbc_lblDescripcion);
		
		scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridwidth = 8;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_1.gridx = 2;
		gbc_scrollPane_1.gridy = 4;
		getContentPane().add(scrollPane_1, gbc_scrollPane_1);
		
		descripcionField = new JTextArea();
		descripcionField.setLineWrap(true);
        descripcionField.setWrapStyleWord(true);       
		scrollPane_1.setViewportView(descripcionField);
		scrollPane_1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		lblHorario = new JLabel("Horario(24Hs)*: ");
		GridBagConstraints gbc_lblHorario = new GridBagConstraints();
		gbc_lblHorario.anchor = GridBagConstraints.EAST;
		gbc_lblHorario.insets = new Insets(0, 0, 5, 5);
		gbc_lblHorario.gridx = 1;
		gbc_lblHorario.gridy = 5;
		getContentPane().add(lblHorario, gbc_lblHorario);
		
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, 23, 1);

		horarioField_1 = new JSpinner(spinnerModel);
		GridBagConstraints gbc_horarioField_1 = new GridBagConstraints();
		gbc_horarioField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_horarioField_1.insets = new Insets(0, 0, 5, 5);
		gbc_horarioField_1.gridx = 2;
		gbc_horarioField_1.gridy = 5;
		getContentPane().add(horarioField_1, gbc_horarioField_1);
		
		entre_1 = new JLabel(":");
		GridBagConstraints gbc_entre_1 = new GridBagConstraints();
		gbc_entre_1.insets = new Insets(0, 0, 5, 5);
		gbc_entre_1.gridx = 3;
		gbc_entre_1.gridy = 5;
		getContentPane().add(entre_1, gbc_entre_1);
        SpinnerNumberModel spinnerModel2 = new SpinnerNumberModel(0, 0, 59, 1);

		horarioField_2 = new JSpinner(spinnerModel2);
		GridBagConstraints gbc_horarioField_2 = new GridBagConstraints();
		gbc_horarioField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_horarioField_2.insets = new Insets(0, 0, 5, 5);
		gbc_horarioField_2.gridx = 4;
		gbc_horarioField_2.gridy = 5;
		getContentPane().add(horarioField_2, gbc_horarioField_2);
		
		entre = new JLabel("-");
		GridBagConstraints gbc_entre = new GridBagConstraints();
		gbc_entre.insets = new Insets(0, 0, 5, 5);
		gbc_entre.gridx = 5;
		gbc_entre.gridy = 5;
		getContentPane().add(entre, gbc_entre);
        SpinnerNumberModel spinnerModel3 = new SpinnerNumberModel(0, 0, 23, 1);

		horarioField_3 = new JSpinner(spinnerModel3);
		GridBagConstraints gbc_horarioField_3 = new GridBagConstraints();
		gbc_horarioField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_horarioField_3.insets = new Insets(0, 0, 5, 5);
		gbc_horarioField_3.gridx = 6;
		gbc_horarioField_3.gridy = 5;
		getContentPane().add(horarioField_3, gbc_horarioField_3);
		
		entre_2 = new JLabel(":");
		GridBagConstraints gbc_entre_2 = new GridBagConstraints();
		gbc_entre_2.insets = new Insets(0, 0, 5, 5);
		gbc_entre_2.gridx = 7;
		gbc_entre_2.gridy = 5;
		getContentPane().add(entre_2, gbc_entre_2);
		
        SpinnerNumberModel spinnerModel4 = new SpinnerNumberModel(0, 0, 59, 1);

		horarioField_4 = new JSpinner(spinnerModel4);
		GridBagConstraints gbc_horarioField_4 = new GridBagConstraints();
		gbc_horarioField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_horarioField_4.gridwidth = 2;
		gbc_horarioField_4.insets = new Insets(0, 0, 5, 5);
		gbc_horarioField_4.gridx = 8;
		gbc_horarioField_4.gridy = 5;
		getContentPane().add(horarioField_4, gbc_horarioField_4);
		
		separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.insets = new Insets(0, 75, 5, 5);
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 7;
		getContentPane().add(separator, gbc_separator);
		
		JLabel lblRemun = new JLabel("Remuneración*: ");
		GridBagConstraints gbc_lblRemun = new GridBagConstraints();
		gbc_lblRemun.anchor = GridBagConstraints.EAST;
		gbc_lblRemun.insets = new Insets(0, 0, 5, 5);
		gbc_lblRemun.gridx = 1;
		gbc_lblRemun.gridy = 6;
		getContentPane().add(lblRemun, gbc_lblRemun);
		
		remuneracionField = new JFormattedTextField();
		GridBagConstraints gbc_remuneracionField = new GridBagConstraints();
		gbc_remuneracionField.fill = GridBagConstraints.HORIZONTAL;
		gbc_remuneracionField.gridwidth = 8;
		gbc_remuneracionField.insets = new Insets(0, 0, 5, 5);
		gbc_remuneracionField.gridx = 2;
		gbc_remuneracionField.gridy = 6;
		remuneracionField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("#0.00"))));
		getContentPane().add(remuneracionField, gbc_remuneracionField);
		remuneracionField.setColumns(2);
		
		lblCiudad = new JLabel("Ciudad*: ");
		GridBagConstraints gbc_lblCiudad = new GridBagConstraints();
		gbc_lblCiudad.anchor = GridBagConstraints.EAST;
		gbc_lblCiudad.insets = new Insets(0, 0, 5, 5);
		gbc_lblCiudad.gridx = 1;
		gbc_lblCiudad.gridy = 7;
		getContentPane().add(lblCiudad, gbc_lblCiudad);
		
		ciudadField = new JTextField();
		GridBagConstraints gbc_ciudadField = new GridBagConstraints();
		gbc_ciudadField.fill = GridBagConstraints.HORIZONTAL;
		gbc_ciudadField.gridwidth = 8;
		gbc_ciudadField.insets = new Insets(0, 0, 5, 5);
		gbc_ciudadField.gridx = 2;
		gbc_ciudadField.gridy = 7;
		getContentPane().add(ciudadField, gbc_ciudadField);
		ciudadField.setColumns(2);
		
		lblDepartamento = new JLabel("Departamento*: ");
		GridBagConstraints gbc_lblDepartamento = new GridBagConstraints();
		gbc_lblDepartamento.anchor = GridBagConstraints.EAST;
		gbc_lblDepartamento.insets = new Insets(0, 0, 5, 5);
		gbc_lblDepartamento.gridx = 1;
		gbc_lblDepartamento.gridy = 8;
		getContentPane().add(lblDepartamento, gbc_lblDepartamento);
		
		departamentoField = new JTextField();
		GridBagConstraints gbc_departamentoField = new GridBagConstraints();
		gbc_departamentoField.fill = GridBagConstraints.HORIZONTAL;
		gbc_departamentoField.gridwidth = 8;
		gbc_departamentoField.insets = new Insets(0, 0, 5, 5);
		gbc_departamentoField.gridx = 2;
		gbc_departamentoField.gridy = 8;
		getContentPane().add(departamentoField, gbc_departamentoField);
		departamentoField.setColumns(2);
		
		lblFechaDeAlta = new JLabel("Fecha de alta*: ");
		GridBagConstraints gbc_lblFechaDeAlta = new GridBagConstraints();
		gbc_lblFechaDeAlta.anchor = GridBagConstraints.EAST;
		gbc_lblFechaDeAlta.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaDeAlta.gridx = 1;
		gbc_lblFechaDeAlta.gridy = 9;
		getContentPane().add(lblFechaDeAlta, gbc_lblFechaDeAlta);
		
		dateChooser = new JDateChooser();
		GridBagConstraints gbc_LocalDateChooser = new GridBagConstraints();
		gbc_LocalDateChooser.fill = GridBagConstraints.HORIZONTAL;
		gbc_LocalDateChooser.gridwidth = 8;
		gbc_LocalDateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_LocalDateChooser.gridx = 2;
		gbc_LocalDateChooser.gridy = 9;
		getContentPane().add(dateChooser, gbc_LocalDateChooser);
		

		lblKeywords = new JLabel("Keywords:");
		GridBagConstraints gbc_lblKeywords = new GridBagConstraints();
		gbc_lblKeywords.anchor = GridBagConstraints.EAST;
		gbc_lblKeywords.insets = new Insets(0, 0, 5, 5);
		gbc_lblKeywords.gridx = 1;
		gbc_lblKeywords.gridy = 10;
		getContentPane().add(lblKeywords, gbc_lblKeywords);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 8;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 10;
		getContentPane().add(scrollPane, gbc_scrollPane);
		
		listKeys = new JPanel();
		listKeys.setBackground(new Color(255, 255, 255));
		scrollPane.setViewportView(listKeys);
		listKeys.setLayout(new BoxLayout(listKeys, BoxLayout.Y_AXIS));

				
		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				registrarOfertaLaboral();
			}
		});
		
		GridBagConstraints gbc_btnRegistrar = new GridBagConstraints();
		gbc_btnRegistrar.gridwidth = 3;
		gbc_btnRegistrar.insets = new Insets(10, 0, 0, 5);
		gbc_btnRegistrar.gridx = 2;
		gbc_btnRegistrar.gridy = 11;
		getContentPane().add(btnRegistrar, gbc_btnRegistrar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				limpiarEntrada();
				setVisible(false);
			}
		});
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.gridwidth = 5;
		gbc_btnCancelar.insets = new Insets(10, 5, 0, 5);
		gbc_btnCancelar.gridx = 5;
		gbc_btnCancelar.gridy = 11;
		getContentPane().add(btnCancelar, gbc_btnCancelar);
	

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
	


	public void cargarTiposDePublicacion() {
		try {
			DtTipoPub[] tipos = interfazOfertaLab.obtenerTipoPublicaciones();
			DefaultComboBoxModel<DtTipoPub> model = new DefaultComboBoxModel<DtTipoPub>(tipos);
			comboBoxTiposDePublicacion.setModel(model);
		} catch (Exception e) {
		}
	}
	
	public void cargarKeyWord() {
		try {
			DtKeyWord[] keysW = interfazOfertaLab.obtenerKeyWords();
			for (DtKeyWord dataKeys : keysW) {
				checkbox = new JCheckBox(dataKeys.getNombreKey());
				listKeys.add(checkbox);
				checkbox.setBackground(Color.white);
				checkbox.setFocusPainted(false);
			}
		} catch (Exception e) {
		}
	}


	private void registrarOfertaLaboral() {
		DtEmpresa empresa = (DtEmpresa) comboBoxEmpresas.getSelectedItem();
		DtTipoPub tipo = (DtTipoPub) comboBoxTiposDePublicacion.getSelectedItem();
		String nombre = this.nombreField.getText();
		horario0 = (int) this.horarioField_1.getValue();
		horario1 = (int) this.horarioField_2.getValue();
		horario2 = (int) this.horarioField_3.getValue();
		horario3 = (int) this.horarioField_4.getValue();
		String descripcion = this.descripcionField.getText();
		Float remuneracion=null;
		
		String horario = horario0 + ":" + horario1 + ":" + horario2 + ":" + horario3;
		if (remuneracionField.getValue() != null) {
			remuneracion = ((Number) remuneracionField.getValue()).floatValue();
		}
			
        String ciudad = this.ciudadField.getText();
        String departamento = this.departamentoField.getText();
		if (dateChooser.getDate() == null) {
			JOptionPane.showMessageDialog(this, "Debe rellenar los campos obligatorios", "Postulacion a Oferta Laboral", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
        int dia = dateChooser.getJCalendar().getDayChooser().getDay();
        int mes = dateChooser.getJCalendar().getMonthChooser().getMonth() + 1; // Sumar 1 para obtener el valor de mes convencional
        int anio = dateChooser.getJCalendar().getYearChooser().getYear();
		LocalDate fechaAlta = LocalDate.of(anio, mes, dia);
		
		//Obtener las keyWord
		String[] keywords = obtenerKeysSelected();
		
			
		if (!checkDatos(nombre, descripcion, horario, remuneracion, ciudad, departamento, fechaAlta)) {
        	JOptionPane.showMessageDialog(this, "Debe rellenar campos obligatorios", "Registrar Oferta Laboral", JOptionPane.ERROR_MESSAGE);
        	return;
		}else {
	        try {
		        interfazOfertaLab.ingresarDatosOfertaLaboral(nombre, descripcion, null, horario, remuneracion, ciudad, departamento, fechaAlta, keywords, empresa.getNickname(), tipo.getNombre());
		        if (horario0==horario1 &&horario2==horario3 && horario2==0)
					JOptionPane.showMessageDialog(this, "El rango horario en la alta, fue de 24 hrs", "Registrar Oferta Laboral", JOptionPane.WARNING_MESSAGE);
		        JOptionPane.showMessageDialog(this, "La Oferta Laboral se ha creado con éxito", "Registrar Oferta Laboral", JOptionPane.INFORMATION_MESSAGE);
		        limpiarEntrada(); 

		    }catch (OfertaLaboralYaExisteException | FechaInvalidaException | UsuarioNoExisteException | CostoMayorACeroExcepcion error) {
				JOptionPane.showMessageDialog(this, error.getMessage(), "Registrar Oferta Laboral", JOptionPane.ERROR_MESSAGE);
		    }
		}
	}
	
	private boolean checkDatos(String nombre, String descripcion, String horario, Float remuneracion, String ciudad, String departamento, LocalDate fechaDeAlta) {
		if (nombre.isEmpty() || horario.isEmpty() || departamento.isEmpty() || remuneracion==null || ciudad.isEmpty() || descripcion.isEmpty() || fechaDeAlta == null)
			return false;
		return true;
	}
	
	private String[] obtenerKeysSelected() {
		String[] keywords=null; 
 		Component[] keys = listKeys.getComponents();
 		int index=0;
		for (Component keySelected : keys) {//Define el largo del arreglo
		    if (keySelected instanceof JCheckBox) {
		    	checkbox = (JCheckBox) keySelected;
		        if (checkbox.isSelected()) {
		        	index++;
		        }
		    }
		}
		keywords = new String[index];
		index=0;
		for (Component keySelected : keys) { //añade las keys al arreglo
		    if (keySelected instanceof JCheckBox) {
		        JCheckBox checkBox = (JCheckBox) keySelected;
		        if (checkBox.isSelected()) {
		            keywords[index] = checkBox.getText(); 
		            index++;
		        }
		    }
		}
		return keywords;
	}
	public void limpiarEntrada() {
		this.descripcionField.setText("");
		this.nombreField.setText("");
		this.horarioField_4.setValue(0);
		this.horarioField_3.setValue(0);
		this.horarioField_2.setValue(0);
		this.horarioField_1.setValue(0);
		this.remuneracionField.setValue(null);
		this.ciudadField.setText("");
		this.departamentoField.setText("");
		this.dateChooser.setDate(null);
		Component[] keys = listKeys.getComponents();
		for (Component keySelected : keys) {//Define el largo del arreglo
		    if (keySelected instanceof JCheckBox) {
		        checkbox = (JCheckBox) keySelected;
		        if (checkbox.isSelected()) {
		        	checkbox.setSelected(false);
		        }
		    }
		}
		
	}

}
