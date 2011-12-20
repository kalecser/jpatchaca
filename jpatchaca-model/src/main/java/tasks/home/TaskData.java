package tasks.home;

import jira.issue.JiraIssue;
import basic.NonEmptyString;

public class TaskData {

	private String taskName;
	private Double budget;
	private String label;
	private JiraIssue jiraIssue;

	public TaskData(NonEmptyString taskName)
	{
		this.taskName = taskName.unbox();
		this.budget = 0.0;
	}	
	
	public TaskData(NonEmptyString taskName, JiraIssue jiraIssue)
	{
		this(taskName);
		this.jiraIssue = jiraIssue;
	}

	public final String getTaskName() {
		return taskName;
	}

	public Double getBudget() {
		return budget;
	}
	
	public String getLabel(){
		return label;
	}

	public JiraIssue getJiraIssue() {
		return jiraIssue;
	}
	
	public void setBudget(Double budget) {
		this.budget = budget;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public void setJiraIssue(JiraIssue jiraIssue) {
		this.jiraIssue = jiraIssue;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
		
	}
}
