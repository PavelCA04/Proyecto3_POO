
package Game;

import Commands.FireCommand;
import Commands.MoveDownCommand;
import Commands.MoveLeftCommand;
import Commands.MoveRightCommand;
import Commands.MoveUpCommand;
import Commands.PauseCommand;
import Interfaces.ICommand;
import Prototypes.FastTank;
import Prototypes.PlayerTank;
import Prototypes.PowerTank;
import Prototypes.SimpleTank;
import Prototypes.Tank;
import Prototypes.TankTank;
import Threads.EnemiesThread;
import Threads.PlayerThread;
import java.awt.Color;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;




public class GameController implements KeyListener{
    
    ///////////////
    // Atributes //
    ///////////////   
    
    private GameModel gameModel;
    private GameWindow gameView;
    private final Map<Integer, ICommand> keyMappings;
    
    private int playerPosX = 0;
    private int playerPosY = 0;
    
    private JLabel playerTankLabel;
    
    private PlayerThread playerThread; 
    private PlayerTank playerTank; 
    
    private ArrayList<EnemiesThread> enemThreadArray; // thread array de defenzas
    private ArrayList<Tank> enemyTankArray; // array de defenzas 
    
    private int level;    
    
    
    /////////////////
    // Contructors //
    /////////////////   
    
    public GameController (GameModel gameModel, GameWindow gameView) {
        this.gameModel = gameModel;
        this.gameView = gameView;
        this.keyMappings = new HashMap<>();
        
        keyMappings.put(KeyEvent.VK_W, new MoveUpCommand(gameModel));
        keyMappings.put(KeyEvent.VK_A, new MoveLeftCommand(gameModel));
        keyMappings.put(KeyEvent.VK_S, new MoveDownCommand(gameModel));
        keyMappings.put(KeyEvent.VK_D, new MoveRightCommand(gameModel));
        keyMappings.put(KeyEvent.VK_SPACE, new FireCommand(gameModel));
        keyMappings.put(KeyEvent.VK_ESCAPE, new PauseCommand(gameModel));
        
        _init_();
        
        
        spawnPlayerTank(new PlayerTank());
    }    
    
    

    
    /////////////
    // Methods //
    /////////////  

    // inicia el binding con la vista
    private void _init_ (){
        gameView.pnlGame.setBackground(Color.BLACK);
        gameView.pnlStats.setBackground(Color.GRAY);
        gameView.setTitle("Tank 1990");
        
        gameView.addKeyListener(this);
        gameView.setFocusable(true);
        gameView.setFocusTraversalKeysEnabled(false);
        
        
        PlayerTank playerTank = new PlayerTank("PlayerTank");
        SimpleTank simpleTank = new SimpleTank("SimpleTank");
        FastTank fastTank = new FastTank("FastTank");
        PowerTank powerTank = new PowerTank("PowerTank");
        TankTank tankTank = new TankTank("TankTank");
        PrototypeFactory.addPrototype(playerTank.getId(), playerTank);
        PrototypeFactory.addPrototype(simpleTank.getId(), simpleTank);
        PrototypeFactory.addPrototype(fastTank.getId(), fastTank);
        PrototypeFactory.addPrototype(powerTank.getId(), powerTank);
        PrototypeFactory.addPrototype(tankTank.getId(), tankTank);        
    }

    public void spawnPlayerTank(PlayerTank pTank){
        ImageIcon image = new ImageIcon("Images\\playerUp.png"); 
        playerTankLabel = new JLabel(image);
        playerTankLabel.setSize(64, 64);
        playerTankLabel.setBackground(Color.red);
        playerTankLabel.setForeground(Color.red);
        playerTankLabel.setLocation(playerPosX, playerPosY);
        
        gameView.pnlGame.add(playerTankLabel);
        gameView.pnlGame.revalidate();
    }

    public void movePlayerTank(int deltaX, int deltaY, ImageIcon icon){
        playerPosX += deltaX;
        playerPosY += deltaY;
        
        // Updates the position of the label
        if (playerTankLabel != null){
            playerTankLabel.setIcon(icon);
            playerTankLabel.setLocation(playerPosX, playerPosY);
        }
    }
    
    public void moveEnemyTank(int posX, int posY){
        
    }
            
    public void drawEnviroment(){ // Draws bushes, bricks, water and eagle
        
    }
    
    @Override
    public void keyPressed(KeyEvent e){
        int keyCode = e.getKeyCode();
        
        ICommand command = keyMappings.get(keyCode);
        
        if (command != null){
            command.execute(this);
        }        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not necessary 
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not necessary  
    }
    

}
