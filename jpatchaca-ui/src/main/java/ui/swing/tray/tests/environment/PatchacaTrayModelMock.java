package ui.swing.tray.tests.environment;

import lang.Maybe;

import org.reactive.Signal;
import org.reactive.Source;

import tasks.tasks.TaskView;
import ui.swing.tray.PatchacaTrayModel;
import ui.swing.tray.PatchacaTrayModelImpl.Listener;

public class PatchacaTrayModelMock implements PatchacaTrayModel {

	private final Source<Maybe<String>> _activeTask;
	private final Source<String> selectedTaskName;

	public PatchacaTrayModelMock() {
		_activeTask = new Source<Maybe<String>>(Maybe.wrap(""));
		this.selectedTaskName = new Source<String>("");
	}

	@Override
	public Signal<Maybe<String>> activeTaskName() {
		return _activeTask;
	}

	@Override
	public void createTaskStarted(final long time) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void destroyMainScreen() {
		// Nothing special
	}

	@Override
	public boolean hasActiveTask() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public TaskView selectedTask() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Source<String> selectedTaskName() {
		return this.selectedTaskName;
	}

	@Override
	public Signal<TaskView> selectedTaskSignal() {
		return new Source<TaskView>(null);
	}

	@Override
	public void setListener(final Listener listener) {
		// Nothing special
	}

	@Override
	public void showMainScreen() {

	}

	@Override
	public void startTaskIn(final TaskView task, final long timeAgo) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void stopTaskIn(final long time) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Signal<String> tooltip() {
		return new Source<String>(null);
	}

	public void setActiveTask(final String string) {
		_activeTask.supply(Maybe.wrap(string));
	}

	public void assertTaskCreated() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void showStartTaskScreen() {
		throw new RuntimeException("not implemented");
	}

}
