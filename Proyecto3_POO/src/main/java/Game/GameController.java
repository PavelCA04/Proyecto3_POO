
package Game;

import Bonus.BombBonus;
import Bonus.ReinforceBonus;
import Bonus.ShieldBonus;
import Bonus.StarBonus;
import Bonus.TankBonus;
import Bonus.TimeBonus;
import Commands.FireCommand;
import Commands.MoveDownCommand;
import Commands.MoveLeftCommand;
import Commands.MoveRightCommand;
import Commands.MoveUpCommand;
import Commands.PauseCommand;
import static Game.EnumDirection.DOWN;
import static Game.EnumDirection.LEFT;
import static Game.EnumDirection.RIGHT;
import static Game.EnumDirection.UP;
import Interfaces.ICommand;
import Prototypes.Brick;
import Prototypes.Bush;
import Prototypes.Eagle;
import Interfaces.IObserver;
import Interfaces.IStrategy;
import Prototypes.EnemyTank;
import Prototypes.MetalBrick;
import Prototypes.PlayerTank;
import Prototypes.Shell;
import Prototypes.TankTypeEnum;
import Prototypes.Water;
import Threads.EnemiesThread;
import Threads.EnemySpawnThread;
import Threads.ShellEnemThread;
import Threads.ShellThread;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;




public class GameController implements KeyListener, IObserver{
    
    ///////////////
    // Atributes //
    ///////////////   
    

    private GameWindow gameView;
    private final Map<Integer, ICommand> keyMappings;
    private IStrategy bonusStrategy;
    
    private Config config; // Singleton configuration
    private static final int Sprite_Size = 64;
    private static final int Width = 17; //15
    private static final int Height = 13;
    private JLabel lblPlayerTank;
    private int currentEnemiesCount = 0;
    private static final String[] tankTypes = {"SimpleTank", "FastTank", "PowerTank", "TankTank"};
    private static final int[][] spawnPoints = {{0, 1}, {0, 8}, {0, 15}};
    private static final String[] bonusTypes = {"Bomb", "Reinforce", "Shield", "Star", "Tank", "Time"};

    
    
    //////////////////////////////// Threads in game //////////////////////////////////
    private ArrayList<IStrategy> bonusStrategies;
    private ArrayList<EnemiesThread> enemThreadArray; // thread array de enemigos
    private ArrayList<ShellThread> shellThreads; // array de disparos
    private EnemySpawnThread enemySpawnThread;
    ///////////////////////////////////////////////////////////////////////////////////
    
    
    /////////////////////////////// Entities in game //////////////////////////////////
    private PlayerTank playerTank; 
    private ArrayList<EnemyTank> enemiesArray;
    private ArrayList<Brick> bricksArray;
    private ArrayList<MetalBrick> metalBricksArray;
    private ArrayList<Bush> bushesArray;
    private Eagle eagle;
    private ArrayList<Water> waterArray;
    private ArrayList<Bonus> bonusArray;
    ///////////////////////////////////////////////////////////////////////////////////
    
    
    
    ////////////////////////////////// Matrix board  //////////////////////////////////
    private JPanel[][] boardCells = new JPanel[13][17]; // matriz
    private int level;    
    private int shotsfired;
    private int killedTank;
    private int bonusLevel;
    private int playerLife;
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
        this.playerLife = 3;
        
        enemThreadArray = new ArrayList<EnemiesThread>();
        shellThreads = new ArrayList<ShellThread>();
        bonusStrategies = new ArrayList<>();
        
        enemiesArray = new ArrayList<EnemyTank>();
        bricksArray = new ArrayList<Brick>();
        metalBricksArray = new ArrayList<MetalBrick>();
        bushesArray = new ArrayList<Bush>();
        waterArray = new ArrayList<Water>();
        bonusArray = new ArrayList<Bonus>();
        
        
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
        
        EnemyTank simpleTank = new EnemyTank(config, TankTypeEnum.SIMPLE, 2, DOWN);
        EnemyTank fastTank = new EnemyTank(config, TankTypeEnum.FAST, 1, DOWN);
        EnemyTank powerTank = new EnemyTank(config, TankTypeEnum.POWER, 1, DOWN);
        EnemyTank tankTank = new EnemyTank(config, TankTypeEnum.TANK, 4, DOWN);

        PrototypeFactory.addPrototype("SimpleTank", simpleTank);
        PrototypeFactory.addPrototype("FastTank", fastTank);
        PrototypeFactory.addPrototype("PowerTank", powerTank);
        PrototypeFactory.addPrototype("TankTank", tankTank);
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

                    // Colocar los elementos del mapa en el tablero según el tipo de celda
                    switch (cellType) {
                        case 1:
                            Eagle eagle1 = new Eagle(i, j);
                            placeEagle(i, j, eagle1);
                            break;
                        case 2:
                            Brick brick = new Brick(i, j);
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
        spawnBonuses();
        ImageIcon image = new ImageIcon("Images\\playerUp.png");
        drawPlayerTank(10, 8, image);
        startEnemySpawning();
    }
    
    public void nextLevel(){
        int level = this.level;
        PassLevel next = new PassLevel(level);
        next.setVisible(true);
    }
    
    public void spawnBonuses(){
        
        ArrayList<String> bonusTypesList = new ArrayList<>(Arrays.asList(bonusTypes));

        // Mezclar aleatoriamente los tipos de bonificaciones
        Collections.shuffle(bonusTypesList);

        // Tomar los primeros cuatro tipos mezclados para colocar las bonificaciones
        for (int i = 0; i < 4; i++) {
            String bonusType = bonusTypesList.get(i);

            // Obtener posiciones aleatorias para colocar las bonificaciones
            int[] randomCoordinates = getRandomCoordinates();
            int randomX = randomCoordinates[1];
            int randomY = randomCoordinates[0];

            // Verificar si ya hay una bonificación en esa posición
            System.out.println(""+ randomY+ " "+ randomX);
            while (getCellType(randomX, randomY) != -1) {
                randomCoordinates = getRandomCoordinates();
                randomX = randomCoordinates[1];
                randomY = randomCoordinates[0];
            }       
            
            placeBonus(randomX, randomY, bonusType);
        }        
    }   
    
    private int[] getRandomCoordinates() {
        int[] coordinates = new int[2];
        coordinates[0] = (int) (Math.random() * 16); // Generar una coordenada Y aleatoria (entre 0 y 12)
        coordinates[1] = (int) (Math.random() * 12); // Generar una coordenada X aleatoria (entre 0 y 16)
        return coordinates;
    }
    
    public int getCurrentEnemiesCount() {
        return currentEnemiesCount;
    }
    
    private void startEnemySpawning() {
        enemySpawnThread = new EnemySpawnThread(this);
        enemySpawnThread.start();
    }
    
    private String getRandomEnemyType(){
        Random random = new Random();
        int index = random.nextInt(tankTypes.length);
        return tankTypes[index];        
    }
    
    private static int[] getRandomSpawnPosition() {
        Random random = new Random();
        int index = random.nextInt(spawnPoints.length);
        return spawnPoints[index];
    }
    
    public void spawnEnemyTank() {
        String rand = getRandomEnemyType();
        EnemyTank enemyTank = (EnemyTank)PrototypeFactory.getPrototype(rand);
        enemiesArray.add(enemyTank);
        EnemiesThread et = new EnemiesThread(enemyTank, this);
        enemThreadArray.add(et);
        et.start();
        
        int[] spawnPosition = getRandomSpawnPosition();

        if (enemyTank != null && spawnPosition != null) {
            TankTypeEnum enemId = enemyTank.getType();
            String path = "";
            switch (enemId) {
                case SIMPLE:
                    path = "Images\\simpleDown.png";
                    break;
                case FAST:
                    path = "Images\\fastDown.png";
                    break;
                case POWER:
                    path = "Images\\powerDown.png";
                    break;
                case TANK:
                    path = "Images\\tankDown.png";
                    break;
            }
            currentEnemiesCount++;
            
            if (currentEnemiesCount > 19){
                enemySpawnThread.setIsRunning(false);
            }
            
            enemyTank.setDir(EnumDirection.DOWN);
            ImageIcon enemyIcon = new ImageIcon(path); // Reemplaza con el icono real
            drawEnemyTank(spawnPosition[0], spawnPosition[1], enemyIcon, enemyTank);
        }
    }
    
    public void drawEnemyTank(int posX, int posY, ImageIcon icon, EnemyTank enemy) {
        JLabel lblEnemyTank = new JLabel(icon);
        lblEnemyTank.setSize(64, 64);
        lblEnemyTank.setBackground(Color.BLACK);
        lblEnemyTank.setForeground(Color.BLACK);
        enemy.setLabel(lblEnemyTank);
        enemy.setPosX(posX);
        enemy.setPosY(posY);
        boardCells[posX][posY].add(lblEnemyTank);
        gameView.pnlGame.revalidate();        
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
    
    public void placeBonus(int posX, int posY, String type){ 
        ImageIcon icon = null;
        switch(type){
            case "Bomb":
                icon = new ImageIcon("Images\\bomb.png");
                break;
            case "Reinforce":
                icon = new ImageIcon("Images\\shovel.png");
                break;
            case "Shield":
                icon = new ImageIcon("Images\\helmet.png");
                break;
            case "Star":
                icon = new ImageIcon("Images\\star.png");
                break;
            case "Tank":
                icon = new ImageIcon("Images\\miniTank.png");
                break;
            case "Time":
                icon = new ImageIcon("Images\\clock.png");
                break;
        }
        JLabel lblBonus = new JLabel(icon);
        lblBonus.setSize(64, 64);
        lblBonus.setBackground(Color.BLACK);
        lblBonus.setForeground(Color.BLACK);
        Bonus bonus = new Bonus(type, posX, posY);
        bonusArray.add(bonus);
        bonus.setLabel(lblBonus);
        boardCells[posX][posY].add(lblBonus); // Coloca en la nueva posición
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
                } else if(enemiesArray.stream().anyMatch(enemy -> enemy.getLabel() == lbl)){
                    return 7;
                } else if(playerTank.getLabel() == lbl){
                    return 8;                    
                }else if (eagle.getLabel() == lbl) {
                    return 1; // Si coincide con el águila
                }else if (bushesArray.stream().anyMatch(bush -> bush.getLabel() == lbl)) {
                    return 4; // Si coincide con el águila 
                }else if (bonusArray.stream().anyMatch(bonus -> bonus.getLabel() == lbl)) {
                    return 9; // Si coincide con el águila  
                }else{
                    return -1;
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
    private Bonus findBonusAtPosition(int posX, int posY) {
        for (Bonus bonus : bonusArray) {
            if (bonus.getPosX() == posX && bonus.getPosY() == posY) {
                return bonus;
            }
        }
        return null;
    }    
    private EnemyTank findEnemyAtPosition(int posX, int posY) {
        for (EnemyTank enem : enemiesArray) {
            if (enem.getPosX() == posX && enem.getPosY() == posY) {
                return enem;
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
    private boolean checkBonusCollision(int posX, int posY){
        // Verificar si hay colisión en la posición proporcionada
        int cellType = getCellType(posX, posY); // Obtener el tipo de celda en la posición   
        
        return cellType == 9;
    }
    private boolean checkTankCollision(int posX, int posY){
        // Verificar si hay colisión en la posición proporcionada
        int cellType = getCellType(posX, posY); // Obtener el tipo de celda en la posición

        // Verificar si la celda es bloqueante (agua, ladrillo o ladrillo de metal)
        return cellType == 1 || cellType == 2 || cellType == 3 || cellType == 5 || cellType ==7 || cellType ==8;
    }
    
    private boolean checkShellCollision(int posX, int posY){
        // Verificar si hay colisión en la posición proporcionada
        int cellType = getCellType(posX, posY); // Obtener el tipo de celda en la posición

        // Verificar si la celda es bloqueante (ladrillo o ladrillo de metal o aguila)
        return cellType == 1 || cellType == 2 || cellType == 3 || cellType ==7 || cellType ==8;        
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
                        finishedLevel();
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
            case 7: //Colisiona con enemigo
                EnemyTank collidedEnemy = findEnemyAtPosition(posX, posY);
                if (collidedEnemy != null) {
                    System.out.println("Vida: " + collidedEnemy.getHp());
                    collidedEnemy.decreaseHealth(); // Reducir la vida del enemigo
                    System.out.println("Vida despues: " + collidedEnemy.getHp());
                    if (collidedEnemy.getHp() <= 0) {
                        for (EnemiesThread et : enemThreadArray){
                            if(et.getEnemy() == collidedEnemy){
                                et.setIsRunning(false);
                            }
                        }
                        // Eliminar el enemigo del tablero y de la lista de ladrillos
                        erasePreviousPosition(posX, posY, collidedEnemy.getLabel());
                        enemiesArray.remove(collidedEnemy);
                        playerTank.addKillObserver();
                    }
                }                
                break;

        }
    }    
    private void handleEnemyCollision(Shell shell, int posX, int posY) {
        int cellType = getCellType(posX, posY); // Obtener el tipo de celda en la posición

        switch (cellType) {
            case 1:
                if(eagle != null){
                    eagle.decreaseHealth();
                    if (eagle.getHp() <= 0) {
                        // Eliminar el ladrillo del tablero y de la lista de ladrillos
                        erasePreviousPosition(posX, posY, eagle.getLabel());
                        finishedLevel();
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
            case 8: //Colisiona con jugador
                PlayerTank collidedPlayer = playerTank;
                if (collidedPlayer != null) {
                    System.out.println("Vida: " + collidedPlayer.getHp());
                    collidedPlayer.lesslifes(); // Reducir la vida del jugador
                    System.out.println("Vida despues: " + collidedPlayer.getHp());
                    if (collidedPlayer.getHp() <= 0) {
                        // Eliminar el jugador
                        erasePreviousPosition(posX, posY, collidedPlayer.getLabel());
                        finishedLevel();
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
                    if(checkBonusCollision(posX, posY)){
                        Bonus bonus = findBonusAtPosition(posX, posY);
                        erasePreviousPosition(posX, posY, bonus.getLabel());
                        String type = bonus.getType();
                        System.out.println(""+ bonus.getType());
                        
                        IStrategy stratBonus = null;
                        switch(type){
                            case "Bomb":
                                 stratBonus = new BombBonus();
                                break;
                            case "Reinforce":
                                 stratBonus = new ReinforceBonus();
                                break;
                            case "Shield":
                                stratBonus = new ShieldBonus();
                                break;
                            case "Star":
                                 stratBonus = new StarBonus();
                                break;
                            case "Tank":
                                 stratBonus = new TankBonus();
                                break;
                            case "Time":
                                 stratBonus = new TimeBonus();
                                break;
                        }

                        addBonusStrategy(stratBonus);
                        applyBonusesToGame();
                    }
                    
                    erasePreviousPosition(playerTank.getPosX(), playerTank.getPosY(), playerTank.getLabel()); // Eliminar la posición anterior

                    playerTank.setPosX(posX);
                    playerTank.setPosY(posY);

                    drawPlayerTank(posX, posY, icon); // Dibujar el tanque en el tablero

                    // Actualizar el tiempo del último movimiento
                    playerTank.updateLastMoveTime();
                    //pruebaShieldBonus();
                    //finishedLevel();
                }
            } 
        }
    }

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
    
    public EnumDirection pathFinding(EnemyTank enem) {
        int dx = enem.getPosX() - 12;
        int dy = enem.getPosY() - 8;
        EnumDirection initialDirection;

        // Compara las diferencias en las coordenadas para determinar la dirección
        if (Math.abs(dx) > Math.abs(dy)) {
            if (dx > 0) {
                initialDirection = UP; // Si dx es positivo, mueve hacia arriba
            } else {
                initialDirection = DOWN; // Si dx es negativo, mueve hacia abajo
            }
        } else {
            if (dy > 0) {
                initialDirection = LEFT; // Si dy es positivo, mueve hacia la izquierda
            } else {
                initialDirection = RIGHT; // Si dy es negativo, mueve hacia la derecha
            }
        }

        // Intenta moverse en la dirección calculada inicialmente
        if (canMoveInDirection(enem, initialDirection)) {
            return initialDirection; // Si puede moverse, retorna la dirección inicial
        } else {
            // Si no puede moverse en la dirección inicial, elige una dirección aleatoria entre las disponibles
            EnumDirection[] directions = {UP, DOWN, LEFT, RIGHT};
            Random random = new Random();
            EnumDirection randomDirection;

            do {
                randomDirection = directions[random.nextInt(directions.length)];
            } while (randomDirection == initialDirection || !canMoveInDirection(enem, randomDirection));

            return randomDirection; // Retorna una dirección aleatoria diferente a la inicial
        }
    }

    // Función para verificar si se puede mover en una dirección específica
    private boolean canMoveInDirection(EnemyTank enem, EnumDirection direction) {
        int posX = enem.getPosX();
        int posY = enem.getPosY();

        switch (direction) {
            case UP:
                posX--;
                break;
            case DOWN:
                posX++;
                break;
            case LEFT:
                posY--;
                break;
            case RIGHT:
                posY++;
                break;
        }

        return inBounds(posX, posY) && !checkTankCollision(posX, posY);
    }
    
    // Función para calcular la distancia entre dos puntos en un tablero
    private double calculateDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }  
    
    public void moveEnemyTank(EnemyTank enem){ 
        long currentTime = System.currentTimeMillis(); // Obtener el tiempo actual
        
        EnumDirection dir = pathFinding(enem);
        enem.setDir(dir); // Sets the new direction the tank is facing and icon
        
        int posX = enem.getPosX();
        int posY = enem.getPosY();        
        switch(dir){
            case UP:
                posX--;
                break;
            case DOWN:
                posX++;
                break;
            case LEFT:
                posY--;
                break;
            case RIGHT:
                posY++;
                break;            
        }
        
        double distanceToEagle = calculateDistance(posX, posY, eagle.getPosX(), eagle.getPosY());
        double distanceToPlayer = calculateDistance(posX, posY, playerTank.getPosX(), playerTank.getPosY());
        if ((distanceToEagle <= 3 || distanceToPlayer <= 4)&& (currentTime - enem.getLastFireTime() >= enem.getFireRate())) {
            fireShellEnemy(enem);
            enem.setLastFireTime(currentTime); // Actualizar el tiempo del último disparo
            return;
        }
        if (inBounds(posX, posY)){ // Verificar los límites del tablero
            if (!checkTankCollision(posX, posY)) { // Verificar la colisión
                
                
                erasePreviousPosition(enem.getPosX(), enem.getPosY(), enem.getLabel()); // Eliminar la posición anterior

                enem.setPosX(posX);
                enem.setPosY(posY);

                drawEnemyTank(posX, posY, enem.getIcon(), enem); // Dibujar el tanque en el tablero
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
    
    public void moveEnemyShell(ShellEnemThread st, Shell shell, EnumDirection dir, int posX, int posY) {

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
            handleEnemyCollision(shell, nextPosX, nextPosY);
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
    
    public void fireShellEnemy(EnemyTank enemy){
        Shell shell = new Shell(enemy.getPosX(), enemy.getPosY());
        ShellEnemThread st = new ShellEnemThread(enemy.getFireRate(), shell, enemy.getDir(), this);    
        st.start(); // Init shell thread
    }
    
    public void addBonusStrategy(IStrategy bonusStrategy) {
        bonusStrategies.add(bonusStrategy);
        playerTank.addbonusobserver();
    }
    
    public void pruebaShieldBonus(){ //Base para que sepa como va
        IStrategy shieldBonus = new ShieldBonus();
        System.out.println("new shieldbonus");
        addBonusStrategy(shieldBonus);
        applyBonusesToGame();
    }
    
    public void applyBonusesToGame() {
        for (IStrategy bonusStrategy : bonusStrategies) {
            bonusStrategy.applyBonus(this);
        }
        bonusStrategies.clear(); // Clear the list after applying bonuses
    }
    
    public void killEnemies(){
       /* for (EnemiesThread enemyThread : enemThreadArray) {
            Enemy enemy = enemyThread.getEnemy;
            enemy.setHp(0);
            playerTank.addKillObserver();
        }
        finishedLevel();*/
    }
    
    public void finishedLevel(){
        gameView.finishLevel();
    }
        
    public void protectBase(){
        
    }
    
    public void protectTank(){
        playerTank.protectTank();
    }
    
    public void increaseShooting(){
        //playerTank.setFireRate(playerTank.getFireRate()+1000);
    }
    
    public void increaseTankSpeed(){
       // playerTank.setTankVel((playerTank.getTankVel()+1000000000));
    }
    
    public void pauseEnemies(){
        // Pausar 10 seg el thread de enemigos
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
    
    public void updateLifesLabel(){
        gameView.getPlayersLife().setText("Players Life : " + playerTank.getHp());
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

    
    public void enemyEvents(EnemyTank enemy){
        moveEnemyTank(enemy);
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
            shotsfired ++;
            updateShotsLabel(); 
            }        
        if (command.equals("killedTank")) {
            System.out.println("Killed Tank");
            killedTank++;
            updateKilledTanksLabel();
        }
        if (command.equals("bonus")) {
            bonusLevel++;
            updateBonusLabel();
        }
        if (command.equals("pause")) {
            pauseGame();
        }
        if (command.equals("lifes")) {
            updateLifesLabel();
        }
    }
}
