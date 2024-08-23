package presentacion;

import javax.swing.JInternalFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import com.toedter.calendar.JDateChooser;

import excepciones.FechaInvalidaException;
import excepciones.PaqueteRegistrado;
import excepciones.DescuentoMayorACeroExcepcion;
import excepciones.CostoMayorACeroExcepcion;
import java.time.LocalDate;
import logica.interfaces.IOfertaLab;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class AltaPaqueteTipoPublicacion extends JInternalFrame{
	private IOfertaLab interfazOfertaLab;
	private JTextField FieldNombre;
	private JTextArea FieldDescripcion;
	private JSpinner FieldPeriodo;
	private JTextField FieldDescuento;
	private JDateChooser FehaAlta;
	public AltaPaqueteTipoPublicacion(IOfertaLab interfazOfertaLaboral) {
		interfazOfertaLab = interfazOfertaLaboral;
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
		setClosable(true);
		setBounds(0, 0, 500, 350);
		
		setTitle("Registro Paquete tipo publicacion");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{151, 0, 165, 0};
		gridBagLayout.rowHeights = new int[]{0, 35, 0, 65, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblIngresePaquete = new JLabel("Ingrese los datos,('*' campos obligatorios)");
		GridBagConstraints gbc_lblIngresePaquete = new GridBagConstraints();
		gbc_lblIngresePaquete.gridwidth = 2;
		gbc_lblIngresePaquete.insets = new Insets(0, 0, 5, 5);
		gbc_lblIngresePaquete.gridx = 1;
		gbc_lblIngresePaquete.gridy = 1;
		getContentPane().add(lblIngresePaquete, gbc_lblIngresePaquete);
		
		JLabel lblNombre = new JLabel("Nombre *:");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 0;
		gbc_lblNombre.gridy = 2;
		getContentPane().add(lblNombre, gbc_lblNombre);
		
		FieldNombre = new JTextField();
		GridBagConstraints gbc_FieldNombre = new GridBagConstraints();
		gbc_FieldNombre.gridwidth = 2;
		gbc_FieldNombre.insets = new Insets(0, 0, 5, 0);
		gbc_FieldNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_FieldNombre.gridx = 1;
		gbc_FieldNombre.gridy = 2;
		getContentPane().add(FieldNombre, gbc_FieldNombre);
		FieldNombre.setColumns(2);
		
		JLabel lblDescripcion = new JLabel("Descripcion*:");
		GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
		gbc_lblDescripcion.anchor = GridBagConstraints.EAST;
		gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcion.gridx = 0;
		gbc_lblDescripcion.gridy = 3;
		getContentPane().add(lblDescripcion, gbc_lblDescripcion);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 3;
		getContentPane().add(scrollPane, gbc_scrollPane);
		
		FieldDescripcion = new JTextArea();
		FieldDescripcion.setLineWrap(true);
		FieldDescripcion.setWrapStyleWord(true);        
		scrollPane.setViewportView(FieldDescripcion);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		FieldDescripcion.setColumns(2);
		
		JLabel lblNewLabel = new JLabel("Fecha alta*:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 4;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		FehaAlta = new JDateChooser();
		GridBagConstraints gbc_LocalDateChooserFehaAlta = new GridBagConstraints();
		gbc_LocalDateChooserFehaAlta.gridwidth = 2;
		gbc_LocalDateChooserFehaAlta.insets = new Insets(0, 0, 5, 0);
		gbc_LocalDateChooserFehaAlta.fill = GridBagConstraints.BOTH;
		gbc_LocalDateChooserFehaAlta.gridx = 1;
		gbc_LocalDateChooserFehaAlta.gridy = 4;
		getContentPane().add(FehaAlta, gbc_LocalDateChooserFehaAlta);
		
		JLabel lblPeriodo = new JLabel("Periodo*:");
		GridBagConstraints gbc_lblPeriodo = new GridBagConstraints();
		gbc_lblPeriodo.anchor = GridBagConstraints.EAST;
		gbc_lblPeriodo.insets = new Insets(0, 0, 5, 5);
		gbc_lblPeriodo.gridx = 0;
		gbc_lblPeriodo.gridy = 5;
		getContentPane().add(lblPeriodo, gbc_lblPeriodo);
		
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 1000, 1);

		
		FieldPeriodo = new JSpinner(spinnerModel);
		GridBagConstraints gbc_FieldPeriodo = new GridBagConstraints();
		gbc_FieldPeriodo.insets = new Insets(0, 0, 5, 5);
		gbc_FieldPeriodo.fill = GridBagConstraints.HORIZONTAL;
		gbc_FieldPeriodo.gridx = 1;
		gbc_FieldPeriodo.gridy = 5;
		getContentPane().add(FieldPeriodo, gbc_FieldPeriodo);
		
		JLabel lblDescuento = new JLabel("Descuento*(%):");
		GridBagConstraints gbc_lblDescuento = new GridBagConstraints();
		gbc_lblDescuento.anchor = GridBagConstraints.EAST;
		gbc_lblDescuento.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescuento.gridx = 0;
		gbc_lblDescuento.gridy = 6;
		getContentPane().add(lblDescuento, gbc_lblDescuento);
		
		FieldDescuento = new JTextField();
		GridBagConstraints gbc_FieldDescuento = new GridBagConstraints();
		gbc_FieldDescuento.gridwidth = 2;
		gbc_FieldDescuento.insets = new Insets(0, 0, 5, 0);
		gbc_FieldDescuento.fill = GridBagConstraints.HORIZONTAL;
		gbc_FieldDescuento.gridx = 1;
		gbc_FieldDescuento.gridy = 6;
		getContentPane().add(FieldDescuento, gbc_FieldDescuento);
		FieldDescuento.setColumns(2);
		
		JButton btnAceptar = new JButton("Registrar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				registrarPaqueteTipoPub(); 
			}
		});
		GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
		gbc_btnAceptar.insets = new Insets(0, 0, 0, 5);
		gbc_btnAceptar.gridx = 1;
		gbc_btnAceptar.gridy = 8;
		getContentPane().add(btnAceptar, gbc_btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				limpiarEntrada();
				setVisible(false);
			}
		});
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.gridx = 2;
		gbc_btnCancelar.gridy = 8;
		getContentPane().add(btnCancelar, gbc_btnCancelar);
	}
	
	private void registrarPaqueteTipoPub() {
		String nombre = this.FieldNombre.getText();
		String descripcion = this.FieldDescripcion.getText();
		int periodoValidez = (int) this.FieldPeriodo.getValue();
		String descuento = this.FieldDescuento.getText();
		
		if (FehaAlta.getDate() == null) {
			JOptionPane.showMessageDialog(this, "Debe rellenar los campos obligatorios", "Postulacion a Oferta Laboral", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
        int dia = FehaAlta.getJCalendar().getDayChooser().getDay();
        int mes = FehaAlta.getJCalendar().getMonthChooser().getMonth() + 1; // Sumar 1 para obtener el valor de mes convencional
        int anio = FehaAlta.getJCalendar().getYearChooser().getYear();
		LocalDate fechaAlta = LocalDate.of(anio, mes, dia);

		if (checkDatos(nombre, descripcion, periodoValidez, descuento, fechaAlta)) {//falta fecha
			try {

				Float descuentoFloat = Float.parseFloat(FieldDescuento.getText());
				interfazOfertaLab.ingresarDatosPaquete(nombre, descripcion, null, fechaAlta, periodoValidez, descuentoFloat);
				JOptionPane.showMessageDialog(this, "El Paquete de tipo publicacion se ha creado con éxito", "Registrar Tipo Publicacion", JOptionPane.INFORMATION_MESSAGE);
				limpiarEntrada();
				
			} catch (PaqueteRegistrado | DescuentoMayorACeroExcepcion | CostoMayorACeroExcepcion  | FechaInvalidaException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Registrar Paquete Tipo Publicacion", JOptionPane.ERROR_MESSAGE);
			}
		}

	}
	
	private boolean checkDatos(String nombre, String descripcion, int periodoValidez, String descuento, LocalDate fechaAlta) {
		if (nombre.isEmpty() || descripcion.isEmpty() || periodoValidez <= 0 || descuento.isEmpty() || fechaAlta == null) {
			JOptionPane.showMessageDialog(this, "Debe rellenar campos obligatorios", "Registrar Tipo Publicacion", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		try {
			Float.parseFloat(descuento);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "El descuento debe ser un numero", "Registrar Paquete de tipo Publicacion", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	public void limpiarEntrada() {
		this.FieldNombre.setText("");
		this.FieldDescripcion.setText("");
		this.FieldPeriodo.setValue(1);
		this.FieldDescuento.setText("");
		this.FehaAlta.setDate(null);
	}

}
