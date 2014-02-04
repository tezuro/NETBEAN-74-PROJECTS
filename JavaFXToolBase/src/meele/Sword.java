/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meele;

import basis.Player;
import basis.SkillType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafxtoolbase.Damage;
import meele.modifier.Modifier;

/**
 *
 * @author tezuro
 */
public class Sword implements MeeleWeapon {
    private final TYPE type;
    private final List<Modifier> modi = new ArrayList<>();
    private final List<UnpurgeableBonus> weaponBonus = new ArrayList<>();
    final Player owner;
    final String weaponName;
    protected double weaponWeight = 0.0;

    public Sword(final Player owner, final String weaponName, final TYPE type) {
        this.type = type;
        this.owner = owner;
        this.weaponName = weaponName;
    }

    @Override
    public Damage getDamageOnTarget(final Player target) {
        Random r = new Random();
        if (owner.STATS.armor.accuracyDebuff.doubleValue() < r.nextDouble()) {
            return new Damage(target, 0.0);
        }
        Damage d = new Damage(target, 200.0);
        target.STATS.armor.attack(d);
        for (Modifier m : modi) {
            m.hit(target, d);
        }
        return target.STATS.armor.attack(d);

    }

    @Override
    public boolean equipWeapon() {
        if(TYPE.ONE_HAND.equals(type)&& weaponWeight > owner.STATS.MAX_WEAPON_WEIGHT_ONE_HANDED.doubleValue()){               
            return false;
        }
        if(TYPE.TWO_HAND.equals(type)&& weaponWeight > owner.STATS.MAX_WEAPON_WEIGHT_TWO_HANDED.doubleValue()){               
            return false;
        }
        
        for (UnpurgeableBonus bonus:weaponBonus) {
            bonus.equip(owner);
        }
        return true;
    }

    @Override
    public boolean unequipWeapon() {
        for (UnpurgeableBonus bonus:weaponBonus) {
            bonus.unequip(owner);
        }
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
        return SkillType.OFFENSIVE;
    }

    @Override
    public boolean executeSkillOnTarget(Player target) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static interface UnpurgeableBonus {

        public void equip(final Player owner);

        public void unequip(final Player owner);

    }

}
