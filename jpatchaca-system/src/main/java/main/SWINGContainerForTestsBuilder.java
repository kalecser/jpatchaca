package main;

import jira.service.JiraImpl;
import jira.service.JiraMock;
import main.singleInstance.AssureSingleInstance;
import model.PatchacaModelContainerFactory;

import org.picocontainer.MutablePicoContainer;

import ui.swing.singleInstance.ShowMainScreenOnSecondRun;
import ui.swing.tray.PatchacaTray;
import wheel.io.files.impl.tranzient.TransientDirectory;
import basic.DeferredExecutor;
import basic.HardwareClock;
import basic.PatchacaDirectory;

public final class SWINGContainerForTestsBuilder {

public static MutablePicoContainer createSWINGContainerForTests(
		final HardwareClock hardwareClock) {

	DeferredExecutor.makeSynchronous();
	final MutablePicoContainer container = PatchacaModelContainerFactory.createNonUIContainer(hardwareClock);
	UIStuffBuilder.registerUIStuff(container);
	
	container.removeComponent(PatchacaDirectory.class);
	container.addComponent(new TransientDirectory());
	
	container.removeComponent(JiraImpl.class);
	container.addComponent(JiraMock.class);

	container.removeComponent(AssureSingleInstance.class);		
	
	container.removeComponent(ShowMainScreenOnSecondRun.class);

	makePatchacaTrayStopShowingStatusMessages(container);

	return container;

}

private static void makePatchacaTrayStopShowingStatusMessages(
		final MutablePicoContainer container) {
	final PatchacaTray tray = container.getComponent(PatchacaTray.class);
	tray.test_mode = true;
}

}