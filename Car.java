package assignment5;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Car {
	private static final int FINISH_LINE = 800;
	
	private BufferedImage car;
	private Random rand = new Random();
	private Double velocity = 0.0;
	private Double acceleration = rand.nextDouble() / 400;
	private Double x;
	private Double y;
	public String carNumber;
	private Color BurntOrange;
	
	public Car(Double position, int number){
		BurntOrange = new Color(28, 221, 28);
		carNumber = Integer.toString(number);
		this.car = new BufferedImage(65, 35, BufferedImage.TYPE_3BYTE_BGR);
		this.y = position;
		this.x = 0.0;
		
		Rectangle body = new Rectangle(0, 10, 60, 10);
		//tires
		Ellipse2D.Double frontTire = new Ellipse2D.Double(10, 20, 10, 10);
		Ellipse2D.Double rearTire = new Ellipse2D.Double(40, 20, 10, 10);
		//4 points connecting windshields and roof
		Point2D.Double r1 = new Point2D.Double(10, 10);
		Point2D.Double r2 = new Point2D.Double(20, 0);
		Point2D.Double r3 = new Point2D.Double(40, 0);
		Point2D.Double r4 = new Point2D.Double(50, 10);
		//windshield and roof
		Line2D.Double frontWindshield = new Line2D.Double(r1, r2);
		Line2D.Double roofTop = new Line2D.Double(r2, r3);
		Line2D.Double rearWindshield = new Line2D.Double(r3, r4);
		//draw on screen
		Graphics2D g2 = car.createGraphics();
		g2.setBackground(Color.WHITE);
		g2.clearRect(0, 0, 65, 35);
		g2.setColor(BurntOrange);
		g2.draw(body); 
		g2.setColor(Color.BLACK);
		g2.fill(frontTire); 
		g2.fill(rearTire);
		g2.setColor(BurntOrange);
		g2.draw(frontWindshield); 
		g2.draw(roofTop); 
		g2.draw(rearWindshield);
		//draw number on car
		g2.setColor(Color.BLACK);
		g2.drawString(carNumber, 30, 20);
		g2.dispose();
	}
	
	public void accelerate(){
		if(!winner()){
			this.x += this.velocity;
			this.velocity += this.acceleration;
		}
	}
	public boolean winner(){
		if(this.x + 60 >= FINISH_LINE){
			return true;
		}
		else{
			return false;
		}
	}

	public void draw(Graphics2D g2) {
		g2.drawImage(car, new AffineTransform(1f,0f,0f,1f,x,y), null);
	}
}
