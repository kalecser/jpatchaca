package main;

import labels.LabelsSystem;
import labels.LabelsSystemImpl;

import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoBuilder;

import periods.impl.PeriodsFactoryImpl;
import periodsInTasks.impl.PeriodsInTasksSystemImpl;
import tasks.ActiveTask;
import tasks.TasksSystemImpl;
import tasks.delegates.CreateTaskDelegate;
import tasks.delegates.StartTaskDelegate;
import tasks.persistence.CreateAndStartTaskRegister;
import tasks.persistence.CreateTaskPersistence;
import tasks.persistence.CreateTaskProcessorRegister;
import tasks.persistence.StartTaskPersistence;
import tasks.persistence.StartTaskProcessor2Register;
import tasks.persistence.StartTaskProcessorRegister;
import tasks.processors.CreateAndStartTaskProcessor;
import tasks.processors.CreateTaskProcessor;
import tasks.processors.StartTaskProcessor;
import tasks.processors.StartTaskProcessor2;
import tasks.tasks.Tasks;
import tasks.tasks.TasksHomeImpl;
import wheel.io.files.impl.tranzient.TransientDirectory;
import basic.SystemClockImpl;
import basic.mock.MockHardwareClock;
import basic.mock.MockIdProvider;
import core.events.eventslist.TransientEventList;
import events.EventsSystemImpl;

public class TransientNonUIContainer {

	private static final long serialVersionUID = 1L;

	private final MutablePicoContainer container;

	public TransientNonUIContainer() {
		container = new PicoBuilder()
				.withLifecycle()
					.withCaching()
					.withConsoleMonitor()
					.build();

		container.addComponent(new TransientDirectory());
		container.addComponent(new MockIdProvider());
		container.addComponent(EventsSystemImpl.class);
		container.addComponent(PeriodsFactoryImpl.class);
		container.addComponent(TransientEventList.class);
		container.addComponent(MockHardwareClock.class);
		container.addComponent(SystemClockImpl.class);
		container.addComponent(ActiveTask.class);
		container.addComponent(Tasks.class);
		container.addComponent(TasksSystemImpl.class);
		container.addComponent(CreateAndStartTaskProcessor.class);
		container.addComponent(CreateAndStartTaskRegister.class);
		container.addComponent(StartTaskProcessorRegister.class);
		container.addComponent(StartTaskProcessor2Register.class);
		container.addComponent(StartTaskProcessor.class);
		container.addComponent(StartTaskProcessor2.class);
		container.addComponent(TasksHomeImpl.class);
		container.addComponent(CreateTaskProcessor.class);
		container.addComponent(CreateTaskProcessorRegister.class);
		container.addComponent(CreateTaskDelegate.class);
		container.addComponent(CreateTaskPersistence.class);
		container.addComponent(StartTaskDelegate.class);
		container.addComponent(StartTaskPersistence.class);
		container.addComponent(PeriodsInTasksSystemImpl.class);
		container.addComponent(LabelsSystem.class, LabelsSystemImpl.class);

		container.start();
	}

	public MockHardwareClock getMockHardwareClock() {
		return container.getComponent(MockHardwareClock.class);
	}

	public <T> T getComponent(final Class<T> class1) {
		return container.getComponent(class1);
	}

	public void stop() {
		container.stop();
	}

}
