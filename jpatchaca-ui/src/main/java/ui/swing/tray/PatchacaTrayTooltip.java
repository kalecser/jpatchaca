package ui.swing.tray;

import org.reactivebricks.pulses.Pulse;
import org.reactivebricks.pulses.Receiver;
import org.reactivebricks.pulses.Signal;
import org.reactivebricks.pulses.Source;

public class PatchacaTrayTooltip {

	
	private String currentSelectedTask;
	private String currentActiveTask;
	private Source<String> tooltip;
	
	public PatchacaTrayTooltip(Signal<String> activeTaskName,
			Signal<String> selectedTaskName) {
		
		
		tooltip = new Source<String>("");
		
		activeTaskName.addReceiver(new Receiver<String>() {
		
			@Override
			public void receive(Pulse<String> pulse) {
				synchronized (PatchacaTrayTooltip.this){
					currentActiveTask = pulse.value();
					updateToolTip();
				}
		
			}
		});
		
		selectedTaskName.addReceiver(new Receiver<String>() {
			
			@Override
			public void receive(Pulse<String> pulse) {
				synchronized (PatchacaTrayTooltip.this){
					currentSelectedTask = pulse.value();
					updateToolTip();
				}
			}
		});
	}

	protected void updateToolTip() {
		
		if (currentActiveTask.equals("")) {
			tooltip.supply("Start task " + currentSelectedTask);
		} else {
			tooltip.supply("Patchaca timer - active: " + currentActiveTask);
		}
		
		
	}
	
	public Signal<String> output(){
		return tooltip;
	}

}
