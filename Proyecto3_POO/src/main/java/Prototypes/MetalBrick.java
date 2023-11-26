
package Prototypes;

import javax.swing.ImageIcon;


public class MetalBrick {
    private static final int width = 64;
    private static final int length = 64;
    private int posX, posY;
    
    public MetalBrick(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }
    
    public void draw(){
        ImageIcon image = new ImageIcon("Images\\metalBrick.png");
        // Conectar con el controller
    }    
}
