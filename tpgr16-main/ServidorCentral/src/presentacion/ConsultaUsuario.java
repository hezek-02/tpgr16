package presentacion;
import javax.swing.JInternalFrame;
import java.awt.GridBagLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;


import excepciones.UsuarioNoExisteException;
import logica.datatypes.DtEmpresa;
import logica.datatypes.DtOferta;
import logica.datatypes.DtPostulante;
import logica.datatypes.DtUsuario;
import logica.interfaces.IOfertaLab;
import logica.interfaces.IUsuario;

import com.toedter.calendar.JDateChooser;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

@SuppressWarnings("serial")
public class ConsultaUsuario extends JInternalFrame{
	private IUsuario interfazUsuario;
	private IOfertaLab interfazOfertaLaboral;
	
	DtUsuario usu;
	//Campos del formulario
	private JTextArea descripcionField;
	private JTextField linkWebField;
	private JTextField apellidoField;
	private JTextField nombreField;
	private JTextField paisField;
	private JLabel lblNombre;
	private JLabel lblApellido;
	private JLabel lblDescripcion;
	private JLabel lblLinkweb;
	private JLabel lblFechaNacimiento;
	private JLabel lblPais;
	private JSeparator separator;
	private JLabel lblUsuario;
	private JDateChooser dateChooser;
	private JTextField lblClave;
	private JComboBox<DtUsuario> comboBoxUsuarios;
	private JScrollPane scrollPane;
	private GridBagLayout gridBagLayout;
	private JList<DtOferta> ofertasList;
	private JLabel lblOfertas;
	private JScrollPane scrollPane_1;
	private JLabel lblCorreo;
	private JTextField textFieldCorreo;
	
	public ConsultaUsuario(IUsuario interfazUsu, IOfertaLab iOfertaLaboral, ConsultaOfertaLaboral consultaOfertaLaboralInternalFrame) {
		setClosable(true);
		interfazUsuario = interfazUsu;
		interfazOfertaLaboral = iOfertaLaboral;
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
		setTitle("Consulta de Usuario");
		setBounds(0, 0, 493,427);
		
		
		gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{177, 0, 59, 170, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 70, 0, 0, 0, 65, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblListaDeUsuarios = new JLabel("Lista de Usuarios: ");
		GridBagConstraints gbc_lblListaDeUsuarios = new GridBagConstraints();
		gbc_lblListaDeUsuarios.gridwidth = 2;
		gbc_lblListaDeUsuarios.anchor = GridBagConstraints.EAST;
		gbc_lblListaDeUsuarios.insets = new Insets(0, 0, 5, 5);
		gbc_lblListaDeUsuarios.gridx = 0;
		gbc_lblListaDeUsuarios.gridy = 0;
		getContentPane().add(lblListaDeUsuarios, gbc_lblListaDeUsuarios);
	
		comboBoxUsuarios = new JComboBox<DtUsuario>();

		comboBoxUsuarios.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent evento) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent evento) {
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent evento) {
				cargaUsuarios();
			}
		});

		comboBoxUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				DtUsuario usuario = (DtUsuario) comboBoxUsuarios.getSelectedItem();
				mostrarUsuarioSeleccionado(usuario);
				mostrarOfertasDeUsuario(usuario);
			}
		});
		
		GridBagConstraints gbc_comboBoxUsuarios = new GridBagConstraints();
		gbc_comboBoxUsuarios.gridwidth = 2;
		gbc_comboBoxUsuarios.fill = GridBagConstraints.BOTH;
		gbc_comboBoxUsuarios.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxUsuarios.gridx = 2;
		gbc_comboBoxUsuarios.gridy = 0;
		getContentPane().add(comboBoxUsuarios, gbc_comboBoxUsuarios);
		
		lblUsuario = new JLabel("Usuario:");
		GridBagConstraints gbc_lblUsuario = new GridBagConstraints();
		gbc_lblUsuario.anchor = GridBagConstraints.EAST;
		gbc_lblUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsuario.gridx = 0;
		gbc_lblUsuario.gridy = 1;
		getContentPane().add(lblUsuario, gbc_lblUsuario);
		
		lblClave = new JTextField("");
		lblClave.setEditable(false);
		lblClave.setEnabled(false);
		GridBagConstraints gbc_lblClave = new GridBagConstraints();
		gbc_lblClave.fill = GridBagConstraints.BOTH;
		gbc_lblClave.gridwidth = 3;
		gbc_lblClave.insets = new Insets(0, 0, 5, 5);
		gbc_lblClave.gridx = 1;
		gbc_lblClave.gridy = 1;
		getContentPane().add(lblClave, gbc_lblClave);
		
		separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.insets = new Insets(0, 80, 5, 0);
		gbc_separator.gridx = 4;
		gbc_separator.gridy = 2;
		getContentPane().add(separator, gbc_separator);
		
		lblCorreo = new JLabel("Correo*:");
		GridBagConstraints gbc_lblCorreo = new GridBagConstraints();
		gbc_lblCorreo.anchor = GridBagConstraints.EAST;
		gbc_lblCorreo.insets = new Insets(0, 0, 5, 5);
		gbc_lblCorreo.gridx = 0;
		gbc_lblCorreo.gridy = 3;
		getContentPane().add(lblCorreo, gbc_lblCorreo);
		
		textFieldCorreo = new JTextField();
		textFieldCorreo.setEditable(false);
		GridBagConstraints gbc_textFieldCorreo = new GridBagConstraints();
		gbc_textFieldCorreo.gridwidth = 3;
		gbc_textFieldCorreo.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldCorreo.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldCorreo.gridx = 1;
		gbc_textFieldCorreo.gridy = 3;
		getContentPane().add(textFieldCorreo, gbc_textFieldCorreo);
		textFieldCorreo.setColumns(10);
		
		lblNombre = new JLabel("Nombre*: ");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 0;
		gbc_lblNombre.gridy = 4;
		getContentPane().add(lblNombre, gbc_lblNombre);
		
		nombreField = new JTextField();
		nombreField.setEditable(false);
		GridBagConstraints gbc_nombreField = new GridBagConstraints();
		gbc_nombreField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nombreField.gridwidth = 3;
		gbc_nombreField.insets = new Insets(0, 0, 5, 5);
		gbc_nombreField.gridx = 1;
		gbc_nombreField.gridy = 4;
		getContentPane().add(nombreField, gbc_nombreField);
		
		lblApellido = new JLabel("Apellido*: ");
		GridBagConstraints gbc_lblApellido = new GridBagConstraints();
		gbc_lblApellido.anchor = GridBagConstraints.EAST;
		gbc_lblApellido.insets = new Insets(0, 0, 5, 5);
		gbc_lblApellido.gridx = 0;
		gbc_lblApellido.gridy = 5;
		getContentPane().add(lblApellido, gbc_lblApellido);
		
		apellidoField = new JTextField();
		apellidoField.setEditable(false);
		GridBagConstraints gbc_apellidoField = new GridBagConstraints();
		gbc_apellidoField.fill = GridBagConstraints.HORIZONTAL;
		gbc_apellidoField.gridwidth = 3;
		gbc_apellidoField.insets = new Insets(0, 0, 5, 5);
		gbc_apellidoField.gridx = 1;
		gbc_apellidoField.gridy = 5;
		getContentPane().add(apellidoField, gbc_apellidoField);
		
		lblDescripcion = new JLabel("Descripcion*: ");
		GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
		gbc_lblDescripcion.anchor = GridBagConstraints.EAST;
		gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcion.gridx = 0;
		gbc_lblDescripcion.gridy = 6;
		getContentPane().add(lblDescripcion, gbc_lblDescripcion);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 6;
		getContentPane().add(scrollPane, gbc_scrollPane);
		
		descripcionField = new JTextArea();
		descripcionField.setEnabled(false);
		descripcionField.setLineWrap(true);
        descripcionField.setWrapStyleWord(true);       
		scrollPane.setViewportView(descripcionField);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		descripcionField.setEditable(false);
		
		lblLinkweb = new JLabel("Link pág. web: ");
		GridBagConstraints gbc_lblLinkweb = new GridBagConstraints();
		gbc_lblLinkweb.anchor = GridBagConstraints.EAST;
		gbc_lblLinkweb.insets = new Insets(0, 0, 5, 5);
		gbc_lblLinkweb.gridx = 0;
		gbc_lblLinkweb.gridy = 7;
		getContentPane().add(lblLinkweb, gbc_lblLinkweb);
		
		linkWebField = new JTextField();
		linkWebField.setEditable(false);
		GridBagConstraints gbc_linkWebField = new GridBagConstraints();
		gbc_linkWebField.fill = GridBagConstraints.HORIZONTAL;
		gbc_linkWebField.gridwidth = 3;
		gbc_linkWebField.insets = new Insets(0, 0, 5, 5);
		gbc_linkWebField.gridx = 1;
		gbc_linkWebField.gridy = 7;
		getContentPane().add(linkWebField, gbc_linkWebField);
		
		lblFechaNacimiento = new JLabel("Fecha de nac*: ");
		GridBagConstraints gbc_lblFechaNacimiento = new GridBagConstraints();
		gbc_lblFechaNacimiento.anchor = GridBagConstraints.EAST;
		gbc_lblFechaNacimiento.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaNacimiento.gridx = 0;
		gbc_lblFechaNacimiento.gridy = 8;
		getContentPane().add(lblFechaNacimiento, gbc_lblFechaNacimiento);
		
		dateChooser = new JDateChooser();
		dateChooser.setEnabled(false);
		GridBagConstraints gbc_dateChooser = new GridBagConstraints();
		gbc_dateChooser.gridwidth = 3;
		gbc_dateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser.fill = GridBagConstraints.BOTH;
		gbc_dateChooser.gridx = 1;
		gbc_dateChooser.gridy = 8;
		getContentPane().add(dateChooser, gbc_dateChooser);
		

	
		lblPais = new JLabel("Nacionalidad*:");
		GridBagConstraints gbc_lblPas = new GridBagConstraints();
		gbc_lblPas.anchor = GridBagConstraints.EAST;
		gbc_lblPas.insets = new Insets(0, 0, 5, 5);
		gbc_lblPas.gridx = 0;
		gbc_lblPas.gridy = 9;
		getContentPane().add(lblPais, gbc_lblPas);
		
		paisField = new JTextField();
		paisField.setEditable(false);
		GridBagConstraints gbc_paisField = new GridBagConstraints();
		gbc_paisField.fill = GridBagConstraints.HORIZONTAL;
		gbc_paisField.gridwidth = 3;
		gbc_paisField.insets = new Insets(0, 0, 5, 5);
		gbc_paisField.gridx = 1;
		gbc_paisField.gridy = 9;
		getContentPane().add(paisField, gbc_paisField);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				limpiarEntrada();
				setVisible(false);
			}
		});
		
		lblOfertas = new JLabel("Ofertas:");
		GridBagConstraints gbc_lblOfertas = new GridBagConstraints();
		gbc_lblOfertas.anchor = GridBagConstraints.EAST;
		gbc_lblOfertas.insets = new Insets(0, 0, 5, 5);
		gbc_lblOfertas.gridx = 0;
		gbc_lblOfertas.gridy = 10;
		getContentPane().add(lblOfertas, gbc_lblOfertas);
		
		scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridwidth = 3;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_1.gridx = 1;
		gbc_scrollPane_1.gridy = 10;
		getContentPane().add(scrollPane_1, gbc_scrollPane_1);
		
		ofertasList = new JList<DtOferta>();
		ofertasList.setValueIsAdjusting(true);
		ofertasList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent evento) {				
				DtOferta oferta = (DtOferta) ofertasList.getSelectedValue();
				if (oferta != null) {
					DtUsuario usuario = (DtUsuario) comboBoxUsuarios.getSelectedItem();
					consultaOfertaLaboralInternalFrame.setVisible(true);
					consultaOfertaLaboralInternalFrame.cargarDatosOferta(oferta, (DtUsuario)usuario);
				}
			}
		});
		scrollPane_1.setViewportView(ofertasList);

		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.gridwidth = 3;
		gbc_btnCancelar.insets = new Insets(10, 5, 0, 5);
		gbc_btnCancelar.gridx = 1;
		gbc_btnCancelar.gridy = 11;
		getContentPane().add(btnCancelar, gbc_btnCancelar);
	
	}

	private void mostrarUsuarioSeleccionado(DtUsuario usuario) {
		DtUsuario usu = usuario;
		if (usu!=null) {
			if (usu instanceof DtPostulante) {
				DtPostulante pos = (DtPostulante) usu;
				this.gridBagLayout.rowHeights[6]=0;
				this.paisField.setVisible(true);
				this.lblFechaNacimiento.setVisible(true);
				this.dateChooser.setVisible(true);
				this.lblPais.setVisible(true);
				
				this.textFieldCorreo.setText(pos.getCorreo());
				this.nombreField.setText(pos.getNombre());
				this.apellidoField.setText(pos.getApellido());
				this.paisField.setText(pos.getNacionalidad());


				int anio = pos.getFechaNacimiento().getYear();
		        int mes = pos.getFechaNacimiento().getMonthValue();
		        int dia = pos.getFechaNacimiento().getDayOfMonth();
		        
			    Calendar calendar = Calendar.getInstance();
			    calendar.set(Calendar.YEAR, anio);
			    calendar.set(Calendar.MONTH, mes-1); // Mes (0-11)
			    calendar.set(Calendar.DAY_OF_MONTH, dia);
			    this.dateChooser.setCalendar(calendar);
			    
				this.lblDescripcion.setVisible(false);
				this.descripcionField.setVisible(false);
				this.lblLinkweb.setVisible(false);
				this.linkWebField.setVisible(false);
				this.scrollPane.setVisible(false);
				
			}else if (usu instanceof DtEmpresa) {
				DtEmpresa emp = (DtEmpresa) usu;
				this.lblDescripcion.setVisible(true);
				this.descripcionField.setVisible(true);
				this.lblLinkweb.setVisible(true);
				this.linkWebField.setVisible(true);
				this.scrollPane.setVisible(true);
				
				this.gridBagLayout.rowHeights[6]=70;
				this.textFieldCorreo.setText(emp.getCorreo());
				this.nombreField.setText(emp.getNombre());
				this.apellidoField.setText(emp.getApellido());
				this.descripcionField.setText(emp.getDescripcion());
				this.linkWebField.setText(emp.getSitioWeb());
				
				this.lblPais.setVisible(false);
				this.paisField.setVisible(false);
				this.lblFechaNacimiento.setVisible(false);
				this.dateChooser.setVisible(false);
			}
			this.lblClave.setText(usu.getNickname());
		}
	}
	
	public void mostrarOfertasDeUsuario(DtUsuario usuario)  {
		try {
			if (usuario !=null) {
				DtOferta[] ofertas = interfazOfertaLaboral.obtenerOfertas(usuario.getNickname());
				if (ofertas == null) {
					this.scrollPane_1.setVisible(false);
					this.lblOfertas.setVisible(false);
					return;
				}
				this.lblOfertas.setVisible(true);
				this.scrollPane_1.setVisible(true);
				DefaultListModel<DtOferta> ofertasListModel = new DefaultListModel<>();
				for (DtOferta oferta : ofertas) {
					ofertasListModel.addElement(oferta);
				}
				this.ofertasList.setModel(ofertasListModel);
				this.ofertasList.repaint();
				this.ofertasList.setVisible(true);
				this.lblOfertas.setVisible(true);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cargaUsuarios() {
		DefaultComboBoxModel<DtUsuario> model;
		try {
			model = new DefaultComboBoxModel<DtUsuario>(interfazUsuario.obtenerUsuarios());
			comboBoxUsuarios.setModel(model);
		}catch (UsuarioNoExisteException e) {
			
		}
		comboBoxUsuarios.repaint();
	}

	public void limpiarEntrada() {
		this.textFieldCorreo.setText("");
		this.nombreField.setText("");
		this.apellidoField.setText("");
		this.descripcionField.setText("");
		this.lblClave.setText("");
		this.dateChooser.setDate(null);
		DefaultListModel<DtOferta> modeloVacio = new DefaultListModel<>();
		this.ofertasList.setModel(modeloVacio);
		this.gridBagLayout.rowHeights[6]=0;
		this.lblPais.setVisible(false);
		this.paisField.setVisible(false);
		this.linkWebField.setVisible(false);
		this.dateChooser.setDate(null);
		this.lblDescripcion.setVisible(false);
		this.lblLinkweb.setVisible(false);
		this.lblFechaNacimiento.setVisible(false);
		this.scrollPane.setVisible(false);
		this.descripcionField.setVisible(false);
		this.dateChooser.setVisible(false);
	}
}
//aca




