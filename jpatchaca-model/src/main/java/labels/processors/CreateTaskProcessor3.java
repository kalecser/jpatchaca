package labels.processors;

import java.io.Serializable;

import labels.labels.LabelsHome;
import tasks.TaskView;
import tasks.tasks.TasksView;
import events.CreateTaskEvent3;
import events.Processor;

public class CreateTaskProcessor3 implements Processor<CreateTaskEvent3> {

	
	private final LabelsHome labelsHome;
	private final TasksView tasks;

	public CreateTaskProcessor3(LabelsHome labelsHome, TasksView tasks){
		this.labelsHome = labelsHome;
		this.tasks = tasks;
	}
	
	@Override
	public Class<? extends Serializable> eventType() {
		return CreateTaskEvent3.class;
	}

	@Override
	public void execute(CreateTaskEvent3 eventObj) {
		final String label = eventObj.getLabel();
		if (label != null){
			final TaskView createdTask = tasks.get(eventObj.getObjectIdentity());
			labelsHome.setLabelToTask(createdTask, label);
		}
	}

}
