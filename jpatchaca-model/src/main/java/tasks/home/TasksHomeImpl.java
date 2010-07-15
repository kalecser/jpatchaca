package tasks.home;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import periods.Period;
import periods.PeriodsFactory;
import periods.impl.PeriodManagerImpl;
import tasks.ActiveTask;
import tasks.Task;
import tasks.TaskView;
import tasks.TasksListener;
import tasks.notes.NoteView;
import tasks.taskName.TaskName;
import tasks.tasks.Tasks;
import basic.Alert;
import basic.AlertImpl;
import basic.SystemClock;
import core.ObjectIdentity;
import events.persistence.MustBeCalledInsideATransaction;

public class TasksHomeImpl implements TasksHome {

	private final SystemClock clock;
	private final PeriodsFactory periodsFactory;
	private final AlertImpl taskListChangedAlert;
	private final AlertImpl lastActiveTasksAlert;
	private final List<TasksListener> tasksListeners;
	private final Deque<TaskView> lastActiveTasks;
	private final Tasks tasks;
	private final ActiveTask activeTask;

	public TasksHomeImpl(final PeriodsFactory periodsFactory,
			final Tasks tasks, final SystemClock clock,
			final ActiveTask activeTask) {
		this.periodsFactory = periodsFactory;
		this.tasks = tasks;
		this.clock = clock;
		this.activeTask = activeTask;
		this.tasksListeners = new ArrayList<TasksListener>();
		this.taskListChangedAlert = new AlertImpl();
		this.lastActiveTasksAlert = new AlertImpl();
		this.lastActiveTasks = new ArrayDeque<TaskView>();

	}

	public void createTask(final ObjectIdentity taskId, final TaskName name,
			final Double budget) throws MustBeCalledInsideATransaction {
		final Task task = new TaskImpl(name, this.clock, budget,
				new PeriodManagerImpl(), this.periodsFactory);
		tasks.add(taskId, task);
		
		
		this.taskListChangedAlert.fire();
		fireTaskCreated(task);

	}

	private void fireTaskCreated(final Task task) {
		for (final TasksListener tasksListener : tasksListeners) {
			tasksListener.createdTask(task);
		}

	}

	private void fireTaskRemoved(final TaskView task) {
		for (final TasksListener tasksListener : tasksListeners) {
			tasksListener.removedTask(task);
		}

	}

	public void remove(final TaskView task) {
		tasks.remove(task);
		this.taskListChangedAlert.fire();
		fireTaskRemoved(task);

		lastActiveTasks.remove(task);
		lastActiveTasksAlert.fire();

	}

	public Alert taskListChangedAlert() {
		return this.taskListChangedAlert;
	}

	public void addPeriodToTask(final ObjectIdentity taskId, final Period period) {
		tasks.get(taskId).addPeriod(period);
	}

	public void editTask(final ObjectIdentity taskId, final TaskName newName,
			final Double newBudget) {
		final Task task = tasks.get(taskId);
		task.setName(newName);
		task.setBudgetInHours(newBudget);

	}

	public void transferPeriod(final ObjectIdentity selectedTaskId,
			final int selectedPeriod, final ObjectIdentity targetTaskId) {
		final Task selectedTask = tasks.get(selectedTaskId);
		final Task targetTask = tasks.get(targetTaskId);
		final Period period = selectedTask.periods().get(selectedPeriod);

		selectedTask.removePeriod(period);
		targetTask.addPeriod(period);

	}

	public void addTasksListener(final TasksListener tasksListener) {
		tasksListeners.add(tasksListener);
	}

	public void removePeriodFromTask(final TaskView task, final Period period) {
		((Task) task).removePeriod(period);
	}

	public void addNoteToTask(final ObjectIdentity idOfTask, final NoteView note) {
		final Task task = tasks.get(idOfTask);
		task.addNote(note);
	}

	@Override
	public void stop(final ObjectIdentity taskId)
			throws MustBeCalledInsideATransaction {

		tasks.get(taskId).stop();
		activeTask.supply(null);

	}

}
