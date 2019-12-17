package com.mycompany.a3.Interfaces;
import java.util.Vector;

import com.mycompany.a3.GameWorldCollection;

public interface IGameWorld{
	public static boolean playing = true;
	public void print_stats();
	public void print_map();
	public int get_missile_count();
	public int get_tick_count();
	public int get_lives();
	public int get_score();
	public boolean get_sound();
	public void set_sound(boolean op);
	public GameWorldCollection get_collection();
	public Vector<ISelectable> get_selected();
	public int get_time();
	public static boolean get_playing() {return playing;}
	public void set_playing(boolean p);
}