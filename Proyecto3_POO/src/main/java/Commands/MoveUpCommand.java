
package Commands;
import Game.EnumDirection;
import Game.GameController;

import Interfaces.ICommand;
import javax.swing.ImageIcon;


public class MoveUpCommand implements ICommand{
    

    private final ImageIcon image;
    private final EnumDirection dir = EnumDirection.UP;
    
    public MoveUpCommand(){
        this.image = new ImageIcon("Images\\playerUp.png"); 
    }
    
    @Override
    public void execute(GameController gameCtrl) {
        // logic
        System.out.println("Up");
        gameCtrl.movePlayerTank(-1, 0, image, dir);
    }
    
}
