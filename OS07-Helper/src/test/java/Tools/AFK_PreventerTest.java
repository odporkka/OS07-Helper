/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ode
 */
public class AFK_PreventerTest {
    
    private AFK_Preventer ap;
    private Random random;
    private Robot robot;
    
    public AFK_PreventerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws AWTException {
        this.random = new Random();
        this.robot = new Robot();
        this.ap = new AFK_Preventer(robot, this.random);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of run method, of class AFK_Preventer.
     */
    @Test
    public void testRun() {
        Thread t = new Thread(ap);
        System.out.println("run");
        t.start();
        ap.stop();
        
    }
    
}
