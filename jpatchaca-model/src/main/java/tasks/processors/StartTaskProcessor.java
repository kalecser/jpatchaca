package tasks.processors;

import java.io.Serializable;

import tasks.TaskView;
import tasks.tasks.TasksView;
import basic.NonEmptyString;
import events.Processor;
import events.StartTaskEvent;
import events.StartTaskEvent2;
import events.persistence.MustBeCalledInsideATransaction;

public class StartTaskProcessor implements Processor<StartTaskEvent> {

	private final StartTaskProcessor2 startTaskProcessor;
	private final TasksView tasks;

	public StartTaskProcessor(final StartTaskProcessor2 startTaskProcessor,
			final TasksView tasks) {
		this.startTaskProcessor = startTaskProcessor;
		this.tasks = tasks;
	}

	public void execute(final StartTaskEvent event)
			throws MustBeCalledInsideATransaction {
		final TaskView task = tasks.get(event.getTaskId());
		startTaskProcessor.execute(new StartTaskEvent2(new NonEmptyString(task
				.name()), 0));
	}

	public Class<? extends Serializable> eventType() {
		return StartTaskEvent.class;
	}

}
