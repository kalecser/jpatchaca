package ui.swing.mainScreen;

import java.util.List;

import tasks.tasks.TaskView;

public interface TaskContextMenuModel {

	List<String> getLabelsFor(TaskView selectedTask);
	List<String> assignableLabels();

}
