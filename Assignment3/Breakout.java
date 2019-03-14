/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;

/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
	while(true) {
		setup();
		
	for(int i=0; i<3;i++) {
		setBonce();
		waitForClick();
		while(!GameOver()||count==100) {
			moveBonce();
			checkForCollision();
			pause(15);
		}
		}
	removeAll();
	}
	}
	/**
	 * provjerava da li postoji kontak izmedju kublice i cigla
	 */
private void checkForCollision() {
	try {
		last = new GPoint(bonce.getX(), bonce.getY());
		gobj = getElementAt(last);
		if(gobj.getHeight()==brick.getHeight()||gobj.getWidth()==brick.getWidth()) {
			remove(gobj);
			if(k<0)
			vy=-vy;
		
			if(k>0) {
			  vy=-vy;
			}
			
			k=-k;
			bounceClip.play();
			count++;
		}
	}catch(Exception e) {
		
	}
		
		
	
	
	
	
}

/**
 * uslov za zaustavjanje igre
 * @return
 */
private boolean GameOver() {
	return(bonce.getY() >=window.getHeight()-BALL_RADIUS);	
		
	 
}
/**
 * krteane loptice	
 */
private void moveBonce() {
	bonce.move(vx, vy);
	
	if(bonce.getX()>=window.getWidth()-BALL_RADIUS) {
		if(k>0) vx=-vx;	
		if(k<0) vx=-vx;
		k=-k;
		bounceClip.play();
	
	}
	if(bonce.getY()<=1) {
		if(k<0)vy=-vy;
		if(k>0)vy=-vy;
		k=-k;
		bounceClip.play();
	}
	
	if(bonce.getX()<=1){
		if(k<0)vx=-vx;
		if(k>0)vx=-vx;
		bounceClip.play();	
		k=-k;
	}

	GObject collObj = getElementAt(bonce.getX(), bonce.getY());
	if (collObj == paddle) {
		if(k<0)vy=-vy;
		if(k>0)vy=-vy;
		k=-k;
		bounceClip.play();
	}
	

}
/**
 * setup za pocetne postavke
 */
private void setup() {
	
	window = new GRect(APPLICATION_WIDTH,APPLICATION_HEIGHT);
	add(window,0,0);
	setBrick();
	setPaddle();
	
	
}
/**
 * postavke za lopticu
 */
private void setBonce() {
	bonce = new GOval((window.getWidth()-BALL_RADIUS)/2,(window.getHeight()-BALL_RADIUS)/2,BALL_RADIUS,BALL_RADIUS);
	bonce.setFilled(true);
	add(bonce);
	vy = 3;
	vx = rgen.nextDouble(1.0, 3.0);
	if (rgen.nextBoolean(0.5)) vx = -vx;
	k=vy/vy;
}

/**
 * kreiranje vesla na dnu aplikacije
 */
private void setPaddle() {
paddle= new GRect(PADDLE_WIDTH, PADDLE_HEIGHT);
paddle.setFilled(true);
add(paddle,0,getHeight()-BRICK_Y_OFFSET);
addMouseListeners();
}

/**
 * pokretom misa pokrece se veslo samo po x osi
 */
 public void mouseMoved(MouseEvent e) {
	 paddle.setLocation(e.getX()-PADDLE_WIDTH/2, getHeight()-BRICK_Y_OFFSET);
	 
 }


/**
 * postaviti kocke na pravo mjesto i obojiti ih
 */
private void setBrick() {
	int x=(int) ((window.getWidth()-(NBRICK_ROWS*BRICK_WIDTH)-(9*BRICK_SEP))/2);
	int y=BRICK_Y_OFFSET;
	
 for(int i=1;i<=NBRICK_ROWS; i++) {
	 for(int j=1; j<=NBRICKS_PER_ROW;j++) {
		brick= new GRect(BRICK_WIDTH,BRICK_HEIGHT);
		brick.setFilled(true);
		if(i<=2&&i>=0) brick.setColor(Color.RED);
		if(i<=4&&i>2)  brick.setColor(Color.ORANGE);
		if(i<=6&&i>4)  brick.setColor(Color.YELLOW);
		if(i<=8&&i>6)  brick.setColor(Color.GREEN);
		if(i>8&&i<=10) brick.setColor(Color.CYAN);
		add(brick,x,y);
		x=x+BRICK_SEP+BRICK_WIDTH;
	 }

	 x=(int) ((window.getWidth()-(NBRICK_ROWS*BRICK_WIDTH)-(9*BRICK_SEP))/2);
	 y=y+BRICK_HEIGHT+BRICK_SEP;
 }
	
}
private int count=0;
private GObject gobj;
private GObject coll;
private GPoint last;
private GRect window;
private GRect brick;
private GRect paddle;
private GOval bonce;
private double vx,vy,k;

AudioClip bounceClip = MediaTools.loadAudioClip("bounce.au"); 
private RandomGenerator rgen =RandomGenerator.getInstance();


}
