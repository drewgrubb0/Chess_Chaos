package d_utils;

/**
 * A timer that uses system time to keep track of the passing of time.
 * 
 * Doesn't truly keep track of time but rather keeps track of the amount of time that has passed
 * since the creation of the DTimer. 
 * 
 * @author Drew Grubb
 */
public class DTimer
{
	private long referenceTime;
	private long lastKnownTime;
	
	private int nowTime;
	private int numMinutes;
	private int numSeconds;
	
	private boolean paused;
	
	/**
	 * Creates new Timer
	 */
	public DTimer()
	{
		nowTime = 0;
		this.paused = true;
	}
	
	/**
	 * Initiates the System time from which this timer
	 * will be referenced to.
	 */
	public void startTimer()
	{
		referenceTime = System.currentTimeMillis();
		this.paused = false;
	}
	
	/**
	 * Initiates the System time from which this timer
	 * will be referenced to, but also starts the timer
	 * at a specific time based on the passed in refTime.
	 *
	 * Useful for the saving and resuming of a timer across program shutdowns.
	 *
	 * @param refTime 
	 */
	public void startTimer(long refTime)
	{
		referenceTime = System.currentTimeMillis() - refTime;
		this.paused = false;
	}
	
	/**
	 * Gets the current time in reference to starting time
	 * @return
	 */
	public long getTime()
	{
		if(paused)
			return lastKnownTime;
		
		lastKnownTime = System.currentTimeMillis() - referenceTime;
		return System.currentTimeMillis() - referenceTime;
	}
	
	/**
	 * Pauses counting the Timer
	 */
	public void pauseTimer()
	{
		this.paused = true;
	}
	
	/**
	 * Resumes counting the Timer after a pause.
	 * Requires changing the System time due to the nature of a pause
	 */
	public void resumeTimer()
	{
		referenceTime = System.currentTimeMillis() - lastKnownTime;
		this.paused = false;
	}
	
	/**
	 * Resets the timer by calling startTimer
	 * Purely for readability
	 */
	public void resetTimer()
	{
		startTimer();
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isPaused()
	{
		return paused;
	}
	
	/**
	 * Returns the current time on the timer in Minutes : Seconds
	 * Updates the time on every toString call
	 */
	public String toString()
	{
		nowTime = (int) (getTime() / 1000);
		numSeconds = nowTime % 60;
		numMinutes = nowTime / 60;
		
		//Adds extra 0 to 0-9 to keep format consistent
		if(numMinutes < 10 && numSeconds < 10)
			return "0" + numMinutes + " : 0" + numSeconds;
		if(numSeconds < 10)
			return "" + numMinutes + " : 0" + numSeconds;
		if(numMinutes < 10)
			return "0" + numMinutes + " : " + numSeconds;
		
		return "" + numMinutes + " : " + numSeconds;
	}

}
