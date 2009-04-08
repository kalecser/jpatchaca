/**
 * 
 */
package tasks.adapters.ui;

import java.util.List;

import tasks.PatchacaTasksOperator;
import ui.swing.tray.tests.environment.PathcacaTrayOperator;
import basic.mock.MockHardwareClock;

public final class PatchacaTasksOperatorUsingUI implements PatchacaTasksOperator {
	
	private final MainScreenOperator mainScreen;
	private final MockHardwareClock mockHardwareClock;
	private PathcacaTrayOperator patchacaTray;
	
	public PatchacaTasksOperatorUsingUI(MockHardwareClock mockHardwareClock){
		
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
	public void createTask(String taskName) {
		mainScreen.createTask(taskName);
	}

	@Override
	public void createTaskAndAssignToLabel(String taskName,
			String labelName) {
		createTask(taskName);
		mainScreen.assignTaskToLabel(taskName, labelName);
	}

	@Override
	public boolean isTaskInLabel(String taskName, String labelName) {
		mainScreen.selectLabel(labelName);
		return mainScreen.isTaskVisible(taskName);
	}

	@Override
	public void ediTask(String taskName, String taskNewName) {
		selectDefaultLabel();
		
		mainScreen.selectTask(taskName);
		mainScreen.pushEditTaskMenu();
		
		final TaskScreenOperator taskScreen = new TaskScreenOperator();
		taskScreen.setTaskName(taskNewName);
		taskScreen.clickOk();
	}

	@Override
	public void setTime(int time) {
		mockHardwareClock.setTime(time);
		
	}

	@Override
	public void startTask(String taskName) {
		mainScreen.startTask(taskName);
	}

	@Override
	public void stopTask() {
		mainScreen.stopTask();			
	}

	public void selectTask(String taskName) {
		mainScreen.selectTask(taskName);
		patchacaTray.assertSelectedTask(taskName);
		
	}

	@Override
	public void startNewTaskNow(String taskName) {
		
		patchacaTray.startNewTaskNow(taskName);
		
		final TaskScreenOperator taskScreen = new TaskScreenOperator();
		taskScreen.setTaskName(taskName);
		taskScreen.clickOk();
		
		
	}

	@Override
	public void startTaskHalfAnHourAgo(String taskName) {
		patchacaTray.startTaskHalfAnHourAgo(taskName);
		
	}

	@Override
	public void assertTimeSpent(String taskName, int periodIndex,  long timeSpent) {
		mainScreen.selectTask(taskName);
		mainScreen.waitTimeSpent(periodIndex, timeSpent);
	}

	@Override
	public void assertLastActiveTasks(List<String> expectedLastActiveTasksNames) {
		patchacaTray.assertLastActiveTasks(expectedLastActiveTasksNames);		
	}

	@Override
	public void assertActiveTask(String taskName) {
		mainScreen.assertActiveTask(taskName);	
		patchacaTray.assertActiveTask(taskName);
	}

	
}