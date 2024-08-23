package presentacion;

import javax.swing.JInternalFrame;

import logica.interfaces.IOfertaLab;
import logica.interfaces.IUsuario;

import java.awt.GridBagLayout;
import javax.swing.JComboBox;
import logica.datatypes.DtEmpresa;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import logica.datatypes.DtOferta;
import logica.datatypes.EstadoOferta;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import excepciones.UsuarioNoExisteException;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;

@SuppressWarnings("serial")
public class AceptarRechazarOfertaLaboral extends JInternalFrame {
	private JList<DtOferta> jListOfertas;
	private JComboBox<DtEmpresa> comboBoxEmpresas;
	private IOfertaLab interfazOferta;
	private IUsuario interfazUsuario;

	
	public AceptarRechazarOfertaLaboral(IOfertaLab interfazOfertaLaboral, IUsuario interfazUsu) {
		setClosable(true);
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
		setBounds(0, 0, 510, 310);
		interfazUsuario = interfazUsu;
		interfazOferta = interfazOfertaLaboral;
		setTitle("Aceptar/Rechazar Ofertas Laborales");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{44, 137, 115, 113, 69, 57, 0};
		gridBagLayout.rowHeights = new int[]{10, 0, 0, 102, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblListaDeEmpresas = new JLabel("Empresa:");
		GridBagConstraints gbc_lblListaDeEmpresas = new GridBagConstraints();
		gbc_lblListaDeEmpresas.insets = new Insets(0, 0, 5, 5);
		gbc_lblListaDeEmpresas.anchor = GridBagConstraints.EAST;
		gbc_lblListaDeEmpresas.gridx = 1;
		gbc_lblListaDeEmpresas.gridy = 1;
		getContentPane().add(lblListaDeEmpresas, gbc_lblListaDeEmpresas);
		
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
		comboBoxEmpresas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				DtEmpresa emp = (DtEmpresa) comboBoxEmpresas.getSelectedItem();
				cargarOfertasIngresadas(emp);
			}
		});
		GridBagConstraints gbc_comboBoxEmpresas = new GridBagConstraints();
		gbc_comboBoxEmpresas.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxEmpresas.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxEmpresas.gridwidth = 3;
		gbc_comboBoxEmpresas.gridx = 2;
		gbc_comboBoxEmpresas.gridy = 1;
		getContentPane().add(comboBoxEmpresas, gbc_comboBoxEmpresas);
		
		JLabel lblListaOfertasLaborales = new JLabel("Ofertas Ingresadas:");
		GridBagConstraints gbc_lblListaOfertasLaborales = new GridBagConstraints();
		gbc_lblListaOfertasLaborales.anchor = GridBagConstraints.EAST;
		gbc_lblListaOfertasLaborales.gridheight = 2;
		gbc_lblListaOfertasLaborales.insets = new Insets(0, 0, 5, 5);
		gbc_lblListaOfertasLaborales.gridx = 1;
		gbc_lblListaOfertasLaborales.gridy = 2;
		getContentPane().add(lblListaOfertasLaborales, gbc_lblListaOfertasLaborales);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 2;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 2;
		getContentPane().add(scrollPane, gbc_scrollPane);
		
		jListOfertas = new JList<DtOferta>();
		scrollPane.setViewportView(jListOfertas);
		
		JButton btnAceptarOferta = new JButton("Aceptar");
		btnAceptarOferta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				confirmarOfertas();
			}
		});
		
		GridBagConstraints gbc_btnAceptarOferta = new GridBagConstraints();
		gbc_btnAceptarOferta.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAceptarOferta.insets = new Insets(0, 0, 0, 5);
		gbc_btnAceptarOferta.gridx = 2;
		gbc_btnAceptarOferta.gridy = 5;
		getContentPane().add(btnAceptarOferta, gbc_btnAceptarOferta);
		
		JButton btnRechazarOferta = new JButton("Rechazar");
		btnRechazarOferta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				rechazarOfertas();
			}
		});
		GridBagConstraints gbc_btnRechazarOferta = new GridBagConstraints();
		gbc_btnRechazarOferta.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRechazarOferta.insets = new Insets(0, 0, 0, 5);
		gbc_btnRechazarOferta.gridx = 3;
		gbc_btnRechazarOferta.gridy = 5;
		getContentPane().add(btnRechazarOferta, gbc_btnRechazarOferta);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				setVisible(false);
			}
		});
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCancelar.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancelar.gridx = 4;
		gbc_btnCancelar.gridy = 5;
		getContentPane().add(btnCancelar, gbc_btnCancelar);

	}
	
	private void cargarOfertasIngresadas(DtEmpresa emp) {
		if (emp!=null) {
			try {
				DtOferta[] ofertasIngresadas = interfazOferta.obtenerOfertasIngresadas(emp.getNickname());
		        DefaultListModel<DtOferta> modeloLista = new DefaultListModel<DtOferta>();
		        for (DtOferta oferta : ofertasIngresadas) {
		            modeloLista.addElement(oferta);
		        }
		        jListOfertas.setModel(modeloLista);
			} catch (UsuarioNoExisteException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void cargarEmpresas() {
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
	
	private void confirmarOfertas() {
		List<DtOferta> ofertasSeleccionados = jListOfertas.getSelectedValuesList();
		for (DtOferta oferta : ofertasSeleccionados) {
			interfazOferta.actualizarOfertaEstado(oferta.getNombre(), EstadoOferta.CONFIRMADO);
		}
		if (ofertasSeleccionados.size()>0) {
			JOptionPane.showMessageDialog(this, "Se han confirmado las oferta/s", "Aceptar/Rechazar Ofertas", JOptionPane.INFORMATION_MESSAGE);
			cargarOfertasIngresadas((DtEmpresa) comboBoxEmpresas.getSelectedItem());
		}
	}
	
	private void rechazarOfertas() {
		List<DtOferta> ofertasSeleccionados = jListOfertas.getSelectedValuesList();
		for (DtOferta oferta : ofertasSeleccionados) {
			interfazOferta.actualizarOfertaEstado(oferta.getNombre(), EstadoOferta.RECHAZADO);
		}
		if (ofertasSeleccionados.size()>0) {
			JOptionPane.showMessageDialog(this, "Se han rechazado las oferta/s", "Aceptar/Rechazar Ofertas", JOptionPane.INFORMATION_MESSAGE);
			cargarOfertasIngresadas((DtEmpresa) comboBoxEmpresas.getSelectedItem());
		}

	}

}
