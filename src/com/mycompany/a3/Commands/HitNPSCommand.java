

package com.mycompany.a3.Commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.*;

public class HitNPSCommand extends Command{
	private GameWorld gw;
	
	public HitNPSCommand(GameWorld gw) {
		super("PS(NPS)");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getKeyEvent() != -1) {
			//gw.hit_NPS();
		}
		
	}
}