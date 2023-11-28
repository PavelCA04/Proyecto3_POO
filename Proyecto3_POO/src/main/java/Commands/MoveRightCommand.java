
package Commands;
import Game.EnumDirection;
import Game.GameController;
import Game.GameModel;
import Interfaces.ICommand;
import javax.swing.ImageIcon;



public class MoveRightCommand implements ICommand{
    
    private final GameModel gameModel;
    private final ImageIcon image;
    private final EnumDirection dir = EnumDirection.RIGHT;
    
    public MoveRightCommand(GameModel gameModel){
        this.gameModel = gameModel;
        this.image = new ImageIcon("Images\\playerRight.png");         
    }
    
    @Override
    public void execute(GameController gameCtrl) {
        // logic
        System.out.println("Right");
        gameCtrl.movePlayerTank(0, 1, image, dir);

    }
    
}