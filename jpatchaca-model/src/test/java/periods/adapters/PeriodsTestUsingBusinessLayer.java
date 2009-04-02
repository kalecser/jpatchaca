package periods.adapters;

import labels.LabelsSystem;
import main.TransientNonUIContainer;
import periods.PatchacaPeriodsOperator;
import periods.PeriodsTest;
import periodsInTasks.PeriodsInTasksSystem;
import tasks.PatchacaTasksOperator;
import tasks.TasksSystem;
import tasks.adapters.PatchacaTasksOperatorUsingBusinessLayer;
import tasks.delegates.StartTaskDelegate;
import basic.mock.MockHardwareClock;

public class PeriodsTestUsingBusinessLayer extends PeriodsTest {

	private MockHardwareClock mockHardwareClock;
	private TransientNonUIContainer container;
	private LabelsSystem labelsSystem;
	private TasksSystem tasksSystem;
	private PatchacaTasksOperatorUsingBusinessLayer tasksOperator;
	private PeriodsInTasksSystem periodsSystem;

	@Override
	protected void setUp() throws Exception {
		mockHardwareClock = new MockHardwareClock();
		container = new TransientNonUIContainer();

		labelsSystem = container.getComponent(LabelsSystem.class);
		tasksSystem = container.getComponent(TasksSystem.class);
		periodsSystem = container.getComponent(PeriodsInTasksSystem.class);
		
		StartTaskDelegate startTaskDelegate = container.getComponent(StartTaskDelegate.class);
		
		tasksOperator = new PatchacaTasksOperatorUsingBusinessLayer(
				labelsSystem, mockHardwareClock, tasksSystem, startTaskDelegate);
		
		super.setUp();
	}

	@Override
	protected PatchacaPeriodsOperator createPeriodsOperator() {
		return new PatchacaPeriodsOperatorUsingBusinessLayer(tasksOperator,
				tasksSystem, periodsSystem);
	}

	@Override
	protected PatchacaTasksOperator createTasksOperator() {
		return tasksOperator;
	}

	@Override
	protected void tearDown() throws Exception {
		container.stop();
	}

}
