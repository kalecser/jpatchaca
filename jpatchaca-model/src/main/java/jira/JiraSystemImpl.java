package jira;

import java.util.Calendar;

import jira.events.SendWorklog;
import jira.service.Jira;
import periods.Period;
import tasks.TaskView;
import tasks.tasks.Tasks;
import events.EventsConsumer;

public class JiraSystemImpl implements JiraSystem {

	private final Jira jira;
	private final EventsConsumer consumer;
	private final Tasks tasks;
	private final JiraWorklogOverride worklogOverride;

	public JiraSystemImpl(final Jira jira, final EventsConsumer consumer,
			final Tasks tasks, JiraWorklogOverride worklogOverride) {
		this.jira = jira;
		this.consumer = consumer;
		this.tasks = tasks;
		this.worklogOverride = worklogOverride;
	}

	@Override
	public void addWorklog(final TaskView task, final Period period) {

		if (task.getJiraIssue() == null) {
			throw new IllegalArgumentException("Task without issue: "
					+ task.name());
		}

		final String issueKey = task.getJiraIssue().unbox().getKey();
		logWorkOnIssue(period, issueKey);
		markPeriodAsSent(task, period);

	}

	private void markPeriodAsSent(final TaskView task, final Period period) {
		final int periodIndex = task.getPeriodIndex(period);
		consumer.consume(new SendWorklog(tasks.idOf(task),
				periodIndex));
	}

	void logWorkOnIssue(final Period period, final String issueKey) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(period.startTime());
		
		final String duration = worklogOverride.getDuration(period);
		
		if (duration.equals("0h 0m")){
			return;
		}
		
		jira.newWorklog(issueKey, calendar, duration);
	}
}
