/**
 * 
 */
package periods;



public interface PeriodsListener{
	public void periodAdded(Period period);
	public void periodRemoved(Period period);
	public void clean();
}