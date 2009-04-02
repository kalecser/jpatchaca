package wheel.io.serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Writes and reads objects using Java serialization. This serializer can be used for snapshots, journals or both.
 */
public class JavaSerializer implements Serializer {

	private final ClassLoader _loader;

	public JavaSerializer() {
		_loader = null;
	}

	public JavaSerializer(ClassLoader loader) {
		_loader = loader;
	}

	public void writeObject(OutputStream stream, Object object) throws IOException {
		final ObjectOutputStream objects = new ObjectOutputStream(stream);
		objects.writeObject(object);
		objects.close();
	}

	public Object readObject(InputStream stream) throws IOException, ClassNotFoundException {
		final ObjectInputStream objects = objectInputStreamFor(stream);
		final Object object = objects.readObject();
		objects.close();
		return object;
	}

	private ObjectInputStream objectInputStreamFor(InputStream stream) throws IOException {
		if (_loader == null) return new ObjectInputStream(stream);
		return new ObjectInputStreamWithClassLoader(stream, _loader);
	}

}
