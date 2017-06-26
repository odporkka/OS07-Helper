package tools;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Timer class for handling run time. Time is set in hours, minutes and seconds.
 * When timer hits zero Timer notifies all components that are listening and
 * they are responsible for other actions including closing timers thread.
 * @author ode
 */
public class ToolRunTimer extends Thread {

    private final int runTimeInHours;
    private final int runTimeInMinutes;
    private final int runTimeInSeconds;

    public ToolRunTimer(int runTimeInHours, int runTimeInMinutes, int runTimeInSeconds) {
        this.runTimeInHours = runTimeInHours;
        this.runTimeInMinutes = runTimeInMinutes;
        this.runTimeInSeconds = runTimeInSeconds;
    }

    @Override
    public void run() {

        synchronized (this) {
            try {
                TimeUnit.HOURS.sleep(runTimeInHours);
                TimeUnit.MINUTES.sleep(runTimeInMinutes);
                TimeUnit.SECONDS.sleep(runTimeInSeconds);
                notify();
            } catch (InterruptedException ex) {
                Logger.getLogger(ToolRunTimer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }

    }

}
