package basic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.picocontainer.Startable;
import org.reactive.Source;

public class ErrorLog implements Startable{
	
	public final Source<String> errorLog = new Source<String>("");
	
	@Override
	public void start() {
		monitorSystemErr();
	}

	private void monitorSystemErr() {
		
		ByteArrayOutputStream os = new ByteArrayOutputStream(){
			@Override
			public void write(byte[] b) throws IOException {
				reloadErrorLog(this);
				System.out.write(b);
				super.write(b);
			}
			
			@Override
			public synchronized void write(int b) {
				reloadErrorLog(this);
				System.out.write(b);
				super.write(b);
			}
			
			@Override
			public synchronized void write(byte[] b, int off, int len) {
				reloadErrorLog(this);
				System.out.write(b, off, len);
				super.write(b, off, len);
			}
		};
		
		System.setErr(new PrintStream(os));
	}

	protected void reloadErrorLog(ByteArrayOutputStream byteArrayOutputStream) {
		errorLog.supply(byteArrayOutputStream.toString());
		
	}

	@Override
	public void stop() {}

}
