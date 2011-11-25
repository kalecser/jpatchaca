package wheel.lang;

import wheel.lang.exceptions.IllegalParameter;

public class IntegerConsumerBoundaries implements Consumer<Integer> {

	private final Consumer<Integer> _endConsumer;
	private final int _min;
	private final int _max;
	private final String _friendlyName;

	public IntegerConsumerBoundaries(String friendlyName, Consumer<Integer> endConsumer, int min, int max) {
		_friendlyName = friendlyName;
		_endConsumer = endConsumer;
		_min = min;
		_max = max;
	}

	@Override
	public void consume(Integer value) throws IllegalParameter {
		if (value < _min || value > _max) throw new IllegalParameter("" + _friendlyName + " must be no less than " + _min + " and no more than " + _max + ".");
		_endConsumer.consume(value);
	}

}
