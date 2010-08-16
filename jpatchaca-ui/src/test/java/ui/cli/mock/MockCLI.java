package ui.cli.mock;

import ui.commandLine.CommandLineInterface;

public class MockCLI implements CommandLineInterface {

	@Override
	public String command(String command) {
		return String.format("Command %s received", command);
	}

}
