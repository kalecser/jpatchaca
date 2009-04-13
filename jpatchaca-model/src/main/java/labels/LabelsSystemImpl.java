package labels;

import java.util.List;

import labels.labels.LabelsHome;
import labels.labels.LabelsHomeImpl;
import labels.labels.LabelsHomeView;
import labels.processors.CreateTaskProcessor3;
import labels.processors.RemoveTaskFromLabelProcessor;
import labels.processors.SetLabelToTaskProcessor;
import labels.processors.SetSelectedLabelProcessor;

import org.apache.commons.lang.Validate;
import org.picocontainer.Startable;

import tasks.TasksListener;
import tasks.TasksSystem;
import tasks.tasks.TaskView;
import tasks.tasks.TasksView;
import basic.Alert;
import events.EventsSystem;
import events.RemoveTaskFromLabelEvent;
import events.SetLabelToTaskEvent;

public class LabelsSystemImpl implements LabelsSystem, Startable {

	private final LabelsHomeView labelsHomeView;
	private final EventsSystem eventsSystem;
	private final TasksView tasks;
	


	public LabelsSystemImpl(final EventsSystem eventsSystem, final TasksSystem tasksSystem, TasksView tasks) {

		this.tasks = tasks;
		final LabelsHome labelsHome = new LabelsHomeImpl();
		
		this.eventsSystem = eventsSystem;
		this.labelsHomeView = labelsHome;
		
		eventsSystem.addProcessor(new SetLabelToTaskProcessor(tasks, labelsHome));
		eventsSystem.addProcessor(new RemoveTaskFromLabelProcessor(labelsHome, tasks));
		eventsSystem.addProcessor(new SetSelectedLabelProcessor());
		eventsSystem.addProcessor(new CreateTaskProcessor3(labelsHome, tasks));
		
		tasksSystem.addTasksListener(new TasksListener() {
			public void createdTask(final TaskView task) {
				labelsHome.setLabelToTask(task, allLabelName());
			}

			public void removedTask(final TaskView task) {
				final List<String> assignedLabels = labelsHome.getLabelsFor(task);
				for (final String label : assignedLabels){
					labelsHome.removeTaskFromLabel(task, label);
				}
				labelsHome.removeTaskFromLabel(task, allLabelName());
			}
		});
		
	}

	public void setNewLabelToTask(final TaskView task, final String newLabelName) {
			Validate.notNull(task);
			Validate.notNull(newLabelName);
			final SetLabelToTaskEvent event = new SetLabelToTaskEvent(tasks.idOf(task), 
					newLabelName);
			this.eventsSystem.writeEvent(event);
	}

	public void setLabelToTask(final TaskView task, final String labeltoAssignTo) {
			Validate.notNull(task);
			Validate.notNull(labeltoAssignTo);
			final SetLabelToTaskEvent event = new SetLabelToTaskEvent(tasks.idOf(task), 
					labeltoAssignTo);
			this.eventsSystem.writeEvent(event);
	}

	public void removeLabelFromTask(final TaskView task, final String labelToAssignTo) {
		final RemoveTaskFromLabelEvent event = new RemoveTaskFromLabelEvent(tasks.idOf(task),
				labelToAssignTo);
		this.eventsSystem.writeEvent(event);		
	}



	public List<TaskView> tasksInlabel(final String labelName) {
		return labelsHomeView.getTasksInLabel(labelName);
	}



	public String allLabelName() {
		return labelsHomeView.allLabelName();
	}


	public List<String> labels() {
		return labelsHomeView.labels();
	}


	public List<String> getLabelsFor(final TaskView task) {
		return labelsHomeView.getLabelsFor(task);
	}

	public Alert labelsListChangedAlert() {
		return labelsHomeView.labelsListChangedAlert();
	}

	public List<String> assignableLabels() {
		return labelsHomeView.assignableLabels();
	}

	@Override
	public Alert tasksInLabelChangedAlert() {
		return labelsHomeView.tasksInLabelChangedAlert();
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
