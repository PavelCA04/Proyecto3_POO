/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

/**
 *
 * @author pavel
 */
public interface Prototype<T extends Prototype> extends Cloneable{       
    public T clone();       
    public T deepClone();   
}
    

