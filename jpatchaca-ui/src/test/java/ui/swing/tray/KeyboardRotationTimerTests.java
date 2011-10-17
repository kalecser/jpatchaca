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
				"showNotification(\"Keyboard rotation, turn 0!\")\n" +
				"showShakingMessageWithTitle(\"Keyboard rotation, turn 0!\")", getOperations());
	}

	@Test
	public void configureToSupressDialogNotification_WillOnlyShowTrayNotification(){
		configureToSupressDialogs();
		showNotificationMessage();
		showNotificationMessage();
		Assert.assertEquals(
				"showNotification(\"Keyboard rotation, turn 0!\")\n" +
				"showNotification(\"Keyboard rotation, turn 1!\")", getOperations());
	}

	PresenterMock presenter = new PresenterMock();
	KeyboardRotationOptions preferences = new KeyboardRotationOptions();
	int minutesToWaitDoesNotMatter = 1;
	KeyboardRotationTimer subject = new KeyboardRotationTimer(minutesToWaitDoesNotMatter, presenter, preferences);

	private String getOperations() {
		return presenter.getOperations();
	}

	private void showNotificationMessage() {
		subject.showTurnMessage();
	}
	
	private void configureToSupressDialogs() {
		try {
			preferences.setSupressShakingDialog(true);
		} catch (MustBeCalledInsideATransaction e) {
			//supress
		}
	}
	
}
