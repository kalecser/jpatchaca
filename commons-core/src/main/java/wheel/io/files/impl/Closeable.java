package wheel.io.files.impl;

import java.io.IOException;

public interface Closeable {
	
	void close() throws IOException;

	void notifyOnClose(Listener listener);
	public interface Listener {
		void streamClosed(Closeable stream);
	}
	
}
