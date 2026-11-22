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
	//estados 's' y 'f'
	//etiquetas 'a' 'b' 'v' y 'n'
	//si 'n' esta activa es que esta en "reposo" es decir, lo que se colorea es el estado actual
	//si no es 'n' se colorea la flecha
	protected Character estadoActual='s';
	protected Character etiqueta='n';
	protected Color estadoS0 = new Color (247, 217, 175);
	protected Color estadoS1 = new Color (242, 160, 45);
	protected Color estadoF0 = new Color (154, 230, 154);
	protected Color estadoF1= new Color (31, 222, 31);
	
	protected Color error = new Color (255, 0, 0);
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
	    
	    //pinta los estados
	    rellenarCirculos(g2d,radio,diametro,centroY,centroS_X,centroF_X);
	    g2d.setColor(Color.BLACK);
	    pintarCircunferencia(g2d,radio,diametro,centroY,centroS_X,centroF_X);
	    
		 
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
	//mejorar si es posible
	public void rellenarCirculos(Graphics2D g2d,int radio,int diametro,int centroY,int centroS_X,int centroF_X ) {
		g2d.setColor(estadoS0);
	    g2d.fillOval(centroS_X - radio, centroY - radio, diametro, diametro);
	    g2d.setColor(estadoF0);
		g2d.fillOval(centroF_X - radio, centroY - radio, diametro, diametro);
		
	    if (etiqueta.equals('n')) {
			if (estadoActual.equals('s')) {
		    	g2d.setColor(estadoS1);
			    g2d.fillOval(centroS_X - radio, centroY - radio, diametro, diametro);
		    }
		    else if (estadoActual.equals('f')) {
				g2d.setColor(estadoF1);
				g2d.fillOval(centroF_X - radio, centroY - radio, diametro, diametro);
		    }
	    }


	}
	
	//mejorar si es posble
	public void pintarCircunferencia(Graphics2D g2d,int radio,int diametro,int centroY,int centroS_X,int centroF_X ) {
	    
		g2d.drawOval(centroS_X - radio, centroY - radio, diametro, diametro);
	    g2d.drawString("s", centroS_X - 13, centroY+12); 
	    
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
		
		if (etiqueta.equals('n')) {
			g2d.setStroke(new BasicStroke(4));
			 if (estadoActual.equals('s')){
				g2d.drawOval(centroS_X - radio, centroY - radio, diametro, diametro);
				g2d.drawString("s", centroS_X - 13, centroY+12);
			 }
			 else if(estadoActual.equals('f')) {
				 g2d.drawOval(centroF_X - radio, centroY - radio, diametro, diametro);  
					g2d.drawOval(
				        centroF_X - radio + margen, 
				        centroY - radio + margen, 
				        diametro - (margen * 2), 
				        diametro - (margen * 2)
							 );
					g2d.drawString("f", centroF_X - 10, centroY + 17);
			 }
			 g2d.setStroke(new BasicStroke(2));
		 }
	}
	
	//implementar
	public void colorearEtiquetas() {
		
	}
	public void colorearFlechas() {
		
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
