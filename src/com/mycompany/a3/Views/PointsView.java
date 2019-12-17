package com.mycompany.a3.Views;
import com.codename1.ui.Container;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;
import com.mycompany.a3.Interfaces.IGameWorld;

import java.util.*;

public class PointsView extends Container implements Observer{
	
	private IGameWorld gw;
	private Label total_points;
	private Label tick_count;
	private Label sound_on;
	private Label total_lives;
	private Label missile_count;
	
	public PointsView(IGameWorld g) {
		this.gw = g;
		Container pv_container = new Container();
		pv_container.setLayout(new GridLayout(1,10));
		pv_container.getAllStyles().setBgColor(ColorUtil.rgb(255,255,255));
		pv_container.getAllStyles().setBgTransparency(255);
		//pv_container.getAllStyles().setBorder(Border.createLineBorder(2,ColorUtil.rgb(0,0,0)));
		
		Label pts_txt = new Label("Points: ");
        pts_txt.getAllStyles().setBgColor(ColorUtil.WHITE);
        pts_txt.getAllStyles().setFgColor(ColorUtil.BLACK);
        pts_txt.getAllStyles().setBgTransparency(255);
        pts_txt.getAllStyles().setBorder(Border.createLineBorder(1,ColorUtil.rgb(51,153,255)));
        pv_container.add(pts_txt);
        
		total_points = new Label("");
		total_points.getAllStyles().setBgColor(ColorUtil.WHITE);
        total_points.getAllStyles().setFgColor(ColorUtil.BLACK);
        total_points.getAllStyles().setBgTransparency(255);
        total_points.getAllStyles().setBorder(Border.createLineBorder(1,ColorUtil.rgb(51,153,255)));
        pv_container.add(total_points);
        
        Label m_txt = new Label("Missiles: ");
        m_txt.getAllStyles().setBgColor(ColorUtil.WHITE);
        m_txt.getAllStyles().setFgColor(ColorUtil.BLACK);
        m_txt.getAllStyles().setBgTransparency(255);
        m_txt.getAllStyles().setBorder(Border.createLineBorder(1,ColorUtil.rgb(51,153,255)));
        pv_container.add(m_txt);
        
        missile_count = new Label("");
        missile_count.getAllStyles().setBgColor(ColorUtil.WHITE);
        missile_count.getAllStyles().setFgColor(ColorUtil.BLACK);
        missile_count.getAllStyles().setBgTransparency(255);
        missile_count.getAllStyles().setBorder(Border.createLineBorder(1,ColorUtil.rgb(51,153,255)));
        pv_container.add( missile_count);
        
        Label lives_txt = new Label("Lives: ");
        lives_txt.getAllStyles().setBgColor(ColorUtil.WHITE);
        lives_txt.getAllStyles().setFgColor(ColorUtil.BLACK);
        lives_txt.getAllStyles().setBgTransparency(255);
        lives_txt.getAllStyles().setBorder(Border.createLineBorder(1,ColorUtil.rgb(51,153,255)));
        pv_container.add(lives_txt);
        
        total_lives = new Label("");
        total_lives.getAllStyles().setBgColor(ColorUtil.WHITE);
        total_lives.getAllStyles().setFgColor(ColorUtil.BLACK);
        total_lives.getAllStyles().setBgTransparency(255);
        total_lives.getAllStyles().setBorder(Border.createLineBorder(1,ColorUtil.rgb(51,153,255)));
        pv_container.add(total_lives);
        
        Label tick_txt = new Label("Time: ");
        tick_txt.getAllStyles().setBgColor(ColorUtil.WHITE);
        tick_txt.getAllStyles().setFgColor(ColorUtil.BLACK);
        tick_txt.getAllStyles().setBgTransparency(255);
        tick_txt.getAllStyles().setBorder(Border.createLineBorder(1,ColorUtil.rgb(51,153,255)));
        pv_container.add(tick_txt);
        
        tick_count = new Label("");
        tick_count.getAllStyles().setBgColor(ColorUtil.WHITE);
        tick_count.getAllStyles().setFgColor(ColorUtil.BLACK);
        tick_count.getAllStyles().setBgTransparency(255);
        tick_count.getAllStyles().setBorder(Border.createLineBorder(1,ColorUtil.rgb(51,153,255)));
        pv_container.add(tick_count);
        
        Label s_txt = new Label("Sound: ");
        s_txt.getAllStyles().setBgColor(ColorUtil.WHITE);
        s_txt.getAllStyles().setFgColor(ColorUtil.BLACK);
        s_txt.getAllStyles().setBgTransparency(255);
        s_txt.getAllStyles().setBorder(Border.createLineBorder(1,ColorUtil.rgb(51,153,255)));
        pv_container.add(s_txt);
        
        sound_on = new Label("");
        sound_on.getAllStyles().setBgColor(ColorUtil.WHITE);
        sound_on.getAllStyles().setFgColor(ColorUtil.BLACK);
        sound_on.getAllStyles().setBgTransparency(255);
        sound_on.getAllStyles().setBorder(Border.createLineBorder(1,ColorUtil.rgb(51,153,255)));
        pv_container.add(sound_on);
        this.add(pv_container);
	}
	
	@Override
	public void update(Observable obs, Object obj){
		IGameWorld g = (IGameWorld)obj;
		
		int m_count = g.get_missile_count();
		missile_count.setText(""+m_count+"");
		
		int score = g.get_score();
		total_points.setText(""+score+"");
		
		int lives = g.get_lives();
		if(lives <= 0) {
			total_lives.setText("" + 0);
		}else {
			total_lives.setText(""+lives+"");
		}
		int t_count = g.get_time();
		tick_count.setText(""+t_count+"");
		
		boolean sound = g.get_sound();
		if(sound == true) 
			sound_on.setText("ON");
		else
			sound_on.setText("OFF");
		
		
		this.repaint();
	}
}