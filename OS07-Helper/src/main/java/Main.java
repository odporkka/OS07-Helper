import java.awt.AWTException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * OS07-Helper
 * @author ode
 */
public class Main {

    public static void main(String[] args) {
        
        try {
            ArgHandler ah = new ArgHandler(args);
            ah.executeArguments();
        } catch (AWTException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
