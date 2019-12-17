package com.mycompany.a3.GameObjects;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.mycompany.a3.Interfaces.*;

import java.util.Random;

import com.codename1.charts.util.ColorUtil;

public class Missile extends MoveableGameObject implements IDrawable, ISelectable, ICollider{
	//Class defines missile objects.
	private int fuel_level;
	private String ownership;
	private boolean selected;
	public Missile() {
		this.set_color(ColorUtil.rgb(225,0,0));
		this.set_fuel_level(10);
		this.set_speed(20);
		this.setSelected(false);
	}

	public String whose_missile() {return this.ownership;}
	public void set_ownership(String owner) {this.ownership = owner;}
	public String get_ownership() {return this.ownership;}
	public int get_fuel_level() {return this.fuel_level;}
	public void set_fuel_level(int lvl) {this.fuel_level = lvl;}
	
	public String toString() {
		String ret = new String(this.whose_missile() + " Missile ID: " + get_id()  + " location: " + (Math.round(this.get_location().getX())/ 1.0) + "," + (Math.round(this.get_location().getY()) / 1.0) + " color: [" + 
				ColorUtil.red(this.get_color()) + ", " + ColorUtil.green(this.get_color()) + ", " + ColorUtil.blue(this.get_color()) + "]" + " speed: " + this.get_speed() + " dir: " + this.get_direction() + " fuel: " + this.get_fuel_level() + " Selected:" + isSelected());
		return ret;
	}
	
	public boolean collidesWith(ICollider obj) {
		boolean result = false;
		int thisCenterX = (int)(this.get_X() + (this.get_size() / 2));
		int thisCenterY = (int)(this.get_Y() + (this.get_size() / 2));
		int otherCX = (int)(((GameObject)obj).get_X() + ((GameObject)obj).get_size() / 2);
		int otherCY = (int)(((GameObject)obj).get_Y() + ((GameObject)obj).get_size() / 2);
		
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
		g.setColor(this.get_color());
		int x = (int)(pCmpRelPrnt.getX() + this.get_location().getX());
		int y = (int)(pCmpRelPrnt.getY() + this.get_location().getY());
		if(this.isSelected()) {
			g.drawRoundRect(x, y, 10, 30, 360, 360);
			
		}else {
			g.fillRoundRect(x, y, 10, 30, 360, 360);
		}
	}
	
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
		int px = pPtrRelPrnt.getX();
		int py = pPtrRelPrnt.getY();
		int xLoc = pCmpRelPrnt.getX() + (int)this.get_X();
		int yLoc = pCmpRelPrnt.getY() + (int)this.get_Y();
		if((px >= xLoc) && (px <= xLoc + 10)
				&& (py >= yLoc) && (py <= yLoc + 30))
			return true;
		else
			return false;
		
	}
	 public void setSelected(boolean select) {
		 this.selected = select;
	 }
	 public boolean isSelected() {
		 return this.selected;
	 }
}