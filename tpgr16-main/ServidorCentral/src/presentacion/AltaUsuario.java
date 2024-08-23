package presentacion;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.time.LocalDate;
import excepciones.FechaInvalidaException;
import excepciones.UsuarioRegistradoException;
import logica.interfaces.IUsuario;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;


@SuppressWarnings("serial")//se debe definir un nro serie a clases extendidas de JFrame, java lo hace auto pero se define la warning
public class AltaUsuario extends JInternalFrame {
	
	private IUsuario interfazUsuario;
	//Campos del formulario
	private JTextField nicknameField;
	private JTextField correoField;
	private JTextArea descripcionField;
	private JTextField linkWebField;
	private JTextField apellidoField;
	private JTextField nombreField;
	private JTextField paisField;
	private JDateChooser  dateChooser;
	private JLabel lblApellido;
	private JLabel lblDescripcion;
	private JLabel lblLinkweb;
	private JLabel lblFechaNacimiento;
	private String altaDe;
	private JLabel lblIngreseLosDatos;
	private JLabel lblPais;
	private JScrollPane scrollPane;
	private GridBagLayout gridBagLayout;
	private JComboBox<String> comboBox;
	private JLabel lblTipoDeAlta;
	private JLabel lblPassword_;
	private JLabel lblConfirmPassword;
	private JPasswordField passwordField;
	private JPasswordField passwordConfirmField;
	public AltaUsuario(IUsuario iUsu) {
		getContentPane().setFont(new Font("Dialog", Font.BOLD, 15));
		
		interfazUsuario = iUsu;
		
		// Propiedades del internalFrame
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
		setClosable(true);
		setTitle("Registrar un Usuario");
		setBounds(0, 0, 557,451);
		
		// Componentes Swing generados por WindowBuilder
		gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{5, 16, 170, 128, 96, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 65, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		lblIngreseLosDatos = new JLabel("Ingrese los datos,('*' campos obligatorios)");
		lblIngreseLosDatos.setFont(new Font("Dialog", Font.BOLD, 14));
		GridBagConstraints gbc_lblIngreseLosDatos = new GridBagConstraints();
		gbc_lblIngreseLosDatos.anchor = GridBagConstraints.EAST;
		gbc_lblIngreseLosDatos.gridwidth = 3;
		gbc_lblIngreseLosDatos.insets = new Insets(25, 0, 25, 15);
		gbc_lblIngreseLosDatos.gridx = 1;
		gbc_lblIngreseLosDatos.gridy = 0;
		getContentPane().add(lblIngreseLosDatos, gbc_lblIngreseLosDatos);
		
		comboBox = new JComboBox<String>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				altaDe = (String) comboBox.getSelectedItem();
				actualizarForm();
			}
		});
		
		lblTipoDeAlta = new JLabel("Tipo de alta:");
		GridBagConstraints gbc_lblTipoDeAlta = new GridBagConstraints();
		gbc_lblTipoDeAlta.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipoDeAlta.anchor = GridBagConstraints.EAST;
		gbc_lblTipoDeAlta.gridx = 1;
		gbc_lblTipoDeAlta.gridy = 1;
		getContentPane().add(lblTipoDeAlta, gbc_lblTipoDeAlta);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 2;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 1;
		getContentPane().add(comboBox, gbc_comboBox);
		
		JLabel lblIdNickname = new JLabel("Nickname*: ");
		GridBagConstraints gbc_lblIdNickname = new GridBagConstraints();
		gbc_lblIdNickname.anchor = GridBagConstraints.EAST;
		gbc_lblIdNickname.insets = new Insets(0, 0, 5, 5);
		gbc_lblIdNickname.gridx = 1;
		gbc_lblIdNickname.gridy = 2;
		getContentPane().add(lblIdNickname, gbc_lblIdNickname);
		
		nicknameField = new JTextField();
		GridBagConstraints gbc_nicknameField = new GridBagConstraints();
		gbc_nicknameField.gridwidth = 2;
		gbc_nicknameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nicknameField.insets = new Insets(0, 0, 5, 5);
		gbc_nicknameField.gridx = 2;
		gbc_nicknameField.gridy = 2;
		getContentPane().add(nicknameField, gbc_nicknameField);
		nicknameField.setColumns(2);
		
		JLabel lblNombre = new JLabel("Nombre*: ");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 3;
		getContentPane().add(lblNombre, gbc_lblNombre);
		
		nombreField = new JTextField();
		nombreField.setColumns(2);
		GridBagConstraints gbc_nombreField = new GridBagConstraints();
		gbc_nombreField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nombreField.gridwidth = 2;
		gbc_nombreField.insets = new Insets(0, 0, 5, 5);
		gbc_nombreField.gridx = 2;
		gbc_nombreField.gridy = 3;
		getContentPane().add(nombreField, gbc_nombreField);
		
		lblPassword_ = new JLabel("Contraseña*: ");
		GridBagConstraints gbc_lblPassword_ = new GridBagConstraints();
		gbc_lblPassword_.anchor = GridBagConstraints.EAST;
		gbc_lblPassword_.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword_.gridx = 1;
		gbc_lblPassword_.gridy = 4;
		getContentPane().add(lblPassword_, gbc_lblPassword_);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(2);
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.gridwidth = 2;
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 2;
		gbc_passwordField.gridy = 4;
		getContentPane().add(passwordField, gbc_passwordField);
		
		lblConfirmPassword = new JLabel(" Confirmar contraseña*: ");
		GridBagConstraints gbc_lblConfirmPassword = new GridBagConstraints();
		gbc_lblConfirmPassword.anchor = GridBagConstraints.EAST;
		gbc_lblConfirmPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblConfirmPassword.gridx = 1;
		gbc_lblConfirmPassword.gridy = 5;
		getContentPane().add(lblConfirmPassword, gbc_lblConfirmPassword);
		
		passwordConfirmField = new JPasswordField();
		passwordConfirmField.setColumns(2);
		GridBagConstraints gbc_passwordConfirmField = new GridBagConstraints();
		gbc_passwordConfirmField.gridwidth = 2;
		gbc_passwordConfirmField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordConfirmField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordConfirmField.gridx = 2;
		gbc_passwordConfirmField.gridy = 5;
		getContentPane().add(passwordConfirmField, gbc_passwordConfirmField);
		
		lblApellido = new JLabel("Apellido*: ");
		GridBagConstraints gbc_lblApellido = new GridBagConstraints();
		gbc_lblApellido.anchor = GridBagConstraints.EAST;
		gbc_lblApellido.insets = new Insets(0, 0, 5, 5);
		gbc_lblApellido.gridx = 1;
		gbc_lblApellido.gridy = 6;
		getContentPane().add(lblApellido, gbc_lblApellido);
		
		apellidoField = new JTextField();
		GridBagConstraints gbc_apellidoField = new GridBagConstraints();
		gbc_apellidoField.fill = GridBagConstraints.HORIZONTAL;
		gbc_apellidoField.gridwidth = 2;
		gbc_apellidoField.insets = new Insets(0, 0, 5, 5);
		gbc_apellidoField.gridx = 2;
		gbc_apellidoField.gridy = 6;
		getContentPane().add(apellidoField, gbc_apellidoField);
		apellidoField.setColumns(2);
		
		JLabel lblCorreo = new JLabel("Correo*: ");
		GridBagConstraints gbc_lblCorreo = new GridBagConstraints();
		gbc_lblCorreo.anchor = GridBagConstraints.EAST;
		gbc_lblCorreo.insets = new Insets(0, 0, 5, 5);
		gbc_lblCorreo.gridx = 1;
		gbc_lblCorreo.gridy = 7;
		getContentPane().add(lblCorreo, gbc_lblCorreo);
		
		correoField = new JTextField();
		GridBagConstraints gbc_correoField = new GridBagConstraints();
		gbc_correoField.fill = GridBagConstraints.HORIZONTAL;
		gbc_correoField.gridwidth = 2;
		gbc_correoField.insets = new Insets(0, 0, 5, 5);
		gbc_correoField.gridx = 2;
		gbc_correoField.gridy = 7;
		getContentPane().add(correoField, gbc_correoField);
		correoField.setColumns(2);
		
		lblDescripcion = new JLabel("Descripcion*: ");
		GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
		gbc_lblDescripcion.anchor = GridBagConstraints.EAST;
		gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcion.gridx = 1;
		gbc_lblDescripcion.gridy = 8;
		getContentPane().add(lblDescripcion, gbc_lblDescripcion);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 8;
		getContentPane().add(scrollPane, gbc_scrollPane);
		
		descripcionField = new JTextArea();
		descripcionField.setLineWrap(true);
		descripcionField.setWrapStyleWord(true);
		scrollPane.setViewportView(descripcionField);
		descripcionField.setColumns(2);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		lblLinkweb = new JLabel("Link pág. web: ");
		GridBagConstraints gbc_lblLinkweb = new GridBagConstraints();
		gbc_lblLinkweb.anchor = GridBagConstraints.EAST;
		gbc_lblLinkweb.insets = new Insets(0, 0, 5, 5);
		gbc_lblLinkweb.gridx = 1;
		gbc_lblLinkweb.gridy = 9;
		getContentPane().add(lblLinkweb, gbc_lblLinkweb);
		
		linkWebField = new JTextField();
		GridBagConstraints gbc_linkWebField = new GridBagConstraints();
		gbc_linkWebField.fill = GridBagConstraints.HORIZONTAL;
		gbc_linkWebField.gridwidth = 2;
		gbc_linkWebField.insets = new Insets(0, 0, 5, 5);
		gbc_linkWebField.gridx = 2;
		gbc_linkWebField.gridy = 9;
		getContentPane().add(linkWebField, gbc_linkWebField);
		linkWebField.setColumns(2);
		
		lblFechaNacimiento = new JLabel("Fecha de nac*: ");
		GridBagConstraints gbc_lblFechaNacimiento = new GridBagConstraints();
		gbc_lblFechaNacimiento.anchor = GridBagConstraints.EAST;
		gbc_lblFechaNacimiento.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaNacimiento.gridx = 1;
		gbc_lblFechaNacimiento.gridy = 10;
		getContentPane().add(lblFechaNacimiento, gbc_lblFechaNacimiento);
		
		dateChooser = new JDateChooser();
		GridBagConstraints gbc_LocalDateChooser = new GridBagConstraints();
		gbc_LocalDateChooser.fill = GridBagConstraints.HORIZONTAL;
		gbc_LocalDateChooser.gridwidth = 2;
		gbc_LocalDateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_LocalDateChooser.gridx = 2;
		gbc_LocalDateChooser.gridy = 10;
		getContentPane().add(dateChooser, gbc_LocalDateChooser);
		

		
		lblPais = new JLabel("Nacionalidad*:");
		GridBagConstraints gbc_lblPas = new GridBagConstraints();
		gbc_lblPas.anchor = GridBagConstraints.EAST;
		gbc_lblPas.insets = new Insets(0, 0, 5, 5);
		gbc_lblPas.gridx = 1;
		gbc_lblPas.gridy = 11;
		getContentPane().add(lblPais, gbc_lblPas);
		
		paisField = new JTextField();
		GridBagConstraints gbc_paisField = new GridBagConstraints();
		gbc_paisField.fill = GridBagConstraints.HORIZONTAL;
		gbc_paisField.gridwidth = 2;
		gbc_paisField.insets = new Insets(0, 0, 5, 5);
		gbc_paisField.gridx = 2;
		gbc_paisField.gridy = 11;
		getContentPane().add(paisField, gbc_paisField);
		paisField.setColumns(2);
		
		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				registrarUsuario();
			}
		});
		
		GridBagConstraints gbc_btnRegistrar = new GridBagConstraints();
		gbc_btnRegistrar.insets = new Insets(10, 0, 0, 5);
		gbc_btnRegistrar.gridx = 2;
		gbc_btnRegistrar.gridy = 12;
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
		gbc_btnCancelar.gridx = 3;
		gbc_btnCancelar.gridy = 12;
		getContentPane().add(btnCancelar, gbc_btnCancelar);
	

		
	}
	public void altaCombo() {
		comboBox.addItem("Postulante");
		comboBox.addItem("Empresa");
	}
	
	private void registrarUsuario() {
		String nickname = this.nicknameField.getText();
		String correo = this.correoField.getText();
		String nombre = this.nombreField.getText();
		String descripcion = this.descripcionField.getText();
		String linkWeb = this.linkWebField.getText();
    	String pais = this.paisField.getText();
		String apellido = this.apellidoField.getText();
		String password = new String(this.passwordField.getPassword());
		String passwordConfirmation = new String(this.passwordConfirmField.getPassword());

		int dia = this.dateChooser.getJCalendar().getDayChooser().getDay();
        int mes = this.dateChooser.getJCalendar().getMonthChooser().getMonth() + 1; // Sumar 1 para obtener el valor de mes convencional
        int anio = this.dateChooser.getJCalendar().getYearChooser().getYear();
        
		LocalDate fechaNac = LocalDate.of(anio, mes, dia);        

		if (!checkDatos(nickname, correo, nombre, descripcion, pais, apellido, fechaNac, password, passwordConfirmation)) {
        	JOptionPane.showMessageDialog(this, "Debe rellenar campos obligatorios", "Registrar Usuario", JOptionPane.ERROR_MESSAGE);
        	return;
		}
		else if (!password.contentEquals(passwordConfirmation)) {
        	JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden", "Registrar Usuario", JOptionPane.ERROR_MESSAGE);
        	return;
		}
		else if(!correo.contains(String.valueOf('@'))) {
        	JOptionPane.showMessageDialog(this, "El correo debe ser válido", "Registrar Usuario", JOptionPane.ERROR_MESSAGE);
        	return;
		}
		else {
			
	        try {
		        switch (this.altaDe) {
		        case "Empresa":
		    		interfazUsuario.ingresarDatosEmpresa(nickname, nombre, apellido, password, correo,null, descripcion, linkWeb);
		        	break;
		        case "Postulante":
		    		interfazUsuario.ingresarDatosPostulante(nickname, nombre, apellido, password, correo,null, pais, fechaNac);
		        	break;
		        }
				JOptionPane.showMessageDialog(this, "El Usuario se ha creado con éxito", "Registrar Usuario", JOptionPane.INFORMATION_MESSAGE);
				limpiarEntrada();
	        } catch (FechaInvalidaException | UsuarioRegistradoException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Registrar Usuario", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private boolean checkDatos(String nickname,String correo,String nombre,String descripcion,String pais,String apellido,LocalDate fechNac,String contra,String contraConfirm) {
		if(nickname.isEmpty() || correo.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || contra.isEmpty() || contraConfirm.isEmpty())
			return false;
		else if((descripcion.isEmpty()) &&  (pais.isEmpty() || fechNac==null))
			return false;
		return true;
	}
	
	public void modoAlta(String tipoUsu) {
		this.altaDe = tipoUsu; 
	}
	
	public void actualizarForm() {
		switch (this.altaDe) {
        case "Empresa":{
        	this.lblFechaNacimiento.setVisible(false);
        	this.lblDescripcion.setVisible(true);
        	this.lblLinkweb.setVisible(true);
        	this.lblPais.setVisible(false);
        	this.scrollPane.setVisible(true);
        	this.gridBagLayout.rowHeights[8]=65;
        	
        	this.descripcionField.setVisible(true);
        	this.linkWebField.setVisible(true);
        	this.dateChooser.setVisible(false);
        	this.paisField.setVisible(false);
        	break;
        }
        case "Postulante":{
        	this.lblFechaNacimiento.setVisible(true);
        	this.lblDescripcion.setVisible(false);
        	this.lblLinkweb.setVisible(false);
        	this.lblPais.setVisible(true);
        	this.scrollPane.setVisible(false);
        	this.gridBagLayout.rowHeights[8]=0;
        	
        	this.descripcionField.setVisible(false);
        	this.linkWebField.setVisible(false);
        	this.dateChooser.setVisible(true);
        	this.paisField.setVisible(true);
        	break;
        }
        default:
        	break;
        }
	}
	
	public void limpiarEntrada() {
		this.passwordConfirmField.setText("");
		this.passwordField.setText("");
		this.nicknameField.setText("");
		this.nombreField.setText("");
		this.apellidoField.setText("");
		this.correoField.setText("");
		this.descripcionField.setText("");
		this.linkWebField.setText("");
		this.dateChooser.setDate(null);
		this.paisField.setText("");
	}

}
