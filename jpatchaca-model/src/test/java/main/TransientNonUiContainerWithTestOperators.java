package main;

import periods.adapters.PatchacaPeriodsOperatorUsingBusinessLayer;
import tasks.adapters.PatchacaTasksOperatorUsingBusinessLayer;

public class TransientNonUiContainerWithTestOperators extends TransientNonUIContainer{

	public TransientNonUiContainerWithTestOperators(){
		addComponent(PatchacaTasksOperatorUsingBusinessLayer.class);
		addComponent(PatchacaPeriodsOperatorUsingBusinessLayer.class);
	}
}
