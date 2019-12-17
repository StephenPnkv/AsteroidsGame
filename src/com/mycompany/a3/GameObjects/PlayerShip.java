package com.mycompany.a3.GameObjects;
import com.codename1.ui.Graphics;

import com.codename1.ui.geom.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.geom.Point2D;
import com.mycompany.a3.GameWorld;
import com.mycompany.a3.Interfaces.*;

import java.util.Random;


public class PlayerShip extends Ship implements ISteerable, IDrawable, IMoveable, ICollider, ISelectable{
	//Class that creates an object that the player will control.
	private MissileLauncher launcher;
	private int reverse_x = 1; // <------------------- Initialize
	private int reverse_y = 1; // <------------------- Initialize
	public MissileLauncher get_launcher() {return this.launcher;}
	private boolean selected;
	
	public PlayerShip(int x, int y) {
		this.set_color(ColorUtil.rgb(51,153,255));
		this.set_location(x,y);//PS located at the center
		this.set_missile_count(10);
		this.set_direction(0);
		this.set_speed(0);
		this.set_size(30);
		this.setSelected(false);
		launcher = new MissileLauncher(0, this.get_speed(), this.get_location());
		
	}
	
	public void rotate_launcher(int theta) {
		//int angle = 90 - theta;
		this.launcher.change_direction(this.launcher.get_direction() + theta);
	}
	
	
	public void change_direction(int theta) {
		int dir = (this.get_direction() + theta) % 360;
		this.set_direction(dir);	
	}
	
	public void move(int elapsed_t) {
		
		double deltaX = Math.cos(Math.toRadians(90 - this.get_direction())) * this.get_speed() *  ((double)elapsed_t / 70)  ; // cos(90-heading)
		double deltaY = Math.sin(Math.toRadians(90 - this.get_direction())) * this.get_speed() *  ((double)elapsed_t / 70) ; // sin(90-heading)
		double finalValueX = get_location().getX() + deltaX * reverse_x;
		double finalValueY= get_location().getY() + deltaY * reverse_y;
		this.set_location(finalValueX, finalValueY);
		this.launcher.set_location(this.get_X(), this.get_Y());
		if((get_location().getX()+ this.get_size() >= GameWorld.get_width() || this.get_location().getX() < 0))
		{
		   reverse_x = -reverse_x;
		}
		if((get_location().getY()+ this.get_size() >= GameWorld.get_height() || this.get_location().getY() < 0))
		{
		   reverse_y = -reverse_y;
		}
	}
	
	public Missile fire_missile() {
		
		Missile m = new Missile();
		m.set_location(this.launcher.get_X()+this.launcher.get_size(), this.launcher.get_Y()+this.launcher.get_size());
		m.set_direction(this.launcher.get_direction());
		m.set_ownership("Player");
		this.set_missile_count(this.get_missile_count() - 1);
		return m;
	}
	
	public String toString() {
		String ret = new String("*Player Ship: " + get_id()  + " location: " + (Math.round(this.get_location().getX())/ 1.0) + "," + (Math.round(this.get_location().getY()) / 1.0) + " color: [" + 
				ColorUtil.red(this.get_color())+ ", " + ColorUtil.green(this.get_color()) + ", " + ColorUtil.blue(this.get_color()) + "]" + " speed: " + this.get_speed() + " dir: " + this.get_direction() + " missiles: " + this.get_missile_count() 
				+ " Missile Launcher dir: " + this.launcher.get_direction());
		return ret;
	}
	

	
	public void draw(Graphics g, Point pCmpRelPrnt) {
		Point top, bottomLeft, bottomRight;
		top = new Point(0,this.get_size() / 2);
		bottomLeft = new Point(-(this.get_size() / 2), -(this.get_size() / 2));
		bottomRight = new Point(this.get_size() / 2, -(this.get_size() / 2));
		g.setColor(this.get_color());
		g.fillTriangle((int)(pCmpRelPrnt.getX() + this.get_X()), 
				(int)(pCmpRelPrnt.getY() + this.get_Y() + top.getY()), 
				(int)(pCmpRelPrnt.getX() + this.get_X() + bottomLeft.getX()),
				(int)(pCmpRelPrnt.getY() + this.get_Y() + bottomLeft.getY()),
				(int)(pCmpRelPrnt.getX() + this.get_X() + bottomRight.getX()),
				(int)(pCmpRelPrnt.getY() + this.get_Y() + bottomRight.getY()));
		//draw launcher
		MissileLauncher myLauncher = this.get_launcher();
		myLauncher.draw(g, pCmpRelPrnt);
	}
	
	public boolean collidesWith(ICollider obj) {
		boolean result = false;
		int thisCenterX = (int)(this.get_location().getX() + 25);
		int thisCenterY = (int)(this.get_location().getY() + 25);
		int otherCenterX = (int)((GameObject)obj).get_X() + 25;
		int otherCenterY = (int)((GameObject)obj).get_Y() + 25;
		int dX = thisCenterX - otherCenterX;
		int dY = thisCenterY - otherCenterY;
		int dist = (dX*dX + dY*dY);
		int otherRadius = 25;
		int thisRadius = 25;
		int sqr = (thisRadius*thisRadius + thisRadius*otherRadius + otherRadius * otherRadius);
		if(dist <= sqr)
			result = true;
		return result;
	}
	
	public void handleCollision(ICollider otherObject){
		Random rand = new Random();
		this.set_color(ColorUtil.rgb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
	}
	
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
		int px = pPtrRelPrnt.getX();
		int py = pPtrRelPrnt.getY();
		int xLoc = pCmpRelPrnt.getX() + (int)this.get_X();
		int yLoc = pCmpRelPrnt.getY() + (int)this.get_Y();
		if((px >= xLoc) && (px <= xLoc + get_size())
				&& (py >= yLoc) && (py <= yLoc+get_size()))
			return true;
		else
			return false;
		
	}
	 //public void draw(Graphics g,Point pCmpRelPrnt);
	 public void setSelected(boolean select) {
		 this.selected = select;
	 }
	 public boolean isSelected() {
		 return this.selected;
	 }
	
	
}