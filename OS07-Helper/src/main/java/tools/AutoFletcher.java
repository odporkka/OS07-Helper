package tools;

import helpers.HumanLikeRandom;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author ode
 */
public class AutoFletcher implements OsHelperTool {

    private final Robot robot;
    private final HumanLikeRandom random;
    private volatile boolean isRunning;
    private String[] heatmap;
    private int imgNro;

    public AutoFletcher(Robot robot, HumanLikeRandom random) {
        this.robot = robot;
        this.random = random;
        this.imgNro = 1;
    }

    @Override
    public void run() {
        this.isRunning = true;
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException ex) {
            Logger.getLogger(AutoFletcher.class.getName()).log(Level.SEVERE, null, ex);
        }

        //int test = 1;
        boolean notFound = true;
        while (isRunning) {
            try {
                //Move to up right in bank
                this.robot.mouseMove(545 + (random.nextBetaX(18) - 9),
                        150 + (random.nextBetaY(18) - 9));
                sixthInMenu();
                TimeUnit.MILLISECONDS.sleep(475 + random.nextInt(500));

                //Close bank
                this.robot.keyPress(KeyEvent.VK_ESCAPE);
                TimeUnit.MILLISECONDS.sleep(45 + random.nextInt(30));
                this.robot.keyRelease(KeyEvent.VK_ESCAPE);
                TimeUnit.MILLISECONDS.sleep(475 + random.nextInt(500));

                //Move to first in inv
                this.robot.mouseMove(826 + (random.nextBetaX(18) - 9),
                        515 + (random.nextBetaY(18) - 9));
                randomizeLeftClick();
                TimeUnit.MILLISECONDS.sleep(400 + random.nextInt(300));

                //Move to second in inv
                this.robot.mouseMove(869 + (random.nextBetaX(18) - 9),
                        515 + (random.nextBetaY(18) - 9));
                randomizeLeftClick();
                TimeUnit.MILLISECONDS.sleep(1987 + random.nextInt(1000));

                //Move to longbow in fletch menu
                this.robot.mouseMove(325 + (random.nextBetaX(70) - 35),
                        685 + (random.nextBetaY(18) - 12));
                fourthInMakeMenu();
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
                while (notFound) {
                    System.out.println("Trying to find chest:");
                    Point chest;
                    try {
                        chest = findCastleWarsChest();
                        //Chest found!
                        if (chest != null) {
                            TimeUnit.MILLISECONDS.sleep(789 + random.nextBetaX(1000));
                            this.robot.mouseMove((int) chest.getX() + (random.nextBetaX(40) - 10),
                                    (int) chest.getY() + (random.nextBetaX(25) - 15));
                            randomizeLeftClick();
                            //Check if click was red!
                            if (checkIfRedLeftClick()) {
                                System.out.println("Red click!");
                                if (checkIfBankOpen()) {
                                    System.out.println("Bank open!");
                                    notFound = false;
                                } else {
                                    System.out.println("Bank not open!");
                                }
                            } else {
                                System.out.println("Fail click!");
                                TimeUnit.MILLISECONDS.sleep(375 + random.nextInt(500));
                                return;
                            }
                        } else {
                            System.out.println("Chest not found!");
                            TimeUnit.MILLISECONDS.sleep(1000);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(AutoFletcher.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                notFound = true;
                TimeUnit.MILLISECONDS.sleep(1789 + random.nextBetaX(1000));

                //Move to second in inv
                this.robot.mouseMove(869 + (random.nextBetaX(18) - 9),
                        515 + (random.nextBetaY(18) - 9));
                sixthInMenu();
                TimeUnit.MILLISECONDS.sleep(1900 + random.nextBetaX(1000));

//                //Move to test draw
//                this.robot.mouseMove(270 + (random.nextBetaX(200) - 100),
//                        470 + (random.nextBetaY(200) - 100));
//                randomizeLeftClick();
//                TimeUnit.MILLISECONDS.sleep(100);
//                test ++;
//                if (test >= 1000) isRunning = false;
            } catch (InterruptedException ex) {
                Logger.getLogger(NMZ_Helper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void stop() {
        this.isRunning = false;
    }

    private void randomizeLeftClick() throws InterruptedException {
        int milliseconds = 50 + random.nextInt(60);
        TimeUnit.MILLISECONDS.sleep(30 + random.nextBetaX(55));

        //System.out.println("Left click! (" + milliseconds + ")");
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        TimeUnit.MILLISECONDS.sleep(milliseconds);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    private void randomizeRightClick() throws InterruptedException {
        int milliseconds = 50 + random.nextInt(60);
        TimeUnit.MILLISECONDS.sleep(30 + random.nextBetaX(55));
        //System.out.println("Right click! (" + milliseconds + ")");

        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        TimeUnit.MILLISECONDS.sleep(milliseconds);
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
    }

    private void firstInMenu() throws InterruptedException {
        randomizeRightClick();
        Point point = MouseInfo.getPointerInfo().getLocation();
        int x = (int) point.getX();
        int y = (int) point.getY();
        TimeUnit.MILLISECONDS.sleep(random.nextBetaX(300) + 450);
        robot.mouseMove(x + (random.nextInt(65) - 60), y + 23 + random.nextInt(10));
        randomizeLeftClick();
    }

    private void sixthInMenu() throws InterruptedException {
        randomizeRightClick();
        Point point = MouseInfo.getPointerInfo().getLocation();
        int x = (int) point.getX();
        int y = (int) point.getY();
        TimeUnit.MILLISECONDS.sleep(random.nextBetaX(500) + 450);
        robot.mouseMove(x + (random.nextInt(100) - 80), y + 98 + random.nextInt(10));
        randomizeLeftClick();
    }

    private void fourthInMakeMenu() throws InterruptedException {
        randomizeRightClick();
        Point point = MouseInfo.getPointerInfo().getLocation();
        int x = (int) point.getX();
        int y = (int) point.getY();
        TimeUnit.MILLISECONDS.sleep(random.nextBetaX(600) + 450);
        robot.mouseMove(x + (random.nextInt(90) - 48), y + 69 + random.nextInt(9));
        randomizeLeftClick();
    }

    private Point findCastleWarsChest() throws IOException {
        Point p = null;
        Color c;
        BufferedImage rectang = this.robot.createScreenCapture(new Rectangle(540, 380, 170, 170));
        String path = AutoFletcher.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        File folder = new File(path);
        ImageIO.write(rectang, "PNG", new File(folder.getParentFile() + "/CastleWarsChest.png"));
        long startTime = System.currentTimeMillis();
        loop:
        for (int y = 0; y < 150; y++) {
            for (int x = 0; x < 70; x++) {
                c = new Color(rectang.getRGB(x, y));
                //System.out.println(c.getRed() + ", "+c.getGreen()+", "+c.getBlue());
                if ((c.getRed() >= 17) && (c.getRed() <= 19)
                        && (c.getGreen() >= 12) && (c.getGreen() <= 14)
                        && (c.getBlue() >= 9) && (c.getBlue() <= 11)) {
                    long stopTime = System.currentTimeMillis();
                    long elapsedTime = stopTime - startTime;
                    System.out.println("Chest found @" + (x + 540) + "x" + (y + 380) + " ("
                            + elapsedTime + "ms)");
                    p = new Point((x + 520) + 30, (y + 380) + 30);
                    break loop;
                }
            }
        }
        return p;
    }

    private boolean checkIfRedLeftClick() throws InterruptedException, IOException {
        Point p = MouseInfo.getPointerInfo().getLocation();
        String path = AutoFletcher.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        File folder = new File(path);
        TimeUnit.MILLISECONDS.sleep(50);
//        BufferedImage rectang50 = this.robot.createScreenCapture(
//                new Rectangle((int) p.getX() - 30, (int) p.getY() - 30, 60, 60));
        TimeUnit.MILLISECONDS.sleep(50);
        BufferedImage rectang100 = this.robot.createScreenCapture(
                new Rectangle((int) p.getX() - 40, (int) p.getY() - 40, 80, 80));
//        TimeUnit.MILLISECONDS.sleep(50);
//        BufferedImage rectang150 = this.robot.createScreenCapture(
//                new Rectangle((int) p.getX() - 30, (int) p.getY() - 30, 60, 60));
//        TimeUnit.MILLISECONDS.sleep(50);
//        BufferedImage rectang200 = this.robot.createScreenCapture(
//                new Rectangle((int) p.getX() - 30, (int) p.getY() - 30, 60, 60));
//        TimeUnit.MILLISECONDS.sleep(50);
//        BufferedImage rectang250 = this.robot.createScreenCapture(
//                new Rectangle((int) p.getX() - 30, (int) p.getY() - 30, 60, 60));
//        ImageIO.write(rectang50, "JPG", new File(folder.getParentFile() + "/" + 
//                this.imgNro++ + "_Click50"+ ".jpg"));
//        ImageIO.write(rectang100, "PNG", new File(folder.getParentFile() + "/" + 
//                this.imgNro++ + "_Click100" + ".png"));
//        ImageIO.write(rectang150, "JPG", new File(folder.getParentFile() + "/" + 
//                this.imgNro++ + "_Click150" + ".jpg"));
//        ImageIO.write(rectang200, "JPG", new File(folder.getParentFile() + "/" + 
//                this.imgNro++ + "_Click200" + ".jpg"));
//        ImageIO.write(rectang250, "JPG", new File(folder.getParentFile() + "/" + 
//                this.imgNro++ + "_Click250" + ".jpg"));
        Color c;
        for (int y = 30; y < 50; y++) {
            for (int x = 30; x < 50; x++) {
                c = new Color(rectang100.getRGB(x, y));
                if ((c.getRed() > 240)
                        && (c.getGreen() < 20)
                        && (c.getBlue() < 30)) {
//                    ImageIO.write(rectang100, "PNG", new File(folder.getParentFile()
//                            + "/" + this.imgNro++ + "_Click(100ms).png"));
                    return true;
                }
            }
        }
        ImageIO.write(rectang100, "PNG", new File(folder.getParentFile()
                + "/" + this.imgNro++ + "_FailedClick(100ms).png"));
        return false;
    }

    private boolean checkIfBankOpen() throws InterruptedException, IOException {
        TimeUnit.MILLISECONDS.sleep(1000);
        BufferedImage rectang = this.robot.createScreenCapture(
                new Rectangle(300, 70, 40, 15));
        Color c;
        String path = AutoFletcher.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        File folder = new File(path);
        for (int y = 0; y < 15; y++) {
            for (int x = 0; x < 15; x++) {
                c = new Color(rectang.getRGB(x, y));
                if ((c.getRed() > 245)
                        && (c.getGreen() > 110) && (c.getGreen() < 140)
                        && (c.getBlue() < 15)) {
                    return true;
                }
            }
        }
        ImageIO.write(rectang, "PNG", new File(folder.getParentFile() + "/failedBankCheck.png"));
        return false;
    }
}
