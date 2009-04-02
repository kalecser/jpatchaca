package wheel.io;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;


public class Jars {

	public static void runAllowingForClassGC(URL jar, String classToInstantiate) throws Exception {
		final URLClassLoader loader = createGarbageCollectableClassLoader(jar);
		loader.loadClass(classToInstantiate).newInstance();
	}
	
	
	private static URLClassLoader createGarbageCollectableClassLoader(URL jar) throws Exception {
		return new URLClassLoader(new URL[]{jar}, bootstrapClassLoader());
	}
	
	
	private static ClassLoader bootstrapClassLoader() {
		ClassLoader candidate = ClassLoader.getSystemClassLoader();
		while (candidate.getParent() != null) candidate = candidate.getParent();
		return candidate;
	}


	public static URL jarGiven(Class<?> clazz) {
		final URL url = clazz.getResource(clazz.getSimpleName() + ".class");
		final String fullPath = url.getPath();
		final String path = fullPath.substring(0, fullPath.indexOf("!"));
		try {
			return new URL(path);
		} catch (final MalformedURLException e) {
			throw new IllegalStateException(e); 
		}
	}
	

}
