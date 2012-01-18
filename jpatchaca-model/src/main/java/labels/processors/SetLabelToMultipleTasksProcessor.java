package labels.processors;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import labels.labels.LabelsHome;
import lang.Maybe;
import tasks.TaskView;
import tasks.tasks.TasksView;
import basic.NonEmptyString;
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
		Set<TaskView> tasksTosetLabelTo = getTasksToSetLabelTo(eventObj);
		labelsHome.setLabelToMultipleTasks(eventObj.labelName, tasksTosetLabelTo);
	}

	private Set<TaskView> getTasksToSetLabelTo(SetLabelToMultipleTasks eventObj) {
		Set<TaskView> tasksTosetLabelTo = new LinkedHashSet<TaskView>();
		for (String name : eventObj.tasknames){
			tasksTosetLabelTo.add(taskByNameOrCry(name));
		}
		return tasksTosetLabelTo;
	}

	private TaskView taskByNameOrCry(String name) {
		@SuppressWarnings("unchecked")
		Maybe<TaskView> maybeTask = (Maybe<TaskView>) tasks.byName(new NonEmptyString(name));
		TaskView task = maybeTask.unbox();
		return task;
	}

	@Override
	public Class<? extends Serializable> eventType() {
		return SetLabelToMultipleTasks.class;
	}

}
