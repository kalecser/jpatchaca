package tasks;

import java.util.Collection;
import java.util.Date;

import org.reactivebricks.pulses.Signal;

import periods.Period;
import tasks.tasks.TaskData;
import tasks.tasks.TaskView;
import tasks.tasks.TasksHome;
import basic.Alert;
import core.ObjectIdentity;
import events.persistence.MustBeCalledInsideATransaction;

public interface TasksSystem {
	
	public void createAndStartTaskIn(TaskData newTaskData, long in);
	public void editTask(TaskView task, TaskData taskData);
	public void removeTask(TaskView task);	
	
	public void editPeriod(TaskView task, int periodIndex, Period newPeriod);
	public void setPeriodStarting(TaskView task, int periodIndex,
			Date startDate);
	public void setPeriod(TaskView task, int i, Date startTime,
			Date endTime);
	public void setPeriodEnding(TaskView task, int periodIndex,
			Date endDate);
	public void movePeriod(TaskView taskFrom, TaskView taskTo, int periodFrom);
	public void removePeriod(TaskView task, Period period) throws MustBeCalledInsideATransaction;
	public void addPeriodToTask(ObjectIdentity taskId, Period period) throws MustBeCalledInsideATransaction;
	
	public void addNoteToTask(TaskView task, String text);
	
	public void stopTask(TaskView task);
	public void stopIn(long millisAhead);
	
	public void taskStarted(TaskView selectedTask, long millisAgo);
	
	
	public Collection<TaskView> lastActiveTasks();
	
	public void addTasksListener(TasksListener tasksListener);
	
	public Alert activeTaskChangedAlert();
	public TaskView activeTask();
	public String activeTaskName();
	public Signal<String> activeTaskNameSignal();
	public Alert taskListChangedAlert();
	public Alert lastActiveTasksAlert();
	
	public TasksHome tasksHome();
			
		
	
}
