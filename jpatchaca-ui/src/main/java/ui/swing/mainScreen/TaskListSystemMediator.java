package ui.swing.mainScreen;


import labels.LabelsSystem;

import org.picocontainer.Startable;

import tasks.TasksListener;
import tasks.TasksSystem;
import tasks.tasks.TaskView;
import ui.swing.mainScreen.periods.PeriodsList;
import ui.swing.users.SwingTasksUser;
import basic.Subscriber;

public class TaskListSystemMediator implements Startable{

	private final TaskList list;
	private final LabelsList labelsList;
	private final LabelsSystem labelsSystem;

	public TaskListSystemMediator(final TaskList list, LabelsList labelsList, final TasksSystem tasksSystem, LabelsSystem labelsSystem, final SwingTasksUser tasksUser, final PeriodsList periodsList){
		this.list = list;
		this.labelsList = labelsList;
		this.labelsSystem = labelsSystem;
		
		tasksSystem.taskListChangedAlert().subscribe(new Subscriber() {
			public void fire() {
				updateTasks();
			}			
		});
		
		this.labelsSystem.tasksInLabelChangedAlert().subscribe(new Subscriber() {
			public void fire() {
				updateTasks();
			}			
		});
		
		this.labelsSystem.labelsListChangedAlert().subscribe(new Subscriber() {
			public void fire() {
				updateLabels();
				updateTasks();
			}			
		});
		
		this.list.selectedLabelChanged().subscribe(new Subscriber() {
			public void fire() {
				updateTasks();
			}			
		});
		
		this.list.movePeriodAlert().subscribe(new Subscriber() {
			public void fire() {
				tasksSystem.movePeriod(tasksUser.getSelectedTask(), tasksUser.getPeriodMovingTarget(), periodsList.selectedPeriodIndex());
			}			
		});
		
		tasksSystem.addTasksListener(new TasksListener() {
		
			@Override
			public void removedTask(TaskView task) {
				//Ignored
		
			}
		
			@Override
			public void createdTask(TaskView task) {
				list.setSelectedTask(task);
			}
		
		});
		
		updateLabels();
	}
	
	
	private void updateLabels() {
		this.labelsList.setLabels(this.labelsSystem.labels());
	}

	private void updateTasks() {
		final String selectedLabel = this.list.selectedLabel();
		if (selectedLabel == null) return;
		this.list.setTasks(this.labelsSystem.tasksInlabel(selectedLabel));
	}
	
	public void stop() {
	}


	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}
}
