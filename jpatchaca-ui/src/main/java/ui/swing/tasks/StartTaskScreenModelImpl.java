package ui.swing.tasks;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jira.Jira;
import jira.JiraIssue;
import jira.JiraOptions;
import jira.JiraUtil;
import jira.exception.JiraException;
import tasks.delegates.StartTaskData;
import tasks.delegates.StartTaskDataParser;
import tasks.delegates.StartTaskDelegate;
import tasks.tasks.TasksView;
import basic.NonEmptyString;

public class StartTaskScreenModelImpl implements StartTaskScreenModel {

	private static final Pattern jiraIssuePattern = Pattern.compile("\\[(.+)\\].*");

	private final TasksView tasks;
	private final StartTaskDelegate startTaskDelegate;
	private final StartTaskDataParser parser;
	private final Jira jira;
	private final JiraOptions options;

	public StartTaskScreenModelImpl(final StartTaskDelegate startTaskDelegate, final TasksView tasks,
			final StartTaskDataParser parser, Jira jira, JiraOptions options) {
		this.startTaskDelegate = startTaskDelegate;
		this.tasks = tasks;
		this.parser = parser;
		this.jira = jira;
		this.options = options;
	}

	@Override
	public void startTask(final String taskName) {
		if (taskName == null) {
			return;
		}

		if (taskName.isEmpty()) {
			return;
		}

		final NonEmptyString nonEmptyTaskName = new NonEmptyString(taskName);
		StartTaskData taskData = parser.parse(nonEmptyTaskName);

		setJiraIssueIfPresent(taskData);

		startTaskDelegate.starTask(taskData);
	}

	private void setJiraIssueIfPresent(StartTaskData taskData) {

		if (!options.isJiraEnabled())
			return;

		Matcher matcher = jiraIssuePattern.matcher(taskData.taskData().getTaskName());
		boolean hasJiraIssue = matcher.matches();
		if (hasJiraIssue) {
			String issueKey = matcher.group(1);
			JiraIssue issue = getIssueByIdOrCry(issueKey);

			if (issue == null)
				return;

			taskData.taskData().setJiraIssue(issue);
			taskData.taskData().setTaskName(JiraUtil.getIssueDescription(issue));
		}
	}

	private JiraIssue getIssueByIdOrCry(String issueKey) {
		try {
			return jira.getIssueByKey(issueKey);
		} catch (JiraException e) {
			// issue not found
			return null;
		}
	}

	@Override
	public List<String> taskNames() {
		return tasks.taskNames();
	}
}
