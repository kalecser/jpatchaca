package ui.swing.mainScreen.tasks.mock;

import java.util.Date;

import periods.Period;
import tasks.TaskView;
import tasks.TasksListener;
import tasks.TasksSystem;
import tasks.home.TaskData;
import basic.Alert;
import core.ObjectIdentity;
import events.persistence.MustBeCalledInsideATransaction;

public class MockTasksSystem implements TasksSystem {

	@Override
	public void editTask(TaskView ignored, TaskData doNothing) {}

	@Override
	public void removeTask(TaskView task) {
		throw new java.lang.RuntimeException("Not implemented");
	}

	@Override
	public void editPeriod(TaskView task, int periodIndex, Period newPeriod) {
		throw new java.lang.RuntimeException("Not implemented");
	}

	@Override
	public void setPeriodStarting(TaskView task, int periodIndex, Date startDate) {
		throw new java.lang.RuntimeException("Not implemented");
	}

	@Override
	public void setPeriod(TaskView task, int i, Date startTime, Date endTime) {
		throw new java.lang.RuntimeException("Not implemented");
	}

	@Override
	public void setPeriodEnding(TaskView task, int periodIndex, Date endDate) {
		throw new java.lang.RuntimeException("Not implemented");
	}

	@Override
	public void movePeriod(TaskView taskFrom, TaskView taskTo, int periodFrom) {
		throw new java.lang.RuntimeException("Not implemented");
	}

	@Override
	public void removePeriod(TaskView task, Period period)
			throws MustBeCalledInsideATransaction {
		throw new java.lang.RuntimeException("Not implemented");
	}

	@Override
	public void addPeriodToTask(ObjectIdentity taskId, Period period)
			throws MustBeCalledInsideATransaction {
		throw new java.lang.RuntimeException("Not implemented");
	}

	@Override
	public void addNoteToTask(TaskView task, String text) {
		throw new java.lang.RuntimeException("Not implemented");
	}

	@Override
	public void stopTask() {
		throw new java.lang.RuntimeException("Not implemented");
	}

	@Override
	public void stopIn(long millisAhead) {
		throw new java.lang.RuntimeException("Not implemented");
	}

	@Override
	public void taskStarted(TaskView selectedTask, long millisAgo) {
		throw new java.lang.RuntimeException("Not implemented");
	}

	@Override
	public void addTasksListener(TasksListener tasksListener) {
		throw new java.lang.RuntimeException("Not implemented");
	}

	@Override
	public Alert taskListChangedAlert() {
		throw new java.lang.RuntimeException("Not implemented");
	}

}
