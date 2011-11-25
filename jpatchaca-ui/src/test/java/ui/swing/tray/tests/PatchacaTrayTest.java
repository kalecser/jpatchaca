package ui.swing.tray.tests;

import java.awt.SystemTray;

import keyboardRotation.KeyboardRotationOptions;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tasks.tasks.tests.MockTaskName;
import ui.swing.presenter.Presenter;
import ui.swing.tray.PatchacaTray;
import ui.swing.tray.mock.PresenterMock;
import ui.swing.tray.tests.environment.PatchacaTrayModelMock;
import ui.swing.tray.tests.environment.PathcacaTrayOperator;

public class PatchacaTrayTest {

	private PatchacaTrayModelMock modelMock;
	private PatchacaTray tray;

	@Before
	public void setup() {

		if (!SystemTray.isSupported()) {
			return;
		}

		modelMock = new PatchacaTrayModelMock();
		Presenter presenter = new PresenterMock();
		KeyboardRotationOptions keyboardRotationPreferences = null;
		tray = new PatchacaTray(modelMock, presenter, keyboardRotationPreferences);

		tray.start();
	}

	@After
	public void tearDown() {
		if (tray != null) {
			tray.stop();
		}
	}

	@Test
	public void testPatchacaTray() {

		if (!SystemTray.isSupported()) {
			return;
		}

		final PathcacaTrayOperator operator = new PathcacaTrayOperator();
		modelMock.setActiveTask(new MockTaskName("test task"));
		operator.assertActiveTask("test task");

	}

}
