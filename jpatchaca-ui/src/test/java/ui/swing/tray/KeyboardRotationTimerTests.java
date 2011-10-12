package ui.swing.tray;

import junit.framework.Assert;
import keyboardRotation.KeyboardRotationOptions;

import org.junit.Test;

import events.persistence.MustBeCalledInsideATransaction;

import ui.swing.tray.mock.PresenterMock;

public class KeyboardRotationTimerTests {

	@Test
	public void showNotification_WillShowTrayAndDialogNotifications(){
		showNotificationMessage();
		Assert.assertEquals(
				"showNotification()\n" +
				"showShakingMessageWithTitle()", getOperations());
	}

	@Test
	public void configureToSupressDialogNotification_WillOnlyShowTrayNotification(){
		configureToSupressDialogs();
		showNotificationMessage();
		Assert.assertEquals("showNotification()", getOperations());
	}

	PresenterMock presenter = new PresenterMock();
	KeyboardRotationOptions preferences = new KeyboardRotationOptions();

	private String getOperations() {
		return presenter.getOperations();
	}

	private void showNotificationMessage() {
		int minutesToWaitDoesNotMatter = 1;
		KeyboardRotationTimer timer = new KeyboardRotationTimer(minutesToWaitDoesNotMatter, presenter, preferences);
		timer.showMessage();
	}
	
	private void configureToSupressDialogs() {
		try {
			preferences.setSupressShakingDialog(true);
		} catch (MustBeCalledInsideATransaction e) {
			//supress
		}
	}
	
}
