

package Game;

import Prototypes.FastTank;
import Prototypes.PlayerTank;
import Prototypes.PowerTank;
import Prototypes.SimpleTank;
import Prototypes.Tank;
import Prototypes.TankTank;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;


public class Main {

    // Método para mostrar los detalles del tanque en consola (sustituye con los atributos de tu tanque)
    private static void displayTankDetails(Tank tank) {
        System.out.println("Tank Type: " + tank.getClass().getSimpleName());
        // Imprimir otros detalles específicos del tanque
    }
    
    public static void main(String[] args) {
               
        GameModel model = new GameModel();
        GameWindow view = new GameWindow();
        GameController controller = new GameController(model, view, 1);
        
        view.addWindowListener(new WindowAdapter(){ //detecta el evento de cierre de la ventana 
            public void windowClosing(WindowEvent e){
                System.exit(0); // programa se cierra
            }
        });
        
        view.setVisible(true);
    }
}
