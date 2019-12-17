package com.mycompany.a3.Commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.*;

public class IncreaseSpeedCommand extends Command{
	private GameWorld gw;
	
	public IncreaseSpeedCommand(GameWorld gw) {
		super("PS Speed(+)");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getKeyEvent() != -1) {
			gw.increase_speed();
		}
		
	}
}