package com.mycompany.a3.GameObjects;
import java.util.Random;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.geom.Point2D;

	

public abstract class GameObject{
	
	
	private Point2D location;
	public final int x_lim = 1024;//Dimensions
	public final int y_lim = 768;
	private int size, id, color;
	
	public int get_id() {return this.id;}
	public void set_id(int id_) {
		this.id = id_;
	}
	
	public GameObject() {
		Random rand = new Random();
		int x = rand.nextInt(x_lim);
		int y = rand.nextInt(y_lim);
		this.location = new Point2D(x,y);
		this.color = ColorUtil.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
		size = 5 + rand.nextInt(15);
		id = 0;
		set_col(false);
	}
	
	public int get_size() {return this.size;}
	public void set_size(int sz) {this.size = sz;}
	
	
	public Point2D get_location() { return location; }
	public void set_location(double x, double y){
		location.setX(x);
		location.setY(y);
	}
	
	public double get_X() {
		return this.location.getX();
	}
	public double get_Y() {
		return this.location.getY();
	}
	
	public int get_color() { return color; }
	public void set_color(int col) {
		color = col;
	}
	
	private boolean collided;
	public boolean get_col() {return this.collided;}
	public void set_col(boolean c) {this.collided = c;}
	
}








