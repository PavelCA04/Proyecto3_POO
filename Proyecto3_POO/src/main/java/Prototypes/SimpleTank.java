
package Prototypes;


public class SimpleTank implements Tank{
    private String id;
    private int hp;
    private int tankVel;
    private int shellVel;
    
    
    
    
    public SimpleTank(){
    }
    public SimpleTank(String id) {
        this();
        this.id = id;
        this.hp = 2;
        this.tankVel = 5;
        this.shellVel = 10;
    }
    



    @Override
    public Tank clone() {
        return new FastTank(this.id);
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
