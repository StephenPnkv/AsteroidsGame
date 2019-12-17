package com.mycompany.a3.Commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.*;

public class SoundCommand extends Command{
	private GameWorld gw;
	
	public SoundCommand(GameWorld gw) {
		super("Sound Off");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getKeyEvent() != -1) {
			if(gw.get_sound() == true)
				gw.set_sound(false);
			else
				gw.set_sound(true);
		}
		
	}
}