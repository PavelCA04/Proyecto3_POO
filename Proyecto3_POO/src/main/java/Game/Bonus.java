
package Game;

import javax.swing.JLabel;


public class Bonus {
    JLabel label;
    String type;
    int posX;
    int posY;

    public Bonus(String type, int posX, int posY) {
        this.type = type;
        this.posX = posX;
        this.posY = posY;
    }

    public JLabel getLabel() {
        return label;
    }

    public String getType() {
        return type;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

    public void setType(String type) {
        this.type = type;
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
