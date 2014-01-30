/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package meele;

import basis.Player;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafxtoolbase.Damage;

/**
 *
 * @author tezuro
 */
public class CursedSword implements MeeleWeapon{

    @Override
    public Damage getDamageOnTarget(Player target) {
        Damage d = new Damage(target,200.0);
        return target.STATS.armor.attack(d);
        
    }

    @Override
    public void equipWeapon() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void unequipWeapon() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getCooldownDuration(Player owner, Player target) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Transition getSkillAnimation(Player owner, Player target, Damage damage, SequentialTransition comboChainAnimation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isReady(Player owner, Player target) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
