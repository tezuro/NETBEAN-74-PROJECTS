/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meele;

import basis.Player;
import javafx.animation.ParallelTransition;
import javafxtoolbase.Damage;
import javafxtoolbase.UnpurgeableBonus;

/**
 *
 * @author tezuro
 */
public class OneHandedMeleeWeapon extends AbstractMeleeWeapon {


    public OneHandedMeleeWeapon(final Player owner, final String weaponName, final double weight) {
        super(owner, weaponName, weight);
    }

    @Override
    public boolean equipWeapon() {

        if (weaponWeight > owner.STATS.MAX_WEAPON_WEIGHT_ONE_HANDED.doubleValue()) {
            return false;
        } else {
            owner.STATS.MAX_WEAPON_WEIGHT_ONE_HANDED.addListener(weightListener);
        }

        for (UnpurgeableBonus bonus : weaponBonus) {
            bonus.equip(owner);
        }
        return true;
    }

    @Override
    public boolean unequipWeapon() {
        owner.STATS.MAX_WEAPON_WEIGHT_ONE_HANDED.removeListener(weightListener);
        for (UnpurgeableBonus bonus : weaponBonus) {
            bonus.unequip(owner);
        }
        return true;
    }

    @Override
    public TYPE getType() {
        return TYPE.ONE_HAND;
    }

    @Override
    void prepareAnimation(ParallelTransition animation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void prepareAnimation(ParallelTransition animation, Damage damage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
