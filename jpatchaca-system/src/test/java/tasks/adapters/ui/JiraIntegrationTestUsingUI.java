package tasks.adapters.ui;

import org.junit.After;
import org.picocontainer.MutablePicoContainer;

import basic.mock.MockHardwareClock;
import tasks.JiraIntegrationTest;
import tasks.PatchacaTasksOperator;
import tasks.adapters.ui.operators.PatchacaTasksOperatorUsingUI;

public class JiraIntegrationTestUsingUI extends JiraIntegrationTest {

	private MutablePicoContainer containerForTests;

	@Override
	public PatchacaTasksOperator operator() {
		containerForTests = ContainerForUiTestsFactory.createUIContainerForTests();
		MockHardwareClock mockHardwareClock = containerForTests.getComponent(MockHardwareClock.class);
		return new PatchacaTasksOperatorUsingUI(mockHardwareClock);
	}
	
	@After
	public void tesarDown(){
		containerForTests.stop();
	}

}
