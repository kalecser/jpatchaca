package labels.labels;

import java.util.List;
import java.util.Set;

import tasks.TaskView;
import basic.Alert;


public interface LabelsHomeView {
	Set<TaskView> getTasksInLabel(String labelName);
	List<String> labels();
	List<String> assignableLabels();
	List<String> getLabelsFor(TaskView selectedTask);
	Alert labelsListChangedAlert();
	Alert tasksInLabelChangedAlert();
	String allLabelName();
}
