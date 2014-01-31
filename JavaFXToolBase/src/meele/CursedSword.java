/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package meele;

import basis.Player;
import basis.SkillType;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafxtoolbase.Damage;
import meele.modifier.Modifier;

/**
 *
 * @author tezuro
 */
public class CursedSword implements MeeleWeapon{
    private final Set<Modifier> modi = new HashSet<>();
    final Player owner;
    
    public CursedSword(final Player owner){
        this.owner = owner;
    }

    @Override
    public Damage getDamageOnTarget(final Player target) {
        Random r = new Random();
        if(owner.STATS.armor.accuracyDebuff.doubleValue() < r.nextDouble()){
            return new Damage(target, 0.0);
        }
        Damage d = new Damage(target,200.0);
        target.STATS.armor.attack(d);
        for(Modifier m:modi){
            m.hit(target, d);
        }
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


    
}
