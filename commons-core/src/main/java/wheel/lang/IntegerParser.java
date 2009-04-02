package wheel.lang;

import wheel.lang.exceptions.IllegalParameter;

public class IntegerParser implements Consumer<String> {

	private final Consumer<Integer> _endConsumer;

	public IntegerParser(Consumer<Integer> endConsumer) {
		_endConsumer = endConsumer;
	}

	
	public void consume(String string) throws IllegalParameter {
		int result = 0;
		
		try {
			result = Integer.parseInt(string);
		} catch (final NumberFormatException e) {
			throw new IllegalParameter(string + " is not a valid number.");
		}
		
		_endConsumer.consume(result);
	}

}
