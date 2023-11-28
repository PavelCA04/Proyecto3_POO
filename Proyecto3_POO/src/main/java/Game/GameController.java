
package Game;

import Commands.FireCommand;
import Commands.MoveDownCommand;
import Commands.MoveLeftCommand;
import Commands.MoveRightCommand;
import Commands.MoveUpCommand;
import Commands.PauseCommand;
import Interfaces.ICommand;
import Prototypes.Brick;
import Prototypes.Bush;
import Prototypes.Eagle;
import Interfaces.IObserver;
import Prototypes.FastTank;
import Prototypes.MetalBrick;
import Prototypes.PlayerTank;
import Prototypes.PowerTank;
import Prototypes.Shell;
import Prototypes.SimpleTank;
import Prototypes.Tank;
import Prototypes.TankTank;
import Prototypes.Water;
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
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;




public class GameController implements KeyListener, IObserver{
    
    ///////////////
    // Atributes //
    ///////////////   
    

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
    
    
    
    //////////////////////////// Lists of entities in game ////////////////////////////
    private ArrayList<Brick> bricksArray;
    private ArrayList<MetalBrick> metalBricksArray;
    private ArrayList<Bush> bushesArray;
    private Eagle eagle;
    private ArrayList<Water> waterArray;
    ///////////////////////////////////////////////////////////////////////////////////
    
    
    
    ////////////////////////////////// Matrix board  //////////////////////////////////
    private JPanel[][] boardCells = new JPanel[13][17]; // matriz
    private int level;    
    private int shotsfired;
    private int killedTank;
    private int bonusLevel;
    
    
    
    /////////////////
    // Contructors //
    /////////////////   
    
    public GameController (int level) {
        config = Config.getInstance(); // Singleton de config
        
        this.level = level;
        this.gameView = new GameWindow(this);
        gameView.setVisible(true);
        this.keyMappings = new HashMap<>();
        this.shotsfired = 0;
        this.killedTank = 0;
        this.bonusLevel = 0;
        
        enemThreadArray = new ArrayList<EnemiesThread>();
        shellThreads = new ArrayList<ShellThread>();
        
        keyMappings.put(KeyEvent.VK_W, new MoveUpCommand());
        keyMappings.put(KeyEvent.VK_A, new MoveLeftCommand());
        keyMappings.put(KeyEvent.VK_S, new MoveDownCommand());
        keyMappings.put(KeyEvent.VK_D, new MoveRightCommand());
        keyMappings.put(KeyEvent.VK_SPACE, new FireCommand(this));
        keyMappings.put(KeyEvent.VK_ESCAPE, new PauseCommand(this));
        
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
        gameView.getLvlLabel().setText("Level: " + level);
        gameView.addKeyListener(this);
        gameView.setFocusable(true);
        gameView.setFocusTraversalKeysEnabled(false);
        
        
        playerTank = new PlayerTank("PlayerTank", config,this);
        playerTank.setDir(EnumDirection.UP);
        //SimpleTank simpleTank = new SimpleTank("SimpleTank");
        //FastTank fastTank = new FastTank("FastTank");
        //PowerTank powerTank = new PowerTank("PowerTank");
        //TankTank tankTank = new TankTank("TankTank");
        //PrototypeFactory.addPrototype(playerTank.getId(), playerTank);
        //PrototypeFactory.addPrototype(simpleTank.getId(), simpleTank);
        //PrototypeFactory.addPrototype(fastTank.getId(), fastTank);
        //PrototypeFactory.addPrototype(powerTank.getId(), powerTank);
        //PrototypeFactory.addPrototype(tankTank.getId(), tankTank);
        enableNxtLevelBtn();
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
        Eagle eagle = new Eagle(12, 8);
        placeEagle(12, 8, eagle);
    }
    
    public void nextLevel(){
        int level = this.level;
        PassLevel next = new PassLevel(level);
        next.setVisible(true);
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
    
    public void placeBrick(int posX, int posY, Brick brick){
        bricksArray.add(brick); 
        ImageIcon icon = new ImageIcon("Images\\brick.png");
        JLabel lblBrick = new JLabel(icon);
        lblBrick.setSize(64, 64);
        lblBrick.setBackground(Color.BLACK);
        lblBrick.setForeground(Color.BLACK);
        brick.setLabel(lblBrick); // Vincula el JLabel al objeto del jugador
        boardCells[posX][posY].add(lblBrick); // Coloca en la nueva posición
        gameView.pnlGame.revalidate();                
    }
    
    public void placeMetalBrick(int posX, int posY, MetalBrick mBrick){
        metalBricksArray.add(mBrick); 
        ImageIcon icon = new ImageIcon("Images\\metalBrick.png");
        JLabel lblMBrick = new JLabel(icon);
        lblMBrick.setSize(64, 64);
        lblMBrick.setBackground(Color.BLACK);
        lblMBrick.setForeground(Color.BLACK);
        mBrick.setLabel(lblMBrick); // Vincula el JLabel al objeto del jugador
        boardCells[posX][posY].add(lblMBrick); // Coloca en la nueva posición
        gameView.pnlGame.revalidate();                
    }
    
    public void placeWater(int posX, int posY, Water water){
        waterArray.add(water);
        ImageIcon icon = new ImageIcon("Images\\water.png");
        JLabel lblWater = new JLabel(icon);
        lblWater.setSize(64, 64);
        lblWater.setBackground(Color.BLACK);
        lblWater.setForeground(Color.BLACK);
        water.setLabel(lblWater); // Vincula el JLabel al objeto del jugador
        boardCells[posX][posY].add(lblWater); // Coloca en la nueva posición
        gameView.pnlGame.revalidate();        
    }
    
    public void placeBush(int posX, int posY, Bush bush){
        bushesArray.add(bush);
        ImageIcon icon = new ImageIcon("Images\\bush.png");
        JLabel lblBush = new JLabel(icon);
        lblBush.setSize(64, 64);
        lblBush.setBackground(Color.BLACK);
        lblBush.setForeground(Color.BLACK);
        bush.setLabel(lblBush); // Vincula el JLabel al objeto del jugador
        boardCells[posX][posY].add(lblBush); // Coloca en la nueva posición
        gameView.pnlGame.revalidate();        
    }
    
    public void placeEagle(int posX, int posY, Eagle eagle){
        this.eagle = eagle;
        ImageIcon icon = new ImageIcon("Images\\eagle.png");
        JLabel lblEagle = new JLabel(icon);
        lblEagle.setSize(64, 64);
        lblEagle.setBackground(Color.BLACK);
        lblEagle.setForeground(Color.BLACK);
        eagle.setLabel(lblEagle); // Vincula el JLabel al objeto del jugador
        boardCells[posX][posY].add(lblEagle); // Coloca en la nueva posición
        gameView.pnlGame.revalidate();      
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
    public void enableNxtLevelBtn(){
        gameView.setNxtLvlbtn();
    }
    public void updateShotsLabel(){
        gameView.getShotsFiredLabel().setText("Shots fired : " + shotsfired);
    }
    
    public void updateKilledTanksLabel(){
        gameView.getKilledTanksLabel().setText("Killed Tanks : " + killedTank);
    }
    
    public void updateBonusLabel(){
        gameView.getBonusLabel().setText("Bonus Taken : " + bonusLevel);
    }
    public void pauseGame(){
        gameView.pauseGame();
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

    @Override
    public void notifyObserver(String command, Object source) {
        if (command.equals("fired")) {
            System.out.println("SocialMedia.Celebrity.notifyAllFollowers()");
            shotsfired ++;
            updateShotsLabel();
            
            }        
        if (command.equals("killedTank")) {
            System.out.println("Killed Tank");
            killedTank++;
            updateKilledTanksLabel();
        }
        if (command.equals("bonus")) {
            System.out.println("Game.GameController.notifyObserver()");
            bonusLevel++;
            updateBonusLabel();
        }
        if (command.equals("pause")) {
            System.out.println("Game.GameController.notifyObserver()");
            pauseGame();
        }
    }
    /* Codigo del compa
    public void setBoard() {


        levelMatrix = LevelConstructor.levelChooser(level);
        /
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                              
                labels[i][j] = new JLabel();
                labels[i][j].setOpaque(true);
                
                int identifier = levelMatrix[i][j];
                ImageIcon imageIcon = imageMap.get(identifier);
                
                if (identifier != 0){
                    labels[i][j].setIcon(imageIcon);
                    if (identifier != 5){ //Si es arbol es transparente
                        hasWall[i][j] = true; //Se hace para verificar las colisiones a la hora de mover el tanque enemigo y propio
                        if (identifier == 4){
                            bricks[i][j] = new Wall();
                        }
                    } else {
                        hasGrass[i][j] = true;
                    }
                } else {
                    labels[i][j].setBackground(new java.awt.Color(0, 0, 0));
                }

                labels[i][j].setPreferredSize(new Dimension(labelSize, labelSize));

                GamePlayPanel.add(labels[i][j]);
            }
        }
        
        labels[TankY][TankX].setIcon(new ImageIcon(tank.getIcon())); 
        isTankInPlace[TankY][TankX] = true;
        tanks[TankY][TankX] = tank;
    }
    */
}
