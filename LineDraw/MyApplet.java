import java.awt.*;
import java.applet.*;
import java.util.*;

public class MyApplet extends Applet  {
    Dimension dmDown;   
    Dimension dmUp;     
    Dimension dmPrev;   
    boolean bDrawing;   
	Vector lines;       
    
    public String getAppletInfo( ) {
        return "Name: LineDraw";
  	}
	
    public void init() {  		 
        bDrawing = false;
        lines = new Vector();
    }
	
    public void paint(Graphics g) {	 
        Dimension dimAppWndDimension = getSize();
        setBackground(Color.yellow);
        g.setColor(Color.black);
  	    g.drawRect(0, 0, 
	    dimAppWndDimension.width  - 1, 
	    dimAppWndDimension.height - 1);    
	    for (int i=0; i < lines.size(); i++) 	 
		{
	        Rectangle p = (Rectangle)lines.elementAt(i); 
            g.drawLine(p.width, p.height,  p.x, p.y);
            g.drawString("<" + p.width+","+p.height+">",p.width,p.height);
            g.drawString("<"+p.x+","+p.y+">",p.x,p.y);
		}
   	    bDrawing = false;
  	}
  
    public boolean mouseDown(Event evt,int x,int y)  { 
    	if (evt.clickCount > 1)    { 
    	    lines.removeAllElements(); 
	        repaint();
	        return true;
    	}
    	
        dmDown = new Dimension(x, y); 
        dmPrev = new Dimension(x, y);
        bDrawing = false;
        return true;
    }
	
    public boolean mouseUp(Event evt, int x, int y){
	    if(bDrawing) {
            dmUp = new Dimension(x, y);
            lines.addElement(   
                new Rectangle(dmDown.width, 
                dmDown.height, x, y)); 
            repaint();
            bDrawing = false; 
        }
        return true;
	}
  
    public boolean mouseDrag(Event evt, int x, int y)  {
        Graphics g = getGraphics();
        bDrawing = true;
        g.setColor(Color.yellow); 
        g.drawLine(dmDown.width,dmDown.height,dmPrev.width,dmPrev.height);
        g.setColor(Color.black); 	
	    g.drawLine(dmDown.width, dmDown.height, x, y);
	    dmPrev = new Dimension(x, y); 
        return true;
    }
 
    public boolean mouseMove(Event evt, int x, int y)  {
        bDrawing = false; 	
 	    return true;
	}
}

