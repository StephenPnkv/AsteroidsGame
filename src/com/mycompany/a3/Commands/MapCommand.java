package com.mycompany.a3.Commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.*;

public class MapCommand extends Command{
	private GameWorld gw;
	
	public MapCommand(GameWorld g) {
		super("Print Map");
		this.gw = g;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getKeyEvent() != -1) {
			gw.print_map();
		}
	}
}