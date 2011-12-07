package ui.swing.mainScreen.tasks.mock;

import java.util.List;

import jira.JiraIssue;
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

public class MockTask implements TaskView {

	public MockTask(String name) {	}

	@Override
	public String name() {
		return null;
	}

	@Override
	public Signal<TaskName> nameSignal() {
		return null;
	}

	@Override
	public boolean isActive() {
		return false;
	}

	@Override
	public Alert changedAlert() {
		return null;
	}

	@Override
	public Double budgetInHours() {
		return null;
	}

	@Override
	public Double budgetBallanceInHours() {
		return null;
	}

	@Override
	public Double totalTimeInHours() {
		return null;
	}

	@Override
	public long totalTimeInMillis() {
		return 0;
	}

	@Override
	public List<Period> periods() {
		return null;
	}

	@Override
	public Period lastPeriod() {
		return null;
	}

	@Override
	public int lastPeriodIndex() {
		return 0;
	}

	@Override
	public Period getPeriod(int row) {
		return null;
	}

	@Override
	public int periodsCount() {
		return 0;
	}

	@Override
	public Period periodAt(int i) {
		return null;
	}

	@Override
	public PeriodManager periodManager() {
		return null;
	}

	@Override
	public void addPeriodsListener(PeriodsListener listener) {
	}

	@Override
	public void removePeriodListener(PeriodsListener listener) {
	}

	@Override
	public List<NoteView> notes() {
		return null;
	}

	@Override
	public void addNotesListener(NotesListener listener) {
	}

	@Override
	public void removeNotesListener(NotesListener listener) {
	}

	@Override
	public ListSignal<Period> periodsList() {
		return null;
	}

	@Override
	public Maybe<JiraIssue> getJiraIssue() {
		return null;
	}

	@Override
	public int getPeriodIndex(Period period) {
		return 0;
	}

}
