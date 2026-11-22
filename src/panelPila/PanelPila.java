package panelPila;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class PanelPila extends JPanel {
	protected Timer timerRepaint;
	protected int numeroElementos=0;
	protected double margen = 20;
	protected ArrayList<Character> enPila=new ArrayList<Character>();

	protected void paintComponent(Graphics g) {
		numeroElementos=enPila.size();
		//enPila.add('a');
		//enPila.add('a');
		//enPila.add('a');
		//enPila.add('a');
		//enPila.add('a');
	    super.paintComponent(g);
	    Graphics2D g2d = (Graphics2D) g;
	    int tamanoFont=0;
	    double altura=0;
	    if (numeroElementos>0) {
	    	tamanoFont=350/numeroElementos;
	    	altura=(getHeight()/numeroElementos - margen/(numeroElementos/1.9));
	    }
	    
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2d.setStroke(new BasicStroke(2));
	    double FondoY = getHeight()/2 + getHeight()/2 - margen;
	    double centroIzquierdaX = getWidth() - getWidth();
	    double centroX=getWidth()/2;
	    double puntoXDerecha=getWidth()-margen;
	    double puntoXIzquierda=centroIzquierdaX + margen;
	   
	    Font font =new Font("SansSerif",Font.PLAIN, tamanoFont);
	    g2d.setFont(font);
	    FontMetrics fm = g2d.getFontMetrics(font);
	    int ancho=0;
	    if (!enPila.isEmpty()) ancho = fm.stringWidth(String.valueOf(enPila.getLast()));
	    int altoTexto = fm.getHeight();
	    for (int i=1; i<=numeroElementos;i++) {
	    	 //parte Baja
		    g2d.draw(new Line2D.Double(puntoXIzquierda,FondoY-((i-1)*altura), puntoXDerecha,FondoY-((i-1)*altura)));
		    //parte Alta
		    g2d.draw(new Line2D.Double(puntoXIzquierda,FondoY-((i)*altura), puntoXDerecha, FondoY-((i)*altura)));
		    
		    //laterales
		    g2d.draw(new Line2D.Double(puntoXIzquierda, FondoY-((i-1)*altura) ,centroIzquierdaX + margen,FondoY-((i)*altura)));
		    
		    g2d.draw(new Line2D.Double(puntoXDerecha ,FondoY-((i-1)*altura) ,puntoXDerecha,FondoY-((i)*altura)));
		    
		    g2d.drawString(String.valueOf(enPila.get(i-1)),(float)(centroX - ancho/2.0),(float)(((FondoY-((i)*altura))+(FondoY-((i-1)*altura)))/2+ ancho/2.0));
	    }
	     
	  }
	
	public void setAnimacion() {
		repaint();
	}
	
	public void agregarElemento(Character letra) {
		enPila.add(letra);
		numeroElementos++;
	}
	public void eliminarElemento() {
		enPila.remove(enPila.size()-1);
	}
	public void reiniciar() {
		enPila.clear();
		numeroElementos=0;
		repaint();
	}
	

}
