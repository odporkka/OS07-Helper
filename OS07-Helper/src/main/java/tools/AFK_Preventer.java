package tools;

import helpers.HumanLikeRandom;
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
public class AFK_Preventer implements OsHelperTool {

    private final Robot robot;
    private final HumanLikeRandom random;
    //Max time between keystrokes, defaults to 20 min to prevent afk
    private final int maxWaitTime;
    private volatile boolean isRunning;

    public AFK_Preventer(Robot robot, HumanLikeRandom random) {
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
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException ex) {
            Logger.getLogger(AFK_Preventer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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

    @Override
    public void stop() {
        this.isRunning = false;
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
                TimeUnit.MILLISECONDS.sleep(random.nextInt(50));
                System.out.println("2nd stroke!");
                randomizeKeyPress(false);
            }
        }

    }

}
