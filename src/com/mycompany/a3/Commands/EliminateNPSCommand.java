package com.mycompany.a3.Commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.*;

public class EliminateNPSCommand extends Command{
	private GameWorld gw;
	
	public EliminateNPSCommand(GameWorld gw) {
		super("PS Missile(NPS)");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getKeyEvent() != -1) {
			//gw.eliminate_NPS();
		}
		
	}
}