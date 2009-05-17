/**
 * 
 */
package tasks.adapters;

import junit.framework.Assert;
import labels.LabelsSystem;
import lang.Maybe;

import org.apache.commons.lang.time.DateUtils;

import tasks.ActiveTask;
import tasks.PatchacaTasksOperator;
import tasks.Task;
import tasks.TaskView;
import tasks.TasksSystem;
import tasks.delegates.CreateTaskDelegate;
import tasks.delegates.StartTaskData;
import tasks.delegates.StartTaskDelegate;
import tasks.home.TaskData;
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
	private final ActiveTask activeTask;

	public PatchacaTasksOperatorUsingBusinessLayer(
			final LabelsSystem labelsSystem,
			final MockHardwareClock mockHardwareClock,
			final TasksSystem tasksSystem,
			final StartTaskDelegate startTaskDelegate, final TasksView tasks,
			final CreateTaskDelegate createTaskDelegate,
			final ActiveTask activeTask) {
		super();
		this.labelsSystem = labelsSystem;
		this.mockHardwareClock = mockHardwareClock;
		this.tasksSystem = tasksSystem;

		this.startTaskDelegate = startTaskDelegate;
		this.tasks = tasks;
		this.createTaskDelegate = createTaskDelegate;
		this.activeTask = activeTask;
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
		tasksSystem.stopTask();
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
		startTaskDelegate.starTask(new StartTaskData(new NonEmptyString(
				taskName), (int) (DateUtils.MILLIS_PER_MINUTE * 30)));
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
	public void assertActiveTask(final String taskName) {

		final Maybe<Task> currentValue = activeTask.currentValue();
		if (currentValue == null) {
			Assert.assertTrue(taskName == null);
			return;
		}

		Assert.assertEquals(taskName, currentValue.unbox().name());
	}

}