
package Commands;
import Game.EnumDirection;
import Game.GameController;
import Interfaces.ICommand;
import javax.swing.ImageIcon;



public class MoveLeftCommand implements ICommand{
    

    private final ImageIcon image;
    private final EnumDirection dir = EnumDirection.LEFT;
    
    public MoveLeftCommand(){

        this.image = new ImageIcon("Images\\playerLeft.png"); 
    }
    
    @Override
    public void execute(GameController gameCtrl) {
        // logic
        System.out.println("Left");
        gameCtrl.movePlayerTank(0, -1, image, dir);
    }
    
}