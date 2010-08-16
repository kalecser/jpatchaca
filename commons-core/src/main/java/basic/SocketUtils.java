package basic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SocketUtils {

	public static String readLine(final Socket socket) throws IOException {
		final InputStream inputStream = socket.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		return reader.readLine();
	}

	public static void writeLine(final String command, final Socket socket) throws IOException {
		final OutputStream outputStream = socket.getOutputStream();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
		writer.write(command + "\n");
		writer.flush();
	}

}
