package ui.swing.errorLog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import lang.Maybe;

import ui.swing.presenter.Presenter;
import ui.swing.utils.UIEventsExecutorImpl;

public class ErrorLogScreen {

	private final Presenter presenter;
	private final JTextArea errorTextArea;

	private JDialog errorLog;

	public ErrorLogScreen(Presenter presenter) {
		this.presenter = presenter;
		errorTextArea = new JTextArea("empty log");
		monitorSystemErr();
		
	}


	public void show() {
		if (errorLog == null){
			errorLog = createErrorLogDialog();
		}
		presenter.showDialog(errorLog);
	}


	private ByteArrayOutputStream monitorSystemErr() {
		ByteArrayOutputStream os = new ByteArrayOutputStream(){
			@Override
			public void write(byte[] b) throws IOException {
				reloadErrorLog(this);
				super.write(b);
			}
			
			@Override
			public synchronized void write(int b) {
				reloadErrorLog(this);
				super.write(b);
			}
			
			@Override
			public synchronized void write(byte[] b, int off, int len) {
				reloadErrorLog(this);
				super.write(b, off, len);
			}
			
			@Override
			public synchronized void writeTo(OutputStream out)
					throws IOException {
				reloadErrorLog(this);
				super.writeTo(out);
			}
		};
		
		System.setErr(new PrintStream(os));
		return os;
	}


	private void reloadErrorLog(ByteArrayOutputStream byteArrayOutputStream) {
		errorTextArea.setText(byteArrayOutputStream.toString());
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
		Presenter presenter = new Presenter(new UIEventsExecutorImpl(null));
		presenter.setMainScreen(new JFrame());
		ErrorLogScreen errorLogScreen = new ErrorLogScreen(presenter);
		
		
		errorLogScreen.show();
		new Exception().printStackTrace();
		new Exception().printStackTrace();
	}
}
