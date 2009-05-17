package tasks.processors;

import java.io.Serializable;

import lang.Maybe;


import tasks.ActiveTask;
import tasks.Task;
import tasks.tasks.Tasks;
import events.Processor;
import events.StartTaskEvent2;
import events.persistence.MustBeCalledInsideATransaction;

public class StartTaskProcessor2 implements Processor<StartTaskEvent2> {

	private final ActiveTask activeTask;
	private final Tasks tasks;

	public StartTaskProcessor2(final Tasks tasks, final ActiveTask activeTask) {
		this.tasks = tasks;
		this.activeTask = activeTask;
	}

	public void execute(final StartTaskEvent2 event)
			throws MustBeCalledInsideATransaction {
		final Maybe<Task> task = tasks.byName(event.getName());

		if (task == null) {
			throw new RuntimeException("task " + event.getName()
					+ " does not exist");
		}

		final Maybe<Task> activeTaskCurrentValue = activeTask.currentValue();
		if (activeTaskCurrentValue != null) {
			activeTaskCurrentValue.unbox().stop();
		}

		activeTask.supply(task);
		task.unbox().start(event.getMillisecondsAgo());

	}

	public Class<? extends Serializable> eventType() {
		return StartTaskEvent2.class;
	}

}
