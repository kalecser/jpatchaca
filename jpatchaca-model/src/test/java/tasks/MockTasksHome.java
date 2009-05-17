package tasks;

import periods.Period;
import tasks.home.TasksHome;
import tasks.notes.NoteView;
import tasks.taskName.TaskName;
import basic.Alert;
import core.ObjectIdentity;
import events.persistence.MustBeCalledInsideATransaction;

public class MockTasksHome implements TasksHome {

	@Override
	public void addNoteToTask(final ObjectIdentity idOfTask, final NoteView note)
			throws MustBeCalledInsideATransaction {
		// TODO Auto-generated method stub

	}

	@Override
	public void addPeriodToTask(final ObjectIdentity taskId, final Period period)
			throws MustBeCalledInsideATransaction {
		// TODO Auto-generated method stub

	}

	@Override
	public void addTasksListener(final TasksListener tasksListener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createTask(final ObjectIdentity identity,
			final TaskName taskName, final Double budget)
			throws MustBeCalledInsideATransaction {
		// TODO Auto-generated method stub

	}

	@Override
	public void editTask(final ObjectIdentity identity, final TaskName newName,
			final Double newBudget) throws MustBeCalledInsideATransaction {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(final TaskView task)
			throws MustBeCalledInsideATransaction {
		// TODO Auto-generated method stub

	}

	@Override
	public void removePeriodFromTask(final TaskView task, final Period period)
			throws MustBeCalledInsideATransaction {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop(final ObjectIdentity taskId)
			throws MustBeCalledInsideATransaction {
		// TODO Auto-generated method stub

	}

	@Override
	public void transferPeriod(final ObjectIdentity selectedTask,
			final int selectedPeriod, final ObjectIdentity targetTask)
			throws MustBeCalledInsideATransaction {
		// TODO Auto-generated method stub

	}

	@Override
	public Alert taskListChangedAlert() {
		// TODO Auto-generated method stub
		return null;
	}

}
