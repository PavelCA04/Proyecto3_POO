
package Bonus;

import Game.GameController;
import Interfaces.IStrategy;
import javax.swing.JLabel;

/**
 *
 * @author pavel
 */
public class BombBonus implements IStrategy {

    
    @Override
    public void applyBonus(GameController gamecontroller) {
        System.out.println("Bomba Bonus: Destroy all enemies on the screen");
        // Implementation of destroying all enemies
        gamecontroller.killEnemies();
    }

    
    
}
