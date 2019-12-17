
package com.mycompany.a3.Commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.*;

public class RefuelCommand extends Command{
	private GameWorld gw;
	
	public RefuelCommand(GameWorld gw) {
		super("Refuel");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getKeyEvent() != -1 && gw.get_playing() == false) {
			gw.refuel();
			System.out.println("Refueling . . . ");
		}
		
	}
}