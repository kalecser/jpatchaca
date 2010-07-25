package tasks.adapters.ui;

import org.picocontainer.MutablePicoContainer;

import basic.mock.MockHardwareClock;
import tasks.JiraIntegrationTest;
import tasks.PatchacaTasksOperator;
import tasks.adapters.ui.operators.ContainerForUiTestsFactory;
import tasks.adapters.ui.operators.PatchacaTasksOperatorUsingUI;

public class JiraIntegrationTestUsingUI extends JiraIntegrationTest {

	@Override
	public PatchacaTasksOperator operator() {
		MutablePicoContainer containerForTests = ContainerForUiTestsFactory.createUIContainerForTests();
		MockHardwareClock mockHardwareClock = containerForTests.getComponent(MockHardwareClock.class);
		return new PatchacaTasksOperatorUsingUI(mockHardwareClock);
	}

}
