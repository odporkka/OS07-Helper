package Tools;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * AFK prevention tool. Randomizes time between keystrokes to avoid detection.
 * Also key press is randomized between 100 and 200 milliseconds to simulate
 * natural action. Tool is ran in a own thread invoked at upper level.
 * 
 * @author ode
 */
public class AFK_Preventer implements Runnable{
    
     
    private final Robot robot;
    private final Random random;
    private final int maxWaitTime;
    private volatile boolean isRunning;
    private KeyEvent key;
    private int runningTime;

    public AFK_Preventer(Robot robot, Random random){
        this.robot = robot;
        this.random = random;
        this.maxWaitTime = 20;
        this.isRunning = false;
    }

    /**
     * Method for key press. Runs until stopped by stop() -method.
     */
    @Override
    public synchronized void run() {
        this.isRunning = true;
        System.out.println("Keep the window you want input in in front..");
       
        while (isRunning) { 
            try {
                //Press key
                TimeUnit.SECONDS.sleep(2);
                robot.keyPress(KeyEvent.VK_LEFT);
                TimeUnit.MILLISECONDS.sleep(100+random.nextInt(100));
                robot.keyRelease(KeyEvent.VK_LEFT);
                
                //Calculates wait time between keystrokes 
                int waitTimeMinutes = random.nextInt(maxWaitTime-1);
                int waitTimeSeconds = random.nextInt(59);
                int waitTimeTotal = waitTimeMinutes*60;
                waitTimeTotal += waitTimeSeconds;
                System.out.println(waitTimeTotal);
                
                System.out.println("Next keystroke in: "+waitTimeMinutes+" min "+
                        waitTimeSeconds+" sec");
                TimeUnit.SECONDS.sleep(waitTimeTotal);
            } catch (InterruptedException ex) {
                Logger.getLogger(AFK_Preventer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void stop(){
        this.isRunning = false;
    }

    public void setKey(String key) {
    }

    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }

    public int getRunningTime() {
        return runningTime;
    }
    
}
