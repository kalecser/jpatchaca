package wheel.lang;

import wheel.lang.exceptions.IllegalParameter;

public interface Consumer<VO> {
	
	void consume(VO valueObject) throws IllegalParameter;

}
