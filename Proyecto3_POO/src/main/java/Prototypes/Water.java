
package Prototypes;

import javax.swing.JLabel;


public class Water {
    private static final int width = 64;
    private static final int length = 64;
    private int posX, posY;
    JLabel label;

    public Water(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }
    
    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }    
}
