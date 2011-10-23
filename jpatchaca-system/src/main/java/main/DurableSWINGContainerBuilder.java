package main;

import org.picocontainer.MutablePicoContainer;

import basic.durable.HardwareClockImpl;

final class DurableSWINGContainerBuilder {

	static MutablePicoContainer startDurableSWINGContainer() {
		final MutablePicoContainer container = createDurableSWINGContainer();
		container.start();
		return container;
	}

	private static MutablePicoContainer createDurableSWINGContainer() {
		final MutablePicoContainer container;
		container = SwingContainerBuilder
				.createSwingContainer(new HardwareClockImpl());
		return container;
	}

}