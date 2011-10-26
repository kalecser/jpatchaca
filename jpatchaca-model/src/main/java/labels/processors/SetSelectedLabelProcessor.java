package labels.processors;

import java.io.Serializable;

import events.Processor;
import events.SelectLabelEvent;


public class SetSelectedLabelProcessor implements Processor<SelectLabelEvent> {

	
	public SetSelectedLabelProcessor(){	
	}
	
	@Override
	public Class<? extends Serializable> eventType() {
		return SelectLabelEvent.class;
	}

	@Override
	public void execute(SelectLabelEvent eventObj) {
		//does nothing...
	}

}
