package main;

import labels.LabelsSystemImpl;

import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoBuilder;

import periods.impl.PeriodsFactoryImpl;
import periodsInTasks.impl.PeriodsInTasksSystemImpl;
import tasks.TasksSystemImpl;
import tasks.delegates.CreateTaskDelegate;
import tasks.delegates.StartTaskDelegate;
import tasks.persistence.CreateTaskPersistence;
import tasks.persistence.StartTaskPersistence;
import tasks.tasks.Tasks;
import tasks.tasks.TasksHomeImpl;
import wheel.io.files.impl.tranzient.TransientDirectory;
import basic.mock.MockBasicSystem;
import basic.mock.MockHardwareClock;
import basic.mock.MockIdProvider;
import events.EventsSystemImpl;

public class TransientNonUIContainer {
	private static final long serialVersionUID = 1L;
	
	private final MockBasicSystem mockBasicSystem;
	private final MutablePicoContainer container;


	public TransientNonUIContainer() {
		container = new PicoBuilder()
			.withLifecycle()
			.withCaching()
			.withConsoleMonitor()
		.build();
		
		mockBasicSystem = new MockBasicSystem();
		container.addComponent(new TransientDirectory());
		container.addComponent(new MockIdProvider());
		container.addComponent(mockBasicSystem);
		container.addComponent(EventsSystemImpl.class);
		container.addComponent(PeriodsFactoryImpl.class);
		container.addComponent(Tasks.class);
		container.addComponent(TasksSystemImpl.class);
		container.addComponent(TasksHomeImpl.class);
		container.addComponent(CreateTaskDelegate.class);
		container.addComponent(CreateTaskPersistence.class);
		container.addComponent(StartTaskDelegate.class);
		container.addComponent(StartTaskPersistence.class);
		container.addComponent(PeriodsInTasksSystemImpl.class);
		container.addComponent(LabelsSystemImpl.class);
		
		container.start();
	}

	public MockHardwareClock getMockHardwareClock() {
		return mockBasicSystem.getMockHardwareClock();
	}

	
	public <T>  T getComponent(Class<T> class1) {
		return container.getComponent(class1);
	}

	public void stop() {
		container.stop();
	}

	


}
