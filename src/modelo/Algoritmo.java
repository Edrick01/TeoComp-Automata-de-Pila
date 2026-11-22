package modelo;
import modelo.SenalAnimacion;

import java.util.ArrayList;
import java.util.Stack;

public class Algoritmo {
	private Stack<Character> pila;
	private ArrayList<Character> leido;
	private String cadena;
	private int index;
	private boolean pasoPosible;
	private boolean aceptado;
	private int mitadCadena;
	private boolean mitad=false;
	public Algoritmo () {
		pila = new Stack<Character>();
		leido = new ArrayList<Character>();
		cadena="";
		index=0;
		pasoPosible=false;
		aceptado=false;
		mitadCadena=0;
	}
	public Algoritmo(String cadena) {
		this();
		this.cadena=cadena;
		mitadCadena=this.cadena.length()/2;
	}
	
	
	public boolean checkPossible() {
		if (cadena.length()%2==1) {
			return false;
		}
		else if (index+1==cadena.length()) {
			return true;
		}
		else if (index+1<=mitadCadena) {
			return true;
		}
		else if (index+1>mitadCadena) {
			if (cadena.charAt(index)== pila.lastElement()) {
				return true;
			}
		}
		return false;
	}
	
	// Paso EstadoActual, s saco pila n no saco, Etiqueta
	public ArrayList<Character> siguientePaso() {
		System.out.println("Siguiente paso");

		boolean meterPila=false;
		boolean sacarPila=false;
		ArrayList<Character> banderas = new ArrayList<Character>();
		if(index+1<=cadena.length()) {
			
			if (mitad) {
				banderas.add('f');
				banderas.add('n');
				banderas.add('v');
				mitad=false;
				return banderas;
			}
			
			else if (index+1<=mitadCadena) {
				banderas.add('s');
				banderas.add('n');
				meterPila=true;
				if (index+1==mitadCadena) {
					mitad=true;
				}
				
			}
			else {
				banderas.add('f');
				banderas.add('s');
			}
			if (cadena.charAt(index)=='a') {
				banderas.add('a');
			}
			else {
				banderas.add('b');
			}
			
			if(meterPila) {
				meterPila=false;
				System.out.println("meto a la pila");
				pila.add(banderas.get(2));
			}
			if(banderas.get(1).equals('s')) {
				pila.pop();
			}
			
			index++;
			if (pila.size()==0 && index+1>=cadena.length()) {
				aceptado=true;
			}
			System.out.println(String.valueOf(index-1)+" <-index " + banderas.get(0)+ " " + banderas.get(1) + " " + banderas.get(2));
			return banderas;
		}
		
		return banderas;
	}
	public String getCadena() {
		return cadena;
	}
	
	//verificar si la cadena es de a y  b
	public boolean checkCadena(String cadena) {
		for (int i=0; i<cadena.length();i++) {
			if(cadena.charAt(i)!= 'a' && cadena.charAt(i)!= 'b' ) {
				return false;
			}
		}
		return true;
	}
	
	// poner cadena
	public boolean setCadena(String cadena) {
		if (checkCadena(cadena)) {
			this.cadena = cadena;
			index=0;
			aceptado=false;
			mitadCadena=this.cadena.length()/2;
			mitad=false;
			pila.clear();
			leido.clear();
			return true;
		}

		return false;
	}
	
	public boolean isAceptado() {
		return aceptado;
	}
	public void setAceptado(boolean aceptado) {
		this.aceptado = aceptado;
	}
	public Stack<Character> getPila() {
		return pila;
	}
	public ArrayList<Character> getLeido() {
		return leido;
	}
	public int getIndex() {
		return index;
	}
	public boolean isPasoPosible() {
		return pasoPosible;
	}
	public int getMitadCadena() {
		return mitadCadena;
	}
	public boolean isMitad() {
		return mitad;
	}
	
	
	
}
