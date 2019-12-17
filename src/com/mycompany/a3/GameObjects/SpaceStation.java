package com.mycompany.a3.GameObjects;
import java.util.Random;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.mycompany.a3.Interfaces.*;
import com.codename1.charts.util.ColorUtil;

public class SpaceStation extends FixedGameObject implements IDrawable, ICollider{
	//Definition for a space station. Includes functionality to toggle lights.
	private int BlinkRate;
	private boolean LightOn;
	
		public SpaceStation(int x, int y) {
			this.set_id(10);
			Random rand = new Random();
			this.set_BlinkRate(1 + rand.nextInt(4));
			this.set_location(rand.nextDouble()*x,rand.nextDouble()*y);
			
			this.set_color(ColorUtil.rgb(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)));
			this.LightOn = false;
		}
	
	public boolean isToggled() {return this.LightOn;}
	public void toggle_light() {this.LightOn = true;}
	public void untoggle_light() {this.LightOn = false;}
	
	
	public void set_BlinkRate(int blr) { this.BlinkRate = blr; }
	public int get_BlinkRate() { return this.BlinkRate; } 
	
	public String toString() {
		
		String ret = new String("SpaceStation ID: " + get_id() + " location: " + (Math.round(this.get_location().getX())/ 1.0) + "," + (Math.round(this.get_location().getY()) / 1.0) + " color: [" + 
				ColorUtil.red(this.get_color())+ ", " + ColorUtil.green(this.get_color()) + ", " + ColorUtil.blue(this.get_color()) + "]" + " rate: " + this.get_BlinkRate() + " Light: " + this.isToggled());
		return ret;
		
	}
	
	public boolean collidesWith(ICollider obj) {
		boolean result = false;
		int thisCenterX = (int)(this.get_X() + (this.get_size() / 2));
		int thisCenterY = (int)(this.get_Y() + (this.get_size() / 2));
		int otherCX = (int)(((GameObject)obj).get_X() + 50 / 2);
		int otherCY = (int)(((GameObject)obj).get_Y() + 100 / 2);
		
		int dx = thisCenterX - otherCX;
		int dy = thisCenterY - otherCY;
		
		int dist = (dx*dx + dy*dy);
		int r = this.get_size() / 2;
		int obj_r = ((GameObject)obj).get_size();
		int sqr = (r*r + 2*r*obj_r + obj_r*obj_r);
		if(dist <= sqr)
			result = true;
		return result;
	}
	public void handleCollision(ICollider otherObject) {
		Random rand = new Random();
		this.set_color(ColorUtil.rgb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
	}
	
	public void draw(Graphics g, Point pCmpRelPrnt) {
		int x = (int)(pCmpRelPrnt.getX() + this.get_location().getX());
		int y = (int)(pCmpRelPrnt.getY() + this.get_location().getY());
		g.setColor(this.get_color());
		if(isToggled()) 
			g.fillRect(x, y, 50, 100);
		else
			g.drawRect(x, y, 50, 100);
	}
}