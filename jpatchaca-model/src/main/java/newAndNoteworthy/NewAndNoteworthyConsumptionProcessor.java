package newAndNoteworthy;

import java.io.Serializable;

import events.NewAndNoteworthyConsumed;
import events.Processor;
import events.persistence.MustBeCalledInsideATransaction;

public class NewAndNoteworthyConsumptionProcessor implements Processor<NewAndNoteworthyConsumed>{

	private final NewAndNoteworthyImpl newAndNoteworthy;

	public NewAndNoteworthyConsumptionProcessor(NewAndNoteworthyImpl newAndNoteworthy){
		this.newAndNoteworthy = newAndNoteworthy;
	}
	
	@Override
	public void execute(NewAndNoteworthyConsumed eventObj)
			throws MustBeCalledInsideATransaction {
		newAndNoteworthy.setLastConsumedTextHash(eventObj.hash);
	}

	@Override
	public Class<? extends Serializable> eventType() {
		return NewAndNoteworthyConsumed.class;
	}

}
