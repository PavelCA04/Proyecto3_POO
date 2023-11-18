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
public class MoveDown extends BaseCommand{
    @Override
    public String getCommandName() {
        return "MoveDownCommand";
    }
/*
    @Override
    public void execute(Tank tank, OutputStream out) {
        tank.moveDown();
        write(out, "Tank is moving right.\n");
    }*/

    @Override
    public void execute(String[] args, OutputStream out) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
