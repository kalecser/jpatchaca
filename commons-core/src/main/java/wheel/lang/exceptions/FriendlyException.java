package wheel.lang.exceptions;

public class FriendlyException extends Exception {

	private final String _help;

	public FriendlyException(String message, String help) {
		super(message);
		_help = help;
	}


	public String getHelp() {
		return _help;
	}

	private static final long serialVersionUID = 1L;
}
