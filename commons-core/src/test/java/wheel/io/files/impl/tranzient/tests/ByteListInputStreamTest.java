package wheel.io.files.impl.tranzient.tests;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import wheel.io.files.impl.Closeable;
import wheel.io.files.impl.tranzient.ByteListInputStream;

public class ByteListInputStreamTest  extends CloseableStreamTest  {

	public void testByteListInputStream() throws IOException{
		final ArrayList<Byte> bytesList = new ArrayList<Byte>();
		final ByteListInputStream byteListInputStream = new ByteListInputStream(bytesList);
		
		final InputStream stream = byteListInputStream;

		bytesList.add((byte)10);
		bytesList.add((byte)-1);
		
		
		assertEquals((byte)10, stream.read());
		final int unsigned = -1 & 0xFF;
		assertEquals(unsigned, stream.read());
		
		
		assertEquals(-1, stream.read()); //EOF
		
		bytesList.add((byte)2);
		assertEquals(2, stream.read());
	}

	@Override
	protected Closeable createSubject() {
		return new ByteListInputStream(new ArrayList<Byte>());
	}
}
