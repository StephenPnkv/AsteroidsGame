package com.mycompany.a3.Commands;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.*;

public class QuitCommand extends Command{
	private GameWorld gw;
	
	public QuitCommand(GameWorld gw) {
		super("Quit Game");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
			if(Dialog.show("Confirm Quit", "Are you sure you want to quit?", "Ok", "Cancel")) {
				if (e.getKeyEvent() != -1)
					gw.quit_game();
    	  	}else {
    		  System.out.println("Ok let's continue playing then!");
    	  	}
	}
}