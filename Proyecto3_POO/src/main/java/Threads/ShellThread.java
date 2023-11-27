
package Threads;

import Game.GameController;
import Prototypes.Shell;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ShellThread extends Thread{
    ///////////////
    // Atributes //
    ///////////////
    
    Shell shell;
    GameController refController;
    boolean isRunning = true;
 
    
    
    
    /////////////////
    // Contructors //
    /////////////////
    
    public ShellThread(Shell shell, GameController refController){
        this.shell = shell;
        this.refController = refController;
    }
    
    
    
    
    /////////////
    // Methods //
    /////////////   
    @Override
    public void run(){
        while (isRunning){
            try{
                refController.moveShell(shell.getLabel());
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(EnemiesThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }    
}
