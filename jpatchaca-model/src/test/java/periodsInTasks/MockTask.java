package periodsInTasks;

import java.util.List;

import org.reactivebricks.pulses.Signal;

import periods.Period;
import periods.PeriodManager;
import periods.PeriodsListener;
import basic.Alert;
import tasks.NotesListener;
import tasks.tasks.NoteView;
import tasks.tasks.TaskView;

public class MockTask implements TaskView {

	
	@Override
	public void addNotesListener(NotesListener listener) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void addPeriodsListener(PeriodsListener listener) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Double budgetBallanceInHours() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Double budgetInHours() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Alert changedAlert() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Period getPeriod(int row) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public boolean isActive() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Period lastPeriod() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public int lastPeriodIndex() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public String name() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Signal<String> nameSignal() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public List<NoteView> notes() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Period periodAt(int i) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public PeriodManager periodManager() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public List<Period> periods() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public int periodsCount() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void removeNotesListener(NotesListener listener) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void removePeriodListener(PeriodsListener listener) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Double totalTimeInHours() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public long totalTimeInMillis() {
		throw new RuntimeException("not implemented");
	}

}
