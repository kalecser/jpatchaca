package tasks.tasks;

import java.util.List;

import org.reactive.Signal;

import periods.Period;
import periods.PeriodManager;
import periods.PeriodsListener;
import tasks.NotesListener;
import basic.Alert;

public interface Task extends TaskView {

	public abstract void start();

	public abstract void stop();

	public abstract boolean isActive();

	public abstract PeriodManager periodManager();

	public abstract void setName(String newNameForTask);

	public abstract void setBudgetInHours(Double newBudget);

	public abstract List<Period> periods();

	public abstract Alert changedAlert();

	public abstract Signal<String> nameSignal();

	public abstract Double budgetInHours();

	public abstract Double budgetBallanceInHours();

	public abstract String toString();

	public abstract Double totalTimeInHours();

	public abstract void addPeriod(Period period);

	public abstract void removePeriod(Period period);

	public abstract void addPeriodsListener(PeriodsListener listener);

	public abstract long totalTimeInMillis();

	public abstract void addNote(NoteView note);

	public abstract void addNotesListener(NotesListener listener);

	public abstract List<NoteView> notes();

	public abstract void removeNotesListener(NotesListener listener);

	public abstract void removePeriodListener(PeriodsListener listener);

	public abstract Period getPeriod(int index);

	public abstract Period lastPeriod();

	public abstract int lastPeriodIndex();

	public abstract Period periodAt(int i);

	public abstract int periodsCount();

	public abstract void start(long millisecondsAgo);

}