package main;

import javax.swing.JFrame;

import commons.swing.FloatingArea;

public class Spikes {

	public static void main(final String[] args) {
		FloatingArea area1 = new FloatingArea(new JFrame());
		@SuppressWarnings("unused")
		final FloatingArea area2 = new FloatingArea(new JFrame());

		area1.setVisible(true);
		area1.dispose();
		area1 = null;
		System.gc();

		System.out.println("foo");
	}
}
