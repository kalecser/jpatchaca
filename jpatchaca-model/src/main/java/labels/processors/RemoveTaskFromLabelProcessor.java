package labels.processors;

import java.io.Serializable;

import labels.labels.LabelsHome;
import tasks.tasks.TasksView;
import events.Processor;
import events.RemoveTaskFromLabelEvent;

public class RemoveTaskFromLabelProcessor implements Processor<RemoveTaskFromLabelEvent> {

	private final LabelsHome labelsHome;
	private final TasksView tasks;
	


	public RemoveTaskFromLabelProcessor(LabelsHome labelsHome, TasksView tasks) {
		this.labelsHome = labelsHome;
		this.tasks = tasks;
		
	}

	public void execute(RemoveTaskFromLabelEvent eventObj) {
		this.labelsHome.removeTaskFromLabel(
				this.tasks.get(eventObj.getTaskId()),
				eventObj.getLabelName());
	}

	public Class<? extends Serializable> eventType() {

		return RemoveTaskFromLabelEvent.class;
	}

}
