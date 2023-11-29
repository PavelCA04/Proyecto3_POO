
package Prototypes;

import Game.Config;
import Game.EnumDirection;
import Game.GameController;
import Interfaces.IObservable;
import Interfaces.IObserver;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;


public class PlayerTank implements Tank, IObservable{
    
    private List<IObserver> observers = new ArrayList<>();
    private String id;
    private Config config;
    private EnumDirection dir;
    private int posX, posY;
    private int hp;
    private long tankVel;
    private long fireRate;
    private int score;
    JLabel label;
    private long lastMoveTime;
    private long lastFireTime;
    private GameController controller;
    
    
    public PlayerTank(){       
    }
    public PlayerTank(String id, Config config, GameController controller) {
        this();
        this.id = id;        
        this.hp = 3;
        this.tankVel = config.getPlayerMovementSpeed();
        this.fireRate = config.getPlayerFireRate();
        this.controller = controller;
        addObserver(controller);
        lastMoveTime = System.currentTimeMillis(); // Inicializa lastMoveTime con el tiempo actual
        lastFireTime = System.currentTimeMillis(); // Inicializa lastFireTime con el tiempo actual
    }
    
    
    @Override
    public Tank clone() {
        return new PlayerTank(this.id, this.config,this.controller);//No se si el this.controller vaya aca,es para que tenga la conexcion para el notify
    }

    @Override
    public Tank deepClone() {
        return clone();    
    }   

    @Override
    public int getResistance() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public boolean canMove() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - lastMoveTime;
        pruebaObserver();

        return elapsedTime >= tankVel; // Devuelve true si ha pasado suficiente tiempo
    }
    
    public void updateLastMoveTime() {
        lastMoveTime = System.currentTimeMillis(); // Actualiza el tiempo del último movimiento
    }
    
    public boolean canFire(){
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - lastMoveTime;//Creo que aqui va lastfiretime

        return elapsedTime >= fireRate; // Devuelve true si ha pasado suficiente tiempo        
    }
    
    public void updateLastFireTime() {
        lastFireTime = System.currentTimeMillis(); // Actualiza el tiempo del último movimiento
    }
    
    public Config getConfig() {
        return config;
    }

    public long getFireRate() {
        return fireRate;
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

    public EnumDirection getDir() {
        return dir;
    }

    public long getTankVel() {
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

    public void setDir(EnumDirection dir) {
        this.dir = dir;
    }
    public void pruebaObserver(){
        notifyAllObservers("bonus", this);
    }

   @Override
    public void addObserver(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyAllObservers(String command, Object source) {
        for (IObserver observer : observers) {
            observer.notifyObserver(command, source);
        }
    }
}
