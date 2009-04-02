package wheel.io.ui;


public interface TrayIcon {

	public interface Action {
		String caption();
		void run();
	}

	void addAction(Action action);
	
	void clearActions();

	void messageBalloon(String title, String message);

	void setDefaultAction(Action defaultAction);

}