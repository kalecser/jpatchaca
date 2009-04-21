package tasks.tasks;

import java.util.List;

import org.reactive.Signal;

import periods.Period;
import periods.PeriodManager;
import periods.PeriodsListener;
import tasks.NotesListener;
import basic.Alert;
import basic.NonEmptyString;

public interface TaskView {

	String name();

	Signal<String> nameSignal();

	boolean isActive();

	Alert changedAlert();

	Double budgetInHours();

	Double budgetBallanceInHours();

	Double totalTimeInHours();

	long totalTimeInMillis();

	// consider moving this to periodssystem
	List<Period> periods();

	Period lastPeriod();

	int lastPeriodIndex();

	Period getPeriod(int row);

	int periodsCount();

	Period periodAt(int i);

	PeriodManager periodManager();

	void addPeriodsListener(PeriodsListener listener);

	void removePeriodListener(PeriodsListener listener);

	// end here

	List<NoteView> notes();

	void addNotesListener(NotesListener listener);

	void removeNotesListener(NotesListener listener);

	NonEmptyString nonEmptyName();

}
