package tasks;

import java.util.List;

import org.reactive.Signal;

import periods.Period;
import periods.PeriodManager;
import periods.PeriodsListener;
import reactive.ListSignal;
import tasks.notes.NoteView;
import tasks.taskName.TaskName;
import basic.Alert;

public interface TaskView {

	String name();

	Signal<TaskName> nameSignal();

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

	ListSignal<Period> periodsList();

}
