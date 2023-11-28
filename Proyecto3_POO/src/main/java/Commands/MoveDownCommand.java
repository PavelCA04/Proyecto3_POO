
package Commands;
import Game.EnumDirection;
import Game.GameController;
import Interfaces.ICommand;
import javax.swing.ImageIcon;



public class MoveDownCommand implements ICommand{
    

    private final ImageIcon image;
    private final EnumDirection dir = EnumDirection.DOWN;
    
    public MoveDownCommand(){
        this.image = new ImageIcon("Images\\playerDown.png"); 
    }
    
    @Override
    public void execute(GameController gameCtrl) {
        // logic
        System.out.println("Down");
        gameCtrl.movePlayerTank(1, 0, image, dir);

    }
    
}