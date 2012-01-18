package labels;

import labels.labels.LabelsHome;
import labels.labels.LabelsHomeImpl;
import labels.processors.SetLabelToMultipleTasksProcessor;

import org.junit.Assert;
import org.junit.Test;

import core.ObjectIdentity;

import periodsInTasks.MockTask;
import tasks.tasks.Tasks;
import events.Processor;
import events.SetLabelToMultipleTasks;
import events.persistence.MustBeCalledInsideATransaction;

public class SetLabelToMultipleTasksTest {

	private final Tasks tasks = new Tasks();
	
	@Test
	public void onExecute_ShouldSetLabelToMultipleTasks() throws MustBeCalledInsideATransaction{
		LabelsHome labelsHome = new LabelsHomeImpl();
		Processor<SetLabelToMultipleTasks> subject = new SetLabelToMultipleTasksProcessor(labelsHome, tasks);
		SetLabelToMultipleTasks event = 
				new SetLabelToMultipleTasks("labelName", 
						createTask("foo"),
						createTask("bar")
				);
		subject.execute(event);
		
		Assert.assertEquals("[foo, bar]", labelsHome.getTasksInLabel("labelName").toString());
	}

	private MockTask createTask(String name) throws MustBeCalledInsideATransaction {
		MockTask result = new MockTask(name);
		tasks.add(new ObjectIdentity(name), result);
		return result;
	}
	
}
