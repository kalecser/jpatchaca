package main;

import main.singleInstance.AssureSingleInstance;
import model.PatchacaModelContainerFactory;

import org.picocontainer.MutablePicoContainer;

import wheel.io.files.impl.tranzient.TransientDirectory;
import basic.PatchacaDirectory;
import basic.durable.DoubleIdProvider;
import basic.mock.MockHardwareClock;
import basic.mock.MockIdProvider;

public class TransientNonUIContainer {

	private final MutablePicoContainer container;

	public TransientNonUIContainer() {
		container = new PatchacaModelContainerFactory().create(new MockHardwareClock());

		container.removeComponent(PatchacaDirectory.class);
		container.removeComponent(DoubleIdProvider.class);
		container.addComponent(new TransientDirectory());
		container.addComponent(new MockIdProvider());
		
		
		container.removeComponent(AssureSingleInstance.class);
		
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

	public void start() {
		container.start();
		
	}

	public void addComponent(Class<?> component) {
		container.addComponent(component);
	}

}
