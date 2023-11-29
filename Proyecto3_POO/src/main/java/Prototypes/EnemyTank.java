
package Prototypes;

import Game.Config;
import Game.EnumDirection;
import javax.swing.JLabel;


public class EnemyTank implements Tank {
    
    private Config config; 
    public TankTypeEnum type;
    int hp;
    private EnumDirection dir;
    public int posX, posY;
    JLabel label;
    private long tankVel;
    private long fireRate;    
    
    

    public EnemyTank(Config config, TankTypeEnum type, int hp, EnumDirection dir) {
        this.hp = hp;
        this.config = config;
        this.dir = dir;
        this.type = type;
        switch (type) {
            case SIMPLE:
                this.tankVel = config.getSimpleTankSpeed();
                this.fireRate = config.getSimpleTankFireRate();
                break;
            case FAST:
                this.tankVel = config.getFastTankSpeed();
                this.fireRate = config.getFastTankFireRate();
                break;
            case POWER:
                this.tankVel = config.getPowerTankSpeed();
                this.fireRate = config.getPowerTankFireRate();
                break;
            case TANK:
                this.tankVel = config.getTankTankSpeed();
                this.fireRate = config.getTankTankFireRate();
                break;
        }

    }
    
    
    
        
    @Override
    public Tank clone() {
        return new EnemyTank(this.config, this.type, this.hp, this.dir);
    }
    
    @Override
    public Tank deepClone() {
        return clone();    
    }    
    
    @Override
    public int getResistance() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Config getConfig() {
        return config;
    }

    public TankTypeEnum getType() {
        return type;
    }

    public int getHp() {
        return hp;
    }

    public EnumDirection getDir() {
        return dir;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public JLabel getLabel() {
        return label;
    }

    public long getTankVel() {
        return tankVel;
    }

    public long getFireRate() {
        return fireRate;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public void setType(TankTypeEnum type) {
        this.type = type;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setDir(EnumDirection dir) {
        this.dir = dir;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

    public void setTankVel(long tankVel) {
        this.tankVel = tankVel;
    }

    public void setFireRate(long fireRate) {
        this.fireRate = fireRate;
    }  
}
