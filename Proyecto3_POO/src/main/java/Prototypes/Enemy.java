
package Prototypes;

import Game.Config;
import Game.EnumDirection;
import javax.swing.JLabel;


public abstract class Enemy {
    
    public String id;
    private Config config;
    private EnumDirection dir;
    public int posX, posY;
    JLabel label;
    
    public Enemy(String id){
        this.id = id;  
    }
    
    public String getId() {
        return id;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public EnumDirection getDir() {
        return dir;
    }

    public JLabel getLabel() {
        return label;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setLabel(JLabel lbl) {
        this.label = lbl;
    }

    public void setDir(EnumDirection dir) {
        this.dir = dir;
    }   
}
