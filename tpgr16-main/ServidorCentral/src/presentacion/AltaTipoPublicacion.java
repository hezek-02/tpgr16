package presentacion;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import com.toedter.calendar.JDateChooser;

import excepciones.FechaInvalidaException;
import excepciones.TipoPubliRegistradoException;
import logica.interfaces.IOfertaLab;
import javax.swing.JSpinner;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class AltaTipoPublicacion extends JInternalFrame {
	private IOfertaLab interfazOfertaLab;

	private JTextField nombreField;
	private JTextArea descripcionField;
	private JSpinner duracionField;
	// private JTextField exposicionField;
	private JSpinner Exposicion;
	private JTextField costoField;
	private JDateChooser fech;
	private JLabel lblNombre;
	private JLabel lblDescripcion;
	private JLabel lblDuracion;
	private JLabel lblExposicion;
	private JLabel lblFechaAlta;
	private JLabel lblCosto;
	private JLabel lblIngreseLosDatos;
	private JScrollPane scrollPane;
	private JButton btnCancelar;

	public AltaTipoPublicacion(IOfertaLab iOflab) {
		getContentPane().setFont(new Font("Dialog", Font.BOLD, 15));

		interfazOfertaLab = iOflab;

		// Propiedades del internalFrame
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
		setClosable(true);
		setTitle("Registrar un Tipo de Publicacion de Oferta Laboral ");
		setBounds(0, 0, 500, 350);

		// Componentes Swing generados por WindowBuilder
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 83, 68, 72, 84, 45, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 65, 0, 0, 0, 34, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		lblIngreseLosDatos = new JLabel("Ingrese los datos, ('*' campos obligatorios)");
		lblIngreseLosDatos.setFont(new Font("Dialog", Font.BOLD, 14));
		GridBagConstraints gbc_lblIngreseLosDatos = new GridBagConstraints();
		gbc_lblIngreseLosDatos.gridwidth = 3;
		gbc_lblIngreseLosDatos.insets = new Insets(25, 0, 25, 15);
		gbc_lblIngreseLosDatos.gridx = 1;
		gbc_lblIngreseLosDatos.gridy = 0;
		getContentPane().add(lblIngreseLosDatos, gbc_lblIngreseLosDatos);

		lblNombre = new JLabel("Nombre*: ");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 1;
		getContentPane().add(lblNombre, gbc_lblNombre);

		nombreField = new JTextField();
		GridBagConstraints gbc_nombreField = new GridBagConstraints();
		gbc_nombreField.gridwidth = 2;
		gbc_nombreField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nombreField.insets = new Insets(0, 0, 5, 5);
		gbc_nombreField.gridx = 2;
		gbc_nombreField.gridy = 1;
		getContentPane().add(nombreField, gbc_nombreField);
		nombreField.setColumns(2);

		lblDescripcion = new JLabel("Descripcion*: ");
		GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
		gbc_lblDescripcion.anchor = GridBagConstraints.EAST;
		gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcion.gridx = 1;
		gbc_lblDescripcion.gridy = 2;
		getContentPane().add(lblDescripcion, gbc_lblDescripcion);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 2;
		getContentPane().add(scrollPane, gbc_scrollPane);

		descripcionField = new JTextArea();
		descripcionField.setLineWrap(true);
        descripcionField.setWrapStyleWord(true);       
		scrollPane.setViewportView(descripcionField);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				
		lblFechaAlta = new JLabel("Fecha *: ");
		GridBagConstraints gbc_lblFechaAlta = new GridBagConstraints();
		gbc_lblFechaAlta.anchor = GridBagConstraints.EAST;
		gbc_lblFechaAlta.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaAlta.gridx = 1;
		gbc_lblFechaAlta.gridy = 3;
		getContentPane().add(lblFechaAlta, gbc_lblFechaAlta);

		fech = new JDateChooser();
		GridBagConstraints gbc_fechaAlta = new GridBagConstraints();
		gbc_fechaAlta.gridwidth = 2;
		gbc_fechaAlta.fill = GridBagConstraints.HORIZONTAL;
		gbc_fechaAlta.insets = new Insets(0, 0, 5, 5);
		gbc_fechaAlta.gridx = 2;
		gbc_fechaAlta.gridy = 3;
		getContentPane().add(fech, gbc_fechaAlta);
				
		lblDuracion = new JLabel("Duracion*: ");
		GridBagConstraints gbc_lblDuracion = new GridBagConstraints();
		gbc_lblDuracion.anchor = GridBagConstraints.EAST;
		gbc_lblDuracion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDuracion.gridx = 1;
		gbc_lblDuracion.gridy = 4;
		getContentPane().add(lblDuracion, gbc_lblDuracion);
		
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 1000, 1);

		duracionField = new JSpinner(spinnerModel);
		GridBagConstraints gbc_duracionField = new GridBagConstraints();
		gbc_duracionField.fill = GridBagConstraints.HORIZONTAL;
		gbc_duracionField.insets = new Insets(0, 0, 5, 5);
		gbc_duracionField.gridx = 2;
		gbc_duracionField.gridy = 4;
		getContentPane().add(duracionField, gbc_duracionField);
		
        SpinnerNumberModel spinnerModel2 = new SpinnerNumberModel(1, 1, 30, 1);

		lblExposicion = new JLabel("Exposicion*: ");
		GridBagConstraints gbc_lblExposicion = new GridBagConstraints();
		gbc_lblExposicion.anchor = GridBagConstraints.EAST;
		gbc_lblExposicion.insets = new Insets(0, 0, 5, 5);
		gbc_lblExposicion.gridx = 1;
		gbc_lblExposicion.gridy = 5;
		getContentPane().add(lblExposicion, gbc_lblExposicion);
						
		Exposicion = new JSpinner(spinnerModel2);
		
		GridBagConstraints gbc_Exposicion = new GridBagConstraints();
		gbc_Exposicion.fill = GridBagConstraints.HORIZONTAL;
		gbc_Exposicion.insets = new Insets(0, 0, 5, 5);
		gbc_Exposicion.gridx = 2;
		gbc_Exposicion.gridy = 5;
		getContentPane().add(Exposicion, gbc_Exposicion);

		lblCosto = new JLabel("Costo*: ");
		GridBagConstraints gbc_lblCosto = new GridBagConstraints();
		gbc_lblCosto.anchor = GridBagConstraints.EAST;
		gbc_lblCosto.insets = new Insets(0, 0, 5, 5);
		gbc_lblCosto.gridx = 1;
		gbc_lblCosto.gridy = 6;
		getContentPane().add(lblCosto, gbc_lblCosto);
	
		costoField = new JTextField();
		GridBagConstraints gbc_costoField = new GridBagConstraints();
		gbc_costoField.fill = GridBagConstraints.HORIZONTAL;
		gbc_costoField.insets = new Insets(0, 0, 5, 5);
		gbc_costoField.gridx = 2;
		gbc_costoField.gridy = 6;
		getContentPane().add(costoField, gbc_costoField);
		costoField.setColumns(2);
		
		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				registrarTipoPublicacion();
			}
		});
		
		GridBagConstraints gbc_btnRegistrar = new GridBagConstraints();
		gbc_btnRegistrar.anchor = GridBagConstraints.NORTH;
		gbc_btnRegistrar.insets = new Insets(10, 0, 0, 5);
		gbc_btnRegistrar.gridx = 2;
		gbc_btnRegistrar.gridy = 7;
		getContentPane().add(btnRegistrar, gbc_btnRegistrar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				limpiarEntrada();
				setVisible(false);
			}
		});
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.anchor = GridBagConstraints.SOUTH;
		gbc_btnCancelar.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancelar.gridx = 3;
		gbc_btnCancelar.gridy = 7;
		getContentPane().add(btnCancelar, gbc_btnCancelar);

	}

	private void registrarTipoPublicacion() {
		String nombre = this.nombreField.getText();
		String descripcion = this.descripcionField.getText();
		int duracion = (int) this.duracionField.getValue();
		int exposicion = (int) this.duracionField.getValue(); 
		String costo = this.costoField.getText();
		
		if (fech.getDate() == null) {
			JOptionPane.showMessageDialog(this, "Debe rellenar los campos obligatorios", "Postulacion a Oferta Laboral", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
        int dia = this.fech.getJCalendar().getDayChooser().getDay();
        int mes = this.fech.getJCalendar().getMonthChooser().getMonth() + 1; // Sumar 1 para obtener el valor de mes convencional
        int anio = this.fech.getJCalendar().getYearChooser().getYear();
	    
		LocalDate fechaAlta = LocalDate.of(anio, mes, dia);
		
		if (!checkDatos(nombre, descripcion, duracion, exposicion, costo, fechaAlta)) {
        	//JOptionPane.showMessageDialog(this, "Debe rellenar campos obligatorios", "Registrar Tipo de Publicacion", JOptionPane.ERROR_MESSAGE);
		}else {
		
			try {
				Float costoFloat = Float.parseFloat(costoField.getText());
				interfazOfertaLab.ingresarDatosTipoPublicacion(nombre, descripcion, duracion, exposicion, costoFloat, fechaAlta);
				JOptionPane.showMessageDialog(this, "El Tipo Publicacion se ha creado con éxito", "Registrar Tipo de Publicacion", JOptionPane.INFORMATION_MESSAGE);
				limpiarEntrada();
			} catch (TipoPubliRegistradoException | FechaInvalidaException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Registrar Tipo de Publicacion", JOptionPane.ERROR_MESSAGE);
			}
				
		}
	}

	private boolean checkDatos(String nombre, String descripcion, int duracion, int exposicion, String costo, LocalDate fechAlta) {
		try {
			Float costoFloat = Float.parseFloat(costo);
			if (nombre.isEmpty() || descripcion.isEmpty() || duracion <= 0 || exposicion <= 0|| costo.isEmpty() || fechAlta == null) {
				JOptionPane.showMessageDialog(this, "Debe rellenar campos obligatorios", "Registrar Tipo Publicacion",JOptionPane.ERROR_MESSAGE);
				return false;
			} else if (costoFloat < 0) {
				JOptionPane.showMessageDialog(this, "El costo no puede ser menor a 0", "Registrar Tipo Publicacion", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "El costo debe ser un numero", "Registrar Tipo Publicacion", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	public void limpiarEntrada() {
		this.nombreField.setText("");
		this.descripcionField.setText("");
		this.duracionField.setValue(1);
		this.Exposicion.setValue(1);
		this.costoField.setText("");
		this.fech.setDate(null);
	}

}