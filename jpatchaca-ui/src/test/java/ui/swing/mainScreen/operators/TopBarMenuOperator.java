package ui.swing.mainScreen.operators;

import java.awt.Component;

import javax.swing.JMenuItem;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JMenuOperator;

public class TopBarMenuOperator {

	private JMenuOperator task;
	private JMenuOperator newAndNoteworthy;

	public TopBarMenuOperator(JFrameOperator frameOperator) {
		task = new JMenuOperator(frameOperator);
		newAndNoteworthy = new JMenuOperator(frameOperator, "New and noteworthy");
	}

	public void pushStartTaskmenu() {
		task.pushMenu("Task|Start task");
	}

	public void waitAlerIconToShowOnNewandNoteworthy() {
		newAndNoteworthy.waitState(new ComponentChooser() {
			
			@Override
			public String getDescription() {
				return "Waiting for alert icon to show";
			}
			
			@Override
			public boolean checkComponent(Component comp) {
				JMenuItem menuItem = (JMenuItem) comp;
				boolean alerticonTooltip = "new".equals(menuItem.getToolTipText());
				return alerticonTooltip;
			}
		});
	}

	public void readNewAndNoteworthy() {
		newAndNoteworthy.pushMenu("New and noteworthy|read");
	}

	public void waitAlerIconToVanishOnNewandNoteworthy() {
		newAndNoteworthy.waitState(new ComponentChooser() {
			
			@Override
			public String getDescription() {
				return "Waiting for alert icon to vanish";
			}
			
			@Override
			public boolean checkComponent(Component comp) {
				JMenuItem menuItem = (JMenuItem) comp;
				boolean alerticonTooltip = "".equals(menuItem.getToolTipText());
				return alerticonTooltip;
			}
		});
	}

}
