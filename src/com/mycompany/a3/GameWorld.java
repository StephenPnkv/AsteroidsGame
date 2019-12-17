package com.mycompany.a3;
import java.util.Vector;
import com.codename1.ui.geom.Point2D;
import java.lang.String;
import java.util.Observable;
import com.codename1.ui.Dialog;
import com.mycompany.a3.GameObjects.*;
import com.mycompany.a3.Interfaces.*;
import com.mycompany.a3.Sounds.*;

public class GameWorld extends Observable implements IGameWorld{
	private Vector<ISelectable> selectables;
	public Vector<ISelectable> get_selected(){return this.selectables;}
	public void add_selectable(ISelectable obj) {
		this.selectables.add(obj);
	}
	
	private Vector<String> collision_reports;
	private Vector<ICollider> collision_v;
	
	private GameWorldCollection space_object_collection;
	public GameWorldCollection get_collection() {return this.space_object_collection;}

	private int player_score; // 1 for asteroid, 5 for nps
	private int player_lives;
	private int tick_count;
	private static int width,height;//Width and height of the map view
	private boolean sound_on;
	
	//Assign Id
	private static int id;
	public static int allocate_id() {return ++id;}
	
	
	private FireMissileSound fm_sound = new FireMissileSound("SpaceGun.wav");;
	private RotateLauncherSound rl_sound = new RotateLauncherSound("RotateLauncher.wav");
	private YouLoseSound yl_sound = new YouLoseSound("ALARM.WAV");
	private ExplosionSound exp_sound = new ExplosionSound("Explode.WAV");
	private BackgroundSound b_sound = new BackgroundSound("bensound-dubstep.mp3");
	private ReloadSound rel_sound = new ReloadSound("Reload.wav");
	private NoAmmoSound no_ammo_sound = new NoAmmoSound("NoAmmo.wav");
	private ShipExplodeSound ship_exp_sound = new ShipExplodeSound("ShipExplode.wav");
	//Functions to pause the game.
	private static boolean playing;
	public static boolean get_playing() {return playing;}
	public void set_playing(boolean p) {playing = p;}
	public void pause_play() {
		if(get_playing() == true) {
			set_playing(false);
			b_sound.pause();
		}
		else {
			set_playing(true);
			b_sound.play();
		}
			
	}
	
	public void play_sound() {
		if(get_sound() == true)
			b_sound.play();
		else
			b_sound.pause();
	}
	
	
	public static int get_height() {return height;}
	public void set_height(int h) {height = h;}
	
	public static int get_width() {return width;}
	public void set_width(int w) {width = w;}
	
	public boolean get_sound() {return this.sound_on;}
	public void set_sound(boolean option) {
		this.sound_on = option;
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	//Getters and setters for game world object
	public void inc_tick_count() {this.tick_count = this.get_tick_count() + 1;}
	public void init_tick_count() {this.tick_count = 0;}
	public int get_tick_count() {return this.tick_count;}
	
	private static int elapsed_time;
	public int get_time(){
		return elapsed_time;
	}
	
	public int get_lives() {return this.player_lives;}
	public void init_lives() {this.player_lives = 3;}
	public void set_lives(int count){this.player_lives = count;}
	public void decrement_lives() {
		--this.player_lives;
		if(this.get_lives() < 0) {
			this.game_over();
		}
	}
	
	public void increase_score(int pts) {this.player_score += pts;}
	public void init_score() {this.player_score = 0;}
	public int get_score() {return this.player_score;}
	

	
	public int get_missile_count() {
		IIterator it = space_object_collection.getIterator();
		while(it.hasNext()) {
			GameObject obj = (GameObject)it.getNext();
			if(obj instanceof PlayerShip) {
				PlayerShip ps = (PlayerShip)obj;
				return ps.get_missile_count();
			}
				
		}
		return 0;
	}
	
	public GameWorld() {
		selectables = new Vector<ISelectable>();
		space_object_collection = new GameWorldCollection();
		this.player_score = 0;
		this.set_lives(3);
		collision_reports = new Vector<String>();
		collision_v = new Vector<ICollider>();
	}
	
	public void init() {
		//Tested
		init_tick_count();
		init_score();
		init_lives();
		pause_play();
		set_sound(true);
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	public void add_asteroid() {
		//Tested
		//A new asteroid is added to the game.
		Asteroid new_Asteroid = new Asteroid(get_width(),get_height());
		new_Asteroid.set_id(allocate_id());
		space_object_collection.add(new_Asteroid);
		selectables.add(new_Asteroid);
		System.out.println("An asteroid was created!");
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	public void add_PlayerShip() {
		//Tested
		//Add a new Player ship to the game.
		PlayerShip new_PS = new PlayerShip(get_width()/2,get_height()/2);
		new_PS.set_id(allocate_id());
		IIterator it = space_object_collection.getIterator();
		while(it.hasNext()) {
			if(it.getNext() instanceof PlayerShip) {
				System.out.println("A player ship already exists.");
				return;
			}
		}
		space_object_collection.add(new_PS);
		System.out.println("A player ship has been created!");
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	public void add_NPS() {
		//Tested
		//A new NPS is added to the game.
		NPS new_NPS = new NPS(get_width(),get_height());
		new_NPS.set_id(allocate_id());
		Missile nps_missile = new_NPS.fire_missile(this.get_ps_loc());
		nps_missile.set_id(allocate_id());
		space_object_collection.add(new_NPS);
		selectables.add(new_NPS);
		space_object_collection.add(nps_missile);
		selectables.add(nps_missile);
		System.out.println("A non-player ship has been created!");
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}

	public void add_SpaceStation() {
		//Tested
		//A new asteroid is added to the game.
		SpaceStation new_Station = new SpaceStation(get_width(),get_height());
		new_Station.set_id(allocate_id());
		space_object_collection.add(new_Station);
		System.out.println("A space station has been created!");
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}

	public void increase_speed() {
		//Tested
		//The player ship increases speed by one.
		
		IIterator it = space_object_collection.getIterator();
		while(it.hasNext()) {
			GameObject obj = it.getNext();
			if(obj instanceof PlayerShip) {
				PlayerShip PS = (PlayerShip)obj;
				if(PS.get_speed() > 15) {
					System.out.println("The ship is at maximum speed!");
					return;
				}
				PS.set_speed(PS.get_speed() + 1);
				System.out.println("The ship speed was increased.");
				this.setChanged();
				this.notifyObservers(new GameWorldProxy(this));
				return;
			}
		}
		
		System.out.println("A player ship must be created first!");

	}
	
	public void decrease_speed() {
		//Tested
		//The player ship decreases speed by one.
		IIterator it = space_object_collection.getIterator();
		while(it.hasNext()) {
			GameObject obj = it.getNext();
			if(obj instanceof PlayerShip) {
				PlayerShip PS = (PlayerShip)obj;
				if(PS.get_speed() <= 0) {
					System.out.println("The ship is already stalled!");
					return;
				}
				System.out.println("The ship speed was decreased.");
				PS.set_speed(PS.get_speed() - 1);
				this.setChanged();
				this.notifyObservers(new GameWorldProxy(this));
				return;
			}
		}
		System.out.println("A player ship must be created first!");

	}
	
	public void turn_PS_left() {
		//Tested
		//The player ship turns left by a small amount.
		IIterator it = space_object_collection.getIterator();
		while(it.hasNext()) {
			GameObject obj = it.getNext();
			if(obj instanceof PlayerShip) {
				System.out.println("The ship turned left.");
				PlayerShip PS = (PlayerShip)obj;
				PS.set_direction(PS.get_direction() - 10);
				this.setChanged();
				this.notifyObservers(new GameWorldProxy(this));
				return;
			}
		}
		
		System.out.println("A player ship must be created first!");
		
	}
	 
	public void turn_PS_right() {
		//Tested
		//The player ship will turn right by a small amount.
		IIterator it = space_object_collection.getIterator();
		while(it.hasNext()) {
			GameObject obj = it.getNext();
			if(obj instanceof PlayerShip) {
				System.out.println("The ship turned right.");
				PlayerShip PS = (PlayerShip)obj;
				PS.set_direction(PS.get_direction() + 10);
				this.setChanged();
				this.notifyObservers(new GameWorldProxy(this));
				return;
			}
		}
		System.out.println("A player ship must be created first!");
	}
	
	public void rotate_right() {
		//Tested
				//The ship's missile launcher is rotated by a small angle theta.
				IIterator it = space_object_collection.getIterator();
				while(it.hasNext()) {
					GameObject obj = it.getNext();
					if(obj instanceof PlayerShip) {
						PlayerShip PS = (PlayerShip)obj;
						PS.rotate_launcher(+15);
						rl_sound.play();
						System.out.println("Launcher rotated right 15 degrees.");
						this.setChanged();
						this.notifyObservers(new GameWorldProxy(this));
						return;
					}
				}
				System.out.println("A player ship must be created first!");
	}
	
	public void rotate_left() {
		//Tested
				//The ship's missile launcher is rotated by a small angle theta.
				IIterator it = space_object_collection.getIterator();
				while(it.hasNext()) {
					GameObject obj = it.getNext();
					if(obj instanceof PlayerShip) {
						PlayerShip PS = (PlayerShip)obj;
						PS.rotate_launcher(-15);
						rl_sound.play();
						System.out.println("Launcher rotated left 15 degrees.");
						this.setChanged();
						this.notifyObservers(new GameWorldProxy(this));
						return;
					}
				}
				System.out.println("A player ship must be created first!");
	}
	
	public void fire_missile_PS() {
		//Tested
		//If an ps, exists and its missile count > 0, it launches a missile.
		PlayerShip ps = new PlayerShip(get_width()/2,get_height()/2);
		if(!exists(ps)) {
			//System.out.println("A player ship must be created first!");
			return;
		}
		
		IIterator it = space_object_collection.getIterator();
		while(it.hasNext()) {
			GameObject obj = it.getNext();
			if(obj instanceof PlayerShip) {
				ps = (PlayerShip)obj;
				if(ps.get_missile_count() == 0) {
					no_ammo_sound.play();
					System.out.println("The player ship is out of missiles!");
					return;
				}
				
				Missile my_missile = ps.fire_missile();
				my_missile.set_id(allocate_id());
				selectables.add(my_missile);
				space_object_collection.add(my_missile);
				System.out.println("The " + my_missile.whose_missile() + " missile has been launched.");
				fm_sound.play();
				this.setChanged();
				this.notifyObservers(new GameWorldProxy(this));
				return;
			}
		}
		
		
	}
	
	public void nps_fire_again() {
		IIterator it = space_object_collection.getIterator();
		while(it.hasNext()) {
			GameObject obj = (GameObject)it.getNext();
			if(obj instanceof NPS && ((NPS) obj).get_missile_count() > 0) {
				NPS n = (NPS)obj;
				n.setFired(false);
			}
		}
	}
	
	public void launch_missile_NPS() {
		//All enemy ships will launch their missiles.
		NPS _nps = new NPS(get_width(),get_height());
		if(!exists(_nps)) {
			return;
		}
		
		IIterator it = space_object_collection.getIterator();
		while(it.hasNext()) {
			GameObject obj = it.getNext();
			if(obj instanceof NPS
					&& ((NPS)obj).get_missile_count() > 0) {
						
						_nps = (NPS)obj;
						if(_nps.isFired())
							;
						else {
							Missile my_missile = _nps.fire_missile(this.get_ps_loc());
							my_missile.set_id(allocate_id());
							selectables.add(my_missile);
							space_object_collection.add(my_missile);
							System.out.println("The " + my_missile.whose_missile() + " missile has been launched.");
							this.setChanged();
							this.notifyObservers(new GameWorldProxy(this));
							_nps.setFired(true);
						}
			}
			nps_fire_again();
		}
		

	}
	
	public void jump() {
		//The player ship is now located at the center of the game world.
		
		IIterator it = space_object_collection.getIterator();
		while(it.hasNext()) {
			GameObject obj = (GameObject)it.getNext();
			if(obj instanceof PlayerShip) {
				PlayerShip PS = (PlayerShip)obj;
				System.out.println("The player ship has jumped through hyperspace!");
				PS.set_location(get_width()/ 2, get_height() /2); //center of the game world.
				this.setChanged();
				this.notifyObservers(new GameWorldProxy(this));
				return;
			}
		}
		System.out.println("A player ship must be created first!");
	}
	
	public void load_missiles() {
		//Tested
		//The function reloads the player ship's missiles.
		IIterator it = space_object_collection.getIterator();
		while(it.hasNext()) {
			GameObject obj = it.getNext();
			if(obj instanceof PlayerShip) {
				PlayerShip PS = (PlayerShip)obj;
				PS.set_missile_count(10); //reload missiles to the maximum of 10
				System.out.println("The ship has successfully reloaded it's missiles!");
				this.setChanged();
				this.notifyObservers(new GameWorldProxy(this));
				return;
			}
		}
		
		System.out.println("A player ship must be created first!");

	}
	
	public void refuel() {
		IIterator it = space_object_collection.getIterator();
		while(it.hasNext()) {
			ISelectable s = (ISelectable)it.getNext();
			if(s instanceof Missile) {
				Missile m = (Missile)s;
				if (s.isSelected() == true) {
					int id = ((GameObject)s).get_id();
					((Missile) s).set_fuel_level(10);
					System.out.println("Missile id: " + id + " has been refueled!");
				}
			}
		}
	}
	
	public String object_type(GameObject obj) {
		String res = new String("");
		if(obj instanceof Asteroid)
			res = "Asteroid";
		else if(obj instanceof NPS)
			res = "Non-Player Ship";
		else if(obj instanceof PlayerShip)
			res = "Player-Ship";
		else if(obj instanceof Missile)
			res = "Missile";
		else if(obj instanceof SpaceStation)
			res = "Space Station";
		
		return res;
		
	}
	
	public void handle_collisions() {
		//Handle the collision, keep track of which objects collided.
				IIterator it = space_object_collection.getIterator();
				while(it.hasNext()) {
					ICollider cur = (ICollider)it.getNext();
					IIterator iter2 = space_object_collection.getIterator();
					while(iter2.hasNext()) {
						ICollider other = (ICollider)iter2.getNext();
						if(other != cur && other != null) {
							if(cur.collidesWith(other)) {
								
								((GameObject)cur).set_col(true);
								((GameObject)other).set_col(true);
								//If a player ship struck any object, decrement lives
								if(cur instanceof PlayerShip && other instanceof Asteroid
										|| other instanceof PlayerShip && cur instanceof Asteroid
										|| cur instanceof NPS && other instanceof PlayerShip
										|| other instanceof NPS && other instanceof PlayerShip
										|| cur instanceof Asteroid && other instanceof PlayerShip
										|| other instanceof Asteroid && cur instanceof PlayerShip)
									{
										print_col_report(cur,other);
										decrement_lives();
									}
								
								//SpaceStations don't collide with any objects
								if(cur instanceof SpaceStation || other instanceof SpaceStation) {
									((GameObject)cur).set_col(false);
									((GameObject)other).set_col(false);
								}
								
								//If a player ship reaches a space station, its missile count is set to max.
								if(cur instanceof SpaceStation && other instanceof PlayerShip
										|| other instanceof SpaceStation && cur instanceof PlayerShip) {
									load_missiles();
									rel_sound.play();
								}
								
								//Play sound when missile strikes an asteroid. Increment score if player struck asteroid.
								if(cur instanceof Asteroid && other instanceof Missile
										|| cur instanceof Missile && other instanceof Asteroid
										|| cur instanceof Asteroid && other instanceof Asteroid) {
									
									exp_sound.play();
									if(cur instanceof Missile) {
										Missile m = (Missile)cur; 
										if(m.get_ownership() == "Player") {
											print_col_report(cur,other);
											this.increase_score(5);
										}
									}else if(other instanceof Missile) {
										Missile m = (Missile)other; 
										if(m.get_ownership() == "Player") {
											print_col_report(cur,other);
											this.increase_score(5);
										}
									}
								}
								
								if(cur instanceof Missile && other instanceof Missile) {
									((GameObject)cur).set_col(false);
									((GameObject)other).set_col(false);
									continue;
								}
								
								//If a missile strikes an nps, play explosion sound, increment score if struck by player.
								//Enemy missiles don't collide with enemy ships.
								if(cur instanceof NPS && other instanceof Missile
										|| cur instanceof Missile && other instanceof NPS) {
									
									if(cur instanceof Missile) {
										Missile m = (Missile)cur; 
										if(m.get_ownership() == "Player") {
											print_col_report(cur,other);
											this.increase_score(10);
											ship_exp_sound.play();
										}else {
											((GameObject)cur).set_col(false);
											((GameObject)other).set_col(false);
										}
									}else if(other instanceof Missile) {
										Missile m = (Missile)other; 
										if(m.get_ownership() == "Player") {
											print_col_report(cur,other);
											this.increase_score(10);
											ship_exp_sound.play();
										}else {
											((GameObject)cur).set_col(false);
											((GameObject)other).set_col(false);
										}
									}
									
									
								}
								
								
								//A Missile hit the Player ship, resolve if own missile, else player loses a life.
								if(cur instanceof PlayerShip && other instanceof Missile
										|| cur instanceof Missile && other instanceof PlayerShip) {
									if(cur instanceof Missile && ((Missile)cur).get_ownership() == "Player") {
										((GameObject)cur).set_col(false);
										((GameObject)other).set_col(false);
									}
									else if(other instanceof Missile && ((Missile)other).get_ownership() == "Player") {
										((GameObject)cur).set_col(false);
										((GameObject)other).set_col(false);
									}
									else {
										ship_exp_sound.play();
										decrement_lives();
										cur.handleCollision(other);
										print_col_report(cur,other);
										

									}
										
								}
								
								
								
							}
						}
					}

				}
				//Remove collisions from the colleciton
				space_object_collection.remove_collisions();
	}
	
	public void print_col_report(ICollider cur, ICollider other) {
		String report = object_type((GameObject)cur) + ": " + ((GameObject)cur).get_id() + " and " + object_type((GameObject)other) + ": " + ((GameObject)other).get_id() + " collided!";
		collision_reports.add(report);
		System.out.println(object_type((GameObject)cur) + ": " + ((GameObject)cur).get_id() + " and " + object_type((GameObject)other) + ": " + ((GameObject)other).get_id() + " collided!");
	}
	
	public void clock_tick(int globalSpeed) {
		//Tested
		//All moveable objects are told to update their positions 
		//according to their current direction and speed. Missiles
		//with fuel level 0 are removed, and Space Stations blink
		//according to their blink rate.
		
		
		this.inc_tick_count();
		if(get_tick_count() % 60 == 0) {
			++elapsed_time;
			decrement_fuel();
		}
		
		//Launch enemy missiles at player location every 5 game seconds.
		if(elapsed_time % 5 == 0) {
			launch_missile_NPS();
			
		}

		
		//System.out.println("Elapsed time: " + elapsed_time);
		IIterator it = space_object_collection.getIterator();
		while(it.hasNext()) {
			GameObject obj = (GameObject)it.getNext();
			if(obj instanceof IMoveable) {
				IMoveable movable = (IMoveable)obj;
				movable.move(globalSpeed);
			}
		}
		//Handle collisions
		handle_collisions();
		
		//Toggle the blinking light of the space station if
		//the tick count mod blink rate equals to zero.
		it = space_object_collection.getIterator();
		while(it.hasNext()) {
			GameObject obj = (GameObject)it.getNext();
			if(obj instanceof SpaceStation) {
				SpaceStation s = (SpaceStation)obj;
				if(this.get_time() % s.get_BlinkRate() == 0)
					s.toggle_light();
				else
					s.untoggle_light();
			}
		}
		
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	
	public void print_stats() {
		//Tested
		//(1) current score (number of points the player has earned)
		//(2) number of missiles currently in the ship
		//(3) current elapsed time
		System.out.println("********************************************************************************************************************************");
		System.out.println("                                      				Statistics                                                              ");
		System.out.println("********************************************************************************************************************************");		
		System.out.println("Points: " + this.get_score() + "\nLives: " + this.get_lives());
		IIterator obj_it = space_object_collection.getIterator();
		PlayerShip PS = new PlayerShip(get_width()/2,get_height()/2);
		
		if(!exists(PS)) {
			System.out.println("Missile Count: 0");
		}else {
			while(obj_it.hasNext()) {
				GameObject obj = obj_it.getNext();
				if(obj instanceof PlayerShip) {
					PS = (PlayerShip)obj;
					System.out.println("Missile Count: " + PS.get_missile_count());
					break;
				}
			
			}
		}
		
		System.out.println("Tick Count: " + this.get_tick_count());
		boolean sound = this.get_sound();
		if(sound == true) 
			System.out.println("Sound: ON");
		else
			System.out.println("Sound OFF");
		
		System.out.println("Printing collision reports.");
		Object [] list = collision_reports.toArray();
		collision_reports.toArray();
		for(int i = 0; i < list.length; ++i) {
			System.out.println(list[i]);
		}
	
		System.out.println("********************************************************************************************************************************");
		System.out.println("                                       					                                                                        ");
		System.out.println("********************************************************************************************************************************");
	}
	
	public void print_map() {
		//Tested
		//Function that prints the map, or prints that its empty.
		System.out.println("********************************************************************************************************************************");
		System.out.println("                                       				Map                                                                     ");
		System.out.println("********************************************************************************************************************************");

		if(space_object_collection.get_size() == 0) {
			System.out.println("The game world is empty . . .");
			return;
		}
		for(int i = 0; i < collision_v.size(); ++i) {
			System.out.println(((GameObject)collision_v.elementAt(i)).get_id() + " is in the vector.");
		}
		
		IIterator obj_it = space_object_collection.getIterator();
		while(obj_it.hasNext()) {
			System.out.println(obj_it.getNext().toString());
		}
		System.out.println("********************************************************************************************************************************");
		System.out.println("                                      					                                                                       ");
		System.out.println("********************************************************************************************************************************");	
		}
	
	public void quit_game() {
		System.exit(0);
	}
	
	public boolean exists(GameObject o) {
		//Function that checks whether an instance of obj exists in the game world.
		
		IIterator it = space_object_collection.getIterator();
		while(it.hasNext()) {
			GameObject obj = (GameObject)it.getNext();
			if(obj instanceof PlayerShip && o instanceof PlayerShip)
				return true;
			else if(obj instanceof Missile && o instanceof Missile)
				return true;
			else if(obj instanceof NPS && o instanceof NPS)
				return true;
			else if(obj instanceof Asteroid && o instanceof Asteroid)
				return true;
		}
		return false;
		
	}
	
	
	
	public void game_over() {
		yl_sound.play();
		int score = get_score();
		if(Dialog.show("Game Over!", "Your Score: " + score + "\nContinue Playing?", "Quit", "Ok")) {
			System.exit(0);
		}else {
			System.out.println("Ok let's continue playing then!");
			add_PlayerShip();
			this.init_score();
			this.init_lives();
			this.setChanged();
			this.notifyObservers(new GameWorldProxy(this));
			
		}
	}
	
	public void decrement_fuel() {
		//Every missile’s fuel level is reduced by one and any missiles
				//which are now out of fuel are removed from the game;
				IIterator it = space_object_collection.getIterator();
					while(it.hasNext()) {
						GameObject obj = (GameObject)it.getNext();
						if(obj instanceof Missile) {
							Missile m = (Missile)obj;
								m.set_fuel_level(m.get_fuel_level() - 1);
								if(m.get_fuel_level() <= 0) {
									int index = it.get_index();
									space_object_collection.remove_at(index);
									it = space_object_collection.getIterator();
								}
						}
					}
	}
	
	public Point2D get_ps_loc() {
		//Returns location of the player ship, or the center of the map if the ship
		//does not yet exist.
		IIterator it = this.space_object_collection.getIterator();
		while(it.hasNext()) {
			GameObject obj = (GameObject)it.getNext();
			if(obj instanceof PlayerShip) {
				double x = ((PlayerShip)obj).get_X();
				double y = ((PlayerShip)obj).get_Y();
				return new Point2D(x, y);
			}
		}
		return new Point2D(get_width()/2, get_height()/2);
	}

	
}






