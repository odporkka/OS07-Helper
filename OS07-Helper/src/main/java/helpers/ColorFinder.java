/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import tools.AutoFletcher;

/**
 *
 * @author ode
 */
public class ColorFinder {

    private final Robot robot;

    public ColorFinder(Robot robot) {
        this.robot = robot;
    }

    public boolean checkIfRedLeftClick() throws InterruptedException, IOException {
        Point p = MouseInfo.getPointerInfo().getLocation();
        String path = AutoFletcher.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        File folder = new File(path);
        TimeUnit.MILLISECONDS.sleep(100);
        BufferedImage rectang100 = this.robot.createScreenCapture(
                new Rectangle((int) p.getX() - 40, (int) p.getY() - 40, 80, 80));
        Color c;
        for (int y = 30; y < 50; y++) {
            for (int x = 30; x < 50; x++) {
                c = new Color(rectang100.getRGB(x, y));
                if ((c.getRed() > 240)
                        && (c.getGreen() < 20)
                        && (c.getBlue() < 30)) {
                    return true;
                }
            }
        }
        ImageIO.write(rectang100, "PNG", new File(debugImagePath() + "FailedClick(100ms).png"));
        return false;
    }

    public boolean checkIfBankOpen() throws InterruptedException, IOException {
        TimeUnit.MILLISECONDS.sleep(1000);
        BufferedImage rectang = this.robot.createScreenCapture(
                new Rectangle(250, 50, 150, 150));
        Color c;
        String path = AutoFletcher.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        File folder = new File(path);
        for (int y = 20; y < 35; y++) {
            for (int x = 50; x < 65; x++) {
                c = new Color(rectang.getRGB(x, y));
                if ((c.getRed() > 245)
                        && (c.getGreen() > 110) && (c.getGreen() < 140)
                        && (c.getBlue() < 15)) {
                    return true;
                }
            }
        }
        ImageIO.write(rectang, "PNG", new File(debugImagePath() + "FailedBankCheck.png"));
        return false;
    }

    public Point findBankChest() throws IOException {
        Point p = null;
        Color c;
        BufferedImage rectang = this.robot.createScreenCapture(new Rectangle(540, 380, 170, 170));
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
                } else {
                    ImageIO.write(rectang, "PNG", new File(debugImagePath() + "CastleWarsChest.png"));
                }
            }
        }
        return p;
    }

    private String debugImagePath() {
        String path = AutoFletcher.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        File folder = new File(path);
        return folder.getParentFile() + "/" + logTime();
    }

    private String logTime() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.HOUR_OF_DAY) + "_" + now.get(Calendar.MINUTE) + "_";
    }
}
