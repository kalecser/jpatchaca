package tasks;

import lang.Maybe;

import org.reactive.Receiver;
import org.reactive.Signal;
import org.reactive.Source;

import tasks.tasks.Task;
import tasks.tasks.taskName.TaskName;

public class ActiveTaskName implements Signal<Maybe<TaskName>> {

	private final class ActiveTaskNameListener implements Receiver<TaskName> {
		@Override
		public void receive(final TaskName pulse) {
			supply(pulse);
		}
	}

	private final Source<Maybe<TaskName>> activeTaskName;

	public Maybe<TaskName> addReceiver(final Receiver<Maybe<TaskName>> receiver) {
		return activeTaskName.addReceiver(receiver);
	}

	public Maybe<TaskName> currentValue() {
		return activeTaskName.currentValue();
	}

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
