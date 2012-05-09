package keyboardRotation;

import java.io.Serializable;

import org.picocontainer.Startable;

import events.EventsSystem;
import events.Processor;
import events.SetKeyboardRotationOptions2;
import events.persistence.MustBeCalledInsideATransaction;

public class SetKeyboardRotationOptions2Processor implements Processor<SetKeyboardRotationOptions2>, Startable{

	private KeyboardRotationOptions options;

	public SetKeyboardRotationOptions2Processor(KeyboardRotationOptions options, EventsSystem eventsSystem){
		this.options = options;
		eventsSystem.addProcessor(this);
	}
	
	@Override
	public void execute(SetKeyboardRotationOptions2 optionsToSet)
			throws MustBeCalledInsideATransaction {
		options.setSupressShakingDialog(optionsToSet.supressDialogs);
		options.setRemoteIntegration(optionsToSet.remoteSystemIntegration);
	}

	@Override
	public Class<? extends Serializable> eventType() {
		return SetKeyboardRotationOptions2.class;
	}

	@Override
	public void start() {
	}

	@Override
	public void stop() {
	}

}
