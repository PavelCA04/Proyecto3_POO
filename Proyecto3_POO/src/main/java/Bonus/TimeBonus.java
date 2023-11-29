
package Bonus;

import Game.GameController;
import Interfaces.IStrategy;
import javax.swing.JLabel;


public class TimeBonus implements IStrategy {

    @Override
    public void applyBonus(GameController gamecontroller) {
        System.out.println("Reloj Bonus: Enemies are frozen for 10 seconds");
        gamecontroller.pauseEnemies();
    }
   
}
