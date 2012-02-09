package ui.swing.mainScreen.tasks.day;

import jira.exception.JiraNotAvailable;

public class WorklogStatusLoader {

    private final TaskWorklog worklog;
    private String status;
    private boolean running;
    private final WorklogListModel dayTasksListModel;

    WorklogStatusLoader(TaskWorklog worklog, WorklogListModel dayTasksListModel) {
        this.worklog = worklog;
        this.dayTasksListModel = dayTasksListModel;
    }

    @Override
    public String toString() {
        return loadStatus();
    }

    private String loadStatus() {
        if (status != null)
            return status;
        if (!running)
            fireStatusLoaderThread();
        return "loading...";
    }

    private void fireStatusLoaderThread() {
        running = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                loadStatusAndFireChange();
            }
        }).start();
    }

    private void loadStatusAndFireChange() {
        try {
            status = worklog.getStatus();
        } catch (JiraNotAvailable e) {
            status = e.getMessage();
        } catch (Exception e) {
            status = "Error loading status";
        }
        dayTasksListModel.fireChange();
    }

}
