
package fractalflames;

import java.awt.*;
import javax.swing.*;


public class FractalFlames extends JFrame  {

    static int screenHeight = 1700, screenWidth = 1700;
    //Define how big the screen will be
    static int numPoints = 1500000;
    //How many points will be drawn to the screen. 
    static int numCalcs = 20;
    static int numFunctions = 1;
    static int finalFunc = 5;
    //How many times a random calculation will be applied to a point, how many random functions there are
    //and what the final function will be. The final function defines what the shape will look like.
    
    static int yMax = 7, yMin = -7;
    static int xMax = 7, xMin = -7;
    static int xScale = (xMax - xMin), yScale = (yMax - yMin);
    //The screen will graph points from (xMin, yMin) to (xMax, yMax)
    
    static int[][] pointDensity = new int[screenHeight+1][screenWidth+1];
    static double[][] logDensity = new double[screenHeight+1][screenWidth+1];
    //The point density and log density determine how bright a point will appear
    
    static doublePoints[] randomPoints = new doublePoints[numPoints];
    static doublePoints[] pointsToBeDrawn = new doublePoints[numPoints];
    //An array for the random points, and the points to be drawn to the screen.
    //No calculations are applied to the pointsToBeDrawn array.
    
    
    //Affine functions color bank
    static float af1ColorVal = (float).1;
    static float af2ColorVal = (float).4;
    static float af3ColorVal = (float).7;
    static float af4ColorVal = (float).9;
    static float af5ColorVal = (float).8;
    
    //Final functions color bank
    static float f0ColorVal = (float).1;
    static float f1ColorVal = (float).2;
    static float f2ColorVal = (float).3;
    static float f3ColorVal = (float).4;
    static float f4ColorVal = (float).5;
    static float f5ColorVal = (float).6;
    static float f6ColorVal = (float).7;
    static float f7ColorVal = (float).8;
    static float f8ColorVal = (float).9;
    static float f9ColorVal = (float)1;
    static float f10ColorVal =(float)-.1;

    //Adjust these values to get different colors on the screen.
    
    

   
    public static void main(String[] args) {
        FractalFlames screen = new FractalFlames();
        
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.setSize(screenHeight, screenWidth);
        screen.setBackground(Color.black);
        screen.setVisible(true);
        //Create the screen. It is now ready to have points drawn on it.
        
        
        
        for (int i = 0; i < numPoints; i++) {
           randomPoints[i] = new doublePoints(1,1, (float).1);
           
           randomPoints[i].x = (Math.random()*(xMax-xMin)-(xMax/2));
           randomPoints[i].y = (Math.random()*(yMax-yMin)-(yMax/2));
           float colorVal = (float) Math.random();
           randomPoints[i].c = colorVal;
           //Each point is given a random x and y value, along with a random starting color.           
           
           performCalcs(i);
           //Perform the calculations on the randomly generated poit. 
  
        }
        
        
        screen.repaint();
        //Draw the image.
        
    }
    
    public void paint(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0,0, screenWidth, screenHeight);
        //Paint the background black.
        
        Graphics2D G = (Graphics2D)g;
        
        
        for (int z = 0; z < numPoints; z++) {
            
            logDensity[(int)getXfinal(z)][(int)getYfinal(z)] = Math.log10(pointDensity[(int)getXfinal(z)][(int)getYfinal(z)]);
            
            
            pointsToBeDrawn[z].draw(G,logDensity[(int)getXfinal(z)][(int)getYfinal(z)] );
            
            
        }
    }
   
    public static void performCalcs(int i){
        
        int randFunc;
        for (int j = 0; j < numCalcs; j++) {
            
            randFunc = (int) Math.ceil(Math.random()*numFunctions);
            affineCall(randFunc, i);//Change back to functionCall
            
            //System.out.println(getX(i)+" " + getY(i));
            NaNHandlingX(i);
            NaNHandlingY(i);
            
        }
        functionCall(finalFunc, i);
        
        pointsToBeDrawn[i] = new doublePoints(1,1,(float).1);
        
        pointsToBeDrawn[i].x = (screenHeight / (xMax - xMin) ) * (randomPoints[i].x - xMin);
        pointsToBeDrawn[i].y = (screenHeight/(yMax - yMin)) * (yMax - randomPoints[i].y);
        pointsToBeDrawn[i].c = randomPoints[i].c;
        
        checkBounds(i);
        
        
        //System.out.println(getX(i)+" " + getY(i));
        
        pointDensity[(int)pointsToBeDrawn[i].x][(int)pointsToBeDrawn[i].y] ++;
        
        
        
    }
    
    public static void functionCall(int funcNumb, int i){
        
        
        if (funcNumb == 0){
            function0(i);// Change back to function0
            
        }
        
        else if (funcNumb == 1){
            function1(i); // x+1/2
        }
        
        else if (funcNumb == 2){
           function2(i); // y + 1/2
        }
        
        else if (funcNumb == 3){
           function3(i); //sin x
        }
        
        else if (funcNumb == 4){
           function4(i); //1/r^2 * x
        }
        
        else if (funcNumb == 5){
           function5(i); 
        }
        
        else if (funcNumb == 6){
           function6(i); 
        }
        
        else if (funcNumb == 7){
           function7(i); 
        }
        
        else if (funcNumb == 8){
           function8(i); 
        }
        
        else if (funcNumb == 9){
           function9(i); 
        }
        
        else if (funcNumb == 10){
           function10(i); 
        }
        
    }
     
    public static void affineCall (int affNumb, int i){
        
        if (affNumb == 0){
            affineFunc1(i);
        }
        
        else if (affNumb == 1){
            affineFunc2(i);
        }
        
        else if (affNumb == 2){
            affineFunc3(i);
        }
        
        else if (affNumb == 3){
            affineFunc4(i);
        }
        
        else if (affNumb == 4){
            affineFunc5(i);
        }
    }
    
    public static double getX (int i){
        return randomPoints[i].x;
    }
    public static double getY (int i){
        return randomPoints[i].y;
    }
    
    public static double getXfinal (int i){
        return pointsToBeDrawn[i].x;
    }
    public static double getYfinal (int i){
        return pointsToBeDrawn[i].y;
    }
     
    public static void checkBounds (int i){
        if (pointsToBeDrawn[i].x > screenHeight ||pointsToBeDrawn[i].x < 0 ){
            outOfBoundsX(i);
        }
        
        if (pointsToBeDrawn[i].y > screenHeight || pointsToBeDrawn[i].y < 0){
            outOfBoundsY(i);
        }
    }
    public static void outOfBoundsX(int i){
        if (pointsToBeDrawn[i].x >screenWidth){
            pointsToBeDrawn[i].x = screenWidth-1;
        }
        else if (pointsToBeDrawn[i].x <0){
            pointsToBeDrawn[i].x = 0;
        }
        
    }
    public static void outOfBoundsY(int i){
         if (pointsToBeDrawn[i].y >screenHeight){
            pointsToBeDrawn[i].y = screenHeight-1;
        }
         else if (pointsToBeDrawn[i].y <700){
            pointsToBeDrawn[i].y = 0;
        }
    }
    
    public static void NaNHandlingX(int i){
        if (getX(i) != getX(i)){
            randomPoints[i].x = randomPoints[i-1].x;
            
        }        
    }
    public static void NaNHandlingY(int i){
        if (getY(i) != getY(i)){
            randomPoints[i].y = randomPoints[i-1].y;  
        }
    }
    
    
    
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    //                               FUNCTION BANK
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    public static void function0(int i){
        
        randomPoints[i].c = (randomPoints[i].c + f0ColorVal)/2;
        
        double x = getX(i);
        double y = getY(i);
        double r = getR(i);
        
        
        
        randomPoints[i].x = x*Math.sin(Math.toRadians(Math.pow(r,2))) - y*Math.cos(Math.toRadians(Math.pow(r,2)));
        randomPoints[i].y = x*Math.cos(Math.toRadians(Math.pow(r,2))) + y*Math.sin(Math.toRadians(Math.pow(r,2)));
        
        
    }
    
    public static void function1(int i){
        
        randomPoints[i].c = (randomPoints[i].c + f1ColorVal)/2;
        
        double r = getR(i);
        double theta = getTheta(i);
        
        randomPoints[i].x = (theta/Math.PI)*(xScale-1);
        randomPoints[i].y = r-yMax;
    }
    
    public static void function2(int i){
        
        randomPoints[i].c = (randomPoints[i].c + f2ColorVal)/2;
        
        double r = getR(i);
        double theta = getTheta(i);
        
        randomPoints[i].x = r * Math.sin(theta + r);
        randomPoints[i].y = r * Math.cos(theta - r);
    }
    
    public static void function3(int i){
        
        randomPoints[i].c = (randomPoints[i].c + f3ColorVal)/2;
        
        double x = getX(i);
        double y = getY(i);
        
        
        randomPoints[i].x = xMax*Math.sin(x)*Math.cos(y);
        randomPoints[i].y = Math.sin(y)/Math.cos(x);
        //System.out.println(getX(i)+" " + getY(i));
    }
     
    public static void function4(int i){
        
        randomPoints[i].c = (randomPoints[i].c + f4ColorVal)/2;
        double q = Math.floor(Math.random() * 3 -1);
        if (q == 0){
            q = 1;
        }
        double  oneOverR = 1 / Math.pow( getR(i),2);
        double x = getX(i);
        double y = getY(i);
        
        randomPoints[i].x = xMax * x * oneOverR;
        randomPoints[i].y = yMax * y * q* oneOverR;
    }
    
    public static void function5 (int i ){
        
        randomPoints[i].c = (randomPoints[i].c + f5ColorVal)/2;
        
        double x = getX(i);
        double y = getY(i);
        double r = getR(i);
        
        
        randomPoints[i].x = (x * Math.sin(Math.pow(r,2)) - y*Math.cos(Math.pow(r, 2)));
        randomPoints[i].y = (x * Math.sin(Math.pow(r,2)) + y*Math.cos(Math.pow(r, 2)));
    }
    
    public static void function6 (int i){
    
        
        randomPoints[i].c = (randomPoints[i].c + f6ColorVal)/2;    
        
        double  oneOverR = 1 /  getR(i);
        double x = getX(i);
        double y = getY(i);
        
        
        
        randomPoints[i].x = ((x - y)  * (x + y)) * oneOverR;
        randomPoints[i].y = 2 * x * y * oneOverR;
}
    
    public static void function7 (int i){
        
        
        randomPoints[i].c = (randomPoints[i].c + f7ColorVal)/2;    
        
        double x = getX(i);
        double y = getY(i);
        double r = getR(i);
        
        double theta = getTheta(i);
        
        randomPoints[i].x = r * Math.sin(theta * r);
        randomPoints[i].y = r * -Math.cos(theta * r);
        
    }
    
    public static void function8 (int i){
        
        randomPoints[i].c = (randomPoints[i].c + f8ColorVal)/2;    
        
       
        double r = getR(i);
        double theta = getTheta(i);
        
        randomPoints[i].x = xScale*(theta/Math.PI * (Math.sin(Math.toRadians(Math.PI * r))));
        randomPoints[i].y = yScale*(theta/Math.PI * (Math.cos(Math.toRadians(Math.PI * r))));
        
    }
    
    public static void function9(int i){
        
        randomPoints[i].c = (randomPoints[i].c + f9ColorVal)/2;
        
        double x = getX(i);
        double y = getY(i);
        double r = getR(i);
        
        //System.out.println(r+1);
        
        randomPoints[i].x = (2/(r-.1))*x;
        randomPoints[i].x = (2/(r-.1))*y;
    }
    
    public static void function10(int i){
        randomPoints[i].c = (randomPoints[i].c + f10ColorVal)/2;
        
        double r = getR(i);
        double theta = getTheta(i);
        
        randomPoints[i].x = r * Math.sin(Math.toDegrees(theta + r));
        randomPoints[i].y = r * Math.cos(Math.toDegrees(theta - r));
    }
    
    public static double getR(int i){
        return Math.sqrt(Math.pow(getX(i),2) + Math.pow(getY(i), 2));
    }
    
    public static double getTheta (int i){
        
        return Math.atan(getY(i)/getX(i));
    }
    
    
    
    //x = ax + by + c
    //y = ex + fy + d
    
    
    public static void affineFunc1 (int i){
        double[] affine1 = new double[]{1,.01,0, 1,.01,0};
        randomPoints[i].x = affine1[0]*getX(i) + affine1[1]*getY(i) + affine1[2];
        randomPoints[i].y = affine1[3]*getX(i) + affine1[4]*getY(i) + affine1[5];
        
        
        randomPoints[i].c = (randomPoints[i].c + af1ColorVal)/2;
        
    }
    
    public static void affineFunc2 (int i){
        double[] affine2 = new double[]{Math.sin(getX(i)),.3,2,    Math.cos((getY(i))),.3,2};
        randomPoints[i].x = affine2[0]*getX(i) + affine2[1]*getY(i) + affine2[2];
        randomPoints[i].y = affine2[3]*getX(i) + affine2[4]*getY(i) + affine2[5];
        
        
        randomPoints[i].c = (randomPoints[i].c + af2ColorVal)/2;
    }
    
    public static void affineFunc3 (int i){
        
       
        double[] affine3 = new double[]{0,1,-1*getY(i),1,0,-1*getX(i)};
        randomPoints[i].x = affine3[0]*getX(i) + affine3[1]*getY(i) + affine3[2];
        randomPoints[i].y = affine3[3]*getX(i) + affine3[4]*getY(i) + affine3[5];
        
        
        
        randomPoints[i].c = (randomPoints[i].c + af3ColorVal)/2;
    }
    
    public static void affineFunc4 (int i){
       
        double x = getX(i);
        double y = getY(i);
        double radX = Math.toRadians(x);
        double radY = Math.toRadians(y);
        
        double[] affine4 = new double[]{Math.cos(radX),-Math.sin(radY),2,Math.sin(radY),Math.cos(radX),2};
        randomPoints[i].x = affine4[0]*getX(i) + affine4[1]*getY(i) + affine4[2];
        randomPoints[i].y = affine4[3]*getX(i) + affine4[4]*getY(i) + affine4[5];
        
        
        randomPoints[i].c = (randomPoints[i].c + af4ColorVal)/2;
    }
    
    public static void affineFunc5 (int i){
        double[] affine1 = new double[]{1,-.01,0, 1,-.01,0};
        randomPoints[i].x = affine1[0]*getX(i) + affine1[1]*getY(i) + affine1[2];
        randomPoints[i].y = affine1[3]*getX(i) + affine1[4]*getY(i) + affine1[5];
        
        
        randomPoints[i].c = (randomPoints[i].c + af4ColorVal)/2;
        
    }
    
}
