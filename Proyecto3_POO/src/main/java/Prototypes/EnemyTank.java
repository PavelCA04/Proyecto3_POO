
package Prototypes;

import Game.Config;
import Game.EnumDirection;
import static Game.EnumDirection.DOWN;
import static Game.EnumDirection.LEFT;
import static Game.EnumDirection.RIGHT;
import static Game.EnumDirection.UP;
import static Prototypes.TankTypeEnum.FAST;
import static Prototypes.TankTypeEnum.POWER;
import static Prototypes.TankTypeEnum.SIMPLE;
import static Prototypes.TankTypeEnum.TANK;
import javax.swing.ImageIcon;
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
    private ImageIcon icon;
    private long lastFireTime = 0; // Variable para controlar el tiempo del último disparo

    

    public EnemyTank(Config config, TankTypeEnum type, int hp, EnumDirection dir) {
        this.hp = hp;
        this.config = config;
        this.type = type;
        this.setDir(dir);
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
        this.setIcon(dir);
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

    private void setIcon(EnumDirection dir) {
        String baseName = null;
        switch (this.type) {
            case SIMPLE:
                baseName = "simple";
                break;
            case FAST:
                baseName = "fast";
                break;
            case POWER:
                baseName = "power";
                break;
            case TANK:
                baseName = "tank";
                break;
        }  
        ImageIcon icon = null;
        switch (dir) {
            case UP:
                icon = new ImageIcon("Images\\" + baseName + "Up.png");
                break;
            case DOWN:
                icon = new ImageIcon("Images\\" + baseName + "Down.png");
                break;
            case LEFT:
                icon = new ImageIcon("Images\\" + baseName + "Left.png");
                break;
            case RIGHT:
                icon = new ImageIcon("Images\\" + baseName + "Right.png");
                break;
        }
        
        this.icon = icon;
    }

    public ImageIcon getIcon() {
        return icon;
    }
    
    public void decreaseHealth(){
        this.hp--;
    }
    
    public void setLastFireTime(long time) {
        this.lastFireTime = time;
    }

    public long getLastFireTime() {
        return lastFireTime;
    }
}
