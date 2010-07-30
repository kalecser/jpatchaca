package ui.swing.singleInstance;

import main.singleInstance.AssureSingleInstance;

import org.picocontainer.Startable;

import basic.Subscriber;

import ui.swing.tray.PatchacaTray;
import ui.swing.tray.PatchacaTrayModel;

public class ShowMainScreenOnSecondRun implements Startable {

	private final PatchacaTrayModel model;
	private final AssureSingleInstance singleInstance;
	private final PatchacaTray tray;

	public ShowMainScreenOnSecondRun(PatchacaTrayModel model, AssureSingleInstance singleInstance, PatchacaTray tray){
		this.model = model;
		this.singleInstance = singleInstance;
		this.tray = tray;
	}

	@Override
	public void start() {
		singleInstance.subscribeTryedToCreateAnotherInstance(new Subscriber() {
			@Override
			public void fire() {
				tray.ensureTrayIconIsVisibleDueToWindowsBug();
				model.showMainScreen();	
			}
		});
	}

	@Override
	public void stop() {

	}

}
