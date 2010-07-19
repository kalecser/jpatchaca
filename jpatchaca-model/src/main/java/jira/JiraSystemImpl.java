package jira;

import java.util.Calendar;

import jira.events.SendWorklog;
import periods.Period;
import tasks.TaskView;
import tasks.tasks.Tasks;
import events.EventsSystem;

public class JiraSystemImpl implements JiraSystem {

	private final Jira jira;
	private final EventsSystem eventsSystem;
	private final Tasks tasks;

	public JiraSystemImpl(final Jira jira, final EventsSystem eventsSytem,
			final Tasks tasks) {
		this.jira = jira;
		this.eventsSystem = eventsSytem;
		this.tasks = tasks;
	}

	@Override
	public void addWorklog(final TaskView task, final Period period) {

		if (task.getJiraIssue() == null) {
			throw new IllegalArgumentException("Task without issue: "
					+ task.name());
		}

		try {
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(period.startTime());
			final String duration = JiraUtil.humanFormat(period.totalTime());
			final String issueKey = task.getJiraIssue().unbox().getKey();
			jira.newWorklog(issueKey, calendar, duration);
			final int periodIndex = task.getPeriodIndex(period);
			eventsSystem.writeEvent(new SendWorklog(tasks.idOf(task),
					periodIndex));
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}
}
