package tasks.tasks;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

public class TasksHomeImpl implements TasksHome {

	private final Map<ObjectIdentity, TaskImpl> tasksById;
	private final Map<TaskImpl, ObjectIdentity> idsByTask;
	private final List<TaskImpl> tasksList;
	private final SystemClock clock;
	private final PeriodsFactory periodsFactory;
	private final AlertImpl taskListChangedAlert;
	private final AlertImpl lastActiveTasksAlert;
	private final AlertImpl activeTaskChangedAlert;
	private final List<TasksListener> tasksListeners;
	private final Deque<TaskView> lastActiveTasks;
	private TaskImpl activeTask;
	private final Source<String> activeTaskName;

	public TasksHomeImpl(final PeriodsFactory periodsFactory, final BasicSystem basicSystem) {
		this.periodsFactory = periodsFactory;
		this.clock = basicSystem.systemClock();
		this.tasksById = new LinkedHashMap<ObjectIdentity, TaskImpl>();
		this.idsByTask = new LinkedHashMap<TaskImpl, ObjectIdentity>();
		this.tasksList = new ArrayList<TaskImpl>();
		this.tasksListeners = new ArrayList<TasksListener>();
		this.taskListChangedAlert = new AlertImpl();
		this.lastActiveTasksAlert = new AlertImpl();
		this.activeTaskChangedAlert = new AlertImpl();
		this.lastActiveTasks = new ArrayDeque<TaskView>();
		
		this.activeTaskName = new Source<String>("");
		
		
	}

	public void createTask(final ObjectIdentity taskId, final String name, final Double budget) {
		final TaskImpl task = new TaskImpl(name, this.clock, budget, new PeriodManagerImpl(), this.periodsFactory);
		this.tasksById.put(taskId, task);
		this.idsByTask.put(task, taskId);
		this.tasksList.add(task);
		this.taskListChangedAlert.fire();
		fireTaskCreated(task);
		
		updateLastActiveTasks(task);
	}

	
	private void fireTaskCreated(final TaskImpl task) {
		for (final TasksListener tasksListener : tasksListeners)
			tasksListener.createdTask(task);
		
	}
	
	private void fireTaskRemoved(final TaskView task) {
		for (final TasksListener tasksListener : tasksListeners)
			tasksListener.removedTask(task);
		
	}

	public TaskImpl getTask(final ObjectIdentity taskId) {
		final TaskImpl task = this.tasksById.get(taskId);
		if (task == null) throw new IllegalArgumentException("There is no task with taskId: " + taskId);
		
		return task;
	}

	
	public ObjectIdentity getIdOfTask(final TaskView task) {
		return this.idsByTask.get(task);
	}

	
	public void remove(final TaskView task) {
		final ObjectIdentity taskId = this.getIdOfTask(task);
		this.tasksById.remove(taskId);
		this.idsByTask.remove(task);
		this.tasksList.remove(task);
		this.taskListChangedAlert.fire();
		fireTaskRemoved(task);
		
		lastActiveTasks.remove(task);
		lastActiveTasksAlert.fire();
		
	}

	public List<TaskView> tasks() {
		return Collections.unmodifiableList(new ArrayList<TaskView>(this.tasksList));
	}

	public Alert taskListChangedAlert() {
		return this.taskListChangedAlert;
	}

	public TaskView getTaskView(final ObjectIdentity taskId) {
		return getTask(taskId);
	}

	public void addPeriodToTask(final ObjectIdentity taskId, final Period period) {
		getTask(taskId).addPeriod(period);
	}

	public void editTask(final ObjectIdentity taskId, final String newName, final Double newBudget) {
		final TaskImpl task = getTask(taskId);
		task.setName(newName);
		task.setBudgetInHours(newBudget);
		
		updateLastActiveTasks(task);
		if (activeTask == task)
			updateActiveTaskName();
		
		
	}

	public void transferPeriod(final ObjectIdentity selectedTaskId, final int selectedPeriod, final ObjectIdentity targetTaskId) {
		final TaskImpl selectedTask = getTask(selectedTaskId);
		final TaskImpl targetTask = getTask(targetTaskId);
		final Period period = selectedTask.periods().get(selectedPeriod);
		
		selectedTask.removePeriod(period);
		targetTask.addPeriod(period);
		
	}

	public void start(final ObjectIdentity taskId) {
		if (activeTask != null)
			activeTask.stop();

		final TaskImpl task = getTask(taskId);
		
		task.start();
		activeTask = task; 
		
		updateLastActiveTasks(task);
		activeTaskChangedAlert.fire();
		
		updateActiveTaskName();
		
	}

	private void updateLastActiveTasks(final TaskImpl task) {
		if (lastActiveTasks.contains(task))
			lastActiveTasks.remove(task);
		
		lastActiveTasks.push(task);
		lastActiveTasksAlert.fire();
	}

	public void stop(final ObjectIdentity taskId) {
		getTask(taskId).stop();
		activeTask = getTask(taskId); 
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
		((TaskImpl)task).removePeriod(period);		
	}

	public void addNoteToTask(final ObjectIdentity idOfTask, final NoteView note) {
		final TaskImpl task = getTask(idOfTask);
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
