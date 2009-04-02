package ui.swing.mainScreen.test;

import junit.framework.Assert;

import org.junit.Test;

import ui.swing.mainScreen.DeferredTaskListMemory;
import ui.swing.mainScreen.TaskListMemory;
import ui.swing.mainScreen.TasksListData;
import wheel.io.files.impl.tranzient.TransientDirectory;
import basic.DeferredExecutor;


public class TaskListMemoryTest {

	@Test
	public void testSaveRetrieveData() throws InterruptedException{
				
		DeferredExecutor.makeSynchronous();
		
		final TransientDirectory directory = new TransientDirectory();
		final TaskListMemory memory = new DeferredTaskListMemory(directory);
		memory.mind(new TasksListData());
		memory.mind(new TasksListData(1,2));
		
		Assert.assertEquals(1, memory.retrieve().getSelectedTask());
		Assert.assertEquals(2, memory.retrieve().getSelectedLabel());
		
		Thread.sleep(300);
		final TaskListMemory secondMemory = new DeferredTaskListMemory(directory);
		Assert.assertEquals(1, secondMemory.retrieve().getSelectedTask());
		Assert.assertEquals(2, secondMemory.retrieve().getSelectedLabel());
		
	}

}
