package periodsInTasks;

import java.util.List;

import periods.Period;
import tasks.tasks.TaskView;
import core.ObjectIdentity;
import events.persistence.MustBeCalledInsideATransaction;

public interface PeriodsInTasksHome {

	void addPeriodToTask(ObjectIdentity taskId, Period period) throws MustBeCalledInsideATransaction;
	void removePeriodFromTask(TaskView taskId, Period period) throws MustBeCalledInsideATransaction;

	List<MonthYear> monthYears();


}
