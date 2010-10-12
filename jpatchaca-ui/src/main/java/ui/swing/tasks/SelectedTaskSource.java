package ui.swing.tasks;

import org.reactive.Receiver;
import org.reactive.Signal;
import org.reactive.Source;

import tasks.TaskView;

public class SelectedTaskSource {

	private Source<TaskView> _subject;

	public SelectedTaskSource() {
		_subject = new Source<TaskView>(null);
	}

	public void addReceiver(Receiver<TaskView> receiver) {
		_subject.addReceiver(receiver);		
	}

	public TaskView currentValue() { 
		return _subject.currentValue();
	}

	public void removeReceiver(Receiver<TaskView> taskReceiver) {
		_subject.removeReceiver(taskReceiver);
	}

	public void supply(TaskView t) {
		_subject.supply(t);
	}

	public Signal<TaskView> output() {
		return _subject;
	}
	

}
