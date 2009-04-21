package periodsInTasks;

import java.util.List;

import org.junit.Assert;
import org.reactive.Signal;

import periods.Period;
import periods.PeriodManager;
import periods.PeriodsListener;
import tasks.NotesListener;
import tasks.tasks.NoteView;
import basic.Alert;
import basic.NonEmptyString;

public class MockTask implements tasks.tasks.Task {

	private String name;
	private Long startedMillisecondsAgo = null;
	private boolean stopped;

	public MockTask() {
		name = null;
	}

	public MockTask(final String string) {
		this.name = string;
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
		// Auto-generated method stub

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
	public NonEmptyString nonEmptyName() {
		return new NonEmptyString(name);
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public Signal<String> nameSignal() {
		// Auto-generated method stub
		return null;
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
	public void setName(final String newNameForTask) {
		name = newNameForTask;

	}

	@Override
	public void start() {
		start(0);

	}

	@Override
	public void stop() {
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

	public void assertStopped() {
		Assert.assertTrue(stopped);
	}

}