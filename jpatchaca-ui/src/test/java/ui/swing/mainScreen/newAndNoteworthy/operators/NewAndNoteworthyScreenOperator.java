package ui.swing.mainScreen.newAndNoteworthy.operators;

import org.junit.Assert;
import org.netbeans.jemmy.operators.JDialogOperator;
import org.netbeans.jemmy.operators.JTextAreaOperator;

public class NewAndNoteworthyScreenOperator {

	private JDialogOperator frame;

	public NewAndNoteworthyScreenOperator(){
		frame = new JDialogOperator("New and noteworthy");
	}
	
	public void waitVisible() {
		Assert.assertTrue(frame.isVisible());
	}

	public void waitText(String string) {
		JTextAreaOperator txtNewAndNoteworthy = new JTextAreaOperator(frame);
		txtNewAndNoteworthy.waitText(string);
	}

}
