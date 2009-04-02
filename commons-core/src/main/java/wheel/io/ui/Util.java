package wheel.io.ui;

public class Util {
	
	public static String correctSwingNewlineSpaceProblem(String proposition) {
		return " " + proposition.replaceAll("\\n", "\n ");
	}
	
}
