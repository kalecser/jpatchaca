package ui.swing.mainScreen;

import java.io.IOException;

import javax.swing.UnsupportedLookAndFeelException;

import main.Main;
import tasks.adapters.ui.operators.MainScreenOperator;
import jira.WorkLogger;

public class WorkLoggerUsingSwing implements WorkLogger {

	@Override
	public void logWork() {
		new MainScreenOperator().sendTodaysWorkLog();
	}
	
	public static void main(String[] args) throws UnsupportedLookAndFeelException, IOException {
		Main.main(new String[]{});
		new WorkLoggerUsingSwing().logWork();
	}

}
