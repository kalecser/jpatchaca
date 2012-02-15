package events;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import tasks.TaskView;

public class SetLabelToMultipleTasks implements Serializable{
	
	public final String labelName;
	public final Set<String> tasknames;

	public SetLabelToMultipleTasks(String labelName, TaskView... tasks) {
		this.labelName = labelName;
		this.tasknames = geTaskNames(tasks);
	}

	public static Set<String> geTaskNames(TaskView... tasks) {
		Set<String> taskNames = new LinkedHashSet<String>();
		for (TaskView t : tasks){
			taskNames.add(t.name());
		}
		return taskNames;
	}

	private static final long serialVersionUID = 1L;

}
