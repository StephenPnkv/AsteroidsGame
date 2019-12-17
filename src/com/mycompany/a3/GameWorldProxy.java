package com.mycompany.a3;
import java.util.Observable;
import java.util.Vector;

import com.mycompany.a3.Interfaces.*;



public class GameWorldProxy extends Observable implements IGameWorld{
	
	
	private GameWorld proxy;
	private static boolean  playing;
	private int elapsed_t;
	private Vector<ISelectable> s;
	public Vector<ISelectable> get_selected(){return this.s;}
	
	private GameWorldCollection collection;
	public GameWorldCollection get_collection() {return this.collection;}
	
	public GameWorldProxy(GameWorld gw) {
		this.proxy = gw;
		this.collection = gw.get_collection();
		this.s = gw.get_selected();
		this.setChanged();
		this.elapsed_t = gw.get_time();
		playing = GameWorld.get_playing();
	}
	
	public int get_time() {return this.elapsed_t;}
	public boolean get_playing() {return playing;}
	public void set_playing(boolean p) {playing = p;}
	
	public void print_stats() {
		proxy.print_stats();
	}
	
	public void print_map() {
		proxy.print_map();
	}
	
	public int get_lives(){
		return proxy.get_lives();
	}
	
	public boolean get_sound() {return proxy.get_sound();}
	public void set_sound(boolean option) {this.proxy.set_sound(option);}
	
	/*public int get_height() {return this.proxy.get_height();}
	public void set_height(int h) {this.proxy.set_height(h);}
	
	public int get_width() {return this.proxy.get_width();}
	public void set_width(int w) {this.proxy.set_width(w);}*/
	
	public int get_score() {
		return proxy.get_score();
	}
	
	public int get_tick_count() {return proxy.get_tick_count();}
	public int get_missile_count() {return proxy.get_missile_count();}
}