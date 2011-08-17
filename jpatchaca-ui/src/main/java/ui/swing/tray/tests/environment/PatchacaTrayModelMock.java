package ui.swing.tray.tests.environment;

import lang.Maybe;

import org.reactive.Signal;
import org.reactive.Source;

import tasks.TaskView;
import tasks.taskName.TaskName;
import ui.swing.tray.PatchacaTrayModel;
import ui.swing.tray.PatchacaTrayModelImpl.Listener;

public class PatchacaTrayModelMock implements PatchacaTrayModel {

	private final Source<Maybe<TaskName>> _activeTask;
	private final Source<Maybe<TaskName>> selectedTaskName;

	public PatchacaTrayModelMock() {
		_activeTask = new Source<Maybe<TaskName>>(null);
		this.selectedTaskName = new Source<Maybe<TaskName>>(null);
	}

	@Override
	public Signal<Maybe<TaskName>> activeTaskName() {
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
	public Source<Maybe<TaskName>> selectedTaskName() {
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
	public void startTask(final TaskView task, final long timeAgo) {
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

	public void setActiveTask(final TaskName taskName) {
		_activeTask.supply(Maybe.wrap(taskName));
	}

	public void assertTaskCreated() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void showStartTaskScreen() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void copyActiveTaskNameToClipboard() {
		throw new RuntimeException("not implemented");		
	}

}
