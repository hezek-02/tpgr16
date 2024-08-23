package presentacion;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import logica.datatypes.DtTipoPub;
import logica.interfaces.IOfertaLab;
import com.toedter.calendar.JDateChooser;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class ConsultaTipoPublicacion extends JInternalFrame {
	@SuppressWarnings("unused")
	private IOfertaLab interfazOL;
	private JTextArea descripcionField;
	private JTextField duracionField;
	private JTextField ExposicionField;
	private JTextField costoBaseField;
	private JLabel lblDescripcion;
	private JLabel lblFechaAlta;
	private JLabel lblDuracion;
	private JLabel lblExposicion;
	private JLabel lblCostoBase;
	private JDateChooser dateChooser;
	private JScrollPane scrollPane;
	private JComboBox<DtTipoPub> comboBoxTipoPub;
	private JLabel lblNombreTpub;
	private JTextField nomTpub;

	public ConsultaTipoPublicacion(IOfertaLab iOfLab) {
		getContentPane().setFont(new Font("Dialog", Font.BOLD, 15));

		interfazOL = iOfLab;

		// Propiedades del internalFrame
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
		setClosable(true);
		setTitle("Consulta de Tipo publicación");
		setBounds(0, 0, 500, 386);

		// Componentes Swing generados por WindowBuilder
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 83, 40, 57, 183, 109 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 65, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0 };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		getContentPane().setLayout(gridBagLayout);

		JLabel lblListaDeTiposPub = new JLabel("Lista de Tipo pub: ");
		lblListaDeTiposPub.setVisible(false);
		
		lblNombreTpub = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNombreTpub = new GridBagConstraints();
		gbc_lblNombreTpub.anchor = GridBagConstraints.EAST;
		gbc_lblNombreTpub.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombreTpub.gridx = 1;
		gbc_lblNombreTpub.gridy = 0;
		getContentPane().add(lblNombreTpub, gbc_lblNombreTpub);
		
		nomTpub = new JTextField();
		nomTpub.setEditable(false);
		nomTpub.setEnabled(false);
		GridBagConstraints gbc_nomTpub = new GridBagConstraints();
		gbc_nomTpub.gridwidth = 2;
		gbc_nomTpub.insets = new Insets(0, 0, 5, 5);
		gbc_nomTpub.fill = GridBagConstraints.HORIZONTAL;
		gbc_nomTpub.gridx = 2;
		gbc_nomTpub.gridy = 0;
		getContentPane().add(nomTpub, gbc_nomTpub);
		nomTpub.setColumns(10);
		GridBagConstraints gbc_lblListaDeTiposPub = new GridBagConstraints();
		gbc_lblListaDeTiposPub.gridwidth = 2;
		gbc_lblListaDeTiposPub.anchor = GridBagConstraints.EAST;
		gbc_lblListaDeTiposPub.insets = new Insets(0, 0, 5, 5);
		gbc_lblListaDeTiposPub.gridx = 0;
		gbc_lblListaDeTiposPub.gridy = 1;
		getContentPane().add(lblListaDeTiposPub, gbc_lblListaDeTiposPub);
		

		
		comboBoxTipoPub = new JComboBox<DtTipoPub>();
		comboBoxTipoPub.setVisible(false);
		comboBoxTipoPub.setEnabled(false);
		comboBoxTipoPub.setEditable(false);
		comboBoxTipoPub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				cargarDatos((DtTipoPub) comboBoxTipoPub.getSelectedItem());
			}
		});
		GridBagConstraints gbc_comboBoxTipPub = new GridBagConstraints();
		gbc_comboBoxTipPub.gridwidth = 2;
		gbc_comboBoxTipPub.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxTipPub.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxTipPub.gridx = 2;
		gbc_comboBoxTipPub.gridy = 1;
		getContentPane().add(comboBoxTipoPub, gbc_comboBoxTipPub);

		lblDescripcion = new JLabel("Descripcion: ");
		GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
		gbc_lblDescripcion.anchor = GridBagConstraints.EAST;
		gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcion.gridx = 1;
		gbc_lblDescripcion.gridy = 2;
		getContentPane().add(lblDescripcion, gbc_lblDescripcion);
		
		scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 2;
		getContentPane().add(scrollPane, gbc_scrollPane);

		descripcionField = new JTextArea();
		descripcionField.setEditable(false);
		descripcionField.setEnabled(false);
		descripcionField.setLineWrap(true);
        descripcionField.setWrapStyleWord(true);       
		scrollPane.setViewportView(descripcionField);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		lblFechaAlta = new JLabel("Fecha Alta : ");
		GridBagConstraints gbc_lblFechaAlta = new GridBagConstraints();
		gbc_lblFechaAlta.anchor = GridBagConstraints.EAST;
		gbc_lblFechaAlta.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaAlta.gridx = 1;
		gbc_lblFechaAlta.gridy = 3;
		getContentPane().add(lblFechaAlta, gbc_lblFechaAlta);
		
		dateChooser = new JDateChooser();
		dateChooser.setEnabled(false);
		GridBagConstraints gbc_dateChooser = new GridBagConstraints();
		gbc_dateChooser.gridwidth = 2;
		gbc_dateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser.fill = GridBagConstraints.BOTH;
		gbc_dateChooser.gridx = 2;
		gbc_dateChooser.gridy = 3;
		getContentPane().add(dateChooser, gbc_dateChooser);

		lblDuracion = new JLabel("duracion: ");
		GridBagConstraints gbc_lblDuracion = new GridBagConstraints();
		gbc_lblDuracion.anchor = GridBagConstraints.EAST;
		gbc_lblDuracion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDuracion.gridx = 1;
		gbc_lblDuracion.gridy = 4;
		getContentPane().add(lblDuracion, gbc_lblDuracion);

		duracionField = new JTextField();
		duracionField.setEditable(false);
		GridBagConstraints gbc_duracionField = new GridBagConstraints();
		gbc_duracionField.gridwidth = 2;
		gbc_duracionField.fill = GridBagConstraints.HORIZONTAL;
		gbc_duracionField.insets = new Insets(0, 0, 5, 5);
		gbc_duracionField.gridx = 2;
		gbc_duracionField.gridy = 4;
		getContentPane().add(duracionField, gbc_duracionField);

		lblExposicion = new JLabel("Exposicion:");
		GridBagConstraints gbc_lblExposicion = new GridBagConstraints();
		gbc_lblExposicion.anchor = GridBagConstraints.EAST;
		gbc_lblExposicion.insets = new Insets(0, 0, 5, 5);
		gbc_lblExposicion.gridx = 1;
		gbc_lblExposicion.gridy = 5;
		getContentPane().add(lblExposicion, gbc_lblExposicion);

		ExposicionField = new JTextField("");
		ExposicionField.setEditable(false);
		GridBagConstraints gbc_ExposicionField = new GridBagConstraints();
		gbc_ExposicionField.gridwidth = 2;
		gbc_ExposicionField.fill = GridBagConstraints.HORIZONTAL;
		gbc_ExposicionField.insets = new Insets(0, 0, 5, 5);
		gbc_ExposicionField.gridx = 2;
		gbc_ExposicionField.gridy = 5;
		getContentPane().add(ExposicionField, gbc_ExposicionField);
		
		lblCostoBase = new JLabel("Costo:");
		GridBagConstraints gbc_lblCostoBase = new GridBagConstraints();
		gbc_lblCostoBase.anchor = GridBagConstraints.EAST;
		gbc_lblCostoBase.insets = new Insets(0, 0, 5, 5);
		gbc_lblCostoBase.gridx = 1;
		gbc_lblCostoBase.gridy = 6;
		getContentPane().add(lblCostoBase, gbc_lblCostoBase);

		costoBaseField = new JTextField();
		costoBaseField.setEditable(false);
		GridBagConstraints gbc_costoBaseField = new GridBagConstraints();
		gbc_costoBaseField.gridwidth = 2;
		gbc_costoBaseField.fill = GridBagConstraints.HORIZONTAL;
		gbc_costoBaseField.insets = new Insets(0, 0, 5, 5);
		gbc_costoBaseField.gridx = 2;
		gbc_costoBaseField.gridy = 6;
		getContentPane().add(costoBaseField, gbc_costoBaseField);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				limpiarEntrada();
				setVisible(false);
			}
		});
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.gridwidth = 2;
		gbc_btnCancelar.insets = new Insets(10, 5, 0, 5);
		gbc_btnCancelar.gridx = 2;
		gbc_btnCancelar.gridy = 8;
		getContentPane().add(btnCancelar, gbc_btnCancelar);
		limpiarEntrada();
	}
	
	public void cargarTipoPub(DtTipoPub tipoPub) {
		comboBoxTipoPub.addItem(tipoPub);
		comboBoxTipoPub.setSelectedItem(tipoPub);
		cargarDatos(tipoPub);
//		DefaultComboBoxModel<DtTipoPub> modelTipoPub;
//		try {
//			modelTipoPub = new DefaultComboBoxModel<DtTipoPub>(interfazOL.obtenerTipoPublicaciones());
//			comboBoxTipoPub.setModel(modelTipoPub);
//
//		} catch (TipoPubNoExisteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}


	public void limpiarEntrada() {
		this.lblCostoBase.setVisible(false);
		this.scrollPane.setVisible(false);
		this.duracionField.setVisible(false);

		this.duracionField.setText("");
		this.ExposicionField.setVisible(false);
		this.ExposicionField.setText("");
		this.costoBaseField.setVisible(false);
		this.costoBaseField.setText("");

		this.descripcionField.setVisible(false);
		this.descripcionField.setText("");

		this.dateChooser.setDate(null);
		this.dateChooser.setVisible(false);
		this.lblFechaAlta.setVisible(false);
		this.lblDescripcion.setVisible(false);
		this.lblDuracion.setVisible(false);
		this.lblExposicion.setVisible(false);
	}

	public void cargarDatos(DtTipoPub tipoPub) {
		if (tipoPub!= null) {
			this.nomTpub.setText(tipoPub.getNombre());
			this.lblCostoBase.setVisible(true);
			this.scrollPane.setVisible(true);
			this.duracionField.setVisible(true);
			this.ExposicionField.setVisible(true);
			this.costoBaseField.setVisible(true);
			this.descripcionField.setVisible(true);
			this.dateChooser.setVisible(true);
			this.lblFechaAlta.setVisible(true);
			this.lblDescripcion.setVisible(true);
			this.lblDuracion.setVisible(true);
			this.lblExposicion.setVisible(true);
			
			int anio = tipoPub.getFechaAlta().getYear();
	        int mes = tipoPub.getFechaAlta().getMonthValue();
	        int dia = tipoPub.getFechaAlta().getDayOfMonth();
		    Calendar calendar1 = Calendar.getInstance();
		    calendar1.set(Calendar.YEAR, anio);
		    calendar1.set(Calendar.MONTH, mes-1); // Mes (0-11)
		    calendar1.set(Calendar.DAY_OF_MONTH, dia);
		    this.dateChooser.setCalendar(calendar1);
		    
			this.descripcionField.setText(tipoPub.getDescripcion());
			this.ExposicionField.setText(String.valueOf(tipoPub.getExposicion()));
			this.costoBaseField.setText(String.valueOf(tipoPub.getCosto()));
			this.duracionField.setText(String.valueOf(tipoPub.getDuracion()));
		}
	}
	

}
