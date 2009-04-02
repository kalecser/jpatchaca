package ui.swing.tray;

import java.util.Collection;

import org.reactivebricks.pulses.Signal;

import tasks.tasks.TaskView;
import ui.swing.mainScreen.SelectedTaskName;
import ui.swing.tray.PatchacaTrayModelImpl.Listener;

public interface PatchacaTrayModel {

	public abstract SelectedTaskName selectedTaskName();

	public abstract void destroyMainScreen();

	public abstract void showMainScreen();

	public abstract void stopTaskIn(final long time);

	public abstract void setListener(final Listener listener);

	public abstract void startTaskIn(final TaskView task, final long timeAgo);

	public abstract Collection<TaskView> lastActiveTasks();

	public abstract void createTaskStarted(final long time);

	public abstract Signal<String> activeTaskName();

	public abstract TaskView selectedTask();

	public abstract Signal<String> tooltip();

	public abstract Signal<TaskView> selectedTaskSignal();

	public abstract boolean hasActiveTask();

}