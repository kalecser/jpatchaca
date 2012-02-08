package ui.swing.mainScreen.tasks.day;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import periods.Period;
import tasks.TaskView;
import tasks.tasks.TasksView;
import basic.AlertImpl;
import basic.HardwareClock;
import basic.Subscriber;

public class DayTasksListModel {

    private final TasksView tasks;
    private final AlertImpl changeAlert;
    private int[] selectedWorklogs;
    private Calendar day;
    private final TaskWorklogFactory taskWorklogFactory;
    private List<TaskWorklog> lista;

    public DayTasksListModel(TasksView tasks, TaskWorklogFactory taskWorklogFactory,
            HardwareClock clock) {
        this.tasks = tasks;
        this.taskWorklogFactory = taskWorklogFactory;
        this.changeAlert = new AlertImpl();
        this.day = Calendar.getInstance();
        this.selectedWorklogs = new int[0];
        this.lista = new ArrayList<TaskWorklog>();
        setDay(clock.getTime());
    }

    public List<TaskWorklog> getWorklogList() {
        return lista;
    }

    void refrescate() {
        lista.clear();
        fillWorklogList();
        Collections.sort(lista);
    }

    private void fillWorklogList() {
        for (final TaskView task : tasks.tasks())
            createWorklogsForTask(task);
    }

    private void createWorklogsForTask(final TaskView task) {
        for (final Period period : task.periods())
            if (periodoDentroDoDia(period))
                lista.add(taskWorklogFactory.newTaskWorklog(task, period, this));
    }

    private boolean periodoDentroDoDia(final Period period) {
        return period.getDay().equals(day.getTime());
    }

    public void setDay(Date date) {
        day.setTime(date);
        day.set(Calendar.MILLISECOND, 0);
        day.set(Calendar.SECOND, 0);
        day.set(Calendar.MINUTE, 0);
        day.set(Calendar.HOUR_OF_DAY, 0);
        changeAlert.fire();
        refrescate();
    }

    public double getDayTotalHours() {
        double totalHours = 0;
        for (final TaskWorklog worklog : getWorklogList())
            totalHours += worklog.getMiliseconds();
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
        final List<TaskWorklog> worklogs = selectedTaskWorklogs();
        if (worklogs.size() == 0)
            return;

        sendWorklog(worklogs);
    }

    private List<TaskWorklog> selectedTaskWorklogs() {
        final List<TaskWorklog> tasksPeriods = new LinkedList<TaskWorklog>();
        for (final int i : selectedWorklogs) {
            final TaskWorklog worklog = getWorklogList().get(i);
            if (worklog.canSend())
                tasksPeriods.add(worklog);
        }
        return tasksPeriods;
    }

    private void sendWorklog(final List<TaskWorklog> worklogs) {
        for (final TaskWorklog worklog : worklogs) {
            worklog.send();
            changeAlert.fire();
        }
    }
}
