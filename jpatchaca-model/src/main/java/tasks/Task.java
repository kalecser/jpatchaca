package tasks;

import jira.issue.JiraIssue;
import lang.Maybe;
import periods.Period;
import periods.PeriodsListener;
import tasks.notes.NoteView;
import tasks.taskName.TaskName;

public interface Task extends TaskView {

	public abstract void start();

	public abstract void start(long millisecondsAgo);

	public abstract void stop();

	public abstract void stop(long millisecondsAgo);

	public abstract void setName(TaskName newNameForTask);

	public abstract void setBudgetInHours(Double newBudget);

	public abstract void addPeriod(Period period);

	public abstract void removePeriod(Period period);

	@Override
	public abstract void addPeriodsListener(PeriodsListener listener);

	public abstract void addNote(NoteView note);

	@Override
	public abstract void addNotesListener(NotesListener listener);

	@Override
	public abstract void removeNotesListener(NotesListener listener);

	@Override
	public abstract void removePeriodListener(PeriodsListener listener);

	@Override
	public abstract Maybe<JiraIssue> getJiraIssue();

	public abstract void setJiraIssue(JiraIssue jiraIssue);
}