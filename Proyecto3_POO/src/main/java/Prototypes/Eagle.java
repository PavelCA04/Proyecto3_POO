
package Prototypes;

import javax.swing.ImageIcon;


public class Eagle {
    private int hp;
    private static final int width = 64;
    private static final int length = 64;
    private int posX, posY;
    
    public Eagle(int posX, int posY){
        this.hp = 1;
        this.posX = posX;
        this.posY = posY;
    }
    
    public void draw(){
        ImageIcon image = new ImageIcon("Images\\eagle.png");
        // Conectar con el controller
    }    
}
