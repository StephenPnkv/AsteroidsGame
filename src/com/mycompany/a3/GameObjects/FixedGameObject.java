package com.mycompany.a3.GameObjects;






public class FixedGameObject extends GameObject{
	private int id;
	private static int count = 0;
	public int get_count() {return count;}
	public void set_id(int id_){ 
		this.id = id_; 
		
	}
	public int get_id() { return this.id; } 
	
	
	
	
	
}


