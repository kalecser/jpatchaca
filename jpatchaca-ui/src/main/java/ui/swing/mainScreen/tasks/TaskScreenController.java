package ui.swing.mainScreen.tasks;


import javax.swing.SwingUtilities;

import jira.service.Jira;
import lang.Maybe;
import tasks.TaskView;
import ui.swing.presenter.Presenter;
import basic.Formatter;


public class TaskScreenController {

	public static final String TITLE = "Task edition";

	private final Formatter formatter;
	private final TaskScreenModel model;
	private final Presenter presenter;
	private final Jira jira;
	
	public TaskScreenController(final Formatter formatter,
			final TaskScreenModel model, final Presenter presenter,
			final Jira jira) {
		this.model = model;
		this.formatter = formatter;
		this.presenter = presenter;
		this.jira = jira;
	}

	public void editSelectedTask() {
		final TaskView selectedTask = model.selectedTask();
		if (selectedTask == null) {
			internalShow(((Maybe<TaskView>) null));
		} else {
			internalShow(Maybe.wrap(selectedTask));
		}
	}

	public void createTask() {
		internalShow(null);
	}

	private void internalShow(final Maybe<TaskView> task) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				showOkCancelDialog(task);
			}
		});
	}

	void showOkCancelDialog(final Maybe<TaskView> task) {
		presenter.showOkCancelDialog(new TaskScreen(model, formatter, jira, task),
				TITLE);
	}

}
