package ui.swing.tray.tests.environment;

import java.util.Collection;

import org.reactivebricks.pulses.Signal;
import org.reactivebricks.pulses.Source;

import tasks.tasks.TaskView;
import ui.swing.mainScreen.SelectedTaskName;
import ui.swing.tray.PatchacaTrayModel;
import ui.swing.tray.PatchacaTrayModelImpl.Listener;

public class PatchacaTrayModelMock implements PatchacaTrayModel {

	private Source<String> _activeTask = new Source<String>("");

	@Override
	public Signal<String> activeTaskName() {
		return _activeTask;
	}

	@Override
	public void createTaskStarted(long time) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void destroyMainScreen() {
	}

	@Override
	public boolean hasActiveTask() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Collection<TaskView> lastActiveTasks() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public TaskView selectedTask() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public SelectedTaskName selectedTaskName() {
		return new SelectedTaskName();
	}

	@Override
	public Signal<TaskView> selectedTaskSignal() {
		return new Source<TaskView>(null);
	}

	@Override
	public void setListener(Listener listener) {
	}

	@Override
	public void showMainScreen() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void startTaskIn(TaskView task, long timeAgo) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void stopTaskIn(long time) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Signal<String> tooltip() {
		return new Source<String>(null);
	}

	public void setActiveTask(String string) {
		_activeTask.supply(string);
	}

	public void assertTaskCreated() {
		throw new RuntimeException("not implemented");
	}


}