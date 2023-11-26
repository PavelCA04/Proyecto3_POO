
package Prototypes;

import javax.swing.ImageIcon;


public class Brick {
    private int hp;
    private static final int width = 64;
    private static final int length = 64;
    private int posX, posY;
    
    public Brick(int posX, int posY){
        this.hp = 4;
        this.posX = posX;
        this.posY = posY;
    }
    
    public void draw(){
        ImageIcon image = new ImageIcon("Images\\brick.png");
        // Conectar con el controller
    }
}
