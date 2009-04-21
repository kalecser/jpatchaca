package periodsInTasks.impl;

import java.util.Calendar;
import java.util.Date;

import org.picocontainer.Startable;

import periods.Period;
import periods.PeriodsFactory;
import periodsInTasks.PeriodsInTasksHome;
import periodsInTasks.PeriodsInTasksSystem;
import periodsInTasks.processors.AddPeriodProcessor;
import tasks.TasksSystem;
import tasks.processors.RemovePeriodProcessor;
import tasks.tasks.TaskView;
import tasks.tasks.Tasks;
import core.ObjectIdentity;
import events.AddPeriodEvent;
import events.EventsSystem;
import events.RemovePeriodEvent;

public class PeriodsInTasksSystemImpl implements PeriodsInTasksSystem,
		Startable {

	private final EventsSystem eventsSystem;
	private final TasksSystem taskSystem;
	private final PeriodsInTasksHomeImpl periodsInTasksHome;
	private final Tasks tasks;

	public PeriodsInTasksSystemImpl(final EventsSystem eventsSystem,
			final TasksSystem taskSystem, final PeriodsFactory periodsFactory,
			final Tasks tasks) {
		this.eventsSystem = eventsSystem;
		this.taskSystem = taskSystem;
		this.tasks = tasks;

		periodsInTasksHome = new PeriodsInTasksHomeImpl(taskSystem);
		eventsSystem.addProcessor(new AddPeriodProcessor(periodsInTasksHome,
				periodsFactory));
		eventsSystem.addProcessor(new RemovePeriodProcessor(periodsInTasksHome,
				tasks));

	}

	@Override
	public void addPeriod(final TaskView task, final Period period) {
		final ObjectIdentity idOfTask = idOfTask(task);
		final AddPeriodEvent addPeriod = new AddPeriodEvent(idOfTask, period
				.startTime(), period.endTime());
		eventsSystem.writeEvent(addPeriod);
	}

	@Override
	public void editPeriodDay(final TaskView task, final int i, final Date date) {
		final Period period = task.getPeriod(i);
		final Date endTime = period.endTime();

		final Date adjustedStartTime = copyDayMonthYear(period.startTime(),
				date);

		if (endTime == null) {
			taskSystem.setPeriodStarting(task, i, adjustedStartTime);
			return;
		}

		final Date adjustedEndTime = copyDayMonthYear(endTime, date);
		taskSystem.setPeriod(task, i, adjustedStartTime, adjustedEndTime);

	}

	private Date copyDayMonthYear(final Date datea, final Date dateb) {
		final Calendar calendara = Calendar.getInstance();
		calendara.setTime(datea);

		final Calendar calendarb = Calendar.getInstance();
		calendarb.setTime(dateb);

		final Calendar calendarc = Calendar.getInstance();
		calendarc.set(Calendar.YEAR, calendarb.get(Calendar.YEAR));
		calendarc
				.set(Calendar.DAY_OF_YEAR, calendarb.get(Calendar.DAY_OF_YEAR));
		calendarc
				.set(Calendar.HOUR_OF_DAY, calendara.get(Calendar.HOUR_OF_DAY));
		calendarc.set(Calendar.MINUTE, calendara.get(Calendar.MINUTE));
		calendarc.set(Calendar.SECOND, calendara.get(Calendar.SECOND));

		final Date time = calendarc.getTime();
		return time;
	}

	private ObjectIdentity idOfTask(final TaskView task) {
		return tasks.idOf(task);
	}

	@Override
	public void start() {
	}

	@Override
	public void stop() {
	}

	@Override
	public PeriodsInTasksHome getHome() {
		return periodsInTasksHome;
	}

	@Override
	public void removePeriod(final TaskView task, final Period period) {
		final ObjectIdentity idOfTask = idOfTask(task);
		final int indexOfPeriod = task.periods().indexOf(period);

		eventsSystem.writeEvent(new RemovePeriodEvent(idOfTask, indexOfPeriod));

	}

}
