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
public class TimeBonus implements IStrategy {
    @Override
    public void applyBonus() {
        System.out.println("Reloj Bonus: Enemies are frozen for 10 seconds");
        // Implementation of freezing enemies
    }
}
