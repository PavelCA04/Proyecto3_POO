
package Prototypes;

import Interfaces.IPrototype;
import javax.swing.JLabel;


public class Shell implements IPrototype<Shell> {
    JLabel label;
    private int posX;
    private int posY;
    
    public Shell(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }
    
    public void setLabel(JLabel label) {
        this.label = label;
    }

    public JLabel getLabel() {
        return label;
    }

    @Override
    public Shell clone() {
        return new Shell(this.posX, this.posY);
    }

    @Override
    public Shell deepClone() {
        return clone();
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
