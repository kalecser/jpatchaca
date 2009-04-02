package basic;

import java.text.DecimalFormat;

public class FormatterImpl implements Formatter {

	private final DecimalFormat decimalFormat;

	public FormatterImpl(){
		decimalFormat = new DecimalFormat();		
	}
	
	public String formatNumber(Double number) {
		return decimalFormat.format(number);
	}

}
