package labels.processors;

import java.io.Serializable;

import labels.labels.LabelsHome;
import tasks.tasks.TasksView;
import events.Processor;
import events.RemoveLabelFromMultipleTasks;
import events.persistence.MustBeCalledInsideATransaction;

public class RemoveLabelFromMultipleTasksProcessor implements Processor<RemoveLabelFromMultipleTasks> {

	private final LabelsHome labels;
	private final TasksView tasks;

	public RemoveLabelFromMultipleTasksProcessor(LabelsHome labels, TasksView tasks){
		this.labels = labels;
		this.tasks = tasks;
	}
	
	@Override
	public void execute(RemoveLabelFromMultipleTasks eventObj)
			throws MustBeCalledInsideATransaction {
		labels.removeMultipleTasks(eventObj.label, tasks.getTasksByName(eventObj.taskNames));
	}

	@Override
	public Class<? extends Serializable> eventType() {
		return RemoveLabelFromMultipleTasks.class;
	}

}
