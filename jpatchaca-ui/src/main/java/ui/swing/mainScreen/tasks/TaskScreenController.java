package ui.swing.mainScreen.tasks;


import javax.swing.SwingUtilities;

import jira.Jira;
import lang.Maybe;


import tasks.TaskView;
import ui.swing.presenter.Presenter;
import ui.swing.tasks.SelectedTaskSource;
import basic.Formatter;


public class TaskScreenController {

	public static final String TITLE = "Task edition";

	private final Formatter formatter;
	private final TaskScreenModel model;
	private final Presenter presenter;
	private final Jira jira;
	private final SelectedTaskSource selectedTaskSource;
	
	public TaskScreenController(final Formatter formatter,
			final TaskScreenModel model, final Presenter presenter,
			final Jira jira, SelectedTaskSource selectedTaskSource) {
		this.model = model;
		this.formatter = formatter;
		this.presenter = presenter;
		this.jira = jira;
		this.selectedTaskSource = selectedTaskSource;
	}

	public void createTaskStarted(final long time) {
		internalShow(null, Maybe.wrap(Long.valueOf(time)));
	}

	public void editSelectedTask() {
		final TaskView selectedTask = model.selectedTask();
		if (selectedTask == null) {
			internalShow(((Maybe<TaskView>) null), null);
		} else {
			internalShow(Maybe.wrap(selectedTask), null);
		}
	}

	public void createTask() {
		internalShow(null, null);
	}

	private void internalShow(final Maybe<TaskView> task,
			final Maybe<Long> start) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				showOkCancelDialog(task, start);
			}
		});
	}

	void showOkCancelDialog(final Maybe<TaskView> task, final Maybe<Long> start) {
		presenter.showOkCancelDialog(new TaskScreen(model, formatter, jira, selectedTaskSource, task, start),
				TITLE);
	}

}
