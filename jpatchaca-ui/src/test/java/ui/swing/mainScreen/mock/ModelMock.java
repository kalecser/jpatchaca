package ui.swing.mainScreen.mock;

import java.util.ArrayList;
import java.util.List;

import tasks.TaskView;
import ui.swing.mainScreen.TaskContextMenuModel;

public class ModelMock implements TaskContextMenuModel {

	private String operations = "";

	@Override
	public List<String> getLabelsFor(TaskView selectedTask) {
		return new ArrayList<String>();
	}

	@Override
	public List<String> assignableLabels() {
		return new ArrayList<String>();
	}

	public String getOperations() {
		return operations;
	}

	@Override
	public void openInBrowser(TaskView task) {
		operations  += "open task " +task.name() +  " in browser";
	}

}
