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
public class TankBonus implements IStrategy {
    @Override
    public void applyBonus() {
        System.out.println("Tanque Bonus: Tank moves faster");
        // Implementation of increasing tank speed
    }
}
