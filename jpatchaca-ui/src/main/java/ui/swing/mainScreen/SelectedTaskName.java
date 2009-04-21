package ui.swing.mainScreen;

import org.picocontainer.Startable;
import org.reactivebricks.pulses.Receiver;
import org.reactivebricks.pulses.Signal;
import org.reactivebricks.pulses.Source;

import tasks.tasks.TaskView;
import ui.swing.tasks.SelectedTaskSource;

public class SelectedTaskName extends Source<String> implements Startable {

	private final SelectedTaskSource selectedTaskSource;
	private final Receiver<String> taskNameReceiver;
	private final Receiver<TaskView> taskReceiver;
	private TaskView previous;

	public SelectedTaskName(final SelectedTaskSource selectedTaskSource) {
		super("");
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

	final class TaskNameReceiver implements Receiver<String> {

		@Override
		public void receive(final String pulse) {
			supply(pulse);
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
			supply("");
			return;
		}
		final Signal<String> nextNameSignal = next.nameSignal();
		nextNameSignal.addReceiver(this.taskNameReceiver);
	}

	private void detachFromPrevious() {
		if (this.previous == null) {
			return;
		}
		final Signal<String> previousNameSignal = this.previous.nameSignal();
		previousNameSignal.removeReceiver(this.taskNameReceiver);
	}

}
