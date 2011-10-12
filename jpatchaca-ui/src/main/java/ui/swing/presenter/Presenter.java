package ui.swing.presenter;

import javax.swing.JDialog;
import javax.swing.JFrame;

import org.reactive.Signal;

import lang.Maybe;

public interface Presenter {

	public abstract void setMainScreen(final JFrame mainScreen);

	public abstract JDialog showOkCancelDialog(final ActionPane pane,
			final String title);

	public abstract JDialog showDialog(final JDialog dialog);

	public abstract void showShakingMessageWithTitle(String message,
			String title);

	public abstract void closeAllWindows();

	public abstract void showYesNoFloatingWindow(final String caption,
			final UIAction action) throws ValidationException;

	public abstract void showMessageBalloon(final String message);
	
	public abstract void showNotification(String message);
	
	public Signal<String> notification();

	public abstract void start();

	public abstract void stop();

	public abstract Maybe<JFrame> getMainScreen();

}