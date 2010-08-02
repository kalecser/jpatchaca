package tasks.adapters.ui.operators;

import main.Main;

import org.picocontainer.MutablePicoContainer;

import basic.mock.MockHardwareClock;

public class ContainerForUiTestsFactory {

	public static MutablePicoContainer createUIContainerForTests() {
		final MockHardwareClock mockHardwareClock = new MockHardwareClock();
		MutablePicoContainer container = Main.createSWINGContainerForTests(mockHardwareClock);	
		
		container.addComponent(PatchacaTasksOperatorUsingUI.class);
		
		container.start();
		return container;
	}

}
