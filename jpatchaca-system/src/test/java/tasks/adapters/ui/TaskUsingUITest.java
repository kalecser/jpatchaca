package tasks.adapters.ui;


import org.picocontainer.MutablePicoContainer;

import tasks.PatchacaTasksOperator;
import tasks.adapters.ui.operators.ContainerForUiTestsFactory;
import tasks.adapters.ui.operators.PatchacaTasksOperatorUsingUI;
import basic.mock.MockHardwareClock;



public final class TaskUsingUITest extends tasks.TasksTest {
	
	private MutablePicoContainer container;

	@Override
	public PatchacaTasksOperator createOperator() {
		container = ContainerForUiTestsFactory.createUIContainerForTests();
		MockHardwareClock mockHardwareClock = container.getComponent(MockHardwareClock.class);
		return new PatchacaTasksOperatorUsingUI(mockHardwareClock);
	}

	@Override
	protected void tearDown() throws Exception {
		container.stop();
	}
	
	


}
