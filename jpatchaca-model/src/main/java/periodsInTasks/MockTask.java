package periodsInTasks;

import java.util.List;

import jira.JiraIssue;
import lang.Maybe;

import org.junit.Assert;
import org.reactive.Signal;
import org.reactive.Source;

import periods.Period;
import periods.PeriodManager;
import periods.PeriodsListener;
import periods.impl.PeriodManagerImpl;
import reactive.ListSignal;
import tasks.NotesListener;
import tasks.notes.NoteView;
import tasks.taskName.TaskName;
import tasks.tasks.tests.MockTaskName;
import basic.Alert;

public class MockTask implements tasks.Task {

	private String name;
	private Long startedMillisecondsAgo = null;
	private boolean stopped;
	private final Source<TaskName> nameSignal;
	private final PeriodManagerImpl periodsManager;
	private long stopTime;
	private Maybe<JiraIssue> jiraIssue;

	public MockTask() {
		this("empty");
	}

	public MockTask(final String string) {
		this.name = string;
		nameSignal = new Source<TaskName>(null);
		nameSignal.supply(new MockTaskName(string));
		periodsManager = new PeriodManagerImpl();
	}

	@Override
	public void addNote(final NoteView note) {
		// Auto-generated method stub

	}

	@Override
	public void addNotesListener(final NotesListener listener) {
		// Auto-generated method stub

	}

	@Override
	public void addPeriod(final Period period) {
		periodsManager.addPeriod(period);
	}

	@Override
	public void addPeriodsListener(final PeriodsListener listener) {
		// Auto-generated method stub

	}

	@Override
	public Double budgetBallanceInHours() {
		// Auto-generated method stub
		return null;
	}

	@Override
	public Double budgetInHours() {
		// Auto-generated method stub
		return null;
	}

	@Override
	public Alert changedAlert() {
		// Auto-generated method stub
		return null;
	}

	@Override
	public Period getPeriod(final int index) {
		// Auto-generated method stub
		return null;
	}

	@Override
	public boolean isActive() {
		// Auto-generated method stub
		return false;
	}

	@Override
	public Period lastPeriod() {
		// Auto-generated method stub
		return null;
	}

	@Override
	public int lastPeriodIndex() {
		// Auto-generated method stub
		return 0;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public Signal<TaskName> nameSignal() {
		return nameSignal;
	}

	@Override
	public List<NoteView> notes() {
		// Auto-generated method stub
		return null;
	}

	@Override
	public Period periodAt(final int i) {
		// Auto-generated method stub
		return null;
	}

	@Override
	public PeriodManager periodManager() {
		// Auto-generated method stub
		return null;
	}

	@Override
	public List<Period> periods() {
		// Auto-generated method stub
		return null;
	}

	@Override
	public int periodsCount() {
		// Auto-generated method stub
		return 0;
	}

	@Override
	public void removeNotesListener(final NotesListener listener) {
		// Auto-generated method stub

	}

	@Override
	public void removePeriod(final Period period) {
		// Auto-generated method stub

	}

	@Override
	public void removePeriodListener(final PeriodsListener listener) {
		// Auto-generated method stub

	}

	@Override
	public void setBudgetInHours(final Double newBudget) {
		// Auto-generated method stub

	}

	@Override
	public void setName(final TaskName newNameForTask) {
		name = newNameForTask.unbox();
		nameSignal.supply(newNameForTask);

	}

	@Override
	public void start() {
		start(0);

	}

	@Override
	public void stop() {
		stop(0);
	}

	@Override
	public void stop(final long millisecondsAgo) {
		stopTime = millisecondsAgo;
		stopped = true;
	}

	@Override
	public Double totalTimeInHours() {
		// Auto-generated method stub
		return null;
	}

	@Override
	public long totalTimeInMillis() {
		// Auto-generated method stub
		return 0;
	}

	@Override
	public void start(final long millisecondsAgo) {
		startedMillisecondsAgo = millisecondsAgo;
	}

	public void assertWasStarted(final long millisecondsAgo) {
		Assert.assertEquals((Long) millisecondsAgo, startedMillisecondsAgo);
	}

	public void assertStoppedMillisecondsAgo(final long millis) {
		Assert.assertTrue(stopped);
		Assert.assertEquals(millis, stopTime);
	}

	@Override
	public ListSignal<Period> periodsList() {
		return periodsManager.periodsList();
	}

	@Override
	public Maybe<JiraIssue> getJiraIssue() {
		return jiraIssue;
	}

	@Override
	public void setJiraIssue(final JiraIssue jiraIssue) {
		this.jiraIssue = Maybe.wrap(jiraIssue);
	}

	@Override
	public int getPeriodIndex(final Period period) {
		// TODO Auto-generated method stub
		return 0;
	}

}