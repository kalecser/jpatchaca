package main;

import java.io.IOException;

import junit.framework.TestCase;

import org.picocontainer.MutablePicoContainer;

public class BasicTest extends TestCase {

	public void testIfSystemTurnsOn() throws IOException{
		final MutablePicoContainer container = DurableSWINGContainerBuilder.startDurableSWINGContainer();
		container.stop();
	}
}
