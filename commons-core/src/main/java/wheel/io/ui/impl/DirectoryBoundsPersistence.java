package wheel.io.ui.impl;

import java.awt.Rectangle;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

import wheel.io.Log;
import wheel.io.Streams;
import wheel.io.files.Directory;
import wheel.io.ui.BoundsPersistence;

public class DirectoryBoundsPersistence implements BoundsPersistence {

	private static final String BOUNDS_FILE_NAME = ".bounds";
	private final Directory _directory;
	private final Map<String, Rectangle> _storedBounds;
	private final Map<String, Rectangle> _pendingBounds;

	public DirectoryBoundsPersistence(Directory directory) {
		_directory = directory;
		_storedBounds = getStoredBounds();
		_pendingBounds = new HashMap<String, Rectangle>();
	}

	@Override
	public synchronized Rectangle getStoredBounds(String id) {
		return _storedBounds.get(id);
	}

	@Override
	public synchronized void setBounds(String id, Rectangle bounds) {

		if (bounds == null)
			throw new InvalidParameterException();

		if (bounds.equals(getStoredBounds(id)))
			return;

		_pendingBounds.put(id, bounds);

	}

	@SuppressWarnings("unchecked")
	private Map<String, Rectangle> getStoredBounds() {
		if (!_directory.fileExists(BOUNDS_FILE_NAME))
			return new HashMap<String, Rectangle>();

		ObjectInputStream objectInputStream = null;
		try {
			objectInputStream = new ObjectInputStream(_directory
					.openFile(BOUNDS_FILE_NAME));
			final Object savedBounds = objectInputStream.readObject();
			if (savedBounds instanceof Map)
				return (Map<String, Rectangle>) savedBounds;

		} catch (final IOException e) {
			Log.log(e);
		} catch (final ClassNotFoundException e) {
			Log.log(e);
		} finally {
			if (objectInputStream != null)
				Streams.crash(objectInputStream);
		}

		return new HashMap<String, Rectangle>();
	}

	@Override
	public synchronized void store() {
		_storedBounds.putAll(_pendingBounds);
		ObjectOutputStream objectOutputStream = null;
		try {
			if (_directory.fileExists(BOUNDS_FILE_NAME))
				_directory.deleteFile(BOUNDS_FILE_NAME);

			objectOutputStream = new ObjectOutputStream(_directory
					.createFile(BOUNDS_FILE_NAME));
			objectOutputStream.writeObject(_storedBounds);
			objectOutputStream.flush();
		} catch (final IOException e) {
			Log.log(e);
		} finally {
			if (objectOutputStream != null)
				Streams.crash(objectOutputStream);
		}

	}

}
