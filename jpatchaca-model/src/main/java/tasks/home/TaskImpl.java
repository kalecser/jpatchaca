package tasks.home;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jira.issue.JiraIssue;
import lang.Maybe;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.time.DateUtils;
import org.reactive.Signal;
import org.reactive.Source;

import periods.Period;
import periods.PeriodManager;
import periods.PeriodsFactory;
import periods.PeriodsListener;
import reactive.ListSignal;
import tasks.NotesListener;
import tasks.Task;
import tasks.notes.NoteView;
import tasks.taskName.TaskName;
import basic.Alert;
import basic.AlertImpl;
import basic.NonEmptyString;
import basic.SystemClock;

class TaskImpl implements Task {

	private boolean _active;
	private Period activePeriod;
	private final AlertImpl changedAlert;
	private final PeriodsFactory periodsFactory;
	private final SystemClock clock;
	private final PeriodManager manager;
	private String name;
	private Double budget;

	private final List<NotesListener> notesListeners;
	private final List<NoteView> notes;
	private final Source<TaskName> _nameSource;

	private Maybe<JiraIssue> jiraIssue;

	public TaskImpl(final TaskName name, final SystemClock clock,
			final Double budget, final PeriodManager manager,
			final PeriodsFactory periodsFactory) {

		Validate.notNull(name, "name");

		this.name = name.unbox();
		this.clock = clock;
		this.budget = budget;
		this.manager = manager;
		this.periodsFactory = periodsFactory;
		this.changedAlert = new AlertImpl();

		_nameSource = new Source<TaskName>(name);

		this.notesListeners = new ArrayList<NotesListener>();
		this.notes = new ArrayList<NoteView>();

		this.jiraIssue = null;
	}

	@Override
	public synchronized void start() {
		start(0);
	}

	@Override
	public synchronized void stop() {
		stop(0);
	}

	@Override
	public synchronized void stop(final long millisecondsAgo) {
		if (!this._active) {
			return;
		}

		final Date now = this.clock.getDate();
		final Date stopTime = new Date(now.getTime() - millisecondsAgo);
		this.activePeriod.setStop(stopTime);

		this._active = false;
		this.changedAlert.fire();
	}

	@Override
	public synchronized boolean isActive() {
		return this._active;
	}

	@Override
	public synchronized PeriodManager periodManager() {
		return this.manager;
	}

	@Override
	public synchronized void setName(final TaskName newNameForTask) {
		Validate.notNull(newNameForTask);

		this.name = newNameForTask.unbox();
		_nameSource.supply(newNameForTask);
		this.changedAlert.fire();
	}

	@Override
	public synchronized void setBudgetInHours(final Double newBudget) {
		this.budget = newBudget;
		this.changedAlert.fire();
	}

	// refactor: make private
	@Override
	public synchronized List<Period> periods() {
		return this.periodManager().periods();
	}

	@Override
	public synchronized Alert changedAlert() {
		return this.changedAlert;
	}

	public synchronized NonEmptyString nonEmptyName() {

		if (name == null) {
			return new NonEmptyString("empty name");
		}

		return new NonEmptyString(this.name);
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public synchronized Signal<TaskName> nameSignal() {
		return _nameSource;
	}

	@Override
	public synchronized Double budgetInHours() {
		return this.budget;
	}

	@Override
	public synchronized Double budgetBallanceInHours() {
		if (budgetInHours() == null) {
			return 0.0;
		}
		return budgetInHours() - totalTimeInHours();
	}

	@Override
	public synchronized String toString() {
		if (this.name == null) {
			return "null";
		}
		return this.name;
	}

	@Override
	public synchronized Double totalTimeInHours() {
		return ((double) periodManager().totalTime() / (double) DateUtils.MILLIS_PER_HOUR);
	}

	@Override
	public synchronized void addPeriod(final Period period) {
		periodManager().addPeriod(period);
	}

	@Override
	public synchronized void removePeriod(final Period period) {
		periodManager().removePeriod(period);
	}

	@Override
	public synchronized void addPeriodsListener(final PeriodsListener listener) {
		periodManager().addListener(listener);
	}

	@Override
	public synchronized long totalTimeInMillis() {
		return periodManager().totalTime();
	}

	@Override
	public synchronized void addNote(final NoteView note) {
		notes.add(note);

		for (final NotesListener listener : notesListeners) {
			listener.noteAdded(note);
		}
	}

	@Override
	public synchronized void addNotesListener(final NotesListener listener) {
		notesListeners.add(listener);
	}

	@Override
	public synchronized List<NoteView> notes() {
		return notes;
	}

	@Override
	public synchronized void removeNotesListener(final NotesListener listener) {
		notesListeners.remove(listener);

	}

	@Override
	public synchronized void removePeriodListener(final PeriodsListener listener) {
		periodManager().removeListener(listener);

	}

	@Override
	public synchronized Period getPeriod(final int index) {
		return periodManager().period(index);
	}

	@Override
	public synchronized Period lastPeriod() {
		return periods().get(lastPeriodIndex());
	}

	@Override
	public synchronized int lastPeriodIndex() {
		return periods().size() - 1;
	}

	@Override
	public synchronized Period periodAt(final int i) {
		return periods().get(i);
	}

	@Override
	public synchronized int periodsCount() {
		return periods().size();
	}

	@Override
	public synchronized void start(final long millisecondsAgo) {
		if (this._active) {
			return;
		}
		this.activePeriod = this.periodsFactory.createPeriod(new Date(
				this.clock.getDate().getTime() - millisecondsAgo));
		this.manager.addPeriod(this.activePeriod);
		this._active = true;
		this.changedAlert.fire();

	}

	@Override
	public synchronized ListSignal<Period> periodsList() {
		return periodManager().periodsList();
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
		for (int i = 0; i < periodsCount(); i++) {
			if (getPeriod(i) == period) {
				return i;
			}
		}
		return -1;
	}
}
