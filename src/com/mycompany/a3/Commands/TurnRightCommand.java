package com.mycompany.a3.Commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.*;

public class TurnRightCommand extends Command{
	private GameWorld gw;
	
	public TurnRightCommand(GameWorld gw) {
		super("PS Right");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getKeyEvent() != -1) {
			gw.turn_PS_right();
		}
	}
}