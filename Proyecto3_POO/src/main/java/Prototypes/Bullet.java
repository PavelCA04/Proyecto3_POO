
package Prototypes;


public class Bullet {
    
    public static  int velX = 12;
    public static  int velY = 12;
    public static final int width = 10;
    public static final int length = 10;
    private int posX, posY;
    private boolean alive = true;  
    
    public Bullet(int posX, int posY) { 
        this.posX = posX;
        this.posY = posY;
    }
}
