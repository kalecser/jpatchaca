/**
 * 
 */
package tasks.adapters.ui;

import tasks.PatchacaTasksOperator;
import ui.swing.tray.tests.environment.PathcacaTrayOperator;
import basic.mock.MockHardwareClock;

public final class PatchacaTasksOperatorUsingUI implements
		PatchacaTasksOperator {

	private final MainScreenOperator mainScreen;
	private final MockHardwareClock mockHardwareClock;
	private final PathcacaTrayOperator patchacaTray;

	public PatchacaTasksOperatorUsingUI(
			final MockHardwareClock mockHardwareClock) {

		this.mockHardwareClock = mockHardwareClock;
		mainScreen = new MainScreenOperator();
		patchacaTray = new PathcacaTrayOperator();
	}

	@Override
	public String allLabelName() {
		selectDefaultLabel();
		return mainScreen.getSelectedLabel();
	}

	private void selectDefaultLabel() {
		mainScreen.selectLabel(0);
	}

	@Override
	public void createTask(final String taskName) {
		mainScreen.createTask(taskName);
	}

	@Override
	public void createTaskAndAssignToLabel(final String taskName,
			final String labelName) {
		createTask(taskName);
		mainScreen.assignTaskToLabel(taskName, labelName);
	}

	@Override
	public boolean isTaskInLabel(final String taskName, final String labelName) {
		mainScreen.selectLabel(labelName);
		return mainScreen.isTaskVisible(taskName);
	}

	@Override
	public void ediTask(final String taskName, final String taskNewName) {
		selectDefaultLabel();

		mainScreen.selectTask(taskName);
		mainScreen.pushEditTaskMenu();

		final TaskScreenOperator taskScreen = new TaskScreenOperator();
		taskScreen.setTaskName(taskNewName);
		taskScreen.clickOk();
	}

	@Override
	public void setTime(final int time) {
		mockHardwareClock.setTime(time);

	}

	@Override
	public void advanceTimeBy(final long millis) {
		mockHardwareClock.advanceTimeBy(millis);
	}

	@Override
	public void startTask(final String taskName) {
		mainScreen.startTask(taskName);
		assertActiveTask(taskName);
	}

	@Override
	public void stopTask() {
		mainScreen.stopTask();
	}

	public void selectTask(final String taskName) {
		mainScreen.selectTask(taskName);
	}

	@Override
	public void startNewTaskNow(final String taskName) {

		patchacaTray.startNewTaskNow(taskName);

		final TaskScreenOperator taskScreen = new TaskScreenOperator();
		taskScreen.setTaskName(taskName);
		taskScreen.clickOk();

	}

	@Override
	public void startTaskHalfAnHourAgo(final String taskName) {
		final StartTaskScreenOperator operator = mainScreen
				.openStartTaskScreen();
		operator.startTaskHalfAnHourAgo(taskName);

	}

	@Override
	public void assertTimeSpent(final String taskName, final int periodIndex,
			final long timeSpent) {
		selectTask(taskName);
		mainScreen.waitTimeSpent(periodIndex, timeSpent);
	}

	@Override
	public void assertTimeSpentInMinutes(final String taskName,
			final long timeSpentInMinutes) {
		selectTask(taskName);
		mainScreen.waitTimeSpent(timeSpentInMinutes);
	}

	@Override
	public void assertActiveTask(final String taskName) {
		mainScreen.assertActiveTask(taskName);
		patchacaTray.assertActiveTask(taskName);
	}

}