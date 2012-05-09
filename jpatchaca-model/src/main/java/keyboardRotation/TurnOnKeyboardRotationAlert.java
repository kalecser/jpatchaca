package keyboardRotation;

import basic.AlertImpl;
import basic.Subscriber;

public class TurnOnKeyboardRotationAlert {

	AlertImpl alert = new AlertImpl();

	public void register(Subscriber s){
		alert.subscribe(s);
	}
	
	public void fire(){
		alert.fire();
	}
	
}
