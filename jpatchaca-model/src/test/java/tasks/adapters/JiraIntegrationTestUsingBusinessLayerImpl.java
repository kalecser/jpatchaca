package tasks.adapters;

import main.TransientNonUIContainer;
import main.TransientNonUiContainerWithTestOperators;

import org.junit.After;

import tasks.JiraIntegrationTest;
import tasks.PatchacaTasksOperator;

public class JiraIntegrationTestUsingBusinessLayerImpl extends
		JiraIntegrationTest {

	private TransientNonUIContainer container;

	@Override
	public PatchacaTasksOperator operator() {
		container = new TransientNonUiContainerWithTestOperators();
		container.start();
		return container.getComponent(PatchacaTasksOperator.class);
	}

	@After
	public void tearDown() throws Exception {
		container.stop();
	}

}
