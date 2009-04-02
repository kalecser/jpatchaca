package labels.labels;

import tasks.tasks.TaskView;

public interface LabelsHome extends LabelsHomeView {	
	void setLabelToTask(TaskView mockTask, String labelName);
	void removeTaskFromLabel(TaskView task, String labelName);

}
