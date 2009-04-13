package ui.swing.presenter;

public class ValidationException extends Exception {
	private static final long serialVersionUID = 1L;
	private final String userMessage;

	public ValidationException(String userMessage) {
		this.userMessage = userMessage;
	}

	public String getUserMessage() {
		return userMessage;
	}

}
