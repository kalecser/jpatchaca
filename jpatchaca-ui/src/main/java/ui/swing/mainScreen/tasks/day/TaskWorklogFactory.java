package ui.swing.mainScreen.tasks.day;

import jira.JiraSystem;
import jira.JiraWorklogOverride;
import jira.service.Jira;
import periods.Period;
import tasks.TaskView;
import tasks.TasksSystem;
import basic.Formatter;

public class TaskWorklogFactory {

	private final Jira jira;
	private final Formatter formatter;
	private final JiraWorklogOverride jiraWorklogOverride;
	private final TasksSystem tasksSystem;
	private final JiraSystem jiraSystem;

	public TaskWorklogFactory(Jira jira, Formatter formatter,
			JiraWorklogOverride jiraWorklogOverride, TasksSystem tasksSystem,
			JiraSystem jiraSystem) {
		this.jira = jira;
		this.formatter = formatter;
		this.jiraWorklogOverride = jiraWorklogOverride;
		this.tasksSystem = tasksSystem;
		this.jiraSystem = jiraSystem;
	}

	public TaskWorklog newTaskWorklog(TaskView task, Period period, DayTasksListModel dayTasksListModel) {
		return new TaskWorklog(task, period, formatter, jiraWorklogOverride,
				tasksSystem, jira, jiraSystem, dayTasksListModel);
	}
}
