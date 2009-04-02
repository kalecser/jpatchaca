package labels.processors;

import java.io.Serializable;

import labels.labels.LabelsHome;
import tasks.TasksSystem;
import tasks.tasks.TaskView;
import events.CreateTaskEvent3;
import events.Processor;

public class CreateTaskProcessor3 implements Processor<CreateTaskEvent3> {

	
	private final LabelsHome labelsHome;
	private final TasksSystem tasksSystem;

	public CreateTaskProcessor3(LabelsHome labelsHome, TasksSystem tasksSystem){
		this.labelsHome = labelsHome;
		this.tasksSystem = tasksSystem;
	}
	
	@Override
	public Class<? extends Serializable> eventType() {
		return CreateTaskEvent3.class;
	}

	@Override
	public void execute(CreateTaskEvent3 eventObj) {
		final String label = eventObj.getLabel();
		if (label != null){
			final TaskView createdTask = tasksSystem.getTaskView(eventObj.getObjectIdentity());
			labelsHome.setLabelToTask(createdTask, label);
		}
	}

}
