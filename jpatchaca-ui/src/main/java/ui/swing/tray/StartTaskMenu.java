package ui.swing.tray;

import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.reactivebricks.pulses.Pulse;
import org.reactivebricks.pulses.Receiver;
import org.reactivebricks.pulses.Signal;

import tasks.tasks.TaskView;
import ui.swing.mainScreen.SelectedTaskName;

public class StartTaskMenu {

	static final String START_TASK = "Start task";
	
	//bug, should depend on selectedtaskname only
	private final Signal<TaskView> selectedTaskSignal;
	private final PatchacaTrayModel model;
	private TaskView selectedTask;
	private final SelectedTaskName selectedTaskName;

	public StartTaskMenu(Signal<TaskView> selectedTaskSignal, SelectedTaskName selectedTaskName, PatchacaTrayModel model) {
		this.selectedTaskSignal = selectedTaskSignal;
		this.selectedTaskName = selectedTaskName;
		this.model = model;
	}

	public MenuItem getMenu() {
		final MenuItem menuItem = new MenuItem("");
		
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedTask == null)
					return;
				
				model.startTaskIn(selectedTask, 0);
			}
		});
		
		selectedTaskSignal.addReceiver(new Receiver<TaskView>() {

			@Override
			public void receive(Pulse<TaskView> pulse) {
				selectedTask = pulse.value();
				if (selectedTask == null){
					menuItem.setLabel(START_TASK);
					menuItem.setEnabled(false);
					return;
				}
			}
		});
		
		selectedTaskName.addReceiver(new Receiver<String>() {
		
			@Override
			public void receive(Pulse<String> pulse) {
				menuItem.setLabel(START_TASK + " " + pulse.value());
			}
		});
		
		return menuItem;
	}

}
