package main;

import model.PatchacaModelContainerFactory;

import org.picocontainer.MutablePicoContainer;

import wheel.io.files.impl.tranzient.TransientDirectory;
import basic.HardwareClock;
import basic.durable.HardwareClockImpl;

public final class TransientNonUIContainerBuilder {

public static MutablePicoContainer createTransientNonUIContainer() {
	return createTransientNonUIContainer(new HardwareClockImpl());
}

public static MutablePicoContainer createTransientNonUIContainer(
		final HardwareClock clock) {
	final MutablePicoContainer nonUIContainer = PatchacaModelContainerFactory.createNonUIContainer(clock);
	nonUIContainer.addComponent(new TransientDirectory());
	return nonUIContainer;
}

}