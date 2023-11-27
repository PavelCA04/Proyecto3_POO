
package Prototypes;

import javax.swing.JLabel;


public abstract class Enemy {
    
    public String id;
    public int posX, posY;
    public int hp;
    public int tankVel;
    public int shellVel;
    JLabel label;
    
    public Enemy(String id, int posX, int posY){
        this.id = id;
        this.posX = posX;
        this.posY = posY;      
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

    public int getHp() {
        return hp;
    }

    public int getTankVel() {
        return tankVel;
    }

    public int getShellVel() {
        return shellVel;
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

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setTankVel(int tankVel) {
        this.tankVel = tankVel;
    }

    public void setShellVel(int shellVel) {
        this.shellVel = shellVel;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }
    
    
}
