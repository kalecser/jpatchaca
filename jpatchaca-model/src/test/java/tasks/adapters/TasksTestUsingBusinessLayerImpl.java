package tasks.adapters;

import main.TransientNonUIContainer;
import main.TransientNonUiContainerWithTestOperators;
import tasks.PatchacaTasksOperator;
import tasks.TasksTest;

public final class TasksTestUsingBusinessLayerImpl extends TasksTest {

	private TransientNonUIContainer container;

	@Override
	public PatchacaTasksOperator createOperator() {

		container = new TransientNonUiContainerWithTestOperators();
		container.start();
		return container.getComponent(PatchacaTasksOperator.class);
	}

	@Override
	protected void tearDown() throws Exception {
		container.stop();
	}

}
