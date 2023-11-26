
package Threads;

import java.util.logging.Level;
import java.util.logging.Logger;




public class EnemiesThread extends Thread{
    ///////////////
    // Atributes //
    ///////////////

    boolean isRunning = true;
 
    
    
    
    /////////////////
    // Contructors //
    /////////////////
    
    public EnemiesThread(){
    }
    
    
    
    
    /////////////
    // Methods //
    /////////////   
    @Override
    public void run(){
        while (isRunning){
            try{
                sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(EnemiesThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
