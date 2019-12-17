
package com.mycompany.a3.GameObjects;
import java.util.Random;
import com.mycompany.a3.GameWorld;
import com.mycompany.a3.Interfaces.*;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.geom.Point2D;
import com.codename1.util.MathUtil;

public class NPS extends Ship implements IMoveable, IDrawable, ISelectable, ICollider{
	
	private boolean selected;
	private MissileLauncher nps_launcher;
	private boolean fired;
	public boolean isFired() {return fired;}
	public void setFired(boolean f) {
		this.fired = f;
	}
	
	
	public NPS(int width, int height) {
		//Class that defines non-player enemy ships.
		this.set_color(ColorUtil.rgb(96,96,96));
		Random rand = new Random();
		this.set_location(rand.nextDouble()*width,rand.nextDouble()*height);
		if(rand.nextInt(20) > 10) {
			this.set_size(20);
		}else {
			this.set_size(10);
		}
		this.set_speed(5 + rand.nextInt(10));

		nps_launcher = new MissileLauncher(this.get_direction(), this.get_speed(), this.get_location());
		this.set_missile_count(3);
		selected = false;
		fired = false;
	}
	
	public void lock_missile_launcher(int ps_loc) {
		this.nps_launcher.set_direction(ps_loc);
	}
	
	
	public Missile fire_missile(Point2D ps_loc) {

		Missile m = new Missile();
		Point2D PS_location = this.get_location();
		double x = (this.get_X() - ps_loc.getX());
		double y = (this.get_Y() - ps_loc.getY());
		int angle = (int)Math.toDegrees(MathUtil.atan(y/x));
		if(this.get_X() > ps_loc.getX()) {
			angle += 180;
		}
		lock_missile_launcher(90 - angle);
		m.set_direction(this.nps_launcher.get_direction());
		m.set_location(PS_location.getX(), PS_location.getY());
		m.set_ownership("Enemy");
		m.set_color(ColorUtil.rgb(0, 50, 255));
		m.set_speed(12);
		this.set_missile_count(this.get_missile_count() - 1);
		
		return m;
	}
	
	public String toString() {
		String ret = new String("Non-Player Ship ID: " + get_id() + " location: " + (Math.round(this.get_location().getX())/ 1.0) + "," + (Math.round(this.get_location().getY()) / 1.0) + " color: [" + 
				ColorUtil.red(this.get_color())+ ", " + ColorUtil.green(this.get_color()) + ", " + ColorUtil.blue(this.get_color()) + "]" + " speed: " + this.get_speed() + " dir: " + this.get_direction() + " size: " + this.get_size());
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
		
		if(isSelected() == true) {
			g.drawRect(x, y, 10, 30);
			g.drawLine(x,y, x - 10, y - 15);
			g.drawRect(x, y, 30, 10);
		}else {
			g.fillRect(x, y, 10, 30);
			g.drawLine(x,y, x - 10, y - 15);
			g.fillRect(x, y, 30, 10);
		}
		
		
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