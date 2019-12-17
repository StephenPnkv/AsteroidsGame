package com.mycompany.a3.GameObjects;
import com.mycompany.a3.GameWorld;
import com.mycompany.a3.Interfaces.IMoveable;

import java.util.Random;

public abstract class MoveableGameObject extends GameObject implements IMoveable{
	
	private int speed, direction;
	private int reverse_x = 1; // <------------------- Initialize
	private int reverse_y = 1; // <------------------- Initialize 
	public MoveableGameObject(){
		Random rand = new Random();
		set_direction(rand.nextInt(359));
		set_speed(1 + rand.nextInt(10));
	}
	public void set_speed(int spd) { speed = spd; }
	public int get_speed() { return speed; }
	
	public void set_direction(int dir) { direction = dir; }
	public int get_direction() { return direction; }
	
	public void move(int elapsed_t) {
		
		double deltaX = Math.cos(Math.toRadians(90 - this.get_direction())) * this.get_speed() *  ((double)elapsed_t / 70)  ; // cos(90-heading)
		double deltaY = Math.sin(Math.toRadians(90 - this.get_direction())) * this.get_speed() *  ((double)elapsed_t / 70) ; // sin(90-heading)
		double finalValueX = get_location().getX() + deltaX * reverse_x;
		double finalValueY= get_location().getY() + deltaY * reverse_y;
		this.set_location(finalValueX, finalValueY);
		if((get_location().getX()+ this.get_size() >= GameWorld.get_width() || this.get_location().getX() <0))
		{
		   reverse_x = -1 * reverse_x;
		}
		if((get_location().getY()+ this.get_size() >= GameWorld.get_height() || this.get_location().getX() <0))
		{
		   reverse_y = -1 * reverse_y;
		}
	}
	
		/*
		//calculate x and y coordinates
				double theta = 90 - this.get_direction();
				theta = Math.toRadians(theta);
				int distance = this.get_speed() * (elapsed_t);
				double dX = Math.cos(theta) * distance;
				double dY = Math.sin(theta) * distance;
				
				if(this.get_location().getX() + dX + 15 > GameWorld.get_width() || this.get_location().getX() + dX - 15 <= 0) {
					dX = -dX;
					this.set_direction(360 - this.get_direction());
				}
					
				if(this.get_location().getY() + dY + 15 > GameWorld.get_height() || this.get_location().getY() + dY - 15 <= 0) {
					dY = -dY;
					this.set_direction(180 - this.get_direction());
				}
				
				if(this.get_X() <= 0) {
					dX = -dX;
					this.set_direction(360 - this.get_direction());

				}
				
				if(this.get_Y() <= 0) {
					dY = -dY;
					this.set_direction(180 - this.get_direction());
				}
					
				
				
				//Set the new location
				this.set_location(this.get_location().getX() - dX, 
								  this.get_location().getY() - dY);
				
	}
	*/
	
	
}

