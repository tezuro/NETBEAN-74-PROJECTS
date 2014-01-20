/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package meele;

import basis.*;
import javafxtoolbase.Damage;

/**
 *
 * @author tezuro
 */
public interface MeeleWeapon {

    Damage getDamageOnTarget(Player target);
    
    void equipWeapon();
    void unequipWeapon();
    
}
