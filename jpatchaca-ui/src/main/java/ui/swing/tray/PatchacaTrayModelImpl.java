package ui.swing.tray;

import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

import javax.swing.SwingUtilities;

import lang.Maybe;

import org.reactive.Signal;
import org.reactive.Source;

import tasks.TaskView;
import tasks.TasksSystem;
import tasks.taskName.ActiveTaskName;
import tasks.taskName.TaskName;
import ui.swing.mainScreen.MainScreen;
import ui.swing.mainScreen.SelectedTaskName;
import ui.swing.mainScreen.tasks.TaskScreenController;
import ui.swing.mainScreen.tasks.WindowManager;
import ui.swing.presenter.Presenter;
import ui.swing.tasks.SelectedTaskSource;
import ui.swing.tasks.StartTaskController;

public class PatchacaTrayModelImpl implements PatchacaTrayModel {

	public interface Listener {

		void lastActiveTasksChanged();
	}

	private final MainScreen mainScreen;
	private final TasksSystem tasksSystem;
	private final SelectedTaskSource selectedTask;
	private Maybe<Listener> listener;
	private final WindowManager windowManager;
	private final TaskScreenController taskScreen;
	private final StartTaskController startTaskController;
	private final SelectedTaskName selectedTaskName;
	private final ActiveTaskName activeTaskName;
	private final Presenter presenter;

	public PatchacaTrayModelImpl(final MainScreen mainScreen,
			final TasksSystem tasksSystem,
			final SelectedTaskName selectedTaskName,
			final SelectedTaskSource selectedTask,
			final TaskScreenController taskScreen,
			final WindowManager windowManager,
			final StartTaskController startTaskController,
			final ActiveTaskName activeTaskName, 
			final Presenter presenter) {

		this.mainScreen = mainScreen;
		this.tasksSystem = tasksSystem;
		this.selectedTaskName = selectedTaskName;
		this.selectedTask = selectedTask;
		this.taskScreen = taskScreen;
		this.windowManager = windowManager;
		this.startTaskController = startTaskController;
		this.activeTaskName = activeTaskName;
		this.presenter = presenter;

	}

	public Source<Maybe<TaskName>> selectedTaskName() {
		return this.selectedTaskName;
	}

	public void destroyMainScreen() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				mainScreen.hide();
				mainScreen.setVisible(false);
				mainScreen.dispose();
			}
		});
	}

	public void showMainScreen() {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				windowManager.setMainWindow(mainScreen.getWindow());
				mainScreen.setVisible(true);
				mainScreen.toFront();

				if (mainScreen.getExtendedState() == Frame.MAXIMIZED_BOTH) {
					return;
				}

				final int state = Frame.NORMAL;
				mainScreen.setExtendedState(state);

			}
		});
	}

	public void stopTaskIn(final long time) {

		new Thread() {

			@Override
			public void run() {

				tasksSystem.stopIn(time);
			}
		}.start();
	}

	public void setListener(final Listener listener) {
		if (this.listener != null || listener == null) {
			throw new IllegalArgumentException();
		}

		this.listener = Maybe.wrap(listener);

	}

	public void startTask(final TaskView task, final long timeAgo) {
		new Thread() {

			@Override
			public void run() {
				tasksSystem.taskStarted(task, timeAgo);
			}
		}.start();

	}

	public void createTaskStarted(final long time) {
		taskScreen.createTaskStarted(time);
	}

	public Signal<Maybe<TaskName>> activeTaskName() {
		return activeTaskName;

	}

	public TaskView selectedTask() {
		return selectedTask.currentValue();
	}

	public Signal<String> tooltip() {
		return new PatchacaTrayTooltip(activeTaskName(), selectedTaskName())
				.output();
	}


	public Signal<TaskView> selectedTaskSignal() {
		return selectedTask.output();
	}

	public boolean hasActiveTask() {
		return activeTaskName.currentValue() != null;
	}

	@Override
	public void showStartTaskScreen() {
		startTaskController.show();
	}

	@Override
	public void copyActiveTaskName() {
		
		if (activeTaskName.currentValue() == null){
			presenter.showMessageBalloon("You can only copy active task name if there is an active task.");
			return;
		}
		
		TaskName activeTaskNameNotNull = activeTaskName.currentValue().unbox();
		StringSelection selection = new StringSelection(activeTaskNameNotNull.unbox());
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
	}

}
