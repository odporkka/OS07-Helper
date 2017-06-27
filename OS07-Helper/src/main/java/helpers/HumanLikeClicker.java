package helpers;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author ode
 */
public class HumanLikeClicker {

    private final Robot robot;
    private final HumanLikeRandom random;

    public HumanLikeClicker(Robot robot, HumanLikeRandom random) {
        this.random = random;
        this.robot = robot;
    }

    /**
     * Basic left and right click.
     *
     * @throws InterruptedException
     */
    public void randomizeLeftClick() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(9 + random.nextBetaX(10));
        int milliseconds = 20 + random.nextInt(40) + randomKeyPressedDelay();
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        TimeUnit.MILLISECONDS.sleep(milliseconds);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    public void randomizeRightClick() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(9 + random.nextBetaX(10));
        int milliseconds = 20 + random.nextInt(40) + randomKeyPressedDelay();
        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        TimeUnit.MILLISECONDS.sleep(milliseconds);
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
    }

    /**
     * Right click menu options
     *
     * @throws InterruptedException
     */
    public void firstInMenu() throws InterruptedException {
        randomizeRightClick();
        Point point = MouseInfo.getPointerInfo().getLocation();
        int x = (int) point.getX();
        int y = (int) point.getY();
        TimeUnit.MILLISECONDS.sleep(random.nextBetaX(300) + 450);
        robot.mouseMove(x + (random.nextInt(65) - 60), y + 23 + random.nextInt(10));
        randomizeLeftClick();
    }

    public void sixthInMenu() throws InterruptedException {
        randomizeRightClick();
        Point point = MouseInfo.getPointerInfo().getLocation();
        int x = (int) point.getX();
        int y = (int) point.getY();
        TimeUnit.MILLISECONDS.sleep(random.nextBetaX(600) + 450 + randomMenuPressDelay());
        robot.mouseMove(x + (random.nextInt(100) - 80), y + 98 + random.nextInt(10));
        randomizeLeftClick();
    }

    /**
     * Smaller right click menu. I.e. in crafting/fletching "make x"
     *
     * @throws java.lang.InterruptedException
     */
    public void fourthInMakeMenu() throws InterruptedException {
        randomizeRightClick();
        Point point = MouseInfo.getPointerInfo().getLocation();
        int x = (int) point.getX();
        int y = (int) point.getY();
        TimeUnit.MILLISECONDS.sleep(random.nextBetaX(500) + 450 + randomMenuPressDelay());
        robot.mouseMove(x + (random.nextInt(90) - 48), y + 69 + random.nextInt(9));
        randomizeLeftClick();
    }

    /**
     * Private helpers.
     */
    private int randomKeyPressedDelay() {
        int delay = this.random.nextInt(69);
        if (delay == 0) {
            return 80;
        }
        return 0;
    }
    
    private int randomMenuPressDelay() {
        int delay = this.random.nextInt(95);
        if (delay < 1) {
            return 3000;
        }
        if (delay < 5) {
            return 1000;
        }
        return 0;
    }
}
