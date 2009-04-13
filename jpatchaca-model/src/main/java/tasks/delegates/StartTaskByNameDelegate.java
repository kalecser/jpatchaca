package tasks.delegates;

import ui.swing.mainScreen.Delegate;
import basic.NonEmptyString;

public class StartTaskByNameDelegate extends Delegate<NonEmptyString>{

	public void starTask(NonEmptyString taskname){
		super.execute(taskname);
	}
	
}
