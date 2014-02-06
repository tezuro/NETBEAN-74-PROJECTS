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
import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafxtoolbase.Damage;
import javafxtoolbase.UnpurgeableBonus;
import meele.modifier.Modifier;

/**
 *
 * @author tezuro
 */
abstract class AbstractMeleeWeapon implements MeeleWeapon {

    protected final List<Modifier> modi = new ArrayList<>();
    protected final List<UnpurgeableBonus> weaponBonus = new ArrayList<>();
    final Player owner;
    final String weaponName;
    protected final double weaponWeight;
    protected final double weaponRange;

    protected final ParallelTransition animation = new ParallelTransition();

    protected final ChangeListener weightListener = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
            if (weaponWeight > newValue.doubleValue()) {
                unequipWeapon();
            }
        }
    };

    public AbstractMeleeWeapon(final Player owner, final String weaponName, final double weight) {
        this.weaponWeight = weight;
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
        return d;

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
    public double getCooldownDuration(Player owner, Player target) {
        return 0.0;
    }

    @Override
    public Transition getSkillAnimation(final Player owner, final Player target, final Damage damage, final SequentialTransition comboChainAnimation) {

        /**
         * hier muss die entsprechende animation hin am ende der animation wird
         * noch mal isSkillReady() aufgerufen und nur wenn dann true
         * zurueckkommt wird damage gemacht weil wenn wir nicht mehr in range
         * sind wird auch kein schaen gemacht
         *
         *
         * schauen ob man fuer hoehere combocounts andere animationen macht
         *
         * dauer der animation -> (W / (STR*AGI)) * DEX /100
         */
        animation.getChildren().clear();

        prepareAnimation(animation, damage);

        animation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (isTargetInRange()) {
                    Damage attackDamage = getDamageOnTarget(target);
                    final int comboCount = damage.comboCount.get() + 1;
                    attackDamage.comboCount.set(comboCount);
                    damage.comboCount.set(comboCount);
                    attackDamage.doTheDamage();
                } else {
                    damage.isInterrupted.set(true);
                    comboChainAnimation.stop();
                }
            }
        });
        return animation;
    }

    protected boolean isTargetInRange() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isSkillReady(final Player target) {
        return !Animation.Status.RUNNING.equals(animation.getStatus()) && isTargetInRange();
    }

    @Override
    public SkillType isSupportSkill() {
        return SkillType.OFFENSIVE;
    }

    @Override
    public boolean executeSkillOnTarget(final Player target) {
        if (isSkillReady(target)) {
            animation.getChildren().clear();

            prepareAnimation(animation);

            animation.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    if (isTargetInRange()) {
                        getDamageOnTarget(target).doTheDamage();
                    }
                }
            });
            animation.play();
        }
        return false;
    }


    abstract void prepareAnimation(ParallelTransition animation);

    abstract void prepareAnimation(ParallelTransition animation, Damage damage);

}
