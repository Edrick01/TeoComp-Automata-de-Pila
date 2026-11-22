package panelPila;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;
import javax.swing.Timer;

public class PanelPila extends JPanel {
	protected Timer timerRepaint;
	protected int numeroElementos=1;
	protected int margen = 30;
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    Graphics2D g2d = (Graphics2D) g;
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2d.setStroke(new BasicStroke(2));
	    int centroFondoY = getHeight()/2 + getHeight()/2 -10;
	    int centroIzquierdaX = getWidth() - getWidth();
	    int base=getWidth()-margen;
	    int Altura=getHeight()/numeroElementos+margen;
	    g2d.drawLine(centroIzquierdaX + margen,centroFondoY * numeroElementos, base,centroFondoY * numeroElementos);
	    g2d.drawLine(centroIzquierdaX + margen, ,CentroIzquierdaX + margen,)
	    
	}
	

}
