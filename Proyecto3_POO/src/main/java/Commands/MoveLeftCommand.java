
package Commands;
import Game.GameController;
import Game.GameModel;
import Interfaces.ICommand;
import javax.swing.ImageIcon;



public class MoveLeftCommand implements ICommand{
    
    private final GameModel gameModel;
    private final ImageIcon image;
    
    public MoveLeftCommand(GameModel gameModel){
        this.gameModel = gameModel;
        this.image = new ImageIcon("Images\\playerLeft.png"); 
    }
    
    @Override
    public void execute(GameController gameCtrl) {
        // logic
        System.out.println("Left");
        gameCtrl.movePlayerTank(0, -1, image);

    }
    
}