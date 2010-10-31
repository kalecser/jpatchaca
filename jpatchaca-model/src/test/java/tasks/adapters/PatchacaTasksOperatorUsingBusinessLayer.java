/**
 * 
 */
package tasks.adapters;

import junit.framework.Assert;
import labels.LabelsSystem;
import labels.labels.LabelsHome;
import labels.labels.SelectedLabel;
import lang.Maybe;

import org.apache.commons.lang.NotImplementedException;
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
	private final SelectedLabel selectedLabel;

	public PatchacaTasksOperatorUsingBusinessLayer(
			final LabelsSystem labelsSystem,
			final MockHardwareClock mockHardwareClock,
			final TasksSystem tasksSystem,
			final StartTaskDelegate startTaskDelegate, final TasksView tasks,
			final CreateTaskDelegate createTaskDelegate,
			final ActiveTask activeTask, SelectedLabel selectedLabel) {
		super();
		this.labelsSystem = labelsSystem;
		this.mockHardwareClock = mockHardwareClock;
		this.tasksSystem = tasksSystem;

		this.startTaskDelegate = startTaskDelegate;
		this.tasks = tasks;
		this.createTaskDelegate = createTaskDelegate;
		this.activeTask = activeTask;
		this.selectedLabel = selectedLabel;
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
		NonEmptyString nonEmptyTaskName = new NonEmptyString(taskName);
		TaskData taskData = new TaskData(nonEmptyTaskName);
		taskData.setBudget(0.0);
		createTaskDelegate.createTask(taskData);
		
		
		Maybe<? extends TaskView> byName = tasks.byName(nonEmptyTaskName);
		labelsSystem.setLabelToTask(byName.unbox(), labelName);
	}

	@Override
	public void createTask(final String taskName) {
		TaskData taskData = new TaskData(new NonEmptyString(taskName));
		
		createTaskDelegate.createTask(taskData);
	}

	@Override
	public String allLabelName() {
		return labelsSystem.allLabelName();
	}

	@Override
	public void editTask(final String taskName, final String taskNewName) {
		TaskData taskData = new TaskData(new NonEmptyString(taskNewName));
		taskData.setBudget(0.0);
		taskData.setLabel(LabelsHome.ALL_LABEL_NAME);

		tasksSystem.editTask(taskByName(taskName), taskData);
	}

	public long getTimeSpentInMinutes(final String taskName,
			final int periodIndex) {
		return taskByName(taskName).periodAt(periodIndex).totalTime();
	}

	private long getTimeSpentInMinutes(final String taskName) {
		final int periodsCount = taskByName(taskName).periodsCount();
		long totalTime = 0l;

		for (int period = 0; period < periodsCount; period++) {
			totalTime += getTimeSpentInMinutes(taskName, period);
		}

		return totalTime;
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

		startTaskDelegate.starTask(new StartTaskData(new TaskData(new NonEmptyString(taskName)), 0));
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
		TaskData taskData = new TaskData(new NonEmptyString(taskName));
		taskData.setBudget(0.0);
		taskData.setLabel(LabelsHome.ALL_LABEL_NAME);
		createTaskDelegate.createTask(taskData);
		startTask(taskName);

	}

	@Override
	public void startTaskHalfAnHourAgo(final String taskName) {
		startTaskDelegate.starTask(new StartTaskData(new TaskData(new NonEmptyString(taskName)), (int) (DateUtils.MILLIS_PER_MINUTE * 30)));
	}

	@Override
	public void assertTimeSpent(final String taskName, final int periodIndex,
			final long expectedTimeSpent) {
		final long timeSpent = getTimeSpentInMinutes(taskName, periodIndex)
				/ DateUtils.MILLIS_PER_MINUTE;
		assertTimeSpentEquals(expectedTimeSpent, timeSpent);
	}

	@Override
	public void assertTimeSpentInMinutes(final String taskName,
			final long expectedTimeSpent) {
		final long timeSpent = getTimeSpentInMinutes(taskName)
				/ DateUtils.MILLIS_PER_MINUTE;
		assertTimeSpentEquals(expectedTimeSpent, timeSpent);
	}

	private void assertTimeSpentEquals(final long expectedTimeSpent,
			final long timeSpent) {
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

	@Override
	public void advanceTimeBy(final long millis) {
		mockHardwareClock.advanceTimeBy(millis);
	}

	@Override
	public void editTaskJiraKey(String string, String string2) {
		throw new NotImplementedException();
		
	}

	@Override
	public void assertJiraKeyForTask(String string, String string2) {
		throw new NotImplementedException();
		
	}
	
	@Override
	public void createTaskWithJiraIntegration(String taskName, String jiraKey) {
		throw new NotImplementedException();		
	}

	@Override
	public void selectLabel(String label) {
		selectedLabel.update(label);
	}

}