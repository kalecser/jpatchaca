package ui.swing.tray;

import junit.framework.Assert;
import keyboardRotation.KeyboardRotationOptions;

import org.junit.Test;

import events.persistence.MustBeCalledInsideATransaction;

import ui.swing.tray.mock.PresenterMock;

public class NotificationTimerTests {

	@Test
	public void showNotification_WillShowTrayAndDialogNotifications(){
		showNotificationMessage();
		Assert.assertEquals(
				"showMessageBalloon()\n" +
				"showShakingMessageWithTitle()", getOperations());
	}

	@Test
	public void configureToSupressDialogNotification_WillOnlyShowTrayNotification(){
		configureToSupressDialogs();
		showNotificationMessage();
		Assert.assertEquals("showMessageBalloon()", getOperations());
	}

	PresenterMock presenter = new PresenterMock();
	KeyboardRotationOptions preferences = new KeyboardRotationOptions();

	private String getOperations() {
		return presenter.getOperations();
	}

	private void showNotificationMessage() {
		int minutesToWaitDoesNotMatter = 1;
		NotificationTimer timer = new NotificationTimer(minutesToWaitDoesNotMatter, presenter, preferences);
		timer.showMessage();
	}
	
	private void configureToSupressDialogs() {
		try {
			preferences.setSupressDialogs(true);
		} catch (MustBeCalledInsideATransaction e) {
			//supress
		}
	}
	
}
