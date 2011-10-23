package main;

import model.PatchacaModelContainerFactory;

import org.picocontainer.MutablePicoContainer;

import basic.HardwareClock;

final class SwingContainerBuilder {

	static MutablePicoContainer createSwingContainer(final HardwareClock clock) {
		final MutablePicoContainer container = PatchacaModelContainerFactory
				.createNonUIContainer(clock);
		final MutablePicoContainer swingContainer = container
				.makeChildContainer();

		UIStuffBuilder.registerUIStuff(swingContainer);
		return container;
	}

}