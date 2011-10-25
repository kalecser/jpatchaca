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

	@Override
	public Source<Maybe<TaskName>> selectedTaskName() {
		return this.selectedTaskName;
	}

	@Override
	public void destroyMainScreen() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				destroyMainScreenFromSwingThread();
			}
		});
	}

	@Override
	public void showMainScreen() {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				showMainScreenFromSwingThread();

			}
		});
	}

	@Override
	public void stopTaskIn(final long time) {

		new Thread() {

			@Override
			public void run() {

				stopIn(time);
			}
		}.start();
	}

	void stopIn(final long time) {
		tasksSystem.stopIn(time);
	}
	
	@Override
	public void setListener(final Listener listener) {
		if (this.listener != null || listener == null) {
			throw new IllegalArgumentException();
		}

		this.listener = Maybe.wrap(listener);

	}

	@Override
	public void startTask(final TaskView task, final long timeAgo) {
		new Thread() {

			@Override
			public void run() {
				taskStarted(task, timeAgo);
			}
		}.start();

	}

	@Override
	public void createTaskStarted(final long time) {
		taskScreen.createTaskStarted(time);
	}

	@Override
	public Signal<Maybe<TaskName>> activeTaskName() {
		return activeTaskName;

	}

	@Override
	public TaskView selectedTask() {
		return selectedTask.currentValue();
	}

	@Override
	public Signal<String> tooltip() {
		return new PatchacaTrayTooltip(activeTaskName(), selectedTaskName())
				.output();
	}


	@Override
	public Signal<TaskView> selectedTaskSignal() {
		return selectedTask.output();
	}

	@Override
	public boolean hasActiveTask() {
		return activeTaskName.currentValue() != null;
	}

	@Override
	public void showStartTaskScreen() {
		startTaskController.show();
	}

	@Override
	public void copyActiveTaskNameToClipboard() {
		
		if (activeTaskName.currentValue() == null){
			presenter.showMessageBalloon("You can only copy active task name if there is an active task.");
			return;
		}
		
		TaskName activeTaskNameNotNull = activeTaskName.currentValue().unbox();
		StringSelection selection = new StringSelection(activeTaskNameNotNull.unbox());
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
	}

	void destroyMainScreenFromSwingThread() {
		mainScreen.hide();
		mainScreen.setVisible(false);
		mainScreen.dispose();
	}

	void showMainScreenFromSwingThread() {
		windowManager.setMainWindow(mainScreen.getWindow());
		mainScreen.setVisible(true);
		mainScreen.toFront();

		if (mainScreen.getExtendedState() == Frame.MAXIMIZED_BOTH) {
			return;
		}

		final int state = Frame.NORMAL;
		mainScreen.setExtendedState(state);
	}

	void taskStarted(final TaskView task, final long timeAgo) {
		tasksSystem.taskStarted(task, timeAgo);
	}

}
