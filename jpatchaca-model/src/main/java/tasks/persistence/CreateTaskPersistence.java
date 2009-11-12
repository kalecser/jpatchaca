package tasks.persistence;

import org.picocontainer.Startable;

import basic.Delegate;
import basic.IdProvider;

import tasks.delegates.CreateTaskDelegate;
import tasks.home.TaskData;
import events.CreateTaskEvent3;
import events.EventsConsumer;

public class CreateTaskPersistence implements Startable{

	private final CreateTaskDelegate delegate;
	private final EventsConsumer consumer;
	private final IdProvider provider;

	public CreateTaskPersistence(CreateTaskDelegate delegate, EventsConsumer consumer, IdProvider provider) {
				this.delegate = delegate;
				this.consumer = consumer;
				this.provider = provider;
	}

	@Override
	public void start() {
		delegate.addListener(new Delegate.Listener<TaskData>() {
			@Override
			public void execute(TaskData object) {
				consumer.consume(
						new CreateTaskEvent3(
								provider.provideId(), 
								object.getTaskName(), 
								object.getBudget(), 
								object.getLabel()));
			}
		});
	}

	@Override
	public void stop() {
		
	}

}
