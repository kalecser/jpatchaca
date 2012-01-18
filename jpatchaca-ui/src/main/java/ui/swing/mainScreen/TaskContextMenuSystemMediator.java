package ui.swing.mainScreen;

import java.util.List;

import jira.issue.JiraIssue;
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


	private final LabelsUser labelsUser;
	private final LabelsSystem labelsSystem;
	private final SelectedTaskSource selectedTaskSource;

	public TaskContextMenuSystemMediator(
			final TaskContextMenu taskContextMenu, 
			final LabelsSystem labelsSystem,
			final TasksSystem tasksSystem, 
			final SelectedTaskSource selectedTaskSource, 
			final SwingTasksUser tasksUser,
			final LabelsUser labelsUser, 
			final StartTaskDelegate startTaskDelegate,
			final TaskScreenController taskScreen,
			final IssueTrackerBrowserIntegration jiraBrowser) {
		this.labelsSystem = labelsSystem;
		this.selectedTaskSource = selectedTaskSource;
		this.labelsUser = labelsUser;
		taskContextMenu.addNoteAlert().subscribe(new Subscriber() {

			@Override
			public void fire() {
				try {
					tasksSystem.addNoteToTask(selectedTaskSource.currentValue(), tasksUser.getTextForNote());
				} catch (final UserOperationCancelledException e) {
					// cancelled
				}
			}
		});

		taskContextMenu.createLabelAlert().subscribe(new Subscriber() {

			@Override
			public void fire() {
				try {
					assignSelectedTasksToNewLabel();
				} catch (final UserOperationCancelledException e) {
				}
			}
		});

		taskContextMenu.assignToLabelAlert().subscribe(new Subscriber() {

			@Override
			public void fire() {
				assignTasksToLabel();
			}
		});

		taskContextMenu.removeFromLabelAlert().subscribe(new Subscriber() {

			@Override
			public void fire() {
				labelsSystem.removeLabelFromTask(selectedTaskSource.currentValue(), labelsUser.selectedLabel());
			}
		});

		taskContextMenu.startCurrentTaskAlert().subscribe(new Subscriber() {

			@Override
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

			@Override
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

			@Override
			public void fire() {
				if (tasksUser.isTaskExclusionConfirmed()) {
					tasksSystem.removeTask(selectedTaskSource.currentValue());
				}
			}
		});

		taskContextMenu.editTaskAlert().subscribe(new Subscriber() {

			@Override
			public void fire() {

				taskScreen.editSelectedTask();

			}
		});

		taskContextMenu.setModel(new TaskContextMenuModel() {

			@Override
			public List<String> assignableLabels() {
				return labelsSystem.assignableLabels();
			}

			@Override
			public List<String> getLabelsFor(final TaskView selectedTask) {
				return labelsSystem.getLabelsFor(selectedTask);
			}

			@Override
			public void openInBrowser(TaskView task) {
				jiraBrowser.openJiraIssueOnBrowser(task);
			}
		});
	}

	@Override
	public void start() {}

	@Override
	public void stop() {}

	private void assignSelectedTasksToNewLabel() throws UserOperationCancelledException {
		String labelToAssignTaskTo =  labelsUser.getNewLabelName(null);
		labelsSystem.setNewLabelToTask(selectedTaskSource.currentValue(), labelToAssignTaskTo);
		setLabelToSelectedTasks(labelToAssignTaskTo);
	}


	private void assignTasksToLabel() {
		String labelToAssignTaskTo = labelsUser.getLabelToAssignTaskTo();
		setLabelToSelectedTasks(labelToAssignTaskTo);
	}

	private void setLabelToSelectedTasks(String labelToAssignTaskTo) {
		Object[] selectedTasks = selectedTaskSource.selectedTasks();
		for (Object task : selectedTasks){
			labelsSystem.setLabelToTask((TaskView) task, labelToAssignTaskTo);					
		}
	}

}
