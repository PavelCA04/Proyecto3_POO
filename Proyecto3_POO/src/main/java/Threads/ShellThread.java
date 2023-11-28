
package Threads;

import Game.EnumDirection;
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
    EnumDirection dir;
    long fireRate;

    
    boolean isRunning = true;
 
    
    
    
    /////////////////
    // Contructors //
    /////////////////
    
    public ShellThread(long fireRate, Shell shell, EnumDirection dir, GameController refController){
        this.shell = shell;
        this.refController = refController;
        this.dir = dir;
        this.fireRate = fireRate;
    }
    
    
    
    
    /////////////
    // Methods //
    /////////////   
    @Override
    public void run(){
        while (isRunning){
            try{
                refController.moveShell(this, shell, dir, shell.getPosX(), shell.getPosY());
                sleep(fireRate);
            } catch (InterruptedException ex) {
                Logger.getLogger(EnemiesThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }
    
    
}
