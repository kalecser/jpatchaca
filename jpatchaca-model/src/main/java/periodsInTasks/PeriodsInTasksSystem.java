package periodsInTasks;

import java.util.Date;

import periods.Period;
import tasks.TaskView;

public interface PeriodsInTasksSystem {

	public void addPeriod(TaskView task, Period period);
	public void editPeriodDay(TaskView task, int i, Date date);
	public PeriodsInTasksHome getHome();
	public void removePeriod(TaskView taskByName, Period period);
	
}
