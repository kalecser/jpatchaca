package ui.swing.mainScreen.tasks;

import tasks.TasksSystem;
import tasks.tasks.TaskData;
import tasks.tasks.TaskView;
import ui.swing.tasks.SelectedTaskSource;

public class TaskScreenModelImpl implements TaskScreenModel{

	private final TasksSystem taskSystem;
	private final SelectedTaskSource selectedTask;

	public TaskScreenModelImpl(TasksSystem taskSystem, SelectedTaskSource selectedTask){
		this.taskSystem = taskSystem;
		this.selectedTask = selectedTask;
	}
	
	@Override
	public void createTask(TaskData data) {
		taskSystem.createTask(data);
	}

	@Override
	public void createTaskAndStart(TaskData data, Long unbox) {
		taskSystem.createAndStartTaskIn(data, unbox);
		
	}

	@Override
	public TaskView selectedTask() {
		return selectedTask.currentValue();
	}

	@Override
	public void editTask(TaskView taskView, TaskData data) {
		taskSystem.editTask(taskView, data);
		
	}

}
