package labels;

import java.util.List;

import tasks.TaskView;
import basic.Alert;

public interface LabelsSystem{
	
	
	public void setNewLabelToTask(TaskView task, String newLabelName);
	public void setLabelToTask(TaskView task, String labelToAssignTo);
	public void removeLabelFromTask(TaskView task, String labelToRemoveFrom);
	public List<TaskView> tasksInlabel(String labelName);
	public String allLabelName();
	public List<String> labels();
	public List<String> getLabelsFor(TaskView task);
	public Alert labelsListChangedAlert();
	public Alert tasksInLabelChangedAlert();
	public List<String> assignableLabels();

	
}