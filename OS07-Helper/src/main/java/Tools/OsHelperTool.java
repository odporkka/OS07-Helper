/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

/**
 *
 * @author ode
 */
public interface OsHelperTool extends Runnable {
    
    @Override
    public void run();
    
    public void stop();
    
}
