
import gui.BasicGui;
import helpers.BankOpener;
import helpers.ColorFinder;
import helpers.HumanLikeClicker;
import tools.AFK_Preventer;
import tools.AutoFletcher;
import helpers.HumanLikeRandom;
import tools.NMZ_Helper;
import tools.OsHelperTool;
import tools.ToolRunTimer;
import java.awt.AWTException;
import java.awt.Robot;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;

/**
 * Class responsible for command line argument handling and invoking runnable
 * tools according to them.
 *
 * @param args Command line arguments in a String list.
 *
 * @author ode
 */
class ArgHandler {

    private final String[] args;

    //Helpers
    private final Robot robot;
    private final HumanLikeRandom random;
    private final HumanLikeClicker clicker;
    private final ColorFinder colorFinder;
    private final BankOpener bankOpener;

    //Tools
    private ToolRunTimer trt;
    private final AFK_Preventer afk_p;
    private final NMZ_Helper nmz;
    private final AutoFletcher fletcher;

    public ArgHandler(String[] args) throws AWTException {
        this.args = args;

        this.robot = new Robot();
        this.random = new HumanLikeRandom();
        this.clicker = new HumanLikeClicker(robot, random);
        this.colorFinder = new ColorFinder(robot);
        this.bankOpener = new BankOpener(colorFinder, random, clicker, robot);

        this.afk_p = new AFK_Preventer(robot, random);
        this.nmz = new NMZ_Helper(robot, random);
        this.fletcher = new AutoFletcher(robot, random, clicker, bankOpener);
    }

    /**
     * Method for argument executing.
     */
    public void executeArguments() {
        if (args.length == 0) {
            Application.launch(BasicGui.class, args);
            return;
        } 
        String command = args[0];

        System.out.println("Executing command: " + command);

        switch (command) {
            case "afk":
                invokeTool(afk_p);
                break;
            case "nmz":
                invokeTool(nmz);
                break;
            case "fletch":
                invokeTool(fletcher);
                break;
            case "help":
                printHelp();
                break;
            case "test":
                test();
                break;
            default:
                System.out.println("No command: \"" + args[0] + "\" found!");
                printHelp();
                break;
        }
    }

    /**
     * Method for printing help in console.
     */
    private void printHelp() {
        ClassLoader cl = getClass().getClassLoader();
        InputStream is = cl.getResourceAsStream("help.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line;
        try {
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            Logger.getLogger(ArgHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Invokes helper tool. Calls for tools run() and stop() methods
     * respectively. Timer is run also if set.
     *
     * @param tool
     */
    private void invokeTool(OsHelperTool tool) {
        Thread t1 = new Thread(tool);

        //Starting timer in different thread if time is set.
        if (args.length > 1) {
            int waitTime;
            try {
                waitTime = Integer.parseInt(args[1]);
            } catch (Exception e) {
                System.out.println("Could not convert run time. Stopping execute..");
                return;
            }
            trt = new ToolRunTimer(0, waitTime, 0);
            Thread t2 = new Thread(trt);
            //Starting timer for tool
            t2.start();

            synchronized (t2) {
                try {
                    System.out.println("Running " + tool.getClass().getName() + " for " + waitTime + " minutes...");
                    System.out.println("Press ctrl-c twice in terminal or quit manually to stop.");
                    //Starting tool thread
                    t1.start();
                    //Waiting for timer to run out
                    t2.wait();
                    System.out.println("Stopping...");
                    //Waiting for timer thread to close
                    trt.join();
                    //Stopping NMZ_Helper
                    tool.stop();

                } catch (InterruptedException ex) {
                    Logger.getLogger(ArgHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            System.out.println("Time not set. Running " + tool.getClass().getName() + " till stopped.");
            t1.start();
        }
    }

    private void test() {
        try {
            TimeUnit.MILLISECONDS.sleep(3000);
            colorFinder.checkIfBankOpen();
        } catch (InterruptedException | IOException ex) {
            Logger.getLogger(ArgHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
