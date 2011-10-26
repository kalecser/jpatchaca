package tasks.taskName;

import lang.Maybe;

import org.reactive.Receiver;
import org.reactive.Signal;
import org.reactive.Source;

import tasks.ActiveTask;
import tasks.Task;

public class ActiveTaskName implements Signal<Maybe<TaskName>> {

	final class ActiveTaskNameListener implements Receiver<TaskName> {
		@Override
		public void receive(final TaskName pulse) {
			supply(pulse);
		}
	}

	private final Source<Maybe<TaskName>> activeTaskName;

	@Override
	public Maybe<TaskName> addReceiver(final Receiver<Maybe<TaskName>> receiver) {
		return activeTaskName.addReceiver(receiver);
	}

	@Override
	public Maybe<TaskName> currentValue() {
		return activeTaskName.currentValue();
	}

	@Override
	public void removeReceiver(final Receiver<Maybe<TaskName>> receiver) {
		activeTaskName.removeReceiver(receiver);
	}

	public ActiveTaskName(final ActiveTask activeTask) {
		activeTaskName = new Source<Maybe<TaskName>>(null);

		final ActiveTaskNameListener listener = new ActiveTaskNameListener();

		activeTask.addReceiver(new Receiver<Maybe<Task>>() {
			private Maybe<Task> activeTaskView = null;

			@Override
			public void receive(final Maybe<Task> pulse) {
				if (activeTaskView != null) {
					activeTaskView.unbox().nameSignal()
							.removeReceiver(listener);
				}
				activeTaskView = pulse;

				if (activeTaskView == null) {
					supply(null);
					return;
				}

				activeTaskView.unbox().nameSignal().addReceiver(listener);

			}

		});
	}

	public void supply(final TaskName taskName) {
		if (taskName == null) {
			activeTaskName.supply(null);
			return;
		}
		activeTaskName.supply(Maybe.wrap(taskName));
	}

}
