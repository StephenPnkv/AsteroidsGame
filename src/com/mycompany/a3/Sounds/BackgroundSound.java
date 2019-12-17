package com.mycompany.a3.Sounds;
import java.io.InputStream;
import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;
import com.codename1.ui.util.UITimer;



public class BackgroundSound implements Runnable{
	
	private Media m;
	
	public BackgroundSound(String fileName) {
		try{
			InputStream is = Display.getInstance().getResourceAsStream(getClass(),
			"/"+fileName);
			m = MediaManager.createMedia(is, "audio/mp3", this);
			}
			catch(Exception e)
			{
			e.printStackTrace();
			}
	}
	
	public void play() {
		m.play();
	}
	public void pause() {
		m.pause();
	}
	
	public void run() {
		m.setTime(0);
		m.setVolume(3);
		m.play();
	}
}
