package ui.commandLine;


public class CommandLineInterface {

	private final WorkLogger workLogger;
	
	public CommandLineInterface(WorkLogger workLogger) {
		this.workLogger = workLogger;
	}

	public String command(final String command){
		
		if(command.equals("sendWorklog")){
			return sendWorklog();
		}
		
		return "Invalid command: "+command;
	}

	private String sendWorklog() {
		try{
			workLogger.logWork();
		}catch (Exception e) {
			e.printStackTrace();
			return "Worklog not sent. Error: "+ e.getMessage();
		}
		
		return "Worklog sent";
	}
}
