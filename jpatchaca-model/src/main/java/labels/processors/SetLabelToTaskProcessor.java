package labels.processors;

import java.io.Serializable;

import labels.labels.LabelsHome;
import tasks.TasksSystem;
import events.Processor;
import events.SetLabelToTaskEvent;

public class SetLabelToTaskProcessor implements Processor<SetLabelToTaskEvent> {

	private final TasksSystem tasksSystem;
	private final LabelsHome labelsHome;

	public SetLabelToTaskProcessor(TasksSystem tasksSystem, LabelsHome labelsHome) {
		this.tasksSystem = tasksSystem;
		this.labelsHome = labelsHome;
	}

	public Class<? extends Serializable> eventType() {
		return SetLabelToTaskEvent.class;
	}

	public void execute(SetLabelToTaskEvent eventObj) {
		final String labelName = eventObj.labelName();
		
		this.labelsHome.setLabelToTask(
				this.tasksSystem.getTaskView(eventObj.taskId()), 
				labelName);
	}
}
