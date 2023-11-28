
package Prototypes;


public class TankTank implements Tank{
    private String id;
    private int posX, posY;
    private int hp;
    private int tankVel;
    private int shellVel;
    
    
    
    
    public TankTank(){
    }
    public TankTank(String id, int posX, int posY) {
        this();
        this.id = id;
        this.posX = posX;
        this.posY = posY;        
        this.hp = 4;
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
    public int getResistance() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getId() {
        return id;
    }


    
}
