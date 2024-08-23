package presentacion;

import java.awt.Font;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import excepciones.PaqueteNoExisteException;
import excepciones.TipoPubNoExisteException;
import excepciones.TipoPubRegistradoEnPaqueteException;
import logica.interfaces.IOfertaLab;
import logica.datatypes.DtPaquetePub;
import logica.datatypes.DtTipoPub;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;

@SuppressWarnings("serial")
public class AgregarTipoPubAPaquete extends JInternalFrame {
	private IOfertaLab interfazOL;
	private JComboBox<DtPaquetePub> comboBoxPaquetes;
	private JComboBox<DtTipoPub> comboBoxTipoPub;

	private JLabel lblCantidad;
	private JSpinner cantidadField;
	private JLabel lblPaquete;
	private JLabel lblTipoPub;
	private JLabel lblIngreseLosDatos;

	public AgregarTipoPubAPaquete(IOfertaLab iOflab) {
		getContentPane().setFont(new Font("Dialog", Font.BOLD, 15));

		interfazOL = iOflab;

		// Propiedades del internalFrame
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
		setClosable(true);
		setTitle("Agregar Tipo Publicacion de Oferta Laboral a Paquete");
		setBounds(0, 0, 485, 253);

		// Componentes Swing generados por WindowBuilder
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 54, 85, 0, 23, 0, 40, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 61, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		lblIngreseLosDatos = new JLabel("Ingrese los datos, ('*' campos obligatorios)");
		lblIngreseLosDatos.setFont(new Font("Dialog", Font.BOLD, 14));
		GridBagConstraints gbc_lblIngreseLosDatos = new GridBagConstraints();
		gbc_lblIngreseLosDatos.anchor = GridBagConstraints.WEST;
		gbc_lblIngreseLosDatos.gridwidth = 6;
		gbc_lblIngreseLosDatos.insets = new Insets(25, 0, 25, 15);
		gbc_lblIngreseLosDatos.gridx = 1;
		gbc_lblIngreseLosDatos.gridy = 0;
		getContentPane().add(lblIngreseLosDatos, gbc_lblIngreseLosDatos);

		lblPaquete = new JLabel("Paquete*: ");
		GridBagConstraints gbc_lblPaquete = new GridBagConstraints();
		gbc_lblPaquete.gridwidth = 2;
		gbc_lblPaquete.anchor = GridBagConstraints.EAST;
		gbc_lblPaquete.insets = new Insets(0, 0, 5, 5);
		gbc_lblPaquete.gridx = 1;
		gbc_lblPaquete.gridy = 1;
		getContentPane().add(lblPaquete, gbc_lblPaquete);

		lblTipoPub = new JLabel("Tipo de Publicacion *: ");
		GridBagConstraints gbc_lblTipoPub = new GridBagConstraints();
		gbc_lblTipoPub.gridwidth = 2;
		gbc_lblTipoPub.anchor = GridBagConstraints.EAST;
		gbc_lblTipoPub.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipoPub.gridx = 1;
		gbc_lblTipoPub.gridy = 2;
		getContentPane().add(lblTipoPub, gbc_lblTipoPub);

		lblCantidad = new JLabel("Cantidad*: ");
		lblCantidad.setToolTipText("del tipo Publicacion a añadir");
		GridBagConstraints gbc_lblCantidad = new GridBagConstraints();
		gbc_lblCantidad.gridwidth = 2;
		gbc_lblCantidad.anchor = GridBagConstraints.EAST;
		gbc_lblCantidad.insets = new Insets(0, 0, 5, 5);
		gbc_lblCantidad.gridx = 1;
		gbc_lblCantidad.gridy = 3;
		getContentPane().add(lblCantidad, gbc_lblCantidad);
		
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 300, 1);
		cantidadField = new JSpinner(spinnerModel);
		cantidadField.setToolTipText("del tipo Publicacion a añadir");
		GridBagConstraints gbc_cantidadField = new GridBagConstraints();
		gbc_cantidadField.gridwidth = 2;
		gbc_cantidadField.fill = GridBagConstraints.HORIZONTAL;
		gbc_cantidadField.insets = new Insets(0, 0, 5, 5);
		gbc_cantidadField.gridx = 3;
		gbc_cantidadField.gridy = 3;
		getContentPane().add(cantidadField, gbc_cantidadField);

		comboBoxPaquetes = new JComboBox<DtPaquetePub>();
		comboBoxPaquetes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				DtPaquetePub paquete = (DtPaquetePub) comboBoxPaquetes.getSelectedItem();
				if (paquete != null) {
					cargarTipoPub(paquete);
				}
			}
		});
		comboBoxPaquetes.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent evento) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent evento) {
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent evento) {
				cargarPaquetes();
			}
		});

		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridwidth = 3;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.gridx = 3;
		gbc_comboBox.gridy = 1;
		getContentPane().add(comboBoxPaquetes, gbc_comboBox);

		comboBoxTipoPub = new JComboBox<DtTipoPub>();

		GridBagConstraints gbc_comboBox2 = new GridBagConstraints();
		gbc_comboBox2.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox2.gridwidth = 3;
		gbc_comboBox2.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox2.gridx = 3;
		gbc_comboBox2.gridy = 2;
		getContentPane().add(comboBoxTipoPub, gbc_comboBox2);
										
		JButton btnRegistrar = new JButton("Agregar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				agregarTipoPubAPaquete();
			}
		});
		
		GridBagConstraints gbc_btnRegistrar = new GridBagConstraints();
		gbc_btnRegistrar.gridwidth = 2;
		gbc_btnRegistrar.insets = new Insets(10, 0, 5, 5);
		gbc_btnRegistrar.gridx = 2;
		gbc_btnRegistrar.gridy = 4;
		getContentPane().add(btnRegistrar, gbc_btnRegistrar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				limpiarEntrada();
				setVisible(false);
			}
		});
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.gridwidth = 2;
		gbc_btnCancelar.insets = new Insets(10, 5, 5, 5);
		gbc_btnCancelar.gridx = 4;
		gbc_btnCancelar.gridy = 4;
		getContentPane().add(btnCancelar, gbc_btnCancelar);
	}

	// ComboBox
	public void cargarPaquetes() {
		DefaultComboBoxModel<DtPaquetePub> model;
		try {
			model = new DefaultComboBoxModel<DtPaquetePub>(interfazOL.obtenerPaquetesNoComprados());
			comboBoxPaquetes.setModel(model);
		} catch (PaqueteNoExisteException error) {
			error.printStackTrace();

		}
	}

	public void cargarTipoPub(DtPaquetePub paquete) {
		DefaultComboBoxModel<DtTipoPub> model;
		try {
			model = new DefaultComboBoxModel<DtTipoPub>(interfazOL.obtenerTipoPublicacionesQueNoEstanEnPaquete(paquete.getNombre()));
			comboBoxTipoPub.setModel(model);
		} catch (TipoPubNoExisteException | PaqueteNoExisteException error) {
			error.printStackTrace();
		}
	}

	public void limpiarEntrada() {
		this.comboBoxPaquetes.setSelectedItem(null);
		this.comboBoxTipoPub.setSelectedItem(null);
		this.cantidadField.setValue(1);

	}

	private void agregarTipoPubAPaquete() {
		DtPaquetePub paquete = (DtPaquetePub) comboBoxPaquetes.getSelectedItem();
		DtTipoPub tipoPub = (DtTipoPub) comboBoxTipoPub.getSelectedItem();
		int cantidad = (int) this.cantidadField.getValue();
		
		if (checkDatos(paquete, tipoPub, cantidad)) {
			try {
				interfazOL.agregarTipoPubAPaquete(cantidad, tipoPub.getNombre(), paquete.getNombre());
				JOptionPane.showMessageDialog(this, "El Tipo Publicacion de Oferta Laboral se a agregado con éxito al Paquete", "Agregar Tipo Publicacion de Oferta Laboral a Paquete", JOptionPane.INFORMATION_MESSAGE);
				limpiarEntrada();

			} catch (TipoPubRegistradoEnPaqueteException  e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Agregar Tipo Publicacion de Oferta Laboral a Paquete", JOptionPane.ERROR_MESSAGE);
			} 
		
		}
		
		
	}
	
	private boolean checkDatos(DtPaquetePub paquete, DtTipoPub tipoPublicacion, int cantidad) {
		if (paquete == null || tipoPublicacion == null || cantidad < 0) {
			JOptionPane.showMessageDialog(this, "Debe rellenar campos obligatorios", "Agregar Tipo Publicacion de Oferta Laboral a Paquete", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
}
