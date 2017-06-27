package tools;

import helpers.BankOpener;
import helpers.HumanLikeClicker;
import helpers.HumanLikeRandom;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ode
 */
public class AutoFletcher implements OsHelperTool {

    private final Robot robot;
    private final HumanLikeRandom random;
    private final HumanLikeClicker clicker;
    private final BankOpener bankOpener;
    private volatile boolean isRunning;

    public AutoFletcher(Robot robot, HumanLikeRandom random, 
            HumanLikeClicker clicker, BankOpener bankOpener) {
        this.robot = robot;
        this.random = random;
        this.clicker = clicker;
        this.bankOpener = bankOpener;
    }

    @Override
    public void run() {
        this.isRunning = true;
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException ex) {
            Logger.getLogger(AutoFletcher.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (isRunning) {
            try {
                //Move to up right in bank
                this.robot.mouseMove(545 + (random.nextBetaX(18) - 9),
                        150 + (random.nextBetaY(18) - 9));
                clicker.sixthInMenu();
                TimeUnit.MILLISECONDS.sleep(475 + random.nextInt(500));

                //Close bank
                this.robot.keyPress(KeyEvent.VK_ESCAPE);
                TimeUnit.MILLISECONDS.sleep(45 + random.nextInt(80));
                this.robot.keyRelease(KeyEvent.VK_ESCAPE);
                TimeUnit.MILLISECONDS.sleep(475 + random.nextInt(500));

                //Move to first in inv
                this.robot.mouseMove(826 + (random.nextBetaX(18) - 9),
                        515 + (random.nextBetaY(18) - 9));
                clicker.randomizeLeftClick();
                TimeUnit.MILLISECONDS.sleep(100 + random.nextBetaX(1000));

                //Move to second in inv
                this.robot.mouseMove(869 + (random.nextBetaX(18) - 9),
                        515 + (random.nextBetaY(18) - 9));
                clicker.randomizeLeftClick();
                TimeUnit.MILLISECONDS.sleep(1987 + random.nextInt(1000));

                //Move to longbow in fletch menu
                this.robot.mouseMove(325 + (random.nextBetaX(70) - 35),
                        685 + (random.nextBetaY(18) - 12));
                clicker.fourthInMakeMenu();
                TimeUnit.MILLISECONDS.sleep(900 + random.nextBetaX(900));

//                //Move to shortbow in fletch menu
//                this.robot.mouseMove(265 + (random.nextBetaX(40) - 20),
//                        685 + (random.nextBetaY(18) - 12));
//                fourthInMakeMenu();
//                TimeUnit.MILLISECONDS.sleep(900 + random.nextInt(900));
                this.robot.keyPress(KeyEvent.VK_9);
                TimeUnit.MILLISECONDS.sleep(25 + random.nextInt(30));
                this.robot.keyRelease(KeyEvent.VK_9);
                TimeUnit.MILLISECONDS.sleep(59 + random.nextInt(30));
                this.robot.keyPress(KeyEvent.VK_9);
                TimeUnit.MILLISECONDS.sleep(25 + random.nextInt(30));
                this.robot.keyRelease(KeyEvent.VK_9);
                TimeUnit.MILLISECONDS.sleep(245 + random.nextBetaX(300));
                this.robot.keyPress(KeyEvent.VK_ENTER);
                TimeUnit.MILLISECONDS.sleep(25 + random.nextBetaX(70));
                this.robot.keyRelease(KeyEvent.VK_ENTER);
                TimeUnit.SECONDS.sleep(51 + random.nextBetaX(20));
                TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));

                //Move to bank
                bankOpener.openBankChest();
                TimeUnit.MILLISECONDS.sleep(1789 + random.nextBetaX(1000));

                //Move to second in inv
                this.robot.mouseMove(869 + (random.nextBetaX(18) - 9),
                        515 + (random.nextBetaY(18) - 9));
                clicker.sixthInMenu();
                TimeUnit.MILLISECONDS.sleep(1900 + random.nextBetaX(1000));
            } catch (InterruptedException ex) {
                Logger.getLogger(NMZ_Helper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void stop() {
        this.isRunning = false;
    }
}
