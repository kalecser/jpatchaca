package events.eventslist;

public class ErrorExecutingTransaction extends Error {
	private static final long serialVersionUID = 1L;

	public ErrorExecutingTransaction(Error cause){
		super(cause);
	}
}
