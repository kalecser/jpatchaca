package ui.swing.tray;

import java.awt.Frame;
import java.util.Collection;

import javax.swing.SwingUtilities;

import org.reactivebricks.commons.lang.Maybe;
import org.reactivebricks.pulses.Signal;
import org.reactivebricks.pulses.Source;

import tasks.TasksSystem;
import tasks.tasks.TaskView;
import ui.swing.mainScreen.MainScreen;
import ui.swing.mainScreen.SelectedTaskName;
import ui.swing.mainScreen.tasks.TaskScreenController;
import ui.swing.mainScreen.tasks.WindowManager;
import ui.swing.tasks.StartTaskController;
import ui.swing.users.SwingTasksUser;
import basic.Subscriber;

public class PatchacaTrayModelImpl implements PatchacaTrayModel {

	public interface Listener {

		void lastActiveTasksChanged();
	}

	private final MainScreen mainScreen;
	private final TasksSystem tasksSystem;
	private final SwingTasksUser taskUser;
	private Maybe<Listener> listener;
	private final WindowManager windowManager;
	private final TaskScreenController taskScreen;
	private final StartTaskController startTaskController;
	private final SelectedTaskName selectedTaskName;

	public PatchacaTrayModelImpl(final MainScreen mainScreen,
			final TasksSystem tasksSystem,
			final SelectedTaskName selectedTaskName,
			final SwingTasksUser taskUser,
			final TaskScreenController taskScreen,
			final WindowManager windowManager,
			final StartTaskController startTaskController) {

		this.mainScreen = mainScreen;
		this.tasksSystem = tasksSystem;
		this.selectedTaskName = selectedTaskName;
		this.taskUser = taskUser;
		this.taskScreen = taskScreen;
		this.windowManager = windowManager;
		this.startTaskController = startTaskController;

		bindToTasksSystem(tasksSystem);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ui.swing.tray.PatchacaTrayModel#selectedTaskName()
	 */
	public Source<String> selectedTaskName() {
		return this.selectedTaskName;
	}

	private void bindToTasksSystem(final TasksSystem tasksSystem) {
		tasksSystem.lastActiveTasksAlert().subscribe(new Subscriber() {

			public void fire() {
				if (listener != null) {
					listener.unbox().lastActiveTasksChanged();
				}
			}
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ui.swing.tray.PatchacaTrayModel#destroyMainScreen()
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see ui.swing.tray.PatchacaTrayModel#showMainScreen()
	 */
	public void showMainScreen() {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				windowManager.setMainWindow(mainScreen.getWindow());
				mainScreen.setVisible(true);
				mainScreen.toFront();
				final int state = Frame.NORMAL;
				mainScreen.setExtendedState(state);

			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ui.swing.tray.PatchacaTrayModel#stopTaskIn(long)
	 */
	public void stopTaskIn(final long time) {

		new Thread() {

			@Override
			public void run() {

				tasksSystem.stopIn(time);
			}
		}.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeui.swing.tray.PatchacaTrayModel#setListener(ui.swing.tray.
	 * PatchacaTrayModelImpl.Listener)
	 */
	public void setListener(final Listener listener) {
		if (this.listener != null || listener == null) {
			throw new IllegalArgumentException();
		}

		this.listener = Maybe.wrap(listener);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ui.swing.tray.PatchacaTrayModel#startTaskIn(tasks.tasks.TaskView,
	 * long)
	 */
	public void startTaskIn(final TaskView task, final long timeAgo) {
		new Thread() {

			@Override
			public void run() {
				tasksSystem.taskStarted(task, timeAgo);
			}
		}.start();

	}

	public Collection<TaskView> lastActiveTasks() {
		return tasksSystem.lastActiveTasks();
	}

	public void createTaskStarted(final long time) {
		taskScreen.createTaskStarted(time);
	}

	public Signal<String> activeTaskName() {
		return tasksSystem.activeTaskNameSignal();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ui.swing.tray.PatchacaTrayModel#selectedTask()
	 */
	public TaskView selectedTask() {

		return taskUser.selectedTask();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ui.swing.tray.PatchacaTrayModel#tooltip()
	 */
	public Signal<String> tooltip() {
		return new PatchacaTrayTooltip(activeTaskName(), selectedTaskName())
				.output();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ui.swing.tray.PatchacaTrayModel#selectedTaskSignal()
	 */
	public Signal<TaskView> selectedTaskSignal() {
		return taskUser.selectedTaskSignal();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ui.swing.tray.PatchacaTrayModel#hasActiveTask()
	 */
	public boolean hasActiveTask() {
		return tasksSystem.activeTask() != null;
	}

	@Override
	public void showStartTaskScreen() {
		startTaskController.show();
	}

}
