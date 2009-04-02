//Copyright (C) 2004 Klaus Wuestefeld
//This is free software. It is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the license distributed along with this file for more details.
//Contributions: Alexandre Nodari.

package wheel.io.network;

import java.io.Closeable;
import java.io.IOException;


public interface ObjectSocket extends Closeable {

	void writeObject(Object object) throws IOException;

	Object readObject() throws IOException, ClassNotFoundException;

}
