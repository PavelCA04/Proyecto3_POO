
package Commands;
import Game.GameController;
import Game.GameModel;
import Interfaces.ICommand;



public class FireCommand implements ICommand{
    
    private final GameModel gameModel;
    
    public FireCommand(GameModel gameModel){
        this.gameModel = gameModel;
    }
    


    @Override
    public void execute(GameController gameCtrl) {
        System.out.println("Piu piu"); 
    }
    
}