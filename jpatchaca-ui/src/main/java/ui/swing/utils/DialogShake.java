package ui.swing.utils;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class DialogShake {

	private static DialogShake instance;
	
	public static void showDialogShaking(final String message, final String title){
		
		if(instance == null)
			createShakeDialog();
		
		instance.mostraNovaMensagem(message, title);
		instance.startShake();
	}

	
	private static void createShakeDialog() {
		pane = new JOptionPane("",
				JOptionPane.ERROR_MESSAGE);
		final JDialog d = pane.createDialog(null, "");
		instance = new DialogShake(d);
		d.pack();
		d.setModal(false);
	}

	static JOptionPane pane;
	JDialog dialog;
	Point naturalLocation;
	Timer shakeTimer;

	
	
	private DialogShake(final JDialog d) {
		dialog = d;
	}
	
	private void mostraNovaMensagem(final String message,
			final String title) {
		pane.setMessage(message);
		dialog.setVisible(true);
		dialog.setTitle(title);
	}

	public void startShake() {
		final long startTime;

		naturalLocation = dialog.getLocation();
		startTime = System.currentTimeMillis();
		shakeTimer = new Timer(5, new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				final double TWO_PI = Math.PI * 2.0;
				final double SHAKE_CYCLE = 50;

				final long elapsed = System.currentTimeMillis() - startTime;
				final double waveOffset = (elapsed % SHAKE_CYCLE) / SHAKE_CYCLE;
				final double angle = waveOffset * TWO_PI;

				final int SHAKE_DISTANCE = 10;

				final int shakenX = (int) ((Math.sin(angle) * SHAKE_DISTANCE) + naturalLocation.x);
				dialog.setLocation(shakenX, naturalLocation.y);
				dialog.repaint();

				final int SHAKE_DURATION = 1000;
				if (elapsed >= SHAKE_DURATION) {
					stopShake();
				}
			}
		});
		shakeTimer.start();
	}

	public void stopShake() {
		shakeTimer.stop();
		dialog.setLocation(naturalLocation);
		dialog.repaint();
	}

}
