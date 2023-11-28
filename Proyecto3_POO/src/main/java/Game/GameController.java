
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
import Prototypes.Shell;
import Prototypes.SimpleTank;
import Prototypes.Tank;
import Prototypes.TankTank;
import Threads.EnemiesThread;
import Threads.PlayerThread;
import Threads.ShellThread;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.ObjectInputFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;




public class GameController implements KeyListener{
    
    ///////////////
    // Atributes //
    ///////////////   
    
    private GameModel gameModel;
    private GameWindow gameView;
    private final Map<Integer, ICommand> keyMappings;
    
    private Config config; // Singleton configuration
    private static final int Sprite_Size = 64;
    private static final int Width = 15;
    private static final int Height = 13;
    private JLabel lblPlayerTank;
    
    private PlayerTank playerTank; 
    
    private ArrayList<EnemiesThread> enemThreadArray; // thread array de enemigos
    private ArrayList<ShellThread> shellThreads; // array de disparos
    
    private JPanel[][] boardCells = new JPanel[13][17]; // matriz
    private int level;    
    
    
    /////////////////
    // Contructors //
    /////////////////   
    
    public GameController (GameModel gameModel, GameWindow gameView, int level) {
        config = Config.getInstance(); // Singleton de config
        
        this.level = level;
        this.gameModel = gameModel;
        this.gameView = gameView;
        this.keyMappings = new HashMap<>();
        
        enemThreadArray = new ArrayList<EnemiesThread>();
        shellThreads = new ArrayList<ShellThread>();
        
        keyMappings.put(KeyEvent.VK_W, new MoveUpCommand(gameModel));
        keyMappings.put(KeyEvent.VK_A, new MoveLeftCommand(gameModel));
        keyMappings.put(KeyEvent.VK_S, new MoveDownCommand(gameModel));
        keyMappings.put(KeyEvent.VK_D, new MoveRightCommand(gameModel));
        keyMappings.put(KeyEvent.VK_SPACE, new FireCommand(gameModel));
        keyMappings.put(KeyEvent.VK_ESCAPE, new PauseCommand(gameModel));
        
        _init_();
        configureGamePanel();
    
        
        
        //drawPlayerTank(new PlayerTank());
    }    
    
    

    
    /////////////
    // Methods //
    /////////////  
    private void configureGamePanel(){
        board();
        gameView.pnlStats.setBackground(Color.GRAY);
    }
    
    // inicia el binding con la vista
    private void _init_ (){
        gameView.setTitle("Battle City");
        
        gameView.addKeyListener(this);
        gameView.setFocusable(true);
        gameView.setFocusTraversalKeysEnabled(false);
        
        
        playerTank = new PlayerTank("PlayerTank", config);
        playerTank.setDir(EnumDirection.UP);
        //SimpleTank simpleTank = new SimpleTank("SimpleTank");
        //FastTank fastTank = new FastTank("FastTank");
        //PowerTank powerTank = new PowerTank("PowerTank");
        //TankTank tankTank = new TankTank("TankTank");
        PrototypeFactory.addPrototype(playerTank.getId(), playerTank);
        //PrototypeFactory.addPrototype(simpleTank.getId(), simpleTank);
        //PrototypeFactory.addPrototype(fastTank.getId(), fastTank);
        //PrototypeFactory.addPrototype(powerTank.getId(), powerTank);
        //PrototypeFactory.addPrototype(tankTank.getId(), tankTank);        
    }

    private void board(){  // Genera el tablero
        gameView.pnlGame.setLayout(new GridLayout(Height, Width)); // Crear grid
        gameView.pnlGame.setBackground(Color.BLACK);
        
        for(int i = 0; i < 13; i++){ // Matriz 17x13
            for(int j = 0; j<17; j++){
                JPanel cell = new JPanel();
                cell.setLayout(new GridLayout(1,1));
                cell.setBackground(Color.BLACK);
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                
                boardCells[i][j] = cell;
                gameView.pnlGame.add(cell);
            }
        }
        ImageIcon image = new ImageIcon("Images\\playerUp.png");
        drawPlayerTank(10, 8, image);
    }
     
    public void drawPlayerTank(int posX, int posY, ImageIcon icon) {
        // Elimina el JLabel anterior si existe
        if (lblPlayerTank != null) {
            boardCells[playerTank.getPosX()][playerTank.getPosY()].remove(lblPlayerTank);
            lblPlayerTank = null; // Establece el JLabel actual como nulo
        }

        // Crea un nuevo JLabel y lo coloca en la nueva posición
        lblPlayerTank = new JLabel(icon);
        lblPlayerTank.setSize(64, 64);
        lblPlayerTank.setBackground(Color.red);
        lblPlayerTank.setForeground(Color.red);
        playerTank.setLabel(lblPlayerTank); // Vincula el JLabel al objeto del jugador
        playerTank.setPosX(posX);
        playerTank.setPosY(posY);
        boardCells[posX][posY].add(lblPlayerTank); // Coloca en la nueva posición
        gameView.pnlGame.revalidate();
    }

    public void drawShell(Shell shell, int posX, int posY, ImageIcon icon){

        // Crea un nuevo JLabel y lo coloca en la nueva posición
        JLabel lblShell = new JLabel(icon);
        lblShell.setSize(64, 64);
        lblShell.setBackground(Color.BLACK);
        lblShell.setForeground(Color.BLACK);
        shell.setLabel(lblShell); // Vincula el JLabel al objeto del jugador
        boardCells[posX][posY].add(lblShell); // Coloca en la nueva posición
        gameView.pnlGame.revalidate();        
    }
    
    public void drawEnemyTank(int posX, int posY){
        
    }
            
    public void drawEnviroment(int posX, int posY){ // Draws bushes, bricks, water and eagle
        
    }
    
    public int[] findPos(JLabel lbl){
        // Find the current position of the button in the boardCells array
        int[] position = {-1, -1};

        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 25; j++) {
                if (boardCells[i][j].getComponentCount() > 0) {
                    Component component = boardCells[i][j].getComponent(0);
                    if (component instanceof JLabel && component.equals(lbl)) {
                        position[0] = i;
                        position[1] = j;
                        return position;
                    }
                }
            }
        }    
        
        return position;
    }   
    
    public void erasePreviousPosition(int posX, int posY, JLabel lbl) {
        // Obtén el JLabel en la posición anterior
        Component[] components = boardCells[posX][posY].getComponents();

        // Verifica si hay componentes y si el primer componente es un JLabel
        if (components.length > 0 && components[0] instanceof JLabel) {
            boardCells[posX][posY].remove(lbl);
            gameView.pnlGame.revalidate();
            gameView.pnlGame.repaint();
        }
    }
    
    private boolean inBounds(int posX, int posY){
        return posX >= 0 && posX < Height && posY >= 0 && posY < 17;
    }
    
    public void movePlayerTank(int deltaX, int deltaY, ImageIcon icon, EnumDirection dir){ 
        if(playerTank.canMove()){ // Verifies movement cooldown
            playerTank.setDir(dir); // Sets the new direction the tank is facing
            
            int posX = playerTank.getPosX() + deltaX;
            int posY = playerTank.getPosY() + deltaY;

            if (inBounds(posX, posY)){ // Verifies limits of board
                erasePreviousPosition(playerTank.getPosX(), playerTank.getPosY(), playerTank.getLabel()); // Elimina el JLabel anterior

                playerTank.setPosX(posX);
                playerTank.setPosY(posY);

                drawPlayerTank(posX, posY, icon); // Draws the player on board
                
                // Actualizar el tiempo del último movimiento
                playerTank.updateLastMoveTime();
            } 
        }
    }
    
    public void moveShell(ShellThread st, Shell shell, EnumDirection dir, int posX, int posY){
        ImageIcon icon = null;
        switch (dir){
            case UP:
                icon = new ImageIcon("Images\\bulletUp.png");
                
                shell.setPosX(posX-1);
                shell.setPosY(posY);
                System.out.println(""+ shell.getPosX() + " " + shell.getPosY());                
                break;
            case DOWN:
                icon = new ImageIcon("Images\\bulletDown.png"); 
                shell.setPosX(posX+1);
                shell.setPosY(posY);
                System.out.println(""+ shell.getPosX() + " " + shell.getPosY()); 
                break;
            case LEFT:
                icon = new ImageIcon("Images\\bulletLeft.png");  
                shell.setPosX(posX);
                shell.setPosY(posY-1);
                System.out.println(""+ shell.getPosX() + " " + shell.getPosY()); 
                break;
            case RIGHT:
                icon = new ImageIcon("Images\\bulletRight.png"); 
                shell.setPosX(posX);
                shell.setPosY(posY+1);
                System.out.println(""+ shell.getPosX() + " " + shell.getPosY()); 
                break;              
        }
        
        if (inBounds(shell.getPosX(), shell.getPosY())){
            if(shell.getLabel() != null)
                erasePreviousPosition(posX, posY, shell.getLabel()); // Elimina el JLabel anterior
            
            drawShell(shell, shell.getPosX(), shell.getPosY(), icon);
        }else{
            erasePreviousPosition(posX, posY, shell.getLabel());
            st.setIsRunning(false);
            System.out.println("Salio");
        }
    }
    
    public void fireShellPlayer(){
        Shell shell = new Shell(playerTank.getPosX(), playerTank.getPosY());
        ShellThread st = new ShellThread(playerTank.getFireRate(), shell, playerTank.getDir(), this);    
        st.start(); // Init shell thread
    }
    
    public void fireShellSimple(){
        //Shell shell = new Shell();
        //ShellThread st = new ShellThread(shell, EnumDirection.UP, Width, Width, this);
        //st.start(); // Init shell thread
    }
    
    public void fireShellFast(){
        //Shell shell = new Shell();
        //ShellThread st = new ShellThread(shell, EnumDirection.UP, Width, Width, this);
    }
    
    public void fireShellPower(){
        //Shell shell = new Shell();
        //ShellThread st = new ShellThread(shell, EnumDirection.UP, Width, Width, this);
    }
    
    public void fireShellTank(){
        //Shell shell = new Shell();
        //ShellThread st = new ShellThread(shell, EnumDirection.UP, Width, Width, this);
    }
    
    
    
    public void moveEnemyTank(){
        
    }
    
    public void enemyEvents(JLabel lbl){
        
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
