
package Prototypes;


public class FastTank extends Enemy implements Tank {
    
    private int hp;
    private int tankVel;
    private int shellVel;    
    
    
    

    public FastTank(String id, int posX, int posY) {
        super(id, posX, posY);
        this.hp = 1;
        this.tankVel = 5;
        this.shellVel = 10;
    }
    
    
    
    
    
    @Override
    public Tank clone() {
        return new FastTank(this.id, this.posX, this.posY);
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
    
}