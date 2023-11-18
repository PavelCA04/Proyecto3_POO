/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

/**
 *
 * @author pavel
 */
public class Memento {
    public String state;//Aqui es el objeto al que le queremos hacer el memento

    public Memento(String state) {
        this.state = state;
    }

    public String getMemento() {
        return state;
    }
}


