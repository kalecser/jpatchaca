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
import tasks.delegates.CreateTaskDelegate;
import tasks.delegates.StartTaskData;
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
	private final CreateTaskDelegate createTaskDelegate;

	public PatchacaTasksOperatorUsingBusinessLayer(
			final LabelsSystem labelsSystem,
			final MockHardwareClock mockHardwareClock,
			final TasksSystem tasksSystem,
			final StartTaskDelegate startTaskDelegate, final TasksView tasks,
			final CreateTaskDelegate createTaskDelegate) {
		super();
		this.labelsSystem = labelsSystem;
		this.mockHardwareClock = mockHardwareClock;
		this.tasksSystem = tasksSystem;

		this.startTaskDelegate = startTaskDelegate;
		this.tasks = tasks;
		this.createTaskDelegate = createTaskDelegate;
	}

	@Override
	public boolean isTaskInLabel(final String taskName, final String labelname) {
		for (final TaskView task : labelsSystem.tasksInlabel(labelname)) {
			if (task.name().equals(taskName)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void createTaskAndAssignToLabel(final String taskName,
			final String labelName) {
		createTaskDelegate.createTask(new TaskData(taskName, 0.0, labelName));

	}

	@Override
	public void createTask(final String taskName) {
		createTaskDelegate.createTask(new TaskData(
				new NonEmptyString(taskName), 0.0));
	}

	@Override
	public String allLabelName() {
		return labelsSystem.allLabelName();
	}

	@Override
	public void ediTask(final String taskName, final String taskNewName) {
		tasksSystem.editTask(taskByName(taskName), new TaskData(
				new NonEmptyString(taskNewName), 0.0));
	}

	public long getTimeSpent(final String taskName, final int periodIndex) {
		return taskByName(taskName).periodAt(periodIndex).totalTime();
	}

	@Override
	public void setTime(final int time) {
		mockHardwareClock.setTime(time);
	}

	@Override
	public void startTask(final String taskName) {

		if (taskName.isEmpty()) {
			return;
		}

		startTaskDelegate.starTask(new StartTaskData(new NonEmptyString(
				taskName), 0));
	}

	@Override
	public void stopTask() {
		tasksSystem.stopTask(tasksSystem.activeTask());
	}

	public TaskView taskByName(final String taskName) {
		for (final TaskView task : tasks.tasks()) {
			if (task.name().equals(taskName)) {
				return task;
			}
		}
		throw new RuntimeException("Could not find task named " + taskName);
	}

	@Override
	public void startNewTaskNow(final String taskName) {
		createTaskDelegate.createTask(new TaskData(
				new NonEmptyString(taskName), 0.0));
		startTask(taskName);

	}

	@Override
	public void startTaskHalfAnHourAgo(final String taskName) {
		tasksSystem.taskStarted(taskByName(taskName),
				DateUtils.MILLIS_PER_HOUR / 2);
	}

	@Override
	public void assertTimeSpent(final String taskName, final int periodIndex,
			final long expectedTimeSpent) {
		final long timeSpent = getTimeSpent(taskName, periodIndex)
				/ DateUtils.MILLIS_PER_MINUTE;
		if (expectedTimeSpent != timeSpent) {
			throw new RuntimeException("Time spent should be: "
					+ expectedTimeSpent + " but was " + timeSpent);
		}
	}

	@Override
	public void assertLastActiveTasks(
			final List<String> expectedLastActiveTasksNames) {
		int index = 0;
		for (final TaskView lastActiveTask : tasksSystem.lastActiveTasks()) {
			Assert.assertEquals(expectedLastActiveTasksNames.get(index++),
					lastActiveTask.name());
		}
	}

	@Override
	public void assertActiveTask(final String taskName) {
		Assert.assertEquals(taskName, tasksSystem.activeTaskName());
	}

}