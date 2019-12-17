package com.mycompany.a3.GameObjects;



public abstract class Ship extends MoveableGameObject {
	private int missile_count;
	public int get_missile_count() {return this.missile_count;}
	public void set_missile_count(int ct) {this.missile_count = ct;}
}