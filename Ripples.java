/**
 * Filename: Ripples.java
 * Author: Aarushi Shha
 * UserID: cs11fbbb
 * Date:10.22.18
 * Sources of help: Textbook,Tutors,objectDraw Library
 */
import Acme.*;
import objectdraw.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Creates a ripple animation displayed on the screen
 */
public class Ripples extends ActiveObject implements MouseListener, MouseMotionListener, ActionListener, ChangeListener {
  // constants for Ripple animation
  private static final int INCREASE_BY = 2;
  private static final int MAX_RIPPLE_SIZE = 80;
  private static final int GROWING_INTERVAL = 10;
  private static final int PIXEL_LIMIT = 6;

  private static int PAUSE_TIME = 75;

  // center of the ripple
  private Location centerPoint;
  // reference to PondQuadrants
  private PondQuadrants quads;
  // canvas to draw on
  private DrawingCanvas pondCanvas;
  //private ReflectingPond reflectingPond;

  // keeps track of when ripple should animate
  private boolean ripple = true;
  private boolean movingRipples = true;
  // ripple object
  private FramedOval ripple1;
  private FramedOval ripple2;
  private FramedOval ripple3;
  private FramedOval ripple4;

  // current size of ripple
  private int ripple1Size;
  private int ripple2Size;
  private int ripple3Size;
  private int ripple4Size;


  // counts how many times ripple has grown
  private int growingCycle;

  // number of ripples created
  private int numRipples;
  // keeping track of if this Ripple should make more ripples
  private boolean createMoreRipples;
  // keeps track of if ripple should grow or shrink
  private boolean grow1 = true;
  private boolean grow2 = true;
  private boolean grow3 = true;
  private boolean grow4 = true;

  private double widthProp;
  private double heightProp;

  private boolean rippleGrabbed = false;
  private Location lastPoint;


  /**
   * Initializes ripple object and starts animation
   * @param center center of the ripple
   * @param canvas canvas to draw on
   * @param quadrants PondQuadrants reference for color switching
   */
  public Ripples(Location center, DrawingCanvas canvas,
             PondQuadrants quadrants, boolean createNew, boolean movingRipples, ReflectingPond reflectingPond) {
    // create  a new ripple
    ripple1 = new FramedOval(center,0,0,canvas);
    ripple1Size = 0;

    // save formal parameters as instance variables for future
    quads = quadrants;
    centerPoint = center;
    pondCanvas = canvas;
    createMoreRipples = createNew;
    numRipples = 1;
    this.movingRipples = movingRipples;
    this.reflectingPond = reflectingPond;

    widthProp = (double)centerPoint.getX()/canvas.getWidth();
    heightProp = (double)centerPoint.getY()/canvas.getHeight();

    // start animation
    start();
  }

     /**
      * animates a single ripple and creates 3 more at varying times
      */
     public void run() {

       while(ripple) {
         moveRipple1();
         if(ripple2 != null) {
           moveRipple2();
         }
         if(ripple3 != null) {
           moveRipple3();
         }
         if(ripple4 != null) {
           moveRipple4();
         }

        if(movingRipples){

        if(growingCycle%GROWING_INTERVAL == 0 &&
           numRipples < 4 && createMoreRipples ){

           if(numRipples == 1) {
             ripple2 = new FramedOval(centerPoint,0,0,pondCanvas);
           } else if (numRipples == 2) {
             ripple3 = new FramedOval(centerPoint,0,0,pondCanvas);
           } else if(numRipples == 3) {
             ripple4 = new FramedOval(centerPoint,0,0,pondCanvas);
           }
           numRipples++;
         }
       }
           updateRippleLocation();

        pause(PAUSE_TIME);
      }
      ripple1.hide();
      ripple2.hide();
      ripple3.hide();
      ripple4.hide();
     }

     public void moveRipple1() {

       if( grow1 ) {
         if(movingRipples) {

         ripple1Size += INCREASE_BY;
         ripple1.setSize(ripple1Size,ripple1Size);
         ripple1.move(-1,-1);
         growingCycle++;
         }

         ripple1.setColor(quads.whatColor(centerPoint));


       } else {
         if(movingRipples ) {

         ripple1Size -= INCREASE_BY;
         ripple1.setSize(ripple1Size,ripple1Size);
         ripple1.move(1,1);
         }

         ripple1.setColor(quads.whatColor(centerPoint));
       }


       if( ripple1Size >= MAX_RIPPLE_SIZE) {
          grow1 = false;
       } else if (ripple1Size <= 0) {
          grow1 = true;
       }
     }

     public void moveRipple2() {

       if( grow2 ) {
         if(movingRipples) {

         ripple2Size += INCREASE_BY;
         ripple2.setSize(ripple2Size,ripple2Size);
         ripple2.move(-1,-1);
         }

         ripple2.setColor(quads.whatColor(centerPoint));


       } else {
         if(movingRipples ) {

         ripple2Size -= INCREASE_BY;
         ripple2.setSize(ripple2Size,ripple2Size);
         ripple2.move(1,1);
         }

         ripple2.setColor(quads.whatColor(centerPoint));
       }


       if( ripple2Size >= MAX_RIPPLE_SIZE) {
          grow2 = false;
       } else if (ripple2Size <= 0) {
          grow2 = true;
       }
     }

     public void moveRipple3() {

       if( grow3 ) {
         if(movingRipples) {

         ripple3Size += INCREASE_BY;
         ripple3.setSize(ripple3Size,ripple3Size);
         ripple3.move(-1,-1);
         }

         ripple3.setColor(quads.whatColor(centerPoint));


       } else {
         if(movingRipples ) {

         ripple3Size -= INCREASE_BY;
         ripple3.setSize(ripple3Size,ripple3Size);
         ripple3.move(1,1);
         }

         ripple3.setColor(quads.whatColor(centerPoint));
       }


       if( ripple3Size >= MAX_RIPPLE_SIZE) {
          grow3 = false;
       } else if (ripple3Size <= 0) {
          grow3 = true;
       }
     }

     public void moveRipple4() {

       if( grow4 ) {
         if(movingRipples) {

         ripple4Size += INCREASE_BY;
         ripple4.setSize(ripple4Size,ripple4Size);
         ripple4.move(-1,-1);
         }

         ripple4.setColor(quads.whatColor(centerPoint));


       } else {
         if(movingRipples ) {

         ripple4Size -= INCREASE_BY;
         ripple4.setSize(ripple4Size,ripple4Size);
         ripple4.move(1,1);
         }

         ripple4.setColor(quads.whatColor(centerPoint));
       }


       if( ripple4Size >= MAX_RIPPLE_SIZE) {
          grow4 = false;
       } else if (ripple4Size <= 0) {
          grow4 = true;
       }
     }



     public void mousePressed( MouseEvent evt ) {
      Location point = new Location(evt.getX(), evt.getY());
       if(ripple1.contains(point) || (ripple2 != null && ripple2.contains(point)) || (ripple3 != null && ripple3.contains(point)) ||
          (ripple4 != null && ripple4.contains(point))){
         rippleGrabbed = true;
         lastPoint = point;
       }

     }

     public void mouseClicked( MouseEvent evt ) {}

     public void mouseReleased( MouseEvent evt ) {
       rippleGrabbed = false;
     }

     public void mouseEntered( MouseEvent evt ) {}

     public void mouseExited( MouseEvent evt ) {}

     public void mouseDragged( MouseEvent evt ) {
       Location point = new Location(evt.getX(), evt.getY());
       if (point.getX() + PIXEL_LIMIT >= pondCanvas.getWidth() ) {
         int xpos = pondCanvas.getWidth() - PIXEL_LIMIT;
         point.translate(xpos-evt.getX(),0);
       } else if(point.getX() - PIXEL_LIMIT <= 0) {
         int xpos = PIXEL_LIMIT;
         point.translate(xpos-evt.getX(),0);
       }

       if (point.getY() + PIXEL_LIMIT >= pondCanvas.getHeight() ) {
         int ypos = pondCanvas.getHeight() - PIXEL_LIMIT;
         point.translate(0,ypos - evt.getY());
       } else if(point.getY() - PIXEL_LIMIT <= 0) {
         int ypos = PIXEL_LIMIT;
         point.translate(0,ypos - evt.getY());
       }

       if( rippleGrabbed ) {


         ripple1.move( point.getX() - lastPoint.getX(),
                     point.getY() - lastPoint.getY() );
        if( ripple2 != null ) {
         ripple2.move( point.getX() - lastPoint.getX(),
                       point.getY() - lastPoint.getY() );
        }
        if(ripple3 != null){
          ripple3.move( point.getX() - lastPoint.getX(),
                        point.getY() - lastPoint.getY() );
        }
        if(ripple4 != null) {
          ripple4.move( point.getX() - lastPoint.getX(),
                        point.getY() - lastPoint.getY() );
        }
         centerPoint.translate(point.getX() - lastPoint.getX(),
                     point.getY() - lastPoint.getY());
        widthProp = (double)centerPoint.getX()/pondCanvas.getWidth();
        heightProp = (double)centerPoint.getY()/pondCanvas.getHeight();


         lastPoint = point;

       }
     }

     public void mouseMoved( MouseEvent evt ) {}

     public void actionPerformed( ActionEvent evt ) {
       if(evt.getActionCommand() == "run") {
         movingRipples = true;
       } else if(evt.getActionCommand() == "pause") {
         movingRipples = false;
       } else if(evt.getActionCommand() == "clear") {
         ripple = false;
       }
     }

     public void stateChanged( ChangeEvent evt ) {
       JSlider source = (JSlider)evt.getSource();
       int speed = source.getValue();
       PAUSE_TIME = -1*(speed) + 101;
     }

     public void updateRippleLocation(){
       int newX = (int)(widthProp*pondCanvas.getWidth());
       int newY = (int)(heightProp*pondCanvas.getHeight());
       int dx = (int)(newX - centerPoint.getX());
       int dy = (int)(newY - centerPoint.getY());
       centerPoint.translate(dx,dy);
       ripple1.move(dx,dy);
       if(ripple2 != null)
          ripple2.move(dx,dy);
       if(ripple3 != null)
         ripple3.move(dx,dy);
       if(ripple4 != null)
         ripple4.move(dx,dy);
     }




   }//end of file
