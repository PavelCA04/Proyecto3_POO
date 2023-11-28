
package Commands;
import Game.GameController;

import Interfaces.ICommand;
import Interfaces.IObservable;
import Interfaces.IObserver;
import java.util.ArrayList;
import java.util.List;



public class FireCommand implements ICommand, IObservable{
    
    private GameController gamecontroller;
    private List<IObserver> observers = new ArrayList<>();
    
    
    public FireCommand(GameController gamecontroller){
        this.gamecontroller = gamecontroller;
        addObserver(gamecontroller); 
    }
    


    @Override
    public void execute(GameController gameCtrl) {
        System.out.println("Piu piu"); 
        gameCtrl.fireShellPlayer();
        
        notifyAllObservers("fired", this);
    }
    
    @Override
    public void addObserver(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyAllObservers(String command, Object source) {
        for (IObserver observer : observers) {
            observer.notifyObserver(command, source);
        }
    }
   
}