package ui.swing.tray;

import lang.Maybe;

import org.reactive.Signal;
import org.reactive.Source;

import tasks.tasks.TaskView;
import ui.swing.tray.PatchacaTrayModelImpl.Listener;

public interface PatchacaTrayModel {

	public abstract Source<String> selectedTaskName();

	public abstract void destroyMainScreen();

	public abstract void showMainScreen();

	public abstract void stopTaskIn(final long time);

	public abstract void setListener(final Listener listener);

	public abstract void startTaskIn(final TaskView task, final long timeAgo);

	public abstract void createTaskStarted(final long time);

	public abstract Signal<Maybe<String>> activeTaskName();

	public abstract TaskView selectedTask();

	public abstract Signal<String> tooltip();

	public abstract Signal<TaskView> selectedTaskSignal();

	public abstract boolean hasActiveTask();

	public abstract void showStartTaskScreen();

}