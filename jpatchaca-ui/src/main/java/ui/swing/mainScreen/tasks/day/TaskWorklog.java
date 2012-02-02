package ui.swing.mainScreen.tasks.day;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;

import jira.JiraSystem;
import jira.JiraWorklogOverride;
import jira.issue.JiraIssue;
import jira.service.Jira;
import lang.Maybe;
import periods.Period;
import tasks.TaskView;
import tasks.TasksSystem;
import basic.Formatter;

public class TaskWorklog implements Comparable<TaskWorklog> {
	private final TaskView task;
	private final Period period;
	private final Formatter formatter;
	private final JiraWorklogOverride worklogOverride;
	private final TasksSystem tasksSystem;
	private final Jira jira;
	private final JiraSystem jiraSystem;

	public TaskWorklog(final TaskView task, final Period period,
			final Formatter formatter, JiraWorklogOverride worklogOverride,
			TasksSystem tasksSystem, Jira jira, JiraSystem jiraSystem) {
		this.task = task;
		this.period = period;
		this.formatter = formatter;
		this.worklogOverride = worklogOverride;
		this.tasksSystem = tasksSystem;
		this.jira = jira;
		this.jiraSystem = jiraSystem;
	}

	@Override
	public int compareTo(final TaskWorklog worklog) {
		return period.startTime().compareTo(worklog.period.startTime());
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
		
		if(jira.isWorkable(task.getJiraIssue().unbox()))
		    return "issue not workable";
		
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

	public boolean canSend() {
		Maybe<JiraIssue> jiraIssue = task.getJiraIssue();
		if (jiraIssue == null)
			return false;

		if (period.isWorklogSent())
			return false;

		return jira.isWorkable(jiraIssue.unbox());
	}

	public void send() {
		jiraSystem.addWorklog(task, period);
	}

	public void editPeriodEnd(final String value) {
		Date dateParsed = editDate(period.endTime(), value);
		if (dateParsed != null)
			tasksSystem.setPeriodEnding(task, periodIndex(), dateParsed);
	}

	public void editPeriodStart(final String value) {
		Date dateParsed = editDate(period.startTime(), value);
		if (dateParsed != null) 
			tasksSystem.setPeriodStarting(task, periodIndex(), dateParsed);
	}

	private Date editDate(final Date date, final String newHour) {
		final String timeFormatted = formatter.formatShortDate(date) + " "
				+ newHour;
		Date dateParsed = null;
		try {
			dateParsed = formatter.parseShortDateTime(timeFormatted);
		} catch (final ParseException e) {
			e.printStackTrace();
		}
		return dateParsed;
	}

	public void overrideTimeSpent(String value) {
		worklogOverride.overrideTimeSpentForPeriod(value, period);
	}

	public double getMiliseconds() {
		return period.getMiliseconds();
	}

}