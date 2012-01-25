package events;

import java.io.Serializable;
import java.util.Set;

import tasks.TaskView;

public class RemoveLabelFromMultipleTasks implements Serializable{
	
	public final String label;
	public Set<String> taskNames;

	public RemoveLabelFromMultipleTasks(String label, Set<TaskView> tasks) {
		this.label = label;
		taskNames = SetLabelToMultipleTasks.geTaskNames(tasks.toArray(new TaskView[0]));
	}

	private static final long serialVersionUID = 1L;

}
