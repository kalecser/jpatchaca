package keyboardRotation;

import java.io.Serializable;

import org.picocontainer.Startable;

import events.EventsSystem;
import events.Processor;
import events.SetKeyboardRotationOptions;
import events.persistence.MustBeCalledInsideATransaction;

public class SetKeyboardRotationOptionsProcessor implements Processor<SetKeyboardRotationOptions>, Startable{

	private KeyboardRotationOptions options;

	public SetKeyboardRotationOptionsProcessor(KeyboardRotationOptions options, EventsSystem eventsSystem){
		this.options = options;
		eventsSystem.addProcessor(this);
	}
	
	@Override
	public void execute(SetKeyboardRotationOptions optionsToSet)
			throws MustBeCalledInsideATransaction {
		options.setSupressShakingDialog(optionsToSet.supressShakingDialog());
	}

	@Override
	public Class<? extends Serializable> eventType() {
		return SetKeyboardRotationOptions.class;
	}

	@Override
	public void start() {
	}

	@Override
	public void stop() {
	}

}
