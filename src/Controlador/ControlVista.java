package Controlador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import modelo.Algoritmo;
import panelautomata.PanelAutomata;
import vista.*;
public class ControlVista implements ActionListener{
	
	private PanelAutomata panelAnimacion;
	private VistaPrincipal vista;
	private Algoritmo algoritmo;
	public ControlVista(JPanel panelAnimacion ,VistaPrincipal vista,Algoritmo algoritmo) {
		this.panelAnimacion=(PanelAutomata) panelAnimacion;
		this.vista=vista;
		this.algoritmo=algoritmo;
		this.vista.getBtnSiguiente().addActionListener(this);
		this.vista.getCadenaText().addActionListener(this);
		this.vista.getBtnLeerCadena().addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object fuente = e.getSource();
		if (fuente.equals(vista.getBtnSiguiente())) {
			siguientePaso();
		}
		else if (fuente.equals(vista.getBtnLeerCadena())) {
			cargarCadena();
		}
		
	}
	//si la cadena no es a y b en el label de estado cadena decir que no es correcta
	public void cargarCadena() {
		try {
			if (!algoritmo.setCadena(vista.getCadenaText().getText().trim())) {
				vista.getLblEstadoCadena().setText("La cadena Ingresada no es compatible");
			}
			else {
				vista.getLblEstadoCadena().setText("Reconociendo Cadena");
				panelAnimacion.setEstados('s', 'n');
				panelAnimacion.setAnimacion();			

			}
		}
		catch (Exception e) {
			vista.getLblEstadoCadena().setText("Ocurrió un error inesperado: " + e.getMessage());
		}
	}
	
	public void siguientePaso() {
		System.out.println("hoal");
		if (algoritmo.getIndex()+1>=algoritmo.getCadena().length()) {
			System.out.println("ho");
			return;
		}
		if (algoritmo.checkPossible()) {
			ArrayList<Character> banderas = algoritmo.siguientePaso();
			panelAnimacion.setEstados(banderas.get(0), banderas.get(2));
			System.out.println("si");

			panelAnimacion.setAnimacion();			
		}
		else {
			vista.getLblEstadoCadena().setText("Cadena Rechazada");
			
		}
	
	}

}
