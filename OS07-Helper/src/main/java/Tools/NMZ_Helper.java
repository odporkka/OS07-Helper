package Tools;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ode
 */
public class NMZ_Helper implements OsHelperTool {
    
    private final Robot robot;
    private final Random random;
    private volatile boolean isRunning;

    public NMZ_Helper(Robot robot, Random random) {
        this.robot = robot;
        this.random = random;
    }

    @Override
    public void run() {
        this.isRunning = true;
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException ex) {
            Logger.getLogger(NMZ_Helper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        while (isRunning) {            
            try {
                //Simulate double click (Prayer flick)
                randomizeClick();
                //Calculate time to next flick (between 44 and 59 seconds)
                int waitTimeSeconds = 44+random.nextInt(15);
                System.out.println("Next flick in "+waitTimeSeconds);
                TimeUnit.SECONDS.sleep(waitTimeSeconds);
            } catch (InterruptedException ex) {
                Logger.getLogger(NMZ_Helper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void stop() {
        this.isRunning = false;
    }

    private void randomizeClick() throws InterruptedException {
        int millisecondsFirst = 50+random.nextInt(60);
        int millisecondsBetweenClicks = 300+random.nextInt(500);
        int millisecondsSecond = 50+random.nextInt(60);
        System.out.println("DoubleClick! ("+millisecondsFirst+"/"+millisecondsBetweenClicks+"/"+millisecondsSecond+")");
        
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        TimeUnit.MILLISECONDS.sleep(millisecondsFirst);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        
        TimeUnit.MILLISECONDS.sleep(millisecondsBetweenClicks);
        
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        TimeUnit.MILLISECONDS.sleep(millisecondsSecond);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        
    }
    
}
