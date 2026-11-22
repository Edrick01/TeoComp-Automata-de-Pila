package panelPila;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;
import javax.swing.Timer;

public class PanelPila extends JPanel {
	protected Timer timerRepaint;
	protected int numeroElementos=7;
	protected int margen = 20;
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    Graphics2D g2d = (Graphics2D) g;
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2d.setStroke(new BasicStroke(2));
	    int FondoY = getHeight()/2 + getHeight()/2 - margen;
	    int centroIzquierdaX = getWidth() - getWidth();
	    int puntoXDerecha=getWidth()-margen;
	    int puntoXIzquierda=centroIzquierdaX + margen;
	    int altura=(getHeight()/numeroElementos -margen);
	    for (int i=1; i<=numeroElementos;i++) {
	    	 //parte Baja
		    g2d.drawLine(puntoXIzquierda,FondoY-((i-1)*altura), puntoXDerecha,FondoY-((i-1)*altura));
		    //parte Alta
		    g2d.drawLine(puntoXIzquierda,FondoY-((i)*altura), puntoXDerecha, FondoY-((i)*altura));
		    
		    //laterales
		    g2d.drawLine(puntoXIzquierda, FondoY-((i-1)*altura) ,centroIzquierdaX + margen,FondoY-((i)*altura));
		    
		    g2d.drawLine(puntoXDerecha ,FondoY-((i-1)*altura) ,puntoXDerecha,FondoY-((i)*altura));
		    
	    	
	    }
	   
	    
	    
	}
	

}
