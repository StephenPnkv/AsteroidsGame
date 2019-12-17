package com.mycompany.a3.Views;
import com.mycompany.a3.GameWorld;
import java.util.Observable;
import java.util.Vector;
import java.util.Observer;
import com.codename1.ui.Container;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.mycompany.a3.GameObjects.*;
import com.mycompany.a3.Interfaces.*;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;


public class MapView extends Container implements Observer{
	
	private IGameWorld gw;
	public MapView(IGameWorld g) {
		this.gw = g;
		Container map_view = new Container();
		map_view.setLayout(new FlowLayout());
		map_view.getAllStyles().setBgColor(ColorUtil.BLACK);
		map_view.getAllStyles().setFgColor(ColorUtil.BLACK);
		map_view.getAllStyles().setBgTransparency(100);
		this.getAllStyles().setBorder(Border.createLineBorder(2,ColorUtil.rgb(51,153,255)));
		this.add(map_view);
	}
	
	@Override 
	public void paint(Graphics g){
		super.paint(g);
		Point pCmpRelPrnt = new Point(getX(),getY());
		IIterator it = gw.get_collection().getIterator();
		IDrawable d;
		while(it.hasNext()) {
			GameObject obj = (GameObject)it.getNext();
			
			if(obj instanceof IDrawable) {
				
				if(obj instanceof PlayerShip) {
					d = (IDrawable)obj;
					d.draw(g, pCmpRelPrnt);
					
				}
				d = (IDrawable)obj;
				d.draw(g, pCmpRelPrnt);
			}
		}
	}
	
	@Override
	public void update(Observable obs, Object obj) {
		this.gw = (IGameWorld)obj;
		//Print map every 100 msec
		if(gw.get_tick_count() % 100 == 0)
			gw.print_map();
		this.repaint();
	}
	
	@Override 
	public void pointerPressed(int x, int y) {
		x = x - getParent().getAbsoluteX();
		y = y - getParent().getAbsoluteY();
		Point pPtrRelPrnt = new Point(x,y);
		Point pCmpRelPrnt = new Point(getX(),getY());
		
		Vector<ISelectable> v = gw.get_selected();
		if(GameWorld.get_playing() == true) {
			;
		}else {
			for(int i = 0; i < v.size(); ++i) {
				if(v.elementAt(i).contains(pPtrRelPrnt, pCmpRelPrnt)){
					v.elementAt(i).setSelected(true);
					GameObject obj = (GameObject)v.elementAt(i);
					System.out.println("Object id: " + obj.get_id() + " was selected!");
				}else {
					v.elementAt(i).setSelected(false);
				}
			}
		}
		this.repaint();
	}
}

