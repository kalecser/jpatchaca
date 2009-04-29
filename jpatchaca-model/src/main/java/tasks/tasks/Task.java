package tasks.tasks;

import periods.Period;
import periods.PeriodsListener;
import tasks.NotesListener;
import tasks.tasks.taskName.TaskName;

public interface Task extends TaskView {

	public abstract void start();

	public abstract void stop();

	public abstract void setName(TaskName newNameForTask);

	public abstract void setBudgetInHours(Double newBudget);

	public abstract void addPeriod(Period period);

	public abstract void removePeriod(Period period);

	public abstract void addPeriodsListener(PeriodsListener listener);

	public abstract void addNote(NoteView note);

	public abstract void addNotesListener(NotesListener listener);

	public abstract void removeNotesListener(NotesListener listener);

	public abstract void removePeriodListener(PeriodsListener listener);

	public abstract void start(long millisecondsAgo);

}