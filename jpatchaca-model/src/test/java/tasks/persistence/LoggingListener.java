package tasks.persistence;

import java.util.ArrayList;
import java.util.List;

import basic.Delegate.Listener;


public class LoggingListener<T> implements Listener<T>{
	public List<T> events = new ArrayList<T>();

	@Override
	public void execute(T object) {
		events.add(object);
	}
}
