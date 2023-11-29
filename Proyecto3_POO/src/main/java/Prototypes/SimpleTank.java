
package Prototypes;

import Game.Config;


public class SimpleTank extends Enemy implements Tank{

    private Config config;  
    int hp;
    private long tankVel;
    private long fireRate;  
    
    
    

    public SimpleTank(String id, Config config) {
        super(id);
        this.hp = 2;
        this.config = config;
        this.tankVel = config.getSimpleTankSpeed();
        this.fireRate = config.getSimpleTankFireRate();       
    }
    



    @Override
    public Tank clone() {
        return new FastTank(this.id, this.config);
    }
    
    @Override
    public Tank deepClone() {
        return clone();    
    }    
    
    @Override
    public int getResistance() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int getHp() {
        return hp;
    }

    public long getTankVel() {
        return tankVel;
    }

    public long getFireRate() {
        return fireRate;
    }
    
    
}
