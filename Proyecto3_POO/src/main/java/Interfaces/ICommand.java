
package Interfaces;

import Game.GameController;
import java.io.OutputStream;

public interface ICommand {            
    public void execute(GameController gameCtrl);   
}
