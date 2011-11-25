package main;

import java.io.IOException;

public class Beep {

	@SuppressWarnings("restriction")
	public static void main(String[] args) throws IOException {
		sun.audio.AudioPlayer.player.start(Beep.class.getResourceAsStream("beep.wav"));
	}

}
