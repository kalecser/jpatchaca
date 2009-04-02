package tasks.adapters.ui;

import main.Main;

import org.picocontainer.MutablePicoContainer;

import tasks.PatchacaTasksOperator;
import basic.mock.MockHardwareClock;



public final class TaskUsingUITest extends tasks.TasksTest {
	

	
	private MutablePicoContainer container;

	
	
	@Override
	public PatchacaTasksOperator createOperator() {
		final MockHardwareClock mockHardwareClock = new MockHardwareClock();
		container = Main.createSWINGContainerForTests(mockHardwareClock);		
		container.start();
		return new PatchacaTasksOperatorUsingUI(mockHardwareClock);
	}
	
	@Override
	protected void tearDown() throws Exception {
		container.stop();
	}
	
	


}
