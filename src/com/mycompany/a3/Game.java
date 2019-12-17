package com.mycompany.a3;
import com.codename1.charts.util.ColorUtil;

import java.util.Random;
import java.util.Vector;
import com.codename1.ui.util.UITimer;
import com.codename1.ui.plaf.Border;
import com.mycompany.a3.Commands.*;
import com.mycompany.a3.Views.MapView;
import com.mycompany.a3.Views.PointsView;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent; 
import com.codename1.ui.Button;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.Command;
import com.codename1.ui.CheckBox;

public class Game extends Form implements Runnable{
	Vector<Command> cmds;
	Vector<Button> btns;
	private GameWorld gw;
	private final int globalSpeed = 20;
	private PointsView pv;
	private MapView mv;
	
	//Commands
	private AddAsteroidCommand my_asteroid;
	
	private AddNPSCommand my_nps;
	private AddPSCommand my_ps;
	private AddSpaceStationCommand my_ss_c;
	private DecreaseSpeedCommand decrease_speed_c;
	private IncreaseSpeedCommand increase_speed_c;
	private FireMissileCommand fire_missile_c;
	private JumpCommand jump_c;
	private RotateRightCommand rr_c;
	private RotateLeftCommand rl_c;
	private TurnLeftCommand left_c;
	private TurnRightCommand right_c;
	private PrintMapCommand print_map_c;
	private PrintStatsCommand print_stats_c;
	private QuitCommand quit_c;
	private PauseCommand pause_c;
	private RefuelCommand refuel_c;
	
	private Button add_asteroidB;
	private Button add_npsB;
	private Button add_psB;
	private Button add_ssB;
	private Button increase_speedB;
	private Button decrease_speedB;
	private Button turn_leftB;
	private Button turn_rightB;
	private Button rotate_rightB;
	private Button rotate_leftB;
	private Button fire_missile_psB;
	private Button refuel;
	private Button jumpB;
	
	private Button pauseB = new Button("Play/Pause");

	public Game() {
		cmds = new Vector<Command>();
		btns = new Vector<Button>();
		this.setLayout(new BorderLayout());
		gw = new GameWorld();
		
		pv = new PointsView(new GameWorldProxy(this.gw));
		gw.addObserver(pv);
	
		mv = new MapView(new GameWorldProxy(this.gw));
		gw.addObserver(mv);
		this.add(BorderLayout.NORTH,pv);
		this.add(BorderLayout.CENTER,mv);
		gw.init();
		
		//Initialize buttons
		add_asteroidB = new Button("Add Asteroid.");
		add_npsB = new Button("Add Enemy.");
		add_psB = new Button("Add Player Ship");
		add_ssB = new Button("Add Space Station");
		increase_speedB = new Button("PS Speed(+)");
		decrease_speedB = new Button("PS Speed(-)");
		turn_leftB = new Button("PS Left");
		turn_rightB = new Button("PS Right");
		rotate_rightB = new Button("MSL Right");
		rotate_leftB = new Button("MSL Left");
		fire_missile_psB = new Button("Fire Missile PS");
		refuel = new Button("Refuel");
		jumpB = new Button("Jump");

		//Toolbar
		Toolbar ui_Toolbar = new Toolbar();
		this.setToolbar(ui_Toolbar);
		Label title = new Label("Asteroid Game");
		title.getAllStyles().setFgColor(ColorUtil.rgb(51,153,255));
		ui_Toolbar.setTitleComponent(title);
		ui_Toolbar.getAllStyles().setBgTransparency(255);
		ui_Toolbar.getAllStyles().setFgColor(ColorUtil.rgb(51,153,255));
		ui_Toolbar.getAllStyles().setBgColor(ColorUtil.rgb(255,255,255));
		ui_Toolbar.getAllStyles().setBorder(Border.createLineBorder(1,ColorUtil.rgb(51,153,255)));
		ui_Toolbar.getAllStyles().setPadding(Component.TOP, 4);
		ui_Toolbar.getAllStyles().setPadding(Component.BOTTOM, 4);
		
		
		Command new_command = new Command("New"){
			public void actionPerformed(ActionEvent e) {
				System.out.println("The New command was selected.");
			}
		};
		Command save_command = new Command("Save"){
			public void actionPerformed(ActionEvent e) {
				System.out.println("The Save command was selected.");
			}
		};
		Command undo_command = new Command("Undo"){
			public void actionPerformed(ActionEvent e) {
				System.out.println("The Undo command was selected.");
			}
		};
		Command about_command = new Command("About"){
			public void actionPerformed(ActionEvent e) {
				String about_msg = "Game Developed by: Stephen Penkov\n"
						+ "CSC133: Spring 2019\n"
						+ "4/2/19";
				Dialog.show("About", about_msg, "Close",null);
			}
		};
		CheckBox sound_command = new CheckBox();
		sound_command.getAllStyles().setBgTransparency(255);
		sound_command.getAllStyles().setBgColor(ColorUtil.WHITE);
		SoundCommand _s = new SoundCommand(gw);
		sound_command.setCommand(_s);

		quit_c = new QuitCommand(gw);
		addKeyListener('Q',quit_c);
		ui_Toolbar.addCommandToRightSideMenu(new_command);
		ui_Toolbar.addCommandToRightSideMenu(save_command);
		ui_Toolbar.addCommandToRightSideMenu(undo_command);
		ui_Toolbar.addCommandToRightSideMenu(about_command);
		ui_Toolbar.addCommandToRightSideMenu(quit_c);
		ui_Toolbar.addComponentToRightSideMenu(sound_command);
		
		/*********************************Command Container*******************************/
		Container cmd_container = new Container();
		cmd_container.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		cmd_container.setScrollable(true);
		cmd_container.getAllStyles().setPadding(Component.TOP,2);
		cmd_container.getAllStyles().setPadding(Component.BOTTOM,1);
		cmd_container.getAllStyles().setBgColor(ColorUtil.rgb(255,255,255));
		cmd_container.getAllStyles().setBgTransparency(255);
		this.add(BorderLayout.WEST,cmd_container);
		
		/*********************************Commands***************************************/
		
		//Button add_asteroidB = new Button("Add Asteroid.");
		add_asteroidB.getAllStyles().setBgColor(ColorUtil.rgb(255,255,255));
		add_asteroidB.getAllStyles().setFgColor(ColorUtil.rgb(51,153,255));
		add_asteroidB.getAllStyles().setPadding(TOP, 4);
		add_asteroidB.getAllStyles().setPadding(BOTTOM, 4);
		add_asteroidB.getAllStyles().setBgTransparency(255);
		add_asteroidB.getAllStyles().setBorder(Border.createLineBorder(2,ColorUtil.rgb(51,153,255)));
		my_asteroid = new AddAsteroidCommand(gw);
		add_asteroidB.setCommand(my_asteroid);

		cmd_container.add(add_asteroidB);
		
		//Button add_npsB = new Button("Add Enemy.");
		add_npsB.getAllStyles().setBgColor(ColorUtil.rgb(255,255,255));
		add_npsB.getAllStyles().setFgColor(ColorUtil.rgb(51,153,255));
		add_npsB.getAllStyles().setPadding(TOP, 4);
		add_npsB.getAllStyles().setPadding(BOTTOM, 4);
		add_npsB.getAllStyles().setBgTransparency(255);
		
		add_npsB.getAllStyles().setBorder(Border.createLineBorder(2,ColorUtil.rgb(51,153,255)));
		my_nps = new AddNPSCommand(gw);
		add_npsB.setCommand(my_nps);
		cmd_container.add(add_npsB);
		
		//Button add_psB = new Button("Add Player Ship");
		add_psB.getAllStyles().setBgColor(ColorUtil.rgb(255,255,255));
		add_psB.getAllStyles().setFgColor(ColorUtil.rgb(51,153,255));
		add_psB.getAllStyles().setPadding(TOP, 4);
		add_psB.getAllStyles().setPadding(BOTTOM, 4);
		add_psB.getAllStyles().setBgTransparency(255);
		add_psB.getAllStyles().setBorder(Border.createLineBorder(2,ColorUtil.rgb(51,153,255)));
		my_ps = new AddPSCommand(gw);
		add_psB.setCommand(my_ps);
		cmd_container.add(add_psB);
		
		//Button add_ssB = new Button("Add Space Station");
		add_ssB.getAllStyles().setBgColor(ColorUtil.rgb(255,255,255));
		add_ssB.getAllStyles().setFgColor(ColorUtil.rgb(51,153,255));
		add_ssB.getAllStyles().setPadding(TOP, 4);
		add_ssB.getAllStyles().setPadding(BOTTOM, 4);
		add_ssB.getAllStyles().setBgTransparency(255);
		add_ssB.getAllStyles().setBorder(Border.createLineBorder(2,ColorUtil.rgb(51,153,255)));
		my_ss_c = new AddSpaceStationCommand(gw);
		add_ssB.setCommand(my_ss_c);
		cmd_container.add(add_ssB);
		
		
		//Button increase_speedB = new Button("PS Speed(+)");
		increase_speedB.getAllStyles().setBgColor(ColorUtil.rgb(255,255,255));
		increase_speedB.getAllStyles().setFgColor(ColorUtil.rgb(51,153,255));
		increase_speedB.getAllStyles().setPadding(TOP, 4);
		increase_speedB.getAllStyles().setPadding(BOTTOM, 4);
		increase_speedB.getAllStyles().setBgTransparency(255);
		increase_speedB.getAllStyles().setBorder(Border.createLineBorder(2,ColorUtil.rgb(51,153,255)));
		increase_speed_c = new IncreaseSpeedCommand(gw);
		increase_speedB.setCommand(increase_speed_c);
		cmd_container.add(increase_speedB);
		
		//Button decrease_speedB = new Button("PS Speed(-)");
		decrease_speedB.getAllStyles().setBgColor(ColorUtil.rgb(255,255,255));
		decrease_speedB.getAllStyles().setFgColor(ColorUtil.rgb(51,153,255));
		decrease_speedB.getAllStyles().setPadding(TOP, 4);
		decrease_speedB.getAllStyles().setPadding(BOTTOM, 4);
		decrease_speedB.getAllStyles().setBgTransparency(255);
		decrease_speedB.getAllStyles().setBorder(Border.createLineBorder(2,ColorUtil.rgb(51,153,255)));
		decrease_speed_c = new DecreaseSpeedCommand(gw);
		decrease_speedB.setCommand(decrease_speed_c);
		cmd_container.add(decrease_speedB);
		
		//Button turn_leftB = new Button("PS Left");
		turn_leftB.getAllStyles().setBgColor(ColorUtil.rgb(255,255,255));
		turn_leftB.getAllStyles().setFgColor(ColorUtil.rgb(51,153,255));
		turn_leftB.getAllStyles().setPadding(TOP, 4);
		turn_leftB.getAllStyles().setPadding(BOTTOM, 4);
		turn_leftB.getAllStyles().setBgTransparency(255);
		turn_leftB.getAllStyles().setBorder(Border.createLineBorder(2,ColorUtil.rgb(51,153,255)));
		left_c = new TurnLeftCommand(gw);
		turn_leftB.setCommand(left_c);
		cmd_container.add(turn_leftB);
		
		//Button turn_rightB = new Button("PS Right");
		turn_rightB.getAllStyles().setBgColor(ColorUtil.rgb(255,255,255));
		turn_rightB.getAllStyles().setFgColor(ColorUtil.rgb(51,153,255));
		turn_rightB.getAllStyles().setPadding(TOP, 4);
		turn_rightB.getAllStyles().setPadding(BOTTOM, 4);
		turn_rightB.getAllStyles().setBgTransparency(255);
		turn_rightB.getAllStyles().setBorder(Border.createLineBorder(2,ColorUtil.rgb(51,153,255)));
		right_c = new TurnRightCommand(gw);
		turn_rightB.setCommand(right_c);
		cmd_container.add(turn_rightB);
		
		//Button rotate_rightB = new Button("MSL Right");
		rotate_rightB.getAllStyles().setBgColor(ColorUtil.rgb(255,255,255));
		rotate_rightB.getAllStyles().setFgColor(ColorUtil.rgb(51,153,255));
		rotate_rightB.getAllStyles().setPadding(TOP, 4);
		rotate_rightB.getAllStyles().setPadding(BOTTOM, 4);
		rotate_rightB.getAllStyles().setBgTransparency(255);
		rotate_rightB.getAllStyles().setBorder(Border.createLineBorder(2,ColorUtil.rgb(51,153,255)));
		rr_c = new RotateRightCommand(gw);
		rotate_rightB.setCommand(rr_c);
		cmd_container.add(rotate_rightB);
		
		//Button rotate_leftB = new Button("MSL Left");
		rotate_leftB.getAllStyles().setBgColor(ColorUtil.rgb(255,255,255));
		rotate_leftB.getAllStyles().setFgColor(ColorUtil.rgb(51,153,255));
		rotate_leftB.getAllStyles().setPadding(TOP, 4);
		rotate_leftB.getAllStyles().setPadding(BOTTOM, 4);
		rotate_leftB.getAllStyles().setBgTransparency(255);
		rotate_leftB.getAllStyles().setBorder(Border.createLineBorder(2,ColorUtil.rgb(51,153,255)));
		rl_c = new RotateLeftCommand(gw);
		rotate_leftB.setCommand(rl_c);
		cmd_container.add(rotate_leftB);
		
		//Button fire_missile_psB = new Button("Fire Missile PS");
		fire_missile_psB.getAllStyles().setBgColor(ColorUtil.rgb(255,255,255));
		fire_missile_psB.getAllStyles().setFgColor(ColorUtil.rgb(51,153,255));
		fire_missile_psB.getAllStyles().setPadding(TOP, 4);
		fire_missile_psB.getAllStyles().setPadding(BOTTOM, 4);
		fire_missile_psB.getAllStyles().setBgTransparency(255);
		fire_missile_psB.getAllStyles().setBorder(Border.createLineBorder(2,ColorUtil.rgb(51,153,255)));
		fire_missile_c = new FireMissileCommand(gw);
		fire_missile_psB.setCommand(fire_missile_c);
		cmd_container.add(fire_missile_psB);
		
		//Button refuel = new Button("Refuel");
		refuel.getAllStyles().setBgColor(ColorUtil.rgb(255,255,255));
		refuel.getAllStyles().setFgColor(ColorUtil.rgb(51,153,255));
		refuel.getAllStyles().setPadding(TOP, 4);
		refuel.getAllStyles().setPadding(BOTTOM, 4);
		refuel.getAllStyles().setBgTransparency(255);
		refuel.getAllStyles().setBorder(Border.createLineBorder(2,ColorUtil.rgb(51,153,255)));
		refuel_c = new RefuelCommand(gw);
		refuel.setCommand(refuel_c);
		cmd_container.add(refuel);
		
		//Button jumpB = new Button("Jump");
		jumpB.getAllStyles().setBgColor(ColorUtil.rgb(255,255,255));
		jumpB.getAllStyles().setFgColor(ColorUtil.rgb(51,153,255));
		jumpB.getAllStyles().setPadding(TOP, 4);
		jumpB.getAllStyles().setPadding(BOTTOM, 4);
		jumpB.getAllStyles().setBgTransparency(255);
		jumpB.getAllStyles().setBorder(Border.createLineBorder(2,ColorUtil.rgb(51,153,255)));
		jump_c = new JumpCommand(gw);
		jumpB.setCommand(jump_c);
		addKeyListener('j',jump_c);
		cmd_container.add(jumpB);
		
		pauseB.getAllStyles().setBgColor(ColorUtil.rgb(255,255,255));
		pauseB.getAllStyles().setFgColor(ColorUtil.rgb(51,153,255));
		pauseB.getAllStyles().setPadding(TOP, 4);
		pauseB.getAllStyles().setPadding(BOTTOM,4);
		pauseB.getAllStyles().setBgTransparency(255);
		pauseB.getAllStyles().setBorder(Border.createLineBorder(2,ColorUtil.rgb(51,153,255)));
		pause_c = new PauseCommand(gw);
		pauseB.setCommand(pause_c);
		cmd_container.add(pauseB);
		
		
		
		//this.getParent().revalidate();
		//Key Listeners
		print_map_c = new PrintMapCommand(gw);
		addKeyListener('m',print_map_c);
		print_stats_c = new PrintStatsCommand(gw);
		
		this.addKeyListener('P',print_stats_c);
		this.addKeyListener(-92,decrease_speed_c);
		this.addKeyListener('d',decrease_speed_c);

		this.addKeyListener(-91,increase_speed_c);
		this.addKeyListener('i',increase_speed_c);
		this.addKeyListener('h',rr_c);
		this.addKeyListener('g',rl_c);
		this.addKeyListener(-93,left_c);
		this.addKeyListener('l',left_c);
		this.addKeyListener(-94,right_c);
		this.addKeyListener('r',right_c);
		this.addKeyListener(-90,fire_missile_c);
		
		this.btns.add(add_npsB);
		this.btns.add(add_asteroidB);
		this.btns.add(add_psB);
		this.btns.add(add_ssB);
		this.btns.add(increase_speedB);
		this.btns.add(decrease_speedB);
		this.btns.add(turn_leftB);
		this.btns.add(turn_rightB);
		this.btns.add(rotate_rightB);
		this.btns.add(rotate_leftB);
		this.btns.add(fire_missile_psB);
		this.btns.add(jumpB);
		
		UITimer t = new UITimer(this);
		t.schedule(globalSpeed,true,this);
		
		this.show();
		gw.set_width(mv.getWidth());
		gw.set_height(mv.getHeight());
	}
	
	public static int genRandInt(int min, int max) {
		Random r = new Random();
		int ret = r.nextInt((max - min) + 1) + min;
		return ret;
	}
	
	public void disable_commands() {
		for(int i = 0; i < btns.size(); ++i) {
			this.btns.elementAt(i).setEnabled(false);
		}
		this.removeKeyListener(-92,decrease_speed_c);
		this.removeKeyListener('d',decrease_speed_c);

		this.removeKeyListener(-91,increase_speed_c);
		this.removeKeyListener('i',increase_speed_c);

		this.removeKeyListener(-93,left_c);
		this.removeKeyListener('l',left_c);

		this.removeKeyListener(-94,right_c);
		this.removeKeyListener('r',right_c);

		this.removeKeyListener(-90,fire_missile_c);
		this.removeKeyListener('j', jump_c);
		
		this.removeKeyListener('h',rr_c);
		this.removeKeyListener('g',rl_c);
	}
	
	public void enable_commands() {
		for(int i = 0; i < btns.size(); ++i) {
			this.btns.elementAt(i).setEnabled(true);
		}
		this.addKeyListener(-92,decrease_speed_c);
		this.addKeyListener('d',decrease_speed_c);

		this.addKeyListener(-91,increase_speed_c);
		this.addKeyListener('i',increase_speed_c);

		this.addKeyListener(-93,left_c);
		this.addKeyListener('l',left_c);

		this.addKeyListener(-94,right_c);
		this.addKeyListener('r',right_c);

		this.addKeyListener(-90,fire_missile_c);
		this.addKeyListener('j', jump_c);
		
		this.addKeyListener('h',rr_c);
		this.addKeyListener('g',rl_c);
		
	}
	
	public void run() {
		
		if(GameWorld.get_playing() == true){
			enable_commands();
			this.pauseB.setText("Pause");
			
			///
			if(gw.get_tick_count() % 200 == 0) {
				//
				//gw.add_NPS();
			}
			
			
			//*/
			
			gw.clock_tick(globalSpeed);
			gw.play_sound();
			mv.repaint();
		}else {
			this.pauseB.setText("Play");
			disable_commands();
		}
		
			
		
			
		
	}
	
	
}







