package tasks.delegates;

import labels.labels.SelectedLabel;
import basic.Delegate;
import basic.Delegate.Listener;
import tasks.home.TaskData;

public class CreateTaskDelegateImpl implements CreateTaskdelegate{

	private Delegate<TaskData> _subject;
	private final SelectedLabel selectedLabel;


	public CreateTaskDelegateImpl(SelectedLabel selectedLabel){
		this.selectedLabel = selectedLabel;
		_subject = new Delegate<TaskData>();
	}
	
	
	@Override
	public void createTask(TaskData taskData) {
		taskData.setLabel(selectedLabel.selectedLabelCurrentValue());
		_subject.execute(taskData);
	}


	@Override
	public void addListener(Listener<TaskData> listener) {
		_subject.addListener(listener);		
	}
	
	
	
}
