package statistics.tests.environment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.reactive.Signal;

import periods.Period;
import periods.PeriodManager;
import periods.PeriodsListener;
import tasks.NotesListener;
import tasks.TaskView;
import tasks.notes.NoteView;
import tasks.taskName.TaskName;
import basic.Alert;

public class FakeTask implements TaskView {

	List<Period> periods = new ArrayList<Period>();
	private final String name;

	public FakeTask(final String name) {
		this.name = name;
	}

	public FakeTask withWorkHoursDay(final Date date, final double hours) {
		final long endTime = (long) (date.getTime() + (hours * DateUtils.MILLIS_PER_HOUR));
		periods.add(new Period(date, new Date(endTime)));
		return this;
	}

	@Override
	public void addNotesListener(final NotesListener listener) {
		throw new RuntimeException("Not expected");

	}

	@Override
	public void addPeriodsListener(final PeriodsListener listener) {
		throw new RuntimeException("Not expected");

	}

	@Override
	public Double budgetBallanceInHours() {
		throw new RuntimeException("Not expected");
	}

	@Override
	public Double budgetInHours() {
		throw new RuntimeException("Not expected");
	}

	@Override
	public Alert changedAlert() {
		throw new RuntimeException("Not expected");
	}

	@Override
	public Period getPeriod(final int row) {
		throw new RuntimeException("Not expected");
	}

	@Override
	public boolean isActive() {
		throw new RuntimeException("Not expected");
	}

	@Override
	public Period lastPeriod() {
		throw new RuntimeException("Not expected");
	}

	@Override
	public int lastPeriodIndex() {
		throw new RuntimeException("Not expected");
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public Signal<TaskName> nameSignal() {
		throw new RuntimeException("Not expected");
	}

	@Override
	public List<NoteView> notes() {
		throw new RuntimeException("Not expected");
	}

	@Override
	public Period periodAt(final int i) {
		throw new RuntimeException("Not expected");
	}

	@Override
	public PeriodManager periodManager() {
		throw new RuntimeException("Not expected");
	}

	@Override
	public List<Period> periods() {
		return periods;
	}

	@Override
	public int periodsCount() {
		throw new RuntimeException("Not expected");
	}

	@Override
	public void removeNotesListener(final NotesListener listener) {
		throw new RuntimeException("Not expected");

	}

	@Override
	public void removePeriodListener(final PeriodsListener listener) {
		throw new RuntimeException("Not expected");

	}

	@Override
	public Double totalTimeInHours() {
		throw new RuntimeException("Not expected");
	}

	@Override
	public long totalTimeInMillis() {
		throw new RuntimeException("Not expected");
	}

}
