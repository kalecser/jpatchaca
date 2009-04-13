package tasks.tasks;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.reactivebricks.pulses.Signal;
import org.reactivebricks.pulses.Source;

import periods.Period;
import periods.PeriodManager;
import periods.PeriodsFactory;
import periods.PeriodsListener;
import tasks.NotesListener;
import basic.Alert;
import basic.AlertImpl;
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
	private Source<String> _nameSource;
	
	
	public TaskImpl(String name, SystemClock clock, Double budget, PeriodManager manager, PeriodsFactory periodsFactory) {
		this.name = name;
		this.clock = clock;
		this.budget = budget;
		this.manager = manager;
		this.periodsFactory = periodsFactory;
		this.changedAlert = new AlertImpl();
		
		
		_nameSource = new Source<String>((name == null? "": name));
		
		this.notesListeners = new ArrayList<NotesListener>();
		this.notes = new ArrayList<NoteView>();
	}

	public synchronized void start() {
		if (this._active) return;
		this.activePeriod = this.periodsFactory.createPeriod(this.clock.getDate());
		this.manager.addPeriod(this.activePeriod);
		this._active  = true;
		this.changedAlert.fire();
	}
	
	public synchronized void stop() {
		if (!this._active) return;
		this.activePeriod.setStop(this.clock.getDate());
		this._active  = false;
		this.changedAlert.fire();
	}

	public synchronized boolean isActive() {
		return this._active;
	}

	public synchronized PeriodManager periodManager() {
		return this.manager;
	}

	public synchronized void setName(String newNameForTask) {
		this.name = newNameForTask;	
		_nameSource.supply(newNameForTask);
		this.changedAlert.fire();
	}
	
	public synchronized void setBudgetInHours(Double newBudget) {
		this.budget = newBudget;	
		this.changedAlert.fire();
	}


	//refactor: make private
	public synchronized List<Period> periods() {
		return this.periodManager().periods();
	}

	public synchronized Alert changedAlert() {
		return this.changedAlert;
	}

	public synchronized String name() {
		return this.name;
	}
	
	@Override
	public synchronized Signal<String> nameSignal() {
		return _nameSource;
	}

	public synchronized Double budgetInHours() {
		return this.budget;
	}

	public synchronized Double budgetBallanceInHours() {
		if (budgetInHours() == null) return 0.0;
		return budgetInHours() - totalTimeInHours();
	}
	
	@Override
	public synchronized String toString() {
		if (this.name == null) return "null";
		return this.name;
	}

	public synchronized Double totalTimeInHours() {
		return ((double) periodManager().totalTime() / (double) DateUtils.MILLIS_PER_HOUR);
	}

	public synchronized void addPeriod(Period period) {
		periodManager().addPeriod(period);		
	}

	public synchronized void removePeriod(Period period) {
		periodManager().removePeriod(period);		
	}

	public synchronized void addPeriodsListener(PeriodsListener listener) {
		periodManager().addListener(listener);
	}

	public synchronized long totalTimeInMillis() {
		return periodManager().totalTime();
	}

	public synchronized void addNote(NoteView note) {
		notes.add(note);
		
		for (final NotesListener listener : notesListeners){
			listener.noteAdded(note);
		}
	}
	
	public synchronized void addNotesListener(NotesListener listener) {
		notesListeners.add(listener);		
	}

	public synchronized List<NoteView> notes() {
		return notes;
	}

	public synchronized void removeNotesListener(NotesListener listener) {
		notesListeners.remove(listener);
		
	}

	@Override
	public synchronized void removePeriodListener(PeriodsListener listener) {
		periodManager().removeListener(listener);
		
	}

	@Override
	public synchronized Period getPeriod(int index) {
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
	public synchronized Period periodAt(int i) {
		return periods().get(i);
	}

	@Override
	public synchronized int periodsCount() {
		return periods().size();
	}
	

}
