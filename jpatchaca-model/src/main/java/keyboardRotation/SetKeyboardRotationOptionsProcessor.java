package keyboardRotation;

import java.io.Serializable;

import events.Processor;
import events.SetKeyboardRotationOptions;
import events.persistence.MustBeCalledInsideATransaction;

public class SetKeyboardRotationOptionsProcessor implements Processor<SetKeyboardRotationOptions>{

	private KeyboardRotationOptions options;

	public SetKeyboardRotationOptionsProcessor(KeyboardRotationOptions options){
		this.options = options;
	}
	
	@Override
	public void execute(SetKeyboardRotationOptions optionsToSet)
			throws MustBeCalledInsideATransaction {
		options.setSupressDialogs(optionsToSet.supressDialogs());
	}

	@Override
	public Class<? extends Serializable> eventType() {
		return SetKeyboardRotationOptions.class;
	}

}
