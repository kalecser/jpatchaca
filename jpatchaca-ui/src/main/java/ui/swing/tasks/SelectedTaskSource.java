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

	public synchronized void addReceiver(Receiver<TaskView> receiver) {
		_subject.addReceiver(receiver);		
	}

	public synchronized TaskView currentValue() { 
		return _subject.currentValue();
	}

	public synchronized void removeReceiver(Receiver<TaskView> taskReceiver) {
		_subject.removeReceiver(taskReceiver);
	}

	public synchronized void supply(TaskView t) {
		_subject.supply(t);
		this.notify();
	}

	public synchronized Signal<TaskView> output() {
		return _subject;
	}

	public TaskView waitForSelectedTask() {
			final TaskView selectedTask = this.currentValue();
			
			if (selectedTask == null)
				this.waitUntilTimeout(200);
			
			return this.currentValue();
	}
	
	public synchronized void waitUntilTimeout(int i) {
		try {
			this.wait(200);
		} catch (InterruptedException expected) {	}
	}

}
