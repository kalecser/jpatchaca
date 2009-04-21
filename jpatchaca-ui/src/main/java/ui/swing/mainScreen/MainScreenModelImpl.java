/*
 * Created on 15/04/2009
 */
package ui.swing.mainScreen;

import org.reactivebricks.commons.lang.Maybe;
import org.reactivebricks.pulses.Pulse;
import org.reactivebricks.pulses.Receiver;
import org.reactivebricks.pulses.Signal;
import org.reactivebricks.pulses.Source;

import tasks.ActiveTaskName;
import tasks.TasksSystem;
import ui.swing.mainScreen.tasks.TaskScreenController;
import ui.swing.options.OptionsScreen;
import ui.swing.tasks.SelectedTaskSource;
import ui.swing.tasks.StartTaskController;
import ui.swing.users.SwingTasksUser;
import version.PatchacaVersion;
import events.EventsSystem;

public class MainScreenModelImpl implements MainScreenModel {

	private final SelectedTaskSource selectedTask;
	private final EventsSystem eventsSystem;
	private final SwingTasksUser tasksUser;

	private final OptionsScreen optionsScreen;
	private final TaskScreenController taskScreen;
	private final StartTaskController startTaskController;

	private final Source<String> title;
	private final ActiveTaskName activeTaskName;
	private final TasksSystem tasksSystem;

	public MainScreenModelImpl(final SelectedTaskSource selectedTask,
			final EventsSystem eventsSystem, final SwingTasksUser taskUser,
			final TasksSystem tasksSystem,
			final StartTaskController startTaskController,
			final OptionsScreen optionsScreen,
			final TaskScreenController taskScreen,
			final ActiveTaskName activeTaskName) {
		this.selectedTask = selectedTask;
		this.eventsSystem = eventsSystem;
		this.tasksUser = taskUser;
		this.tasksSystem = tasksSystem;

		this.startTaskController = startTaskController;
		this.optionsScreen = optionsScreen;
		this.taskScreen = taskScreen;
		this.activeTaskName = activeTaskName;

		this.title = new Source<String>("");

		this.activeTaskName.addReceiver(new Receiver<Maybe<String>>() {

			@Override
			public void receive(final Pulse<Maybe<String>> pulse) {
				updateTitle(pulse.value());
			}
		});
	}

	@Override
	public Signal<String> titleSignal() {
		return title;
	}

	@Override
	public void removeSelectedTask() {
		if (tasksUser.isTaskExclusionConfirmed()) {
			tasksSystem.removeTask(selectedTask.currentValue());
		}
	}

	@Override
	public void stopSelectedTask() {
		tasksSystem.stopTask();
	}

	@Override
	public void editSelectedTask() {
		taskScreen.editSelectedTask();
	}

	@Override
	public void showCreateTaskScreen() {
		taskScreen.createTask();
	}

	@Override
	public void showStartTaskScreen() {
		startTaskController.show();
	}

	@Override
	public void showOptionsScreen() {
		optionsScreen.show();
	}

	void updateTitle(final Maybe<String> taskName) {
		if (taskName != null) {
			setTitle(getTitleString(taskName.unbox()));
		} else {
			setTitle(getTitleString());
		}
	}

	private void setTitle(final String titleString) {
		this.title.supply(titleString);
	}

	private String getTitleString() {
		return "Patchaca tracker 2, version: " + PatchacaVersion.getVersion()
				+ ", events: " + eventsSystem.getEventCount();
	}

	private String getTitleString(final String activeTaskName) {
		return getTitleString() + ", Active task: " + activeTaskName;
	}

}
