package tasks.processors;

import java.io.Serializable;

import lang.Maybe;
import tasks.ActiveTask;
import tasks.Task;
import tasks.tasks.Tasks;
import events.Processor;
import events.StartTaskEvent3;
import events.persistence.MustBeCalledInsideATransaction;

public class StartTaskProcessor3 implements Processor<StartTaskEvent3> {

	private final Tasks tasks;
	private final ActiveTask activeTask;

	public StartTaskProcessor3(final Tasks tasks, final ActiveTask activeTask) {
		this.tasks = tasks;
		this.activeTask = activeTask;
	}

	@Override
	public void execute(final StartTaskEvent3 event)
			throws MustBeCalledInsideATransaction {
		final Maybe<Task> task = taskByNameOrCry(event);
		stopActiveTaskIfAny(event.getMillisecondsAgo());
		startNewTask(event, task);

	}

	private void startNewTask(final StartTaskEvent3 event,
			final Maybe<Task> task) {
		activeTask.supply(task);
		task.unbox().start(event.getMillisecondsAgo());
	}

	private void stopActiveTaskIfAny(final long millisecondsAgo) {
		final Maybe<Task> activeTaskCurrentValue = activeTask.currentValue();
		if (activeTaskCurrentValue != null) {
			activeTaskCurrentValue.unbox().stop(millisecondsAgo);
		}
	}

	private Maybe<Task> taskByNameOrCry(final StartTaskEvent3 event) {
		final Maybe<Task> task = tasks.byName(event.getName());

		if (task == null) {
			throw new RuntimeException("task " + event.getName()
					+ " does not exist");
		}
		return task;
	}

	@Override
	public Class<? extends Serializable> eventType() {
		return StartTaskEvent3.class;
	}

}
