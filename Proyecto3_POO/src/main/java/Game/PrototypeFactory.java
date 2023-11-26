
package Game;

import Prototypes.Tank;
import java.util.HashMap;   

public class PrototypeFactory {
    // hash de prototypes por nombre
    private static HashMap<String,Tank> prototypes = new HashMap<>();       
    
    // getPrototype del hash por nombre, pero CLONADO
    public static Tank getPrototype(String prototypeName){           
        return prototypes.get(prototypeName).deepClone();   
        //return prototypes.get(prototypeName).clone(); 
    }       
    
    // add prototype al hash
    public static void addPrototype(String prototypeName, Tank prototype){   
        prototypes.put(prototypeName, prototype);   
    }
}

