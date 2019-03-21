/**
 * Filename: PondQuadrants.java
 * Author: Aarushi Shah
 * UserID: cs11fbbb
 * Date: 10/22/18
 * Sources of help: Textbook,Tutors, objectdraw Java library
 */

import objectdraw.*;
import java.awt.*;

/**
 * Handles the four quadrants and the two axis - Resizing, Color setting, etc.
 */
public class PondQuadrants {

  // constants representing the quadrant colors
  private static final Color UPPER_LEFT_QUAD_COLOR = new Color(7,156,222);
  private static final Color UPPER_RIGHT_QUAD_COLOR = new Color(0,210,234);
  private static final Color LOWER_LEFT_QUAD_COLOR = new Color(27,67,93);
  private static final Color LOWER_RIGHT_QUAD_COLOR = new Color(9,0,137);

  // consants representing the ripple colors depending on quadrant
  private static final Color UPPER_LEFT_RIPPLE_COLOR = new Color(0,234,212);
  private static final Color UPPER_RIGHT_RIPPLE_COLOR = new Color(0,50,250);
  private static final Color LOWER_LEFT_RIPPLE_COLOR = new Color(255,137,93);
  private static final Color LOWER_RIGHT_RIPPLE_COLOR = new Color(252,220,116);

  // Pixel limit for axis
  private static final int PIXEL_LIMIT = 6;

  // color of the axis
  private static final Color LINE_COLOR = Color.WHITE;

  // represents the four quadrants
  private FilledRect upperRight;
  private FilledRect upperLeft;
  private FilledRect lowerRight;
  private FilledRect lowerLeft;

  // represents the axis
  private Line horizontal;
  private Line vertical;

  // proportions of width and height diving the screen into quadrants
  private double widthProp = 0.5;
  private double heightProp = 0.5;

  // booleans checking if axis have been grabbed
  private boolean horizontalGrabbed = false;
  private boolean verticalGrabbed = false;

  // keeping track of the last point of mouse
  private Location lastPoint;

  // x and y coordinates of axis
  private int verticalX;
  private int horizontalY;

  // canvas to draw on
  private DrawingCanvas canvas;

  /**
    * Creates the initial display of four quadrants and 2 axis
    * @param canvas the canvas to draw on
    */
   public PondQuadrants( DrawingCanvas canvas ) {

     // calcualtes the xpos and ypos of the axis
     int xpos = (int)(canvas.getWidth()*widthProp);
     int ypos = (int)(canvas.getHeight()*heightProp);

     // draw the four quadrants and set to appropriate colors
     upperLeft = new FilledRect(0,0,xpos,ypos,canvas);
     upperLeft.setColor(UPPER_LEFT_QUAD_COLOR);

     upperRight = new FilledRect(xpos,0,canvas.getWidth(),ypos,canvas);
     upperRight.setColor(UPPER_RIGHT_QUAD_COLOR);

     lowerRight = new FilledRect(xpos,ypos,
                                  canvas.getWidth(),canvas.getHeight(),canvas);
     lowerRight.setColor(LOWER_RIGHT_QUAD_COLOR);

     lowerLeft = new FilledRect(0,ypos,xpos,canvas.getHeight(),canvas);
     lowerLeft.setColor(LOWER_LEFT_QUAD_COLOR);

     // draw the axis, and set to appropriate colors
     horizontal = new Line(0,ypos,
                           canvas.getWidth(), ypos,
                           canvas );

     vertical = new Line(xpos,0,
                         xpos,canvas.getHeight(),
                         canvas );

     horizontal.setColor(LINE_COLOR);
     vertical.setColor(LINE_COLOR);

     // save canvas reference for future use
     this.canvas = canvas;
   }

   /**
    * moves the axis to specified location
    * @param point the point to drag the axis to
    * @param canvas the canvas to draw on
    */
   public void moveAxis( Location point, DrawingCanvas canvas ) {
     // if vertical line was grabbed, move the vertical axis
     if(verticalGrabbed) {
       verticalX = (int)lastPoint.getX();
       // update width proportion for the quadrants
       widthProp = (double)verticalX/canvas.getWidth();
       updateQuads(canvas);
     }
     // if horizontal line was grabbed, move the horizontal axis
    if(horizontalGrabbed){
      horizontalY = (int)lastPoint.getY();
      // update height proportions for the quadrants
      heightProp = (double)horizontalY/canvas.getHeight();
      updateQuads(canvas);
    }

    // keep track of last point where mouse was dragged
    if( horizontalGrabbed || verticalGrabbed){
      lastPoint = point;
    }

  }

  /**
   * updates the quadrants to new proportions
   * @param canvas the canvas to draw on
   */
  public void updateQuads(DrawingCanvas canvas){
    // calculates the xpos and ypos of the axis
    int xpos = (int)(canvas.getWidth()*widthProp);
    int ypos = (int)(canvas.getHeight()*heightProp);

    // make sure that the x position doesn't pass the 6 pixel boundary
    if (xpos + PIXEL_LIMIT >= canvas.getWidth() ) {
      xpos = canvas.getWidth() - PIXEL_LIMIT;
    } else if(xpos - PIXEL_LIMIT <= 0) {
      xpos = PIXEL_LIMIT;
    }

    // make sure that the y position doesn't pass the 6 pizel boundary
    if(ypos + PIXEL_LIMIT >= canvas.getHeight()) {
      ypos = canvas.getHeight() - PIXEL_LIMIT;
    } else if(ypos - PIXEL_LIMIT <= 0){
      ypos = PIXEL_LIMIT;
    }

    // update the quadrants and axis to the new positions
    upperLeft.setSize(xpos,ypos);

    upperRight.setSize(canvas.getWidth(),ypos);
upperRight.moveTo(xpos,0);

lowerRight.setSize(canvas.getWidth(),canvas.getHeight());
lowerRight.moveTo(xpos,ypos);

lowerLeft.setSize(xpos,canvas.getHeight());
lowerLeft.moveTo(0,ypos);

horizontal.setStart(0,ypos);
horizontal.setEnd(canvas.getWidth(), ypos);

vertical.setStart(xpos,0);
vertical.setEnd(xpos, canvas.getHeight());

}

/**
* check axis to see which axis is being grabbed
*
* @param point the location of the mouse
*/
public void checkAxis ( Location point ) {
// check to see if any of the axis are being grabbed
horizontalGrabbed = horizontal.contains( point );
verticalGrabbed = vertical.contains( point );

// keep track of the mouse point if axis are grabbed
if( horizontalGrabbed || verticalGrabbed){
  lastPoint = point;
}
}


/**
* check to see which color the ripple should be
*
* @param point the center point of the ripple
* @return the color the ripple should be
*/
public Color whatColor(Location point){
int xpos = (int)(canvas.getWidth()*widthProp);
int ypos = (int)(canvas.getHeight()*heightProp);

// check to see if ripple is in upper left quadrant
if(point.getX() >= 0 && point.getX() <= xpos &&
   point.getY() >= 0 && point.getY() <= ypos ) {
     return UPPER_LEFT_RIPPLE_COLOR;

  // check to see if ripple is in upper right quadrant
} else if(point.getX() >= xpos && point.getX() <= canvas.getWidth() &&
          point.getY() >= 0 && point.getY() <= ypos ) {
         return UPPER_RIGHT_RIPPLE_COLOR;

  // check to see if ripple is in lower left quadrant
} else if(point.getX() >= 0 && point.getX() <= xpos &&
          point.getY() >= ypos && point.getY() <= canvas.getHeight() ) {
         return LOWER_LEFT_RIPPLE_COLOR;

  // check to see if ripple is in lower right quadrant
} else if(point.getX() >= xpos && point.getX() <= canvas.getWidth() &&
           point.getY() >= ypos && point.getY() <= canvas.getHeight() ) {
          return LOWER_RIGHT_RIPPLE_COLOR;

 } else {
   // default return
   return Color.WHITE;
 }
}

} // end of file
