package periods.adapters;

import main.TransientNonUIContainer;
import main.TransientNonUiContainerWithTestOperators;
import periods.PatchacaPeriodsOperator;
import periods.PeriodsTest;
import tasks.PatchacaTasksOperator;

public class PeriodsTestUsingBusinessLayer extends PeriodsTest {

	private TransientNonUIContainer container;

	@Override
	protected void setUp() throws Exception {
		TransientNonUIContainer transientNonUIContainer = new TransientNonUiContainerWithTestOperators();
		container = transientNonUIContainer;
		container.start();
		super.setUp();
	}

	@Override
	protected PatchacaPeriodsOperator createPeriodsOperator() {
		return container.getComponent(PatchacaPeriodsOperator.class);
	}

	@Override
	protected PatchacaTasksOperator createTasksOperator() {
		return container.getComponent(PatchacaTasksOperator.class);
	}

	@Override
	protected void tearDown() throws Exception {
		container.stop();
	}

}
