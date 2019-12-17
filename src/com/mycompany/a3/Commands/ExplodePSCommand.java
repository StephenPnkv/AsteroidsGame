

package com.mycompany.a3.Commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.*;

public class ExplodePSCommand extends Command{
	private GameWorld gw;
	
	public ExplodePSCommand(GameWorld gw) {
		super("NPS Missile(PS)");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getKeyEvent() != -1) {
			//gw.explode_PS();
		}
		
	}
}