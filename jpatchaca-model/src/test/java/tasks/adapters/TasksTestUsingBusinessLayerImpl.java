package tasks.adapters;

import labels.LabelsSystem;
import main.TransientNonUIContainer;
import tasks.PatchacaTasksOperator;
import tasks.TasksSystem;
import tasks.TasksTest;
import tasks.delegates.StartTaskDelegate;
import tasks.tasks.TasksView;
import basic.mock.MockHardwareClock;



public final class TasksTestUsingBusinessLayerImpl extends TasksTest {

	private TransientNonUIContainer container;

	@Override
	public PatchacaTasksOperator createOperator() {

		container = new TransientNonUIContainer();
		MockHardwareClock mockHardwareClock = container.getMockHardwareClock();
		
		LabelsSystem labelsSystem = container.getComponent(LabelsSystem.class);
		TasksSystem tasksSystem = container.getComponent(TasksSystem.class);
		TasksView tasks = container.getComponent(TasksView.class);
		
		StartTaskDelegate startTaskDelegate = container.getComponent(StartTaskDelegate.class);
		
		return new PatchacaTasksOperatorUsingBusinessLayer(labelsSystem, mockHardwareClock, tasksSystem, startTaskDelegate, tasks);
	}

	@Override
	protected void tearDown() throws Exception {
		container.stop();
	}

}
