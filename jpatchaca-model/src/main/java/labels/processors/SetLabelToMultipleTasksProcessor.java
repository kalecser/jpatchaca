package labels.processors;

import java.io.Serializable;
import java.util.Set;

import labels.labels.LabelsHome;
import tasks.TaskView;
import tasks.tasks.TasksView;
import events.Processor;
import events.SetLabelToMultipleTasks;
import events.persistence.MustBeCalledInsideATransaction;

public class SetLabelToMultipleTasksProcessor implements Processor<SetLabelToMultipleTasks> {

	private final LabelsHome labelsHome;
	private final TasksView tasks;

	public SetLabelToMultipleTasksProcessor(LabelsHome labelsHome, TasksView tasks) {
		this.labelsHome = labelsHome;
		this.tasks = tasks;
	}

	@Override
	public void execute(SetLabelToMultipleTasks eventObj)
			throws MustBeCalledInsideATransaction {
		Set<TaskView> tasksTosetLabelTo = tasks.getTasksByName(eventObj.tasknames);
		labelsHome.setLabelToMultipleTasks(eventObj.labelName, tasksTosetLabelTo);
	}

	@Override
	public Class<? extends Serializable> eventType() {
		return SetLabelToMultipleTasks.class;
	}

}
