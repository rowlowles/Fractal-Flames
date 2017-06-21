
package fractalflames;
import java.awt.*;



public class doublePoints {
    double x,y;
    float c;
    
    
    
    public doublePoints(double xIn, double yIn, float cIn){
        x = xIn;
        y = yIn;
        c = cIn;
    }
    
    public void draw (Graphics2D g, double logDensity){
        
        
        //g.setColor(Color.getHSBColor(c,(float)logDensity , (float) logDensity));
        g.setColor(Color.getHSBColor(c,(float)1 , (float) logDensity));
        
        
        
        g.drawLine((int)x, (int)y, (int)x, (int)y);
    }
}
