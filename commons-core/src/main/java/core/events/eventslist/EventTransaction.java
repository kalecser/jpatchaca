package core.events.eventslist;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class EventTransaction implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final Long time;
	private final Serializable event;

	public EventTransaction(Long time, Serializable event){
		this.time = time;
		this.event = event;		
	}

	public final Serializable getEvent() {
		return event;
	}

	public final Long getTime() {
		return time;
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this); 
	}
	
	
}
