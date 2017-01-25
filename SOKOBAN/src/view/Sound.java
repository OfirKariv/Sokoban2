package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

public class Sound {

	/*
	 * 
	 * 
	 * OMER, YOU CAN IGNORE IT MEANWHILE.. IM TRYING TO MAKE SOUNDS
	 * 
	 * 
	 * 
	 */

	public static void music() {

		AudioPlayer MGP = AudioPlayer.player;
		AudioStream BGM;
		AudioData MD;
		ContinuousAudioDataStream loop = null;

		try {
			BGM = new AudioStream(new FileInputStream("./music/test.wav"));
			MD = BGM.getData();
			loop = new ContinuousAudioDataStream(MD);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		MGP.start(loop);

	}

}
