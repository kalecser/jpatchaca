package tasks.adapters;

import main.TransientNonUIContainer;
import tasks.PatchacaTasksOperator;
import tasks.TasksTest;

public final class TasksTestUsingBusinessLayerImpl extends TasksTest {

	private TransientNonUIContainer container;

	@Override
	public PatchacaTasksOperator createOperator() {

		container = new TransientNonUIContainer();
		return container.getComponent(PatchacaTasksOperator.class);
	}

	@Override
	protected void tearDown() throws Exception {
		container.stop();
	}

}
