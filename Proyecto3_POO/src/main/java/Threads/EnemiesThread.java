
package Threads;

import Game.GameController;
import Prototypes.EnemyTank;
import java.util.logging.Level;
import java.util.logging.Logger;




public class EnemiesThread extends Thread{
    ///////////////
    // Atributes //
    ///////////////

    EnemyTank enemy;
    GameController refController;
    boolean isRunning = true;
 
    
    
    
    /////////////////
    // Contructors //
    /////////////////
    
    public EnemiesThread(EnemyTank enemyTank, GameController refController){
        this.enemy = enemyTank;
        this.refController = refController;
    }
    
    
    
    
    /////////////
    // Methods //
    /////////////   
    @Override
    public void run(){
        while (isRunning){
            try{
                refController.enemyEvents(enemy);
                sleep(enemy.getTankVel());
            } catch (InterruptedException ex) {
                Logger.getLogger(EnemiesThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public EnemyTank getEnemy() {
        return enemy;
    }
    
    
}
