package newAndNoteworthy;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.UnhandledException;
import org.reactive.Signal;
import org.reactive.Source;

import events.EventsConsumer;
import events.NewAndNoteworthyConsumed;

public class NewAndNoteworthyImpl implements NewAndNoteworthy {

	private final EventsConsumer eventsConsumer;
	private final Source<Boolean> hasUnreadNewAndNoteworthy = new Source<Boolean>(true);

	public NewAndNoteworthyImpl(EventsConsumer eventsConsumer) {
		this.eventsConsumer = eventsConsumer;
	}

	@Override
	public Signal<Boolean> hasUnreadNewAndNoteworthy() {
		return hasUnreadNewAndNoteworthy ;
	}
	
	public void setLastConsumedTextHash(int hash){
		if (getTextOrCry().hashCode() == hash){
			hasUnreadNewAndNoteworthy.supply(false);
		} else {
			hasUnreadNewAndNoteworthy.supply(true);
		}
	}

	@Override
	public String getTextAndMarkAsRead() {
		String textOrCry = getTextOrCry();
		
		int hash = textOrCry.hashCode();
		eventsConsumer.consume(new NewAndNoteworthyConsumed(hash));
		
		return textOrCry;
	}

	private String getTextOrCry() {
		try {
			return IOUtils.toString(NewAndNoteworthyImpl.class.getResourceAsStream("/newAndNoteworthy.txt"));
		} catch (IOException e) {
			throw new UnhandledException(e);
		}
	}

}
