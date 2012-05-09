package keyboardRotation;

class NetworkMock implements Network {

	private StringBuilder commands = new StringBuilder();

	public String sentCommands() {
		return commands.toString().trim();
	}

	@Override
	public void sendTo(String command, String peer) {
		commands.append(command + " to " + peer + "\n");
	}

}
