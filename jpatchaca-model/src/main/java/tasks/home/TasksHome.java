package tasks.home;

import periods.Period;
import tasks.TaskView;
import tasks.TasksListener;
import tasks.notes.NoteView;
import tasks.taskName.TaskName;
import basic.Alert;
import core.ObjectIdentity;
import events.persistence.MustBeCalledInsideATransaction;

public interface TasksHome {

	void createTask(ObjectIdentity identity, TaskName taskName, Double budget)
			throws MustBeCalledInsideATransaction;

	void remove(TaskView task) throws MustBeCalledInsideATransaction;

	void editTask(ObjectIdentity identity, TaskName newName, Double newBudget)
			throws MustBeCalledInsideATransaction;

	// refactor consider moving these methods to periods system
	void addPeriodToTask(ObjectIdentity taskId, Period period)
			throws MustBeCalledInsideATransaction;

	void removePeriodFromTask(TaskView task, Period period)
			throws MustBeCalledInsideATransaction;

	void transferPeriod(ObjectIdentity selectedTask, int selectedPeriod,
			ObjectIdentity targetTask) throws MustBeCalledInsideATransaction;

	void addNoteToTask(ObjectIdentity idOfTask, NoteView note)
			throws MustBeCalledInsideATransaction;

	void stop(ObjectIdentity taskId) throws MustBeCalledInsideATransaction;

	void addTasksListener(TasksListener tasksListener);

	Alert taskListChangedAlert();

}
