package presentacion;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

import excepciones.FechaInvalidaException;
import excepciones.UsuarioNoExisteException;
import logica.datatypes.DtEmpresa;
import logica.datatypes.DtPostulante;
import logica.datatypes.DtUsuario;
import logica.interfaces.IUsuario;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;

@SuppressWarnings("serial")
public class ModificarUsuario extends JInternalFrame{
	
	IUsuario interfazUsu;
	DtUsuario usu;
	
	private JTextArea descripcionField;
	private JTextField linkWebField;
	private JTextField apellidoField;
	private JTextField nombreField;
	private JTextField paisField;
	private JDateChooser dateChooser;
	private JLabel lblNombre;
	private JLabel lblApellido;
	private JLabel lblDescripcion;
	private JLabel lblLinkweb;
	private JLabel lblFechaNacimiento;
	private JLabel lblPais;
	private JComboBox<DtUsuario> comboBoxUsuarios;
	private JLabel lblClave;
	private JLabel lblUsuario;
	private JScrollPane scrollPane;
	private GridBagLayout gridBagLayout;

	
	public ModificarUsuario(IUsuario interfazUsuario) {
		interfazUsu = interfazUsuario;
		setClosable(true);
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
		setTitle("Modificar Usuario");
		setBounds(0, 0, 490,350);
		
		gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{72, 0, 163, 41};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 65, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};

		getContentPane().setLayout(gridBagLayout);
		
		
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

		comboBoxUsuarios.setSize(new Dimension(32, 0));
		comboBoxUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				limpiarEntrada();
				hallarUsuario(((DtUsuario) comboBoxUsuarios.getSelectedItem()));
			}
		});
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 2;
		gbc_comboBox.fill = GridBagConstraints.BOTH;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 0;
		getContentPane().add(comboBoxUsuarios, gbc_comboBox);
		
		lblUsuario = new JLabel("Usuario:");
		GridBagConstraints gbc_lblUsuario = new GridBagConstraints();
		gbc_lblUsuario.anchor = GridBagConstraints.EAST;
		gbc_lblUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsuario.gridx = 0;
		gbc_lblUsuario.gridy = 1;
		getContentPane().add(lblUsuario, gbc_lblUsuario);
		
		lblClave = new JLabel("");
		GridBagConstraints gbc_lblClave = new GridBagConstraints();
		gbc_lblClave.gridwidth = 2;
		gbc_lblClave.anchor = GridBagConstraints.WEST;
		gbc_lblClave.insets = new Insets(0, 0, 5, 5);
		gbc_lblClave.gridx = 1;
		gbc_lblClave.gridy = 1;
		getContentPane().add(lblClave, gbc_lblClave);
		
		lblNombre = new JLabel("Nombre*: ");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 0;
		gbc_lblNombre.gridy = 2;
		getContentPane().add(lblNombre, gbc_lblNombre);
		
		nombreField = new JTextField();
		nombreField.setColumns(2);
		GridBagConstraints gbc_nombreField = new GridBagConstraints();
		gbc_nombreField.fill = GridBagConstraints.BOTH;
		gbc_nombreField.gridwidth = 2;
		gbc_nombreField.insets = new Insets(0, 0, 5, 5);
		gbc_nombreField.gridx = 1;
		gbc_nombreField.gridy = 2;
		getContentPane().add(nombreField, gbc_nombreField);
		
		lblApellido = new JLabel("Apellido*: ");
		GridBagConstraints gbc_lblApellido = new GridBagConstraints();
		gbc_lblApellido.anchor = GridBagConstraints.EAST;
		gbc_lblApellido.insets = new Insets(0, 0, 5, 5);
		gbc_lblApellido.gridx = 0;
		gbc_lblApellido.gridy = 3;
		getContentPane().add(lblApellido, gbc_lblApellido);
		
		apellidoField = new JTextField();
		GridBagConstraints gbc_apellidoField = new GridBagConstraints();
		gbc_apellidoField.fill = GridBagConstraints.BOTH;
		gbc_apellidoField.gridwidth = 2;
		gbc_apellidoField.insets = new Insets(0, 0, 5, 5);
		gbc_apellidoField.gridx = 1;
		gbc_apellidoField.gridy = 3;
		getContentPane().add(apellidoField, gbc_apellidoField);
		apellidoField.setColumns(2);
		
		lblDescripcion = new JLabel("Descripcion*: ");
		GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
		gbc_lblDescripcion.anchor = GridBagConstraints.EAST;
		gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcion.gridx = 0;
		gbc_lblDescripcion.gridy = 4;
		getContentPane().add(lblDescripcion, gbc_lblDescripcion);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 4;
		getContentPane().add(scrollPane, gbc_scrollPane);
		
		descripcionField = new JTextArea();
		descripcionField.setLineWrap(true);
        descripcionField.setWrapStyleWord(true);        
		scrollPane.setViewportView(descripcionField);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		descripcionField.setColumns(2);
		
		lblLinkweb = new JLabel("Link pág. web: ");
		GridBagConstraints gbc_lblLinkweb = new GridBagConstraints();
		gbc_lblLinkweb.anchor = GridBagConstraints.EAST;
		gbc_lblLinkweb.insets = new Insets(0, 0, 5, 5);
		gbc_lblLinkweb.gridx = 0;
		gbc_lblLinkweb.gridy = 5;
		getContentPane().add(lblLinkweb, gbc_lblLinkweb);
		
		linkWebField = new JTextField();
		GridBagConstraints gbc_linkWebField = new GridBagConstraints();
		gbc_linkWebField.fill = GridBagConstraints.HORIZONTAL;
		gbc_linkWebField.gridwidth = 2;
		gbc_linkWebField.insets = new Insets(0, 0, 5, 5);
		gbc_linkWebField.gridx = 1;
		gbc_linkWebField.gridy = 5;
		getContentPane().add(linkWebField, gbc_linkWebField);
		linkWebField.setColumns(2);
		
		lblFechaNacimiento = new JLabel("Fecha de nac*: ");
		GridBagConstraints gbc_lblFechaNacimiento = new GridBagConstraints();
		gbc_lblFechaNacimiento.anchor = GridBagConstraints.EAST;
		gbc_lblFechaNacimiento.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaNacimiento.gridx = 0;
		gbc_lblFechaNacimiento.gridy = 6;
		getContentPane().add(lblFechaNacimiento, gbc_lblFechaNacimiento);
		
		dateChooser = new JDateChooser();
		GridBagConstraints gbc_LocalDateChooser = new GridBagConstraints();
		gbc_LocalDateChooser.fill = GridBagConstraints.HORIZONTAL;
		gbc_LocalDateChooser.gridwidth = 2;
		gbc_LocalDateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_LocalDateChooser.gridx = 1;
		gbc_LocalDateChooser.gridy = 6;
		getContentPane().add(dateChooser, gbc_LocalDateChooser);
		

		
		lblPais = new JLabel("Nacionalidad*:");
		GridBagConstraints gbc_lblPas = new GridBagConstraints();
		gbc_lblPas.anchor = GridBagConstraints.EAST;
		gbc_lblPas.insets = new Insets(0, 0, 5, 5);
		gbc_lblPas.gridx = 0;
		gbc_lblPas.gridy = 7;
		getContentPane().add(lblPais, gbc_lblPas);
		
		paisField = new JTextField();
		GridBagConstraints gbc_paisField = new GridBagConstraints();
		gbc_paisField.fill = GridBagConstraints.HORIZONTAL;
		gbc_paisField.gridwidth = 2;
		gbc_paisField.insets = new Insets(0, 0, 5, 5);
		gbc_paisField.gridx = 1;
		gbc_paisField.gridy = 7;
		getContentPane().add(paisField, gbc_paisField);
		paisField.setColumns(2);
		
		JButton btnRegistrar = new JButton("Modificar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				modificarUsuario();
			}
		});
		
		GridBagConstraints gbc_btnRegistrar = new GridBagConstraints();
		gbc_btnRegistrar.insets = new Insets(10, 0, 0, 5);
		gbc_btnRegistrar.gridx = 1;
		gbc_btnRegistrar.gridy = 8;
		getContentPane().add(btnRegistrar, gbc_btnRegistrar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				limpiarEntrada();
				setVisible(false);
			}
		});
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.insets = new Insets(10, 5, 0, 5);
		gbc_btnCancelar.gridx = 2;
		gbc_btnCancelar.gridy = 8;
		getContentPane().add(btnCancelar, gbc_btnCancelar);
	
		limpiarEntrada();
		
	}
	//ComboBox
	private void hallarUsuario(DtUsuario usuario) {
		this.gridBagLayout.rowHeights[4]= 0;
		this.usu = usuario;
		if (this.usu!=null) {
			this.nombreField.setVisible(true);
			this.apellidoField.setVisible(true);
			this.lblNombre.setVisible(true);
			this.lblApellido.setVisible(true);

			if (this.usu instanceof DtPostulante) {
				DtPostulante pos = (DtPostulante) usu;
				this.dateChooser.setVisible(true);
				this.paisField.setVisible(true);
				this.lblFechaNacimiento.setVisible(true);
				this.lblPais.setVisible(true);
				this.nombreField.setText(pos.getNombre());
				this.apellidoField.setText(pos.getApellido());
				
				int anio = pos.getFechaNacimiento().getYear();
		        int mes = pos.getFechaNacimiento().getMonthValue();
		        int dia = pos.getFechaNacimiento().getDayOfMonth();
		        
			    Calendar calendar = Calendar.getInstance();
			    calendar.set(Calendar.YEAR, anio);
			    calendar.set(Calendar.MONTH, mes-1); // Mes (0-11)
			    calendar.set(Calendar.DAY_OF_MONTH, dia);
			    this.dateChooser.setCalendar(calendar);
			    
				this.paisField.setText(pos.getNacionalidad());
				
			}else if (usu instanceof DtEmpresa) {
				DtEmpresa emp = (DtEmpresa) usu;
				this.gridBagLayout.rowHeights[4]= 75;
				this.lblDescripcion.setVisible(true);
				this.lblLinkweb.setVisible(true);
				this.scrollPane.setVisible(true);
				this.descripcionField.setVisible(true);
				this.linkWebField.setVisible(true);
				this.nombreField.setText(emp.getNombre());
				this.apellidoField.setText(emp.getApellido());
				this.descripcionField.setText(emp.getDescripcion());
				this.linkWebField.setText(emp.getSitioWeb());
			}
			this.lblClave.setText(usu.getNickname());
		}
			
	}

	public void cargaUsuarios() {
		DefaultComboBoxModel<DtUsuario> model;
		try {
			model = new DefaultComboBoxModel<DtUsuario>(interfazUsu.obtenerUsuarios());
			comboBoxUsuarios.setModel(model);
		}catch (UsuarioNoExisteException e) {
			
		}
	}
	private void modificarUsuario() {
		if (this.usu != null) {
			String nombre = this.nombreField.getText();
			String descripcion = this.descripcionField.getText();
			String linkWeb = this.linkWebField.getText();
	    	String pais = this.paisField.getText();
			String apellido = this.apellidoField.getText();
			String nickname = this.lblClave.getText();
			
			int dia = this.dateChooser.getJCalendar().getDayChooser().getDay();
	        int mes = this.dateChooser.getJCalendar().getMonthChooser().getMonth() + 1; // Sumar 1 para obtener el valor de mes convencional
	        int anio = this.dateChooser.getJCalendar().getYearChooser().getYear();
	        
			LocalDate fechaNac = LocalDate.of(anio, mes, dia);
			if(!checkDatos(nombre, descripcion, pais, apellido, fechaNac)) {
	        	JOptionPane.showMessageDialog(this, "Debe rellenar campos obligatorios", "Modificar Usuario", JOptionPane.ERROR_MESSAGE);
			}else {
				try {
					if (usu instanceof DtPostulante) {
						
							interfazUsu.modificarUsuario(nickname, nombre, apellido, fechaNac, pais,null);
	
						JOptionPane.showMessageDialog(this, "El Usuario se ha modificado con éxito", "Modificar Usuario", JOptionPane.INFORMATION_MESSAGE);
		
					}else if (usu instanceof DtEmpresa) {
						interfazUsu.modificarUsuario(nickname, nombre, apellido, descripcion, linkWeb, null);
						JOptionPane.showMessageDialog(this, "El Usuario se ha modificado con éxito", "Modificar Usuario", JOptionPane.INFORMATION_MESSAGE);
		
					}
					limpiarEntrada();

				} catch (FechaInvalidaException | UsuarioNoExisteException e) {
		        	JOptionPane.showMessageDialog(this, e.getMessage(), "Modificar Usuario", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	
	private boolean checkDatos(String nombre,String descripcion,String pais,String apellido,LocalDate fechNac) {
		if(nombre.isEmpty() || apellido.isEmpty() || (descripcion.isEmpty() &&  (pais.isEmpty() || fechNac==null)))
			return false;
		return true;
	}

	public void limpiarEntrada() {
		this.usu=null;
		this.lblClave.setText("Sin seleccionar");
		this.nombreField.setText("");
		this.apellidoField.setText("");
		this.descripcionField.setText("");
		this.linkWebField.setText("");
		this.gridBagLayout.rowHeights[4]= 0;
		this.dateChooser.setDate(null);
		this.paisField.setText("");
		this.scrollPane.setVisible(false);
		this.descripcionField.setVisible(false);
		this.nombreField.setVisible(false);
		this.apellidoField.setVisible(false);
		this.linkWebField.setVisible(false);
		this.dateChooser.setVisible(false);
		this.paisField.setVisible(false);
		this.lblNombre.setVisible(false);
		this.lblApellido.setVisible(false);
		this.lblDescripcion.setVisible(false);
		this.lblLinkweb.setVisible(false);
		this.lblFechaNacimiento.setVisible(false);
		this.lblPais.setVisible(false);
	}


}
