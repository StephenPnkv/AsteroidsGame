package com.mycompany.a3.Commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.*;

public class AddPSCommand extends Command{
	private GameWorld gw;
	
	public AddPSCommand(GameWorld gw) {
		super("+PS");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getKeyEvent() != -1) {
			gw.add_PlayerShip();
		}
		
	}
}