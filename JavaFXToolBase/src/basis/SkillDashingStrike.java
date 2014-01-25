/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basis;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import javafxtoolbase.Attribute;
import javafxtoolbase.ComboSystem.ComboSkill;
import javafxtoolbase.Damage;

/**
 * Blinks to target and strikes for SkillLevel% of the equiped meele weapon
 *
 * @author tezuro
 */
public class SkillDashingStrike implements ComboSkill{

    final private Attribute skillLevel = new Attribute();
    final private Attribute multipleStrike = new Attribute();
    final private Player owner;

    private boolean singelTarget = true;
    private final PauseTransition cooldown = new PauseTransition();
    private final List<HIT> hits = new ArrayList();

    public SkillDashingStrike(final Player owner) {
        this.owner = owner;
        cooldown.setCycleCount(1);
    }

    public boolean executeSkillOnTarget(final Player target) {
        if (isSkillReady() && target.STATS.nullify.isAbilityNullified()) {
            double cd;
            if (owner.isSkillEquiped(SkillFlash.SKILL_ID)) {
                cd = owner.STATS.flash.activate(target);
                executeHits(target);
            } else {
                cd = owner.STATS.jump.activate(target, new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        executeHits(target);
                    }
                });
            }

            owner.STATS.MANA.reduceBase(cd + getCooldown());
            cooldown.setDuration(Duration.millis(cd + getCooldown()));
            cooldown.play();
            return true;
        }
        return false;
    }

    @Override
    public boolean executeSkillOnTargetInCombo(final Player target, final int comboCount) {
        if (isSkillReady() && target.STATS.nullify.isAbilityNullified()) {
            double cd;
            if (owner.isSkillEquiped(SkillFlash.SKILL_ID)) {
                cd = owner.STATS.flash.activate(target);
                executeHits(target);
            } else {
                cd = owner.STATS.jump.activate(target, new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        executeHits(target);
                    }
                });
            }

            owner.STATS.MANA.reduceBase(cd + getCooldown());
            cooldown.setDuration(Duration.millis(cd + getCooldown()));
            cooldown.play();
            return true;
        }
        return false;
    }

    private void executeHits(final Player target) {
        for (HIT hit : hits) {
            Damage damage = owner.STATS.meleeWeapon.getDamageOnTarget(target);
            damage.damage.set(damage.damage.doubleValue() * skillLevel.base.doubleValue() / 100);
            damage.doTheDamage();
        }
    }

    private void executeHitsInCombo(final Player target, final double comboCount) {
        for (HIT hit : hits) {
            Damage damage = owner.STATS.meleeWeapon.getDamageOnTarget(target);
            damage.damage.set(damage.damage.doubleValue() * skillLevel.base.doubleValue() * (1.0 + (0.1 * comboCount)) / 100);
            damage.doTheDamage();
        }
    }

    @Override
    public boolean isSkillReady() {
        return !Animation.Status.RUNNING.equals(cooldown.getStatus()) && owner.STATS.IS_NOT_BUSY.get();
    }

    @Override
    public boolean isInRange(final Player target) {
        if (owner.isSkillEquiped(SkillFlash.SKILL_ID)) {
            return true;
        } else {
            return owner.STATS.jump.isInRange(target);
        }
    }

    public boolean isSingelTarget() {
        return singelTarget;
    }

    public void setSingelTarget(boolean singelTarget) {
        this.singelTarget = singelTarget;
    }

    private double getCooldown() {
        return hits.size() * (2000.0 - owner.STATS.DEXTERITY.binding.doubleValue());
    }

    @Override
    public boolean isSupportSkill() {
        return false;
    }

    private enum HIT {

        HIT
    }

}
