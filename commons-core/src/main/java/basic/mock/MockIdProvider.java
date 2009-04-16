package basic.mock;

import java.util.concurrent.atomic.AtomicInteger;

import core.ObjectIdentity;
import basic.IdProvider;

public class MockIdProvider implements IdProvider{

	private AtomicInteger taskId = new AtomicInteger(0);

	@Override
	public ObjectIdentity provideId() {
		return new ObjectIdentity(""+taskId.getAndAdd(1));
	}

	public synchronized void setNextId(String taskId) {
		this.taskId.set(Integer.valueOf(taskId));
		
	}

}
