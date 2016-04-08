package assignment5;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.AffineTransform;
import java.util.Random;
import java.awt.FontMetrics;
import java.awt.Dimension;
import java.awt.Font;

public class JavaCars extends Applet implements Runnable{
	
	private Thread animate;
	private StopWatch time = new StopWatch();
	private String winner = null;
	private Car[] car;
	Random rand = new Random();
	Dimension dim;
	
	public void init(){
		dim = getSize();
		car = new Car[5];
		for(int i = 0; i < 5; i++){
			int randomNumber = rand.nextInt(10) + 10 * i;
			car[i] = new Car(100.0 * i, randomNumber);
		}
	}
	
	public void start(){
		this.animate = new Thread(this);
		this.animate.start();		
	}
	
	public void stop(){
		this.animate = null;
	}
	
	public void run(){
		time.start();
		while(Thread.currentThread() == animate){
			move();
			repaint();
			try {
			    Thread.sleep(10);                 //5 milliseconds
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		}
	}
	
	public void move(){
		for(int i = 0; i < car.length; i++){
			car[i].accelerate();
			if(car[i].winner()){
				winner = car[i].carNumber;
			}
		}
	}
	
	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.drawLine(800, 0, 800, 450);
		//car body
		for(int i = 0; i < car.length; i++){
			car[i].draw(g2);
		}
		g2.drawImage();	
		if(winner != null){
			time.stop();
			Double timeElapsed = (double) time.getElapsedTime();
			timeElapsed /= 1000;
			String msg = String.format("Car #" + winner + " won the race in %.2f seconds!", timeElapsed);
			Dimension d = getSize();
			Font f = new Font("Arial", Font.BOLD, 24);
			g2.setFont(f);
			FontMetrics fm = g2.getFontMetrics();
			int x = d.width / 2 - fm.stringWidth(msg) / 2;
			g2.drawString(msg, x, 500);
			stop();
		}
	}
	
	public void update(Graphics g){
		paint(g);
	}
}
