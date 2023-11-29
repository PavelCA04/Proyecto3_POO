
package Bonus;

import Game.GameController;
import Interfaces.IStrategy;
import javax.swing.JLabel;

/**
 *
 * @author pavel
 */
public class TankBonus implements IStrategy {

    @Override
    public void applyBonus(GameController gamecontroller) {
        System.out.println("Tanque Bonus: Tank moves faster");
        // Implementation of increasing tank speed
        gamecontroller.increaseTankSpeed();
    }
  
}
