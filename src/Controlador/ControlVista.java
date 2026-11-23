package Controlador;
import java.awt.Color;
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
				panelAnimacion.detenerAnimaciones();
				vista.setEstadoCadena(5);
				vista.getLblCadenaRestante().setForeground(new Color (0, 0, 0));
				vista.getLblCadenaRestante().setText("Esperando Cadena...");

				
			}
			else {
				vista.getLblCadenaRestante().setForeground(new Color (0, 0, 0));
				panelAnimacion.detenerAnimaciones();
				panelAnimacion.setEnError(false);
				vista.setEstadoCadena(3);
				panelAnimacion.setEstados('s', 'n');
				panelAnimacion.setAnimacion();	
				if (algoritmo.getLongitudCadena()==1) {
					paneldePila.setElementosTotales(1);
				}
				else if (algoritmo.getLongitudCadena()%2==1) paneldePila.setElementosTotales(algoritmo.getLongitudCadena()/2 + 1);
					
				else paneldePila.setElementosTotales(algoritmo.getLongitudCadena()/2);
				paneldePila.reiniciar();
				this.vista.getBtnSiguiente().setEnabled(true);
				this.vista.getLblCadenaRestante().setText(algoritmo.getPorLeer());
				

			}
		}
		catch (Exception e) {
			vista.getLblEstadoCadena().setForeground(new Color(200, 0, 0));
			vista.getLblEstadoCadena().setText("Ocurrió un error inesperado: " + e.getMessage());
		}
	}
	
	public void siguientePaso() {
		
		System.out.println("hoal");


		if (algoritmo.checkPossible()) {
	
			ArrayList<Character> banderas = algoritmo.siguientePaso();
			vista.getLblCadenaRestante().setText(algoritmo.getPorLeer());
			panelAnimacion.setEstados(banderas.get(0), banderas.get(2));
			vista.getLblEstadoActual().setText(String.valueOf(banderas.get(0)));
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
			panelAnimacion.setEnError(true);
			vista.setEstadoCadena(2);
			this.vista.getBtnSiguiente().setEnabled(false);
			if (!algoritmo.getPorLeer().isEmpty()) {
				this.vista.getLblCadenaRestante().setForeground(new Color (200,0,0));
			}
		}
	
	}
	
	public void esAceptado() {
		if (algoritmo.isAceptado()) {
			vista.setEstadoCadena(1);
			this.vista.getBtnSiguiente().setEnabled(false);
			if (algoritmo.getPorLeer().isEmpty()) {
				vista.getLblCadenaRestante().setForeground(new Color (0, 140, 50));
				vista.getLblCadenaRestante().setText("Cadena leida exitosamente");
				
			}
		}
	}

}
