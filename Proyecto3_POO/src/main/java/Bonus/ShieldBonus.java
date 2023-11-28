/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Bonus;

import Interfaces.IStrategy;

/**
 *
 * @author pavel
 */
public class ShieldBonus implements IStrategy {
    @Override
    public void applyBonus() {
        System.out.println("Casco Bonus: Tank has a protective barrier for 10 seconds");
        // Implementation of providing a protective barrier
    }
}
