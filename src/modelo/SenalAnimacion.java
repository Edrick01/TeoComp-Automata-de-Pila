package modelo;

import java.util.List;

public class SenalAnimacion {
	protected List<Character> estados = List.of('s', 'f');
	protected List<Character> etiquetaTransicion = List.of('a', 'b', 'v', 'n');
	private Character estadoActual = estados.get(0);
	private Character etiquetaActual = estados.get(3);
	private boolean enEstadoFinal = false;
	
	
	public Character getEstadoActual() {
		return estadoActual;
	}
	public void setEstadoActual(Character estadoActual) {
		this.estadoActual = estadoActual;
		if (estadoActual.equals('f')) {
			enEstadoFinal=true;
		}
	}
	public Character getEtiquetaActual() {
		return etiquetaActual;
	}
	
	public void setEtiquetaActual(Character etiquetaActual) {
		this.etiquetaActual = etiquetaActual;
	}
	public boolean isEnEstadoFinal() {
		return enEstadoFinal;
	}
	public void setEnEstadoFinal(boolean enEstadoFinal) {
		this.enEstadoFinal = enEstadoFinal;
	}
	
	
}
