package ui.swing.options;

import org.netbeans.jemmy.operators.JDialogOperator;

public class OptionsScreenOperator {
	
	@SuppressWarnings("unused")
	private final JDialogOperator frame;

	public OptionsScreenOperator(){
		frame = new JDialogOperator("Options");
	}

}
