/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Bonus;

import Game.GameController;
import Interfaces.IStrategy;

/**
 *
 * @author pavel
 */
public class BombBonus implements IStrategy {
    @Override
    public void applyBonus(GameController gamecontroller) {
        System.out.println("Bomba Bonus: Destroy all enemies on the screen");
        // Implementation of destroying all enemies
        gamecontroller.killEnemies();
    }
}
