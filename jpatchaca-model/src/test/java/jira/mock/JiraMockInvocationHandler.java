package jira.mock;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class JiraMockInvocationHandler implements InvocationHandler {

	private List<MethodCall> callLog = new LinkedList<MethodCall>();
	private Map<String, Object> resultMap = new HashMap<String, Object>();
	
	public void addResult(String methodName, Object result) {
		resultMap.put(methodName, result);
	}
	
	public MethodCall getMethodCall(int index){
		return callLog.get(index);
	}
	
	public String[] getLogAsStrings() {		
		String[] result = new String[callLog.size()];
		int index = 0; 
		for(MethodCall call: callLog)
			result[index++] = call.toString();
		return result;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

		callLog.add(new MethodCall(method, args));
		return resultMap.get(method.getName());
	}

}
