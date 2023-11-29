
package Threads;

import Game.GameController;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;


public class EnemySpawnThread extends Thread{
    ///////////////
    // Atributes //
    ///////////////
    
    GameController refController;
    private static final int SPAWN_INTERVAL = 4000; // Intervalo de generación en milisegundos
    boolean isRunning = true;
 
    
    
    
    /////////////////
    // Contructors //
    /////////////////
    
    public EnemySpawnThread(GameController refController){
        this.refController = refController;
    }
    
    
    
    
    /////////////
    // Methods //
    /////////////   
    @Override
    public void run(){
        while (isRunning){
            try{
                if (refController.getCurrentEnemiesCount() <= 20) {
                    refController.spawnEnemyTank();
                }                
                sleep(SPAWN_INTERVAL);
            } catch (InterruptedException ex) {
                Logger.getLogger(EnemiesThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 
    
    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }
}
