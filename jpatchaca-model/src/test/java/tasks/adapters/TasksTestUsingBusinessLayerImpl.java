package tasks.adapters;

import labels.LabelsSystem;
import main.TransientNonUIContainer;
import tasks.ActiveTask;
import tasks.PatchacaTasksOperator;
import tasks.TasksSystem;
import tasks.TasksTest;
import tasks.delegates.CreateTaskDelegate;
import tasks.delegates.StartTaskDelegate;
import tasks.tasks.TasksView;
import basic.mock.MockHardwareClock;

public final class TasksTestUsingBusinessLayerImpl extends TasksTest {

	private TransientNonUIContainer container;

	@Override
	public PatchacaTasksOperator createOperator() {

		container = new TransientNonUIContainer();
		final MockHardwareClock mockHardwareClock = container
				.getMockHardwareClock();

		final LabelsSystem labelsSystem = container
				.getComponent(LabelsSystem.class);
		final TasksSystem tasksSystem = container
				.getComponent(TasksSystem.class);
		final TasksView tasks = container.getComponent(TasksView.class);
		final ActiveTask activeTask = container.getComponent(ActiveTask.class);

		final StartTaskDelegate startTaskDelegate = container
				.getComponent(StartTaskDelegate.class);
		final CreateTaskDelegate createTaskDelegate = container
				.getComponent(CreateTaskDelegate.class);

		return new PatchacaTasksOperatorUsingBusinessLayer(labelsSystem,
				mockHardwareClock, tasksSystem, startTaskDelegate, tasks,
				createTaskDelegate, activeTask);
	}

	@Override
	protected void tearDown() throws Exception {
		container.stop();
	}

}
