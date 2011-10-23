package tasks.adapters.ui;

import main.SWINGContainerForTestsBuilder;

import org.picocontainer.MutablePicoContainer;

import tasks.adapters.ui.operators.PatchacaTasksOperatorUsingUI;

import basic.mock.MockHardwareClock;

class ContainerForUiTestsFactory {

	static MutablePicoContainer createUIContainerForTests() {
		final MockHardwareClock mockHardwareClock = new MockHardwareClock();
		MutablePicoContainer container = SWINGContainerForTestsBuilder.createSWINGContainerForTests(mockHardwareClock);	
		
		container.addComponent(PatchacaTasksOperatorUsingUI.class);
		
		container.start();
		return container;
	}

}
