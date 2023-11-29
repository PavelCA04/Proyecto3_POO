
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
    private static final int Width = 17; //15
    private static final int Height = 13;
    private JLabel lblPlayerTank;
    
    
    
    //////////////////////////////// Threads in game //////////////////////////////////
    private ArrayList<EnemiesThread> enemThreadArray; // thread array de enemigos
    private ArrayList<ShellThread> shellThreads; // array de disparos
    ///////////////////////////////////////////////////////////////////////////////////
    
    
    /////////////////////////////// Entities in game //////////////////////////////////
    private PlayerTank playerTank; 
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
    ///////////////////////////////////////////////////////////////////////////////////
    
    
    
    
    
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
        
        bricksArray = new ArrayList<Brick>();
        metalBricksArray = new ArrayList<MetalBrick>();
        bushesArray = new ArrayList<Bush>();
        waterArray = new ArrayList<Water>();
        
        keyMappings.put(KeyEvent.VK_W, new MoveUpCommand());
        keyMappings.put(KeyEvent.VK_A, new MoveLeftCommand());
        keyMappings.put(KeyEvent.VK_S, new MoveDownCommand());
        keyMappings.put(KeyEvent.VK_D, new MoveRightCommand());
        keyMappings.put(KeyEvent.VK_SPACE, new FireCommand(this));
        keyMappings.put(KeyEvent.VK_ESCAPE, new PauseCommand(this));
        
        
        
        _init_();
        configureGamePanel();
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

    private void board(){ 
        // Genera el tablero
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
        
                
        
        int[][] levelMap = LevelMaker.getLevelMap(level); // Obtener el mapa del nivel actual
        
        if (levelMap != null) {
            for (int i = 0; i < levelMap.length; i++) {
                for (int j = 0; j < levelMap[i].length; j++) {
                    int cellType = levelMap[i][j];
                    if(cellType == 2)
                        System.out.println(""+ i + " "+ j + " Tipo de bloque: " + cellType);
                    // Colocar los elementos del mapa en el tablero según el tipo de celda
                    switch (cellType) {
                        case 1:
                            Eagle eagle = new Eagle(i, j);
                            placeEagle(i, j, eagle);
                            break;
                        case 2:
                            Brick brick = new Brick(i, j);
                            System.out.println("\t brickx: " + brick.getPosX() + ", bricky: " + brick.getPosY());
                            placeBrick(i, j, brick);
                            break;
                        case 3:
                            MetalBrick metal = new MetalBrick(i, j);
                            placeMetalBrick(i, j, metal);
                            break;
                        case 4:
                            Bush bush = new Bush(i, j);
                            placeBush(i, j, bush);
                            break;
                        case 5:
                            Water water = new Water(i, j);
                            placeWater(i, j, water);
                            break;
                    }
                }
            }
        }    
        
               
        // Spawns the player in game
        ImageIcon image = new ImageIcon("Images\\playerUp.png");
        drawPlayerTank(10, 8, image);       
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
        if (lbl != null) {
            // Obtén el JLabel en la posición anterior
            Component[] components = boardCells[posX][posY].getComponents();

            // Verifica si hay componentes y si el primer componente es un JLabel
            if (components.length > 0 && components[0] instanceof JLabel) {
                boardCells[posX][posY].remove(lbl);
                gameView.pnlGame.revalidate();
                gameView.pnlGame.repaint();
            }
        }
    }
    
    private boolean inBounds(int posX, int posY){
        return posX >= 0 && posX < Height && posY >= 0 && posY < 17;
    }
    
    private int getCellType(int posX, int posY) {
        JPanel cell = boardCells[posX][posY];

        Component[] components = cell.getComponents();
        for (Component component : components) {
            if (component instanceof JLabel) {
                JLabel lbl = (JLabel) component;

                // Verificar si el JLabel coincide con los elementos en tus ArrayList
                if (bricksArray.stream().anyMatch(brick -> brick.getLabel() == lbl)) {
                    return 2; // Si coincide con un ladrillo
                } else if (metalBricksArray.stream().anyMatch(metal -> metal.getLabel() == lbl)) {
                    return 3; // Si coincide con un metalbrick
                } else if (waterArray.stream().anyMatch(water -> water.getLabel() == lbl)) {
                    return 5; // Si coincide con agua
                } else if (eagle.getLabel() == lbl) {
                    return 1; // Si coincide con el águila
                }
            }
        }

        return -1; // Si no coincide con ninguno de los elementos
    }
    
    private Brick findBrickAtPosition(int posX, int posY) {
        for (Brick brick : bricksArray) {
            if (brick.getPosX() == posX && brick.getPosY() == posY) {
                return brick;
            }
        }
        return null;
    }

    private MetalBrick findMetalBrickAtPosition(int posX, int posY) {
        for (MetalBrick metalBrick : metalBricksArray) {
            if (metalBrick.getPosX() == posX && metalBrick.getPosY() == posY) {
                return metalBrick;
            }
        }
        return null;
    }

    private Water findWaterAtPosition(int posX, int posY) {
        for (Water water : waterArray) {
            if (water.getPosX() == posX && water.getPosY() == posY) {
                return water;
            }
        }
        return null;
    }

    private boolean checkTankCollision(int posX, int posY){
        // Verificar si hay colisión en la posición proporcionada
        int cellType = getCellType(posX, posY); // Obtener el tipo de celda en la posición

        // Verificar si la celda es bloqueante (agua, ladrillo o ladrillo de metal)
        return cellType == 1 || cellType == 2 || cellType == 3 || cellType == 5;
    }
    
    private boolean checkShellCollision(int posX, int posY){
        // Verificar si hay colisión en la posición proporcionada
        int cellType = getCellType(posX, posY); // Obtener el tipo de celda en la posición

        // Verificar si la celda es bloqueante (ladrillo o ladrillo de metal o aguila)
        return cellType == 1 || cellType == 2 || cellType == 3;        
    }
    
    private void handleCollision(Shell shell, int posX, int posY) {
        int cellType = getCellType(posX, posY); // Obtener el tipo de celda en la posición

        switch (cellType) {
            case 1:
                if(eagle != null){
                    eagle.decreaseHealth();
                    if (eagle.getHp() <= 0) {
                        // Eliminar el ladrillo del tablero y de la lista de ladrillos
                        erasePreviousPosition(posX, posY, eagle.getLabel());
                    }                    
                }
            case 2: // Si colisiona con un ladrillo
                Brick collidedBrick = findBrickAtPosition(posX, posY);
                if (collidedBrick != null) {
                    collidedBrick.decreaseHealth(); // Reducir la vida del ladrillo
                    if (collidedBrick.getHp() <= 0) {
                        // Eliminar el ladrillo del tablero y de la lista de ladrillos
                        erasePreviousPosition(posX, posY, collidedBrick.getLabel());
                        bricksArray.remove(collidedBrick);
                    }
                }
                break;

        }
    }    
    
    public void movePlayerTank(int deltaX, int deltaY, ImageIcon icon, EnumDirection dir){ 
        if(playerTank.canMove()){ // Verifies movement cooldown
            playerTank.setDir(dir); // Sets the new direction the tank is facing
            
            int posX = playerTank.getPosX() + deltaX;
            int posY = playerTank.getPosY() + deltaY;

            if (inBounds(posX, posY)){ // Verificar los límites del tablero
                if (!checkTankCollision(posX, posY)) { // Verificar la colisión
                    erasePreviousPosition(playerTank.getPosX(), playerTank.getPosY(), playerTank.getLabel()); // Eliminar la posición anterior

                    playerTank.setPosX(posX);
                    playerTank.setPosY(posY);

                    drawPlayerTank(posX, posY, icon); // Dibujar el tanque en el tablero

                    // Actualizar el tiempo del último movimiento
                    playerTank.updateLastMoveTime();
                }
            } 
        }
    }
    
public void moveShell(ShellThread st, Shell shell, EnumDirection dir, int posX, int posY) {
    
    ImageIcon icon = null;
    int nextPosX = posX;
    int nextPosY = posY;

    switch (dir) {
        case UP:
            icon = new ImageIcon("Images\\bulletUp.png");
            nextPosX = posX - 1;
            nextPosY = posY;
            break;
        case DOWN:
            icon = new ImageIcon("Images\\bulletDown.png");
            nextPosX = posX + 1;
            nextPosY = posY;
            break;
        case LEFT:
            icon = new ImageIcon("Images\\bulletLeft.png");
            nextPosX = posX;
            nextPosY = posY - 1;
            break;
        case RIGHT:
            icon = new ImageIcon("Images\\bulletRight.png");
            nextPosX = posX;
            nextPosY = posY + 1;
            break;
    }
    
    if (!inBounds(nextPosX, nextPosY)) {
        erasePreviousPosition(posX, posY, shell.getLabel());
        st.setIsRunning(false);
        System.out.println("Salio");
        return;
    }
    
    // Verificar si hay colisión en la próxima posición
    if (checkShellCollision(nextPosX, nextPosY)) {
        handleCollision(shell, nextPosX, nextPosY);
        erasePreviousPosition(posX, posY, shell.getLabel());
        st.setIsRunning(false);
        return;
    }

    // Mover la bala a la próxima posición
    shell.setPosX(nextPosX);
    shell.setPosY(nextPosY);

    if (inBounds(shell.getPosX(), shell.getPosY())) {
        if (shell.getLabel() != null) {
            erasePreviousPosition(posX, posY, shell.getLabel());
        }
        drawShell(shell, shell.getPosX(), shell.getPosY(), icon);
    } else {
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
    public void enableNxtLevelBtn() {
    if (level < 8) {
        gameView.setNxtLvlbtn();
    } else {
        gameView.setNxtLvlbtnfalse();
    }
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
}
