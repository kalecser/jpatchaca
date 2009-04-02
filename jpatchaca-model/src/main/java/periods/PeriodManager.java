package periods;

import java.util.List;





public interface PeriodManager {
	public void addPeriod(Period period);
	public void removePeriod(Period period);
	public List<Period> periods();
	public Period period(int index);
	public void addListener(PeriodsListener listener);
	public void removeListener(PeriodsListener listener);
	public Long totalTime();
	
	
}
