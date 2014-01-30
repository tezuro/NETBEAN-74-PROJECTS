/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package meele;

import basis.Player;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafxtoolbase.ComboChain.Chainable;
import javafxtoolbase.Damage;

/**
 *
 * @author tezuro
 */
public interface MeeleWeapon extends Chainable{

    Damage getDamageOnTarget(Player target);
    
    void equipWeapon();
    void unequipWeapon();
    
    public static final MeeleWeapon NO_WEAPON = new MeeleWeapon() {

        @Override
        public Damage getDamageOnTarget(Player target) {
            return new Damage(target, 0.0);
        }

        @Override
        public void equipWeapon() {
        }

        @Override
        public void unequipWeapon() {
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
    };
}
