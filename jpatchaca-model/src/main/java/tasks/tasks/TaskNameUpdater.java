package tasks.tasks;

import java.util.List;

import org.reactive.Receiver;
import org.reactive.Signal;

import tasks.tasks.taskName.TaskName;

class TaskNameUpdater {

	private final List<String> names;
	private final Signal<TaskName> nameSignal;
	private final TaskNameReceiver receiver;

	private TaskName name;

	private class TaskNameReceiver implements Receiver<TaskName> {

		@Override
		public synchronized void receive(final TaskName value) {
			if (name == null) {
				names.add(value.unbox());
				return;
			}

			names.remove(name.unbox());
			names.add(value.unbox());
			name = value;
		}

	}

	public TaskNameUpdater(final Task task, final List<String> names) {
		this.names = names;
		nameSignal = task.nameSignal();
		receiver = new TaskNameReceiver();
		name = nameSignal.addReceiver(receiver);
	}

	public synchronized void release() {
		nameSignal.removeReceiver(receiver);
		if (name != null) {
			names.remove(name.unbox());
		}

	}

}
