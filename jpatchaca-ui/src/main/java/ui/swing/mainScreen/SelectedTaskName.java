package ui.swing.mainScreen;

import lang.Maybe;

import org.picocontainer.Startable;
import org.reactive.Receiver;
import org.reactive.Signal;
import org.reactive.Source;

import tasks.TaskView;
import tasks.taskName.TaskName;
import ui.swing.tasks.SelectedTaskSource;

public class SelectedTaskName extends Source<Maybe<TaskName>> implements
		Startable {

	private final SelectedTaskSource selectedTaskSource;
	private final Receiver<TaskName> taskNameReceiver;
	private final Receiver<TaskView> taskReceiver;
	private TaskView previous;

	public SelectedTaskName(final SelectedTaskSource selectedTaskSource) {
		super(null);
		this.selectedTaskSource = selectedTaskSource;
		this.taskReceiver = new TaskReceiver();
		this.taskNameReceiver = new TaskNameReceiver();
	}

	@Override
	public void start() {
		selectedTaskSource.addReceiver(this.taskReceiver);
	}

	@Override
	public void stop() {
		selectedTaskSource.removeReceiver(this.taskReceiver);
	}

	final class TaskReceiver implements Receiver<TaskView> {

		@Override
		public void receive(final TaskView pulse) {
			taskChangedTo(pulse);
		}

	}

	final class TaskNameReceiver implements Receiver<TaskName> {

		@Override
		public void receive(final TaskName pulse) {
			supply(Maybe.wrap(pulse));
		}
	}

	void taskChangedTo(final TaskView next) {
		if (next == previous) {
			return;
		}
		detachFromPrevious();
		attachToNext(next);
		this.previous = next;
	}

	private void attachToNext(final TaskView next) {
		if (next == null) {
			supply(null);
			return;
		}
		final Signal<TaskName> nextNameSignal = next.nameSignal();
		nextNameSignal.addReceiver(this.taskNameReceiver);
	}

	private void detachFromPrevious() {
		if (this.previous == null) {
			return;
		}
		final Signal<TaskName> previousNameSignal = this.previous.nameSignal();
		previousNameSignal.removeReceiver(this.taskNameReceiver);
	}

}
