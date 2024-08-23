package presentacion;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import excepciones.PaqueteNoExisteException;
import excepciones.TipoPubNoExisteException;
import logica.datatypes.DtPaqPub;
import logica.datatypes.DtPaquetePub;
import logica.datatypes.DtTipoPub;
import logica.interfaces.IOfertaLab;
import com.toedter.calendar.JDateChooser;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

@SuppressWarnings("serial")
public class ConsultaPaquetesTipoPub extends JInternalFrame {
	private IOfertaLab interfazOL;
	private JComboBox<DtPaquetePub> comboBoxPaquetes;

	DtPaquetePub paquete;
	private JTextArea descripcionField;
	private JTextField periodoValidezField;
	private JTextField descuentoField;
	private JTextField costoBaseField;
	private JLabel lblNombre;
	private JLabel lblDescripcion;
	private JLabel lblFechaAlta;
	private JLabel lblPeriodoValidez;
	private JLabel lblDescuento;
	private JLabel lblCostoBase;
	private JLabel lblTipoPubs;
	private JDateChooser dateChooser;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JList<DtPaqPub> listaTipoPubs;

	public ConsultaPaquetesTipoPub(IOfertaLab iOfLab, ConsultaTipoPublicacion constp) {
		getContentPane().setFont(new Font("Dialog", Font.BOLD, 15));

		interfazOL = iOfLab;

		// Propiedades del internalFrame
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
		setClosable(true);
		setTitle("Consulta de Paquetes");
		setBounds(0, 0, 520, 425);

		// Componentes Swing generados por WindowBuilder
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 149, 254 };
		gridBagLayout.rowHeights = new int[] { 0, 65, 0, 0, 0, 0, 65, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0 };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		getContentPane().setLayout(gridBagLayout);
		
		comboBoxPaquetes = new JComboBox<DtPaquetePub>();
		comboBoxPaquetes.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent evento) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent evento) {
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent evento) {
				cargarPaquetes();
			}
		});
		
		comboBoxPaquetes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				limpiarEntrada();
				hallarPaquete(((DtPaquetePub) comboBoxPaquetes.getSelectedItem()));
				cargarTipoPub((DtPaquetePub) comboBoxPaquetes.getSelectedItem());
			}
		});

		lblNombre = new JLabel("Lista de Paquetes:");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 0;
		gbc_lblNombre.gridy = 0;
		getContentPane().add(lblNombre, gbc_lblNombre);

		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 0;
		getContentPane().add(comboBoxPaquetes, gbc_comboBox);

		lblDescripcion = new JLabel("Descripcion: ");
		GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
		gbc_lblDescripcion.anchor = GridBagConstraints.EAST;
		gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcion.gridx = 0;
		gbc_lblDescripcion.gridy = 1;
		getContentPane().add(lblDescripcion, gbc_lblDescripcion);
		
		scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 1;
		getContentPane().add(scrollPane, gbc_scrollPane);

		descripcionField = new JTextArea();
		descripcionField.setLineWrap(true);
		descripcionField.setEditable(false);
        descripcionField.setWrapStyleWord(true);       
		scrollPane.setViewportView(descripcionField);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		lblFechaAlta = new JLabel("Fecha Alta: ");
		GridBagConstraints gbc_lblFechaAlta = new GridBagConstraints();
		gbc_lblFechaAlta.anchor = GridBagConstraints.EAST;
		gbc_lblFechaAlta.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaAlta.gridx = 0;
		gbc_lblFechaAlta.gridy = 2;
		getContentPane().add(lblFechaAlta, gbc_lblFechaAlta);
		
		dateChooser = new JDateChooser();
		dateChooser.setEnabled(false);
		GridBagConstraints gbc_dateChooser = new GridBagConstraints();
		gbc_dateChooser.insets = new Insets(0, 0, 5, 0);
		gbc_dateChooser.fill = GridBagConstraints.BOTH;
		gbc_dateChooser.gridx = 1;
		gbc_dateChooser.gridy = 2;
		getContentPane().add(dateChooser, gbc_dateChooser);

		lblPeriodoValidez = new JLabel("Periodo Validez: ");
		GridBagConstraints gbc_lblPeriodoValidez = new GridBagConstraints();
		gbc_lblPeriodoValidez.anchor = GridBagConstraints.EAST;
		gbc_lblPeriodoValidez.insets = new Insets(0, 0, 5, 5);
		gbc_lblPeriodoValidez.gridx = 0;
		gbc_lblPeriodoValidez.gridy = 3;
		getContentPane().add(lblPeriodoValidez, gbc_lblPeriodoValidez);

		periodoValidezField = new JTextField();
		periodoValidezField.setEditable(false);
		GridBagConstraints gbc_periodoValidezField = new GridBagConstraints();
		gbc_periodoValidezField.fill = GridBagConstraints.HORIZONTAL;
		gbc_periodoValidezField.insets = new Insets(0, 0, 5, 0);
		gbc_periodoValidezField.gridx = 1;
		gbc_periodoValidezField.gridy = 3;
		getContentPane().add(periodoValidezField, gbc_periodoValidezField);

		lblDescuento = new JLabel("Descuento:");
		GridBagConstraints gbc_lblDescuento = new GridBagConstraints();
		gbc_lblDescuento.anchor = GridBagConstraints.EAST;
		gbc_lblDescuento.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescuento.gridx = 0;
		gbc_lblDescuento.gridy = 4;
		getContentPane().add(lblDescuento, gbc_lblDescuento);

		descuentoField = new JTextField("");
		descuentoField.setEditable(false);
		GridBagConstraints gbc_descuentoField = new GridBagConstraints();
		gbc_descuentoField.fill = GridBagConstraints.HORIZONTAL;
		gbc_descuentoField.insets = new Insets(0, 0, 5, 0);
		gbc_descuentoField.gridx = 1;
		gbc_descuentoField.gridy = 4;
		getContentPane().add(descuentoField, gbc_descuentoField);

		lblCostoBase = new JLabel("Costo Base:");
		GridBagConstraints gbc_lblCostoBase = new GridBagConstraints();
		gbc_lblCostoBase.anchor = GridBagConstraints.EAST;
		gbc_lblCostoBase.insets = new Insets(0, 0, 5, 5);
		gbc_lblCostoBase.gridx = 0;
		gbc_lblCostoBase.gridy = 5;
		getContentPane().add(lblCostoBase, gbc_lblCostoBase);

		costoBaseField = new JTextField();
		costoBaseField.setEditable(false);
		GridBagConstraints gbc_costoBaseField = new GridBagConstraints();
		gbc_costoBaseField.fill = GridBagConstraints.HORIZONTAL;
		gbc_costoBaseField.insets = new Insets(0, 0, 5, 0);
		gbc_costoBaseField.gridx = 1;
		gbc_costoBaseField.gridy = 5;
		getContentPane().add(costoBaseField, gbc_costoBaseField);

		lblTipoPubs = new JLabel("Tipos Publicacion:");
		GridBagConstraints gbc_lblTipoPubs = new GridBagConstraints();
		gbc_lblTipoPubs.anchor = GridBagConstraints.EAST;
		gbc_lblTipoPubs.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipoPubs.gridx = 0;
		gbc_lblTipoPubs.gridy = 6;
		getContentPane().add(lblTipoPubs, gbc_lblTipoPubs);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				limpiarEntrada();
				setVisible(false);
			}
		});
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setEnabled(false);
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_1.gridx = 1;
		gbc_scrollPane_1.gridy = 6;
		getContentPane().add(scrollPane_1, gbc_scrollPane_1);
				
		listaTipoPubs =  new JList<DtPaqPub>();	
		listaTipoPubs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaTipoPubs.setValueIsAdjusting(true);
		listaTipoPubs.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent evento) {
				DtPaqPub paqPub = (DtPaqPub) listaTipoPubs.getSelectedValue();
				DtTipoPub tipoPub;
				try {
					if(paqPub != null) {
						tipoPub = interfazOL.obtenerTipoPublicacion(paqPub.getNombreTipoPub());
						if (tipoPub !=null) {
							constp.setVisible(true);
							constp.cargarTipoPub(tipoPub);
						}
					}
				} catch (TipoPubNoExisteException error) {
					//e1.printStackTrace();
				}
			}
		});
		scrollPane_1.setViewportView(listaTipoPubs);

		
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.insets = new Insets(10, 5, 0, 0);
		gbc_btnCancelar.gridx = 1;
		gbc_btnCancelar.gridy = 7;
		getContentPane().add(btnCancelar, gbc_btnCancelar);
	}

	public void cargarPaquetes() {
		DefaultComboBoxModel<DtPaquetePub> model;
		try {
			model = new DefaultComboBoxModel<DtPaquetePub>(interfazOL.obtenerPaquetes());
			comboBoxPaquetes.setModel(model);
		} catch (PaqueteNoExisteException e) {

		}
	}
	public void cargarTipoPub(DtPaquetePub paquete) {
		DefaultComboBoxModel<DtPaqPub> modelTipoPub;
		DtPaqPub[] tipoPubs = interfazOL.obtenerPaqPubs(paquete.getNombre());
		if (tipoPubs!=null) {
			this.listaTipoPubs.setVisible(true);
			modelTipoPub = new DefaultComboBoxModel<DtPaqPub>(tipoPubs);
			listaTipoPubs.setModel(modelTipoPub);
		}else {
			this.listaTipoPubs.setVisible(false);
		}

	}

	private void hallarPaquete(DtPaquetePub paquete) {
		if (paquete != null) {
			this.lblTipoPubs.setVisible(true);
			this.scrollPane.setVisible(true);
			this.dateChooser.setVisible(true);
			this.descripcionField.setVisible(true);
			this.dateChooser.setVisible(true);
			this.periodoValidezField.setVisible(true);
			this.descuentoField.setVisible(true);
			this.costoBaseField.setVisible(true);
			this.scrollPane_1.setVisible(true);

			this.lblDescripcion.setVisible(true);
			this.lblFechaAlta.setVisible(true);
			this.lblPeriodoValidez.setVisible(true);
			this.lblDescuento.setVisible(true);
			this.lblCostoBase.setVisible(true);

			this.descripcionField.setText(paquete.getDescripcion());
			this.dateChooser.setEnabled(false); 
			int anio = paquete.getFechaAlta().getYear();
	        int mes = paquete.getFechaAlta().getMonthValue();
	        int dia = paquete.getFechaAlta().getDayOfMonth();
	        
		    Calendar calendar = Calendar.getInstance();
		    calendar.set(Calendar.YEAR, anio);
		    calendar.set(Calendar.MONTH, mes-1); // Mes (0-11)
		    calendar.set(Calendar.DAY_OF_MONTH, dia);
		    this.dateChooser.setCalendar(calendar);
		    
			this.periodoValidezField.setText(String.valueOf(paquete.getPeriodoValidez()));
			this.descuentoField.setText(String.valueOf(paquete.getDescuento())+" %");
			this.costoBaseField.setText(String.valueOf(paquete.getCosto()));
		}
	}

	public  void limpiarEntrada() {
		this.paquete = null;
		this.listaTipoPubs.setVisible(false);
		this.lblTipoPubs.setVisible(false);
		this.scrollPane.setVisible(false);
		this.dateChooser.setVisible(false);
		this.scrollPane_1.setVisible(false);
		this.periodoValidezField.setVisible(false);
		this.descuentoField.setVisible(false);
		this.costoBaseField.setVisible(false);
		this.dateChooser.setDate(null);
		this.lblFechaAlta.setVisible(false);
		this.lblDescripcion.setVisible(false);
		this.lblPeriodoValidez.setVisible(false);
		this.lblDescuento.setVisible(false);
		this.lblCostoBase.setVisible(false);
	}
}
