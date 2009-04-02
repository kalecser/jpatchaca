/**
 * 
 */
package ui.swing.mainScreen;

public class TasksListData{		
	private int selectedTask = 0;
	private int selectedLabel = 0;

	public TasksListData(int selectedTask, int selectedlabel) {
		this.selectedTask = selectedTask;
		this.selectedLabel = selectedlabel;
	}
	
	public TasksListData(){
		
	}

	public int getSelectedTask() {
		return selectedTask;
	}

	public void setSelectedTask(int selectedTask) {
		this.selectedTask = selectedTask;
	}

	public void setSelectedLabel(int selectedIndex) {
		this.selectedLabel = selectedIndex;
	}

	public int getSelectedLabel() {
		return selectedLabel;
	}

	public String encodeAsString() {
		return 
			selectedTask + "\n" +
			selectedLabel;
	}

	public static TasksListData decode(String encoded) {
		String[] tokens = encoded.split("\n");
		
		TasksListData decoded = new TasksListData();

			try {
				if (tokens.length > 0){
					decoded.setSelectedTask(Integer.parseInt(tokens[0]));
				}
				
				if (tokens.length > 1){
					decoded.setSelectedLabel(Integer.parseInt(tokens[1]));
				}
			} catch (NumberFormatException e) {
				return new TasksListData(); 
			}
		
		return decoded;
	}
	
}