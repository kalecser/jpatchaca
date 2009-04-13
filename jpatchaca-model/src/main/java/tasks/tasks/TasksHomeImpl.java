package tasks.tasks;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import org.reactivebricks.pulses.Signal;
import org.reactivebricks.pulses.Source;

import periods.Period;
import periods.PeriodsFactory;
import periods.impl.PeriodManagerImpl;
import tasks.TasksListener;
import basic.Alert;
import basic.AlertImpl;
import basic.BasicSystem;
import basic.SystemClock;
import core.ObjectIdentity;
import events.persistence.MustBeCalledInsideATransaction;

public class TasksHomeImpl implements TasksHome {

	private final SystemClock clock;
	private final PeriodsFactory periodsFactory;
	private final AlertImpl taskListChangedAlert;
	private final AlertImpl lastActiveTasksAlert;
	private final AlertImpl activeTaskChangedAlert;
	private final List<TasksListener> tasksListeners;
	private final Deque<TaskView> lastActiveTasks;
	private Task activeTask;
	private final Source<String> activeTaskName;
	private final Tasks tasks;

	public TasksHomeImpl(final PeriodsFactory periodsFactory, final BasicSystem basicSystem, Tasks tasks) {
		this.periodsFactory = periodsFactory;
		this.tasks = tasks;
		this.clock = basicSystem.systemClock();
		this.tasksListeners = new ArrayList<TasksListener>();
		this.taskListChangedAlert = new AlertImpl();
		this.lastActiveTasksAlert = new AlertImpl();
		this.activeTaskChangedAlert = new AlertImpl();
		this.lastActiveTasks = new ArrayDeque<TaskView>();
		
		this.activeTaskName = new Source<String>("");
		
		
	}

	public void createTask(final ObjectIdentity taskId, final String name, final Double budget) throws MustBeCalledInsideATransaction {
		final Task task = new TaskImpl(name, this.clock, budget, new PeriodManagerImpl(), this.periodsFactory);
		tasks.add(taskId, task);
		this.taskListChangedAlert.fire();
		fireTaskCreated(task);
		
		updateLastActiveTasks(task);
	}

	
	private void fireTaskCreated(final Task task) {
		for (final TasksListener tasksListener : tasksListeners)
			tasksListener.createdTask(task);
		
	}
	
	private void fireTaskRemoved(final TaskView task) {
		for (final TasksListener tasksListener : tasksListeners)
			tasksListener.removedTask(task);
		
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

	public void editTask(final ObjectIdentity taskId, final String newName, final Double newBudget) {
		final Task task = tasks.get(taskId);
		task.setName(newName);
		task.setBudgetInHours(newBudget);
		
		updateLastActiveTasks(task);
		if (activeTask == task)
			updateActiveTaskName();
		
		
	}

	public void transferPeriod(final ObjectIdentity selectedTaskId, final int selectedPeriod, final ObjectIdentity targetTaskId) {
		final Task selectedTask = tasks.get(selectedTaskId);
		final Task targetTask = tasks.get(targetTaskId);
		final Period period = selectedTask.periods().get(selectedPeriod);
		
		selectedTask.removePeriod(period);
		targetTask.addPeriod(period);
		
	}

	public void start(final ObjectIdentity taskId) {
		if (activeTask != null)
			activeTask.stop();

		final Task task = tasks.get(taskId);
		
		task.start();
		activeTask = task; 
		
		updateLastActiveTasks(task);
		activeTaskChangedAlert.fire();
		
		updateActiveTaskName();
		
	}

	private void updateLastActiveTasks(final Task task) {
		if (lastActiveTasks.contains(task))
			lastActiveTasks.remove(task);
		
		lastActiveTasks.push(task);
		lastActiveTasksAlert.fire();
	}

	public void stop(final ObjectIdentity taskId) {
		tasks.get(taskId).stop();
		activeTask = tasks.get(taskId); 
		activeTask = null;	
		activeTaskChangedAlert.fire();
		
		updateActiveTaskName();
	}

	private void updateActiveTaskName() {
		if (activeTask == null)
			activeTaskName.supply("");
		else
			activeTaskName.supply(activeTask.name());
		
	}

	public TaskView activeTask() {
		return activeTask;
	}

	public void addTasksListener(final TasksListener tasksListener) {
		tasksListeners.add(tasksListener);		
	}

	public void removePeriodFromTask(final TaskView task, final Period period) {
		((Task)task).removePeriod(period);		
	}

	public void addNoteToTask(final ObjectIdentity idOfTask, final NoteView note) {
		final Task task = tasks.get(idOfTask);
		task.addNote(note);
	}

	@Override
	public Collection<TaskView> lastActiveTasks() {
		return Collections.unmodifiableCollection(lastActiveTasks);
	}

	@Override
	public Alert lastActiveTasksAlert() {
		return lastActiveTasksAlert;
	}

	@Override
	public Alert activeTaskChanged() {
		return activeTaskChangedAlert;
	}

	@Override
	public Signal<String> activeTaskName() {
		return activeTaskName;
	}	
	
}
