package jira.processors;

import jira.events.SetJiraIssueToTask;

import org.picocontainer.Startable;

import tasks.Task;
import tasks.tasks.Tasks;
import events.EventsSystem;
import events.Processor;
import events.persistence.MustBeCalledInsideATransaction;

public class SetJiraIssueToTaskProcessor implements
		Processor<SetJiraIssueToTask>, Startable {

	private final Tasks tasks;

	public SetJiraIssueToTaskProcessor(final EventsSystem eventsSystem,
			final Tasks tasks) {
		eventsSystem.addProcessor(this);
		this.tasks = tasks;
	}

	@Override
	public Class<SetJiraIssueToTask> eventType() {
		return SetJiraIssueToTask.class;
	}

	@Override
	public void execute(final SetJiraIssueToTask eventObj)
			throws MustBeCalledInsideATransaction {

		final Task task = tasks.get(eventObj.getTaskId());
		task.setJiraIssue(eventObj.getJiraIssueId());
	}

	@Override
	public void start() {
	}

	@Override
	public void stop() {
	}

}
