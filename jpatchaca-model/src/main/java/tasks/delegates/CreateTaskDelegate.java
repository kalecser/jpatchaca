package tasks.delegates;

import labels.labels.SelectedLabel;
import basic.Delegate;
import basic.Delegate.Listener;
import tasks.home.TaskData;

public class CreateTaskDelegate{

	private Delegate<TaskData> _subject;
	private final SelectedLabel selectedLabel;


	public CreateTaskDelegate(SelectedLabel selectedLabel){
		this.selectedLabel = selectedLabel;
		_subject = new Delegate<TaskData>();
	}
	
	
	public void createTask(TaskData taskData) {
		taskData.setLabel(selectedLabel.selectedLabelCurrentValue());
		_subject.execute(taskData);
	}


	public void addListener(Listener<TaskData> listener) {
		_subject.addListener(listener);		
	}
	
	
	
}
