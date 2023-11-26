
package Prototypes;

import javax.swing.ImageIcon;


public class HalfBrick {
    private int hp;
    private int posX, posY;
    
    public HalfBrick(int posX, int posY){
        this.hp = 2;
        this.posX = posX;
        this.posY = posY;
    }
    
    public void drawVertical(){
        ImageIcon image = new ImageIcon("Images\\halfBrick.png");
        // Conectar con el controller
    }
    public void drawHorizontal(){
        ImageIcon image = new ImageIcon("Images\\halfBrick2.png");
        // Conectar con el controller
    }    
}
