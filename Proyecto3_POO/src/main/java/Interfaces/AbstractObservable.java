/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractObservable implements Observable {          
    public List<Observer> observers = new ArrayList<>();
    
    @Override       
    public void addObserver(Observer observer) {           
        this.observers.add(observer);
    }                 
    
    @Override       
    public void removeObserver(Observer observer) {           
        this.observers.remove(observer);
    }          
    
    @Override       
    public void notifyAllObservers(String command, String message) {           
        for (Observer observer : observers) {               
            observer.notifyObserver(command, message);
        }        
    }
}

