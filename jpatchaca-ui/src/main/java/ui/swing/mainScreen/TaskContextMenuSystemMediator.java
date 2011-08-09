package ui.swing.mainScreen;

import java.util.List;

import jira.JiraIssue;

import labels.LabelsSystem;

import org.picocontainer.Startable;

import tasks.TaskView;
import tasks.TasksSystem;
import tasks.delegates.StartTaskData;
import tasks.delegates.StartTaskDelegate;
import tasks.home.TaskData;
import ui.swing.mainScreen.tasks.TaskScreenController;
import ui.swing.tasks.SelectedTaskSource;
import ui.swing.users.LabelsUser;
import ui.swing.users.SwingTasksUser;
import basic.NonEmptyString;
import basic.Subscriber;
import basic.UserOperationCancelledException;

public class TaskContextMenuSystemMediator implements Startable {

	public TaskContextMenuSystemMediator(final TaskContextMenu taskContextMenu, final LabelsSystem labelsSystem,
			final TasksSystem tasksSystem, final SelectedTaskSource selectedTaskSource, final SwingTasksUser tasksUser,
			final LabelsUser labelsUser, final StartTaskDelegate startTaskDelegate,
			final TaskScreenController taskScreen) {
		taskContextMenu.addNoteAlert().subscribe(new Subscriber() {

			public void fire() {
				try {
					tasksSystem.addNoteToTask(selectedTaskSource.currentValue(), tasksUser.getTextForNote());
				} catch (final UserOperationCancelledException e) {
					// cancelled
				}
			}
		});

		taskContextMenu.createLabelAlert().subscribe(new Subscriber() {

			public void fire() {
				try {
					labelsSystem.setNewLabelToTask(selectedTaskSource.currentValue(), labelsUser.getNewLabelName(null));
				} catch (final UserOperationCancelledException e) {
				}
			}
		});

		taskContextMenu.assignToLabelAlert().subscribe(new Subscriber() {

			public void fire() {
				labelsSystem.setLabelToTask(selectedTaskSource.currentValue(), labelsUser.getLabelToAssignTaskTo());
			}
		});

		taskContextMenu.removeFromLabelAlert().subscribe(new Subscriber() {

			public void fire() {
				labelsSystem.removeLabelFromTask(selectedTaskSource.currentValue(), labelsUser.selectedLabel());
			}
		});

		taskContextMenu.startCurrentTaskAlert().subscribe(new Subscriber() {

			public void fire() {
				new Thread() {

					@Override
					public void run() {
						final TaskView selectedTask = selectedTaskSource.currentValue();

						if (selectedTask == null) {
							return;
						}

						final String name = selectedTask.name();
						final JiraIssue jiraIssue = selectedTask.getJiraIssue() != null ? selectedTask.getJiraIssue()
								.unbox() : null;

						if (name.isEmpty()) {
							return;
						}

						startTaskDelegate.starTask(new StartTaskData(new TaskData(new NonEmptyString(name), jiraIssue),
								0));
					}
				}.start();
			}
		});

		taskContextMenu.stopCurrentTaskAlert().subscribe(new Subscriber() {

			public void fire() {
				new Thread() {

					@Override
					public void run() {
						tasksSystem.stopTask();
					}
				}.start();
			}
		});

		taskContextMenu.removeTaskAlert().subscribe(new Subscriber() {

			public void fire() {
				if (tasksUser.isTaskExclusionConfirmed()) {
					tasksSystem.removeTask(selectedTaskSource.currentValue());
				}
			}
		});

		taskContextMenu.editTaskAlert().subscribe(new Subscriber() {

			public void fire() {

				taskScreen.editSelectedTask();

			}
		});

		taskContextMenu.setModel(new TaskContextMenuModel() {

			public List<String> assignableLabels() {
				return labelsSystem.assignableLabels();
			}

			public List<String> getLabelsFor(final TaskView selectedTask) {
				return labelsSystem.getLabelsFor(selectedTask);
			}
		});
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}
