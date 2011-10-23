package main;

import java.io.IOException;

import javax.swing.UnsupportedLookAndFeelException;



import ui.swing.utils.LookAndFeelSetter;

public class Main {

	public static void main(final String[] args)
			throws UnsupportedLookAndFeelException, IOException {

		keepWorkingOnMinimize();
		setLookAndFeel();

		DurableSWINGContainerBuilder.startDurableSWINGContainer();
	}

	private static void setLookAndFeel() {
		new LookAndFeelSetter().start();
	}

	private static void keepWorkingOnMinimize() {
		System.setProperty("sun.awt.keepWorkingSetOnMinimize", "true");
	}

}
