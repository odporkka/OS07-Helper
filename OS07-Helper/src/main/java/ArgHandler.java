
import Tools.AFK_Preventer;
import Tools.ToolRunTimer;
import java.awt.AWTException;
import java.awt.Robot;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private final AFK_Preventer afk_p;
    private final Robot robot;
    private final Random random;
    private ToolRunTimer trt;

    public ArgHandler(String[] args) throws AWTException {
        this.args = args;
        this.robot = new Robot();
        this.random = new Random();
        this.afk_p = new AFK_Preventer(robot, random);
    }

    /**
     * Method for argument executing.
     */
    public void executeArguments() {
        String command = args[0];
        System.out.println("Executing command: " + command);

        switch (command) {
            case "afk":
                invokeAfkPreventer();
                break;
            case "help":
                printHelp();
                break;
            default:
                System.out.println("No command: \"" + args[0] + "\" found!");
                printHelp();
                break;
        }
        System.out.println("Run succesfull!");
        System.out.println("Quitting!");
    }

    /**
     * Method for starting AFK_Preventer tool in a new thread. If time is set
     * ToolRunTimer starts too and stops AFK_Preventer after given time.
     */
    private void invokeAfkPreventer() {
        Thread t1 = new Thread(afk_p);

        //Starting timer in different thread if time is set.
        if (args[1] != null) {
            int waitTime = 5;
            try {
                waitTime = Integer.parseInt(args[1]);
            } catch (Exception e) {
                System.out.println("Could not convert run time. Running till stopped...");
                return;
            }
            trt = new ToolRunTimer(0, waitTime, 0);
            Thread t2 = new Thread(trt);
            //Starting timer for AFK_Preventer thread
            t2.start();

            synchronized (t2) {
                try {
                    System.out.println("Running AFK_Preventer for " + waitTime + " minutes...");
                    System.out.println("Press ctrl-c twice in terminal or quit manually to stop.");
                    //Starting AFK_Preventer thread
                    t1.start();
                    //Waiting for timer to run out
                    t2.wait();
                    System.out.println("Stopping...");
                    //Waiting for timer thread to close
                    trt.join();
                    //Stopping AFK_Preventer
                    afk_p.stop();

                } catch (InterruptedException ex) {
                    Logger.getLogger(ArgHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

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

}
