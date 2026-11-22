package panelautomata;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

public class PanelAutomata extends JPanel {
	protected List<Character> estados = List.of('s', 'f');
	protected List<Character> etiquetaTransicion = List.of('a', 'b', 'v', 'n');
	protected Character estadoActual=estados.get(0);
	protected Character etiqueta=etiquetaTransicion.get(3);
	protected Color Transicion = new Color (255, 243, 176);
	protected Timer timerRepaint;
	
	@Override
	protected void paintComponent(Graphics g) {
		
	    super.paintComponent(g);
	    Graphics2D g2d = (Graphics2D) g;
	    g2d.setFont(new Font("SansSerif",Font.PLAIN, 50));

	    // para que no se vea pixeleado
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2d.setStroke(new BasicStroke(2)); // Grosor de la línea

	    // Dimensiones y posiciones relativas al centro del panel
	    int radio = 30;
	    int diametro = radio * 2;
	    int centroY = getHeight() / 2;

	    // Posición del círculo 's' (izquierda) y 'f' (derecha)
	    int centroS_X = (getWidth() / 2) - 120;
	    int centroF_X = (getWidth() / 2) + 120;

	    // Dibuja las dos rectas esas del estado inicial
	    g2d.drawLine(centroS_X - radio - 12, centroY - 13, centroS_X - radio - 2, centroY);
	    g2d.drawLine(centroS_X - radio - 12, centroY + 13, centroS_X - radio - 2, centroY);
	    
	    // da color, rellena, luego nuevamente color negro y dibuja la circunferencia encima
	    //igual escribe el estado incial
	    
	    if (estadoActual.equals('s') && etiqueta.equals('n') ) {
	    	g2d.setColor(new Color(255, 243, 176));
		    g2d.fillOval(centroS_X - radio, centroY - radio, diametro, diametro);
	    }
	    
	    g2d.setColor(Color.BLACK);
	    g2d.drawOval(centroS_X - radio, centroY - radio, diametro, diametro);
	    g2d.drawString("s", centroS_X - 13, centroY+12);
		 
		 //Si estamos la etiqueta no es n ni v y esta en s, se pinta el arco, n significa que NO esta usando una transicion
	    if (!etiqueta.equals('n') && !etiqueta.equals('v') && estadoActual.equals('s')) {
	    	g2d.setColor(new Color(255, 243, 176));
	    }
	    else {
	    	 g2d.setColor(Color.BLACK);
	    }
	    //Dibuja el arco ese para el ciclo
		g2d.drawArc(centroS_X-27,centroY-2*radio-8,55,90,-10,200);
		g2d.drawLine(centroS_X+20, centroY-radio, centroS_X+28,centroY-15 );
		g2d.drawLine(centroS_X+28,centroY-15, centroS_X+35, centroY-radio );
	    
		
		 if (estadoActual.equals('f') && etiqueta.equals('n')) {
			 g2d.setColor(new Color(255, 243, 176));
			 g2d.fillOval(centroF_X - radio, centroY - radio, diametro, diametro);
		 }
		 g2d.setColor(Color.BLACK);
	    // dibuja el circulo mas grande del estado final
		 g2d.drawOval(centroF_X - radio, centroY - radio, diametro, diametro);   
	    // paradibujar el circulo interior hacemos un margen y dibujamos
		 int margen = 6;
		 g2d.drawOval(
	        centroF_X - radio + margen, 
	        centroY - radio + margen, 
	        diametro - (margen * 2), 
	        diametro - (margen * 2)
				 );
		 g2d.drawString("f", centroF_X - 10, centroY + 17);
		 
		 //Si estamos la etiqueta no es n ni v y esta en f, se pinta el arco, n significa que NO esta usando una transicion
		  if (!etiqueta.equals('n') && !etiqueta.equals('v') && estadoActual.equals('f')) {
		    g2d.setColor(new Color(255, 243, 176));
		  }
		  else {
		    g2d.setColor(Color.BLACK);
		  }
	  //Dibuja el arco ese para el ciclo
		 g2d.drawArc(centroF_X-27,centroY-2*radio-8,55,90,-10,200);
		 g2d.drawLine(centroF_X+20, centroY-radio, centroF_X+28,centroY-15 );
		 g2d.drawLine(centroF_X+28,centroY-15, centroF_X+35, centroY-radio );
	   
		 //si pasa al estado f con el vacio se pinta
		  if (etiqueta.equals('v')) {
			  g2d.setColor(new Color(255, 243, 176));
		  }
		  else {
			  g2d.setColor(Color.BLACK);
		  }
	   //Dibuja flecha que conecta un circulo con otro
		  
		 g2d.drawLine(centroS_X+radio, centroY, centroF_X-radio, centroY);
		 g2d.drawLine(centroF_X-radio-10, centroY-10, centroF_X-radio, centroY);
		 g2d.drawLine(centroF_X-radio-10, centroY+10, centroF_X-radio, centroY);
	   
		 g2d.setFont(new Font("SansSerif",Font.PLAIN, 15));
		 
	   //lo que lee / saca / mete en la pila del bulce de s
		 // lee a mete a y se pinta esa flecha
		 if (etiqueta.equals('a') && estadoActual.equals('s')) {
			 g2d.setColor(new Color(255, 243, 176));
		 }
		 else {
			 g2d.setColor(Color.BLACK);
		 }
		 g2d.drawString("a / \u03b5 / a", centroS_X-80, centroY-radio-10);
		 
		 //en el estado s, mete b y lee b y no saca nada se pinta esa
		 if (etiqueta.equals('b') && estadoActual.equals('s')) {
			 g2d.setColor(new Color(255, 243, 176));
		 }
		 else {
			 g2d.setColor(Color.BLACK);
		 }
		 g2d.drawString("b / \u03b5 / b", centroS_X+33, centroY-radio-10);
		 
		 
		 // saca a y lee a, se pinta esa transicion
		 if (etiqueta.equals('a') && estadoActual.equals('f')) {
			 g2d.setColor(new Color(255, 243, 176));
		 }
		 else {
			 g2d.setColor(Color.BLACK);
		 }
	 //lo que lee / saca / mete en la pila del bulce de f
		 g2d.drawString("a / a / \u03b5", centroF_X-80, centroY-radio-10);
		 
		 // si pasa esa transicion (el bucle) de f se pinta, saca b y lee b;
		 if (etiqueta.equals('b') && estadoActual.equals('f')) {
			 g2d.setColor(new Color(255, 243, 176));
		 }
		 else {
			 g2d.setColor(Color.BLACK);
		 }
		 g2d.drawString("b / b / \u03b5", centroF_X+33, centroY-radio-10);
		 
		 //si pasa al estado f con el vacio se pinta
		 if (etiqueta.equals('v')) {
			 g2d.setColor(new Color(255, 243, 176));
		 }
		 else {
			 g2d.setColor(Color.BLACK);
		 }
	   // lo mismo pero de la transicion s - > f
		 g2d.drawString("\u03b5 / \u03b5 / \u03b5", (getWidth() / 2)-20, centroY+20);
	   
	}
	public void setAnimacion() {
		System.out.println("animacion");

		if (!etiqueta.equals('n')) {
			System.out.println("timer");
			repaint();
			//this.etiqueta='n';
			
	        if (timerRepaint != null && timerRepaint.isRunning()) {
	            timerRepaint.stop();
	        }
	        // 600 ms = 0.6 segundos
	        timerRepaint = new Timer(600, e -> {
	        	this.etiqueta = 'n';
	        	repaint();
	        });
	        
	        timerRepaint.setRepeats(false); // Para que solo se ejecute UNA vez y no en bucle
	        timerRepaint.start();
		
			return;
		}
		//timer
		System.out.println("repindtando");
		repaint();
	}
	public void setEstados(Character estadoActual, Character etiqueta) {
		System.out.println("seteo estados");

		this.estadoActual=estadoActual;
		this.etiqueta=etiqueta;
	}
}
