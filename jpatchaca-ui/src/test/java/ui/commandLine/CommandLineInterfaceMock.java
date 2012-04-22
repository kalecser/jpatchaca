package ui.commandLine;

class CommandLineInterfaceMock implements CommandLineInterface {

	private String command;

	@Override
	public String command(String command) {
		this.command = command;
		return "";
	}

	public String getCommand() {
		return command;
	}

}
