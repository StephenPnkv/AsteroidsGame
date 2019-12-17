package com.mycompany.a3.Commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.*;

public class AddAsteroidCommand extends Command{
	private GameWorld gw;
	
	public AddAsteroidCommand(GameWorld gw) {
		super("+Asteroid");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getKeyEvent() != -1) {
			gw.add_asteroid();
		}
		
	}
}