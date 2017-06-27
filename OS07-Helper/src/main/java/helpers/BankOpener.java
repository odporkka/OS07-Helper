/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.awt.Point;
import java.awt.Robot;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import tools.AutoFletcher;

/**
 *
 * @author ode
 */
public class BankOpener {

    private final ColorFinder colorFinder;
    private final HumanLikeRandom random;
    private final HumanLikeClicker clicker;
    private final Robot robot;

    public BankOpener(ColorFinder colorFinder, HumanLikeRandom random, HumanLikeClicker clicker, Robot robot) {
        this.colorFinder = colorFinder;
        this.random = random;
        this.clicker = clicker;
        this.robot = robot;
    }

    public void openBankChest() throws InterruptedException {
        while (true) {
            System.out.println("Trying to find bank chest:");
            Point chest;
            try {
                chest = colorFinder.findBankChest();
                if (chest != null) {
                    this.robot.mouseMove((int) chest.getX() + (random.nextBetaX(40) - 10),
                            (int) chest.getY() + (random.nextBetaX(25) - 15));
                    clicker.randomizeLeftClick();
                    if (colorFinder.checkIfRedLeftClick()) {
                        if (colorFinder.checkIfBankOpen()) {
                            System.out.println("Found & opened!");
                            break;
                        } else {
                            System.out.println("Red click, but bank not open!");
                        }
                    } else {
                        System.out.println("Fail click!");
                        TimeUnit.MILLISECONDS.sleep(375 + random.nextInt(500));
                        // TODO Stop execute!
                    }
                } else {
                    System.out.println("Chest not found!");
                    TimeUnit.MILLISECONDS.sleep(2000);
                }
            } catch (IOException ex) {
                Logger.getLogger(AutoFletcher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
