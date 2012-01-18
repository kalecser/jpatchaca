package ui.swing.tasks;

import java.util.LinkedHashSet;
import java.util.Set;

import org.reactive.Receiver;
import org.reactive.Signal;
import org.reactive.Source;

import tasks.TaskView;

public class SelectedTaskSource {

	private Source<TaskView> _subject;
	private Object[] selectedValues = new Object[0];

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

	public void supplySelectedValues(Object[] selectedValues) {
		this.selectedValues = selectedValues;
	}

	public Set<TaskView> selectedTasks() {
		Set<TaskView> result = new LinkedHashSet<TaskView>();
		for (Object task : selectedValues){
			result.add((TaskView) task);					
		}
		return result;
	}

}
