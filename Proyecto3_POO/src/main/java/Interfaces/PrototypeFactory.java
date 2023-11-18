/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

import java.util.HashMap;

/**
 *
 * @author pavel
 */
public class PrototypeFactory {
    private static HashMap<String,Prototype> prototypes = new HashMap<>();       
    
    // getPrototype del hash por nombre, pero CLONADO
    public static Prototype getPrototype(String prototypeName){           
        return prototypes.get(prototypeName).deepClone();   
        //return prototypes.get(prototypeName).clone(); 
    }       
    
    // add prototype al hash
    public static void addPrototype(String prototypeName,Prototype prototype){   
        prototypes.put(prototypeName, prototype);   
    }
}
