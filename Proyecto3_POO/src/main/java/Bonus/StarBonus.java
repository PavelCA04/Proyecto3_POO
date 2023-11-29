
package Bonus;

import Game.GameController;
import Interfaces.IStrategy;
import javax.swing.JLabel;

/**
 *
 * @author pavel
 */
public class StarBonus implements IStrategy {

    @Override
    public void applyBonus(GameController gamecontroller) {
        System.out.println("Estrella Bonus: Tank shoots faster");
        gamecontroller.increaseShooting();
    }  
 
}
