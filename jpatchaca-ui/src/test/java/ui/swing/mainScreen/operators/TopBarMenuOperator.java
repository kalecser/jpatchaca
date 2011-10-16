package ui.swing.mainScreen.operators;

import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JMenuOperator;

public class TopBarMenuOperator {

	private JMenuOperator task;

	public TopBarMenuOperator(JFrameOperator frameOperator) {
		task = new JMenuOperator(frameOperator);
	}

	public void pushStartTaskmenu() {
		task.pushMenu("Task|Start task");
	}

}
