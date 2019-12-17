package com.mycompany.a3.Commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.*;

public class AddSpaceStationCommand extends Command{
	private GameWorld gw;
	
	public AddSpaceStationCommand(GameWorld gw) {
		super("+Space Station");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getKeyEvent() != -1) {
			gw.add_SpaceStation();
		}
		
	}
}