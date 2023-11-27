
package Commands;
import Game.GameController;
import Game.GameModel;
import Interfaces.ICommand;
import javax.swing.ImageIcon;



public class MoveDownCommand implements ICommand{
    
    private final GameModel gameModel;
    private final ImageIcon image;
    
    public MoveDownCommand(GameModel gameModel){
        this.gameModel = gameModel;
        this.image = new ImageIcon("Images\\playerDown.png"); 
    }
    
    @Override
    public void execute(GameController gameCtrl) {
        // logic
        System.out.println("Down");
        gameCtrl.movePlayerTank(1, 0, image);

    }
    
}