package d_utils;

/**
 * A class that uses DTimer methods to keep track of time, but in a Countdown fashion.
 * The timer is started at a set time, and counts down until it reaches zero.
 * 
 * Still has the same functionalities and responsibilities as DTimer.
 * @see DTimer
 * 
 * @author Drew Grubb
 */
public class DCountdownTimer extends DTimer
{
	long numMilliseconds;
	long timeRemaining;

	/**
	 * Creates a new CountdownTimer
	 * @param numMilliseconds the number of milliseconds this countDownTimer should start at
	 */
	public DCountdownTimer(long numMilliseconds)
	{
		this.numMilliseconds = numMilliseconds;
		this.timeRemaining = numMilliseconds;
	}
	
	@Override
	public long getTime()
	{	
		if(timeRemaining <= 0)
			return 0;
		
		timeRemaining = numMilliseconds - super.getTime();
		return numMilliseconds - super.getTime();
	}
	
	/**
	 * @return has this timer hit zero
	 */
	public boolean isCompleted()
	{
		return timeRemaining > 0;
	}
	
	/**
	 * @return the number that the countdown timer started at.
	 */
	public long getStartingTime()
	{
		return numMilliseconds;
	}

}
