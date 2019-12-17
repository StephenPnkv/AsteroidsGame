package com.mycompany.a3.GameObjects;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.geom.Point2D;
import com.mycompany.a3.Interfaces.IDrawable;
import com.mycompany.a3.Interfaces.ISteerable;

public class MissileLauncher extends MoveableGameObject implements ISteerable,IDrawable{
	//Class defines the missile launcher of both the enemy and player ships.
	private Missile m;
		
	public MissileLauncher(int dir, int speed, Point2D location) {
		
		this.set_direction(dir);
		this.set_speed(speed);
		this.set_location(location.getX(), location.getY());
		this.set_color(ColorUtil.rgb(192,192,192));
	}
	
	public void fire_missile() {
		m = new Missile();
		m.set_direction(this.get_direction());
		m.set_fuel_level(10);
		m.set_speed(5);
			
	}
	
	public void change_direction(int new_dir) {
		set_direction(new_dir);
	}
	
	public void draw(Graphics g, Point pCmpRelPrnt) {
		int x = (int)(pCmpRelPrnt.getX() + this.get_X());
		int y = (int)(pCmpRelPrnt.getY() + this.get_Y());
		
		double angle = Math.toRadians(90 - this.get_direction());
		double dX = Math.cos(angle);
		double dY = Math.sin(angle);
		
		
		g.setColor(ColorUtil.BLACK);
		g.drawLine(x,y, (int)(x + (50*dX)), (int)(y + (50 * dY)));
	}
	
}