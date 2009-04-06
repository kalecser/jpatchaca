package tasks;

import java.util.Collection;
import java.util.List;

import org.reactivebricks.pulses.Signal;

import periods.Period;
import basic.Alert;
import core.ObjectIdentity;
import events.persistence.MustBeCalledInsideATransaction;
import tasks.tasks.NoteView;
import tasks.tasks.TaskView;
import tasks.tasks.TasksHome;

public class MockTasksHome implements TasksHome {

	@Override
	public TaskView activeTask() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Alert activeTaskChanged() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Signal<String> activeTaskName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addNoteToTask(ObjectIdentity idOfTask, NoteView note)
			throws MustBeCalledInsideATransaction {
		// TODO Auto-generated method stub

	}

	@Override
	public void addPeriodToTask(ObjectIdentity taskId, Period period)
			throws MustBeCalledInsideATransaction {
		// TODO Auto-generated method stub

	}

	@Override
	public void addTasksListener(TasksListener tasksListener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createTask(ObjectIdentity identity, String taskName,
			Double budget) throws MustBeCalledInsideATransaction {
		// TODO Auto-generated method stub

	}

	@Override
	public void editTask(ObjectIdentity identity, String newName,
			Double newBudget) throws MustBeCalledInsideATransaction {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<TaskView> lastActiveTasks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Alert lastActiveTasksAlert() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(TaskView task) throws MustBeCalledInsideATransaction {
		// TODO Auto-generated method stub

	}

	@Override
	public void removePeriodFromTask(TaskView task, Period period)
			throws MustBeCalledInsideATransaction {
		// TODO Auto-generated method stub

	}

	@Override
	public void start(ObjectIdentity taskId)
			throws MustBeCalledInsideATransaction {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop(ObjectIdentity taskId)
			throws MustBeCalledInsideATransaction {
		// TODO Auto-generated method stub

	}

	@Override
	public void transferPeriod(ObjectIdentity selectedTask, int selectedPeriod,
			ObjectIdentity targetTask) throws MustBeCalledInsideATransaction {
		// TODO Auto-generated method stub

	}

	@Override
	public ObjectIdentity getIdOfTask(TaskView task) {
		
		return new ObjectIdentity("42");
	}

	@Override
	public TaskView getTaskView(ObjectIdentity taskId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Alert taskListChangedAlert() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TaskView> tasks() {
		// TODO Auto-generated method stub
		return null;
	}

}
