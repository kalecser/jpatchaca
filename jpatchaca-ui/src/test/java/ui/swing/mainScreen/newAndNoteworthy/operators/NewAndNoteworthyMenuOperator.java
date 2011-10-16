package ui.swing.mainScreen.newAndNoteworthy.operators;

import java.awt.Component;

import javax.swing.JMenuItem;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JMenuOperator;

public class NewAndNoteworthyMenuOperator {

	private JMenuOperator newAndNoteworthy;

	public NewAndNoteworthyMenuOperator(JFrameOperator frameOperator) {
		newAndNoteworthy = new JMenuOperator(frameOperator, "New and noteworthy");
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
