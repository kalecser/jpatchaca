package labels.labels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.Validate;

import tasks.TaskView;
import basic.Alert;
import basic.AlertImpl;

public class LabelsHomeImpl implements LabelsHome {

	private final Map<String, Set<TaskView>> tasksByLabel;
	private final AlertImpl labelsListChangedAlert;
	private final AlertImpl tasksInLabelChangedAlert;
	
	
	public LabelsHomeImpl() {
	
		this.tasksByLabel = new HashMap<String, Set<TaskView>>();
		this.labelsListChangedAlert = new AlertImpl();
		tasksInLabelChangedAlert = new AlertImpl();
		
		createLabel(LabelsHome.ALL_LABEL_NAME);
	}

	@Override
	public Set<TaskView> getTasksInLabel(final String labelName) {		
		if (!this.tasksByLabel.containsKey(labelName))
			createLabel(labelName);
		
		return this.tasksByLabel.get(labelName);
	}

	private void createLabel(final String labelName) {
		Validate.notNull(labelName);
		
		this.tasksByLabel.put(labelName, new LinkedHashSet<TaskView>());
		this.labelsListChangedAlert.fire();
	}

	@Override
	public void setLabelToTask(final TaskView task, final String labelName) {
		Validate.notNull(task);
		Validate.notNull(labelName);
		
		final Set<TaskView> tasksInLabel = getTasksInLabel(labelName);
		if (!tasksInLabel.contains(task)) {
			tasksInLabel.add(task);
			labelsListChangedAlert.fire();
		}
	
		tasksInLabelChangedAlert.fire();
	}

	@Override
	public void removeTaskFromLabel(final TaskView task, final String labelName) {
		Validate.notNull(task);
		Validate.notNull(labelName);
		
		getTasksInLabel(labelName).remove(task);
		if (getTasksInLabel(labelName).size() == 0) {
			this.tasksByLabel.remove(labelName);
			this.labelsListChangedAlert.fire();
		}
		
		tasksInLabelChangedAlert.fire();
	}

	@Override
	public List<String> labels() {
		final List<String> labels = new ArrayList<String>(this.tasksByLabel.keySet());
		labels.remove(LabelsHome.ALL_LABEL_NAME);
		Collections.sort(labels);
		labels.add(0,LabelsHome.ALL_LABEL_NAME);
		return labels;
	}

	@Override
	public Alert labelsListChangedAlert() {
		return this.labelsListChangedAlert;
	}

	@Override
	public List<String> assignableLabels() {
		final List<String> labels = labels();
		labels.remove(LabelsHome.ALL_LABEL_NAME);
		return labels;
	}

	@Override
	public List<String> getLabelsFor(final TaskView selectedTask) {
		final List<String> result = new ArrayList<String>();
		for(final String label : assignableLabels())
			if (this.tasksByLabel.get(label).contains(selectedTask))
				result.add(label);
		return result;		
	}

	@Override
	public String allLabelName() {
		return LabelsHome.ALL_LABEL_NAME;
	}

	
	@Override
	public Alert tasksInLabelChangedAlert() {
		return tasksInLabelChangedAlert;
	}

	@Override
	public void setLabelToMultipleTasks(String labelName,
			Set<TaskView> tasksTosetLabelTo) {
		final Set<TaskView> tasksInLabel = getTasksInLabel(labelName);
		if (tasksInLabel.containsAll(tasksTosetLabelTo)){
			return;
		}
		tasksInLabel.addAll(tasksTosetLabelTo);
		tasksInLabelChangedAlert.fire();
	}

}
