package Controlador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import modelo.Algoritmo;
import panelPila.PanelPila;
import panelautomata.PanelAutomata;
import vista.*;
public class ControlVista implements ActionListener{
	
	private PanelAutomata panelAnimacion;
	private VistaPrincipal vista;
	private Algoritmo algoritmo;
	private PanelPila paneldePila;
	public ControlVista(JPanel panelAnimacion ,JPanel panelPila,VistaPrincipal vista,Algoritmo algoritmo) {
		this.panelAnimacion=(PanelAutomata) panelAnimacion;
		this.vista=vista;
		this.algoritmo=algoritmo;
		this.paneldePila=(PanelPila) panelPila;
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
				panelAnimacion.detenerAnimaciones();
			}
			else {
				vista.getLblEstadoCadena().setText("Reconociendo Cadena");
				panelAnimacion.setEstados('s', 'n');
				panelAnimacion.setAnimacion();	
				paneldePila.reiniciar();
				this.vista.getBtnSiguiente().setEnabled(true);
				panelAnimacion.detenerAnimaciones();

			}
		}
		catch (Exception e) {
			vista.getLblEstadoCadena().setText("Ocurrió un error inesperado: " + e.getMessage());
		}
	}
	
	public void siguientePaso() {
		
		System.out.println("hoal");


		if (algoritmo.checkPossible()) {
			ArrayList<Character> banderas = algoritmo.siguientePaso();
			panelAnimacion.setEstados(banderas.get(0), banderas.get(2));
			if(banderas.get(1).equals('s')) {
				paneldePila.eliminarElemento();
			}else if (!banderas.get(2).equals('v')){
				paneldePila.agregarElemento(banderas.get(2));
			}
			System.out.println("si");
			paneldePila.setAnimacion();
			panelAnimacion.setAnimacion();
			//si se acepta en este paso, da el mensaje
			esAceptado();
		}		
		else {
			vista.getLblEstadoCadena().setText("Cadena Rechazada");
			this.vista.getBtnSiguiente().setEnabled(false);
			
		}
	
	}
	
	public void esAceptado() {
		if (algoritmo.isAceptado()) {
			vista.getLblEstadoCadena().setText("Cadena Aceptada");
			this.vista.getBtnSiguiente().setEnabled(false);
			return;
		}
	}

}
