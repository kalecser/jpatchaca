package labels.processors;

import java.io.Serializable;

import labels.labels.LabelsHome;
import tasks.TasksSystem;
import events.Processor;
import events.RemoveTaskFromLabelEvent;

public class RemoveTaskFromLabelProcessor implements Processor<RemoveTaskFromLabelEvent> {

	private final LabelsHome labelsHome;
	private final TasksSystem tasksSystem;
	


	public RemoveTaskFromLabelProcessor(LabelsHome labelsHome, TasksSystem tasksSystem) {
		this.labelsHome = labelsHome;
		this.tasksSystem = tasksSystem;
		
	}

	public void execute(RemoveTaskFromLabelEvent eventObj) {
		this.labelsHome.removeTaskFromLabel(
				this.tasksSystem.getTaskView(eventObj.getTaskId()),
				eventObj.getLabelName());
	}

	public Class<? extends Serializable> eventType() {

		return RemoveTaskFromLabelEvent.class;
	}

}
