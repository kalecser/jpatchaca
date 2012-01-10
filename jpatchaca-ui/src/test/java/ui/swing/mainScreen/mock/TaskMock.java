package ui.swing.mainScreen.mock;

import java.util.List;

import jira.issue.JiraIssue;
import lang.Maybe;

import org.reactive.Signal;

import periods.Period;
import periods.PeriodManager;
import periods.PeriodsListener;
import reactive.ListSignal;
import basic.Alert;
import tasks.NotesListener;
import tasks.TaskView;
import tasks.notes.NoteView;
import tasks.taskName.TaskName;

public class TaskMock implements TaskView {

	private final String name;

	public TaskMock(String name) {
		this.name = name;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public Signal<TaskName> nameSignal() {
		throw new java.lang.RuntimeException("Not implemented");
	}

	@Override
	public boolean isActive() {
		return false;
	}

	@Override
	public Alert changedAlert() {
		throw new java.lang.RuntimeException("Not implemented");
	}

	@Override
	public Double budgetInHours() {
		throw new java.lang.RuntimeException("Not implemented");
	}

	@Override
	public Double budgetBallanceInHours() {
		throw new java.lang.RuntimeException("Not implemented");
	}

	@Override
	public Double totalTimeInHours() {
		throw new java.lang.RuntimeException("Not implemented");
	}

	@Override
	public long totalTimeInMillis() {
		throw new java.lang.RuntimeException("Not implemented");
	}

	@Override
	public List<Period> periods() {
		throw new java.lang.RuntimeException("Not implemented");
	}

	@Override
	public Period lastPeriod() {
		throw new java.lang.RuntimeException("Not implemented");
	}

	@Override
	public int lastPeriodIndex() {
		throw new java.lang.RuntimeException("Not implemented");
	}

	@Override
	public Period getPeriod(int row) {
		throw new java.lang.RuntimeException("Not implemented");
	}

	@Override
	public int periodsCount() {
		throw new java.lang.RuntimeException("Not implemented");
	}

	@Override
	public Period periodAt(int i) {
		throw new java.lang.RuntimeException("Not implemented");
	}

	@Override
	public PeriodManager periodManager() {
		throw new java.lang.RuntimeException("Not implemented");
	}

	@Override
	public void addPeriodsListener(PeriodsListener listener) {
		throw new java.lang.RuntimeException("Not implemented");
	}

	@Override
	public void removePeriodListener(PeriodsListener listener) {
		throw new java.lang.RuntimeException("Not implemented");
	}

	@Override
	public List<NoteView> notes() {
		throw new java.lang.RuntimeException("Not implemented");
	}

	@Override
	public void addNotesListener(NotesListener listener) {
		throw new java.lang.RuntimeException("Not implemented");
	}

	@Override
	public void removeNotesListener(NotesListener listener) {
		throw new java.lang.RuntimeException("Not implemented");
	}

	@Override
	public ListSignal<Period> periodsList() {
		throw new java.lang.RuntimeException("Not implemented");
	}

	@Override
	public Maybe<JiraIssue> getJiraIssue() {
		throw new java.lang.RuntimeException("Not implemented");
	}

	@Override
	public int getPeriodIndex(Period period) {
		throw new java.lang.RuntimeException("Not implemented");
	}
	
	@Override
	public String toString() {
		return name;
	}

}
