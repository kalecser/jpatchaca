package tasks;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.picocontainer.Startable;
import org.reactivebricks.pulses.Signal;

import periods.Period;
import periods.PeriodsFactory;
import tasks.delegates.StartTaskDelegate;
import tasks.processors.AddNoteToTaskProcessor;
import tasks.processors.CreateAndStartTaskProcessor;
import tasks.processors.CreateTaskProcessor;
import tasks.processors.CreateTaskProcessor2;
import tasks.processors.CreateTaskProcessor3;
import tasks.processors.EditPeriodProcessor;
import tasks.processors.EditPeriodProcessor2;
import tasks.processors.EditTaskProcessor;
import tasks.processors.MovePeriodProcessor;
import tasks.processors.RemoveTaskProcessor;
import tasks.processors.RenameTaskProcessor;
import tasks.processors.StartTaskProcessor;
import tasks.processors.StopTaskProcessor;
import tasks.tasks.NotesHome;
import tasks.tasks.NotesHomeImpl;
import tasks.tasks.TaskData;
import tasks.tasks.TaskView;
import tasks.tasks.Tasks;
import tasks.tasks.TasksHome;
import basic.Alert;
import basic.BasicSystem;
import basic.IdProvider;
import core.ObjectIdentity;
import events.AddNoteToTaskEvent;
import events.AddPeriodEvent;
import events.CreateTaskEvent3;
import events.EditPeriodEvent2;
import events.EditTaskEvent;
import events.EventsSystem;
import events.MovePeriodEvent;
import events.RemoveTaskEvent;
import events.StopTaskEvent;
import events.persistence.MustBeCalledInsideATransaction;


public class TasksSystemImpl implements TasksSystem, Startable {
		
	private final IdProvider provider;
	private final TasksHome tasksHome;
	private final EventsSystem eventsSystem;
	private final StartTaskDelegate startTaskDelegate;
	private final Tasks tasks;
	

	public TasksSystemImpl(final BasicSystem basicSystem, TasksHome tasksHome,  final EventsSystem eventsSystem, final PeriodsFactory periodsFactory, StartTaskDelegate startTaskDelegate, Tasks tasks){
		this.eventsSystem = eventsSystem;
		this.tasksHome = tasksHome;
		this.startTaskDelegate = startTaskDelegate;
		this.tasks = tasks;		
		this.provider = basicSystem.idProvider();
		
		final NotesHome notesHome = new NotesHomeImpl(basicSystem);
		
		eventsSystem.addProcessor(new CreateTaskProcessor(tasksHome));
		eventsSystem.addProcessor(new CreateTaskProcessor2(tasksHome));
		eventsSystem.addProcessor(new CreateTaskProcessor3(tasksHome));
		eventsSystem.addProcessor(new EditPeriodProcessor(tasks));
		eventsSystem.addProcessor(new EditPeriodProcessor2(tasksHome, tasks));
		eventsSystem.addProcessor(new EditTaskProcessor(tasksHome));		
		eventsSystem.addProcessor(new MovePeriodProcessor(tasksHome));
		eventsSystem.addProcessor(new RemoveTaskProcessor(tasks, tasksHome));
		eventsSystem.addProcessor(new RenameTaskProcessor(tasksHome));
		eventsSystem.addProcessor(new StartTaskProcessor(tasksHome));
		eventsSystem.addProcessor(new StopTaskProcessor(tasksHome));
		eventsSystem.addProcessor(new CreateAndStartTaskProcessor(tasksHome));
		eventsSystem.addProcessor(new AddNoteToTaskProcessor(tasksHome, notesHome));
		
		
	}

	public synchronized void createTask(final TaskData data) {	
		
		final CreateTaskEvent3 event = produceCreateTaskEvent(data);
		this.eventsSystem.writeEvent(event);
		
	}

	private CreateTaskEvent3 produceCreateTaskEvent(final TaskData data) {
		final ObjectIdentity newTasksId = this.provider.provideId();
		final CreateTaskEvent3 event = new CreateTaskEvent3(newTasksId, 
				data.getTaskName(), data.getBudget(), data.getLabel());
		return event;
	}

	public synchronized void addPeriod(final TaskView selectedTask, final Period period) {
		
		final AddPeriodEvent event = new AddPeriodEvent(
				tasks.idOf(selectedTask), 
				period.startTime(), 
				period.endTime());
		this.eventsSystem.writeEvent(event);
		
		
	}

	public synchronized void editTask(final TaskView taskView, final TaskData taskData) {
							
		final ObjectIdentity idOfTask = tasks.idOf(taskView);
		final EditTaskEvent event = new EditTaskEvent(idOfTask, taskData
			.getTaskName(), taskData.getBudget());

		this.eventsSystem.writeEvent(event);

	}

	public synchronized void editPeriod(final TaskView selectedTask, final int periodIndex, final Period newPeriod) {		

		final EditPeriodEvent2 event = new EditPeriodEvent2(
				tasks.idOf(selectedTask),
				periodIndex,
				newPeriod.startTime(),
				newPeriod.endTime());
		this.eventsSystem.writeEvent(event);
							
	}

	public synchronized void removeTask(final TaskView task) {
		final RemoveTaskEvent event = new RemoveTaskEvent(tasks.idOf(task));
		this.eventsSystem.writeEvent(event);
	}
	
	public synchronized void addNoteToTask(final TaskView task, final String text) {
		this.eventsSystem.writeEvent(new AddNoteToTaskEvent(tasks.idOf(task), text));
	}

	public synchronized void movePeriod(final TaskView taskFrom, final TaskView taskTo, final int periodFrom) {
		final MovePeriodEvent event = new MovePeriodEvent(tasks.idOf(taskFrom), periodFrom, tasks.idOf(taskTo));
		this.eventsSystem.writeEvent(event);		
	}

	public synchronized void stopTask(final TaskView task) {
		this.eventsSystem.writeEvent(new StopTaskEvent(tasks.idOf(task)));
	}
	
	@Override
	public synchronized void createAndStartTaskIn(final TaskData newTaskData, final long in) {
		final CreateTaskEvent3 createTaskEvent = produceCreateTaskEvent(newTaskData);
		this.eventsSystem.writeEvent(createTaskEvent);
		TaskView task = tasks.get(createTaskEvent.getObjectIdentity());
		taskStarted(task, in);
	}

	

	public synchronized TaskView activeTask() {
		return tasksHome.activeTask();
	}

	public synchronized Alert activeTaskChangedAlert() {
		return tasksHome.activeTaskChanged();
	}

	public synchronized void addTasksListener(final TasksListener tasksListener) {
		tasksHome.addTasksListener(tasksListener);		
	}

	public synchronized Alert taskListChangedAlert() {
		return tasksHome.taskListChangedAlert();
	}

	@Override
	public synchronized void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public synchronized void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public synchronized String activeTaskName() {
		
		if (activeTask() == null)
			return null;
		
		return activeTask().name();
	}

	@Override
	public synchronized void stopIn(final long millisAhead) {
		final TaskView task = activeTask();
		
		if (task == null)
			throw new IllegalStateException();
		
		stopTask(task);
		final Period lastPeriod = task.lastPeriod();
		final Period alteredPeriod = lastPeriod.createNewEnding(new Date(lastPeriod.endTime().getTime() + millisAhead));
		editPeriod(task, task.lastPeriodIndex(), alteredPeriod);
	}

	@Override
	public synchronized void taskStarted(final TaskView task, final long millisAgo) {
		startTaskDelegate.starTask(task);
		final Period lastPeriod = task.lastPeriod();
		final Period alteredPeriod = lastPeriod.createNewStarting(new Date(lastPeriod.startTime().getTime() - millisAgo));
		editPeriod(task, task.lastPeriodIndex(), alteredPeriod);
	}

	@Override
	public synchronized Collection<TaskView> lastActiveTasks() {
		return tasksHome.lastActiveTasks();
	}

	@Override
	public synchronized Alert lastActiveTasksAlert() {
		return tasksHome.lastActiveTasksAlert();
	}

	@Override
	public synchronized void setPeriodEnding(final TaskView task, final int periodIndex, final Date endDate) {
		
		final Date endDateAdjusted = getEndTime(task.getPeriod(periodIndex).startTime(), endDate);
		editPeriod(task, periodIndex, task.getPeriod(periodIndex).createNewEnding(endDateAdjusted));
	}

	@Override
	public synchronized void setPeriodStarting(final TaskView task, final int periodIndex, final Date startDate) {
		if (periodIndex == -1)
			throw new IllegalArgumentException("Index must be positive");
		final Period period = task.getPeriod(periodIndex);
		editPeriod(task, periodIndex, period.createNewStarting(startDate));				
	}
	
	private synchronized Date getEndTime(final Date startDate, final Date endDate) {
		if (startDate.after(endDate))
			return datePlusOneDay(endDate);

		return endDate;
	}

	private synchronized Date datePlusOneDay(final Date insertedDate) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(insertedDate);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	@Override
	public synchronized Signal<String> activeTaskNameSignal() {
		return tasksHome.activeTaskName();
	}

	@Override
	public void setPeriod(final TaskView task, final int i, final Date adjustedStartTime,
			final Date adjustedEndTime) {
		final Period period = task.getPeriod(i);
		editPeriod(task, i, 
				period.createNewStarting(adjustedStartTime).
					createNewEnding(adjustedEndTime));
		
	}

	@Override
	public void addPeriodToTask(final ObjectIdentity taskId, final Period period) throws MustBeCalledInsideATransaction {
		tasksHome.addPeriodToTask(taskId, period);
	}

	@Override
	public void removePeriod(final TaskView task, final Period period) throws MustBeCalledInsideATransaction{
		tasksHome.removePeriodFromTask(task, period);
	}

	@Override
	public TasksHome tasksHome() {
		return tasksHome;
	}


	

}
