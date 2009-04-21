package tasks;

import lang.Maybe;

import org.reactive.Receiver;
import org.reactive.Signal;
import org.reactive.Source;

import tasks.tasks.Task;

public class ActiveTaskName implements Signal<Maybe<String>> {

	private final class ActiveTaskNameListener implements Receiver<String> {
		@Override
		public void receive(final String pulse) {
			supply(pulse);
		}
	}

	private final Source<Maybe<String>> activeTaskName;

	public Maybe<String> addReceiver(final Receiver<Maybe<String>> receiver) {
		return activeTaskName.addReceiver(receiver);
	}

	public Maybe<String> currentValue() {
		return activeTaskName.currentValue();
	}

	public void removeReceiver(final Receiver<Maybe<String>> receiver) {
		activeTaskName.removeReceiver(receiver);
	}

	public ActiveTaskName(final ActiveTask activeTask) {
		activeTaskName = new Source<Maybe<String>>(null);

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

	public void supply(final String value) {
		if (value == null) {
			activeTaskName.supply(null);
			return;
		}
		activeTaskName.supply(Maybe.wrap(value));
	}

}
