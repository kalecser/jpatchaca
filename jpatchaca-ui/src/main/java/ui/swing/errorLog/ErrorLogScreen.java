package ui.swing.errorLog;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import lang.Maybe;

import org.reactive.Receiver;

import basic.ErrorLog;

import ui.swing.presenter.Presenter;
import ui.swing.presenter.PresenterImpl;
import ui.swing.utils.UIEventsExecutorImpl;

public class ErrorLogScreen  {

	private final Presenter presenter;
	private final JTextArea errorTextArea;

	private JDialog errorLog;

	public ErrorLogScreen(final Presenter presenter, final ErrorLog errorLog) {
		this.presenter = presenter;
		errorTextArea = new JTextArea("empty log");
		monitorErrorLog(errorLog);
	}


	private void monitorErrorLog(ErrorLog errorLog) {
		errorLog.errorLog.addReceiver(new Receiver<String>(){
			@Override
			public void receive(String value) {
				errorTextArea.setText(value);
			}
		});
	}


	public void show() {
		if (errorLog == null){
			errorLog = createErrorLogDialog();
		}
		presenter.showDialog(errorLog);
	}

	private JFrame getMainScreenOrCry(Presenter presenter) {
		Maybe<JFrame> maybeMainScreen = presenter.getMainScreen();
		
		if(maybeMainScreen == null)
			throw new IllegalStateException("Main screen must be set to show Error Log screen.");
		
		JFrame unbox = maybeMainScreen.unbox();
		return unbox;
	}
	
	
	private JDialog createErrorLogDialog() {
		JFrame mainScreen = getMainScreenOrCry(presenter);
		
		JDialog errorLog = new JDialog(mainScreen,"Error Log");
		errorLog.setLayout(new BorderLayout());
		errorTextArea.setEditable(false);
		errorLog.add(new JScrollPane(errorTextArea), BorderLayout.CENTER);
		errorLog.setPreferredSize(new Dimension(600,337));
		errorLog.pack();
		
		return errorLog;
	}


	public static void main(String[] args) {
		Presenter presenter = new PresenterImpl(new UIEventsExecutorImpl(null));
		presenter.setMainScreen(new JFrame());
		ErrorLogScreen errorLogScreen = new ErrorLogScreen(presenter, new ErrorLog());
		
		
		errorLogScreen.show();
		new Exception().printStackTrace();
		new Exception().printStackTrace();
	}
}
