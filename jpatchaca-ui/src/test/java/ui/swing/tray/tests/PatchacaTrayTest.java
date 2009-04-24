package ui.swing.tray.tests;

import java.awt.SystemTray;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ui.swing.tray.PatchacaTray;
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
		tray = new PatchacaTray(modelMock);

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
		modelMock.setActiveTask("test task");
		operator.assertActiveTask("test task");

	}

}
