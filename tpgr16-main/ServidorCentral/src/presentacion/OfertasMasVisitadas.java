package presentacion;

import javax.swing.JInternalFrame;

import logica.datatypes.DtEmpresa;
import logica.datatypes.DtOferta;
import logica.interfaces.IOfertaLab;
import logica.interfaces.IUsuario;
import logica.manejadores.ManejadorOfertaLaboral;
import logica.modelos.OfertaLab;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class OfertasMasVisitadas extends JInternalFrame{
	private IOfertaLab interfazOfertaLaboral;
	private IUsuario interfazUsuario;
	private JTextField Oferta1;
	private JTextField Oferta2;
	private JTextField Oferta3;
	private JTextField Oferta4;
	private JTextField Oferta5;
	private JTextField Empresa1;
	private JTextField Empresa2;
	private JTextField Empresa3;
	private JTextField Empresa4;
	private JTextField Empresa5;
	private JTextField Pub1;
	private JTextField Pub2;
	private JTextField Pub3;
	private JTextField Pub4;
	private JTextField Pub5;
	private JTextField Visitas1;
	private JTextField Visitas2;
	private JTextField Visitas3;
	private JTextField Visitas4;
	private JTextField Visitas5;
	
	public OfertasMasVisitadas(IUsuario interfazUsu, IOfertaLab iOfertaLaboral) {
		interfazUsuario = interfazUsu;
		interfazOfertaLaboral = iOfertaLaboral;
		
		setTitle("Ofertas mas Visitadas");
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setClosable(true);
		setBounds(0, 0, 600,600);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 24, 205, 96, 103, 101, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 40, 40, 40, 40, 40, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("#");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lblNewLabel_6 = new JLabel("Oferta Laboral");
		GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
		gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_6.gridx = 2;
		gbc_lblNewLabel_6.gridy = 1;
		getContentPane().add(lblNewLabel_6, gbc_lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Empresa");
		GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
		gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_7.gridx = 3;
		gbc_lblNewLabel_7.gridy = 1;
		getContentPane().add(lblNewLabel_7, gbc_lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("Tipo Pub");
		GridBagConstraints gbc_lblNewLabel_8 = new GridBagConstraints();
		gbc_lblNewLabel_8.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_8.gridx = 4;
		gbc_lblNewLabel_8.gridy = 1;
		getContentPane().add(lblNewLabel_8, gbc_lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("Cantidad de Visitas");
		GridBagConstraints gbc_lblNewLabel_9 = new GridBagConstraints();
		gbc_lblNewLabel_9.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_9.gridx = 5;
		gbc_lblNewLabel_9.gridy = 1;
		getContentPane().add(lblNewLabel_9, gbc_lblNewLabel_9);
		
		JLabel lblNewLabel_1 = new JLabel("1");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 2;
		getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		Oferta1 = new JTextField();
		Oferta1.setEditable(false);
		GridBagConstraints gbc_Oferta1 = new GridBagConstraints();
		gbc_Oferta1.insets = new Insets(0, 0, 5, 5);
		gbc_Oferta1.fill = GridBagConstraints.BOTH;
		gbc_Oferta1.gridx = 2;
		gbc_Oferta1.gridy = 2;
		getContentPane().add(Oferta1, gbc_Oferta1);
		Oferta1.setColumns(10);
		
		Empresa1 = new JTextField();
		Empresa1.setEditable(false);
		GridBagConstraints gbc_Empresa1 = new GridBagConstraints();
		gbc_Empresa1.insets = new Insets(0, 0, 5, 5);
		gbc_Empresa1.fill = GridBagConstraints.BOTH;
		gbc_Empresa1.gridx = 3;
		gbc_Empresa1.gridy = 2;
		getContentPane().add(Empresa1, gbc_Empresa1);
		Empresa1.setColumns(10);
		
		Pub1 = new JTextField();
		Pub1.setEditable(false);
		GridBagConstraints gbc_Pub1 = new GridBagConstraints();
		gbc_Pub1.insets = new Insets(0, 0, 5, 5);
		gbc_Pub1.fill = GridBagConstraints.BOTH;
		gbc_Pub1.gridx = 4;
		gbc_Pub1.gridy = 2;
		getContentPane().add(Pub1, gbc_Pub1);
		Pub1.setColumns(10);
		
		Visitas1 = new JTextField();
		Visitas1.setEditable(false);
		GridBagConstraints gbc_Visitas1 = new GridBagConstraints();
		gbc_Visitas1.insets = new Insets(0, 0, 5, 0);
		gbc_Visitas1.fill = GridBagConstraints.BOTH;
		gbc_Visitas1.gridx = 5;
		gbc_Visitas1.gridy = 2;
		getContentPane().add(Visitas1, gbc_Visitas1);
		Visitas1.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("2");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 3;
		getContentPane().add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		Oferta2 = new JTextField();
		Oferta2.setEditable(false);
		GridBagConstraints gbc_Oferta2 = new GridBagConstraints();
		gbc_Oferta2.insets = new Insets(0, 0, 5, 5);
		gbc_Oferta2.fill = GridBagConstraints.BOTH;
		gbc_Oferta2.gridx = 2;
		gbc_Oferta2.gridy = 3;
		getContentPane().add(Oferta2, gbc_Oferta2);
		Oferta2.setColumns(10);
		
		Empresa2 = new JTextField();
		Empresa2.setEditable(false);
		GridBagConstraints gbc_Empresa2 = new GridBagConstraints();
		gbc_Empresa2.insets = new Insets(0, 0, 5, 5);
		gbc_Empresa2.fill = GridBagConstraints.BOTH;
		gbc_Empresa2.gridx = 3;
		gbc_Empresa2.gridy = 3;
		getContentPane().add(Empresa2, gbc_Empresa2);
		Empresa2.setColumns(10);
		
		Pub2 = new JTextField();
		Pub2.setEditable(false);
		GridBagConstraints gbc_Pub2 = new GridBagConstraints();
		gbc_Pub2.insets = new Insets(0, 0, 5, 5);
		gbc_Pub2.fill = GridBagConstraints.BOTH;
		gbc_Pub2.gridx = 4;
		gbc_Pub2.gridy = 3;
		getContentPane().add(Pub2, gbc_Pub2);
		Pub2.setColumns(10);
		
		Visitas2 = new JTextField();
		Visitas2.setEditable(false);
		GridBagConstraints gbc_Visitas2 = new GridBagConstraints();
		gbc_Visitas2.insets = new Insets(0, 0, 5, 0);
		gbc_Visitas2.fill = GridBagConstraints.BOTH;
		gbc_Visitas2.gridx = 5;
		gbc_Visitas2.gridy = 3;
		getContentPane().add(Visitas2, gbc_Visitas2);
		Visitas2.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("3");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 1;
		gbc_lblNewLabel_3.gridy = 4;
		getContentPane().add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		Oferta3 = new JTextField();
		Oferta3.setEditable(false);
		GridBagConstraints gbc_Oferta3 = new GridBagConstraints();
		gbc_Oferta3.insets = new Insets(0, 0, 5, 5);
		gbc_Oferta3.fill = GridBagConstraints.BOTH;
		gbc_Oferta3.gridx = 2;
		gbc_Oferta3.gridy = 4;
		getContentPane().add(Oferta3, gbc_Oferta3);
		Oferta3.setColumns(10);
		
		Empresa3 = new JTextField();
		Empresa3.setEditable(false);
		GridBagConstraints gbc_Empresa3 = new GridBagConstraints();
		gbc_Empresa3.insets = new Insets(0, 0, 5, 5);
		gbc_Empresa3.fill = GridBagConstraints.BOTH;
		gbc_Empresa3.gridx = 3;
		gbc_Empresa3.gridy = 4;
		getContentPane().add(Empresa3, gbc_Empresa3);
		Empresa3.setColumns(10);
		
		Pub3 = new JTextField();
		Pub3.setEditable(false);
		GridBagConstraints gbc_Pub3 = new GridBagConstraints();
		gbc_Pub3.insets = new Insets(0, 0, 5, 5);
		gbc_Pub3.fill = GridBagConstraints.BOTH;
		gbc_Pub3.gridx = 4;
		gbc_Pub3.gridy = 4;
		getContentPane().add(Pub3, gbc_Pub3);
		Pub3.setColumns(10);
		
		Visitas3 = new JTextField();
		Visitas3.setEditable(false);
		GridBagConstraints gbc_Visitas3 = new GridBagConstraints();
		gbc_Visitas3.insets = new Insets(0, 0, 5, 0);
		gbc_Visitas3.fill = GridBagConstraints.BOTH;
		gbc_Visitas3.gridx = 5;
		gbc_Visitas3.gridy = 4;
		getContentPane().add(Visitas3, gbc_Visitas3);
		Visitas3.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("4");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 1;
		gbc_lblNewLabel_4.gridy = 5;
		getContentPane().add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		Oferta4 = new JTextField();
		Oferta4.setEditable(false);
		GridBagConstraints gbc_Oferta4 = new GridBagConstraints();
		gbc_Oferta4.insets = new Insets(0, 0, 5, 5);
		gbc_Oferta4.fill = GridBagConstraints.BOTH;
		gbc_Oferta4.gridx = 2;
		gbc_Oferta4.gridy = 5;
		getContentPane().add(Oferta4, gbc_Oferta4);
		Oferta4.setColumns(10);
		
		Empresa4 = new JTextField();
		Empresa4.setEditable(false);
		GridBagConstraints gbc_Empresa4 = new GridBagConstraints();
		gbc_Empresa4.insets = new Insets(0, 0, 5, 5);
		gbc_Empresa4.fill = GridBagConstraints.BOTH;
		gbc_Empresa4.gridx = 3;
		gbc_Empresa4.gridy = 5;
		getContentPane().add(Empresa4, gbc_Empresa4);
		Empresa4.setColumns(10);
		
		Pub4 = new JTextField();
		Pub4.setEditable(false);
		GridBagConstraints gbc_Pub4 = new GridBagConstraints();
		gbc_Pub4.insets = new Insets(0, 0, 5, 5);
		gbc_Pub4.fill = GridBagConstraints.BOTH;
		gbc_Pub4.gridx = 4;
		gbc_Pub4.gridy = 5;
		getContentPane().add(Pub4, gbc_Pub4);
		Pub4.setColumns(10);
		
		Visitas4 = new JTextField();
		Visitas4.setEditable(false);
		GridBagConstraints gbc_Visitas4 = new GridBagConstraints();
		gbc_Visitas4.insets = new Insets(0, 0, 5, 0);
		gbc_Visitas4.fill = GridBagConstraints.BOTH;
		gbc_Visitas4.gridx = 5;
		gbc_Visitas4.gridy = 5;
		getContentPane().add(Visitas4, gbc_Visitas4);
		Visitas4.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("5");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 1;
		gbc_lblNewLabel_5.gridy = 6;
		getContentPane().add(lblNewLabel_5, gbc_lblNewLabel_5);
		
		Oferta5 = new JTextField();
		Oferta5.setEditable(false);
		GridBagConstraints gbc_Oferta5 = new GridBagConstraints();
		gbc_Oferta5.insets = new Insets(0, 0, 5, 5);
		gbc_Oferta5.fill = GridBagConstraints.BOTH;
		gbc_Oferta5.gridx = 2;
		gbc_Oferta5.gridy = 6;
		getContentPane().add(Oferta5, gbc_Oferta5);
		Oferta5.setColumns(10);
		
		Empresa5 = new JTextField();
		Empresa5.setEditable(false);
		GridBagConstraints gbc_Empresa5 = new GridBagConstraints();
		gbc_Empresa5.insets = new Insets(0, 0, 5, 5);
		gbc_Empresa5.fill = GridBagConstraints.BOTH;
		gbc_Empresa5.gridx = 3;
		gbc_Empresa5.gridy = 6;
		getContentPane().add(Empresa5, gbc_Empresa5);
		Empresa5.setColumns(10);
		
		Pub5 = new JTextField();
		Pub5.setEditable(false);
		GridBagConstraints gbc_Pub5 = new GridBagConstraints();
		gbc_Pub5.insets = new Insets(0, 0, 5, 5);
		gbc_Pub5.fill = GridBagConstraints.BOTH;
		gbc_Pub5.gridx = 4;
		gbc_Pub5.gridy = 6;
		getContentPane().add(Pub5, gbc_Pub5);
		Pub5.setColumns(10);
		
		Visitas5 = new JTextField();
		Visitas5.setEditable(false);
		GridBagConstraints gbc_Visitas5 = new GridBagConstraints();
		gbc_Visitas5.insets = new Insets(0, 0, 5, 0);
		gbc_Visitas5.fill = GridBagConstraints.BOTH;
		gbc_Visitas5.gridx = 5;
		gbc_Visitas5.gridy = 6;
		getContentPane().add(Visitas5, gbc_Visitas5);
		Visitas5.setColumns(10);
	}
	
	public void cargarDatos() {
		OfertaLab[] ofertas = ManejadorOfertaLaboral.getInstance().obtenerOfertas();
		if (ofertas != null) {
			OfertaLab temp = null;
			
			for (int i = 0; i < ofertas.length; i++) {
				for (int j = 1; j < (ofertas.length - i); j++) {
					if (ofertas[j - 1].getVisitas() < ofertas[j].getVisitas()) {
						temp = ofertas[j - 1];
						ofertas[j - 1] = ofertas[j];
						ofertas[j] = temp;
					}
				}
			}
		}
		
		this.Oferta1.setText(ofertas[0].getNombre());
		this.Oferta2.setText(ofertas[1].getNombre());
		this.Oferta3.setText(ofertas[2].getNombre());
		this.Oferta4.setText(ofertas[3].getNombre());
		this.Oferta5.setText(ofertas[4].getNombre());
		
		this.Empresa1.setText(ofertas[0].getEmpresa().getNickname());
		this.Empresa2.setText(ofertas[1].getEmpresa().getNickname());
		this.Empresa3.setText(ofertas[2].getEmpresa().getNickname());
		this.Empresa4.setText(ofertas[3].getEmpresa().getNickname());
		this.Empresa5.setText(ofertas[4].getEmpresa().getNickname());
		
		this.Pub1.setText(ofertas[0].getTipo().getNombre());
		this.Pub2.setText(ofertas[1].getTipo().getNombre());
		this.Pub3.setText(ofertas[2].getTipo().getNombre());
		this.Pub4.setText(ofertas[3].getTipo().getNombre());
		this.Pub5.setText(ofertas[4].getTipo().getNombre());
		
		String q = String.valueOf(ofertas[0].getVisitas());
		String w = String.valueOf(ofertas[1].getVisitas());
		String e = String.valueOf(ofertas[2].getVisitas());
		String r = String.valueOf(ofertas[3].getVisitas());
		String t = String.valueOf(ofertas[4].getVisitas());
		
		this.Visitas1.setText(q);
		this.Visitas2.setText(w);
		this.Visitas3.setText(e);
		this.Visitas4.setText(r);
		this.Visitas5.setText(t);
	}
	
}
