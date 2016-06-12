package Tools;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * AFK prevention tool. Randomizes time between keystrokes and time key is
 * pressed. Also sometimes adds randomly a second press. Tool is ran in a own
 * thread invoked at upper level.
 *
 * @author ode
 */
public class AFK_Preventer implements Runnable {

    private final Robot robot;
    private final Random random;
    private final int maxWaitTime;
    private volatile boolean isRunning;
    private KeyEvent key;
    private int runningTime;

    public AFK_Preventer(Robot robot, Random random) {
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
                //Simulates key press
                randomizeKeyPress(true);

                //Calculates wait time between keystrokes 
                int waitTimeMinutes = random.nextInt(maxWaitTime - 1);
                int waitTimeSeconds = random.nextInt(59);
                int waitTimeTotal = waitTimeMinutes * 60;
                waitTimeTotal += waitTimeSeconds;
                System.out.println(waitTimeTotal);

                System.out.println("Next keystroke in: " + waitTimeMinutes + " min "
                        + waitTimeSeconds + " sec");
                TimeUnit.SECONDS.sleep(waitTimeTotal);
            } catch (InterruptedException ex) {
                Logger.getLogger(AFK_Preventer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void stop() {
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

    private void randomizeKeyPress(boolean firstPress) throws InterruptedException {
        int pressedKey = random.nextInt(4);
        System.out.println("Pressing key #" + pressedKey);
        switch (pressedKey) {
            case 0:
                TimeUnit.MILLISECONDS.sleep(15);
                robot.keyPress(KeyEvent.VK_LEFT);
                TimeUnit.MILLISECONDS.sleep(100 + random.nextInt(100));
                robot.keyRelease(KeyEvent.VK_LEFT);
                break;
            case 1:
                TimeUnit.MILLISECONDS.sleep(15);
                robot.keyPress(KeyEvent.VK_RIGHT);
                TimeUnit.MILLISECONDS.sleep(100 + random.nextInt(100));
                robot.keyRelease(KeyEvent.VK_RIGHT);
                break;
            case 2:
                TimeUnit.MILLISECONDS.sleep(15);
                robot.keyPress(KeyEvent.VK_UP);
                TimeUnit.MILLISECONDS.sleep(100 + random.nextInt(100));
                robot.keyRelease(KeyEvent.VK_UP);
                break;
            case 3:
                TimeUnit.MILLISECONDS.sleep(15);
                robot.keyPress(KeyEvent.VK_DOWN);
                TimeUnit.MILLISECONDS.sleep(100 + random.nextInt(100));
                robot.keyRelease(KeyEvent.VK_DOWN);
                break;
        }

        if (firstPress) {
            int r = random.nextInt(4);
            if (r > 1) {
                System.out.println("2nd stroke!");
                randomizeKeyPress(false);
            }
        }

    }

}
