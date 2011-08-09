package ui.swing.mainScreen.tasks.day;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import jira.JiraWorklogOverride;
import periods.Period;
import tasks.TaskView;
import basic.Formatter;

public class Pair implements Comparable<Pair> {
	private final TaskView task;
	private final Period period;
	private final Formatter formatter;
	private final JiraWorklogOverride worklogOverride;

	public Pair(final TaskView task, final Period period,
			final Formatter formatter, JiraWorklogOverride worklogOverride) {
		this.task = task;
		this.period = period;
		this.formatter = formatter;
		this.worklogOverride = worklogOverride;
	}

	public TaskView task() {
		return task;
	}

	public Period period() {
		return period;
	}

	@Override
	public int compareTo(final Pair outroPar) {		
		return period.startTime().compareTo(outroPar.period.startTime());
	}

	public String formatedStartTime() {
		return formatter.formatShortTime(period.startTime());
	}

	public String formatedEndTime() {
		if (period.endTime() == null)
			return "";
		return formatter.formatShortTime(period.endTime());
	}

	public String worklogStatus() {
		if (task.getJiraIssue() == null)
			return "no issue";
		if (period.isWorklogSent())
			return "sent";
		return "not sent";
	}

	public String formatedTotalTime() {
		final NumberFormat format = new DecimalFormat("#0.00");
		return format.format(period.getHours());
	}

	public String timeToSend() {
		return worklogOverride.getDuration(period);
	}

	public String taskName() {
		return task.name();
	}

	public int periodIndex() {
		return task.getPeriodIndex(period);
	}

}