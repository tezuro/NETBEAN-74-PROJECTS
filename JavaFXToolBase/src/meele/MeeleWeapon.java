/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package meele;

import basis.Player;
import basis.SkillType;
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
    TYPE getType();
    boolean equipWeapon();
    boolean unequipWeapon();
    

    
    public static enum TYPE{
        ONE_HAND,TWO_HAND
    }
    
    public static final MeeleWeapon NO_WEAPON = new MeeleWeapon() {

        @Override
        public Damage getDamageOnTarget(Player target) {
            return new Damage(target, 0.0);
        }

        @Override
        public boolean equipWeapon() {
            return true;
        }

        @Override
        public boolean unequipWeapon() {
            return true;
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
        public boolean isSkillReady(Player target) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public SkillType isSupportSkill() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean executeSkillOnTarget(Player target) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public TYPE getType() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }


    };
}
