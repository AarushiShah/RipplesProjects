/**
 * Filename: ReflectingPond.java
 * Author: Aarushi Shah
 * UserID: cs11fbbb
 * Date: 10/22/18
 * Sources of help: Textbook, tutors, object draw Java library
 */

import Acme.*;
import objectdraw.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;


/**
 * Controls the GUI of the program and handles mouse events
 */
public class ReflectingPond extends WindowController implements MouseListener, MouseMotionListener, ActionListener, ChangeListener {

  // size of the window
  private static final int WINDOW_SIZE = 600;

  // instance variable referring to PondQuadrants
  private PondQuadrants pond;

  private JButton run;
  private JButton pause;
  private JButton clear;
  private JSlider speedController;
  private JLabel speedLabel;

  private boolean movingRipples;
  private int speed;

  private Ripples ripple;


  /**
   * Creates a screen in which the quadrants and ripples will be displayed
   *
   * @param args arguments entered in terminal (if any)
   */
  public static void main(String[] args) {
    // creating a new window with specified window size
    new Acme.MainFrame(new ReflectingPond(), args, WINDOW_SIZE, WINDOW_SIZE);
  }

  /**
   * Displays PondQuadrants initially for use
   */
  public void begin() {

    JLabel header = new JLabel("Reflecting Pond Control Panel");
    run = new JButton("Run");
    run.addActionListener(this);
    run.setActionCommand("run");
    pause = new JButton("Pause");
    pause.addActionListener(this);
    pause.setActionCommand("pause");
    clear = new JButton("Clear Ripples");
    clear.addActionListener(this);
    clear.setActionCommand("clear");

    speed = 50;
    speedLabel = new JLabel("The speed is " + speed);
    speedController = new JSlider(JSlider.HORIZONTAL,1,100,50);
    speedController.addChangeListener(this);

    JPanel northPanel = new JPanel();
    JPanel northPanel2 = new JPanel();
    JPanel southPanel = new JPanel();
    southPanel.add(speedLabel);
    southPanel.add(speedController);
    northPanel.add(header);
    northPanel2.add(run);
    northPanel2.add(pause);
    northPanel2.add(clear);

    JPanel gridPanel = new JPanel();
    gridPanel.setLayout(new GridLayout(2,1));
    gridPanel.add(northPanel);
    gridPanel.add(northPanel2);

    this.add(gridPanel, BorderLayout.NORTH);
    this.add(southPanel, BorderLayout.SOUTH);


    canvas.addMouseListener(this);
    canvas.addMouseMotionListener(this);

    this.validate();

    movingRipples = true;

    // create an instance of class PondQuadrants
    pond = new PondQuadrants( canvas );
  }

  /**
   * Calls PondQuadrant method to check if mouse is pressing an axis
   *
   * @param point the place on the canvas where mouse was pressed
   */
  public void mousePressed(MouseEvent evt) {
    Location x = new Location( evt.getX(), evt.getY() );
    pond.checkAxis( x );

  }

  /**
   * Creates a new Ripple
   *
   * @param point the place on the canvas where mouse was clicked
   */
   public void mouseClicked(MouseEvent evt) {
       Location x = new Location( evt.getX(), evt.getY() );
       ripple = new Ripples(x, canvas, pond, true, movingRipples,this);
       run.addActionListener(ripple);
       pause.addActionListener(ripple);
       clear.addActionListener(ripple);
       speedController.addChangeListener(ripple);
       canvas.addMouseListener(ripple);
       canvas.addMouseMotionListener(ripple);
   }

   public void mouseReleased( MouseEvent evt ) {}

   public void mouseEntered( MouseEvent evt ) {}

   public void mouseExited( MouseEvent evt ) {}

  /**
   * Calls PondQuadrant method to move axis if grabbed
   *
   * @param point the place on the canvas wehre mouse was dragged
   */
  public void mouseDragged( MouseEvent evt ){
    Location x = new Location( evt.getX(), evt.getY() );
    pond.moveAxis(x, canvas);

  }

  public void mouseMoved( MouseEvent evt ) {}

  public void actionPerformed( ActionEvent evt ) {
    if(evt.getSource() == run) {
      speedLabel.setText("The speed is " + speed );
      movingRipples = true;
    } else if(evt.getSource() == pause) {
      movingRipples = false;
      speedLabel.setText("The speed is paused (" + speed + ")");
    }
  }

  public void addListeners( Ripples ripples) {
    run.addActionListener(ripples);
    pause.addActionListener(ripples);
    clear.addActionListener(ripples);
  }

  public void stateChanged( ChangeEvent evt ) {
    speed = speedController.getValue();
    if(movingRipples){
      speedLabel.setText("The speed is " + speed);
    } else {
      speedLabel.setText("The speed is paused (" + speed + ")");
    }

  }



     /**
      * Repaints the canvas and updates Quadrants
      *
      * @param g argument to pass to superconstructor
      */
     public void paint(java.awt.Graphics g) {
       super.paint(g);
       // only update the quadrants if pond has a value
       if( pond != null) {
         pond.updateQuads(canvas);
       }
     }

   } // end of file
