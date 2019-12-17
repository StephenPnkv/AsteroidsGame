

package com.mycompany.a3.Commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.*;

public class FireMissileCommand extends Command{
	private GameWorld gw;
	
	public FireMissileCommand(GameWorld gw) {
		super("PS Fire");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		if (e.getKeyEvent() != -1) {
			gw.fire_missile_PS();
		}
		
	}
}