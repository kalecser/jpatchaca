/*
 * Created on 15/04/2009
 */
package ui.swing.mainScreen;

import org.reactivebricks.pulses.Pulse;
import org.reactivebricks.pulses.Receiver;
import org.reactivebricks.pulses.Signal;
import org.reactivebricks.pulses.Source;

import tasks.TasksSystem;
import ui.swing.mainScreen.tasks.TaskScreenController;
import ui.swing.options.OptionsScreen;
import ui.swing.tasks.StartTaskController;
import ui.swing.users.SwingTasksUser;
import version.PatchacaVersion;
import events.EventsSystem;

public class MainScreenModelImpl implements MainScreenModel {

	private final SelectedTask selectedTask;
	private final EventsSystem eventsSystem;
	private final SwingTasksUser tasksUser;
	private final TasksSystem tasksSystem;

	private final OptionsScreen optionsScreen;
	private final TaskScreenController taskScreen;
	private final StartTaskController startTaskController;

	private final Source<String> title;

	public MainScreenModelImpl(final SelectedTask selectedTask,
			final EventsSystem eventsSystem, final SwingTasksUser taskUser,
			final TasksSystem tasksSystem,
			final StartTaskController startTaskController,
			final OptionsScreen optionsScreen,
			final TaskScreenController taskScreen) {
		this.selectedTask = selectedTask;
		this.eventsSystem = eventsSystem;
		this.tasksUser = taskUser;
		this.tasksSystem = tasksSystem;

		this.startTaskController = startTaskController;
		this.optionsScreen = optionsScreen;
		this.taskScreen = taskScreen;

		this.title = new Source<String>("");

		tasksSystem.activeTaskNameSignal().addReceiver(new Receiver<String>() {

			@Override
			public void receive(final Pulse<String> pulse) {
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
			tasksSystem.removeTask(selectedTask.selectedTask());
		}
	}

	@Override
	public void stopSelectedTask() {
		tasksSystem.stopTask(selectedTask.selectedTask());
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

	void updateTitle(final String activeTask) {
		if (tasksSystem.activeTask() != null) {
			setTitle(getTitleString(tasksSystem.activeTaskName()));
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
