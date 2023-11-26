
package Prototypes;

import Interfaces.IPrototype;


public interface Tank extends IPrototype<Tank>{
    void move();
    void shoot();
    int getResistance();
    // Other methods later
}
