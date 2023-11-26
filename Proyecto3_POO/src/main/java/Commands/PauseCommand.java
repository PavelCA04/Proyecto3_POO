
package Commands;
import Game.GameController;
import Game.GameModel;
import Interfaces.ICommand;



public class PauseCommand implements ICommand{
    
    private final GameModel gameModel;
    
    public PauseCommand(GameModel gameModel){
        this.gameModel = gameModel;
    }

    @Override
    public void execute(GameController gameCtrl) {
        System.out.println("Pause");
    }
    
}