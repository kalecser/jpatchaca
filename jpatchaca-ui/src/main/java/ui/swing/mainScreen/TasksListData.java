/**
 * 
 */
package ui.swing.mainScreen;

public class TasksListData {
	private String selectedTaskName = "";
	private int selectedLabel = 0;

	public TasksListData(final String selectedTaskName, final int selectedlabel) {
		this.selectedTaskName = selectedTaskName;
		this.selectedLabel = selectedlabel;
	}

	public TasksListData() {

	}

	public String getSelectedTask() {
		return selectedTaskName;
	}

	public void setSelectedTask(final String selectedTaskName) {
		this.selectedTaskName = selectedTaskName;
	}

	public void setSelectedLabel(final int selectedIndex) {
		this.selectedLabel = selectedIndex;
	}

	public int getSelectedLabel() {
		return selectedLabel;
	}

	public String encodeAsString() {
		return selectedTaskName + "\n" + selectedLabel;
	}

	public static TasksListData decode(final String encoded) {
		final String[] tokens = encoded.split("\n");

		final TasksListData decoded = new TasksListData();

		try {
			if (tokens.length > 0) {
				decoded.setSelectedTask(tokens[0]);
			}

			if (tokens.length > 1) {
				decoded.setSelectedLabel(Integer.parseInt(tokens[1]));
			}
		} catch (final NumberFormatException e) {
			return new TasksListData();
		}

		return decoded;
	}

}