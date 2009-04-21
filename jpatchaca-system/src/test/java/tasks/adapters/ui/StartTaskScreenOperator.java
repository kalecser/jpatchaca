package tasks.adapters.ui;

import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JDialogOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;

public class StartTaskScreenOperator {

	private final JDialogOperator startTaskScreen;

	public StartTaskScreenOperator() {
		startTaskScreen = new JDialogOperator("Start task");
	}

	public void startTaskHalfAnHourAgo(final String taskName) {
		new JTextFieldOperator(startTaskScreen).typeText(taskName
				+ " 30 minutes ago");
		new JButtonOperator(startTaskScreen, "ok").push();

	}

}
