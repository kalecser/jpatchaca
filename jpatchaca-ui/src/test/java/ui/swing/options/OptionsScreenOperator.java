package ui.swing.options;

import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JDialogOperator;

public class OptionsScreenOperator {
	
	private final JDialogOperator frame;

	public OptionsScreenOperator(){
		frame = new JDialogOperator("Options");
	}

	public void ok() {
		final JButtonOperator button = new JButtonOperator(frame);
		button.push();
	}

}
