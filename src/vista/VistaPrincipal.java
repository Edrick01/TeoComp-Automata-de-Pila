package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import panelPila.PanelPila;
import panelautomata.PanelAutomata;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class VistaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField cadenaText;
	private JButton btnSiguiente;
	private JLabel lblEstadoCadena;
	private JButton btnLeerCadena;
	private JPanel panelAutomata;
	private JPanel panelPila;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaPrincipal frame = new VistaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VistaPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 988, 589);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(128, 128, 128));
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(2, 1, 0, 0));
		
		JLabel titulo = new JLabel("Automata de Pila");
		titulo.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(titulo);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel cadena = new JLabel("Cadena");
		panel_2.add(cadena);
		
		cadenaText = new JTextField();
		panel_2.add(cadenaText);
		cadenaText.setColumns(10);
		
		btnLeerCadena = new JButton("Leer Cadena");
		panel_2.add(btnLeerCadena);
		
		JPanel panelCuadroDinamico = new JPanel();
		contentPane.add(panelCuadroDinamico, BorderLayout.CENTER);
		panelCuadroDinamico.setLayout(new BorderLayout(0, 0));
		JPanel panelCadenasEstados = new JPanel();
		panelCadenasEstados.setBorder(new EmptyBorder(10, 0, 10, 0));
		panelCuadroDinamico.add(panelCadenasEstados, BorderLayout.NORTH);
		
		JLabel lblPorleer = new JLabel("Por Leer");
		lblPorleer.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblCadenaRestante = new JLabel("Esperando Cadena...");
		lblCadenaRestante.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblEstado = new JLabel("Estado");
		lblEstado.setHorizontalAlignment(SwingConstants.CENTER);
		panelCadenasEstados.setLayout(new GridLayout(4, 4, 10, 10));
		panelCadenasEstados.add(lblPorleer);
		panelCadenasEstados.add(lblCadenaRestante);
		panelCadenasEstados.add(lblEstado);
		
		JLabel lblEstadoActual = new JLabel("N/A");
		lblEstadoActual.setHorizontalAlignment(SwingConstants.CENTER);
		panelCadenasEstados.add(lblEstadoActual);
		panelCadenasEstados.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{lblPorleer, lblCadenaRestante, lblEstado, lblEstadoActual}));
		
		JPanel panelPilaAutomata = new JPanel();
		panelPila = new PanelPila();
		panelPilaAutomata.add(panelPila);
		panelCuadroDinamico.add(panelPilaAutomata, BorderLayout.CENTER);
		panelPilaAutomata.setLayout(new GridLayout(0, 2, 10, 10));
		
		panelAutomata = new PanelAutomata();
		panelPilaAutomata.add(panelAutomata);
		
	
		panelPilaAutomata.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{panelPila, panelAutomata}));
		

		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new EmptyBorder(10, 0, 0, 0));
		contentPane.add(panel_4, BorderLayout.SOUTH);
		panel_4.setLayout(new GridLayout(1, 3, 10, 0));
		
		btnSiguiente = new JButton("Siguiente");
		panel_4.add(btnSiguiente);
		
		lblEstadoCadena = new JLabel("Cadena en Espera");
		lblEstadoCadena.setHorizontalAlignment(SwingConstants.CENTER);
		panel_4.add(lblEstadoCadena);
		
		JButton btnSalir = new JButton("Salir");
		panel_4.add(btnSalir);

	}

	public JTextField getCadenaText() {
		return cadenaText;
	}

	public JButton getBtnSiguiente() {
		return btnSiguiente;
	}

	public JLabel getLblEstadoCadena() {
		return lblEstadoCadena;
	}
	
	public JButton getBtnLeerCadena() {
		return btnLeerCadena;
	}
	
	public JPanel getPanelAutomata() {
		return panelAutomata;
	}
	public JPanel getPanelPila() {
		return panelPila;
	}

	public void setEstadoCadena(int estado) {
		if(estado==1) {
		lblEstadoCadena.setForeground(new Color(0, 140, 50));
		lblEstadoCadena.setText("Cadena Aceptada");
		}
		else if (estado==2) {
			lblEstadoCadena.setForeground(new Color(200, 0, 0));
			lblEstadoCadena.setText("Cadena Rechazada");
		}
		else if (estado ==3){
			lblEstadoCadena.setForeground(new Color(0, 0, 0));
			lblEstadoCadena.setText("Cadena en Proceso");
		}
		else {
			lblEstadoCadena.setForeground(new Color(105, 105, 105));
			lblEstadoCadena.setText("Cadena en Espera");
		}
	}
	public void agregarElementoTabla(Character elemento, boolean sacar) {
	}
	
}
