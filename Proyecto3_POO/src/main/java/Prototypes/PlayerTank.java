
package Prototypes;


public class PlayerTank implements Tank{
    
    private String id;
    private int hp;
    private int tankVel;
    private int shellVel;
    private int score;
    
    
    
    
    public PlayerTank(){       
    }
    public PlayerTank(String id) {
        this();
        this.id = id;
        this.hp = 3;
        this.tankVel = 5;
        this.shellVel = 10;
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

    public int getHp() {
        return hp;
    }

    public int getTankVel() {
        return tankVel;
    }

    public int getShellVel() {
        return shellVel;
    }

    public int getScore() {
        return score;
    }
    
    public void setId(String id) {
        this.id = id;
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

    public void setScore(int score) {
        this.score = score;
    }   
}
