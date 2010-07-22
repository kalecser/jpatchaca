package ui.swing.singleInstance;

import main.singleInstance.AssureSingleInstance;

import org.picocontainer.Startable;

import basic.Subscriber;

import ui.swing.tray.PatchacaTrayModel;

public class ShowMainScreenOnSecondRun implements Startable {

	private final PatchacaTrayModel model;
	private final AssureSingleInstance singleInstance;

	public ShowMainScreenOnSecondRun(PatchacaTrayModel model, AssureSingleInstance singleInstance){
		this.model = model;
		this.singleInstance = singleInstance;
	}

	@Override
	public void start() {
		singleInstance.subscribeTryedToCreateAnotherInstance(new Subscriber() {
			@Override
			public void fire() {
				model.showMainScreen();				
			}
		});
	}

	@Override
	public void stop() {

	}

}
