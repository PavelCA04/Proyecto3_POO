
package Prototypes;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class MetalBrick {
    private static final int width = 64;
    private static final int length = 64;
    private int posX, posY;
    JLabel label;
    
    public MetalBrick(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }
    
    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }   

    public static int getWidth() {
        return width;
    }

    public static int getLength() {
        return length;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
    
}
