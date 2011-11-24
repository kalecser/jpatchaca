package jira.mock;

import java.lang.reflect.Method;
import java.util.Arrays;

public class MethodCall{
	private Method method;
	private Object[] args;
	
	public MethodCall(Method method, Object[] args) {
		this.method = method;
		this.args = args;
	}
	
	@Override
	public String toString() {
		return method.getName() + " " + Arrays.toString(args);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getArgument(Class<T> classe){
		for(Object arg: args)
			if(classe.isInstance(arg))
				return (T) arg;
		throw new IllegalArgumentException("No argument of type " + classe);
	}
}