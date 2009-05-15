package core.events.eventslist;

import java.io.Serializable;

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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventTransaction other = (EventTransaction) obj;
		if (event == null) {
			if (other.event != null)
				return false;
		} else if (!event.equals(other.event))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		return true;
	}
	
	
	
	
}
