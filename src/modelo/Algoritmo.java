package modelo;
import java.util.ArrayList;
import java.util.Stack;

public class Algoritmo {
	private Stack<Character> pila;
	private ArrayList<Character> leido;
	private String cadena;
	private int index;
	private int mitadCadena;
	private int longitudCadena;
	private boolean mitad=false;
	
	public Algoritmo () {
		pila = new Stack<Character>();
		leido = new ArrayList<Character>();
		cadena="";
		longitudCadena=0;
		index=0;
		mitadCadena=0;
	}
	
	public Algoritmo(String cadena) {
		this();
		this.cadena=cadena;
		
		longitudCadena=this.cadena.length();
		mitadCadena=longitudCadena/2;
	}
	
	//verificamos si el paso es posible
	public boolean checkPossible() {
		if (longitudCadena%2==1) {
			return false;
		}
		else if (index+1==longitudCadena) {
			return true;
		}
		else if (index+1<=mitadCadena) {
			return true;
		}
		else if (index+1>mitadCadena && index+1<longitudCadena) {
			if (cadena.charAt(index) == pila.lastElement()) {
				return true;
			}
		}
		return false;
	}
	
	// Paso EstadoActual, 's' saco pila 'n' no saco, Etiqueta
	// tres caracteres
	// 1: 'f' o 's' Estados del automata
	// 2: 's' o 'n', 's' saco pila 'n' no saco
	// 3: 'a' 'b' o 'v' etiqueta de transición 
	// si toca a antes de la mitad metemos a la pila
	// si toca a despues de la mitad sacamos algo de la pila
	// si este algo no es a, en checkPossible retornamos false y nunca llegamos a siguientePaso()
	// asi con b, y 'v' unicamente aparece en la transicion vacia
	public ArrayList<Character> siguientePaso() {
		System.out.println("Siguiente paso");

		ArrayList<Character> banderas = new ArrayList<Character>();
		
		if(index+1<=longitudCadena) {
			//si estamos en la mitad simplemente pasamos al estado f
			if (mitad) {
				banderas.add('f');
				banderas.add('n');
				banderas.add('v');
				mitad=false;
				return banderas;
			}
			// si no y si estamos antes de la mitad siempre leemos y metemos a la pila luego
			else if (index+1<=mitadCadena) {
				banderas.add('s');
				banderas.add('n');
				//si justo llegamos al caracter que divide la cadena, a la proxima entraremos al if mitad
				if (index+1==mitadCadena) {
					mitad=true;
				}
			}
			//si no estamos en la mitad ni antes, estamos despues, ahora toca sacar de la pila y leer y aqui no metemos nada
			else {
				banderas.add('f');
				banderas.add('s');
			}
			//etiqueta a regresar
			if (cadena.charAt(index)=='a') {
				banderas.add('a');
			}
			else {
				banderas.add('b');
			}
			
			//meter/sacar de la pila
			if(banderas.get(1).equals('s')) {
				pila.pop();
			}
			else {
				System.out.println("meto a la pila");
				pila.add(banderas.get(2));
			}	
			
			index++;

			System.out.println(String.valueOf(index-1)+" <-index " + banderas.get(0)+ " " + banderas.get(1) + " " + banderas.get(2));
			if (!pila.isEmpty())
			System.out.println(pila.lastElement());

			return banderas;
		}
		banderas.add(' ');
		banderas.add(' ');
		banderas.add(' ');
		return banderas;
	}
	
	//verificar si la cadena es de a y  b
	public boolean checkCadena(String cadena) {
		for (int i=0; i<longitudCadena;i++) {
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
			longitudCadena=cadena.length();
			mitadCadena=longitudCadena/2;
			mitad=false;
			pila.clear();
			leido.clear();
			return true;
		}

		return false;
	}
	
	public boolean isAceptado() {
		if (index+1>=longitudCadena && pila.size()==0) {
			return true;
		}
		return false;
	}
	
	public String getCadena() {
		return cadena;
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

	public int getMitadCadena() {
		return mitadCadena;
	}
	public boolean isMitad() {
		return mitad;
	}
	public int getLongitudCadena() {
		return longitudCadena;
	}
	
	
	
}
