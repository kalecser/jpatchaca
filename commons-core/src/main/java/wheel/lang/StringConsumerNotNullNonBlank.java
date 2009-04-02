package wheel.lang;

import wheel.lang.exceptions.IllegalParameter;

public class StringConsumerNotNullNonBlank implements Consumer<String> {

	private final String _friendlyName;
	private final Consumer<String> _endConsumer;

	public StringConsumerNotNullNonBlank(String friendlyName,	Consumer<String> endConsumer) {
		_friendlyName = friendlyName;
		_endConsumer = endConsumer;
	}

	@Override
	public void consume(String valueObject) throws IllegalParameter {
		if (valueObject == null)
			throw new IllegalParameter(_friendlyName + " cannot be null.");
		
		if (valueObject.equals(""))
			throw new IllegalParameter(_friendlyName + " cannot be blank.");
		
		_endConsumer.consume(valueObject);

	}

}
