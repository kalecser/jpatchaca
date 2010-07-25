package ui.swing.mainScreen.tasks.mock;

import lang.Maybe;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.Validate;

import periodsInTasks.MockTask;

import tasks.TaskView;
import tasks.home.TaskData;
import ui.swing.mainScreen.tasks.TaskScreenModel;
import wheel.lang.Threads;

public class MockTaskScreenModel implements TaskScreenModel {

	public Maybe<TaskData> createdOrEditedTaskData;
	private MockTask selectedTask;

	@Override
	public TaskView selectedTask() {
		return selectedTask;
	}

	@Override
	public void createTask(TaskData data) {
		createdOrEditedTaskData = Maybe.wrap(data);
	}

	@Override
	public void createTaskAndStart(TaskData data, Long unbox) {
		throw new NotImplementedException();
	}

	@Override
	public void editTask(TaskView taskView, TaskData data) {
		createdOrEditedTaskData = Maybe.wrap(data);
	}

	public void waitCreatedTaskWithJiraId(String jiraKey) {
		
		Validate.notNull(jiraKey);
		
		long current = System.currentTimeMillis();
		while (true){
			Threads.sleepWithoutInterruptions(100);
			boolean fourSecondsHavePassed = System.currentTimeMillis() > current + 4000;
			if (fourSecondsHavePassed){
				throw new IllegalStateException("Timeout waiting for a task with " + jiraKey + " jirakey to be created");
			}
			
			if (createdOrEditedTaskData != null){
				if (createdOrEditedTaskData.unbox().getJiraIssue() != null){
					if (jiraKey.equals(createdOrEditedTaskData.unbox().getJiraIssue().getKey())){
						return;
					}					
				}
			}
		}
	}

	public void setSelectedTask(MockTask mockTask) {
		selectedTask = mockTask;
		
	}

}
