package com.mycompany.a3.Commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.*;

public class RotateRightCommand extends Command{
	private GameWorld g;
	
	public RotateRightCommand(GameWorld gw) {
		super("MSL Right");
		this.g = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getKeyEvent() != -1) {
			g.rotate_right();
		}
	}
}