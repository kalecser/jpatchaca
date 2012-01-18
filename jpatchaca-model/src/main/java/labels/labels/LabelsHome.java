package labels.labels;

import java.util.Set;

import tasks.TaskView;

public interface LabelsHome extends LabelsHomeView {	
	static final String ALL_LABEL_NAME = "All";
	void setLabelToTask(TaskView mockTask, String labelName);
	void removeTaskFromLabel(TaskView task, String labelName);
	void setLabelToMultipleTasks(String labelName, Set<TaskView> tasksTosetLabelTo);

}
