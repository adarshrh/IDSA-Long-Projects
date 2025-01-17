/**
 * IDSA Long Project 3
 * Group members:
 * Adarsh Raghupati   axh190002
 * Akash Akki         apa190001
 * Keerti Keerti      kxk190012
 * Stewart cannon     sjc160330
 */

/** Timer class for roughly calculating running time of programs
 *  @author rbk
 *  Usage:  Timer timer = new Timer();
 *          timer.start();
 *          timer.end();
 *          System.out.println(timer);  // output statistics
 */

package axh190002.lp3;
public class Timer {
    long startTime, endTime, elapsedTime, memAvailable, memUsed;
    boolean ready;
    
    public Timer() {
	startTime = System.currentTimeMillis();
	ready = false;
    }

    public void start() {
	startTime = System.currentTimeMillis();
	ready = false;
    }

    public Timer end() {
	endTime = System.currentTimeMillis();
	elapsedTime = endTime-startTime;
	memAvailable = Runtime.getRuntime().totalMemory();
	memUsed = memAvailable - Runtime.getRuntime().freeMemory();
	ready = true;
	return this;
    }

    public long duration() { if(!ready) { end(); }  return elapsedTime; }

    public long memory()   { if(!ready) { end(); }  return memUsed; }

    public String toString() {
	if(!ready) { end(); }
        return "Time: " + elapsedTime + " msec.\n" + "Memory: " + (memUsed/1048576) + " MB / " + (memAvailable/1048576) + " MB.";
    }

}

