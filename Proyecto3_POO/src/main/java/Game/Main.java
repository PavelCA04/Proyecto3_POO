

package Game;


import Prototypes.Tank;


public class Main {

    // Método para mostrar los detalles del tanque en consola (sustituye con los atributos de tu tanque)
    private static void displayTankDetails(Tank tank) {
        System.out.println("Tank Type: " + tank.getClass().getSimpleName());
        // Imprimir otros detalles específicos del tanque
    }
    
    public static void main(String[] args) {
               
        GameController controller = new GameController( 1);
        
    }
}
