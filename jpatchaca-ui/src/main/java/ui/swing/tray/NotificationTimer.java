package ui.swing.tray;

import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;

import ui.swing.utils.DialogShake;

public class NotificationTimer extends Thread {

	private final TrayIcon trayIcon;
	private final String title = "Timer Alert";
	private final String message = "Keyboard Rotation!";
	private final int minutesToWait;
	private static TimerStatus status = TimerStatus.OFF;

	public NotificationTimer(final int minutesToWait, final TrayIcon trayIcon) {
		this.minutesToWait = minutesToWait;
		this.trayIcon = trayIcon;
	}

	@Override
	public void run() {
		try {
			synchronized (this) {
				while (status == TimerStatus.ON) {
					wait(minutesToWait * 60000);
					trayIcon.displayMessage(title, message, MessageType.ERROR);
					DialogShake.showDialogShaking(message, title);
				}
			}
		} catch (final InterruptedException e) {
			System.out.println("Thread has been finalized");
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static TimerStatus getStatus() {
		return status;
	}

	public static void setStatus(final TimerStatus status) {
		NotificationTimer.status = status;
	}
}