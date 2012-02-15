package ui.swing.mainScreen.tasks;

import junit.framework.Assert;

import org.junit.Test;

import tasks.delegates.CreateTaskDelegate;
import tasks.home.TaskData;
import ui.swing.mainScreen.tasks.mock.CreateTaskDelegateMock;
import ui.swing.mainScreen.tasks.mock.MockTask;
import ui.swing.mainScreen.tasks.mock.MockTasksSystem;
import ui.swing.tasks.SelectedTaskSource;
import basic.NonEmptyString;

public class TaskScreenModelTest {

	SelectedTaskSource selectedTask = new SelectedTaskSource();
	private CreateTaskDelegate createTaskMock = new CreateTaskDelegateMock();
	TaskScreenModelImpl subject = 
			new TaskScreenModelImpl(new MockTasksSystem(), selectedTask, createTaskMock);
	MockTask taskView = new MockTask("test");
	TaskData data = new TaskData(new NonEmptyString("newName"));
	
	@Test
	public void onEditTask_ShouldUpdateSelectedTask(){
		subject.editTask(taskView, data);
		Assert.assertEquals(taskView, selectedTask.currentValue());
	}
	
}
