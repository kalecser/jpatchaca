package statistics;

import java.util.Date;

public interface SummaryItem extends Comparable<SummaryItem>{
	Date date();
	String taskName();
	Double hours();
	String getFormatedDate();
	

}
