package ui.swing.tray;

import keyboardRotation.KeyboardRotationOptions;
import ui.swing.presenter.Presenter;

public class NotificationTimer extends Thread {

	private final String title = "Timer Alert";
	private final String message = "Keyboard Rotation!";
	private final int minutesToWait;
	private static TimerStatus status = TimerStatus.OFF;
	private final Presenter presenter;
	private final KeyboardRotationOptions preferences;

	public NotificationTimer(final int minutesToWait, final Presenter presenter, KeyboardRotationOptions preferences) {
		this.minutesToWait = minutesToWait;
		this.presenter = presenter;
		this.preferences = preferences;
	}

	@Override
	public void run() {
		try {
			synchronized (this) {
				while (status == TimerStatus.ON) {
					wait(minutesToWait * 60000);
					showMessage();
				}
			}
		} catch (final InterruptedException e) {
			System.out.println("Thread has been finalized");
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public void showMessage() {
		presenter.showMessageBalloon( message);
		showDialogIfNeeded();
	}

	private void showDialogIfNeeded() {
		if (preferences.supressDialogs())
			return;
		
		presenter.showShakingMessageWithTitle(message, title);
	}

	public static TimerStatus getStatus() {
		return status;
	}

	public static void setStatus(final TimerStatus status) {
		NotificationTimer.status = status;
	}
}