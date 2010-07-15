package labels.labels;

import tasks.TaskView;

public interface LabelsHome extends LabelsHomeView {	
	static final String ALL_LABEL_NAME = "All";
	void setLabelToTask(TaskView mockTask, String labelName);
	void removeTaskFromLabel(TaskView task, String labelName);

}
