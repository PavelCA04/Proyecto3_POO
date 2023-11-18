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
public class MoveLeft extends BaseCommand{
    @Override
    public String getCommandName() {
        return "MoveLeftCommand";
    }
/*
    @Override
    public void execute(Tank tank, OutputStream out) {
        tank.moveLeft();
        write(out, "Tank is moving left.\n");
    }*/

    @Override
    public void execute(String[] args, OutputStream out) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
