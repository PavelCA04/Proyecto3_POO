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
public class ShieldBonus implements IStrategy {
    @Override
    public void applyBonus(GameController gamecontroller) {
        System.out.println("Casco Bonus: Tank has a protective barrier for 10 seconds");
        // Podria subirsele la vida a 10000 o algo exagerado el tanque y es como si fuera invencible quizas
        gamecontroller.protectTank();
    }
}
