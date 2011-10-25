package ui.swing.options;

import org.netbeans.jemmy.operators.JDialogOperator;

public class OptionsScreenOperator {
	
	private final JDialogOperator frame;

	public OptionsScreenOperator(){
		frame = new JDialogOperator("Options");
	}

}
