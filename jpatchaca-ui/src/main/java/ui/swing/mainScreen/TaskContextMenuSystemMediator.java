package ui.swing.mainScreen;

import java.util.List;

import labels.LabelsSystem;

import org.picocontainer.Startable;

import tasks.TasksSystem;
import tasks.delegates.StartTaskDelegate;
import tasks.tasks.TaskView;
import ui.swing.mainScreen.tasks.TaskScreen;
import ui.swing.users.LabelsUser;
import ui.swing.users.SwingTasksUser;
import basic.Subscriber;
import basic.UserOperationCancelledException;

public class TaskContextMenuSystemMediator implements Startable{
	
	




	public TaskContextMenuSystemMediator(final TaskContextMenu taskContextMenu, final LabelsSystem labelsSystem, final TasksSystem tasksSystem, final SwingTasksUser tasksUser, final LabelsUser labelsUser, final MainScreen mainScreen, final StartTaskDelegate startTaskDelegate, final TaskScreen taskScreen) {
		taskContextMenu.addNoteAlert().subscribe(new Subscriber() {
			public void fire() {
				try {
					tasksSystem.addNoteToTask(tasksUser.getSelectedTask(), tasksUser.getTextForNote());
				} catch (final UserOperationCancelledException e) {
					//cancelled
				}
			}		
		});
		
		taskContextMenu.createLabelAlert().subscribe(new Subscriber() {
			public void fire() {
				try {
					labelsSystem.setNewLabelToTask(tasksUser.getSelectedTask(), labelsUser.getNewLabelName(null));
				} catch (final UserOperationCancelledException e) {
				}
			}		
		});
		
		taskContextMenu.assignToLabelAlert().subscribe(new Subscriber() {
			public void fire() {
				labelsSystem.setLabelToTask(tasksUser.getSelectedTask(), labelsUser.getLabelToAssignTaskTo());
			}		
		});	
				
		taskContextMenu.removeFromLabelAlert().subscribe(new Subscriber() {
			public void fire() {
				labelsSystem.removeLabelFromTask(tasksUser.getSelectedTask(), labelsUser.selectedLabel());
			}
		});
		
		taskContextMenu.startCurrentTaskAlert().subscribe(new Subscriber() {
			public void fire() {
				new Thread(){
					@Override
					public void run() {
						startTaskDelegate.execute(tasksUser.getSelectedTask());
					}
				}.start();
			}
		});
		
		taskContextMenu.stopCurrentTaskAlert().subscribe(new Subscriber() {
			public void fire() {
				new Thread(){
					@Override
					public void run() {
						tasksSystem.stopTask(tasksUser.getSelectedTask());
					}
				}.start();
			}
		});
		
		taskContextMenu.removeTaskAlert().subscribe(new Subscriber() {
			public void fire() {
				if (tasksUser.isTaskExclusionConfirmed())
					tasksSystem.removeTask(tasksUser.getSelectedTask());
			}		
		});
		
		taskContextMenu.editTaskAlert().subscribe(new Subscriber() {
			public void fire() {
				
				taskScreen.editSelectedTask();			
				
			}		
		});
		
		taskContextMenu.setModel(new TaskContextMenuModel() {
			
			public List<String> assignableLabels() {
				return labelsSystem.assignableLabels();
			}
			
			public List<String> getLabelsFor(TaskView selectedTask) {
				return labelsSystem.getLabelsFor(selectedTask);
			}
		});
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
