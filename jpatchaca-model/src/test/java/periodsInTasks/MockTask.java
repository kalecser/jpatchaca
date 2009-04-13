package periodsInTasks;

import java.util.List;

import org.reactivebricks.pulses.Signal;

import periods.Period;
import periods.PeriodManager;
import periods.PeriodsListener;
import tasks.NotesListener;
import tasks.tasks.NoteView;
import basic.Alert;

public class MockTask implements tasks.tasks.Task{

	private final String name;

	public MockTask(){
		name = null;
	}
			
	public MockTask(String string) {
		this.name = string;
	}

	@Override
	public void addNote(NoteView note) {
		//  Auto-generated method stub
		
	}

	@Override
	public void addNotesListener(NotesListener listener) {
		//  Auto-generated method stub
		
	}

	@Override
	public void addPeriod(Period period) {
		//  Auto-generated method stub
		
	}

	@Override
	public void addPeriodsListener(PeriodsListener listener) {
		//  Auto-generated method stub
		
	}

	@Override
	public Double budgetBallanceInHours() {
		//  Auto-generated method stub
		return null;
	}

	@Override
	public Double budgetInHours() {
		//  Auto-generated method stub
		return null;
	}

	@Override
	public Alert changedAlert() {
		//  Auto-generated method stub
		return null;
	}

	@Override
	public Period getPeriod(int index) {
		//  Auto-generated method stub
		return null;
	}

	@Override
	public boolean isActive() {
		//  Auto-generated method stub
		return false;
	}

	@Override
	public Period lastPeriod() {
		//  Auto-generated method stub
		return null;
	}

	@Override
	public int lastPeriodIndex() {
		//  Auto-generated method stub
		return 0;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public Signal<String> nameSignal() {
		//  Auto-generated method stub
		return null;
	}

	@Override
	public List<NoteView> notes() {
		//  Auto-generated method stub
		return null;
	}

	@Override
	public Period periodAt(int i) {
		//  Auto-generated method stub
		return null;
	}

	@Override
	public PeriodManager periodManager() {
		//  Auto-generated method stub
		return null;
	}

	@Override
	public List<Period> periods() {
		//  Auto-generated method stub
		return null;
	}

	@Override
	public int periodsCount() {
		//  Auto-generated method stub
		return 0;
	}

	@Override
	public void removeNotesListener(NotesListener listener) {
		//  Auto-generated method stub
		
	}

	@Override
	public void removePeriod(Period period) {
		//  Auto-generated method stub
		
	}

	@Override
	public void removePeriodListener(PeriodsListener listener) {
		//  Auto-generated method stub
		
	}

	@Override
	public void setBudgetInHours(Double newBudget) {
		//  Auto-generated method stub
		
	}

	@Override
	public void setName(String newNameForTask) {
		//  Auto-generated method stub
		
	}

	@Override
	public void start() {
		//  Auto-generated method stub
		
	}

	@Override
	public void stop() {
		//  Auto-generated method stub
		
	}

	@Override
	public Double totalTimeInHours() {
		//  Auto-generated method stub
		return null;
	}

	@Override
	public long totalTimeInMillis() {
		//  Auto-generated method stub
		return 0;
	}

}