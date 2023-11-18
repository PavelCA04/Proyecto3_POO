/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

import java.io.OutputStream;

/**
 *
 * @author pavel
 */
public class FireTank extends BaseCommand {
    @Override
    public String getCommandName() {
        return "FireCommand";
    }

    /*@Override
    public void execute(Tank tank, OutputStream out) {
        tank.fire();
        write(out, "Tank is firing.\n");
    }*/

    @Override
    public void execute(String[] args, OutputStream out) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

