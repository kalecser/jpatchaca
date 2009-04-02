package ui.swing.mainScreen;

import org.reactivebricks.commons.lang.Maybe;
import org.reactivebricks.pulses.Pulse;
import org.reactivebricks.pulses.Receiver;
import org.reactivebricks.pulses.Source;

import tasks.tasks.TaskView;

public class SelectedTaskName extends Source<String> {

	private Maybe<TaskView> selectedTask;
	private Receiver<String> _taskNameReceiver;

	public SelectedTaskName() {
		super("");
				
		_taskNameReceiver = new Receiver<String>() {
			@Override
			public void receive(Pulse<String> pulse) {
				supply(pulse.value());
			}
		};
		
		
	}

	public void taskChangedTo(Maybe<TaskView> selectedTask) {
		
		if (this.selectedTask != null){
			this.selectedTask.unbox().nameSignal().removeReceiver(_taskNameReceiver);
		}
		
		if (selectedTask != null){
			selectedTask.unbox().nameSignal().addReceiver(_taskNameReceiver);
		} else {
			supply("");
		}
		
		this.selectedTask = selectedTask;
		
	}


}
