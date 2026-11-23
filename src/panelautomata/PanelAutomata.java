package panelautomata;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
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
	protected Character estadoActual='n';
	protected Character etiqueta='n';
	protected Color estadoSuave= new Color (197, 217, 197);
	protected Color estadoFuerte= new Color (85, 122, 88);
	protected Color errorSuave = new Color (229, 191, 197);
	protected Color errorFuerte = new Color (152, 77, 88);
	protected boolean enError=false;
	
	protected Color estadoS0 = new Color (247, 217, 175);
	protected Color estadoS1 = new Color (242, 160, 45);
	protected Color estadoF0 = new Color (154, 230, 154);
	protected Color estadoF1= new Color (31, 222, 31);
	
	private Timer timerFlecha;
    private Timer timerRespiracion;
    private double fase = 0.0;
	protected Timer timerRepaint;

	public PanelAutomata() {
        // Timer continuo para el efecto de respiración (~25 FPS)
        timerRespiracion = new Timer(40, e -> {
            fase += 0.15;
            if (fase > Math.PI * 2) {
                fase -= Math.PI * 2; // Evita que el número crezca infinito
            }
            repaint();
        });
        timerRespiracion.start();
    }
	
	@Override
	protected void paintComponent(Graphics g) {
		
	    super.paintComponent(g);
	    Graphics2D g2d = (Graphics2D) g;
	    g2d.setFont(new Font("SansSerif",Font.PLAIN, 50));
	    FontMetrics fm = g2d.getFontMetrics(g2d.getFont());
	    int anchoS = fm.stringWidth("s");
	    int alto=fm.getHeight();
	    int anchoF = fm.stringWidth("f");
	    // Dimensiones y posiciones relativas al centro del panel
	    int radio = 30;
	    int diametro = radio * 2;
	    int centroY = getHeight() / 2;

	    // Posición del círculo 's' (izquierda) y 'f' (derecha)
	    int centroS_X = (getWidth() / 2) - 120;
	    int centroF_X = (getWidth() / 2) + 120;
	    int margen = 6;

	    // para que no se vea pixeleado
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2d.setStroke(new BasicStroke(2)); // Grosor de la línea



	    // Dibuja las dos rectas esas del estado inicial
	    g2d.drawLine(centroS_X - radio - 12, centroY - 13, centroS_X - radio - 2, centroY);
	    g2d.drawLine(centroS_X - radio - 12, centroY + 13, centroS_X - radio - 2, centroY);
	    
	    
	    dibujarCirunferencia(g2d,'s', centroS_X - radio, centroY-radio, diametro, anchoS,alto);
	    dibujarCirunferencia(g2d,'f', centroF_X-radio, centroY-radio, diametro, anchoF+3,alto+4);
		 
		g2d.drawOval(
	        centroF_X - radio + margen, 
	        centroY - radio + margen, 
	        diametro - (margen * 2), 
	        diametro - (margen * 2)
				 );
	    
		colorearEtiquetas(g2d,'a','s',centroS_X-80, centroY-radio-10);
	    colorearEtiquetas(g2d,'b','s',centroS_X+33, centroY-radio-10);
	    colorearEtiquetas(g2d,'a','f',centroF_X-80, centroY-radio-10);
	    colorearEtiquetas(g2d,'b','f',centroF_X+33, centroY-radio-10);
	    colorearEtiquetas(g2d,'v','v',(getWidth() / 2) -20, centroY+20);
	    
	    dibujarTransicion(g2d,"ab",'s', centroS_X, centroY,radio);
	    dibujarTransicion(g2d,"ab",'f', centroF_X, centroY,radio);
	    
	    dibujartransicionSF(g2d,centroS_X, centroY,radio,centroF_X);
	    

	    //pinta los estados
	    //rellenarCirculos(g2d,radio,diametro,centroY,centroS_X,centroF_X);
	    g2d.setColor(Color.BLACK);
	    //pintarCircunferencia(g2d,radio,diametro,centroY,centroS_X,centroF_X);
	    //g2d.setColor(Color.BLACK);
	    //colorearEtiquetasyArcos(g2d,radio,diametro,centroY,centroS_X,centroF_X);
	   
		
	}
	
	
	public void dibujarCirunferencia(Graphics2D g2d,Character estado, int x, int y, int diametro, int letraC, int letraAlt) {
		double pulso = (Math.sin(fase) + 1.0) / 2.0;
		 int alpha = (int) (80 + (pulso * 175));
		if (estadoActual.equals(estado)) {
			if (enError) {
				g2d.setColor(cambiarOpacidad(errorSuave, alpha)); 
			}
			else {
				g2d.setColor(cambiarOpacidad(estadoSuave, alpha));
			}
    
            g2d.fillOval(x, y, diametro, diametro);

            
            float grosor = 2.0f + (float) (pulso * 3.5f);
            g2d.setStroke(new BasicStroke(grosor));
			if (enError) {
				g2d.setColor(errorFuerte); 
			}
			else {
				g2d.setColor(estadoFuerte); 

			}
            g2d.drawOval(x, y, diametro, diametro);
        
		} else {
            // Estado normal
            g2d.setColor(Color.WHITE);
            g2d.fillOval(x, y, diametro, diametro);

            g2d.setStroke(new BasicStroke(2.0f));
            g2d.setColor(Color.BLACK);
            g2d.drawOval(x, y, diametro, diametro);
        }

        // Restaurar trazo normal y dibujar la letra del estado
        g2d.setStroke(new BasicStroke(2.0f));
        g2d.setColor(Color.BLACK);
        g2d.drawString(String.valueOf(estado), x + (diametro / 2) - letraC/2, y - (diametro / 2) + letraAlt+10);
	}
	
	
	// REPARAR LAS ANIMACIONES
	public void colorearEtiquetas(Graphics2D g2d,Character etiqueta,Character estado, int x, int y) {
		g2d.setFont(new Font("SansSerif",Font.PLAIN, 15));

		if (this.etiqueta.equals(etiqueta) && estado.equals(this.estadoActual)) {
            double pulso = (Math.sin(fase*2.0) + 1.0) / 2.0;
            float grosor =13.0f + (float) (pulso*2.0);
            g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, grosor));
			if (enError) {
				g2d.setColor(errorFuerte); 
			}
			else {
				g2d.setColor(estadoFuerte); 

			}
            
            if (estado.equals('s')) {
    			//System.out.println("estados: " + estado + "<- estado de la etiqueta " + estadoActual+ "<-EstadoActual");

    			g2d.drawString(String.valueOf(etiqueta)+" / \u03b5 / "+String.valueOf(etiqueta), x, y);
            }
            else if (estado.equals('f')){
    			//System.out.println("estados: " + estado + "<- estado de la etiqueta " + estadoActual+ "<-EstadoActual");

        		g2d.drawString(String.valueOf(etiqueta)+" / "+ String.valueOf(etiqueta)+" / \u03b5", x, y);
            }
		
		}
		else if (this.etiqueta.equals(etiqueta) && estado.equals('v')) {
            double pulso = (Math.sin(fase*2.0) + 1.0) / 2.0;
            float grosor =13.0f + (float) (pulso*2.0);
            g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, grosor));
			if (enError) {
				g2d.setColor(errorFuerte); 
			}
			else {
				g2d.setColor(estadoFuerte); 
			}
			g2d.drawString("\u03b5 / \u03b5 / \u03b5", x, y);
		}
		else {
			
			g2d.setColor(Color.BLACK);
            if (estado.equals('s')) {
    			g2d.drawString(String.valueOf(etiqueta)+" / \u03b5 / "+String.valueOf(etiqueta), x, y);
            }
            else if (estado.equals('f')){
        		g2d.drawString(String.valueOf(etiqueta)+" / "+ String.valueOf(etiqueta)+" / \u03b5", x, y);
            }
            else if (estado.equals('v')) {
        		g2d.drawString("\u03b5 / \u03b5 / \u03b5", x, y);
            }
		}
		
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(2));
		 
	}
	
	
	public void dibujarTransicion(Graphics2D g2d,String etiquetas,Character estado, int x, int y, int radio  ) {
		Character aux = etiquetas.charAt(0);
		Character aux2 = etiquetas.charAt(1);
		if (estado.equals(estadoActual) && (aux.equals(this.etiqueta) || aux2.equals(this.etiqueta))) {
			//System.out.println("estados: trancisiondibujo  " + estado + "<- estado de la etiqueta " + estadoActual+ "<-EstadoActual");
			double pulso = (Math.sin(fase*2.0) + 1.0) / 2.0;
            float grosor = 2.0f + (float) (pulso * 2.4f);
            g2d.setStroke(new BasicStroke(grosor));
			if (enError) {
				g2d.setColor(errorFuerte); 
			}
		}
		
		g2d.setColor(Color.BLACK);
		g2d.drawArc(x-27,y-2*radio-8,55,90,-10,200);
		g2d.drawLine(x+20, y-radio, x+28,y-15 );
		g2d.drawLine(x+28, y-15, x+35,y-radio );
		g2d.setStroke(new BasicStroke(2));

	}
	
// x1 centro S
	public void dibujartransicionSF(Graphics2D g2d,int x1, int y,int radio,int x2) {
		if (etiqueta.equals('v')) {
            double pulso = (Math.sin(fase*2.0) + 1.0) / 2.0;
            float grosor = 2.0f + (float) (pulso * 2.4f);
            g2d.setStroke(new BasicStroke(grosor));
			if (enError) {
				g2d.setColor(errorFuerte); 
			}	
		}
		g2d.setColor(Color.BLACK);
		g2d.drawLine(x1+radio,y, x2-radio, y);
		g2d.drawLine(x2-radio-10, y-10, x2-radio, y);
		g2d.drawLine(x2-radio-10, y+10, x2-radio, y);
		g2d.setStroke(new BasicStroke(2));

	}

	
	
	public void setAnimacion() {
		//System.out.println("animacion");
		if (timerFlecha != null && timerFlecha.isRunning()) {
            timerFlecha.stop();
        }
		if (!timerRespiracion.isRunning()) {
			timerRespiracion.start();
        }
        fase = 0.0;

		if (!etiqueta.equals('n')) {
			//System.out.println("timer");
			repaint();
			//this.etiqueta='n';
			
			timerFlecha = new Timer(1000, e -> {
                this.etiqueta = 'n';
                //timerRespiracion.start(); // Empieza a respirar el estado destino
                repaint();
            });
            timerFlecha.setRepeats(false);
            timerFlecha.start();
         
			
	        /*if (timerRepaint != null && timerRepaint.isRunning()) {
	            timerRepaint.stop();
	        }
	        // 600 ms = 0.6 segundos
	        timerRepaint = new Timer(600, e -> {
	        	this.etiqueta = 'n';
	        	repaint();
	        });
	        
	        timerRepaint.setRepeats(false); // Para que solo se ejecute UNA vez y no en bucle
	        timerRepaint.start();*/
		
		}
		else {
			System.out.println("repindtando");
			//timerRespiracion.start();
			repaint();
		}
	}
	
	public void setEstados(Character estadoActual, Character etiqueta) {
		System.out.println("seteo estados");

		this.estadoActual=estadoActual;
		this.etiqueta=etiqueta;
	}
	
	public Color cambiarOpacidad(Color color, int alpha) {
	    return new Color(
	        color.getRed(),
	        color.getGreen(),
	        color.getBlue(),
	        alpha
	    );
	}
	
	public void detenerAnimaciones() {
        if (timerFlecha != null) timerFlecha.stop();
        timerRespiracion.stop();
        estadoActual = 'n';
        etiqueta = 'n';
        repaint();
    }
	public void setEnError(boolean error) {
		enError=error;
	}
	//mejorar si es posible
	/*public void rellenarCirculos(Graphics2D g2d,int radio,int diametro,int centroY,int centroS_X,int centroF_X ) {
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


	}*/
	
	
	
	/*public void dibujarArcoEstadoS(Graphics2D g2d,int radio,int diametro,int centroY,int centroS_X,int centroF_X) {
	g2d.drawArc(centroS_X-27,centroY-2*radio-8,55,90,-10,200);
	g2d.drawLine(centroS_X+20, centroY-radio, centroS_X+28,centroY-15 );
	g2d.drawLine(centroS_X+28,centroY-15, centroS_X+35, centroY-radio );
}
public void dibujarArcoEstadoF(Graphics2D g2d,int radio,int diametro,int centroY,int centroS_X,int centroF_X) {
	g2d.drawArc(centroF_X-27,centroY-2*radio-8,55,90,-10,200);
	g2d.drawLine(centroF_X+20, centroY-radio, centroF_X+28,centroY-15 );
	g2d.drawLine(centroF_X+28,centroY-15, centroF_X+35, centroY-radio );
}*/
	
	
	//mejorar si es posble
	/*public void pintarCircunferencia(Graphics2D g2d,int radio,int diametro,int centroY,int centroS_X,int centroF_X ) {
	    
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
	*/
	
	/*
	 * 
	 * 	//implementar
	public void colorearEtiquetasyArcos(Graphics2D g2d,int radio,int diametro,int centroY,int centroS_X,int centroF_X) {
		
		g2d.setFont(new Font("SansSerif",Font.PLAIN, 15));
		g2d.setColor(Color.BLACK);
		g2d.drawString("a / \u03b5 / a", centroS_X-80, centroY-radio-10);
		g2d.drawString("b / \u03b5 / b", centroS_X+33, centroY-radio-10);
		g2d.drawString("a / a / \u03b5", centroF_X-80, centroY-radio-10);
		g2d.drawString("b / b / \u03b5", centroF_X+33, centroY-radio-10);
		g2d.drawString("\u03b5 / \u03b5 / \u03b5", (getWidth() / 2)-20, centroY+20);
		 
		//arco estado S 
		dibujarArcoEstadoS(g2d,radio,diametro,centroY,centroS_X,centroF_X);
		dibujarArcoEstadoF(g2d,radio,diametro,centroY,centroS_X,centroF_X);
		dibujartransicionSF(g2d,radio,diametro,centroY,centroS_X,centroF_X);
		
		 
		   //lo que lee / saca / mete en la pila del bulce de s
			 // lee a mete a y se pinta esa flecha
		 if(estadoActual.equals('s')) {
			 g2d.setColor(new Color(255, 243, 176));
			 if (etiqueta.equals('a')){
				 dibujarArcoEstadoS(g2d,radio,diametro,centroY,centroS_X,centroF_X);
				 g2d.drawString("a / \u03b5 / a", centroS_X-80, centroY-radio-10);
			 }
			 else if(etiqueta.equals('b')) {
				 dibujarArcoEstadoS(g2d,radio,diametro,centroY,centroS_X,centroF_X);
				 g2d.drawString("b / \u03b5 / b", centroS_X+33, centroY-radio-10);
			 }
		 }
		 else if (estadoActual.equals('f')) {
			 g2d.setColor(new Color(255, 243, 176));
			 if (etiqueta.equals('a')){
				dibujarArcoEstadoF(g2d,radio,diametro,centroY,centroS_X,centroF_X);
				g2d.drawString("a / a / \u03b5", centroF_X-80, centroY-radio-10);
			 }
			 else if (etiqueta.equals('b')) {
				 dibujarArcoEstadoF(g2d,radio,diametro,centroY,centroS_X,centroF_X);
				 g2d.drawString("b / b / \u03b5", centroF_X+33, centroY-radio-10);
			 }
			 
		 }
		 if(etiqueta.equals('v')) {
			 g2d.setColor(new Color(255, 243, 176));
			 dibujartransicionSF(g2d,radio,diametro,centroY,centroS_X,centroF_X);
			 g2d.drawString("\u03b5 / \u03b5 / \u03b5", (getWidth() / 2)-20, centroY+20);
		 }
		  
	}
	 */
	 
}
