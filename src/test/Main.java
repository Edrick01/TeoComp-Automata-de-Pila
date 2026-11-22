package test;
import Controlador.*;
import modelo.*;
import panelautomata.*;
import vista.*;
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		VistaPrincipal vista = new VistaPrincipal();
		Algoritmo algoritmo = new Algoritmo();
		PanelAutomata panel = new PanelAutomata();
		ControlVista controlador = new ControlVista(vista.getPanelAutomata(),vista.getPanelPila(),vista,algoritmo);
		
		vista.setVisible(true);
	}

}
