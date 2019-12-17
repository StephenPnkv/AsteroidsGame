package com.mycompany.a3.GameObjects;
import java.util.Random;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.mycompany.a3.Interfaces.*;
import com.codename1.charts.util.ColorUtil;




public class Asteroid extends MoveableGameObject implements IDrawable, ISelectable, ICollider{
	private int size;
	private boolean selected;
	public Asteroid(int x, int y) {
		//Class defines asteroid objects in the game world.
		this.set_color(ColorUtil.rgb(153,76,0));
		Random rand = new Random();
		this.set_size(6 + rand.nextInt(30));
		this.set_location(rand.nextDouble()*x,rand.nextDouble()*y);
		setSelected(false);
	}
	
	public int get_size() {
		return this.size;
	}
	public void set_size(int sz) {this.size = sz;}
	
	
	public String toString() {
		
		
		String ret = new String("Asteroid ID: " + get_id() + " location: " + (Math.round(this.get_location().getX())/ 1.0) + "," + (Math.round(this.get_location().getY()) / 1.0) + " color: [" + 
				ColorUtil.red(this.get_color()) + ", " + ColorUtil.green(this.get_color()) + ", " + ColorUtil.blue(this.get_color()) + "]" + " speed: " + this.get_speed() + " dir: " + this.get_direction() + " size: " + this.get_size()
				+ " Collision: " + this.get_col());
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
		int x = (int)(pCmpRelPrnt.getX() + this.get_location().getX());
		int y = (int)(pCmpRelPrnt.getY() + this.get_location().getY());
		g.setColor(this.get_color());
		if(isSelected() == true) {
			g.drawArc(x, y, get_size(), get_size(), 360, 360);
			g.drawArc(x+2, y, get_size(), get_size(), 360, 360);
		}else {
			g.fillArc(x, y, get_size(), get_size(), 360, 360);
			g.fillArc(x+2, y, get_size(), get_size(), 360, 360);
		}


		//g.fillArc(x-2, y-2, get_size(), get_size(), 360, 360);
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