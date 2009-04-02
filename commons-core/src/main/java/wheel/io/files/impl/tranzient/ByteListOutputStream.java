package wheel.io.files.impl.tranzient;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import wheel.io.files.impl.Closeable;

public class ByteListOutputStream extends OutputStream implements Closeable {

	private final List<Byte> _contents;
	private Listener _closeListener;


	public ByteListOutputStream(List<Byte> contents) {
		_contents = contents;
	}


	@Override
	public void write(int b) {
		//TODO Optimization: Use a ByteArrayOutputStream and connect the input streams to it.
		_contents.add((byte)b);
	}


	@Override
	public void close() throws IOException {
		_closeListener.streamClosed(this);
		super.close();
	}


	public void notifyOnClose(Listener listener) {
		_closeListener = listener;		
	}
	
	
}
