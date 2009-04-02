package main;

import java.io.IOException;

import junit.framework.TestCase;
import main.Main;

import org.picocontainer.MutablePicoContainer;

public class BasicTest extends TestCase {

	public void testIfSystemTurnsOn() throws IOException{
		final MutablePicoContainer container = Main.createDurableSWINGContainer();
		container.start();
		container.stop();
		
	}
}
