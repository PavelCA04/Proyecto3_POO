
package Prototypes;

import javax.swing.JLabel;


public class PlayerTank implements Tank{
    
    private String id;
    private int posX, posY;
    private int hp;
    private int tankVel;
    private int score;
    JLabel label;
    
    
    
    
    public PlayerTank(){       
    }
    public PlayerTank(String id) {
        this();
        this.id = id;        
        this.hp = 3;
        this.tankVel = 5;
    }
    
    
    @Override
    public Tank clone() {
        return new PlayerTank(this.id);
    }

    @Override
    public Tank deepClone() {
        return clone();    
    }   
    
    
    @Override
    public void move() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void shoot() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getResistance() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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

    public int getScore() {
        return score;
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

    public void setScore(int score) {
        this.score = score;
    }   

    public void setLabel(JLabel lbl) {
        this.label = lbl;
    }
}
