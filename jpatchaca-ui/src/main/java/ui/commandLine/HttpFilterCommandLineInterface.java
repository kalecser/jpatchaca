package ui.commandLine;

public class HttpFilterCommandLineInterface implements CommandLineInterface {

	private final CommandLineInterface delegate;

	public HttpFilterCommandLineInterface(CommandLineInterface delegate) {
		this.delegate = delegate;
	}

	@Override
	public String command(String command) {
		String filtered = command.replaceAll("GET /", "");
		filtered = filtered.replaceAll(" HTTP/1.1", "");
		filtered = filtered.replaceAll("%5B", "[");
		filtered = filtered.replaceAll("%5D", "]");
		filtered = filtered.replaceAll("%20", " ");
		return delegate.command(filtered);
	}

}
