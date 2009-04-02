package wheel.io.files.impl.tranzient;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import wheel.io.files.impl.Closeable;

public class ByteListInputStream extends InputStream implements Closeable {

	private final List<Byte> _contents;
	private int _position = 0;
	private Listener _listener;

	public ByteListInputStream(List<Byte> contents) {
		_contents = contents;
	}

	@Override
	public int read() {
		if (_contents.size() <= _position)	return -1;
		return (_contents.get(_position++)).byteValue() & 0xFF;
		
	}

	@Override
	public void close() throws IOException {
		_listener.streamClosed(this);
		super.close();
	}

	public void notifyOnClose(Listener listener) {
		_listener = listener;
	}
	
}
