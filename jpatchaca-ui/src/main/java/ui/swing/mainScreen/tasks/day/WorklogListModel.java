package ui.swing.mainScreen.tasks.day;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import jira.JiraSystem;
import jira.JiraWorklogOverride;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.time.DateUtils;

import periods.Period;
import tasks.TaskView;
import tasks.tasks.TasksView;
import basic.AlertImpl;
import basic.Formatter;
import basic.HardwareClock;
import basic.Subscriber;

public class WorklogListModel {

	private final JiraSystem jiraSystem;
	private final JiraWorklogOverride jiraWorklogOverride;
	private final Formatter formatter;
	private final TasksView tasks;
	private final AlertImpl changeAlert;
	private int[] selectedWorklogs;
	private Calendar filter;
	private WorklogInterval filterType;

	public WorklogListModel(JiraSystem jiraSystem, Formatter formatter,
			JiraWorklogOverride jiraWorklogOverride, TasksView tasks,
			HardwareClock clock) {
		this.jiraSystem = jiraSystem;
		this.formatter = formatter;
		this.jiraWorklogOverride = jiraWorklogOverride;
		this.tasks = tasks;
		this.changeAlert = new AlertImpl();
		this.filter = Calendar.getInstance();

		setFilter(clock.getTime(), WorklogInterval.Day);
	}

	public void sendWorklog(Pair pair) {
		jiraSystem.addWorklog(pair.task(), pair.period());
	}

	public List<Pair> getWorklogList() {
		final List<Pair> lista = new ArrayList<Pair>();

		for (final TaskView task : tasks.tasks())
			for (final Period period : task.periods())
				if (filtraPeriodo(period))
					lista.add(new Pair(task, period, formatter,
							jiraWorklogOverride));

		Collections.sort(lista);
		return lista;
	}

	private boolean filtraPeriodo(final Period period) {
		
		switch (filterType){
		case Day:
			return period.getDay().equals(filter.getTime());
		case Month:
			Calendar periodDay = Calendar.getInstance();
			periodDay.setTime(period.getDay());
			periodDay.set(Calendar.DAY_OF_MONTH, 0);
			return periodDay.equals(filter);
		}

		throw new NotImplementedException();
	}

	public void setFilter(Date date, WorklogInterval interval) {
		resetFilter(date, interval);
		filterType = interval;
		changeAlert.fire();
	}

	private void resetFilter(Date date, WorklogInterval interval) {
		filter.setTime(date);
		switch (interval) {
		case Day:
			resetDay(date);
			break;
		case Month:
			resetMonth(date);
			break;
		}
	}

	private void resetMonth(Date date) {
		filter.set(Calendar.DAY_OF_MONTH, 0);
		resetDay(date);
	}

	private void resetDay(Date date) {
		filter.set(Calendar.MILLISECOND, 0);
		filter.set(Calendar.SECOND, 0);
		filter.set(Calendar.MINUTE, 0);
		filter.set(Calendar.HOUR_OF_DAY, 0);
	}

	public double getDayTotalHours() {
		double totalHours = 0;
		for (final Pair par : getWorklogList())
			totalHours += par.period().getMiliseconds();
		return totalHours / DateUtils.MILLIS_PER_HOUR;
	}

	public void addChangeSubscriber(Subscriber subscriber) {
		changeAlert.subscribe(subscriber);
	}

	public void fireChange() {
		changeAlert.fire();
	}

	public void setSelectedWorklogs(int[] selectedWorklogs) {
		this.selectedWorklogs = selectedWorklogs;
	}

	public void sendWorklog() {
		if (selectedWorklogs == null || selectedWorklogs.length == 0)
			throw new RuntimeException();

		final List<Pair> worklogs = selectedTaskWorklogs();
		if (worklogs.size() == 0)
			return;

		sendWorklog(worklogs);
	}

	private List<Pair> selectedTaskWorklogs() {
		final List<Pair> tasksPeriods = new LinkedList<Pair>();
		for (final int i : selectedWorklogs) {
			final Pair pair = getWorklogList().get(i);
			if (pair.task().getJiraIssue() != null
					&& !pair.period().isWorklogSent()) {
				tasksPeriods.add(pair);
			}
		}
		return tasksPeriods;
	}

	private void sendWorklog(final List<Pair> worklogs) {
		for (final Pair pair : worklogs) {
			jiraSystem.addWorklog(pair.task(), pair.period());
			changeAlert.fire();
		}
	}

}
