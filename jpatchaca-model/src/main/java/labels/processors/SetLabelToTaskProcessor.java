package labels.processors;

import java.io.Serializable;

import labels.labels.LabelsHome;
import tasks.tasks.TasksView;
import events.Processor;
import events.SetLabelToTaskEvent;

public class SetLabelToTaskProcessor implements Processor<SetLabelToTaskEvent> {

	private final LabelsHome labelsHome;
	private final TasksView tasks;

	public SetLabelToTaskProcessor(TasksView tasks, LabelsHome labelsHome) {
		this.tasks = tasks;
		this.labelsHome = labelsHome;
	}

	@Override
	public Class<? extends Serializable> eventType() {
		return SetLabelToTaskEvent.class;
	}

	@Override
	public void execute(SetLabelToTaskEvent eventObj) {
		final String labelName = eventObj.labelName();
		
		this.labelsHome.setLabelToTask(
				this.tasks.get(eventObj.taskId()), 
				labelName);
	}
}
