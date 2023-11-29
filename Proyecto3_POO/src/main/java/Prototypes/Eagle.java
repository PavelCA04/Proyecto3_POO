
package Prototypes;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class Eagle {
    private int hp;
    private static final int width = 64;
    private static final int length = 64;
    private int posX, posY;
    JLabel label;
    
    public Eagle(int posX, int posY){
        this.hp = 1;
        this.posX = posX;
        this.posY = posY;
    }
    
    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    } 

    public int getHp() {
        return hp;
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

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void decreaseHealth() {
        this.hp--;    
    }
    
}
