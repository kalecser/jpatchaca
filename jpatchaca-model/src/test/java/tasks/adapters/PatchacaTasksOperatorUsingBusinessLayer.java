/**
 * 
 */
package tasks.adapters;

import java.util.List;

import junit.framework.Assert;
import labels.LabelsSystem;

import org.apache.commons.lang.time.DateUtils;

import tasks.PatchacaTasksOperator;
import tasks.TasksSystem;
import tasks.delegates.StartTaskDelegate;
import tasks.tasks.TaskData;
import tasks.tasks.TaskView;
import tasks.tasks.TasksView;
import basic.NonEmptyString;
import basic.mock.MockHardwareClock;

public final class PatchacaTasksOperatorUsingBusinessLayer implements
		PatchacaTasksOperator {
	private final MockHardwareClock mockHardwareClock;
	private final TasksSystem tasksSystem;
	private final LabelsSystem labelsSystem;
	private final StartTaskDelegate startTaskDelegate;
	private final TasksView tasks;

	public PatchacaTasksOperatorUsingBusinessLayer(
			LabelsSystem labelsSystem,
			MockHardwareClock mockHardwareClock, TasksSystem tasksSystem, StartTaskDelegate startTaskDelegate, TasksView tasks) {
		super();
		this.labelsSystem = labelsSystem;
		this.mockHardwareClock = mockHardwareClock;
		this.tasksSystem = tasksSystem;
		this.startTaskDelegate = startTaskDelegate;
		this.tasks = tasks;
	}

	@Override
	public boolean isTaskInLabel(String taskName, String labelname) {
		for (final TaskView task : labelsSystem.tasksInlabel(labelname)){
			if (task.name().equals(taskName))
				return true;
		}
		
		return false;
	}

	@Override
	public void createTaskAndAssignToLabel(String taskName, String labelName) {
		tasksSystem.createTask(new TaskData(taskName,0.0,labelName));

	}

	@Override
	public void createTask(String taskName) {
		tasksSystem.createTask(new TaskData(new NonEmptyString(taskName),0.0));		
	}

	@Override
	public String allLabelName() {
		return labelsSystem.allLabelName();
	}

	@Override
	public void ediTask(String taskName, String taskNewName) {
		tasksSystem.editTask(taskByName(taskName), new TaskData(new NonEmptyString(taskNewName), 0.0));
	}

	public long getTimeSpent(String taskName, int periodIndex) {
		return taskByName(taskName).periodAt(periodIndex).totalTime();
	}

	@Override
	public void setTime(int time) {
		mockHardwareClock.setTime(time);				
	}

	@Override
	public void startTask(String taskName) {
		startTaskDelegate.startTask(taskByName(taskName));
	}

	@Override
	public void stopTask() {
		tasksSystem.stopTask(tasksSystem.activeTask());
	}

	public TaskView taskByName(String taskName) {
		for (final TaskView task : tasks.tasks()){
			if (task.name().equals(taskName)){
				return task;
			}
		}
		throw new RuntimeException("Could not find task named " + taskName);
	}

	@Override
	public void startNewTaskNow(String taskName) {
		tasksSystem.createTask(new TaskData(new NonEmptyString(taskName), 0.0));
		startTask(taskName);
		
	}

	@Override
	public void startTaskHalfAnHourAgo(String taskName) {
		tasksSystem.taskStarted(taskByName(taskName), DateUtils.MILLIS_PER_HOUR/2);
	}

	@Override
	public void assertTimeSpent(String taskName, int periodIndex, long expectedTimeSpent) {
		long timeSpent = getTimeSpent(taskName, periodIndex) / DateUtils.MILLIS_PER_MINUTE;
		if (expectedTimeSpent != timeSpent)
			throw new RuntimeException("Time spent should be: " + expectedTimeSpent + " but was " + timeSpent);
	}

	@Override
	public void assertLastActiveTasks(List<String> expectedLastActiveTasksNames) {
		int index = 0;
		for (TaskView lastActiveTask : tasksSystem.lastActiveTasks()){
			Assert.assertEquals(expectedLastActiveTasksNames.get(index++), lastActiveTask.name());
		}
	}

	@Override
	public void assertActiveTask(String taskName) {
		Assert.assertEquals(taskName, tasksSystem.activeTaskName());		
	}


}