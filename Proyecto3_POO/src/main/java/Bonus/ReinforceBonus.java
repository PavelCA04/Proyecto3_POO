
package Bonus;

import Game.GameController;
import Interfaces.IStrategy;
import javax.swing.JLabel;

/**
 *
 * @author pavel
 */
public class ReinforceBonus implements IStrategy {

    @Override
    public void applyBonus(GameController gamecontroller) {
        System.out.println("Pala Bonus: Base is protected with metal for 20 seconds");
        gamecontroller.protectBase();
    } 

}
