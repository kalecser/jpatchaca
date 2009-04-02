package wheel.lang;

public class Casts {

	@SuppressWarnings("unchecked")
	public static <T> T uncheckedGenericCast(Object object) {
		return (T)object;
	}

}
