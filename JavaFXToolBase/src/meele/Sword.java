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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    protected final double weaponWeight;
    protected final double weaponRange;

    public Sword(final Player owner, final String weaponName, final TYPE type, final double weight) {
        this.weaponWeight = weight;
        this.type = type;
        this.owner = owner;
        this.weaponName = weaponName;
        weaponRange = (weaponWeight / 100) + 70;
    }

    @Override
    public Damage getDamageOnTarget(final Player target) {
        Random r = new Random();
        if (owner.STATS.armor.accuracyDebuff.doubleValue() < r.nextDouble()) {
            return new Damage(target, 0.0);
        }
        final double damage = weaponWeight * owner.STATS.AGILITY.binding.get() / 100;
        Damage d = new Damage(target, damage);
        target.STATS.armor.attack(d);
        for (Modifier m : modi) {
            m.hit(target, d);
        }
        return target.STATS.armor.attack(d);

    }

    @Override
    public boolean equipWeapon() {

        if (TYPE.ONE_HAND.equals(type)) {
            if (weaponWeight > owner.STATS.MAX_WEAPON_WEIGHT_ONE_HANDED.doubleValue()) {
                return false;
            } else {
                owner.STATS.MAX_WEAPON_WEIGHT_ONE_HANDED.addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                        if (weaponWeight > newValue.doubleValue()) {
                            unequipWeapon();
                        }
                    }
                });
            }
        }
        if (TYPE.TWO_HAND.equals(type)) {
            if (weaponWeight > owner.STATS.MAX_WEAPON_WEIGHT_TWO_HANDED.doubleValue()) {
                return false;
            } else {
                owner.STATS.MAX_WEAPON_WEIGHT_TWO_HANDED.addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                        if (weaponWeight > newValue.doubleValue()) {
                            unequipWeapon();
                        }
                    }
                });
            }
        }

        for (UnpurgeableBonus bonus : weaponBonus) {
            bonus.equip(owner);
        }
        return true;
    }

    @Override
    public boolean unequipWeapon() {
        for (UnpurgeableBonus bonus : weaponBonus) {
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
        /**
         * hier muss die entsprechende animation hin 
         * am ende der animation wird noch mal isSkillReady() aufgerufen und nur wenn dann true zurueckkommt wird damage gemacht
         * weil wenn wir nicht mehr in range sind wird auch kein schaen gemacht
         * 
         * 
         * schauen ob man fuer hoehere combocounts andere animationen macht
         * 
         * dauer der animation -> (W /  (STR*AGI)) * DEX /100
         */
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
